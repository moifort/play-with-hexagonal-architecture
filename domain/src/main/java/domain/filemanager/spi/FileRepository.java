package domain.filemanager.spi;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;

import java.util.List;
import java.util.Map;

public interface FileRepository {

    File findFileById(String fileId);

    List<File> findFilesByUserId(String ownerId);

    File addFile(String fileName, byte[] data, String ownerId);

    void shareFile(String fileId, Map<String, Permission> usersIdToShareWithPermission);

    void deleteFile(String fileId);

    List<File> findFilesBySharedUser(String userId);
}
