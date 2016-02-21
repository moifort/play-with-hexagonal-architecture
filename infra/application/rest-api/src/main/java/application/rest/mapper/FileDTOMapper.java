package application.rest.mapper;

import application.rest.dto.FileDTO;
import domain.filemanager.api.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FileDTOMapper {
    FileDTOMapper INSTANCE = Mappers.getMapper(FileDTOMapper.class);

    FileDTO fileToFileDTO(File file);
    List<FileDTO> filesToFilesDTO(List<File> file);
}
