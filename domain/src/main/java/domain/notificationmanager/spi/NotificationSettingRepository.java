package domain.notificationmanager.spi;

import java.util.List;

public interface NotificationSettingRepository {

    void saveNotificationSetting(String userId, List<String> servicesId, List<String> notificationTypes, boolean isEnable);
    boolean getNotificationSetting(String userId, String serviceId, String notificationType);
}
