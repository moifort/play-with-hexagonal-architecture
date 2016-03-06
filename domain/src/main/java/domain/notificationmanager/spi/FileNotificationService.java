package domain.notificationmanager.spi;

import domain.filemanager.spi.FileNotification;

public interface FileNotificationService extends FileNotification {

    String getServiceId();
}
