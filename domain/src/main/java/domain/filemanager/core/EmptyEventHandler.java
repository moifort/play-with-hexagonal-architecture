package domain.filemanager.core;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.spi.FileEventHandler;

import java.util.List;
import java.util.Map;

public class EmptyEventHandler implements FileEventHandler {

    @Override
    public void addFileEvent(String userId, File file) {
        // Do nothing
    }

    @Override
    public void getFileEvent(String userId, List<File> files) {
        // Do nothing
    }

    @Override
    public void getSharedFileEvent(String userId, List<File> files) {
        // Do nothing
    }

    @Override
    public void deleteFileEvent(String userId, File file) {
        // Do nothing
    }

    @Override
    public void shareFileEvent(String userId, File file, Map<String, Permission> sharedUsersIdWithPermission) {
        // Do nothing
    }

}
