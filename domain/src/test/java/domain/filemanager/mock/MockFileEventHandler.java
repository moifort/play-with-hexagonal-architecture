package domain.filemanager.mock;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.spi.FileEventHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MockFileEventHandler implements FileEventHandler {
    private String methodName;
    private String userId;
    private List<File> files;
    private Map<String, Permission> sharedUsersIdWithPermission;

    @Override
    public void addFileEvent(String userId, File file) {
        this.methodName = "addFileEvent";
        this.userId = userId;
        this.files = Collections.singletonList(file);
    }

    @Override
    public void getFileEvent(String userId, List<File> files) {
        this.methodName = "getFileEvent";
        this.userId = userId;
        this.files = files;
    }

    @Override
    public void deleteFileEvent(String userId, File file) {
        this.methodName = "deleteFileEvent";
        this.userId = userId;
        this.files = Collections.singletonList(file);
    }

    @Override
    public void shareFileEvent(String userId, File file, Map<String, Permission> sharedUsersIdWithPermission) {
        this.methodName = "shareFileEvent";
        this.userId = userId;
        this.files = Collections.singletonList(file);
        this.sharedUsersIdWithPermission = sharedUsersIdWithPermission;
    }

    @Override
    public void getSharedFileEvent(String userId, List<File> files) {
        this.methodName = "getSharedFileEvent";
        this.userId = userId;
        this.files = files;
    }

    public String getMethodName() {
        return methodName;
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
