package application.commandline;

import domain.filemanager.api.FileManagerService;
import domain.filemanager.api.entity.File;
import domain.filemanager.core.FileManagerServiceImpl;
import domain.filemanager.spi.FileRepository;
import persistence.sql.SQLPersistence;

public class Application {

    public static void main(String[] args) throws Exception {
        //FileRepository fileRepository = new InMemoryFileRepository();
        FileRepository fileRepository = SQLPersistence.get();
        FileManagerService fileManagerService = new FileManagerServiceImpl(fileRepository);

        File file = fileManagerService.addFile("test.txt", "test.txt".getBytes(), "1");
        System.out.println("Add new file   -> " + file);

        File myFile = fileManagerService.getFile(file.getId(), "1");
        System.out.println("Get file id: " + file.getId() + " -> " + myFile);

    }
}
