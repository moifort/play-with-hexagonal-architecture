package notification.irc.configuration;

import domain.notificationcenter.api.NotificationServiceConfiguration;
import domain.notificationcenter.api.exception.ServiceNotificationInternalException;

public class IrcConfigurationParser {
    private final String channel;

    public IrcConfigurationParser(NotificationServiceConfiguration configuration) {
        if (!IrcConfiguration.SERVICE_ID.equals(configuration.getServiceId()))
            throw new ServiceNotificationInternalException("Wrong configuration");
        String channel = configuration.getSettings().get(IrcConfiguration.CHANNEL_SETTING);
        if (channel == null || channel.isEmpty())
            throw new ServiceNotificationInternalException("Channel cannot be null or empty");

        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }
}
