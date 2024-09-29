package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "paymentTypeFields",
    indices = {
        @Index(value = {
            "paymentTypeId"
        })
    }
)
public class PaymentTypeFields {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "coreId")
    private int coreId;

    @ColumnInfo(name = "paymentTypeId")
    private int paymentTypeId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "fieldType")
    private String fieldType;

    public PaymentTypeFields(int coreId, int paymentTypeId, String name, String fieldType) {
        this.coreId = coreId;
        this.paymentTypeId = paymentTypeId;
        this.name = name;
        this.fieldType = fieldType;
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

    public int getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(int paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

}
