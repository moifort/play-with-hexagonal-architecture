package domain.filemanager.spi;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;

import java.util.List;
import java.util.Map;

public interface FileEventNotification {
    void sendNotification(Type type,
                          String userId,
                          List<File> files,
                          Map<String, Permission> sharedUsersIdWithPermission);

    enum Type {
        GET,
        GET_SHARE,
        ADD,
        DELETE,
        SHARE_WITH
    }
}
