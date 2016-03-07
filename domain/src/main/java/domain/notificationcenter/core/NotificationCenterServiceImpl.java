package domain.notificationcenter.core;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.notificationcenter.api.NotificationServiceConfiguration;
import domain.notificationcenter.spi.NotificationService;
import domain.notificationcenter.api.NotificationCenterService;
import domain.notificationcenter.api.exception.UnknownNotificationServiceException;
import domain.notificationcenter.spi.NotificationSettingRepository;
import domain.notificationcenter.spi.ServiceConfigurationRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NotificationCenterServiceImpl implements NotificationCenterService {
    private final List<NotificationService> fileEventHandlerServices;
    private final NotificationSettingRepository notificationSettingRepository;
    private final ServiceConfigurationRepository serviceConfigurationRepository;
    private final List<String> registeredServicesId;

    public NotificationCenterServiceImpl(List<NotificationService> fileEventHandlerServices,
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
    public void setUserServiceConfiguration(String userId, NotificationServiceConfiguration notificationServiceConfiguration) {
        serviceConfigurationRepository.saveServiceConfiguration(userId,
                notificationServiceConfiguration.getServiceId(),
                notificationServiceConfiguration.getSettings());
    }

    @Override
    public void sendNotification(Type type, String userId, List<File> files, Map<String, Permission> sharedUsersIdWithPermission) {
        for (NotificationService fileEventHandlerService : fileEventHandlerServices) {
            String serviceId = fileEventHandlerService.getServiceId();
            NotificationServiceConfiguration notificationServiceConfiguration = getServiceConfiguration(userId, serviceId);
            if (notificationIsEnable(userId, type, serviceId)) {
                fileEventHandlerService.sendNotification(notificationServiceConfiguration, type, userId, files, sharedUsersIdWithPermission);
            }
        }
    }

    private NotificationServiceConfiguration getServiceConfiguration(String userId, String serviceId) {
        Map<String, String> settings = serviceConfigurationRepository.getServiceConfiguration(userId, serviceId);
        return new NotificationServiceConfigurationImpl(serviceId, settings);
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
