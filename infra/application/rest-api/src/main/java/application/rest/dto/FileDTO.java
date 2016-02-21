package application.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileDTO {

    @JsonProperty private Long id;
    @JsonProperty private String name;

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

    @Override
    public String toString() {
        return "FileDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
