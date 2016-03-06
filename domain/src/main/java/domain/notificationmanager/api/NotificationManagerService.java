package domain.notificationmanager.api;

import domain.filemanager.spi.FileNotification;

import java.util.List;

public interface NotificationManagerService extends FileNotification {

    void setUserSettingNotification(String userId, List<String> selectedServicesName, List<Type> notificationTypes, boolean isEnable);
}
