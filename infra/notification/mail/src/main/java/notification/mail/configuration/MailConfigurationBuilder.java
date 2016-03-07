package notification.mail.configuration;

import domain.notificationcenter.api.NotificationServiceConfiguration;
import domain.notificationcenter.api.exception.ServiceNotificationInternalException;
import domain.notificationcenter.core.NotificationServiceConfigurationImpl;

import java.util.HashMap;
import java.util.Map;

public class MailConfigurationBuilder {

    private final Map<String, String> settings;

    public MailConfigurationBuilder() {
        this.settings = new HashMap<>();
    }

    public MailConfigurationBuilder setEmail(String channel) {
        if (channel == null || channel.isEmpty())
            throw new ServiceNotificationInternalException("Email cannot be null or empty");
        settings.put(MailConfiguration.EMAIL_SETTING, channel);
        return this;
    }

    public NotificationServiceConfiguration build() {
        return new NotificationServiceConfigurationImpl(MailConfiguration.SERVICE_ID, settings);
    }
}
