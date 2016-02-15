package domain.filemanager.api;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;

import java.util.Map;

public interface FileManagerService {

    File getFile(String fileId, String userIdRequesting);

    File addFile(String fileName, byte[] data, String userIdRequesting);

    void deleteFile(String fileId, String userIdRequesting);

    void shareFile(String fileId, String userIdRequesting, Map<String, Permission> usersIdToShareWithPermission);
}
