package domain.notificationmanager.core;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.notificationmanager.api.NotificationManagerService;
import domain.notificationmanager.api.exception.UnknownNotificationServiceException;
import domain.notificationmanager.spi.NotificationService;
import domain.notificationmanager.spi.NotificationSettingRepository;
import domain.notificationmanager.spi.ServiceConfigurationRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NotificationManagerServiceImpl implements NotificationManagerService {
    private final List<NotificationService> fileEventHandlerServices;
    private final NotificationSettingRepository notificationSettingRepository;
    private final ServiceConfigurationRepository serviceConfigurationRepository;
    private final List<String> registeredServicesId;

    public NotificationManagerServiceImpl(List<NotificationService> fileEventHandlerServices,
                                          NotificationSettingRepository notificationSettingRepository,
                                          ServiceConfigurationRepository serviceConfigurationRepository) {
        this.fileEventHandlerServices = fileEventHandlerServices;
        this.notificationSettingRepository = notificationSettingRepository;
        this.serviceConfigurationRepository = serviceConfigurationRepository;
        this.registeredServicesId = getRegisteredServicesId(fileEventHandlerServices);
    }

    @Override
    public void setUserNotificationSetting(String userId, List<String> selectedServicesId, List<Type> notificationTypes, boolean isEnable) {
        if (atLeastOneServiceIsUnknown(selectedServicesId)) throw new UnknownNotificationServiceException(selectedServicesId.toString());
        List<String> eventTypeNames = notificationTypes.stream().map(Enum::name).collect(Collectors.toList());
        if (selectedServicesId.isEmpty()) selectedServicesId = this.registeredServicesId;
        notificationSettingRepository.saveNotificationSetting(userId, selectedServicesId, eventTypeNames, isEnable);
    }

    @Override
    public void setUserServiceConfiguration(String userId, String serviceId, Map<String, String> serviceConfiguration) {
        serviceConfigurationRepository.saveServiceConfiguration(userId, serviceId, serviceConfiguration);
    }

    @Override
    public void sendNotification(Type type, String userId, List<File> files, Map<String, Permission> sharedUsersIdWithPermission) {
        for (NotificationService fileEventHandlerService : fileEventHandlerServices) {
            String serviceId = fileEventHandlerService.getServiceId();
            Map<String, String> configurationSettings = serviceConfigurationRepository.getServiceConfiguration(userId, serviceId);
            if (notificationIsEnable(userId, type, serviceId)) {
                fileEventHandlerService.sendNotification(configurationSettings, type, userId, files, sharedUsersIdWithPermission);
            }
        }
    }

    private boolean atLeastOneServiceIsUnknown(List<String> servicesId) {
        return !registeredServicesId.containsAll(servicesId);
    }

    private boolean notificationIsEnable(String userId, Type type, String serviceId) {
        return notificationSettingRepository.getNotificationSetting(userId, serviceId, type.name());
    }

    private static List<String> getRegisteredServicesId(List<NotificationService> notificationServices) {
        return notificationServices.stream().map(NotificationService::getServiceId).collect(Collectors.toList());
    }
}
