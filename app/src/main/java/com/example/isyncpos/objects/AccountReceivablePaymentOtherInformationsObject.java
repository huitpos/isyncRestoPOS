package com.example.isyncpos.objects;

public class AccountReceivablePaymentOtherInformationsObject {

    private int id;

    private int machineId;

    private int branchId;

    private int companyId;

    private int transactionId;

    private int paymentId;

    private String name;

    private String value;

    private int isCutOff;

    private int cutOffId;

    private int isVoid;

    private int isSentToServer;

    private String treg;

    public AccountReceivablePaymentOtherInformationsObject(int machineId, int branchId, int companyId, int transactionId, int paymentId, String name, String value, int isCutOff, int cutOffId, int isVoid, int isSentToServer, String treg) {
        this.machineId = machineId;
        this.branchId = branchId;
        this.companyId = companyId;
        this.transactionId = transactionId;
        this.paymentId = paymentId;
        this.name = name;
        this.value = value;
        this.isCutOff = isCutOff;
        this.cutOffId = cutOffId;
        this.isVoid = isVoid;
        this.isSentToServer = isSentToServer;
        this.treg = treg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
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

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public int getIsVoid() {
        return isVoid;
    }

    public void setIsVoid(int isVoid) {
        this.isVoid = isVoid;
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
