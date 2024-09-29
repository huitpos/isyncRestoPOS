package com.example.isyncpos.objects;

public class AccountReceivableOrdersObject {

    private int id;

    private int machineNumber;

    private int branchId;

    private int companyId;

    private int transactionId;

    private int productId;

    private String code;

    private String name;

    private String description;

    private String abbreviation;

    private double cost;

    private double qty;

    private double amount;

    private double originalAmount;

    private double gross;

    private double total;

    private double totalCost;

    private int isVatable;

    private double vatAmount;

    private double vatableSales;

    private double vatExemptSales;

    private double discountAmount;

    private double vatExpense;

    private int departmentId;

    private String departmentName;

    private int categoryId;

    private String categoryName;

    private int subCategoryId;

    private String subCategoryName;

    private int unitId;

    private String unitName;

    private int priceChangeReasonId;

    private int isDiscountExempt;

    private int isOpenPrice;

    private int withSerial;

    private int isVoid;

    private String voidBy;

    private String voidAt;

    private int isBackout;

    private int isBackoutId;

    private String backoutBy;

    private double minAmountSold;

    private int isPaid;

    private int isSentToServer;

    private int isCompleted;

    private String completedAt;

    private int shiftNumber;

    private int cutOffId;

    private int isCutOff;

    private String cutOffAt;

    private int discountDetailsId;

    private String serialNumber;

    private String remarks;

    private int isReturn;

    private double zeroRatedAmount;

    private int isZeroRated;

    private String treg;

    public AccountReceivableOrdersObject(int machineNumber, int branchId, int companyId, int transactionId, int productId, String code, String name, String description, String abbreviation, double cost, double qty, double amount, double originalAmount, double gross, double total, double totalCost, int isVatable, double vatAmount, double vatableSales, double vatExemptSales, double discountAmount, double vatExpense, int departmentId, String departmentName, int categoryId, String categoryName, int subCategoryId, String subCategoryName, int unitId, String unitName, int priceChangeReasonId, int isDiscountExempt, int isOpenPrice, int withSerial, int isVoid, String voidBy, String voidAt, int isBackout, int isBackoutId, String backoutBy, double minAmountSold, int isPaid, int isSentToServer, int isCompleted, String completedAt, int shiftNumber, int cutOffId, int isCutOff, String cutOffAt, int discountDetailsId, String serialNumber, String remarks, int isReturn, double zeroRatedAmount, int isZeroRated, String treg) {
        this.machineNumber = machineNumber;
        this.branchId = branchId;
        this.companyId = companyId;
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
        this.priceChangeReasonId = priceChangeReasonId;
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
        this.shiftNumber = shiftNumber;
        this.cutOffId = cutOffId;
        this.isCutOff = isCutOff;
        this.cutOffAt = cutOffAt;
        this.discountDetailsId = discountDetailsId;
        this.serialNumber = serialNumber;
        this.remarks = remarks;
        this.isReturn = isReturn;
        this.zeroRatedAmount = zeroRatedAmount;
        this.isZeroRated = isZeroRated;
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

    public int getPriceChangeReasonId() {
        return priceChangeReasonId;
    }

    public void setPriceChangeReasonId(int priceChangeReasonId) {
        this.priceChangeReasonId = priceChangeReasonId;
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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

    public double getZeroRatedAmount() {
        return zeroRatedAmount;
    }

    public void setZeroRatedAmount(double zeroRatedAmount) {
        this.zeroRatedAmount = zeroRatedAmount;
    }

    public int getIsZeroRated() {
        return isZeroRated;
    }

    public void setIsZeroRated(int isZeroRated) {
        this.isZeroRated = isZeroRated;
    }

    public String getTreg() {
        return treg;
    }

    public void setTreg(String treg) {
        this.treg = treg;
    }

}
