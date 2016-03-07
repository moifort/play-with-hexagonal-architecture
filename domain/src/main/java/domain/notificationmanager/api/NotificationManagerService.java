package domain.notificationmanager.api;

import domain.filemanager.spi.FileEventNotification;

import java.util.List;
import java.util.Map;

public interface NotificationManagerService extends FileEventNotification {

    void setUserNotificationSetting(String userId, List<String> selectedServicesName, List<Type> notificationTypes, boolean isEnable);
    void setUserServiceConfiguration(String userId, String serviceId, Map<String, String> serviceConfiguration);
}
