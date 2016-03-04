package domain.filemanager.mock;

import domain.filemanager.api.entity.Permission;
import domain.filemanager.spi.FileEventHandler;

import java.util.Map;

public class MockFileEventHandler implements FileEventHandler {
    private String userId;
    private String fileId;
    private Map<String, Permission> sharedUsersIdWithPermission;

    @Override
    public void addFileEvent(String userId, String fileId) {
        this.userId = userId;
        this.fileId = fileId;
    }

    @Override
    public void deleteFileEvent(String userId, String fileId) {
        this.userId = userId;
        this.fileId = fileId;
    }

    @Override
    public void shareFileEvent(String userId, String fileId, Map<String, Permission> sharedUsersIdWithPermission) {
        this.userId = userId;
        this.fileId = fileId;
        this.sharedUsersIdWithPermission = sharedUsersIdWithPermission;
    }

    public String getUserId() {
        return userId;
    }

    public String getFileId() {
        return fileId;
    }

    public Map<String, Permission> getSharedUsersIdWithPermission() {
        return sharedUsersIdWithPermission;
    }
}
