package com.example.isyncpos;

import android.app.Application;

import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.example.isyncpos.authentication.ServerUserAuthentication;
import com.example.isyncpos.authentication.UserAuthentication;
import com.example.isyncpos.common.Backup;
import com.example.isyncpos.dao.DiscountOtherInformationsDAO;
import com.example.isyncpos.dao.PaymentOtherInformationsDAO;
import com.example.isyncpos.entity.Branch;
import com.example.isyncpos.entity.Company;
import com.example.isyncpos.entity.DeviceDetails;
import com.example.isyncpos.entity.MachineDetails;
import com.example.isyncpos.handler.Exception;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.viewmodel.ApplicationSettingsViewModel;
import com.example.isyncpos.viewmodel.AuditTrailsViewModel;
import com.example.isyncpos.viewmodel.AuthenticatedMachineUserViewModel;
import com.example.isyncpos.viewmodel.BranchViewModel;
import com.example.isyncpos.viewmodel.CashDenominationViewModel;
import com.example.isyncpos.viewmodel.CashFundDenominationViewModel;
import com.example.isyncpos.viewmodel.CashFundViewModel;
import com.example.isyncpos.viewmodel.CategoriesViewModel;
import com.example.isyncpos.viewmodel.ChargeAccountViewModel;
import com.example.isyncpos.viewmodel.CompanyViewModel;
import com.example.isyncpos.viewmodel.CutOffDepartmentsViewModel;
import com.example.isyncpos.viewmodel.CutOffDiscountsViewModel;
import com.example.isyncpos.viewmodel.CutOffPaymentsViewModel;
import com.example.isyncpos.viewmodel.CutOffProductsViewModel;
import com.example.isyncpos.viewmodel.CutOffViewModel;
import com.example.isyncpos.viewmodel.DepartmentsViewModel;
import com.example.isyncpos.viewmodel.DeviceDetailsViewModel;
import com.example.isyncpos.viewmodel.DevicesViewModel;
import com.example.isyncpos.viewmodel.DiscountOtherInformationsViewModel;
import com.example.isyncpos.viewmodel.DiscountTypeDepartmentsViewModel;
import com.example.isyncpos.viewmodel.DiscountTypeFieldOptionsViewModel;
import com.example.isyncpos.viewmodel.DiscountTypeFieldsViewModel;
import com.example.isyncpos.viewmodel.DiscountTypesViewModel;
import com.example.isyncpos.viewmodel.DiscountDetailsViewModel;
import com.example.isyncpos.viewmodel.DiscountsViewModel;
import com.example.isyncpos.viewmodel.EndOfDayDepartmentsViewModel;
import com.example.isyncpos.viewmodel.EndOfDayDiscountsViewModel;
import com.example.isyncpos.viewmodel.EndOfDayPaymentsViewModel;
import com.example.isyncpos.viewmodel.EndOfDayProductsViewModel;
import com.example.isyncpos.viewmodel.EndOfDayViewModel;
import com.example.isyncpos.viewmodel.MachineDetailsViewModel;
import com.example.isyncpos.viewmodel.OfficialReceiptInformationViewModel;
import com.example.isyncpos.viewmodel.OrdersViewModel;
import com.example.isyncpos.viewmodel.POSApplicationViewModel;
import com.example.isyncpos.viewmodel.PaymentOtherInformationsViewModel;
import com.example.isyncpos.viewmodel.PaymentTypeFieldOptionsViewModel;
import com.example.isyncpos.viewmodel.PaymentTypeFieldsViewModel;
import com.example.isyncpos.viewmodel.PaymentTypesViewModel;
import com.example.isyncpos.viewmodel.PaymentsViewModel;
import com.example.isyncpos.viewmodel.PayoutsViewModel;
import com.example.isyncpos.viewmodel.PermissionsViewModel;
import com.example.isyncpos.viewmodel.PriceChangeReasonViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupDevicesViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupViewModel;
import com.example.isyncpos.viewmodel.ProductLocationsViewModel;
import com.example.isyncpos.viewmodel.ProductsBundleItemViewModel;
import com.example.isyncpos.viewmodel.ProductsViewModel;
import com.example.isyncpos.viewmodel.RolesViewModel;
import com.example.isyncpos.viewmodel.SafekeepingDenominationViewModel;
import com.example.isyncpos.viewmodel.SafekeepingViewModel;
import com.example.isyncpos.viewmodel.SpotAuditDenominationViewModel;
import com.example.isyncpos.viewmodel.SpotAuditViewModel;
import com.example.isyncpos.viewmodel.SubCategoriesViewModel;
import com.example.isyncpos.viewmodel.SyncViewModel;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.example.isyncpos.viewmodel.UploadViewModel;
import com.example.isyncpos.viewmodel.UsersViewModel;

import io.socket.client.Socket;

public class POSApplication extends Application {

    //Others
    private static POSApplication instance;
    private UserAuthentication userAuthentication;
    private ServerUserAuthentication serverUserAuthentication;
    private MachineDetails machineDetails;
    private DeviceDetails deviceDetails;
    private Branch branch;
    private Company company;
    private int currentTransactionId = 0;
    private int currentARTransactionId = 0;
    private int sortDepartmentId = 0;
    private int lastUpdateProductId = 0;
    private BluetoothConnection[] bluetoothConnection;
    private int printerNBRCharacterLine = 45;
    private float printerWidthMM = 70f;
    private double totalQuantity = 1;

    //POS Process
    private POSProcess posProcess;
    private Backup backup;

    //View Models
    private POSApplicationViewModel posApplicationViewModel;
    private TransactionsViewModel transactionsViewModel;
    private OrdersViewModel ordersViewModel;
    private DepartmentsViewModel departmentsViewModel;
    private CategoriesViewModel categoriesViewModel;
    private SubCategoriesViewModel subCategoriesViewModel;
    private ProductsViewModel productsViewModel;
    private UsersViewModel usersViewModel;
    private PayoutsViewModel payoutsViewModel;
    private CashDenominationViewModel cashDenominationViewModel;
    private SafekeepingViewModel safekeepingViewModel;
    private SafekeepingDenominationViewModel safekeepingDenominationViewModel;
    private AuditTrailsViewModel auditTrailsViewModel;
    private DevicesViewModel devicesViewModel;
    private PrinterSetupViewModel printerSetupViewModel;
    private PrinterSetupDevicesViewModel printerSetupDevicesViewModel;
    private PaymentTypesViewModel paymentTypesViewModel;
    private PaymentsViewModel paymentsViewModel;
    private MachineDetailsViewModel machineDetailsViewModel;
    private AuthenticatedMachineUserViewModel authenticatedMachineUserViewModel;
    private BranchViewModel branchViewModel;
    private CompanyViewModel companyViewModel;
    private SyncViewModel syncViewModel;
    private DiscountTypesViewModel discountTypesViewModel;
    private ChargeAccountViewModel chargeAccountViewModel;
    private ProductsBundleItemViewModel productsBundleItemViewModel;
    private DeviceDetailsViewModel deviceDetailsViewModel;
    private DiscountsViewModel discountsViewModel;
    private DiscountDetailsViewModel discountDetailsViewModel;
    private CutOffViewModel cutOffViewModel;
    private EndOfDayViewModel endOfDayViewModel;
    private CutOffDepartmentsViewModel cutOffDepartmentsViewModel;
    private CutOffDiscountsViewModel cutOffDiscountsViewModel;
    private CutOffPaymentsViewModel cutOffPaymentsViewModel;
    private EndOfDayDepartmentsViewModel endOfDayDepartmentsViewModel;
    private EndOfDayDiscountsViewModel endOfDayDiscountsViewModel;
    private EndOfDayPaymentsViewModel endOfDayPaymentsViewModel;
    private CashFundViewModel cashFundViewModel;
    private CashFundDenominationViewModel cashFundDenominationViewModel;
    private DiscountTypeDepartmentsViewModel discountTypeDepartmentsViewModel;
    private DiscountTypeFieldsViewModel discountTypeFieldsViewModel;
    private DiscountTypeFieldOptionsViewModel discountTypeFieldOptionsViewModel;
    private PaymentTypeFieldsViewModel paymentTypeFieldsViewModel;
    private PaymentTypeFieldOptionsViewModel paymentTypeFieldOptionsViewModel;
    private PaymentOtherInformationsViewModel paymentOtherInformationsViewModel;
    private DiscountOtherInformationsViewModel discountOtherInformationsViewModel;
    private SpotAuditViewModel spotAuditViewModel;
    private SpotAuditDenominationViewModel spotAuditDenominationViewModel;
    private OfficialReceiptInformationViewModel officialReceiptInformationViewModel;
    private RolesViewModel rolesViewModel;
    private PermissionsViewModel permissionsViewModel;
    private UploadViewModel uploadViewModel;
    private ApplicationSettingsViewModel applicationSettingsViewModel;
    private CutOffProductsViewModel cutOffProductsViewModel;
    private EndOfDayProductsViewModel endOfDayProductsViewModel;
    private PriceChangeReasonViewModel priceChangeReasonViewModel;
    private ProductLocationsViewModel productLocationsViewModel;
    //Socket
    private Socket socket;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //This will set the default handler for the application and log or create the error text file
        Thread.setDefaultUncaughtExceptionHandler(new Exception(this));
    }

    public static POSApplication getInstance(){
        return instance;
    }

    public void setUserAuthentication(UserAuthentication userAuthentication){
        this.userAuthentication = userAuthentication;
    }

    public UserAuthentication getUserAuthentication(){
        return userAuthentication;
    }

    public Boolean checkUserAuthentication(){
        if(userAuthentication != null){
            return true;
        }
        else{
            return false;
        }
    }

    public void removeUserAuthentication(){
        userAuthentication = null;
    }

    public ServerUserAuthentication getServerUserAuthentication() {
        return serverUserAuthentication;
    }

    public void setServerUserAuthentication(ServerUserAuthentication serverUserAuthentication) {
        this.serverUserAuthentication = serverUserAuthentication;
    }

    public MachineDetails getMachineDetails() {
        return machineDetails;
    }

    public void setMachineDetails(MachineDetails machineDetails) {
        this.machineDetails = machineDetails;
    }

    public DeviceDetails getDeviceDetails() {
        return deviceDetails;
    }

    public void setDeviceDetails(DeviceDetails deviceDetails) {
        this.deviceDetails = deviceDetails;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getSortDepartmentId(){
        return sortDepartmentId;
    }

    public void setSortDepartmentId(int sortDepartmentId){
        this.sortDepartmentId = sortDepartmentId;
    }

    public void setCurrentTransaction(int currentTransactionId){
        this.currentTransactionId = currentTransactionId;
    }

    public int getCurrentTransaction(){
        return currentTransactionId;
    }

    public int getCurrentARTransactionId() {
        return currentARTransactionId;
    }

    public void setCurrentARTransactionId(int currentARTransactionId) {
        this.currentARTransactionId = currentARTransactionId;
    }

    public Boolean checkCurrentTransaction(){
        if(currentTransactionId != 0){
            return true;
        }
        else{
            return false;
        }
    }

    public int getLastUpdateProductId() {
        return lastUpdateProductId;
    }

    public void setLastUpdateProductId(int lastUpdateProductId) {
        this.lastUpdateProductId = lastUpdateProductId;
    }

    public BluetoothConnection[] getBluetoothConnection() {
        return bluetoothConnection;
    }

    public void setBluetoothConnection(BluetoothConnection[] bluetoothConnection) {
        this.bluetoothConnection = bluetoothConnection;
    }

    public int getPrinterNBRCharacterLine() {
        return printerNBRCharacterLine;
    }

    public void setPrinterNBRCharacterLine(int printerNBRCharacterLine) {
        this.printerNBRCharacterLine = printerNBRCharacterLine;
    }

    public float getPrinterWidthMM() {
        return printerWidthMM;
    }

    public void setPrinterWidthMM(float printerWidthMM) {
        this.printerWidthMM = printerWidthMM;
    }

    public POSProcess getPosProcess() {
        return posProcess;
    }

    public void setPosProcess(POSProcess posProcess) {
        this.posProcess = posProcess;
    }

    public POSApplicationViewModel getPosApplicationViewModel() {
        return posApplicationViewModel;
    }

    public void setPosApplicationViewModel(POSApplicationViewModel posApplicationViewModel) {
        this.posApplicationViewModel = posApplicationViewModel;
    }

    public TransactionsViewModel getTransactionsViewModel() {
        return transactionsViewModel;
    }

    public void setTransactionsViewModel(TransactionsViewModel transactionsViewModel) {
        this.transactionsViewModel = transactionsViewModel;
    }

    public OrdersViewModel getOrdersViewModel() {
        return ordersViewModel;
    }

    public void setOrdersViewModel(OrdersViewModel ordersViewModel) {
        this.ordersViewModel = ordersViewModel;
    }

    public DepartmentsViewModel getDepartmentsViewModel() {
        return departmentsViewModel;
    }

    public void setDepartmentsViewModel(DepartmentsViewModel departmentsViewModel) {
        this.departmentsViewModel = departmentsViewModel;
    }

    public CategoriesViewModel getCategoriesViewModel() {
        return categoriesViewModel;
    }

    public void setCategoriesViewModel(CategoriesViewModel categoriesViewModel) {
        this.categoriesViewModel = categoriesViewModel;
    }

    public SubCategoriesViewModel getSubCategoriesViewModel() {
        return subCategoriesViewModel;
    }

    public void setSubCategoriesViewModel(SubCategoriesViewModel subCategoriesViewModel) {
        this.subCategoriesViewModel = subCategoriesViewModel;
    }

    public ProductsViewModel getProductsViewModel() {
        return productsViewModel;
    }

    public void setProductsViewModel(ProductsViewModel productsViewModel) {
        this.productsViewModel = productsViewModel;
    }

    public UsersViewModel getUsersViewModel() {
        return usersViewModel;
    }

    public void setUsersViewModel(UsersViewModel usersViewModel) {
        this.usersViewModel = usersViewModel;
    }

    public PayoutsViewModel getPayoutsViewModel() {
        return payoutsViewModel;
    }

    public void setPayoutsViewModel(PayoutsViewModel payoutsViewModel) {
        this.payoutsViewModel = payoutsViewModel;
    }

    public CashDenominationViewModel getCashDenominationViewModel() {
        return cashDenominationViewModel;
    }

    public void setCashDenominationViewModel(CashDenominationViewModel cashDenominationViewModel) {
        this.cashDenominationViewModel = cashDenominationViewModel;
    }

    public SafekeepingViewModel getSafekeepingViewModel() {
        return safekeepingViewModel;
    }

    public void setSafekeepingViewModel(SafekeepingViewModel safekeepingViewModel) {
        this.safekeepingViewModel = safekeepingViewModel;
    }

    public SafekeepingDenominationViewModel getSafekeepingDenominationViewModel() {
        return safekeepingDenominationViewModel;
    }

    public void setSafekeepingDenominationViewModel(SafekeepingDenominationViewModel safekeepingDenominationViewModel) {
        this.safekeepingDenominationViewModel = safekeepingDenominationViewModel;
    }

    public AuditTrailsViewModel getAuditTrailsViewModel() {
        return auditTrailsViewModel;
    }

    public void setAuditTrailsViewModel(AuditTrailsViewModel auditTrailsViewModel) {
        this.auditTrailsViewModel = auditTrailsViewModel;
    }

    public DevicesViewModel getDevicesViewModel() {
        return devicesViewModel;
    }

    public void setDevicesViewModel(DevicesViewModel devicesViewModel) {
        this.devicesViewModel = devicesViewModel;
    }

    public PrinterSetupViewModel getPrinterSetupViewModel() {
        return printerSetupViewModel;
    }

    public void setPrinterSetupViewModel(PrinterSetupViewModel printerSetupViewModel) {
        this.printerSetupViewModel = printerSetupViewModel;
    }

    public PrinterSetupDevicesViewModel getPrinterSetupDevicesViewModel() {
        return printerSetupDevicesViewModel;
    }

    public void setPrinterSetupDevicesViewModel(PrinterSetupDevicesViewModel printerSetupDevicesViewModel) {
        this.printerSetupDevicesViewModel = printerSetupDevicesViewModel;
    }

    public PaymentTypesViewModel getPaymentTypesViewModel() {
        return paymentTypesViewModel;
    }

    public void setPaymentTypesViewModel(PaymentTypesViewModel paymentTypesViewModel) {
        this.paymentTypesViewModel = paymentTypesViewModel;
    }

    public PaymentsViewModel getPaymentsViewModel() {
        return paymentsViewModel;
    }

    public void setPaymentsViewModel(PaymentsViewModel paymentsViewModel) {
        this.paymentsViewModel = paymentsViewModel;
    }

    public MachineDetailsViewModel getMachineDetailsViewModel() {
        return machineDetailsViewModel;
    }

    public void setMachineDetailsViewModel(MachineDetailsViewModel machineDetailsViewModel) {
        this.machineDetailsViewModel = machineDetailsViewModel;
    }

    public AuthenticatedMachineUserViewModel getAuthenticatedMachineUserViewModel() {
        return authenticatedMachineUserViewModel;
    }

    public void setAuthenticatedMachineUserViewModel(AuthenticatedMachineUserViewModel authenticatedMachineUserViewModel) {
        this.authenticatedMachineUserViewModel = authenticatedMachineUserViewModel;
    }

    public BranchViewModel getBranchViewModel() {
        return branchViewModel;
    }

    public void setBranchViewModel(BranchViewModel branchViewModel) {
        this.branchViewModel = branchViewModel;
    }

    public CompanyViewModel getCompanyViewModel() {
        return companyViewModel;
    }

    public void setCompanyViewModel(CompanyViewModel companyViewModel) {
        this.companyViewModel = companyViewModel;
    }

    public SyncViewModel getSyncViewModel() {
        return syncViewModel;
    }

    public void setSyncViewModel(SyncViewModel syncViewModel) {
        this.syncViewModel = syncViewModel;
    }

    public DiscountTypesViewModel getDiscountTypesViewModel() {
        return discountTypesViewModel;
    }

    public void setDiscountTypesViewModel(DiscountTypesViewModel discountTypesViewModel) {
        this.discountTypesViewModel = discountTypesViewModel;
    }

    public ChargeAccountViewModel getChargeAccountViewModel() {
        return chargeAccountViewModel;
    }

    public void setChargeAccountViewModel(ChargeAccountViewModel chargeAccountViewModel) {
        this.chargeAccountViewModel = chargeAccountViewModel;
    }

    public ProductsBundleItemViewModel getProductsBundleItemViewModel() {
        return productsBundleItemViewModel;
    }

    public void setProductsBundleItemViewModel(ProductsBundleItemViewModel productsBundleItemViewModel) {
        this.productsBundleItemViewModel = productsBundleItemViewModel;
    }

    public DeviceDetailsViewModel getDeviceDetailsViewModel() {
        return deviceDetailsViewModel;
    }

    public void setDeviceDetailsViewModel(DeviceDetailsViewModel deviceDetailsViewModel) {
        this.deviceDetailsViewModel = deviceDetailsViewModel;
    }

    public DiscountsViewModel getDiscountsViewModel() {
        return discountsViewModel;
    }

    public void setDiscountsViewModel(DiscountsViewModel discountsViewModel) {
        this.discountsViewModel = discountsViewModel;
    }

    public DiscountDetailsViewModel getDiscountDetailsViewModel() {
        return discountDetailsViewModel;
    }

    public void setDiscountDetailsViewModel(DiscountDetailsViewModel discountDetailsViewModel) {
        this.discountDetailsViewModel = discountDetailsViewModel;
    }

    public CutOffViewModel getCutOffViewModel() {
        return cutOffViewModel;
    }

    public void setCutOffViewModel(CutOffViewModel cutOffViewModel) {
        this.cutOffViewModel = cutOffViewModel;
    }

    public EndOfDayViewModel getEndOfDayViewModel() {
        return endOfDayViewModel;
    }

    public void setEndOfDayViewModel(EndOfDayViewModel endOfDayViewModel) {
        this.endOfDayViewModel = endOfDayViewModel;
    }

    public CutOffDepartmentsViewModel getCutOffDepartmentsViewModel() {
        return cutOffDepartmentsViewModel;
    }

    public void setCutOffDepartmentsViewModel(CutOffDepartmentsViewModel cutOffDepartmentsViewModel) {
        this.cutOffDepartmentsViewModel = cutOffDepartmentsViewModel;
    }

    public CutOffDiscountsViewModel getCutOffDiscountsViewModel() {
        return cutOffDiscountsViewModel;
    }

    public void setCutOffDiscountsViewModel(CutOffDiscountsViewModel cutOffDiscountsViewModel) {
        this.cutOffDiscountsViewModel = cutOffDiscountsViewModel;
    }

    public CutOffPaymentsViewModel getCutOffPaymentsViewModel() {
        return cutOffPaymentsViewModel;
    }

    public void setCutOffPaymentsViewModel(CutOffPaymentsViewModel cutOffPaymentsViewModel) {
        this.cutOffPaymentsViewModel = cutOffPaymentsViewModel;
    }

    public EndOfDayDepartmentsViewModel getEndOfDayDepartmentsViewModel() {
        return endOfDayDepartmentsViewModel;
    }

    public void setEndOfDayDepartmentsViewModel(EndOfDayDepartmentsViewModel endOfDayDepartmentsViewModel) {
        this.endOfDayDepartmentsViewModel = endOfDayDepartmentsViewModel;
    }

    public EndOfDayDiscountsViewModel getEndOfDayDiscountsViewModel() {
        return endOfDayDiscountsViewModel;
    }

    public void setEndOfDayDiscountsViewModel(EndOfDayDiscountsViewModel endOfDayDiscountsViewModel) {
        this.endOfDayDiscountsViewModel = endOfDayDiscountsViewModel;
    }

    public EndOfDayPaymentsViewModel getEndOfDayPaymentsViewModel() {
        return endOfDayPaymentsViewModel;
    }

    public void setEndOfDayPaymentsViewModel(EndOfDayPaymentsViewModel endOfDayPaymentsViewModel) {
        this.endOfDayPaymentsViewModel = endOfDayPaymentsViewModel;
    }

    public CashFundViewModel getCashFundViewModel() {
        return cashFundViewModel;
    }

    public void setCashFundViewModel(CashFundViewModel cashFundViewModel) {
        this.cashFundViewModel = cashFundViewModel;
    }

    public CashFundDenominationViewModel getCashFundDenominationViewModel() {
        return cashFundDenominationViewModel;
    }

    public void setCashFundDenominationViewModel(CashFundDenominationViewModel cashFundDenominationViewModel) {
        this.cashFundDenominationViewModel = cashFundDenominationViewModel;
    }

    public DiscountTypeDepartmentsViewModel getDiscountTypeDepartmentsViewModel() {
        return discountTypeDepartmentsViewModel;
    }

    public void setDiscountTypeDepartmentsViewModel(DiscountTypeDepartmentsViewModel discountTypeDepartmentsViewModel) {
        this.discountTypeDepartmentsViewModel = discountTypeDepartmentsViewModel;
    }

    public DiscountTypeFieldsViewModel getDiscountTypeFieldsViewModel() {
        return discountTypeFieldsViewModel;
    }

    public void setDiscountTypeFieldsViewModel(DiscountTypeFieldsViewModel discountTypeFieldsViewModel) {
        this.discountTypeFieldsViewModel = discountTypeFieldsViewModel;
    }

    public DiscountTypeFieldOptionsViewModel getDiscountTypeFieldOptionsViewModel() {
        return discountTypeFieldOptionsViewModel;
    }

    public void setDiscountTypeFieldOptionsViewModel(DiscountTypeFieldOptionsViewModel discountTypeFieldOptionsViewModel) {
        this.discountTypeFieldOptionsViewModel = discountTypeFieldOptionsViewModel;
    }

    public PaymentTypeFieldsViewModel getPaymentTypeFieldsViewModel() {
        return paymentTypeFieldsViewModel;
    }

    public void setPaymentTypeFieldsViewModel(PaymentTypeFieldsViewModel paymentTypeFieldsViewModel) {
        this.paymentTypeFieldsViewModel = paymentTypeFieldsViewModel;
    }

    public PaymentTypeFieldOptionsViewModel getPaymentTypeFieldOptionsViewModel() {
        return paymentTypeFieldOptionsViewModel;
    }

    public void setPaymentTypeFieldOptionsViewModel(PaymentTypeFieldOptionsViewModel paymentTypeFieldOptionsViewModel) {
        this.paymentTypeFieldOptionsViewModel = paymentTypeFieldOptionsViewModel;
    }

    public PaymentOtherInformationsViewModel getPaymentOtherInformationsViewModel() {
        return paymentOtherInformationsViewModel;
    }

    public void setPaymentOtherInformationsViewModel(PaymentOtherInformationsViewModel paymentOtherInformationsViewModel) {
        this.paymentOtherInformationsViewModel = paymentOtherInformationsViewModel;
    }

    public DiscountOtherInformationsViewModel getDiscountOtherInformationsViewModel() {
        return discountOtherInformationsViewModel;
    }

    public void setDiscountOtherInformationsViewModel(DiscountOtherInformationsViewModel discountOtherInformationsViewModel) {
        this.discountOtherInformationsViewModel = discountOtherInformationsViewModel;
    }

    public SpotAuditViewModel getSpotAuditViewModel() {
        return spotAuditViewModel;
    }

    public void setSpotAuditViewModel(SpotAuditViewModel spotAuditViewModel) {
        this.spotAuditViewModel = spotAuditViewModel;
    }

    public SpotAuditDenominationViewModel getSpotAuditDenominationViewModel() {
        return spotAuditDenominationViewModel;
    }

    public void setSpotAuditDenominationViewModel(SpotAuditDenominationViewModel spotAuditDenominationViewModel) {
        this.spotAuditDenominationViewModel = spotAuditDenominationViewModel;
    }

    public OfficialReceiptInformationViewModel getOfficialReceiptInformationViewModel() {
        return officialReceiptInformationViewModel;
    }

    public void setOfficialReceiptInformationViewModel(OfficialReceiptInformationViewModel officialReceiptInformationViewModel) {
        this.officialReceiptInformationViewModel = officialReceiptInformationViewModel;
    }

    public RolesViewModel getRolesViewModel() {
        return rolesViewModel;
    }

    public void setRolesViewModel(RolesViewModel rolesViewModel) {
        this.rolesViewModel = rolesViewModel;
    }

    public PermissionsViewModel getPermissionsViewModel() {
        return permissionsViewModel;
    }

    public void setPermissionsViewModel(PermissionsViewModel permissionsViewModel) {
        this.permissionsViewModel = permissionsViewModel;
    }

    public UploadViewModel getUploadViewModel() {
        return uploadViewModel;
    }

    public void setUploadViewModel(UploadViewModel uploadViewModel) {
        this.uploadViewModel = uploadViewModel;
    }

    public ApplicationSettingsViewModel getApplicationSettingsViewModel() {
        return applicationSettingsViewModel;
    }

    public void setApplicationSettingsViewModel(ApplicationSettingsViewModel applicationSettingsViewModel) {
        this.applicationSettingsViewModel = applicationSettingsViewModel;
    }

    public CutOffProductsViewModel getCutOffProductsViewModel() {
        return cutOffProductsViewModel;
    }

    public void setCutOffProductsViewModel(CutOffProductsViewModel cutOffProductsViewModel) {
        this.cutOffProductsViewModel = cutOffProductsViewModel;
    }

    public EndOfDayProductsViewModel getEndOfDayProductsViewModel() {
        return endOfDayProductsViewModel;
    }

    public void setEndOfDayProductsViewModel(EndOfDayProductsViewModel endOfDayProductsViewModel) {
        this.endOfDayProductsViewModel = endOfDayProductsViewModel;
    }

    public PriceChangeReasonViewModel getPriceChangeReasonViewModel() {
        return priceChangeReasonViewModel;
    }

    public void setPriceChangeReasonViewModel(PriceChangeReasonViewModel priceChangeReasonViewModel) {
        this.priceChangeReasonViewModel = priceChangeReasonViewModel;
    }

    public ProductLocationsViewModel getProductLocationsViewModel() {
        return productLocationsViewModel;
    }

    public void setProductLocationsViewModel(ProductLocationsViewModel productLocationsViewModel) {
        this.productLocationsViewModel = productLocationsViewModel;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Backup getBackup() {
        return backup;
    }

    public void setBackup(Backup backup) {
        this.backup = backup;
    }

    public int getAvailableThreads(){
        return Runtime.getRuntime().availableProcessors();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

}
