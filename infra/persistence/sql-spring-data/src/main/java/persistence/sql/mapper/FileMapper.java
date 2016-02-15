package persistence.sql.mapper;

import domain.filemanager.api.entity.impl.FileImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import persistence.sql.entity.File;

@Mapper(componentModel = "spring")
public interface FileMapper {

    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "data", ignore = true)
    @Mapping(target = "sharedUsersIdWithPermission", ignore = true)
    FileImpl fileToDomainFile(File file);
}
