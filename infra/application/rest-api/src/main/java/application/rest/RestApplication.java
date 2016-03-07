package application.rest;

import application.rest.mapper.FileDTOMapper;
import application.rest.resource.FileManagerResource;
import domain.filemanager.api.FileManagerService;
import domain.filemanager.core.FileManagerServiceImpl;
import domain.filemanager.spi.FileEventNotification;
import domain.notificationcenter.api.NotificationCenterService;
import domain.notificationcenter.core.NotificationCenterServiceImpl;
import domain.notificationcenter.spi.NotificationService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import notification.irc.configuration.IrcConfigurationBuilder;
import notification.irc.IrcNotification;
import notification.mail.MailNotification;
import notification.mail.configuration.MailConfigurationBuilder;
import persistence.inmemory.repository.InMemoryRepositoryNotification;

import java.util.Arrays;
import java.util.Collections;

public class RestApplication extends Application<RestConfiguration> {

    public static void main(String[] args) throws Exception {
        new RestApplication().run(args);
    }

    @Override
    public String getName() {
        return "rest-api";
    }

    @Override
    public void initialize(Bootstrap<RestConfiguration> bootstrap) {
    }

    @Override
    public void run(RestConfiguration configuration, Environment environment) {
        // Init Domain
        InMemoryRepositoryNotification fileRepository = new InMemoryRepositoryNotification();
        InMemoryRepositoryNotification notificationRepository = new InMemoryRepositoryNotification();

        // Notification Service
        NotificationService mailNotificationService = new MailNotification("thibaut@alantaya.com", "###########");
        NotificationService ircNotificationService = new IrcNotification("irc.freenode.org");
        NotificationCenterService notificationCenterService = new NotificationCenterServiceImpl(
                Arrays.asList(mailNotificationService, ircNotificationService),
                notificationRepository,
                notificationRepository);

        // File Manager
        FileManagerService fileManagerService = new FileManagerServiceImpl(fileRepository, notificationCenterService);

        // Set Notification service
        notificationCenterService.setUserServiceConfiguration("Thibaut",
                new IrcConfigurationBuilder().setChannel("#HexagonalIsAwsome").build());
        notificationCenterService.setUserServiceConfiguration("Thibaut",
                new MailConfigurationBuilder().setEmail("thibaut.mottet@gmail.com").build());

        // Set notification parameter to user
        notificationCenterService.setUserNotificationSetting("Thibaut",
                Collections.emptyList(),
                Collections.singletonList(FileEventNotification.Type.DELETE),
                true);
        notificationCenterService.setUserNotificationSetting("Thibaut",
                Collections.singletonList(ircNotificationService.getServiceId()),
                Collections.singletonList(FileEventNotification.Type.GET),
                true);
        notificationCenterService.setUserNotificationSetting("Thibaut",
                Collections.singletonList(mailNotificationService.getServiceId()),
                Collections.singletonList(FileEventNotification.Type.ADD),
                true);

        // REST Dependency injection
        FileDTOMapper fileDTOMapper = FileDTOMapper.INSTANCE;
        FileManagerResource fileManagerResource = new FileManagerResource(fileManagerService, fileDTOMapper);

        // Register controller
        environment.jersey().register(fileManagerResource);
    }
}