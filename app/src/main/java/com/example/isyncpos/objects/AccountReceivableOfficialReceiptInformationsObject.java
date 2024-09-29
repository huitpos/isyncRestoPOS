package com.example.isyncpos.objects;

public class AccountReceivableOfficialReceiptInformationsObject {

    private int id;

    private int machineNumber;

    private int branchId;

    private int companyId;

    private int transactionId;

    private String name;

    private String address;

    private String tin;

    private String businessStyle;

    private int isVoid;

    private int voidBy;

    private String voidName;

    private String voidAt;

    private int isSentToServer;

    private String treg;

    public AccountReceivableOfficialReceiptInformationsObject(int machineNumber, int branchId, int companyId, int transactionId, String name, String address, String tin, String businessStyle, int isVoid, int voidBy, String voidName, String voidAt, int isSentToServer, String treg) {
        this.machineNumber = machineNumber;
        this.branchId = branchId;
        this.companyId = companyId;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
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

}
