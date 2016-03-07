package domain.notificationcenter.api;

import domain.filemanager.spi.FileEventNotification;

import java.util.List;

public interface NotificationCenterService extends FileEventNotification {

    void setUserNotificationSetting(String userId, List<String> selectedServicesName, List<Type> notificationTypes, boolean isEnable);
    void setUserServiceConfiguration(String userId, NotificationServiceConfiguration notificationServiceConfiguration);
}
