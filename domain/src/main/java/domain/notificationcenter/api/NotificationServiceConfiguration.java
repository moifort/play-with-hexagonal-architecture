package domain.notificationcenter.api;

import java.util.Map;

public interface NotificationServiceConfiguration {
    String getServiceId();
    Map<String, String> getSettings();
}
