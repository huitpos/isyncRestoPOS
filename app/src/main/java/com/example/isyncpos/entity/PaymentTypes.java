package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "paymentTypes",
    indices = {
        @Index(value = {
            "coreId",
            "status"
        })
    }
)
public class PaymentTypes {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "coreId")
    private int coreId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "isAR")
    private int isAR;

    @ColumnInfo(name = "status")
    private int status;

    public PaymentTypes(int coreId, String name, String image, int isAR, int status) {
        this.coreId = coreId;
        this.name = name;
        this.image = image;
        this.isAR = isAR;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIsAR() {
        return isAR;
    }

    public void setIsAR(int isAR) {
        this.isAR = isAR;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
