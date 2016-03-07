package notification.irc;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.spi.FileEventNotification;
import domain.notificationcenter.api.NotificationServiceConfiguration;
import notification.irc.configuration.IrcConfigurationBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IrcNotificationTest {

    private IrcNotification ircNotification;
    private NotificationServiceConfiguration configuration;

    @Before
    public void setUp() throws Exception {
        ircNotification = new IrcNotification("irc.freenode.org");
        configuration = new IrcConfigurationBuilder().setChannel("#HexgonalIrcSampleTest").build();
    }

    @Test
    public void testIrcEvent() throws Exception {
        while(!ircNotification.isConnected()) {
            Thread.sleep(1000);
        }

        ircNotification.sendNotification(configuration, FileEventNotification.Type.ADD, "Thibaut", Arrays.asList(new MockFile("0", "File1"), new MockFile("1", "File2")), Collections.emptyMap());
        ircNotification.sendNotification(configuration, FileEventNotification.Type.DELETE, "Thibaut", Arrays.asList(new MockFile("0", "File1"), new MockFile("1", "File2")), Collections.emptyMap());
        ircNotification.sendNotification(configuration, FileEventNotification.Type.SHARE_WITH, "Thibaut", Arrays.asList(new MockFile("0", "File1"), new MockFile("1", "File2")), Collections.singletonMap("Maxime", Permission.GET));

        Thread.sleep(10000);
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

        public MockFile(String id, String name, String ownerId, Map<String, Permission> sharedUsersIdWithPermission) {
            this.id = id;
            this.name = name;
            this.ownerId = ownerId;
            this.sharedUsersIdWithPermission = sharedUsersIdWithPermission;
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