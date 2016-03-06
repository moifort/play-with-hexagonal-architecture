package domain.notificationmanager.spi;

import domain.filemanager.spi.FileEventNotification;

public interface FileEventNotificationService extends FileEventNotification {

    String getServiceId();
}
