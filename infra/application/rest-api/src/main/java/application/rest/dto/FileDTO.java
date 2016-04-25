package application.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class FileDTO {

    @JsonProperty private Long id;
    @JsonProperty private String name;
    @JsonProperty private String ownerId;
    @JsonProperty private Map<String, String> sharedUsersIdWithPermission;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Map<String, String> getSharedUsersIdWithPermission() {
        return sharedUsersIdWithPermission;
    }

    public void setSharedUsersIdWithPermission(Map<String, String> sharedUsersIdWithPermission) {
        this.sharedUsersIdWithPermission = sharedUsersIdWithPermission;
    }

    @Override
    public String toString() {
        return "FileDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
