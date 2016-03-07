package application.commandline;

import domain.filemanager.api.FileManagerService;
import domain.filemanager.api.entity.File;
import domain.filemanager.core.FileManagerServiceImpl;
import domain.filemanager.spi.FileEventNotification;
import domain.notificationcenter.api.NotificationCenterService;
import domain.notificationcenter.core.NotificationCenterServiceImpl;
import domain.notificationcenter.spi.NotificationService;
import notification.irc.configuration.IrcConfigurationBuilder;
import notification.irc.IrcNotification;
import notification.mail.MailNotification;
import notification.mail.configuration.MailConfigurationBuilder;
import persistence.inmemory.repository.InMemoryRepositoryNotification;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Application {

    public static void main(String[] args) throws Exception {
        /***** Init *****/
        // Repository
        //FileRepository fileRepository = SQLPersistence.get();
        InMemoryRepositoryNotification notificationRepository = new InMemoryRepositoryNotification();

        // Notification Service
        NotificationService mailNotificationService = new MailNotification("thibaut@alantaya.com", "###########");
        NotificationService ircNotificationService = new IrcNotification("irc.freenode.org");
        NotificationCenterService notificationCenterService = new NotificationCenterServiceImpl(
                Arrays.asList(mailNotificationService, ircNotificationService),
                notificationRepository,
                notificationRepository);

        // File Manager
        FileManagerService fileManagerService = new FileManagerServiceImpl(notificationRepository, notificationCenterService);


        /***** Run *****/
        // Set Notification service
        notificationCenterService.setUserServiceConfiguration("Thibaut",
                new IrcConfigurationBuilder().setChannel("#HexagonalIsAwsome").build());
        System.out.println("Thibaut init IRC service");

        notificationCenterService.setUserServiceConfiguration("Thibaut",
                new MailConfigurationBuilder().setEmail("thibaut.mottet@gmail.com").build());
        System.out.println("Thibaut init Mail service");

        // Set notification parameter to user
        notificationCenterService.setUserNotificationSetting("Thibaut",
                Collections.emptyList(),
                Collections.singletonList(FileEventNotification.Type.DELETE),
                true);
        System.out.println("Thibaut accept to receive delete file notification through all services");

        notificationCenterService.setUserNotificationSetting("Thibaut",
                Collections.singletonList(ircNotificationService.getServiceId()),
                Collections.singletonList(FileEventNotification.Type.GET),
                true);
        System.out.println("Thibaut accept to receive get file notification through "+ ircNotificationService.getServiceId());

        notificationCenterService.setUserNotificationSetting("Thibaut",
                Collections.singletonList(mailNotificationService.getServiceId()),
                Collections.singletonList(FileEventNotification.Type.ADD),
                true);
        System.out.println("Thibaut accept to receive add file notification through "+ mailNotificationService.getServiceId());


        // File manage
        File file = fileManagerService.addFile("test1.txt", "test1.txt".getBytes(), "Thibaut");
        System.out.println("Add new file   -> " + file);

        file = fileManagerService.getFile(file.getId(), "Thibaut");
        System.out.println("Get file id: " + file.getId() + " -> " + file);

        file = fileManagerService.addFile("test2.txt", "test2.txt".getBytes(), "Thibaut");
        System.out.println("Add new file   -> " + file);

        List<File> files = fileManagerService.getAllFiles("Thibaut");
        System.out.println("Get all files id: " + files);

        fileManagerService.deleteFile("1", "Thibaut");
        System.out.println("delete file id: " + 1);

        files = fileManagerService.getAllFiles("Thibaut");
        System.out.println("Get all files id: " + files);
    }
}
