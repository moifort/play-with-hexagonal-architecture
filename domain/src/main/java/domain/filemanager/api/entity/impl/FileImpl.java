package domain.filemanager.api.entity.impl;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;

import java.util.HashMap;
import java.util.Map;

public class FileImpl implements File {
    private byte[] data;
    private String id;
    private String name;
    private String ownerId;
    private Map<String, Permission> sharedUsersIdWithPermission = new HashMap<>();

    @Override
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public Map<String, Permission> getSharedUsersIdWithPermission() {
        return sharedUsersIdWithPermission;
    }

    public void setSharedUsersIdWithPermission(Map<String, Permission> sharedUsersIdWithPermission) {
        this.sharedUsersIdWithPermission = sharedUsersIdWithPermission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        File file = (File) o;

        if (!id.equals(file.getId())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "FileImpl{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", sharedUsersIdWithPermission=" + sharedUsersIdWithPermission +
                '}';
    }
}
