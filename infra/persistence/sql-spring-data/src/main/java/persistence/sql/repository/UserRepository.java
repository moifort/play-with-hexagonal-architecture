package persistence.sql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import persistence.sql.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
