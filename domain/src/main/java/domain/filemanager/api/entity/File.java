package domain.filemanager.api.entity;

import java.util.Map;

public interface File {

    String getId();

    String getName();

    byte[] getData();

    String getOwnerId();

    Map<String, Permission> getSharedUsersIdWithPermission();
}
