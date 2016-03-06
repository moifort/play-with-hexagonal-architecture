package application.rest;

import application.rest.mapper.FileDTOMapper;
import application.rest.resource.FileManagerResource;
import domain.filemanager.api.FileManagerService;
import domain.filemanager.core.FileManagerServiceImpl;
import domain.filemanager.spi.FileEventNotification;
import domain.notificationmanager.api.NotificationManagerService;
import domain.notificationmanager.core.NotificationManagerServiceImpl;
import domain.notificationmanager.spi.FileEventNotificationService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import notification.irc.IrcHandler;
import notification.mail.MailEventNotification;
import persistence.inmemory.repository.InMemoryRepository;

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
        InMemoryRepository fileRepository = new InMemoryRepository();
        FileEventNotificationService mailNotificationService = new MailEventNotification("thibaut@alantaya.com", "mypassword", "thibaut.mottet@gmail.com");
        FileEventNotificationService ircNotificationService = new IrcHandler("irc.freenode.org","#HewaBot");
        NotificationManagerService notificationManagerService = new NotificationManagerServiceImpl(
                Arrays.asList(mailNotificationService, ircNotificationService),
                fileRepository);

        // File Manager
        FileManagerService fileManagerService = new FileManagerServiceImpl(fileRepository, notificationManagerService);

        // Set notification parameter to user
        notificationManagerService.setUserSettingNotification("Thibaut",
                Collections.emptyList(),
                Collections.singletonList(FileEventNotification.Type.DELETE),
                true);
        notificationManagerService.setUserSettingNotification("Thibaut",
                Collections.singletonList(ircNotificationService.getServiceId()),
                Collections.singletonList(FileEventNotification.Type.GET),
                true);
        notificationManagerService.setUserSettingNotification("Thibaut",
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