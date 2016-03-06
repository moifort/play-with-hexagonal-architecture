package domain.notificationmanager.mock;

import domain.notificationmanager.spi.FileEventNotificationService;
import domain.filemanager.mock.MockFileEventEvenHandlerList;

public class MockEventNotificationServiceTwo extends MockFileEventEvenHandlerList implements FileEventNotificationService {

    @Override
    public String getServiceId() {
        return "ServiceTwo";
    }
}
