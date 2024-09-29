package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.isyncpos.entity.Permissions;
import com.example.isyncpos.repositories.PermissionRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PermissionsViewModel extends AndroidViewModel {

    private PermissionRepository permissionRepository;

    public PermissionsViewModel(Application application){
        super(application);
        permissionRepository = new PermissionRepository(application);
    }

    public void insert(Permissions permissions){
        permissionRepository.insert(permissions);
    }

    public List<Permissions> fetchByRoleUserId(int roleId, int userId) throws ExecutionException, InterruptedException {
        return permissionRepository.fetchByRoleUserId(roleId, userId);
    }

    public Permissions fetchByRoleUserIdPermissionId(int roleId, int userId, int permissionId) throws ExecutionException, InterruptedException {
        return permissionRepository.fetchByRoleUserIdPermissionId(roleId, userId, permissionId);
    }

    public Void removePermission() throws ExecutionException, InterruptedException {
        return permissionRepository.removePermission();
    }

}
