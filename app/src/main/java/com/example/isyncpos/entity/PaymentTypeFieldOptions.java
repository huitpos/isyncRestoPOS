package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "paymentTypeFieldOptions",
    indices = {
        @Index(value = {
            "paymentTypeFieldId"
        })
    }
)
public class PaymentTypeFieldOptions {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "coreId")
    private int coreId;

    @ColumnInfo(name = "paymentTypeFieldId")
    private int paymentTypeFieldId;

    @ColumnInfo(name = "name")
    private String name;

    public PaymentTypeFieldOptions(int coreId, int paymentTypeFieldId, String name) {
        this.coreId = coreId;
        this.paymentTypeFieldId = paymentTypeFieldId;
        this.name = name;
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

    public int getPaymentTypeFieldId() {
        return paymentTypeFieldId;
    }

    public void setPaymentTypeFieldId(int paymentTypeFieldId) {
        this.paymentTypeFieldId = paymentTypeFieldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
