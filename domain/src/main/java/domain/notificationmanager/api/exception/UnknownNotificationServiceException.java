package domain.notificationmanager.api.exception;

public class UnknownNotificationServiceException extends RuntimeException {

    public UnknownNotificationServiceException(String message) {
        super(message);
    }
}
