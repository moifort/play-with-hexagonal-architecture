package domain.notificationmanager.api.eception;

public class UnknownNotificationServiceException extends RuntimeException {

    public UnknownNotificationServiceException(String message) {
        super(message);
    }
}
