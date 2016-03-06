package domain.filemanager.core;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.spi.FileNotification;

import java.util.List;
import java.util.Map;

public class EmptyEventHandler implements FileNotification {

    @Override
    public void sendNotification(Type type, String userId, List<File> files, Map<String, Permission> sharedUsersIdWithPermission) {
        // Do Nothing
    }
}
