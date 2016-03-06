package domain.filemanager.core;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.spi.FileEventNotification;

import java.util.List;
import java.util.Map;

public class EmptyFileEventNotification implements FileEventNotification {

    @Override
    public void sendNotification(Type type, String userId, List<File> files, Map<String, Permission> sharedUsersIdWithPermission) {
        // Do Nothing
    }
}
