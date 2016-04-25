package application.rest;

import application.rest.configuration.CORSResponseFilter;
import application.rest.configuration.RestConfiguration;
import application.rest.mapper.FileDTOMapper;
import application.rest.resource.FileManagerResource;
import application.rest.resource.NotificationCenterResource;
import domain.filemanager.api.FileManagerService;
import domain.filemanager.core.FileManagerServiceImpl;
import domain.notificationcenter.api.NotificationCenterService;
import domain.notificationcenter.core.NotificationCenterServiceImpl;
import domain.notificationcenter.spi.NotificationService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import notification.irc.IrcNotification;
import notification.mail.MailNotification;
import persistence.inmemory.repository.InMemoryRepositoryNotification;

import java.util.Arrays;

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
        // Default Rest configuration
        environment.jersey().register(CORSResponseFilter.class);

        // Init Domain
        InMemoryRepositoryNotification fileRepository = new InMemoryRepositoryNotification();
        InMemoryRepositoryNotification notificationRepository = new InMemoryRepositoryNotification();

        // Notification Service
        NotificationService mailNotificationService = new MailNotification("thibaut@alantaya.com", "Doremido91!");
        NotificationService ircNotificationService = new IrcNotification("irc.freenode.org");
        NotificationCenterService notificationCenterService = new NotificationCenterServiceImpl(
                Arrays.asList(mailNotificationService, ircNotificationService),
                notificationRepository,
                notificationRepository);

        // File Manager
        FileManagerService fileManagerService = new FileManagerServiceImpl(fileRepository, notificationCenterService);

        /*
        // Set Notification service
        notificationCenterService.setUserServiceConfiguration("Thibaut",
                new IrcConfigurationBuilder().setChannel("#Maximoto").build());
        notificationCenterService.setUserServiceConfiguration("Thibaut",
                new MailConfigurationBuilder().setEmail("maxime.gelle@gmail.com").build());

        // Set notification parameter to user
        notificationCenterService.setUserNotificationSetting("Thibaut",
                Collections.emptyList(),
                Collections.singletonList(FileEventNotification.Type.DELETE),
                true);
        notificationCenterService.setUserNotificationSetting("Thibaut",
                Collections.singletonList(ircNotificationService.getId()),
                Collections.singletonList(FileEventNotification.Type.GET),
                true);
        notificationCenterService.setUserNotificationSetting("Thibaut",
                Collections.singletonList(mailNotificationService.getId()),
                Collections.singletonList(FileEventNotification.Type.ADD),
                true);*/

        // REST Dependency injection
        FileDTOMapper fileDTOMapper = FileDTOMapper.INSTANCE;
        FileManagerResource fileManagerResource = new FileManagerResource(fileManagerService, fileDTOMapper);
        NotificationCenterResource notificationCenterResource = new NotificationCenterResource(notificationCenterService);

        // Register controller
        environment.jersey().register(fileManagerResource);
        environment.jersey().register(notificationCenterResource);
    }
}