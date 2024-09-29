package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "spotAudit",
    indices = {
        @Index(value = {
            "isCutOff",
            "cutOffId",
            "beginningOR",
            "endingOR",
            "isSentToServer",
            "treg"
        })
    }
)
public class SpotAudit {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "machineNumber")
    private int machineNumber;

    @ColumnInfo(name = "branchId")
    private int branchId;

    @ColumnInfo(name = "companyId")
    private int companyId;

    @ColumnInfo(name = "beginningOR")
    private String beginningOR;

    @ColumnInfo(name = "endingOR")
    private String endingOR;

    @ColumnInfo(name = "beginningAmount")
    private double beginningAmount;

    @ColumnInfo(name = "endingAmount")
    private double endingAmount;

    @ColumnInfo(name = "totalTransactions")
    private int totalTransactions;

    @ColumnInfo(name = "grossSales")
    private double grossSales;

    @ColumnInfo(name = "netSales")
    private double netSales;

    @ColumnInfo(name = "vatableSales")
    private double vatableSales;

    @ColumnInfo(name = "vatExemptSales")
    private double vatExemptSales;

    @ColumnInfo(name = "vatAmount")
    private double vatAmount;

    @ColumnInfo(name = "vatExpense")
    private double vatExpense;

    @ColumnInfo(name = "voidQty")
    private int voidQty;

    @ColumnInfo(name = "voidAmount")
    private double voidAmount;

    @ColumnInfo(name = "totalChange")
    private double totalChange;

    @ColumnInfo(name = "totalPayout")
    private double totalPayout;

    @ColumnInfo(name = "totalServiceCharge")
    private double totalServiceCharge;

    @ColumnInfo(name = "totalDiscountAmount")
    private double totalDiscountAmount;

    @ColumnInfo(name = "totalCost")
    private double totalCost;

    @ColumnInfo(name = "safekeepingAmount")
    private double safekeepingAmount;

    @ColumnInfo(name = "safekeepingShortOver")
    private double safekeepingShortOver;

    @ColumnInfo(name = "totalSK")
    private double totalSK;

    @ColumnInfo(name = "totalShortOver")
    private double totalShortOver;

    @ColumnInfo(name = "cashierId")
    private int cashierId;

    @ColumnInfo(name = "cashierName")
    private String cashierName;

    @ColumnInfo(name = "adminId")
    private int adminId;

    @ColumnInfo(name = "adminName")
    private String adminName;

    @ColumnInfo(name = "shiftNumber")
    private int shiftNumber;

    @ColumnInfo(name = "isCutOff")
    private int isCutOff;

    @ColumnInfo(name = "cutOffId")
    private int cutOffId;

    @ColumnInfo(name = "isSentToServer")
    private int isSentToServer;

    @ColumnInfo(name = "treg")
    private String treg;

    public SpotAudit(int machineNumber, int branchId, String beginningOR, String endingOR, double beginningAmount, double endingAmount, int totalTransactions, double grossSales, double netSales, double vatableSales, double vatExemptSales, double vatAmount, double vatExpense, int voidQty, double voidAmount, double totalChange, double totalPayout, double totalServiceCharge, double totalDiscountAmount, double totalCost, double safekeepingAmount, double totalSK, double totalShortOver, int cashierId, String cashierName, int adminId, String adminName, int shiftNumber, int isCutOff, int cutOffId, int isSentToServer, String treg, double safekeepingShortOver, int companyId) {
        this.machineNumber = machineNumber;
        this.branchId = branchId;
        this.beginningOR = beginningOR;
        this.endingOR = endingOR;
        this.beginningAmount = beginningAmount;
        this.endingAmount = endingAmount;
        this.totalTransactions = totalTransactions;
        this.grossSales = grossSales;
        this.netSales = netSales;
        this.vatableSales = vatableSales;
        this.vatExemptSales = vatExemptSales;
        this.vatAmount = vatAmount;
        this.vatExpense = vatExpense;
        this.voidQty = voidQty;
        this.voidAmount = voidAmount;
        this.totalChange = totalChange;
        this.totalPayout = totalPayout;
        this.totalServiceCharge = totalServiceCharge;
        this.totalDiscountAmount = totalDiscountAmount;
        this.totalCost = totalCost;
        this.safekeepingAmount = safekeepingAmount;
        this.totalSK = totalSK;
        this.totalShortOver = totalShortOver;
        this.cashierId = cashierId;
        this.cashierName = cashierName;
        this.adminId = adminId;
        this.adminName = adminName;
        this.shiftNumber = shiftNumber;
        this.isCutOff = isCutOff;
        this.cutOffId = cutOffId;
        this.isSentToServer = isSentToServer;
        this.treg = treg;
        this.safekeepingShortOver = safekeepingShortOver;
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

    public String getBeginningOR() {
        return beginningOR;
    }

    public void setBeginningOR(String beginningOR) {
        this.beginningOR = beginningOR;
    }

    public String getEndingOR() {
        return endingOR;
    }

    public void setEndingOR(String endingOR) {
        this.endingOR = endingOR;
    }

    public double getBeginningAmount() {
        return beginningAmount;
    }

    public void setBeginningAmount(double beginningAmount) {
        this.beginningAmount = beginningAmount;
    }

    public double getEndingAmount() {
        return endingAmount;
    }

    public void setEndingAmount(double endingAmount) {
        this.endingAmount = endingAmount;
    }

    public int getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(int totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public double getGrossSales() {
        return grossSales;
    }

    public void setGrossSales(double grossSales) {
        this.grossSales = grossSales;
    }

    public double getNetSales() {
        return netSales;
    }

    public void setNetSales(double netSales) {
        this.netSales = netSales;
    }

    public double getVatableSales() {
        return vatableSales;
    }

    public void setVatableSales(double vatableSales) {
        this.vatableSales = vatableSales;
    }

    public double getVatExemptSales() {
        return vatExemptSales;
    }

    public void setVatExemptSales(double vatExemptSales) {
        this.vatExemptSales = vatExemptSales;
    }

    public double getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(double vatAmount) {
        this.vatAmount = vatAmount;
    }

    public double getVatExpense() {
        return vatExpense;
    }

    public void setVatExpense(double vatExpense) {
        this.vatExpense = vatExpense;
    }

    public int getVoidQty() {
        return voidQty;
    }

    public void setVoidQty(int voidQty) {
        this.voidQty = voidQty;
    }

    public double getVoidAmount() {
        return voidAmount;
    }

    public void setVoidAmount(double voidAmount) {
        this.voidAmount = voidAmount;
    }

    public double getTotalChange() {
        return totalChange;
    }

    public void setTotalChange(double totalChange) {
        this.totalChange = totalChange;
    }

    public double getTotalPayout() {
        return totalPayout;
    }

    public void setTotalPayout(double totalPayout) {
        this.totalPayout = totalPayout;
    }

    public double getTotalServiceCharge() {
        return totalServiceCharge;
    }

    public void setTotalServiceCharge(double totalServiceCharge) {
        this.totalServiceCharge = totalServiceCharge;
    }

    public double getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public void setTotalDiscountAmount(double totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getSafekeepingAmount() {
        return safekeepingAmount;
    }

    public void setSafekeepingAmount(double safekeepingAmount) {
        this.safekeepingAmount = safekeepingAmount;
    }

    public double getTotalSK() {
        return totalSK;
    }

    public void setTotalSK(double totalSK) {
        this.totalSK = totalSK;
    }

    public double getTotalShortOver() {
        return totalShortOver;
    }

    public void setTotalShortOver(double totalShortOver) {
        this.totalShortOver = totalShortOver;
    }

    public int getCashierId() {
        return cashierId;
    }

    public void setCashierId(int cashierId) {
        this.cashierId = cashierId;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public int getShiftNumber() {
        return shiftNumber;
    }

    public void setShiftNumber(int shiftNumber) {
        this.shiftNumber = shiftNumber;
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

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

    public double getSafekeepingShortOver() {
        return safekeepingShortOver;
    }

    public void setSafekeepingShortOver(double safekeepingShortOver) {
        this.safekeepingShortOver = safekeepingShortOver;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

}
