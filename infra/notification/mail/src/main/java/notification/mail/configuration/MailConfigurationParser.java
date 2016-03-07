package notification.mail.configuration;

import domain.notificationcenter.api.NotificationServiceConfiguration;
import domain.notificationcenter.api.exception.ServiceNotificationInternalException;

public class MailConfigurationParser {
    private final String mail;

    public MailConfigurationParser(NotificationServiceConfiguration configuration) {
        if (!MailConfiguration.SERVICE_ID.equals(configuration.getServiceId()))
            throw new ServiceNotificationInternalException("Wrong configuration");
        String channel = configuration.getSettings().get(MailConfiguration.EMAIL_SETTING);
        if (channel == null || channel.isEmpty())
            throw new ServiceNotificationInternalException("Email cannot be null or empty");

        this.mail = channel;
    }

    public String getMail() {
        return mail;
    }
}
