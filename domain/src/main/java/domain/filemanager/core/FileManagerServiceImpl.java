package domain.filemanager.core;

import domain.filemanager.api.FileManagerService;
import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.api.exception.AccessDeniedException;
import domain.filemanager.api.exception.FileNotFoundException;
import domain.filemanager.spi.FileEventHandler;
import domain.filemanager.spi.FileRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FileManagerServiceImpl implements FileManagerService {
    private final FileRepository fileRepository;
    private final FileEventHandler fileEventHandler;
    private final FileAccessService fileAccessService = new FileAccessService();

    public FileManagerServiceImpl(FileRepository fileRepository) {
        this(fileRepository, new EmptyEventHandler());
    }

    public FileManagerServiceImpl(FileRepository fileRepository, FileEventHandler fileEventHandler) {
        this.fileRepository = fileRepository;
        this.fileEventHandler = fileEventHandler;
    }

    @Override
    public File getFile(String fileId, String userIdRequesting) {
        File fileToGet = fileRepository.findFileById(fileId);
        if (fileToGet == null) throw new FileNotFoundException();
        if (fileAccessService.isUserHasNotAccess(userIdRequesting, fileToGet)) throw new AccessDeniedException();
        fileEventHandler.getFileEvent(userIdRequesting, Collections.singletonList(fileToGet));
        return fileToGet;
    }

    @Override
    public List<File> getAllFiles(String userIdRequesting) {
        List<File> allFiles = fileRepository.findFilesByUserId(userIdRequesting);
        fileEventHandler.getFileEvent(userIdRequesting, allFiles);
        return allFiles;
    }

    @Override
    public List<File> getAllSharedFiles(String userIdRequesting) {
        List<File> allSharedFiles =  fileRepository.findFilesBySharedUser(userIdRequesting);
        fileEventHandler.getSharedFileEvent(userIdRequesting, allSharedFiles);
        return allSharedFiles;
    }

    @Override
    public File addFile(String fileName, byte[] data, String userIdRequesting) {
        File addedFile = fileRepository.addFile(fileName, data, userIdRequesting);
        fileEventHandler.addFileEvent(userIdRequesting, addedFile);
        return addedFile;
    }

    @Override
    public void deleteFile(String fileId, String userIdRequesting) {
        File fileToDelete = fileRepository.findFileById(fileId);
        if (fileToDelete == null) throw new FileNotFoundException();
        if (fileAccessService.isUserCanNotDelete(userIdRequesting, fileToDelete)) throw new AccessDeniedException();
        fileRepository.deleteFile(fileId);
        fileEventHandler.deleteFileEvent(userIdRequesting, fileToDelete);
    }

    @Override
    public void shareFile(String fileId, String userIdRequesting, Map<String, Permission> sharedUsersIdWithPermission) {
        File fileToShare = fileRepository.findFileById(fileId);
        if (fileToShare == null) throw new FileNotFoundException();
        if (fileAccessService.isUserCanNotShare(userIdRequesting, fileToShare)) throw new AccessDeniedException();
        fileRepository.shareFile(fileId, sharedUsersIdWithPermission);
        fileEventHandler.shareFileEvent(userIdRequesting, fileToShare, sharedUsersIdWithPermission);
    }
}
