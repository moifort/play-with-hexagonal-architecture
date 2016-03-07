package domain.filemanager.mock;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.spi.FileEventNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockFileEventNotificationList implements FileEventNotification {
    private final List<MockFileEventNotification> mockFileEventHandlerList = new ArrayList<>();

    @Override
    public void sendNotification(FileEventNotification.Type type, String userId, List<File> files, Map<String, Permission> sharedUsersIdWithPermission) {
        MockFileEventNotification mockFileEventHandler = new MockFileEventNotification();
        mockFileEventHandler.sendNotification(type, userId, files, sharedUsersIdWithPermission);
        mockFileEventHandlerList.add(mockFileEventHandler);
    }

    public List<MockFileEventNotification> getMockFileEventHandlerList() {
        return mockFileEventHandlerList;
    }
}
