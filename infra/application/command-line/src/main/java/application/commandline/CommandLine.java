package application.commandline;

import domain.filemanager.api.FileManagerService;
import domain.filemanager.api.entity.File;
import domain.filemanager.core.FileManagerServiceImpl;
import domain.filemanager.spi.FileRepository;
import persistence.SQLPersistence;

public class CommandLine {

    public static void main(String[] args) throws Exception {
        //FileRepository fileRepository = new IMFileRepository();
        FileRepository fileRepository = SQLPersistence.get();
        FileManagerService fileManagerService = new FileManagerServiceImpl(fileRepository);

        File file = fileManagerService.addFile("test.txt", "test.txt".getBytes(), "1");
        System.out.println(file);

        File myFile = fileManagerService.getFile(file.getId(), "1");
        System.out.println(myFile);

    }
}
