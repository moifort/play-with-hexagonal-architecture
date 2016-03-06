package application.rest.resource;

import application.rest.dto.FileDTO;
import application.rest.mapper.FileDTOMapper;
import com.codahale.metrics.annotation.Timed;
import domain.filemanager.api.FileManagerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/file")
@Produces(MediaType.APPLICATION_JSON)
public class FileManagerResource {

    private final FileManagerService fileManagerService;
    private final FileDTOMapper fileDTOMapper;

    public FileManagerResource(FileManagerService fileManagerService, FileDTOMapper fileDTOMapper) {
        this.fileManagerService = fileManagerService;
        this.fileDTOMapper = fileDTOMapper;
    }

    @GET
    @Path("add")
    @Timed
    public FileDTO addFile(@QueryParam("name") String name) {
        return fileDTOMapper.fileToFileDTO(fileManagerService.addFile(name, null, "Thibaut"));
    }

    @GET
    @Path("delete/{id}")
    @Timed
    public void deleteFile(@PathParam("id") Long id) {
        fileManagerService.deleteFile(id.toString(), "Thibaut");
    }

    @GET
    @Path("/{id}")
    @Timed
    public FileDTO getFile(@PathParam("id") Long id) {
        return fileDTOMapper.fileToFileDTO(fileManagerService.getFile(id.toString(), "Thibaut"));
    }

    @GET
    @Path("/")
    @Timed
    public List<FileDTO> getAllFiles() {
        return fileDTOMapper.filesToFilesDTO(fileManagerService.getAllFiles("Thibaut"));
    }


}