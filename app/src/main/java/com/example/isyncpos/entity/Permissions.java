package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "permissions",
    indices = {
        @Index(value = {
            "userId",
            "roleId"
        })
    }
)
public class Permissions {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "coreId")
    private int coreId;

    @ColumnInfo(name = "parentCoreId")
    private int parentCoreId;

    @ColumnInfo(name = "roleId")
    private int roleId;

    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "level")
    private String level;

    public Permissions(int coreId, int parentCoreId, int roleId, String name, String level, int userId) {
        this.coreId = coreId;
        this.parentCoreId = parentCoreId;
        this.roleId = roleId;
        this.name = name;
        this.level = level;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoreId() {
        return coreId;
    }

    public void setCoreId(int coreId) {
        this.coreId = coreId;
    }

    public int getParentCoreId() {
        return parentCoreId;
    }

    public void setParentCoreId(int parentCoreId) {
        this.parentCoreId = parentCoreId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
