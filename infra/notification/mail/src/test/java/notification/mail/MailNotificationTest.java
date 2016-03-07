package notification.mail;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.spi.FileEventNotification;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MailNotificationTest {

    @Test
    public void testAddFileEvent_Should_When() throws Exception {
        MailNotification mailNotification = new MailNotification("my@mail.com", "MyPassword", "main-send-to@test.com");

        mailNotification.sendNotification(FileEventNotification.Type.ADD, "Thibaut", Arrays.asList(new MockFile("0", "File1"), new MockFile("1", "File2")), Collections.emptyMap());
        mailNotification.sendNotification(FileEventNotification.Type.DELETE, "Thibaut", Arrays.asList(new MockFile("0", "File1"), new MockFile("1", "File2")), Collections.emptyMap());
        mailNotification.sendNotification(FileEventNotification.Type.SHARE_WITH, "Thibaut", Arrays.asList(new MockFile("0", "File1"), new MockFile("1", "File2")), Collections.singletonMap("Maxime", Permission.GET));
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