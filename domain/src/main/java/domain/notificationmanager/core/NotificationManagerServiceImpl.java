package domain.notificationmanager.core;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.notificationmanager.api.NotificationManagerService;
import domain.notificationmanager.api.eception.UnknownNotificationServiceException;
import domain.notificationmanager.spi.FileNotificationService;
import domain.notificationmanager.spi.UserSettingNotificationRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NotificationManagerServiceImpl implements NotificationManagerService {
    private final List<FileNotificationService> fileEventHandlerServices;
    private final UserSettingNotificationRepository userSettingNotificationRepository;
    private final List<String> allServicesId;

    public NotificationManagerServiceImpl(FileNotificationService fileEventHandlerServices, UserSettingNotificationRepository userSettingNotificationRepository) {
        this(Collections.singletonList(fileEventHandlerServices), userSettingNotificationRepository);
    }

    public NotificationManagerServiceImpl(List<FileNotificationService> fileEventHandlerServices, UserSettingNotificationRepository userSettingNotificationRepository) {
        this.fileEventHandlerServices = fileEventHandlerServices;
        this.userSettingNotificationRepository = userSettingNotificationRepository;
        this.allServicesId = getServicesId(fileEventHandlerServices);
    }

    @Override
    public void setUserSettingNotification(String userId, List<String> selectedServicesId, List<Type> notificationTypes, boolean isEnable) {
        if (isAtLeastOneServiceIsUnkown(selectedServicesId)) throw new UnknownNotificationServiceException(selectedServicesId.toString());
        List<String> eventTypeNames = notificationTypes.stream().map(Enum::name).collect(Collectors.toList());
        if (selectedServicesId.isEmpty()) selectedServicesId = this.allServicesId;
        userSettingNotificationRepository.saveUserNotificationSetting(userId, selectedServicesId, eventTypeNames, isEnable);
    }

    @Override
    public void sendNotification(Type type, String userId, List<File> files, Map<String, Permission> sharedUsersIdWithPermission) {
        for (FileNotificationService fileEventHandlerService : fileEventHandlerServices) {
            if (isEventIsEnable(userId, type, fileEventHandlerService.getServiceId())) {
                fileEventHandlerService.sendNotification(type, userId, files, sharedUsersIdWithPermission);
            }
        }
    }

    private boolean isAtLeastOneServiceIsUnkown(List<String> servicesId) {
        return !allServicesId.containsAll(servicesId);
    }

    private boolean isEventIsEnable(String userId, Type type, String serviceName) {
        return userSettingNotificationRepository.getUserNotificationSetting(userId, serviceName, type.name());
    }

    private static List<String> getServicesId(List<FileNotificationService> fileEventHandlerServices) {
        return fileEventHandlerServices.stream().map(FileNotificationService::getServiceId).collect(Collectors.toList());
    }
}
