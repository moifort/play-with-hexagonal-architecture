package application.rest.resource;

import application.rest.dto.NotificationConfigurationDTO;
import application.rest.dto.NotificationServiceConfigurationDTO;
import com.codahale.metrics.annotation.Timed;
import domain.notificationcenter.api.NotificationCenterService;
import domain.notificationcenter.api.NotificationServiceConfiguration;
import notification.irc.configuration.IrcConfigurationBuilder;
import notification.mail.configuration.MailConfigurationBuilder;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

@Path("/notification-center/configuration")
@Produces(MediaType.APPLICATION_JSON)
public class NotificationCenterResource {

    private final NotificationCenterService notificationCenterService;

    public NotificationCenterResource(NotificationCenterService notificationCenterService) {
        this.notificationCenterService = notificationCenterService;
    }

    @POST
    @Path("/service")
    @Timed
    public Response activateServiceNotification(@Valid NotificationServiceConfigurationDTO notificationServiceConfigurationDTO) {
        serviceConfigurationFactory(notificationServiceConfigurationDTO);
        return Response.ok().build();
    }

    @POST
    @Path("/")
    @Timed
    public Response configureNotification(@Valid NotificationConfigurationDTO notificationConfigurationDTO) {
        notificationCenterService.setUserNotificationSetting("Thibaut",
                Collections.singletonList(notificationConfigurationDTO.getId()),
                Collections.singletonList(notificationConfigurationDTO.getType()),
                notificationConfigurationDTO.getIsEnable());
        return Response.ok().build();
    }



    private void serviceConfigurationFactory(NotificationServiceConfigurationDTO notificationServiceConfigurationDTO) {
        final NotificationServiceConfiguration configuration;
        switch (notificationServiceConfigurationDTO.getId()) {
            case "mail": {
                configuration = new MailConfigurationBuilder()
                        .setEmail(notificationServiceConfigurationDTO.getValue())
                        .build();
                break;
            }
            case "irc": {
                configuration = new IrcConfigurationBuilder()
                        .setChannel(notificationServiceConfigurationDTO.getValue())
                        .build();
                break;
            }
            default: configuration = null;
        }
        notificationCenterService.setUserServiceConfiguration("Thibaut", configuration);
    }

}