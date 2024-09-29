package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "tableTypes",
    indices = {
        @Index(value = {
            "coreId",
            "status"
        })
    }
)
public class TableTypes {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "coreId")
    private int coreId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "status")
    private int status;

    public TableTypes(int coreId, String name, int status) {
        this.coreId = coreId;
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
