package persistence;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import persistence.sql.PersistenceConfiguration;
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
            applicationContext = new AnnotationConfigApplicationContext(PersistenceConfiguration.class);
        }
        return applicationContext;
    }
}
