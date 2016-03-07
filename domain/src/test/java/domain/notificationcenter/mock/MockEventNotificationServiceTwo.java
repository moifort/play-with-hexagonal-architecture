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

public class MockEventNotificationServiceTwo extends MockFileEventNotificationList implements NotificationService {
    private static String PSEUDO_SETTING = "pseudo";

    @Override
    public String getServiceId() {
        return "ServiceTwo";
    }

    @Override
    public void sendNotification(NotificationServiceConfiguration serviceConfiguration, FileEventNotification.Type type, String userId, List<File> files, Map<String, Permission> sharedUsersIdWithPermission) {
        if (isNotConfigured(serviceConfiguration)) throw new ServiceNotificationInternalException("Pseudo cannot be null or empty");
        sendNotification(type, userId, files, sharedUsersIdWithPermission);
    }

    private boolean isNotConfigured(NotificationServiceConfiguration serviceConfiguration) {
        if (!getServiceId().equals(serviceConfiguration.getServiceId())) return false;
        Map<String, String> settings = serviceConfiguration.getSettings();
        return !settings.containsKey(PSEUDO_SETTING)
                || settings.get(PSEUDO_SETTING) == null
                || settings.get(PSEUDO_SETTING).isEmpty();
    }
}
