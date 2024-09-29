package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "tableStatus",
    indices = {
        @Index(value = {
            "coreId",
            "status"
        })
    }
)
public class TableStatus {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "coreId")
    private int coreId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "color")
    private String color;

    @ColumnInfo(name = "status")
    private int status;

    public TableStatus(int coreId, String name, String color, int status) {
        this.coreId = coreId;
        this.name = name;
        this.color = color;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
