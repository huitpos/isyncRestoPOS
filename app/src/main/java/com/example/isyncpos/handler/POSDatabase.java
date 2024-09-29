package com.example.isyncpos.handler;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.dao.ApplicationSettingsDAO;
import com.example.isyncpos.dao.AuditTrailsDAO;
import com.example.isyncpos.dao.AuthenticatedMachineUserDAO;
import com.example.isyncpos.dao.BranchDAO;
import com.example.isyncpos.dao.CashDenominationDAO;
import com.example.isyncpos.dao.CashFundDAO;
import com.example.isyncpos.dao.CashFundDenominationDAO;
import com.example.isyncpos.dao.CategoriesDAO;
import com.example.isyncpos.dao.ChargeAccountDAO;
import com.example.isyncpos.dao.CompanyDAO;
import com.example.isyncpos.dao.CutOffDAO;
import com.example.isyncpos.dao.CutOffDepartmentsDAO;
import com.example.isyncpos.dao.CutOffDiscountsDAO;
import com.example.isyncpos.dao.CutOffPaymentsDAO;
import com.example.isyncpos.dao.CutOffProductsDAO;
import com.example.isyncpos.dao.DepartmentsDAO;
import com.example.isyncpos.dao.DeviceDetailsDAO;
import com.example.isyncpos.dao.DevicesDAO;
import com.example.isyncpos.dao.DiscountDetailsDAO;
import com.example.isyncpos.dao.DiscountOtherInformationsDAO;
import com.example.isyncpos.dao.DiscountTypeDepartmentsDAO;
import com.example.isyncpos.dao.DiscountTypeFieldOptionsDAO;
import com.example.isyncpos.dao.DiscountTypeFieldsDAO;
import com.example.isyncpos.dao.DiscountTypesDAO;
import com.example.isyncpos.dao.DiscountsDAO;
import com.example.isyncpos.dao.EndOfDayDAO;
import com.example.isyncpos.dao.EndOfDayDepartmentsDAO;
import com.example.isyncpos.dao.EndOfDayDiscountsDAO;
import com.example.isyncpos.dao.EndOfDayPaymentsDAO;
import com.example.isyncpos.dao.EndOfDayProductsDAO;
import com.example.isyncpos.dao.MachineDetailsDAO;
import com.example.isyncpos.dao.OfficialReceiptInformationDAO;
import com.example.isyncpos.dao.OrdersDAO;
import com.example.isyncpos.dao.PaymentOtherInformationsDAO;
import com.example.isyncpos.dao.PaymentTypeFieldOptionsDAO;
import com.example.isyncpos.dao.PaymentTypeFieldsDAO;
import com.example.isyncpos.dao.PaymentTypesDAO;
import com.example.isyncpos.dao.PaymentsDAO;
import com.example.isyncpos.dao.PayoutsDAO;
import com.example.isyncpos.dao.PermissionsDAO;
import com.example.isyncpos.dao.PriceChangeReasonDAO;
import com.example.isyncpos.dao.PrinterSetupDAO;
import com.example.isyncpos.dao.PrinterSetupDevicesDAO;
import com.example.isyncpos.dao.ProductLocationsDAO;
import com.example.isyncpos.dao.ProductsBundleItemDAO;
import com.example.isyncpos.dao.ProductsDAO;
import com.example.isyncpos.dao.RolesDAO;
import com.example.isyncpos.dao.SafekeepingDAO;
import com.example.isyncpos.dao.SafekeepingDenominationDAO;
import com.example.isyncpos.dao.SpotAuditDAO;
import com.example.isyncpos.dao.SpotAuditDenominationDAO;
import com.example.isyncpos.dao.SubCategoriesDAO;
import com.example.isyncpos.dao.SyncDAO;
import com.example.isyncpos.dao.TransactionsDAO;
import com.example.isyncpos.dao.UploadDAO;
import com.example.isyncpos.dao.UsersDAO;
import com.example.isyncpos.entity.ApplicationSettings;
import com.example.isyncpos.entity.AuditTrails;
import com.example.isyncpos.entity.AuthenticatedMachineUser;
import com.example.isyncpos.entity.Branch;
import com.example.isyncpos.entity.CashDenomination;
import com.example.isyncpos.entity.CashFund;
import com.example.isyncpos.entity.CashFundDenomination;
import com.example.isyncpos.entity.Categories;
import com.example.isyncpos.entity.ChargeAccount;
import com.example.isyncpos.entity.Company;
import com.example.isyncpos.entity.CutOff;
import com.example.isyncpos.entity.CutOffDepartments;
import com.example.isyncpos.entity.CutOffDiscounts;
import com.example.isyncpos.entity.CutOffPayments;
import com.example.isyncpos.entity.CutOffProducts;
import com.example.isyncpos.entity.Departments;
import com.example.isyncpos.entity.DeviceDetails;
import com.example.isyncpos.entity.Devices;
import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.entity.DiscountTypeDepartments;
import com.example.isyncpos.entity.DiscountTypeFieldOptions;
import com.example.isyncpos.entity.DiscountTypeFields;
import com.example.isyncpos.entity.DiscountTypes;
import com.example.isyncpos.entity.DiscountDetails;
import com.example.isyncpos.entity.Discounts;
import com.example.isyncpos.entity.EndOfDay;
import com.example.isyncpos.entity.EndOfDayDepartments;
import com.example.isyncpos.entity.EndOfDayDiscounts;
import com.example.isyncpos.entity.EndOfDayPayments;
import com.example.isyncpos.entity.EndOffDayProducts;
import com.example.isyncpos.entity.MachineDetails;
import com.example.isyncpos.entity.OfficialReceiptInformation;
import com.example.isyncpos.entity.Orders;
import com.example.isyncpos.entity.PaymentOtherInformations;
import com.example.isyncpos.entity.PaymentTypeFieldOptions;
import com.example.isyncpos.entity.PaymentTypeFields;
import com.example.isyncpos.entity.PaymentTypes;
import com.example.isyncpos.entity.Payments;
import com.example.isyncpos.entity.Payouts;
import com.example.isyncpos.entity.Permissions;
import com.example.isyncpos.entity.PriceChangeReason;
import com.example.isyncpos.entity.PrinterSetup;
import com.example.isyncpos.entity.PrinterSetupDevices;
import com.example.isyncpos.entity.ProductLocations;
import com.example.isyncpos.entity.Products;
import com.example.isyncpos.entity.ProductsBundleItem;
import com.example.isyncpos.entity.Roles;
import com.example.isyncpos.entity.Safekeeping;
import com.example.isyncpos.entity.SafekeepingDenomination;
import com.example.isyncpos.entity.SpotAudit;
import com.example.isyncpos.entity.SpotAuditDenomination;
import com.example.isyncpos.entity.SubCategories;
import com.example.isyncpos.entity.Sync;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.entity.Upload;
import com.example.isyncpos.entity.Users;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {
    Users.class,
    Departments.class,
    Categories.class,
    SubCategories.class,
    Products.class,
    Transactions.class,
    Orders.class,
    Payouts.class,
    CashDenomination.class,
    Safekeeping.class,
    SafekeepingDenomination.class,
    AuditTrails.class,
    Devices.class,
    PrinterSetup.class,
    PrinterSetupDevices.class,
    PaymentTypes.class,
    Payments.class,
    MachineDetails.class,
    AuthenticatedMachineUser.class,
    Branch.class,
    Company.class,
    Sync.class,
    DiscountTypes.class,
    ChargeAccount.class,
    ProductsBundleItem.class,
    CutOff.class,
    EndOfDay.class,
    DeviceDetails.class,
    Discounts.class,
    DiscountDetails.class,
    ApplicationSettings.class,
    CutOffDepartments.class,
    CutOffDiscounts.class,
    CutOffPayments.class,
    EndOfDayDepartments.class,
    EndOfDayDiscounts.class,
    EndOfDayPayments.class,
    CashFund.class,
    CashFundDenomination.class,
    DiscountTypeDepartments.class,
    PaymentTypeFields.class,
    PaymentTypeFieldOptions.class,
    DiscountTypeFields.class,
    DiscountTypeFieldOptions.class,
    PaymentOtherInformations.class,
    DiscountOtherInformations.class,
    SpotAudit.class,
    SpotAuditDenomination.class,
    OfficialReceiptInformation.class,
    Roles.class,
    Permissions.class,
    Upload.class,
    CutOffProducts.class,
    EndOffDayProducts.class,
    PriceChangeReason.class,
    ProductLocations.class
}, version = 129)
public abstract class POSDatabase extends RoomDatabase {

    private static POSDatabase instance;

    public abstract UsersDAO usersDAO();
    public abstract DepartmentsDAO departmentsDAO();
    public abstract CategoriesDAO categoriesDAO();
    public abstract SubCategoriesDAO subCategoriesDAO();
    public abstract ProductsDAO productsDAO();
    public abstract TransactionsDAO transactionsDAO();
    public abstract OrdersDAO ordersDAO();
    public abstract PayoutsDAO payoutsDAO();
    public abstract CashDenominationDAO cashDenominationDAO();
    public abstract SafekeepingDAO safekeepingDAO();
    public abstract SafekeepingDenominationDAO safekeepingDenominationDAO();
    public abstract AuditTrailsDAO auditTrailsDAO();
    public abstract DevicesDAO devicesDAO();
    public abstract PrinterSetupDAO printerSetupDAO();
    public abstract PrinterSetupDevicesDAO printerSetupDevicesDAO();
    public abstract PaymentTypesDAO paymentTypesDAO();
    public abstract PaymentsDAO paymentsDAO();
    public abstract MachineDetailsDAO machineDetailsDAO();
    public abstract AuthenticatedMachineUserDAO authenticatedMachineUserDAO();
    public abstract BranchDAO branchDAO();
    public abstract CompanyDAO companyDAO();
    public abstract SyncDAO syncDAO();
    public abstract DiscountTypesDAO discountTypesDAO();
    public abstract ChargeAccountDAO chargeAccountDAO();
    public abstract ProductsBundleItemDAO productsBundleItemDAO();
    public abstract CutOffDAO cutOffDAO();
    public abstract EndOfDayDAO endOfDayDAO();
    public abstract DeviceDetailsDAO deviceDetailsDAO();
    public abstract DiscountsDAO discountsDAO();
    public abstract DiscountDetailsDAO discountDetailsDAO();
    public abstract ApplicationSettingsDAO applicationSettingsDAO();
    public abstract CutOffDepartmentsDAO cutOffDepartmentsDAO();
    public abstract CutOffDiscountsDAO cutOffDiscountsDAO();
    public abstract CutOffPaymentsDAO cutOffPaymentsDAO();
    public abstract EndOfDayDepartmentsDAO endOfDayDepartmentsDAO();
    public abstract EndOfDayDiscountsDAO endOfDayDiscountsDAO();
    public abstract EndOfDayPaymentsDAO endOfDayPaymentsDAO();
    public abstract CashFundDAO cashFundDAO();
    public abstract CashFundDenominationDAO cashFundDenominationDAO();
    public abstract DiscountTypeDepartmentsDAO discountTypeDepartmentsDAO();
    public abstract DiscountTypeFieldsDAO discountTypeFieldsDAO();
    public abstract DiscountTypeFieldOptionsDAO discountTypeFieldOptionsDAO();
    public abstract PaymentTypeFieldsDAO paymentTypeFieldsDAO();
    public abstract PaymentTypeFieldOptionsDAO paymentTypeFieldOptionsDAO();
    public abstract PaymentOtherInformationsDAO paymentOtherInformationsDAO();
    public abstract DiscountOtherInformationsDAO discountOtherInformationsDAO();
    public abstract SpotAuditDAO spotAuditDAO();
    public abstract SpotAuditDenominationDAO spotAuditDenominationDAO();
    public abstract OfficialReceiptInformationDAO officialReceiptInformationDAO();
    public abstract RolesDAO rolesDAO();
    public abstract PermissionsDAO permissionsDAO();
    public abstract UploadDAO uploadDAO();
    public abstract CutOffProductsDAO cutOffProductsDAO();
    public abstract EndOfDayProductsDAO endOfDayProductsDAO();
    public abstract PriceChangeReasonDAO priceChangeReasonDAO();
    public abstract ProductLocationsDAO productLocationsDAO();

    public static synchronized POSDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), POSDatabase.class, BuildConfig.APP_DATABASE)
                    .setJournalMode(JournalMode.WRITE_AHEAD_LOGGING)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomSyncCallback)
                    .addCallback(roomPrinterSetupCallback)
                    .addCallback(roomApplicationSettingsCallback)
                    .addCallback(roomUploadCallback)
                    .build();
        }
        return instance;
    }

    private static final RoomDatabase.Callback roomSyncCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(() -> {
                //doInBackground on AsyncTask
                seedSync seedSync = new seedSync(instance);
                seedSync.seed();
            });
        }
    };

    private static final RoomDatabase.Callback roomPrinterSetupCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(() -> {
                //doInBackground on AsyncTask
                seedPrinterSetup seedPrinterSetup = new seedPrinterSetup(instance);
                seedPrinterSetup.seed();
            });
        }
    };

    private static final RoomDatabase.Callback roomApplicationSettingsCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(() -> {
                //doInBackground on AsyncTask
                seedApplicationSettings seedApplicationSettings = new seedApplicationSettings(instance);
                seedApplicationSettings.seed();
            });
        }
    };

    private static final RoomDatabase.Callback roomUploadCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(() -> {
                //doInBackground on AsyncTask
                seedUpload seedUpload = new seedUpload(instance);
                seedUpload.seed();
            });
        }
    };

    private static class seedSync {

        private final SyncDAO syncDAO;
        private final Dates date = new Dates();

        private seedSync(POSDatabase posDatabase){
            syncDAO = posDatabase.syncDAO();
        }

        private void seed(){
            syncDAO.insert(new Sync("Cash Denominations", 0, 0, date.now(), ""));
            syncDAO.insert(new Sync("Departments", 0, 0, date.now(), ""));
            syncDAO.insert(new Sync("Categories", 0, 0, date.now(), ""));
            syncDAO.insert(new Sync("Sub Categories", 0,0, date.now(), ""));
            syncDAO.insert(new Sync("Users", 0, 0, date.now(), ""));
            syncDAO.insert(new Sync("Payment Type", 0, 0, date.now(), ""));
            syncDAO.insert(new Sync("Discount Type", 0, 0, date.now(), ""));
            syncDAO.insert(new Sync("Charge Account", 0, 0, date.now(), ""));
            syncDAO.insert(new Sync("Products", 0, 0, date.now(), ""));
            syncDAO.insert(new Sync("Price Change Reason", 0, 0, date.now(), ""));
            syncDAO.insert(new Sync("Branch Information", 1, 1, date.now(), date.now()));
            syncDAO.insert(new Sync("Company Information", 1, 1, date.now(), date.now()));
        }

    }

    private static class seedPrinterSetup {

        private final PrinterSetupDAO printerSetupDAO;
        private final Dates date = new Dates();

        private seedPrinterSetup(POSDatabase posDatabase){
            printerSetupDAO = posDatabase.printerSetupDAO();
        }

        private void seed(){
            printerSetupDAO.insert(new PrinterSetup("RESUME TRANSACTION", 0, 1, 0, "cashier", date.now()));
            printerSetupDAO.insert(new PrinterSetup("BACKOUT", 0, 1, 0, "both", date.now()));
            printerSetupDAO.insert(new PrinterSetup("PAYOUT", 0, 1, 0, "cashier", date.now()));
            printerSetupDAO.insert(new PrinterSetup("CASH FUND", 0, 1, 0, "cashier", date.now()));
            printerSetupDAO.insert(new PrinterSetup("STATEMENT OF ACCOUNT",0, 1, 0, "cashier", date.now()));
            printerSetupDAO.insert(new PrinterSetup("OFFICIAL RECEIPT", 0, 1, 1, "cashier", date.now()));
            printerSetupDAO.insert(new PrinterSetup("X READING", 0, 1, 0, "cashier", date.now()));
            printerSetupDAO.insert(new PrinterSetup("Z READING", 0, 1, 0, "cashier", date.now()));
            printerSetupDAO.insert(new PrinterSetup("PRODUCT LIST", 0, 1, 0, "cashier", date.now()));
            printerSetupDAO.insert(new PrinterSetup("POST VOID", 0, 1, 0, "cashier", date.now()));
            printerSetupDAO.insert(new PrinterSetup("ITEM VOID", 0, 1, 0, "both", date.now()));
            printerSetupDAO.insert(new PrinterSetup("REPRINT", 0, 1, 0, "cashier", date.now()));
            printerSetupDAO.insert(new PrinterSetup("SAFEKEEPING", 0, 1, 0, "cashier", date.now()));
            printerSetupDAO.insert(new PrinterSetup("SPOT AUDIT", 0, 1, 0, "cashier", date.now()));
            printerSetupDAO.insert(new PrinterSetup("TAKE ORDER RECEIPT", 0, 1, 0, "take order", date.now()));
            printerSetupDAO.insert(new PrinterSetup("REPRINT X READING", 0, 1, 0, "cashier", date.now()));
            printerSetupDAO.insert(new PrinterSetup("REPRINT Z READING", 0, 1, 0, "cashier", date.now()));
        }

    }

    private static class seedApplicationSettings {

        private final ApplicationSettingsDAO applicationSettingsDAO;

        private seedApplicationSettings(POSDatabase posDatabase){
            applicationSettingsDAO = posDatabase.applicationSettingsDAO();
        }

        private void seed(){
            applicationSettingsDAO.insert(new ApplicationSettings(0, 45, 70, 1, 0));
        }

    }

    private static class seedUpload {

        private final UploadDAO uploadDAO;

        private seedUpload(POSDatabase posDatabase){
            uploadDAO = posDatabase.uploadDAO();
        }

        private void seed(){
            uploadDAO.insert(new Upload("Transactions", "cashier"));
            uploadDAO.insert(new Upload("Orders", "cashier"));
            uploadDAO.insert(new Upload("Payments", "cashier"));
            uploadDAO.insert(new Upload("Machine Details", "cashier"));
            uploadDAO.insert(new Upload("Discounts", "cashier"));
            uploadDAO.insert(new Upload("Discount Details", "cashier"));
            uploadDAO.insert(new Upload("Cutoff", "cashier"));
            uploadDAO.insert(new Upload("End Of Day", "cashier"));
            uploadDAO.insert(new Upload("Audit Trails", "cashier"));
        }

    }

}
