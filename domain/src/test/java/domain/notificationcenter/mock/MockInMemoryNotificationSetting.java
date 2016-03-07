package domain.notificationcenter.mock;

import domain.notificationcenter.spi.NotificationSettingRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockInMemoryNotificationSetting implements NotificationSettingRepository {

    private final Map<SettingKey, Boolean> userEventSettings = new HashMap<>();

    @Override
    public void saveNotificationSetting(String userId, List<String> servicesId, List<String> notificationTypes, boolean isEnable) {
        for (String serviceId : servicesId) {
            for (String notificationType : notificationTypes) {
                saveUserNotificationSetting(userId, serviceId, notificationType, isEnable);
            }
        }
    }

    private void saveUserNotificationSetting(String userId, String serviceId, String notificationType, boolean isEnable) {
        SettingKey key = new SettingKey(userId, serviceId, notificationType);
        userEventSettings.put(key, isEnable);
    }

    @Override
    public boolean getNotificationSetting(String userId, String serviceId, String notificationType) {
        SettingKey key = new SettingKey(userId, serviceId, notificationType);
        if (!userEventSettings.containsKey(key)) return false;
        return userEventSettings.get(key);
    }

    private static class SettingKey {
        private String userId;
        private String serviceId;
        private String key;

        public SettingKey(String userId, String serviceId, String key) {
            this.userId = userId;
            this.serviceId = serviceId;
            this.key = key;
        }

        public String getUserId() {
            return userId;
        }

        public String getServiceId() {
            return serviceId;
        }

        public String getKey() {
            return key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SettingKey that = (SettingKey) o;

            if (key != null ? !key.equals(that.key) : that.key != null)
                return false;
            if (serviceId != null ? !serviceId.equals(that.serviceId) : that.serviceId != null) return false;
            if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = userId != null ? userId.hashCode() : 0;
            result = 31 * result + (serviceId != null ? serviceId.hashCode() : 0);
            result = 31 * result + (key != null ? key.hashCode() : 0);
            return result;
        }
    }

}
