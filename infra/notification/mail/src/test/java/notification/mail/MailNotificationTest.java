package notification.mail;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.spi.FileEventNotification;
import domain.notificationcenter.api.NotificationServiceConfiguration;
import notification.mail.configuration.MailConfigurationBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MailNotificationTest {

    private MailNotification mailNotification;
    private NotificationServiceConfiguration configuration;

    @Before
    public void setUp() throws Exception {
        mailNotification = new MailNotification("test@mail.com", "testPassword");
        configuration = new MailConfigurationBuilder().setEmail("test-to-send@mail.com").build();
    }

    @Test
    public void testAddFileEvent_Should_When() throws Exception {
        mailNotification.sendNotification(configuration, FileEventNotification.Type.ADD, "Thibaut", Arrays.asList(new MockFile("0", "File1"), new MockFile("1", "File2")), Collections.emptyMap());
        mailNotification.sendNotification(configuration, FileEventNotification.Type.DELETE, "Thibaut", Arrays.asList(new MockFile("0", "File1"), new MockFile("1", "File2")), Collections.emptyMap());
        mailNotification.sendNotification(configuration, FileEventNotification.Type.SHARE_WITH, "Thibaut", Arrays.asList(new MockFile("0", "File1"), new MockFile("1", "File2")), Collections.singletonMap("Maxime", Permission.GET));
    }

    private static class MockFile implements File {
        private String id;
        private String name;
        private String ownerId;
        private Map<String, Permission> sharedUsersIdWithPermission = new HashMap<>();

        public MockFile(String id, String name) {
            this.id = id;
            this.name = name;
        }


        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public byte[] getData() {
            return new byte[0];
        }

        @Override
        public String getOwnerId() {
            return ownerId;
        }

        @Override
        public Map<String, Permission> getSharedUsersIdWithPermission() {
            return sharedUsersIdWithPermission;
        }
    }
}