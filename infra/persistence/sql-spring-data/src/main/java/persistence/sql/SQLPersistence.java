package persistence.sql;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import persistence.sql.adapter.FileRepositoryAdapter;

public final class SQLPersistence {

    private static ApplicationContext applicationContext;

    private SQLPersistence() {}

    public static domain.filemanager.spi.FileRepository get() {
        FileRepositoryAdapter fileRepository = getApplicationContext().getBean(FileRepositoryAdapter.class);
        return fileRepository;
    }

    private static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            applicationContext = new SpringApplication(PersistenceConfiguration.class).run();
        }
        return applicationContext;
    }
}
