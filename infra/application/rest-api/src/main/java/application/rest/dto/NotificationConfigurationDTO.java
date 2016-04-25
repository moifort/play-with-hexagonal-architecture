package application.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import domain.filemanager.spi.FileEventNotification;

public class NotificationConfigurationDTO {

    @JsonProperty private String id;
    @JsonProperty private FileEventNotification.Type type;
    @JsonProperty private Boolean isEnable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FileEventNotification.Type getType() {
        return type;
    }

    public void setType(FileEventNotification.Type type) {
        this.type = type;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    @Override
    public String toString() {
        return "NotificationConfigurationDTO{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", isEnable=" + isEnable +
                '}';
    }

}
