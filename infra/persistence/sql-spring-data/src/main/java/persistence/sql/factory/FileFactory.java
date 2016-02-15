package persistence.sql.factory;

import org.springframework.stereotype.Service;
import persistence.sql.entity.File;
import persistence.sql.entity.User;
import persistence.sql.repository.UserRepository;

import javax.inject.Inject;

@Service
public class FileFactory {

    @Inject private UserRepository userRepository;

    public File createFile(String fileName, byte[] data, String ownerId) {
        User user = userRepository.exists(Long.valueOf(ownerId))
                ? userRepository.findOne(Long.valueOf(ownerId))
                : userRepository.save(new User());

        File file = new File();
        file.setName(fileName);
        file.setOwner(user);
        return file;
    }
}
