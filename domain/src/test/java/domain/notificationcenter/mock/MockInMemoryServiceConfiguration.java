package domain.notificationcenter.mock;

import domain.notificationcenter.spi.ServiceConfigurationRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MockInMemoryServiceConfiguration implements ServiceConfigurationRepository {

    private final Map<ConfigurationSettingKey, Map<String, String>> userServiceConfigurationSettings = new HashMap<>();

    @Override
    public void saveServiceConfiguration(String userId, String serviceId, Map<String, String> serviceConfiguration) {
        userServiceConfigurationSettings.put(new ConfigurationSettingKey(userId, serviceId), serviceConfiguration);
    }

    @Override
    public Map<String, String> getServiceConfiguration(String userId, String serviceId) {
        if (!userServiceConfigurationSettings.containsKey(new ConfigurationSettingKey(userId, serviceId))) return Collections.emptyMap();
        return userServiceConfigurationSettings.get(new ConfigurationSettingKey(userId, serviceId));
    }

    private static class ConfigurationSettingKey {
        private String userId;
        private String serviceId;

        public ConfigurationSettingKey(String userId, String serviceId) {
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

            ConfigurationSettingKey that = (ConfigurationSettingKey) o;

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
