package com.example.isyncpos.objects;

public class AccountReceivablePaymentsObject {

    private int id;

    private int machineNumber;

    private int branchId;

    private int companyId;

    private int transactionId;

    private int paymentTypeId;

    private String paymentTypeName;

    private double amount;

    private int isAdvancePayment;

    private int isAccountReceivable;

    private int shiftNumber;

    private int isVoid;

    private int voidById;

    private String voidBy;

    private String voidAt;

    private int isSentToServer;

    private int cutOffId;

    private int isCutOff;

    private String cutOffAt;

    private String treg;

    public AccountReceivablePaymentsObject(int machineNumber, int branchId, int companyId, int transactionId, int paymentTypeId, String paymentTypeName, double amount, int isAdvancePayment, int isAccountReceivable, int shiftNumber, int isVoid, int voidById, String voidBy, String voidAt, int isSentToServer, int cutOffId, int isCutOff, String cutOffAt, String treg) {
        this.machineNumber = machineNumber;
        this.branchId = branchId;
        this.companyId = companyId;
        this.transactionId = transactionId;
        this.paymentTypeId = paymentTypeId;
        this.paymentTypeName = paymentTypeName;
        this.amount = amount;
        this.isAdvancePayment = isAdvancePayment;
        this.isAccountReceivable = isAccountReceivable;
        this.shiftNumber = shiftNumber;
        this.isVoid = isVoid;
        this.voidById = voidById;
        this.voidBy = voidBy;
        this.voidAt = voidAt;
        this.isSentToServer = isSentToServer;
        this.cutOffId = cutOffId;
        this.isCutOff = isCutOff;
        this.cutOffAt = cutOffAt;
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

    public int getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(int paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getIsAdvancePayment() {
        return isAdvancePayment;
    }

    public void setIsAdvancePayment(int isAdvancePayment) {
        this.isAdvancePayment = isAdvancePayment;
    }

    public int getIsAccountReceivable() {
        return isAccountReceivable;
    }

    public void setIsAccountReceivable(int isAccountReceivable) {
        this.isAccountReceivable = isAccountReceivable;
    }

    public int getShiftNumber() {
        return shiftNumber;
    }

    public void setShiftNumber(int shiftNumber) {
        this.shiftNumber = shiftNumber;
    }

    public int getIsVoid() {
        return isVoid;
    }

    public void setIsVoid(int isVoid) {
        this.isVoid = isVoid;
    }

    public int getVoidById() {
        return voidById;
    }

    public void setVoidById(int voidById) {
        this.voidById = voidById;
    }

    public String getVoidBy() {
        return voidBy;
    }

    public void setVoidBy(String voidBy) {
        this.voidBy = voidBy;
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

    public int getCutOffId() {
        return cutOffId;
    }

    public void setCutOffId(int cutOffId) {
        this.cutOffId = cutOffId;
    }

    public int getIsCutOff() {
        return isCutOff;
    }

    public void setIsCutOff(int isCutOff) {
        this.isCutOff = isCutOff;
    }

    public String getCutOffAt() {
        return cutOffAt;
    }

    public void setCutOffAt(String cutOffAt) {
        this.cutOffAt = cutOffAt;
    }

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

}
