package notification.irc;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.notificationmanager.spi.NotificationService;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IrcHandler implements NotificationService {

    private final MyBot bot;

    public IrcHandler(String ircServer, String channel) {
        this.bot = new MyBot();
        try {
            // Enable debugging output.
            bot.setVerbose(true);

            // Connect to the IRC server.
            bot.connect(ircServer);

            // Join the #pircbot channel.
            bot.joinChannel(channel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServiceId() {
        return "irc";
    }

    public boolean isConnected() {
        return bot.isConnected();
    }

    @Override
    public void sendNotification(Type type, String userId, List<File> files, Map<String, Permission> sharedUsersIdWithPermission) {
        if (type == Type.SHARE_WITH) {
            bot.sendMessage("#HewaBot", userId + " share file: " + displayFiles(files) + " with " + sharedUsersIdWithPermission.toString());
        } else {
            bot.sendMessage("#HewaBot", userId + " " + type.name() + " file(s): " + displayFiles(files));
        }
    }


    private String displayFiles(List<File> files) {
        return join(files.stream().map(this::displayFiles).collect(Collectors.toList()), ", ");
    }

    private String displayFiles(File file) {
        return file.getName();
    }

    private static String join(Collection var0, String var1) {
        StringBuffer var2 = new StringBuffer();

        for(Iterator var3 = var0.iterator(); var3.hasNext(); var2.append((String)var3.next())) {
            if(var2.length() != 0) {
                var2.append(var1);
            }
        }

        return var2.toString();
    }
}