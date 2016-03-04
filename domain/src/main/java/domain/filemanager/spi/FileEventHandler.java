package domain.filemanager.spi;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;

import java.util.List;
import java.util.Map;

public interface FileEventHandler {

    void addFileEvent(String userId, File file);

    void getFileEvent(String userId, List<File> files);

    void getSharedFileEvent(String userId, List<File> files);

    void deleteFileEvent(String userId, File file);

    void shareFileEvent(String userId, File file, Map<String, Permission> sharedUsersIdWithPermission);

}
