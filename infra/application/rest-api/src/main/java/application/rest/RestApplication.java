package application.rest;

import application.rest.mapper.FileDTOMapper;
import application.rest.resource.FileManagerResource;
import domain.filemanager.api.FileManagerService;
import domain.filemanager.core.FileManagerServiceImpl;
import domain.filemanager.spi.FileNotification;
import domain.filemanager.spi.FileRepository;
import notification.irc.IrcHandler;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import persistence.inmemory.repository.InMemoryFileNotificationRepository;

public class RestApplication extends Application<RestConfiguration> {

    public static void main(String[] args) throws Exception {
        new RestApplication().run(args);
    }

    @Override
    public String getName() {
        return "rest-api";
    }

    @Override
    public void initialize(Bootstrap<RestConfiguration> bootstrap) {
    }

    @Override
    public void run(RestConfiguration configuration, Environment environment) {
        // Init Domain
        FileRepository fileRepository = new InMemoryFileNotificationRepository();
        FileNotification fileNotification = new IrcHandler("irc.freenode.org","#HewaBot");
        FileManagerService fileManagerService = new FileManagerServiceImpl(fileRepository, fileNotification);

        // REST Dependency injection
        FileDTOMapper fileDTOMapper = FileDTOMapper.INSTANCE;
        FileManagerResource fileManagerResource = new FileManagerResource(fileManagerService, fileDTOMapper);

        // Register controller
        environment.jersey().register(fileManagerResource);
    }
}