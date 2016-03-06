package domain.notificationmanager.spi;

import java.util.List;

public interface UserSettingNotificationRepository {

    void saveUserNotificationSetting(String userId, List<String> servicesId, List<String> notificationTypes, boolean isEnable);

    boolean getUserNotificationSetting(String userId, String serviceId, String notificationType);
}
