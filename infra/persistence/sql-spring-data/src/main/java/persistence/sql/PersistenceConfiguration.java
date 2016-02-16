package persistence.sql;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "persistence.sql.repository", entityManagerFactoryRef="emf")
@ComponentScan("persistence.sql")
public class PersistenceConfiguration {
}
