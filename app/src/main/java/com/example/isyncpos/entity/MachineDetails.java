package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "machineDetails")
public class MachineDetails {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "coreId")
    private int coreId;

    @ColumnInfo(name = "productKey")
    private String productKey;

    @ColumnInfo(name = "machineNumber")
    private String machineNumber;

    @ColumnInfo(name = "serialNumber")
    private String serialNumber;

    @ColumnInfo(name = "minNumber")
    private String minNumber;

    @ColumnInfo(name = "receiptHeader")
    private String receiptHeader;

    @ColumnInfo(name = "receiptFooter")
    private String receiptFooter;

    @ColumnInfo(name = "permitNumber")
    private String permitNumber;

    @ColumnInfo(name = "accreditationNumber")
    private String accreditationNumber;

    @ColumnInfo(name = "validFrom")
    private String validFrom;

    @ColumnInfo(name = "validTo")
    private String validTo;

    @ColumnInfo(name = "tinNumber")
    private String tinNumber;

    @ColumnInfo(name = "cashRegisterLimitAmount")
    private double cashRegisterLimitAmount;

    @ColumnInfo(name = "vatPercentage")
    private double vatPercentage;

    @ColumnInfo(name = "orCounter")
    private int orCounter;

    @ColumnInfo(name = "xreadingCounter")
    private int xreadingCounter;

    @ColumnInfo(name = "zreadingCounter")
    private int zreadingCounter;

    @ColumnInfo(name = "voidCounter")
    private int voidCounter;

    @ColumnInfo(name = "machineType")
    private String machineType;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "isSentToServer")
    private int isSentToServer;

    public MachineDetails(int coreId, String productKey, String machineNumber, String serialNumber, String minNumber, String receiptHeader, String receiptFooter, String permitNumber, String accreditationNumber, String validFrom, String validTo, String tinNumber, double cashRegisterLimitAmount, double vatPercentage, int orCounter, int xreadingCounter, int zreadingCounter, int voidCounter, String machineType, String status, int isSentToServer) {
        this.coreId = coreId;
        this.productKey = productKey;
        this.machineNumber = machineNumber;
        this.serialNumber = serialNumber;
        this.minNumber = minNumber;
        this.receiptHeader = receiptHeader;
        this.receiptFooter = receiptFooter;
        this.permitNumber = permitNumber;
        this.accreditationNumber = accreditationNumber;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.tinNumber = tinNumber;
        this.cashRegisterLimitAmount = cashRegisterLimitAmount;
        this.vatPercentage = vatPercentage;
        this.orCounter = orCounter;
        this.xreadingCounter = xreadingCounter;
        this.zreadingCounter = zreadingCounter;
        this.voidCounter = voidCounter;
        this.machineType = machineType;
        this.status = status;
        this.isSentToServer = isSentToServer;
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

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(String machineNumber) {
        this.machineNumber = machineNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMinNumber() {
        return minNumber;
    }

    public void setMinNumber(String minNumber) {
        this.minNumber = minNumber;
    }

    public String getReceiptHeader() {
        return receiptHeader;
    }

    public void setReceiptHeader(String receiptHeader) {
        this.receiptHeader = receiptHeader;
    }

    public String getReceiptFooter() {
        return receiptFooter;
    }

    public void setReceiptFooter(String receiptFooter) {
        this.receiptFooter = receiptFooter;
    }

    public String getPermitNumber() {
        return permitNumber;
    }

    public void setPermitNumber(String permitNumber) {
        this.permitNumber = permitNumber;
    }

    public String getAccreditationNumber() {
        return accreditationNumber;
    }

    public void setAccreditationNumber(String accreditationNumber) {
        this.accreditationNumber = accreditationNumber;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public double getCashRegisterLimitAmount() {
        return cashRegisterLimitAmount;
    }

    public void setCashRegisterLimitAmount(double cashRegisterLimitAmount) {
        this.cashRegisterLimitAmount = cashRegisterLimitAmount;
    }

    public double getVatPercentage() {
        return vatPercentage;
    }

    public void setVatPercentage(double vatPercentage) {
        this.vatPercentage = vatPercentage;
    }

    public int getOrCounter() {
        return orCounter;
    }

    public void setOrCounter(int orCounter) {
        this.orCounter = orCounter;
    }

    public int getXreadingCounter() {
        return xreadingCounter;
    }

    public void setXreadingCounter(int xreadingCounter) {
        this.xreadingCounter = xreadingCounter;
    }

    public int getZreadingCounter() {
        return zreadingCounter;
    }

    public void setZreadingCounter(int zreadingCounter) {
        this.zreadingCounter = zreadingCounter;
    }

    public int getVoidCounter() {
        return voidCounter;
    }

    public void setVoidCounter(int voidCounter) {
        this.voidCounter = voidCounter;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIsSentToServer() {
        return isSentToServer;
    }

    public void setIsSentToServer(int isSentToServer) {
        this.isSentToServer = isSentToServer;
    }

}
