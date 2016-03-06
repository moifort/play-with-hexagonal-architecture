package domain.notificationmanager.api;

import domain.filemanager.spi.FileEventNotification;

import java.util.List;

public interface NotificationManagerService extends FileEventNotification {

    void setUserSettingNotification(String userId, List<String> selectedServicesName, List<Type> notificationTypes, boolean isEnable);
}
