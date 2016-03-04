package domain.filemanager;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import domain.filemanager.api.FileManagerService;
import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.core.FileManagerServiceImpl;
import domain.filemanager.mock.MockFile;
import domain.filemanager.mock.MockFileEventHandler;
import domain.filemanager.mock.MockInMemoryFile;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class FileManagerStepDefs {

    private MockInMemoryFile mockFileRepository;
    private MockFileEventHandler mockFileEventHandler;
    private FileManagerService fileManagerService;

    @Before
    public void setUp() throws Exception {
        mockFileRepository = new MockInMemoryFile();
        mockFileEventHandler = new MockFileEventHandler();
        fileManagerService = new FileManagerServiceImpl(mockFileRepository, mockFileEventHandler);
    }

    @Given("^'(.*)' add '(.*)' file$")
    public void user_add_a_file(String userId, String fileName) throws Throwable {
        // Exercise
        File fileIsSaved = fileManagerService.addFile(fileName, fileName.getBytes(), userId);

        // Verify
        assertThat(fileIsSaved).isNotNull();
    }

    @When("^'(.*)' get all files$")
    public void user_get_all_files(String userId) throws Throwable {
        // Exercise
        fileManagerService.getAllFiles(userId);
    }

    @When("^'(.*)' get all shared files$")
    public void user_get_all_shared_files(String userId) throws Throwable {
        // Exercise
        fileManagerService.getAllSharedFiles(userId);
    }

    @Then("^'(.*)' can get '(.*)' file$")
    public void user_get_a_file(List<String> usersId, List<String> fileNames) throws Throwable {
        if (fileNames.size() == 1) usersId.forEach(userId -> user_get_a_file(fileNames.get(0), userId));
        else usersId.forEach(userId -> user_get_all_files(fileNames, userId));

    }

    @When("^'(.*)' delete '(.*)' file$")
    public void user_delete_a_file(String userId, String fileName) throws Throwable {
        // Setup
        String fileId = getFileId(fileName);

        // Exercise
        try {
            fileManagerService.deleteFile(fileId, userId);
        } catch (Exception e) {

        }

    }

    @Then("^'(.*)' can delete '(.*)' file$")
    public void user_can_delete_a_file(String userId, String fileName) throws Throwable {
        // Setup
        String fileId = getFileId(fileName);
        Exception exception = null;

        // Exercise
        try {
            fileManagerService.deleteFile(fileId, userId);
        } catch (Exception e) {
            exception = e;
        }

        // Verify
        assertThat(exception).isNull();
    }

    @When("^'(.*)' do nothing$")
    public void do_nothing(String userId) {
        // Do nothing
    }

    @Then("^'(.*)' cannot get '(.*)' file$")
    public void user_cannot_get_file(String userId, String fileName) throws Throwable {
        // Setup
        String fileId = getFileId(fileName);
        Exception exception = null;

        // Exercise
        try {
            fileManagerService.getFile(fileId, userId);
        } catch (Exception e) {
            exception = e;
        }

        // Verify
        assertThat(exception).isNotNull();
    }

    @Then("^'(.*)' cannot delete '(.*)' file$")
    public void user_cannot_delete_file(List<String> usersId, String fileName) {
        usersId.forEach(userId -> {
            // Setup
            String fileId = getFileId(fileName);
            Exception exception = null;

            // Exercise
            try {
                fileManagerService.deleteFile(fileId, userId);
            } catch (Exception e) {
                exception = e;
            }

            // Verify
            assertThat(exception).isNotNull();
        });
    }

    @And("^'(.*)' file doesn't exist")
    public void file_not_exist(String fileName) {
        // Exercise
        Optional<MockFile> file = mockFileRepository.findByName(fileName);

        // Verify
        assertThat(file).isEmpty();
    }


    @And("^'(.*)' file exist")
    public void file_exist(String fileName) {
        // Exercise
        Optional<MockFile> file = mockFileRepository.findByName(fileName);

        // Verify
        assertThat(file).isPresent();
    }

    @When("^'(.*)' share '(.*)' file to '(.*)' with '(.*)' permission$")
    public void user_shares_file_to_users_with_permission(String userId, String fileName, List<String> usersIdToShare, String permission) {
        // Setup
        String fileId = getFileId(fileName);
        Map<String, Permission> usersIdToShareWithPermission = getUsersIdToShareWithPermission(usersIdToShare, permission);

        // Exercise
        try {
            fileManagerService.shareFile(fileId, userId, usersIdToShareWithPermission);
        } catch (Exception e) {}
    }

    @Then("^'(.*)' can get all shared files: '(.*)'$")
    public void user_can_get_all_shared_file(String userId, List<String> fileNames) {
        // Exercise
        List<File> sharedFiles = fileManagerService.getAllSharedFiles(userId);

        // Verify
        assertThat(sharedFiles).hasSize(fileNames.size());
        sharedFiles.stream()
                .map(File::getName)
                .forEach(fileName -> assertThat(fileName).isIn(fileNames));
    }

    @Then("^'(.*)' file is shared to '(.*)' with '(.*)' permission$")
    public void file_is_shared_to_users(String fileName, List<String> usersId, String permission) throws Throwable {
        // Exercise
        Optional<MockFile> file = mockFileRepository.findByName(fileName);

        // Verify
        assertThat(file).isPresent();
        assertThat(file.get().getSharedUsersIdWithPermission().keySet()).containsAll(usersId);
        assertThat(file.get().getSharedUsersIdWithPermission().values()).contains(Permission.valueOf(permission));
    }

    @Then("^'(.*)' file is not shared to '(.*)'$")
    public void file_is_not_shared_to_users(String fileName, List<String> usersId) throws Throwable {
        // Exercise
        Optional<MockFile> file = mockFileRepository.findByName(fileName);

        // Verify
        assertThat(file).isPresent();
        assertThat(file.get().getSharedUsersIdWithPermission().keySet()).doesNotContainAnyElementsOf(usersId);
    }


    @Then("^'(.*)' try to get '(.*)' file, '(.*)' is throw$")
    public void user_try_to_get_a_file_throw_exception(List<String> usersId, String fileName, String throwClassName) throws Throwable {
        usersId.forEach(userId -> {
            // Setup
            Exception exception = null;

            // Exercise
            try {
                user_get_a_file(fileName, userId);
            } catch (Exception e) {
                exception = e;
            }

            // Verify
            assertThat(exception).isNotNull();
            assertThat(exception.getClass().getSimpleName()).isEqualTo(throwClassName);
        });

    }

    @Then("^'(.*)' try to delete '(.*)' file, '(.*)' is throw$")
    public void user_try_to_delete_a_file_throw_exception(String userId, String fileName, String throwClassName) throws Throwable {
        // Setup
        String fileId = getFileId(fileName);
        Exception exception = null;

        // Exercise
        try {
            fileManagerService.deleteFile(fileId, userId);
        } catch (Exception e) {
            exception = e;
        }

        // Verify
        assertThat(exception).isNotNull();
        assertThat(exception.getClass().getSimpleName()).isEqualTo(throwClassName);
    }

    @Then("^'(.*)' try to share '(.*)' file to '(.*)' with '(.*)' permission, '(.*)' is throw$")
    public void user_try_to_shares_file_to_users_with_permission(String userId, String fileName, List<String> usersIdToShare, String permission, String throwClassName) {
        // Setup
        String fileId = getFileId(fileName);
        Map<String, Permission> usersIdToShareWithPermission = getUsersIdToShareWithPermission(usersIdToShare, permission);
        Exception exception = null;

        // Exercise
        try {
            fileManagerService.shareFile(fileId, userId, usersIdToShareWithPermission);
        } catch (Exception e) {
            exception = e;
        }

        // Verify
        assertThat(exception).isNotNull();
        assertThat(exception.getClass().getSimpleName()).isEqualTo(throwClassName);
    }


    /*********************************
     * HOOK
     *********************************/
    @Then("^'(.*)' is triggered with '(.*)' user name and '(.*)' files id$")
    public void methodEvent_is_triggered_with_owner_and_file(String methodName, String userId, List<String> fileNames) {
        assertThat(mockFileEventHandler.getMethodName()).isEqualTo(methodName);
        assertThat(mockFileEventHandler.getUserId()).isEqualTo(userId);
        assertThat(mockFileEventHandler.getFiles()).hasSize(fileNames.size());
        if (!methodName.equals("deleteFileEvent")){
            assertThat(mockFileEventHandler.getFiles()).isEqualTo(getFiles(fileNames));
        }
    }

    @Then("^'(.*)' is triggered with '(.*)' owner and '(.*)' file and '(.*)' shared user and '(.*)' permission$")
    public void share_a_file_event_is_triggered_with_owner_and_file_and_shared_users_and_permission(String methodName, String userId, String fileName, List<String> sharedUsers, String permission) {
        assertThat(mockFileEventHandler.getMethodName()).isEqualTo(methodName);
        assertThat(mockFileEventHandler.getUserId()).isEqualTo(userId);
        assertThat(mockFileEventHandler.getFiles()).isEqualTo(Collections.singletonList(getFile(fileName)));
        assertThat(mockFileEventHandler.getSharedUsersIdWithPermission()).isEqualTo(getUsersIdToShareWithPermission(sharedUsers, permission));
    }

    private void user_get_a_file(String fileName, String userId) {
        // Setup
        String fileId = getFileId(fileName);

        // Exercise
        File file = fileManagerService.getFile(fileId, userId);

        // Verify
        assertThat(file.getName()).isEqualTo(fileName);
    }

    private void user_get_all_files(List<String> fileNames, String userId) {
        // Exercise
        List<File> files = fileManagerService.getAllFiles(userId);

        // Verify
        assertThat(files).hasSize(fileNames.size());
        files.stream()
                .map(File::getName)
                .forEach(fileName ->  assertThat(fileName).isIn(fileNames));
    }

    private List<File> getFiles(List<String> filenames) {
        return filenames.stream()
                .map(this::getFile)
                .collect(Collectors.toList());
    }

    private File getFile(String filename) {
        return mockFileRepository.findByName(filename).get();
    }


    private String getFileId(String fileName) {
        return mockFileRepository.findByName(fileName)
            .map(File::getId)
            .orElse(null);
    }

    private Map<String, Permission> getUsersIdToShareWithPermission(List<String> usersIdToShare, String permission) {
        return usersIdToShare.stream()
            .collect(Collectors.toMap(Function.identity(), s -> Permission.valueOf(permission)));
    }
}
