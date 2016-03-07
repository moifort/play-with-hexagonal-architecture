package domain.notificationmanager.spi;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.spi.FileEventNotification;

import java.util.List;
import java.util.Map;

public interface NotificationService {

    String getServiceId();

    void sendNotification(Map<String, String> serviceConfiguration,
                          FileEventNotification.Type type,
                          String userId,
                          List<File> files,
                          Map<String, Permission> sharedUsersIdWithPermission);
}
