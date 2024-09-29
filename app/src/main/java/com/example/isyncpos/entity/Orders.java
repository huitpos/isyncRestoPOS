package com.example.isyncpos.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "orders",
    indices = {
        @Index(value = {
            "transactionId",
            "productId",
            "isVoid",
            "isPaid",
            "isSentToServer",
            "isCompleted",
            "isBackout",
            "isCutOff",
            "cutOffId",
            "isReturn"
        })
    }
)
public class Orders {

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

    @ColumnInfo(name = "productId")
    private int productId;

    @ColumnInfo(name = "code")
    private String code;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "abbreviation")
    private String abbreviation;

    @ColumnInfo(name = "cost")
    private double cost;

    @ColumnInfo(name = "qty")
    private double qty;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "originalAmount")
    private double originalAmount;

    @ColumnInfo(name = "gross")
    private double gross;

    @ColumnInfo(name = "total")
    private double total;

    @ColumnInfo(name = "totalCost")
    private double totalCost;

    @ColumnInfo(name = "isVatable")
    private int isVatable;

    @ColumnInfo(name = "vatAmount")
    private double vatAmount;

    @ColumnInfo(name = "vatableSales")
    private double vatableSales;

    @ColumnInfo(name = "vatExemptSales")
    private double vatExemptSales;

    @ColumnInfo(name = "discountAmount")
    private double discountAmount;

    @ColumnInfo(name = "vatExpense")
    private double vatExpense;

    @ColumnInfo(name = "departmentId")
    private int departmentId;

    @ColumnInfo(name = "departmentName")
    private String departmentName;

    @ColumnInfo(name = "categoryId")
    private int categoryId;

    @ColumnInfo(name = "categoryName")
    private String categoryName;

    @ColumnInfo(name = "subCategoryId")
    private int subCategoryId;

    @ColumnInfo(name = "subCategoryName")
    private String subCategoryName;

    @ColumnInfo(name = "unitId")
    private int unitId;

    @ColumnInfo(name = "unitName")
    private String unitName;

    @ColumnInfo(name = "priceChangeReasonId")
    private int priceChangeReasonId;

    @ColumnInfo(name = "isDiscountExempt")
    private int isDiscountExempt;

    @ColumnInfo(name = "isOpenPrice")
    private int isOpenPrice;

    @ColumnInfo(name = "withSerial")
    private int withSerial;

    @ColumnInfo(name = "isVoid")
    private int isVoid;

    @ColumnInfo(name = "voidBy")
    private String voidBy;

    @ColumnInfo(name = "voidAt")
    private String voidAt;

    @ColumnInfo(name = "isBackout")
    private int isBackout;

    @ColumnInfo(name = "isBackoutId")
    private int isBackoutId;

    @ColumnInfo(name = "backoutBy")
    private String backoutBy;

    @ColumnInfo(name = "minAmountSold")
    private double minAmountSold;

    @ColumnInfo(name = "isPaid")
    private int isPaid;

    @ColumnInfo(name = "isSentToServer")
    private int isSentToServer;

    @ColumnInfo(name = "isCompleted")
    private int isCompleted;

    @ColumnInfo(name = "completedAt")
    private String completedAt;

    @ColumnInfo(name = "shiftNumber")
    private int shiftNumber;

    @ColumnInfo(name = "cutOffId")
    private int cutOffId;

    @ColumnInfo(name = "isCutOff")
    private int isCutOff;

    @ColumnInfo(name = "cutOffAt")
    private String cutOffAt;

    @ColumnInfo(name = "discountDetailsId")
    private int discountDetailsId;

    @ColumnInfo(name = "serialNumber")
    private String serialNumber;

    @ColumnInfo(name = "remarks")
    private String remarks;

    @ColumnInfo(name = "isReturn")
    private int isReturn;

    @ColumnInfo(name = "zeroRatedAmount")
    private double zeroRatedAmount;

    @ColumnInfo(name = "isZeroRated")
    private int isZeroRated;

    @ColumnInfo(name = "treg")
    private String treg;

    public Orders(int machineNumber, int transactionId, int productId, String code, String name, String description, String abbreviation, double cost, double qty, double amount, double originalAmount, double gross, double total, double totalCost, int isVatable, double vatAmount, double vatableSales, double vatExemptSales, double discountAmount, double vatExpense, int departmentId, String departmentName, int categoryId, String categoryName, int subCategoryId, String subCategoryName, int unitId, String unitName, int isDiscountExempt, int isOpenPrice, int withSerial, int isVoid, String voidBy, String voidAt, int isBackout, int isBackoutId, String backoutBy, double minAmountSold, int isPaid, int isSentToServer, int isCompleted, String completedAt, int branchId, int shiftNumber, int cutOffId, String treg, int isCutOff, String cutOffAt, int discountDetailsId, String remarks, int isReturn, String serialNumber, int isZeroRated, double zeroRatedAmount, int companyId, int priceChangeReasonId) {
        this.machineNumber = machineNumber;
        this.transactionId = transactionId;
        this.productId = productId;
        this.code = code;
        this.name = name;
        this.description = description;
        this.abbreviation = abbreviation;
        this.cost = cost;
        this.qty = qty;
        this.amount = amount;
        this.originalAmount = originalAmount;
        this.gross = gross;
        this.total = total;
        this.totalCost = totalCost;
        this.isVatable = isVatable;
        this.vatAmount = vatAmount;
        this.vatableSales = vatableSales;
        this.vatExemptSales = vatExemptSales;
        this.discountAmount = discountAmount;
        this.vatExpense = vatExpense;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.unitId = unitId;
        this.unitName = unitName;
        this.isDiscountExempt = isDiscountExempt;
        this.isOpenPrice = isOpenPrice;
        this.withSerial = withSerial;
        this.isVoid = isVoid;
        this.voidBy = voidBy;
        this.voidAt = voidAt;
        this.isBackout = isBackout;
        this.isBackoutId = isBackoutId;
        this.backoutBy = backoutBy;
        this.minAmountSold = minAmountSold;
        this.isPaid = isPaid;
        this.isSentToServer = isSentToServer;
        this.isCompleted = isCompleted;
        this.completedAt = completedAt;
        this.branchId = branchId;
        this.shiftNumber = shiftNumber;
        this.cutOffId = cutOffId;
        this.treg = treg;
        this.isCutOff = isCutOff;
        this.cutOffAt = cutOffAt;
        this.discountDetailsId = discountDetailsId;
        this.remarks = remarks;
        this.isReturn = isReturn;
        this.serialNumber = serialNumber;
        this.isZeroRated = isZeroRated;
        this.zeroRatedAmount = zeroRatedAmount;
        this.companyId = companyId;
        this.priceChangeReasonId = priceChangeReasonId;
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

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(double originalAmount) {
        this.originalAmount = originalAmount;
    }

    public double getGross() {
        return gross;
    }

    public void setGross(double gross) {
        this.gross = gross;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getIsVatable() {
        return isVatable;
    }

    public void setIsVatable(int isVatable) {
        this.isVatable = isVatable;
    }

    public double getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(double vatAmount) {
        this.vatAmount = vatAmount;
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

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getVatExpense() {
        return vatExpense;
    }

    public void setVatExpense(double vatExpense) {
        this.vatExpense = vatExpense;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getIsDiscountExempt() {
        return isDiscountExempt;
    }

    public void setIsDiscountExempt(int isDiscountExempt) {
        this.isDiscountExempt = isDiscountExempt;
    }

    public int getIsOpenPrice() {
        return isOpenPrice;
    }

    public void setIsOpenPrice(int isOpenPrice) {
        this.isOpenPrice = isOpenPrice;
    }

    public int getWithSerial() {
        return withSerial;
    }

    public void setWithSerial(int withSerial) {
        this.withSerial = withSerial;
    }

    public int getIsVoid() {
        return isVoid;
    }

    public void setIsVoid(int isVoid) {
        this.isVoid = isVoid;
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

    public int getIsBackout() {
        return isBackout;
    }

    public void setIsBackout(int isBackout) {
        this.isBackout = isBackout;
    }

    public int getIsBackoutId() {
        return isBackoutId;
    }

    public void setIsBackoutId(int isBackoutId) {
        this.isBackoutId = isBackoutId;
    }

    public String getBackoutBy() {
        return backoutBy;
    }

    public void setBackoutBy(String backoutBy) {
        this.backoutBy = backoutBy;
    }

    public double getMinAmountSold() {
        return minAmountSold;
    }

    public void setMinAmountSold(double minAmountSold) {
        this.minAmountSold = minAmountSold;
    }

    public int getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(int isPaid) {
        this.isPaid = isPaid;
    }

    public int getIsSentToServer() {
        return isSentToServer;
    }

    public void setIsSentToServer(int isSentToServer) {
        this.isSentToServer = isSentToServer;
    }

    public int getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
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

    public String getCutOffAt() {
        return cutOffAt;
    }

    public void setCutOffAt(String cutOffAt) {
        this.cutOffAt = cutOffAt;
    }

    public int getDiscountDetailsId() {
        return discountDetailsId;
    }

    public void setDiscountDetailsId(int discountDetailsId) {
        this.discountDetailsId = discountDetailsId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(int isReturn) {
        this.isReturn = isReturn;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getIsZeroRated() {
        return isZeroRated;
    }

    public void setIsZeroRated(int isZeroRated) {
        this.isZeroRated = isZeroRated;
    }

    public double getZeroRatedAmount() {
        return zeroRatedAmount;
    }

    public void setZeroRatedAmount(double zeroRatedAmount) {
        this.zeroRatedAmount = zeroRatedAmount;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getPriceChangeReasonId() {
        return priceChangeReasonId;
    }

    public void setPriceChangeReasonId(int priceChangeReasonId) {
        this.priceChangeReasonId = priceChangeReasonId;
    }

}
