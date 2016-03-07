package domain.notificationcenter.mock;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.mock.MockFileEventNotificationList;
import domain.filemanager.spi.FileEventNotification;
import domain.notificationcenter.api.NotificationServiceConfiguration;
import domain.notificationcenter.api.exception.ServiceNotificationInternalException;
import domain.notificationcenter.spi.NotificationService;

import java.util.List;
import java.util.Map;

public class MockEventNotificationServiceOne extends MockFileEventNotificationList implements NotificationService {

    private static String EMAIL_SETTING = "email";

    @Override
    public String getServiceId() {
        return "ServiceOne";
    }

    @Override
    public void sendNotification(NotificationServiceConfiguration serviceConfiguration, FileEventNotification.Type type, String userId, List<File> files, Map<String, Permission> sharedUsersIdWithPermission) {
        if (isNotConfigured(serviceConfiguration)) throw new ServiceNotificationInternalException("Email cannot be null or empty");
        sendNotification(type, userId, files, sharedUsersIdWithPermission);
    }

    private boolean isNotConfigured(NotificationServiceConfiguration serviceConfiguration) {
        if (!getServiceId().equals(serviceConfiguration.getServiceId())) return true;
        Map<String, String> settings = serviceConfiguration.getSettings();
        return !settings.containsKey(EMAIL_SETTING)
                || settings.get(EMAIL_SETTING) == null
                || settings.get(EMAIL_SETTING).isEmpty();
    }
}
