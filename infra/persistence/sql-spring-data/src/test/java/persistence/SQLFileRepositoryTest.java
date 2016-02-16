package persistence;

import domain.filemanager.api.entity.File;
import domain.filemanager.spi.FileRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import persistence.sql.SQLPersistence;

public class SQLFileRepositoryTest {

    private FileRepository sqlFileRepository;

    @Before
    public void setUp() throws Exception {
        sqlFileRepository =  SQLPersistence.get();
    }

    @Test
    public void testGet() throws Exception {
        File file = sqlFileRepository.addFile("test.txt", "test.txt".getBytes(), "1");
        Assertions.assertThat(sqlFileRepository.findFileById(file.getId())).isNotNull();
    }
}
