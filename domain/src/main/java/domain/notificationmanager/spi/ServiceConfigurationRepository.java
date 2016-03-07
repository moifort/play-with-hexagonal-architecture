package domain.notificationmanager.spi;

import java.util.Map;

public interface ServiceConfigurationRepository {

    void saveServiceConfiguration(String userId, String serviceId, Map<String, String> serviceConfiguration);
    Map<String, String> getServiceConfiguration(String userId, String serviceId);
}
