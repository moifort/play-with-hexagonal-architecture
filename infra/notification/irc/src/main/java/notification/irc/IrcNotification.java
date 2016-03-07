package notification.irc;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.spi.FileEventNotification;
import domain.notificationcenter.api.NotificationServiceConfiguration;
import domain.notificationcenter.spi.NotificationService;
import notification.irc.configuration.IrcConfiguration;
import notification.irc.configuration.IrcConfigurationParser;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IrcNotification implements NotificationService {
    private final MyBot bot;

    public IrcNotification(String ircServer) {
        this.bot = new MyBot();
        try {
            // Enable debugging output.
            bot.setVerbose(true);

            // Connect to the IRC server.
            bot.connect(ircServer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServiceId() {
        return IrcConfiguration.SERVICE_ID;
    }

    public boolean isConnected() {
        return bot.isConnected();
    }

    @Override
    public void sendNotification(NotificationServiceConfiguration serviceConfiguration, FileEventNotification.Type type, String userId, List<File> files, Map<String, Permission> sharedUsersIdWithPermission) {
        IrcConfigurationParser configurationParser = new IrcConfigurationParser(serviceConfiguration);
        String channel = configurationParser.getChannel();

        bot.joinChannel(channel);
        if (type == FileEventNotification.Type.SHARE_WITH) {
            bot.sendMessage(channel, userId + " share file: " + displayFiles(files) + " with " + sharedUsersIdWithPermission.toString());
        } else {
            bot.sendMessage(channel, userId + " " + type.name() + " file(s): " + displayFiles(files));
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

        for (Iterator var3 = var0.iterator(); var3.hasNext(); var2.append((String) var3.next())) {
            if (var2.length() != 0) {
                var2.append(var1);
            }
        }

        return var2.toString();
    }
}