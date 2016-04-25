package application.rest.resource;

import application.rest.dto.FileDTO;
import application.rest.mapper.FileDTOMapper;
import com.codahale.metrics.annotation.Timed;
import domain.filemanager.api.FileManagerService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
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

    @POST
    @Path("/")
    @Timed
    public Response add(@Valid FileDTO fileDTO) {
        FileDTO fileAdded = fileDTOMapper.fileToFileDTO(fileManagerService.addFile(fileDTO.getName(), null, "Thibaut"));
        return Response.created(UriBuilder.fromResource(FileManagerResource.class)
                .build(fileAdded))
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Timed
    public Response delete(@PathParam("id") Long id) {
        fileManagerService.deleteFile(id.toString(), "Thibaut");
        return Response.ok(UriBuilder.fromResource(FileManagerResource.class)
                .build(id))
                .build();
    }

    @GET
    @Path("/{id}")
    @Timed
    public FileDTO get(@PathParam("id") Long id) {
        return fileDTOMapper.fileToFileDTO(fileManagerService.getFile(id.toString(), "Thibaut"));
    }

    @GET
    @Path("/")
    @Timed
    public List<FileDTO> getAll() {
        return fileDTOMapper.filesToFilesDTO(fileManagerService.getAllFiles("Thibaut"));
    }


}