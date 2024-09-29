package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "endOffDayProducts",
    indices = {
        @Index(value = {
            "endOfDayId",
            "isSentToServer"
        })
    }
)
public class EndOffDayProducts {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "machineNumber")
    private int machineNumber;

    @ColumnInfo(name = "branchId")
    private int branchId;

    @ColumnInfo(name = "companyId")
    private int companyId;

    @ColumnInfo(name = "endOfDayId")
    private int endOfDayId;

    @ColumnInfo(name = "productId")
    private int productId;

    @ColumnInfo(name = "qty")
    private double qty;

    @ColumnInfo(name = "isSentToServer")
    private int isSentToServer;

    @ColumnInfo(name = "treg")
    private String treg;

    public EndOffDayProducts(int endOfDayId, int productId, double qty, int isSentToServer, String treg, int machineNumber, int branchId, int companyId) {
        this.endOfDayId = endOfDayId;
        this.productId = productId;
        this.qty = qty;
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

    public int getEndOfDayId() {
        return endOfDayId;
    }

    public void setEndOfDayId(int endOfDayId) {
        this.endOfDayId = endOfDayId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
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
