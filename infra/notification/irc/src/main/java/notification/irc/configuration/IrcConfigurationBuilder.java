package notification.irc.configuration;

import domain.notificationcenter.api.NotificationServiceConfiguration;
import domain.notificationcenter.api.exception.ServiceNotificationInternalException;
import domain.notificationcenter.core.NotificationServiceConfigurationImpl;

import java.util.HashMap;
import java.util.Map;

public class IrcConfigurationBuilder {

    private final Map<String, String> settings;

    public IrcConfigurationBuilder() {
        this.settings = new HashMap<>();
    }

    public IrcConfigurationBuilder setChannel(String channel) {
        if (channel == null || channel.isEmpty())
            throw new ServiceNotificationInternalException("Channel cannot be null or empty");
        settings.put(IrcConfiguration.CHANNEL_SETTING, channel);
        return this;
    }

    public NotificationServiceConfiguration build() {
        return new NotificationServiceConfigurationImpl(IrcConfiguration.SERVICE_ID, settings);
    }
}
