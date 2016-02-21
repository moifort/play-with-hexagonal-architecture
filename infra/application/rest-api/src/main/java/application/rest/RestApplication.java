package application.rest;

import application.rest.mapper.FileDTOMapper;
import application.rest.resource.FileManagerResource;
import domain.filemanager.api.FileManagerService;
import domain.filemanager.core.FileManagerServiceImpl;
import domain.filemanager.spi.FileRepository;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import persistence.inmemory.repository.InMemoryFileRepository;

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
        // Init dependencies & business service
        FileRepository fileRepository = new InMemoryFileRepository();
        FileManagerService fileManagerService = new FileManagerServiceImpl(fileRepository);

        FileDTOMapper fileDTOMapper = FileDTOMapper.INSTANCE;

        // Dependency injection
        FileManagerResource fileManagerResource = new FileManagerResource(fileManagerService, fileDTOMapper);

        // Register controller
        environment.jersey().register(fileManagerResource);
    }
}