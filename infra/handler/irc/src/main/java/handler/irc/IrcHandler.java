package handler.irc;

import com.sun.deploy.util.StringUtils;
import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.spi.FileEventHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IrcHandler implements FileEventHandler {

    private final MyBot bot;

    public IrcHandler() {
        this.bot = new MyBot();
        try {
            // Enable debugging output.
            bot.setVerbose(true);

            // Connect to the IRC server.
            bot.connect("irc.freenode.org");

            // Join the #pircbot channel.
            bot.joinChannel("#HewaBot");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return bot.isConnected();
    }

    @Override
    public void getFileEvent(String userId, List<File> files) {
        bot.sendMessage("#HewaBot", userId + " get file(s): " + displayFiles(files));
    }

    @Override
    public void getSharedFileEvent(String userId, List<File> files) {
        bot.sendMessage("#HewaBot", userId + " get shared file(s): " + displayFiles(files));
    }

    public void addFileEvent(String userId, File file) {
        bot.sendMessage("#HewaBot", userId + " add file: " + displayFiles(file));
    }

    public void deleteFileEvent(String userId, File file) {
        bot.sendMessage("#HewaBot", userId + " delete file: " + displayFiles(file));
    }

    public void shareFileEvent(String userId, File file, Map<String, Permission> sharedUsersIdWithPermission) {
        bot.sendMessage("#HewaBot", userId + " share file: " + displayFiles(file) + " with " + sharedUsersIdWithPermission.toString());
    }

    private String displayFiles(List<File> files) {
        return StringUtils.join(files.stream().map(this::displayFiles).collect(Collectors.toList()), ", ");
    }

    private String displayFiles(File file) {
        return file.getName();
    }
}