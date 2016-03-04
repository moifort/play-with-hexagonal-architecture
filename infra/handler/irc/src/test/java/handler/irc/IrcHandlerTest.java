package handler.irc;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IrcHandlerTest {


    @Test
    public void testAddFileEvent_Should_When() throws Exception {
        IrcHandler ircHandler = new IrcHandler();

        while(!ircHandler.isConnected()) {
            Thread.sleep(1000);
        }

        ircHandler.getFileEvent("Thibaut", Arrays.asList(new MockFile("0", "File1"), new MockFile("1", "File2")));
        ircHandler.getSharedFileEvent("Thibaut", Arrays.asList(new MockFile("0", "SharedFile1"), new MockFile("1", "SharedFile2")));
        ircHandler.addFileEvent("Thibaut", new MockFile("0", "File1"));
        ircHandler.deleteFileEvent("Thibaut", new MockFile("0", "File1"));
        ircHandler.shareFileEvent("Thibaut", new MockFile("0", "File1"), Collections.singletonMap("Maxime", Permission.GET));


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