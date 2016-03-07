package domain.notificationmanager.mock;

import domain.notificationmanager.spi.ServiceConfigurationRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MockInMemoryServiceConfiguration implements ServiceConfigurationRepository {

    private final Map<SettingKey, Map<String, String>> userServiceConfigurationSettings = new HashMap<>();

    @Override
    public void saveServiceConfiguration(String userId, String serviceId, Map<String, String> serviceConfiguration) {
        userServiceConfigurationSettings.put(new SettingKey(userId, serviceId), serviceConfiguration);
    }

    @Override
    public Map<String, String> getServiceConfiguration(String userId, String serviceId) {
        if (!userServiceConfigurationSettings.containsKey(new SettingKey(userId, serviceId))) return Collections.emptyMap();
        return userServiceConfigurationSettings.get(new SettingKey(userId, serviceId));
    }

    private static class SettingKey {
        private String userId;
        private String serviceId;

        public SettingKey(String userId, String serviceId) {
            this.userId = userId;
            this.serviceId = serviceId;
        }

        public String getUserId() {
            return userId;
        }

        public String getServiceId() {
            return serviceId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SettingKey that = (SettingKey) o;

            if (serviceId != null ? !serviceId.equals(that.serviceId) : that.serviceId != null) return false;
            if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = userId != null ? userId.hashCode() : 0;
            result = 31 * result + (serviceId != null ? serviceId.hashCode() : 0);
            return result;
        }
    }

}
