package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "safekeepingDenomination",
    indices = {
        @Index(value = {
            "safekeepingId",
            "isCutOff",
            "cutOffId",
            "endOfDayId",
            "isSentToServer"
        })
    }
)
public class SafekeepingDenomination {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "machineNumber")
    private int machineNumber;

    @ColumnInfo(name = "branchId")
    private int branchId;

    @ColumnInfo(name = "companyId")
    private int companyId;

    @ColumnInfo(name = "safekeepingId")
    private int safekeepingId;

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

    @ColumnInfo(name = "endOfDayId")
    private int endOfDayId;

    @ColumnInfo(name = "isSentToServer")
    private int isSentToServer;

    @ColumnInfo(name = "shiftNumber")
    private int shiftNumber;

    @ColumnInfo(name = "treg")
    private String treg;

    public SafekeepingDenomination(int machineNumber, int branchId, int safekeepingId, int cashDenominationId, String name, double amount, int qty, double total, int shiftNumber, int cutOffId, String treg, int isCutOff, int endOfDayId, int isSentToServer, int companyId) {
        this.machineNumber = machineNumber;
        this.branchId = branchId;
        this.safekeepingId = safekeepingId;
        this.cashDenominationId = cashDenominationId;
        this.name = name;
        this.amount = amount;
        this.qty = qty;
        this.total = total;
        this.shiftNumber = shiftNumber;
        this.cutOffId = cutOffId;
        this.treg = treg;
        this.isCutOff = isCutOff;
        this.endOfDayId = endOfDayId;
        this.isSentToServer = isSentToServer;
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

    public int getSafekeepingId() {
        return safekeepingId;
    }

    public void setSafekeepingId(int safekeepingId) {
        this.safekeepingId = safekeepingId;
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

    public int getShiftNumber() {
        return shiftNumber;
    }

    public void setShiftNumber(int shiftNumber) {
        this.shiftNumber = shiftNumber;
    }

    public int getCutOffId() {
        return cutOffId;
    }

    public void setCutOffId(int cutOffId) {
        this.cutOffId = cutOffId;
    }

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

    public int getIsCutOff() {
        return isCutOff;
    }

    public void setIsCutOff(int isCutOff) {
        this.isCutOff = isCutOff;
    }

    public int getEndOfDayId() {
        return endOfDayId;
    }

    public void setEndOfDayId(int endOfDayId) {
        this.endOfDayId = endOfDayId;
    }

    public int getIsSentToServer() {
        return isSentToServer;
    }

    public void setIsSentToServer(int isSentToServer) {
        this.isSentToServer = isSentToServer;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

}
