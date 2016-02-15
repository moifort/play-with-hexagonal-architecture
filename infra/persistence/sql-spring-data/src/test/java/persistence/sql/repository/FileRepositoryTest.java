package persistence.sql.repository;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.sql.PersistenceConfiguration;
import persistence.sql.entity.File;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfiguration.class)
public class FileRepositoryTest {

    @Inject private FileRepository repository;

    private File file;

    @Before
    public void setUp() {
        file = new File();
        file.setName("thouat");
    }

    @Test
    public void findSavedUserById() {

        file = repository.save(file);

        Assertions.assertThat(repository.findOne(file.getId())).isNotNull();
    }
}
