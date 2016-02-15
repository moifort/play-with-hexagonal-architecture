package persistence.sql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import persistence.sql.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
