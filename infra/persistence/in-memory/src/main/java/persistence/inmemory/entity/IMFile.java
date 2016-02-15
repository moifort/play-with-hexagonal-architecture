package persistence.inmemory.entity;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IMFile implements File {
    private final String id;
    private final String name;
    private final byte[] data;
    private final String ownerId;
    private Map<String, Permission> sharedUsersIdWithPermission = new HashMap<>();

    public IMFile(String id, String name, byte[] data, String ownerId) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.ownerId = ownerId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public byte[] getData() {
        return data;
    }

    @Override
    public String getOwnerId() {
        return ownerId;
    }

    @Override
    public Map<String, Permission> getSharedUsersIdWithPermission() {
        return sharedUsersIdWithPermission;
    }

    public void setSharedUsersIdWithPermission(Map<String, Permission> sharedUsersIdWithPermission) {
        this.sharedUsersIdWithPermission.putAll(sharedUsersIdWithPermission);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IMFile author = (IMFile) o;

        if ( ! Objects.equals(id, author.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "IMFile{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", ownerId='" + ownerId + '\'' +
            ", sharedUsersIdWithPermission=" + sharedUsersIdWithPermission +
            '}';
    }
}
