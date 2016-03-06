package domain.filemanager.mock;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.spi.FileNotification;

import java.util.List;
import java.util.Map;

public class MockFileNotification implements FileNotification {
    private Type type;
    private String userId;
    private List<File> files;
    private Map<String, Permission> sharedUsersIdWithPermission;

    @Override
    public void sendNotification(Type type, String userId, List<File> files, Map<String, Permission> sharedUsersIdWithPermission) {
        this.type = type;
        this.userId = userId;
        this.files = files;
        this.sharedUsersIdWithPermission = sharedUsersIdWithPermission;
    }

    public Type getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }


    public List<File> getFiles() {
        return files;
    }

    public Map<String, Permission> getSharedUsersIdWithPermission() {
        return sharedUsersIdWithPermission;
    }


}
