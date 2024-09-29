package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chargeAccount")
public class ChargeAccount {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "coreId")
    private int coreId;

    @ColumnInfo(name = "companyId")
    private int companyId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "creditLimit")
    private int creditLimit;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "contactNumber")
    private String contactNumber;

    @ColumnInfo(name = "email")
    private String email;

    public ChargeAccount(int coreId, int companyId, String name, int creditLimit, String address, String contactNumber, String email) {
        this.coreId = coreId;
        this.companyId = companyId;
        this.name = name;
        this.creditLimit = creditLimit;
        this.address = address;
        this.contactNumber = contactNumber;
        this.email = email;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
