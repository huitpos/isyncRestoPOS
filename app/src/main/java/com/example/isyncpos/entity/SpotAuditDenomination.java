package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "spotAuditDenomination",
    indices = {
        @Index(value = {
            "isCutOff",
            "cutOffId",
            "spotAuditId",
            "isSentToServer"
        })
    }
)
public class SpotAuditDenomination {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "machineNumber")
    private int machineNumber;

    @ColumnInfo(name = "branchId")
    private int branchId;

    @ColumnInfo(name = "companyId")
    private int companyId;

    @ColumnInfo(name = "spotAuditId")
    private int spotAuditId;

    @ColumnInfo(name = "cashDenominationId")
    private int cashDenominationId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "qty")
    private int qty;

    @ColumnInfo(name = "total")
    private double total;

    @ColumnInfo(name = "isCutOff")
    private int isCutOff;

    @ColumnInfo(name = "cutOffId")
    private int cutOffId;

    @ColumnInfo(name = "isSentToServer")
    private int isSentToServer;

    @ColumnInfo(name = "shiftNumber")
    private int shiftNumber;

    @ColumnInfo(name = "treg")
    private String treg;

    public SpotAuditDenomination(int machineNumber, int branchId, int spotAuditId, int cashDenominationId, String name, double amount, int qty, double total, int isCutOff, int cutOffId, int isSentToServer, int shiftNumber, String treg, int companyId) {
        this.machineNumber = machineNumber;
        this.branchId = branchId;
        this.spotAuditId = spotAuditId;
        this.cashDenominationId = cashDenominationId;
        this.name = name;
        this.amount = amount;
        this.qty = qty;
        this.total = total;
        this.isCutOff = isCutOff;
        this.cutOffId = cutOffId;
        this.isSentToServer = isSentToServer;
        this.shiftNumber = shiftNumber;
        this.treg = treg;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSpotAuditId() {
        return spotAuditId;
    }

    public void setSpotAuditId(int spotAuditId) {
        this.spotAuditId = spotAuditId;
    }

    public int getCashDenominationId() {
        return cashDenominationId;
    }

    public void setCashDenominationId(int cashDenominationId) {
        this.cashDenominationId = cashDenominationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIsCutOff() {
        return isCutOff;
    }

    public void setIsCutOff(int isCutOff) {
        this.isCutOff = isCutOff;
    }

    public int getCutOffId() {
        return cutOffId;
    }

    public void setCutOffId(int cutOffId) {
        this.cutOffId = cutOffId;
    }

    public int getIsSentToServer() {
        return isSentToServer;
    }

    public void setIsSentToServer(int isSentToServer) {
        this.isSentToServer = isSentToServer;
    }

    public int getShiftNumber() {
        return shiftNumber;
    }

    public void setShiftNumber(int shiftNumber) {
        this.shiftNumber = shiftNumber;
    }

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

}
