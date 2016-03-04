package domain.filemanager.core;

import domain.filemanager.api.entity.Permission;
import domain.filemanager.spi.FileEventHandler;

import java.util.Map;

public class EmptyEventHandler implements FileEventHandler {

    @Override
    public void addFileEvent(String userId, String fileName) {
        // Do nothing
    }

    @Override
    public void deleteFileEvent(String userId, String fileName) {
        // Do nothing
    }

    @Override
    public void shareFileEvent(String userId, String fileId, Map<String, Permission> sharedUsersIdWithPermission) {
        // Do nothing
    }

}
