package domain.notificationcenter.core;

import domain.notificationcenter.api.NotificationServiceConfiguration;

import java.util.Map;

public class NotificationServiceConfigurationImpl implements NotificationServiceConfiguration {
    private final String serviceId;
    private final Map<String, String> settings;

    public NotificationServiceConfigurationImpl(String serviceId, Map<String, String> settings) {
        this.serviceId = serviceId;
        this.settings = settings;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public Map<String, String> getSettings() {
        return settings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationServiceConfigurationImpl that = (NotificationServiceConfigurationImpl) o;

        return !(serviceId != null ? !serviceId.equals(that.serviceId) : that.serviceId != null);

    }

    @Override
    public int hashCode() {
        return serviceId != null ? serviceId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ServiceConfigurationImpl{" +
                "serviceId='" + serviceId + '\'' +
                ", settings=" + settings +
                '}';
    }
}
