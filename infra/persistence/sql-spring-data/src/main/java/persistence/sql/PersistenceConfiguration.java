package persistence.sql;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("persistence.sql.repository")
@ComponentScan("persistence.sql")
public class PersistenceConfiguration {
}
