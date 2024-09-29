package com.example.isyncpos.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.isyncpos.entity.Permissions;

import java.util.List;

@Dao
public interface PermissionsDAO {

    @Insert
    void insert(Permissions permissions);

    @Query("SELECT * FROM permissions WHERE roleId = :roleId AND userId = :userId")
    List<Permissions> fetchByRoleUserId(int roleId, int userId);

    @Query("SELECT * FROM permissions WHERE roleId = :roleId AND userId = :userId AND coreId = :permissionId")
    Permissions fetchByRoleUserIdPermissionId(int roleId, int userId, int permissionId);

    @Query("DELETE FROM permissions")
    Void removePermission();

}
