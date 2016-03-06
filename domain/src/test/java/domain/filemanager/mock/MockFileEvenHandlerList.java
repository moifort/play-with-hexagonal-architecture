package domain.filemanager.mock;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.spi.FileNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockFileEvenHandlerList implements FileNotification {
    private final List<MockFileNotification> mockFileEventHandlerList = new ArrayList<>();

    @Override
    public void sendNotification(Type type, String userId, List<File> files, Map<String, Permission> sharedUsersIdWithPermission) {
        MockFileNotification mockFileEventHandler = new MockFileNotification();
        mockFileEventHandler.sendNotification(type, userId, files, sharedUsersIdWithPermission);
        mockFileEventHandlerList.add(mockFileEventHandler);
    }

    public List<MockFileNotification> getMockFileEventHandlerList() {
        return mockFileEventHandlerList;
    }
}
