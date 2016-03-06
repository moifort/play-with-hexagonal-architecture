package domain.notificationmanager.mock;

import domain.notificationmanager.spi.FileEventNotificationService;
import domain.filemanager.mock.MockFileEventEvenHandlerList;

public class MockEventNotificationServiceOne extends MockFileEventEvenHandlerList implements FileEventNotificationService {

    @Override
    public String getServiceId() {
        return "ServiceOne";
    }
}
