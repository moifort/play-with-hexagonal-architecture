package domain.notificationmanager.mock;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.mock.MockFileEventNotificationList;
import domain.filemanager.spi.FileEventNotification;
import domain.notificationmanager.api.exception.ServiceNotificationInternalException;
import domain.notificationmanager.spi.NotificationService;

import java.util.List;
import java.util.Map;

public class MockEventNotificationServiceTwo extends MockFileEventNotificationList implements NotificationService {
    private static String PSEUDO_SETTING = "pseudo";

    @Override
    public String getServiceId() {
        return "ServiceTwo";
    }

    @Override
    public void sendNotification(Map<String, String> serviceConfiguration, FileEventNotification.Type type, String userId, List<File> files, Map<String, Permission> sharedUsersIdWithPermission) {
        if (isNotConfigured(serviceConfiguration)) throw new ServiceNotificationInternalException("Pseudo cannot be null or empty");
        sendNotification(type, userId, files, sharedUsersIdWithPermission);
    }

    private boolean isNotConfigured(Map<String, String> settings) {
        return !settings.containsKey(PSEUDO_SETTING)
                || settings.get(PSEUDO_SETTING) == null
                || settings.get(PSEUDO_SETTING).isEmpty();
    }
}
