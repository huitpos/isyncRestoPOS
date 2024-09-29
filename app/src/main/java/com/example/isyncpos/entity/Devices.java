package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "devices")
public class Devices {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "vendorId")
    private int vendorId;

    @ColumnInfo(name = "productId")
    private int productId;

    @ColumnInfo(name = "device")
    private String device;

    @ColumnInfo(name = "serialNumber")
    private String serialNumber;

    @ColumnInfo(name = "ipAddress")
    private String ipAddress;

    @ColumnInfo(name = "treg")
    private String treg;

    public Devices(String name, String type, int vendorId, int productId, String device, String serialNumber, String ipAddress, String treg) {
        this.name = name;
        this.type = type;
        this.vendorId = vendorId;
        this.productId = productId;
        this.device = device;
        this.serialNumber = serialNumber;
        this.ipAddress = ipAddress;
        this.treg = treg;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

    @Override
    public String toString() {
        return name.isEmpty() ? device : name;
    }

}
