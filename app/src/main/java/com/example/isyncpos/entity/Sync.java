package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sync")
public class Sync {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "isSync")
    private int isSync;
    @ColumnInfo(name = "isFirstSync")
    private int isFirstSync;
    @ColumnInfo(name = "updatedAt")
    private String updatedAt;
    @ColumnInfo(name = "treg")
    private String treg;

    public Sync(String name, int isSync, int isFirstSync, String treg, String updatedAt) {
        this.name = name;
        this.isSync = isSync;
        this.isFirstSync = isFirstSync;
        this.treg = treg;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsSync() {
        return isSync;
    }

    public void setIsSync(int isSync) {
        this.isSync = isSync;
    }

    public int getIsFirstSync() {
        return isFirstSync;
    }

    public void setIsFirstSync(int isFirstSync) {
        this.isFirstSync = isFirstSync;
    }

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
