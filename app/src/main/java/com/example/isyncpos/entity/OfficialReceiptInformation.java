package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "officialReceiptInformation",
    indices = {
        @Index(value = {
            "transactionId",
            "isVoid",
            "isSentToServer",
            "name"
        })
    }
)
public class OfficialReceiptInformation {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "machineNumber")
    private int machineNumber;

    @ColumnInfo(name = "branchId")
    private int branchId;

    @ColumnInfo(name = "companyId")
    private int companyId;

    @ColumnInfo(name = "transactionId")
    private int transactionId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "tin")
    private String tin;

    @ColumnInfo(name = "businessStyle")
    private String businessStyle;

    @ColumnInfo(name = "isVoid")
    private int isVoid;

    @ColumnInfo(name = "voidBy")
    private int voidBy;

    @ColumnInfo(name = "voidName")
    private String voidName;

    @ColumnInfo(name = "voidAt")
    private String voidAt;

    @ColumnInfo(name = "isSentToServer")
    private int isSentToServer;

    @ColumnInfo(name = "treg")
    private String treg;

    public OfficialReceiptInformation(int transactionId, String name, String address, String tin, String businessStyle, int isVoid, int voidBy, String voidName, String voidAt, int isSentToServer, String treg, int machineNumber, int branchId, int companyId) {
        this.transactionId = transactionId;
        this.name = name;
        this.address = address;
        this.tin = tin;
        this.businessStyle = businessStyle;
        this.isVoid = isVoid;
        this.voidBy = voidBy;
        this.voidName = voidName;
        this.voidAt = voidAt;
        this.isSentToServer = isSentToServer;
        this.treg = treg;
        this.machineNumber = machineNumber;
        this.branchId = branchId;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getBusinessStyle() {
        return businessStyle;
    }

    public void setBusinessStyle(String businessStyle) {
        this.businessStyle = businessStyle;
    }

    public int getIsVoid() {
        return isVoid;
    }

    public void setIsVoid(int isVoid) {
        this.isVoid = isVoid;
    }

    public int getVoidBy() {
        return voidBy;
    }

    public void setVoidBy(int voidBy) {
        this.voidBy = voidBy;
    }

    public String getVoidName() {
        return voidName;
    }

    public void setVoidName(String voidName) {
        this.voidName = voidName;
    }

    public String getVoidAt() {
        return voidAt;
    }

    public void setVoidAt(String voidAt) {
        this.voidAt = voidAt;
    }

    public int getIsSentToServer() {
        return isSentToServer;
    }

    public void setIsSentToServer(int isSentToServer) {
        this.isSentToServer = isSentToServer;
    }

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

    public int getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(int machineNumber) {
        this.machineNumber = machineNumber;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

}
