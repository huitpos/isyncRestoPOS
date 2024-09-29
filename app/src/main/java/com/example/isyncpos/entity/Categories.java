package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Categories {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "coreId")
    private int coreId;

    @ColumnInfo(name = "departmentId")
    private int departmentId;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "status")
    private int status;

    public Categories(int coreId, int departmentId, String image, String name, int status) {
        this.coreId = coreId;
        this.departmentId = departmentId;
        this.image = image;
        this.name = name;
        this.status = status;
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

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
