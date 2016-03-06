package domain.filemanager.core;

import domain.filemanager.api.FileManagerService;
import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.api.exception.AccessDeniedException;
import domain.filemanager.api.exception.FileNotFoundException;
import domain.filemanager.spi.FileEventNotification;
import domain.filemanager.spi.FileRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FileManagerServiceImpl implements FileManagerService {
    private final FileRepository fileRepository;
    private final FileEventNotification fileEventNotification;
    private final FileAccessService fileAccessService = new FileAccessService();

    public FileManagerServiceImpl(FileRepository fileRepository) {
        this(fileRepository, new EmptyFileEventNotification());
    }

    public FileManagerServiceImpl(FileRepository fileRepository, FileEventNotification fileEventNotification) {
        this.fileRepository = fileRepository;
        this.fileEventNotification = fileEventNotification;
    }

    @Override
    public File getFile(String fileId, String userIdRequesting) {
        File fileToGet = fileRepository.findFileById(fileId);
        if (fileToGet == null) throw new FileNotFoundException();
        if (fileAccessService.isUserHasNotAccess(userIdRequesting, fileToGet)) throw new AccessDeniedException();
        fileEventNotification.sendNotification(
                FileEventNotification.Type.GET,
                userIdRequesting,
                Collections.singletonList(fileToGet),
                Collections.emptyMap());
        return fileToGet;
    }

    @Override
    public List<File> getAllFiles(String userIdRequesting) {
        List<File> allFiles = fileRepository.findFilesByUserId(userIdRequesting);
        fileEventNotification.sendNotification(
                FileEventNotification.Type.GET,
                userIdRequesting,
                allFiles,
                Collections.emptyMap());
        return allFiles;
    }

    @Override
    public List<File> getAllSharedFiles(String userIdRequesting) {
        List<File> allSharedFiles =  fileRepository.findFilesBySharedUser(userIdRequesting);
        fileEventNotification.sendNotification(
                FileEventNotification.Type.GET_SHARE,
                userIdRequesting,
                allSharedFiles,
                Collections.emptyMap());
        return allSharedFiles;
    }

    @Override
    public File addFile(String fileName, byte[] data, String userIdRequesting) {
        File addedFile = fileRepository.addFile(fileName, data, userIdRequesting);
        fileEventNotification.sendNotification(
                FileEventNotification.Type.ADD,
                userIdRequesting,
                Collections.singletonList(addedFile),
                Collections.emptyMap());
        return addedFile;
    }

    @Override
    public void deleteFile(String fileId, String userIdRequesting) {
        File fileToDelete = fileRepository.findFileById(fileId);
        if (fileToDelete == null) throw new FileNotFoundException();
        if (fileAccessService.isUserCanNotDelete(userIdRequesting, fileToDelete)) throw new AccessDeniedException();
        fileRepository.deleteFile(fileId);
        fileEventNotification.sendNotification(
                FileEventNotification.Type.DELETE,
                userIdRequesting,
                Collections.singletonList(fileToDelete),
                Collections.emptyMap());
    }

    @Override
    public void shareFile(String fileId, String userIdRequesting, Map<String, Permission> sharedUsersIdWithPermission) {
        File fileToShare = fileRepository.findFileById(fileId);
        if (fileToShare == null) throw new FileNotFoundException();
        if (fileAccessService.isUserCanNotShare(userIdRequesting, fileToShare)) throw new AccessDeniedException();
        fileRepository.shareFile(fileId, sharedUsersIdWithPermission);
        fileEventNotification.sendNotification(
                FileEventNotification.Type.SHARE_WITH,
                userIdRequesting,
                Collections.singletonList(fileToShare),
                sharedUsersIdWithPermission);
    }
}
