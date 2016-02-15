package domain.filemanager.core;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;

import java.util.Arrays;
import java.util.Map;

public class FileAccessService {

    public boolean isUserHasNotAccess(String userId, File file) {
        return !isUserHasAccess(userId, file);
    }

    public boolean isUserCanNotDelete(String userId, File file) {
        return !isUserCanDelete(userId, file);
    }

    public boolean isUserCanNotShare(String userId, File file) {
        return !isUserCanShare(userId, file);
    }

    public boolean isUserHasAccess(String userId, File file) {
        if (isOwner(userId, file)) return true;
        if (isUserHasAtLeastGivenPermission(userId, file, Permission.GET_AND_DELETE, Permission.GET)) return true;
        return false;
    }

    public boolean isUserCanDelete(String userId, File file) {
        if (isOwner(userId, file)) return true;
        if (isUserHasAtLeastGivenPermission(userId, file, Permission.GET_AND_DELETE)) return true;
        return false;
    }

    public boolean isUserCanShare(String userId, File file) {
        if (isOwner(userId, file)) return true;
        return false;
    }

    public boolean isOwner(String userId, File file) {
        return file.getOwnerId().equals(userId);
    }

    public boolean isUserHasAtLeastGivenPermission(String userId, File file, Permission... permissions) {
        Map<String, Permission> sharedUsersIdWithPermission = file.getSharedUsersIdWithPermission();
        if (sharedUsersIdWithPermission.containsKey(userId)) {
            Permission userPermission = sharedUsersIdWithPermission.get(userId);
            return Arrays.asList(permissions).contains(userPermission);
        }
        return false;
    }
}
