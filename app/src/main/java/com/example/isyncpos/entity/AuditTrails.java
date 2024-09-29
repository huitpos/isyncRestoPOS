package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "auditTrails")
public class AuditTrails {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "machineNumber")
    private int machineNumber;

    @ColumnInfo(name = "branchId")
    private int branchId;

    @ColumnInfo(name = "companyId")
    private int companyId;

    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "userName")
    private String userName;

    @ColumnInfo(name = "transactionId")
    private int transactionId;

    @ColumnInfo(name = "orderId")
    private int orderId;

    @ColumnInfo(name = "priceChangeReasonId")
    private int priceChangeReasonId;

    @ColumnInfo(name = "action")
    private String action;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "authorizeId")
    private int authorizeId;

    @ColumnInfo(name = "authorizeName")
    private String authorizeName;

    @ColumnInfo(name = "isSentToServer")
    private int isSentToServer;

    @ColumnInfo(name = "treg")
    private String treg;

    public AuditTrails(int machineNumber, int branchId, int userId, String userName, int transactionId, String action, String description, int authorizeId, String authorizeName, int isSentToServer, String treg, int orderId, int priceChangeReasonId, int companyId) {
        this.machineNumber = machineNumber;
        this.branchId = branchId;
        this.userId = userId;
        this.userName = userName;
        this.transactionId = transactionId;
        this.action = action;
        this.description = description;
        this.authorizeId = authorizeId;
        this.authorizeName = authorizeName;
        this.isSentToServer = isSentToServer;
        this.treg = treg;
        this.orderId = orderId;
        this.priceChangeReasonId = priceChangeReasonId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAuthorizeId() {
        return authorizeId;
    }

    public void setAuthorizeId(int authorizeId) {
        this.authorizeId = authorizeId;
    }

    public String getAuthorizeName() {
        return authorizeName;
    }

    public void setAuthorizeName(String authorizeName) {
        this.authorizeName = authorizeName;
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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPriceChangeReasonId() {
        return priceChangeReasonId;
    }

    public void setPriceChangeReasonId(int priceChangeReasonId) {
        this.priceChangeReasonId = priceChangeReasonId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

}
