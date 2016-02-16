package persistence.sql.mapper;

import persistence.sql.adapter.FileAdpater;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import persistence.sql.entity.File;

@Mapper(componentModel = "spring")
public interface FileMapper {

    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "data", ignore = true)
    @Mapping(target = "sharedUsersIdWithPermission", ignore = true)
    FileAdpater fileToFileDTO(File file);
}
