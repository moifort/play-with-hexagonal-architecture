package application.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationServiceConfigurationDTO {

    @JsonProperty private String id;
    @JsonProperty private String value;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "NotificationConfigurationDTO{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
