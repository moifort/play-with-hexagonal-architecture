package domain.filemanager.spi;

import domain.filemanager.api.entity.Permission;

import java.util.Map;

public interface FileEventHandler {

    void addFileEvent(String userId, String fileId);

    void deleteFileEvent(String userId, String fileId);

    void shareFileEvent(String userId, String fileId, Map<String, Permission> sharedUsersIdWithPermission);

}
