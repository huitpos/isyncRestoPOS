package com.example.isyncpos;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.widget.Toast;

import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.example.isyncpos.adapters.CashDenominationAdapter;
import com.example.isyncpos.adapters.MenuItemsAdapter;
import com.example.isyncpos.adapters.OrderItemsAdapter;
import com.example.isyncpos.adapters.ResumeTransactionAdapter;
import com.example.isyncpos.adapters.SyncAdapter;
import com.example.isyncpos.adapters.TakeOrderAdapter;
import com.example.isyncpos.authentication.UserAuthentication;
import com.example.isyncpos.common.AuditTrail;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.Download;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.common.Printer;
import com.example.isyncpos.dialog.CashFundDialog;
import com.example.isyncpos.dialog.ChangePriceDialog;
import com.example.isyncpos.dialog.ChangeQuantityDialog;
import com.example.isyncpos.dialog.OpenPriceDialog;
import com.example.isyncpos.dialog.SerialNumberDialog;
import com.example.isyncpos.dialog.TransactionSyncDialog;
import com.example.isyncpos.entity.ApplicationSettings;
import com.example.isyncpos.entity.AuditTrails;
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
import com.example.isyncpos.entity.Devices;
import com.example.isyncpos.entity.DiscountDetails;
import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.entity.DiscountTypeDepartments;
import com.example.isyncpos.entity.DiscountTypeFieldOptions;
import com.example.isyncpos.entity.DiscountTypeFields;
import com.example.isyncpos.entity.DiscountTypes;
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
import com.example.isyncpos.entity.Permissions;
import com.example.isyncpos.entity.PriceChangeReason;
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
import com.example.isyncpos.entity.Users;
import com.example.isyncpos.fragment.Menu;
import com.example.isyncpos.fragment.Navigation;
import com.example.isyncpos.fragment.Order;
import com.example.isyncpos.preferences.Cache;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.receiver.BluetoothReceiver;
import com.example.isyncpos.receiver.WifiReceiver;
import com.example.isyncpos.response.entity.BranchInformationResponse;
import com.example.isyncpos.response.entity.CashDenominationResponse;
import com.example.isyncpos.response.entity.CategoriesResponse;
import com.example.isyncpos.response.entity.ChargeAccountResponse;
import com.example.isyncpos.response.entity.CompanyInformationResponse;
import com.example.isyncpos.response.entity.DepartmentsResponse;
import com.example.isyncpos.response.entity.DiscountTypesResponse;
import com.example.isyncpos.response.entity.PaymentTypesResponse;
import com.example.isyncpos.response.entity.PriceChangeReasonResponse;
import com.example.isyncpos.response.entity.ProductsResponse;
import com.example.isyncpos.response.entity.SubCategoriesResponse;
import com.example.isyncpos.response.entity.UsersResponse;
import com.example.isyncpos.screen.SecondScreenLoginPresentation;
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
import com.google.gson.Gson;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.socket.client.IO;
import io.socket.client.Socket;

public class MainActivity extends AppCompatActivity implements MenuItemsAdapter.MenuItemListener, ResumeTransactionAdapter.ResumeTransactionListener, OrderItemsAdapter.OrderItemListener, CashDenominationAdapter.CashDenominationListener, SyncAdapter.SyncListener, TakeOrderAdapter.TakeOrderItemListener {

    private POSProcess posProcess;
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
    private SyncViewModel syncViewModel;
    private BranchViewModel branchViewModel;
    private CompanyViewModel companyViewModel;
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
    private PaymentTypeFieldsViewModel paymentTypeFieldsViewModel;
    private PaymentTypeFieldOptionsViewModel paymentTypeFieldOptionsViewModel;
    private DiscountTypeFieldsViewModel discountTypeFieldsViewModel;
    private DiscountTypeFieldOptionsViewModel discountTypeFieldOptionsViewModel;
    private DiscountOtherInformationsViewModel discountOtherInformationsViewModel;
    private PaymentOtherInformationsViewModel paymentOtherInformationsViewModel;
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
    private Generate generate;
    private Dates date;
    private Cache cache;
    private Gson gson;
    private String scanBarcode = "";
    private Timer syncTransactionTimer;
    private Timer syncOrdersTimer;
    private Timer syncPaymentsTimer;
    private Timer syncMachineDetailsTimer;
    private Timer syncDiscountTimer;
    private Timer syncDiscountDetailsTimer;
    private Timer syncCutOffDetailsTimer;
    private Timer syncEndOfDayDetailsTimer;
    private Timer syncSafeKeepingDetailsTimer;
    private Timer syncSafekeepingDenominationTimer;
    private Timer syncPaymentOtherInformationsTimer;
    private Timer syncDiscountOtherInformationsTimer;
    private Timer syncCutOffDepartmentsTimer;
    private Timer syncCutOffDiscountsTimer;
    private Timer syncCutOffPaymentsTimer;
    private Timer syncEndOfDayDepartmentsTimer;
    private Timer syncEndOfDayDiscountsTimer;
    private Timer syncEndOfDayPaymentsTimer;
    private Timer syncCashFundTimer;
    private Timer syncCashFundDenominationTimer;
    private Timer syncAuditTrailsTimer;
    private Timer syncOfficialReceiptInformationTimer;
    private Timer syncSpotAuditTimer;
    private Timer syncSpotAuditDenominationTimer;
    private Timer syncCutOffProductTimer;
    private Menu menu;
    private Order order;
    private Navigation navigation;
    private WifiReceiver wifiReceiver;
    private BluetoothReceiver bluetoothReceiver;
//    private SecondScreenMainPresentation secondScreenMainPresentation;
    private SecondScreenLoginPresentation secondScreenLoginPresentation;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize View Models
        initViewModels();
        //Initialize Fragments
        initFragments();
        //Initialize POSProcess
        initPOSProcess();

        //Initialize
        initialize();
        //Initialize Broadcast
        initBroadcast();

        //Check First Install
        firstInstall();

        checkForCashFunds();
        checkHasCurrentTransaction();

        initSyncToServer();
        initMainSecondScreen();
        initWebSocket();

        //This will override the back button on the phone for your app
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                //Leave empty for no action
            }
        });

        requestPermissionUSB();
        requestPermissionBluetooth();

        //Set Printer Args
        Printer.getInstance().setDevicesViewModel(devicesViewModel);
        Printer.getInstance().setPaymentOtherInformationsViewModel(paymentOtherInformationsViewModel);
        Printer.getInstance().setOfficialReceiptInformationViewModel(officialReceiptInformationViewModel);
        Printer.getInstance().setDiscountOtherInformationsViewModel(discountOtherInformationsViewModel);
        Printer.getInstance().setPrinterSetupViewModel(printerSetupViewModel);

    }


    private void initialize(){
        generate = new Generate();
        date = new Dates();
        cache = new Cache(getApplication());
        gson = new Gson();
    }

    private void initFragments(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        navigation = Navigation.newNavigation();
        fragmentTransaction.replace(R.id.fragmentNavigation, navigation);
        //Set Menus
        order = Order.newOrder();
        menu = Menu.newMenu();
        //Set args
        menu.setArgs(order);
        //Set Fragments
        fragmentTransaction.replace(R.id.fragmentMenu, menu);
        fragmentTransaction.replace(R.id.fragmentOrder, order);
        fragmentTransaction.commit();
    }

    private void initViewModels(){
        posApplicationViewModel = new ViewModelProvider(this).get(POSApplicationViewModel.class);
        transactionsViewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);
        ordersViewModel = new ViewModelProvider(this).get(OrdersViewModel.class);
        departmentsViewModel = new ViewModelProvider(this).get(DepartmentsViewModel.class);
        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        subCategoriesViewModel = new ViewModelProvider(this).get(SubCategoriesViewModel.class);
        productsViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        payoutsViewModel = new ViewModelProvider(this).get(PayoutsViewModel.class);
        cashDenominationViewModel = new ViewModelProvider(this).get(CashDenominationViewModel.class);
        safekeepingViewModel = new ViewModelProvider(this).get(SafekeepingViewModel.class);
        safekeepingDenominationViewModel = new ViewModelProvider(this).get(SafekeepingDenominationViewModel.class);
        auditTrailsViewModel = new ViewModelProvider(this).get(AuditTrailsViewModel.class);
        devicesViewModel = new ViewModelProvider(this).get(DevicesViewModel.class);
        printerSetupViewModel = new ViewModelProvider(this).get(PrinterSetupViewModel.class);
        printerSetupDevicesViewModel = new ViewModelProvider(this).get(PrinterSetupDevicesViewModel.class);
        paymentTypesViewModel = new ViewModelProvider(this).get(PaymentTypesViewModel.class);
        paymentsViewModel = new ViewModelProvider(this).get(PaymentsViewModel.class);
        machineDetailsViewModel = new ViewModelProvider(this).get(MachineDetailsViewModel.class);
        authenticatedMachineUserViewModel = new ViewModelProvider(this).get(AuthenticatedMachineUserViewModel.class);
        branchViewModel = new ViewModelProvider(this).get(BranchViewModel.class);
        companyViewModel = new ViewModelProvider(this).get(CompanyViewModel.class);
        syncViewModel = new ViewModelProvider(this).get(SyncViewModel.class);
        discountTypesViewModel = new ViewModelProvider(this).get(DiscountTypesViewModel.class);
        chargeAccountViewModel = new ViewModelProvider(this).get(ChargeAccountViewModel.class);
        productsBundleItemViewModel = new ViewModelProvider(this).get(ProductsBundleItemViewModel.class);
        deviceDetailsViewModel = new ViewModelProvider(this).get(DeviceDetailsViewModel.class);
        discountsViewModel = new ViewModelProvider(this).get(DiscountsViewModel.class);
        discountDetailsViewModel = new ViewModelProvider(this).get(DiscountDetailsViewModel.class);
        cutOffViewModel = new ViewModelProvider(this).get(CutOffViewModel.class);
        endOfDayViewModel = new ViewModelProvider(this).get(EndOfDayViewModel.class);
        cutOffDepartmentsViewModel = new ViewModelProvider(this).get(CutOffDepartmentsViewModel.class);
        cutOffDiscountsViewModel = new ViewModelProvider(this).get(CutOffDiscountsViewModel.class);
        cutOffPaymentsViewModel = new ViewModelProvider(this).get(CutOffPaymentsViewModel.class);
        endOfDayDepartmentsViewModel = new ViewModelProvider(this).get(EndOfDayDepartmentsViewModel.class);
        endOfDayDiscountsViewModel = new ViewModelProvider(this).get(EndOfDayDiscountsViewModel.class);
        endOfDayPaymentsViewModel = new ViewModelProvider(this).get(EndOfDayPaymentsViewModel.class);
        cashFundViewModel = new ViewModelProvider(this).get(CashFundViewModel.class);
        cashFundDenominationViewModel = new ViewModelProvider(this).get(CashFundDenominationViewModel.class);
        discountTypeDepartmentsViewModel = new ViewModelProvider(this).get(DiscountTypeDepartmentsViewModel.class);
        paymentTypeFieldsViewModel = new ViewModelProvider(this).get(PaymentTypeFieldsViewModel.class);
        paymentTypeFieldOptionsViewModel = new ViewModelProvider(this).get(PaymentTypeFieldOptionsViewModel.class);
        discountTypeFieldsViewModel = new ViewModelProvider(this).get(DiscountTypeFieldsViewModel.class);
        discountTypeFieldOptionsViewModel = new ViewModelProvider(this).get(DiscountTypeFieldOptionsViewModel.class);
        paymentOtherInformationsViewModel = new ViewModelProvider(this).get(PaymentOtherInformationsViewModel.class);
        discountOtherInformationsViewModel = new ViewModelProvider(this).get(DiscountOtherInformationsViewModel.class);
        spotAuditViewModel = new ViewModelProvider(this).get(SpotAuditViewModel.class);
        spotAuditDenominationViewModel = new ViewModelProvider(this).get(SpotAuditDenominationViewModel.class);
        officialReceiptInformationViewModel = new ViewModelProvider(this).get(OfficialReceiptInformationViewModel.class);
        rolesViewModel = new ViewModelProvider(this).get(RolesViewModel.class);
        permissionsViewModel = new ViewModelProvider(this).get(PermissionsViewModel.class);
        uploadViewModel = new ViewModelProvider(this).get(UploadViewModel.class);
        applicationSettingsViewModel = new ViewModelProvider(this).get(ApplicationSettingsViewModel.class);
        cutOffProductsViewModel = new ViewModelProvider(this).get(CutOffProductsViewModel.class);
        endOfDayProductsViewModel = new ViewModelProvider(this).get(EndOfDayProductsViewModel.class);
        priceChangeReasonViewModel = new ViewModelProvider(this).get(PriceChangeReasonViewModel.class);
        productLocationsViewModel = new ViewModelProvider(this).get(ProductLocationsViewModel.class);
        POSApplication.getInstance().setPosApplicationViewModel(posApplicationViewModel);
        POSApplication.getInstance().setTransactionsViewModel(transactionsViewModel);
        POSApplication.getInstance().setOrdersViewModel(ordersViewModel);
        POSApplication.getInstance().setDepartmentsViewModel(departmentsViewModel);
        POSApplication.getInstance().setCategoriesViewModel(categoriesViewModel);
        POSApplication.getInstance().setSubCategoriesViewModel(subCategoriesViewModel);
        POSApplication.getInstance().setProductsViewModel(productsViewModel);
        POSApplication.getInstance().setUsersViewModel(usersViewModel);
        POSApplication.getInstance().setPayoutsViewModel(payoutsViewModel);
        POSApplication.getInstance().setCashDenominationViewModel(cashDenominationViewModel);
        POSApplication.getInstance().setSafekeepingViewModel(safekeepingViewModel);
        POSApplication.getInstance().setSafekeepingDenominationViewModel(safekeepingDenominationViewModel);
        POSApplication.getInstance().setAuditTrailsViewModel(auditTrailsViewModel);
        POSApplication.getInstance().setDevicesViewModel(devicesViewModel);
        POSApplication.getInstance().setPrinterSetupViewModel(printerSetupViewModel);
        POSApplication.getInstance().setPrinterSetupDevicesViewModel(printerSetupDevicesViewModel);
        POSApplication.getInstance().setPaymentTypesViewModel(paymentTypesViewModel);
        POSApplication.getInstance().setPaymentsViewModel(paymentsViewModel);
        POSApplication.getInstance().setMachineDetailsViewModel(machineDetailsViewModel);
        POSApplication.getInstance().setAuthenticatedMachineUserViewModel(authenticatedMachineUserViewModel);
        POSApplication.getInstance().setBranchViewModel(branchViewModel);
        POSApplication.getInstance().setCompanyViewModel(companyViewModel);
        POSApplication.getInstance().setSyncViewModel(syncViewModel);
        POSApplication.getInstance().setDiscountTypesViewModel(discountTypesViewModel);
        POSApplication.getInstance().setChargeAccountViewModel(chargeAccountViewModel);
        POSApplication.getInstance().setProductsBundleItemViewModel(productsBundleItemViewModel);
        POSApplication.getInstance().setDeviceDetailsViewModel(deviceDetailsViewModel);
        POSApplication.getInstance().setDiscountsViewModel(discountsViewModel);
        POSApplication.getInstance().setDiscountDetailsViewModel(discountDetailsViewModel);
        POSApplication.getInstance().setCutOffViewModel(cutOffViewModel);
        POSApplication.getInstance().setEndOfDayViewModel(endOfDayViewModel);
        POSApplication.getInstance().setCutOffDepartmentsViewModel(cutOffDepartmentsViewModel);
        POSApplication.getInstance().setCutOffDiscountsViewModel(cutOffDiscountsViewModel);
        POSApplication.getInstance().setCutOffPaymentsViewModel(cutOffPaymentsViewModel);
        POSApplication.getInstance().setEndOfDayDepartmentsViewModel(endOfDayDepartmentsViewModel);
        POSApplication.getInstance().setEndOfDayDiscountsViewModel(endOfDayDiscountsViewModel);
        POSApplication.getInstance().setEndOfDayPaymentsViewModel(endOfDayPaymentsViewModel);
        POSApplication.getInstance().setCashFundViewModel(cashFundViewModel);
        POSApplication.getInstance().setCashFundDenominationViewModel(cashFundDenominationViewModel);
        POSApplication.getInstance().setDiscountTypeDepartmentsViewModel(discountTypeDepartmentsViewModel);
        POSApplication.getInstance().setPaymentTypeFieldsViewModel(paymentTypeFieldsViewModel);
        POSApplication.getInstance().setPaymentTypeFieldOptionsViewModel(paymentTypeFieldOptionsViewModel);
        POSApplication.getInstance().setDiscountTypeFieldsViewModel(discountTypeFieldsViewModel);
        POSApplication.getInstance().setDiscountTypeFieldOptionsViewModel(discountTypeFieldOptionsViewModel);
        POSApplication.getInstance().setPaymentOtherInformationsViewModel(paymentOtherInformationsViewModel);
        POSApplication.getInstance().setDiscountOtherInformationsViewModel(discountOtherInformationsViewModel);
        POSApplication.getInstance().setSpotAuditViewModel(spotAuditViewModel);
        POSApplication.getInstance().setSpotAuditDenominationViewModel(spotAuditDenominationViewModel);
        POSApplication.getInstance().setOfficialReceiptInformationViewModel(officialReceiptInformationViewModel);
        POSApplication.getInstance().setRolesViewModel(rolesViewModel);
        POSApplication.getInstance().setPermissionsViewModel(permissionsViewModel);
        POSApplication.getInstance().setUploadViewModel(uploadViewModel);
        POSApplication.getInstance().setApplicationSettingsViewModel(applicationSettingsViewModel);
        POSApplication.getInstance().setCutOffProductsViewModel(cutOffProductsViewModel);
        POSApplication.getInstance().setEndOfDayProductsViewModel(endOfDayProductsViewModel);
        POSApplication.getInstance().setPriceChangeReasonViewModel(priceChangeReasonViewModel);
        POSApplication.getInstance().setProductLocationsViewModel(productLocationsViewModel);
    }

    private void initPOSProcess(){
        posProcess = new POSProcess(
                transactionsViewModel,
                ordersViewModel,
                departmentsViewModel,
                categoriesViewModel,
                subCategoriesViewModel,
                auditTrailsViewModel,
                paymentsViewModel,
                machineDetailsViewModel,
                printerSetupDevicesViewModel,
                discountsViewModel,
                discountDetailsViewModel,
                cutOffViewModel,
                endOfDayViewModel,
                payoutsViewModel,
                safekeepingViewModel,
                cutOffDepartmentsViewModel,
                cutOffDiscountsViewModel,
                cutOffPaymentsViewModel,
                safekeepingDenominationViewModel,
                endOfDayDepartmentsViewModel,
                endOfDayDiscountsViewModel,
                endOfDayPaymentsViewModel,
                cashFundViewModel,
                cashFundDenominationViewModel,
                paymentOtherInformationsViewModel,
                discountOtherInformationsViewModel,
                spotAuditViewModel,
                spotAuditDenominationViewModel,
                discountTypeDepartmentsViewModel,
                menu,
                cutOffProductsViewModel,
                endOfDayProductsViewModel,
                permissionsViewModel,
                navigation,
                officialReceiptInformationViewModel,
                paymentTypesViewModel
        );
        POSApplication.getInstance().setPosProcess(posProcess);
    }

    private void initSyncToServer(){
        transactionsViewModel.fetchCompleteTransactionToSync().observe(this, new Observer<List<Transactions>>() {
            @Override
            public void onChanged(List<Transactions> transactions) {
                if(syncTransactionTimer != null){
                    syncTransactionTimer.cancel();
                }
                syncTransactionTimer = new Timer();
                syncTransactionTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                        for(Transactions item : transactions){
                            try {
                                //This will check if the transaction is already completed and change the data to pass to the server
                                Transactions transactionsComplete = transactionsViewModel.fetchTransaction(item.getId());
                                if(transactionsComplete.getIsComplete() == 1){
                                    executorService.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            posProcess.SyncTransactionToServer(transactionsComplete);
                                        }
                                    });
                                }
                                else{
                                    executorService.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            posProcess.SyncTransactionToServer(item);
                                        }
                                    });
                                }
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        ordersViewModel.fetchCompleteOrderToSync().observe(this, new Observer<List<Orders>>() {
            @Override
            public void onChanged(List<Orders> orders) {
                if(syncOrdersTimer != null){
                    syncOrdersTimer.cancel();
                }
                syncOrdersTimer = new Timer();
                syncOrdersTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                        for(Orders item : orders){
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncOrderToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        paymentsViewModel.fetchCompletePaymentToSync().observe(this, new Observer<List<Payments>>() {
            @Override
            public void onChanged(List<Payments> payments) {
                if(syncPaymentsTimer != null){
                    syncPaymentsTimer.cancel();
                }
                syncPaymentsTimer = new Timer();
                syncPaymentsTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                        for(Payments item : payments){
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncPaymentToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        machineDetailsViewModel.fetchMachineDetailsToSync().observe(this, new Observer<MachineDetails>() {
            @Override
            public void onChanged(MachineDetails machineDetails) {
                if(machineDetails != null){
                    if(syncMachineDetailsTimer != null){
                        syncMachineDetailsTimer.cancel();
                    }
                    syncMachineDetailsTimer = new Timer();
                    syncMachineDetailsTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncMachineDetailsToServer(machineDetails);
                                }
                            });
                        }
                    }, BuildConfig.APP_DEFAULT_SYNC_TIME);
                }
            }
        });
        discountsViewModel.fetchCompleteDiscountsToSync().observe(this, new Observer<List<Discounts>>() {
            @Override
            public void onChanged(List<Discounts> discounts) {
                if(syncDiscountTimer != null){
                    syncDiscountTimer.cancel();
                }
                syncDiscountTimer = new Timer();
                syncDiscountTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                        for(Discounts item : discounts){
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncDiscountToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        discountDetailsViewModel.fetchCompleteDiscountDetailsToSync().observe(this, new Observer<List<DiscountDetails>>() {
            @Override
            public void onChanged(List<DiscountDetails> discountDetails) {
                if(syncDiscountDetailsTimer != null){
                    syncDiscountDetailsTimer.cancel();
                }
                syncDiscountDetailsTimer = new Timer();
                syncDiscountDetailsTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                        for(DiscountDetails item : discountDetails){
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncDiscountDetailsToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        cutOffViewModel.fetchCompleteCutOffToSync().observe(this, new Observer<List<CutOff>>() {
            @Override
            public void onChanged(List<CutOff> cutOffs) {
                if(syncCutOffDetailsTimer != null){
                    syncCutOffDetailsTimer.cancel();
                }
                syncCutOffDetailsTimer = new Timer();
                syncCutOffDetailsTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                        for(CutOff item : cutOffs){
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncCutOffDetailsToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        endOfDayViewModel.fetchCompleteEndOfDayToSync().observe(this, new Observer<List<EndOfDay>>() {
            @Override
            public void onChanged(List<EndOfDay> endOfDays) {
                if(syncEndOfDayDetailsTimer != null){
                    syncEndOfDayDetailsTimer.cancel();
                }
                syncEndOfDayDetailsTimer = new Timer();
                syncEndOfDayDetailsTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                        for(EndOfDay item : endOfDays){
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        List<EndOffDayProducts> endOffDayProducts = new ArrayList<>();
                                        if(item.getIsComplete() == 1){
                                            endOffDayProducts = endOfDayProductsViewModel.fetchByEndOfDayId(item.getId());
                                        }
                                        posProcess.SyncEndOfDayDetailsToServer(item, endOffDayProducts);
                                    } catch (ExecutionException e) {
                                        throw new RuntimeException(e);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        safekeepingViewModel.fetchCompleteSafekeepingToSync().observe(this, new Observer<List<Safekeeping>>() {
            @Override
            public void onChanged(List<Safekeeping> safekeepings) {
                if(syncSafeKeepingDetailsTimer != null){
                    syncSafeKeepingDetailsTimer.cancel();
                }
                syncSafeKeepingDetailsTimer = new Timer();
                syncSafeKeepingDetailsTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                        for(Safekeeping item : safekeepings){
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncSafekeepingDetailsToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        safekeepingDenominationViewModel.fetchCompleteSafekeepingDenominationToSync().observe(this, new Observer<List<SafekeepingDenomination>>() {
            @Override
            public void onChanged(List<SafekeepingDenomination> safekeepingDenominations) {
                if(syncSafekeepingDenominationTimer != null){
                    syncSafekeepingDenominationTimer.cancel();
                }
                syncSafekeepingDenominationTimer = new Timer();
                syncSafekeepingDenominationTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                        for(SafekeepingDenomination item : safekeepingDenominations){
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncSafekeepingDenominationDetailsToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        paymentOtherInformationsViewModel.fetchCompletePaymentOtherInformationsToSync().observe(this, new Observer<List<PaymentOtherInformations>>() {
            @Override
            public void onChanged(List<PaymentOtherInformations> paymentOtherInformations) {
                if(syncPaymentOtherInformationsTimer != null){
                    syncPaymentOtherInformationsTimer.cancel();
                }
                syncPaymentOtherInformationsTimer = new Timer();
                syncPaymentOtherInformationsTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for(PaymentOtherInformations item : paymentOtherInformations){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncPaymentOtherInformationDetailsToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        discountOtherInformationsViewModel.fetchCompleteDiscountOtherInformationsToSync().observe(this, new Observer<List<DiscountOtherInformations>>() {
            @Override
            public void onChanged(List<DiscountOtherInformations> discountOtherInformations) {
                if(syncDiscountOtherInformationsTimer != null){
                    syncDiscountOtherInformationsTimer.cancel();
                }
                syncDiscountOtherInformationsTimer = new Timer();
                syncDiscountOtherInformationsTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for(DiscountOtherInformations item : discountOtherInformations){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncDiscountOtherInformationDetailsToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        cutOffDepartmentsViewModel.fetchCompleteCutOffDepartmentsToSync().observe(this, new Observer<List<CutOffDepartments>>() {
            @Override
            public void onChanged(List<CutOffDepartments> cutOffDepartments) {
                if(syncCutOffDepartmentsTimer != null){
                    syncCutOffDepartmentsTimer.cancel();
                }
                syncCutOffDepartmentsTimer = new Timer();
                syncCutOffDepartmentsTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for(CutOffDepartments item : cutOffDepartments){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncCutOffDepartmentsDetailsToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        cutOffDiscountsViewModel.fetchCompleteCutOffDiscountsToSync().observe(this, new Observer<List<CutOffDiscounts>>() {
            @Override
            public void onChanged(List<CutOffDiscounts> cutOffDiscounts) {
                if(syncCutOffDiscountsTimer != null){
                    syncCutOffDiscountsTimer.cancel();
                }
                syncCutOffDiscountsTimer = new Timer();
                syncCutOffDiscountsTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for(CutOffDiscounts item : cutOffDiscounts){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncCutOffDiscountsDetailsToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        cutOffPaymentsViewModel.fetchCompleteCutOffPaymentsToSync().observe(this, new Observer<List<CutOffPayments>>() {
            @Override
            public void onChanged(List<CutOffPayments> cutOffPayments) {
                if(syncCutOffPaymentsTimer != null){
                    syncCutOffPaymentsTimer.cancel();
                }
                syncCutOffPaymentsTimer = new Timer();
                syncCutOffPaymentsTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for(CutOffPayments item : cutOffPayments){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncCutOffPaymentsDetailsToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        endOfDayDiscountsViewModel.fetchCompleteEndOfDayDiscountsToSync().observe(this, new Observer<List<EndOfDayDiscounts>>() {
            @Override
            public void onChanged(List<EndOfDayDiscounts> endOfDayDiscounts) {
                if(syncEndOfDayDiscountsTimer != null){
                    syncEndOfDayDiscountsTimer.cancel();
                }
                syncEndOfDayDiscountsTimer = new Timer();
                syncEndOfDayDiscountsTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for(EndOfDayDiscounts item : endOfDayDiscounts){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncEndOfDayDiscountsDetailsToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        endOfDayPaymentsViewModel.fetchCompleteEndOfDayPaymentsToSync().observe(this, new Observer<List<EndOfDayPayments>>() {
            @Override
            public void onChanged(List<EndOfDayPayments> endOfDayPayments) {
                if(syncEndOfDayPaymentsTimer != null){
                    syncEndOfDayPaymentsTimer.cancel();
                }
                syncEndOfDayPaymentsTimer = new Timer();
                syncEndOfDayPaymentsTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for(EndOfDayPayments item : endOfDayPayments){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncEndOfDayPaymentsDetailsToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        endOfDayDepartmentsViewModel.fetchCompleteEndOfDayDepartmentsToSync().observe(this, new Observer<List<EndOfDayDepartments>>() {
            @Override
            public void onChanged(List<EndOfDayDepartments> endOfDayDepartments) {
                if(syncEndOfDayDepartmentsTimer != null){
                    syncEndOfDayDepartmentsTimer.cancel();
                }
                syncEndOfDayDepartmentsTimer = new Timer();
                syncEndOfDayDepartmentsTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for(EndOfDayDepartments item : endOfDayDepartments){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncEndOfDayDepartmentsDetailsToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        cashFundViewModel.fetchCompleteCashFundToSync().observe(this, new Observer<List<CashFund>>() {
            @Override
            public void onChanged(List<CashFund> cashFunds) {
                if(syncCashFundTimer != null){
                    syncCashFundTimer.cancel();
                }
                syncCashFundTimer = new Timer();
                syncCashFundTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for(CashFund item : cashFunds){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncCashFundsToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        cashFundDenominationViewModel.fetchCompleteCashFundDenominationToSync().observe(this, new Observer<List<CashFundDenomination>>() {
            @Override
            public void onChanged(List<CashFundDenomination> cashFundDenominations) {
                if(syncCashFundDenominationTimer != null){
                    syncCashFundDenominationTimer.cancel();
                }
                syncCashFundDenominationTimer = new Timer();
                syncCashFundDenominationTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for(CashFundDenomination item : cashFundDenominations){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncCashFundDenominationsToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        auditTrailsViewModel.fetchCompleteAuditTrailsToSync().observe(this, new Observer<List<AuditTrails>>() {
            @Override
            public void onChanged(List<AuditTrails> auditTrails) {
                if(syncAuditTrailsTimer != null){
                    syncAuditTrailsTimer.cancel();
                }
                syncAuditTrailsTimer = new Timer();
                syncAuditTrailsTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for(AuditTrails item : auditTrails){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncAuditTrailsToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        officialReceiptInformationViewModel.fetchCompleteOfficialReceiptInformationToSync().observe(this, new Observer<List<OfficialReceiptInformation>>() {
            @Override
            public void onChanged(List<OfficialReceiptInformation> officialReceiptInformations) {
                if(syncOfficialReceiptInformationTimer != null){
                    syncOfficialReceiptInformationTimer.cancel();
                }
                syncOfficialReceiptInformationTimer = new Timer();
                syncOfficialReceiptInformationTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for(OfficialReceiptInformation item : officialReceiptInformations){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncOfficialReceiptInformationToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        spotAuditViewModel.fetchCompleteSpotAuditToSync().observe(this, new Observer<List<SpotAudit>>() {
            @Override
            public void onChanged(List<SpotAudit> spotAudits) {
                if(syncSpotAuditTimer != null){
                    syncSpotAuditTimer.cancel();
                }
                syncSpotAuditTimer = new Timer();
                syncSpotAuditTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for(SpotAudit item : spotAudits){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncSpotAuditToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        spotAuditDenominationViewModel.fetchCompleteSpotAuditDenominationToSync().observe(this, new Observer<List<SpotAuditDenomination>>() {
            @Override
            public void onChanged(List<SpotAuditDenomination> spotAuditDenominations) {
                if(syncSpotAuditDenominationTimer != null){
                    syncSpotAuditDenominationTimer.cancel();
                }
                syncSpotAuditDenominationTimer = new Timer();
                syncSpotAuditDenominationTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for(SpotAuditDenomination item : spotAuditDenominations){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncSpotAuditDenominationToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
        cutOffProductsViewModel.fetchCompleteCutOffProductsToSync().observe(this, new Observer<List<CutOffProducts>>() {
            @Override
            public void onChanged(List<CutOffProducts> cutOffProducts) {
                if(syncCutOffProductTimer != null){
                    syncCutOffProductTimer.cancel();
                }
                syncCutOffProductTimer = new Timer();
                syncCutOffProductTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for(CutOffProducts item : cutOffProducts){
                            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    posProcess.SyncCutOffProductsToServer(item);
                                }
                            });
                        }
                    }
                }, BuildConfig.APP_DEFAULT_SYNC_TIME);
            }
        });
    }

    private void detachObservers(){
        transactionsViewModel.fetchCompleteTransactionToSync().removeObservers(this);
        ordersViewModel.fetchCompleteOrderToSync().removeObservers(this);
        paymentsViewModel.fetchCompletePaymentToSync().removeObservers(this);
        machineDetailsViewModel.fetchMachineDetailsToSync().removeObservers(this);
        discountsViewModel.fetchCompleteDiscountsToSync().removeObservers(this);
        discountDetailsViewModel.fetchCompleteDiscountDetailsToSync().removeObservers(this);
        cutOffViewModel.fetchCompleteCutOffToSync().removeObservers(this);
        endOfDayViewModel.fetchCompleteEndOfDayToSync().removeObservers(this);
        safekeepingViewModel.fetchCompleteSafekeepingToSync().removeObservers(this);
        safekeepingDenominationViewModel.fetchCompleteSafekeepingDenominationToSync().removeObservers(this);
        paymentOtherInformationsViewModel.fetchCompletePaymentOtherInformationsToSync().removeObservers(this);
        discountOtherInformationsViewModel.fetchCompleteDiscountOtherInformationsToSync().removeObservers(this);
        cutOffDepartmentsViewModel.fetchCompleteCutOffDepartmentsToSync().removeObservers(this);
        cutOffDiscountsViewModel.fetchCompleteCutOffDiscountsToSync().removeObservers(this);
        cutOffPaymentsViewModel.fetchCompleteCutOffPaymentsToSync().removeObservers(this);
        endOfDayDiscountsViewModel.fetchCompleteEndOfDayDiscountsToSync().removeObservers(this);
        endOfDayPaymentsViewModel.fetchCompleteEndOfDayPaymentsToSync().removeObservers(this);
        endOfDayDepartmentsViewModel.fetchCompleteEndOfDayDepartmentsToSync().removeObservers(this);
        cashFundViewModel.fetchCompleteCashFundToSync().removeObservers(this);
        cashFundDenominationViewModel.fetchCompleteCashFundDenominationToSync().removeObservers(this);
        auditTrailsViewModel.fetchCompleteAuditTrailsToSync().removeObservers(this);
        officialReceiptInformationViewModel.fetchCompleteOfficialReceiptInformationToSync().removeObservers(this);
        spotAuditViewModel.fetchCompleteSpotAuditToSync().removeObservers(this);
        spotAuditDenominationViewModel.fetchCompleteSpotAuditDenominationToSync().removeObservers(this);
        cutOffProductsViewModel.fetchCompleteCutOffProductsToSync().removeObservers(this);
    }

    private void initBroadcast(){
        //Wifi
        IntentFilter intentFilterWifi = new IntentFilter();
        intentFilterWifi.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        wifiReceiver = new WifiReceiver();
        wifiReceiver.setMenu(menu);
        registerReceiver(wifiReceiver, intentFilterWifi);
        //Bluetooth
        IntentFilter intentFilterBluetooth = new IntentFilter();
        intentFilterBluetooth.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilterBluetooth.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilterBluetooth.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        bluetoothReceiver = new BluetoothReceiver(MainActivity.this);
        registerReceiver(bluetoothReceiver, intentFilterBluetooth);
    }

    private void initMainSecondScreen(){
        DisplayManager displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = displayManager.getDisplays();
        if(displays.length > 1){
            Display secondDisplay = displays[1];
            secondScreenLoginPresentation = new SecondScreenLoginPresentation(this, secondDisplay);
            secondScreenLoginPresentation.show();
//            secondScreenMainPresentation = new SecondScreenMainPresentation(this, secondDisplay);
//            secondScreenMainPresentation.show();
        }
        else{
            Log.d("SecondScreen", "No second display available");
        }
    }

    private void initWebSocket(){
        try {
            //10.0.2.2 This is the localhost in emulator
            socket = IO.socket(BuildConfig.ENV.equals("DEVELOPMENT") ? BuildConfig.SOCKET_DEVELOPMENT_URL : BuildConfig.SOCKET_PRODUCTION_URL);
            socket.connect();
            POSApplication.getInstance().setSocket(socket);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void firstInstall(){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            try {
                ApplicationSettings applicationSettings = applicationSettingsViewModel.fetch();
                if(applicationSettings != null){
                    if(applicationSettings.getFirstInstall() == 1){
                        TransactionSyncDialog transactionSyncDialog = TransactionSyncDialog.newTransactionSyncDialog();
                        transactionSyncDialog.setCancelable(false);
                        transactionSyncDialog.show(this.getSupportFragmentManager(), "Transaction Sync Dialog");
                    }
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void checkForCashFunds(){
        try {
            ApplicationSettings applicationSettings = applicationSettingsViewModel.fetch();
            if(applicationSettings != null){
                if(applicationSettings.getAutomaticCashFund() == 1){
                    CashFund cashFund = cashFundViewModel.checkForCashFund();
                    if(cashFund == null){
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                CashFundDialog cashFundDialog = CashFundDialog.newCashFundDialog();
                                cashFundDialog.setCancelable(false);
                                cashFundDialog.show(getSupportFragmentManager(), "Cash Fund Dialog");
                            }
                        }, BuildConfig.APP_DEFAULT_SYNC_TIME);
                    }
                }
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onItemMenuClick(Products products, double qty) {
        //This will clear the menu text search
        menu.clearMenuSearchText();
        //This function will create or update existing transaction
        if(!POSApplication.getInstance().checkCurrentTransaction()){
            if(products.getIsOpenPrice() == 1 && products.getWithSerial() == 0){
                OpenPriceDialog openPriceDialog = new OpenPriceDialog(this, products) {
                    @Override
                    public void confirm(Products openPriceProducts) {
                        createTransaction(openPriceProducts, qty, "");
                    }
                };
                openPriceDialog.setCancelable(false);
                openPriceDialog.show();
            } else if (products.getWithSerial() == 1 && products.getIsOpenPrice() == 0) {
                SerialNumberDialog serialNumberDialog = new SerialNumberDialog(this, products) {
                    @Override
                    public void confirm(Products withSerialProducts, String serialNumber) {
                        createTransaction(withSerialProducts, qty, serialNumber);
                    }
                };
                serialNumberDialog.setCancelable(false);
                serialNumberDialog.show();
            } else if (products.getIsOpenPrice() == 1 && products.getWithSerial() == 1) {
                OpenPriceDialog openPriceDialog = new OpenPriceDialog(this, products) {
                    @Override
                    public void confirm(Products openPriceProducts) {
                        SerialNumberDialog serialNumberDialog = new SerialNumberDialog(MainActivity.this, openPriceProducts) {
                            @Override
                            public void confirm(Products withSerialProducts, String serialNumber) {
                                createTransaction(withSerialProducts, qty, serialNumber);
                            }
                        };
                        serialNumberDialog.setCancelable(false);
                        serialNumberDialog.show();
                    }
                };
                openPriceDialog.setCancelable(false);
                openPriceDialog.show();
            } else{
                createTransaction(products, qty, "");
            }
        }
        else{
            if(products.getIsOpenPrice() == 1 && products.getWithSerial() == 0){
                if(checkHasExistingOpenPriceProductCurrentTransaction(products.getCoreId())){
                    Toast.makeText(this, "Item already exist with open price please adjust the quantity on the order panel.", Toast.LENGTH_LONG).show();
                }
                else{
                    OpenPriceDialog openPriceDialog = new OpenPriceDialog(this, products) {
                        @Override
                        public void confirm(Products openPriceProducts) {
                            posProcess.saveTransactionOrder(openPriceProducts, qty, "");
                            posProcess.recomputeCurrentTransactionOrders();
                            transactionsViewModel.setCurrentTransaction(posProcess.recomputeCurrentTransaction());
                            ordersViewModel.setCurrentTransactionOrders(posProcess.getCurrentTransactionOrders());
                            //Save Audit Trail
                            AuditTrail.getInstance().save(
                                POSApplication.getInstance().getUserAuthentication().getId(),
                                POSApplication.getInstance().getUserAuthentication().getName(),
                                transactionsViewModel.getCurrentTransaction().getValue().getId(),
                                "TRANSACTION",
                                "Update transaction control number of " + transactionsViewModel.getCurrentTransaction().getValue().getControlNumber() + " with product " + openPriceProducts.getName(),
                                0,
                                "",
                                0,
                                0
                            );
                        }
                    };
                    openPriceDialog.setCancelable(false);
                    openPriceDialog.show();
                }
            } else if (products.getWithSerial() == 1 && products.getIsOpenPrice() == 0) {
                SerialNumberDialog serialNumberDialog = new SerialNumberDialog(this, products) {
                    @Override
                    public void confirm(Products withSerialProducts, String serialNumber) {
                        posProcess.saveTransactionOrder(withSerialProducts, qty, serialNumber);
                        posProcess.recomputeCurrentTransactionOrders();
                        transactionsViewModel.setCurrentTransaction(posProcess.recomputeCurrentTransaction());
                        ordersViewModel.setCurrentTransactionOrders(posProcess.getCurrentTransactionOrders());
                        //Save Audit Trail
                        AuditTrail.getInstance().save(
                            POSApplication.getInstance().getUserAuthentication().getId(),
                            POSApplication.getInstance().getUserAuthentication().getName(),
                            transactionsViewModel.getCurrentTransaction().getValue().getId(),
                            "TRANSACTION",
                            "Update transaction control number of " + transactionsViewModel.getCurrentTransaction().getValue().getControlNumber() + " with product " + withSerialProducts.getName(),
                            0,
                            "",
                            0,
                            0
                        );
                    }
                };
                serialNumberDialog.setCancelable(false);
                serialNumberDialog.show();
            } else if (products.getIsOpenPrice() == 1 && products.getWithSerial() == 1) {
                OpenPriceDialog openPriceDialog = new OpenPriceDialog(this, products) {
                    @Override
                    public void confirm(Products openPriceProducts) {
                        //Save Audit Trail
                        AuditTrail.getInstance().save(
                            POSApplication.getInstance().getUserAuthentication().getId(),
                            POSApplication.getInstance().getUserAuthentication().getName(),
                            transactionsViewModel.getCurrentTransaction().getValue().getId(),
                            "TRANSACTION",
                            "Update transaction control number of " + transactionsViewModel.getCurrentTransaction().getValue().getControlNumber() + " with product " + openPriceProducts.getName(),
                            0,
                            "",
                            0,
                            0
                        );
                        SerialNumberDialog serialNumberDialog = new SerialNumberDialog(MainActivity.this, openPriceProducts) {
                            @Override
                            public void confirm(Products withSerialProducts, String serialNumber) {
                                posProcess.saveTransactionOrder(withSerialProducts, qty, serialNumber);
                                posProcess.recomputeCurrentTransactionOrders();
                                transactionsViewModel.setCurrentTransaction(posProcess.recomputeCurrentTransaction());
                                ordersViewModel.setCurrentTransactionOrders(posProcess.getCurrentTransactionOrders());
                                //Save Audit Trail
                                AuditTrail.getInstance().save(
                                    POSApplication.getInstance().getUserAuthentication().getId(),
                                    POSApplication.getInstance().getUserAuthentication().getName(),
                                    transactionsViewModel.getCurrentTransaction().getValue().getId(),
                                    "TRANSACTION",
                                    "Update transaction control number of " + transactionsViewModel.getCurrentTransaction().getValue().getControlNumber() + " with product " + withSerialProducts.getName(),
                                    0,
                                    "",
                                    0,
                                    0
                                );
                            }
                        };
                        serialNumberDialog.setCancelable(false);
                        serialNumberDialog.show();
                    }
                };
                openPriceDialog.setCancelable(false);
                openPriceDialog.show();
            } else{
                posProcess.saveTransactionOrder(products, qty, "");
                posProcess.recomputeCurrentTransactionOrders();
                transactionsViewModel.setCurrentTransaction(posProcess.recomputeCurrentTransaction());
                ordersViewModel.setCurrentTransactionOrders(posProcess.getCurrentTransactionOrders());
                //Save Audit Trail
                AuditTrail.getInstance().save(
                    POSApplication.getInstance().getUserAuthentication().getId(),
                    POSApplication.getInstance().getUserAuthentication().getName(),
                    transactionsViewModel.getCurrentTransaction().getValue().getId(),
                    "TRANSACTION",
                    "Update transaction control number of " + transactionsViewModel.getCurrentTransaction().getValue().getControlNumber() + " with product " + products.getName(),
                    0,
                    "",
                    0,
                    0
                );
            }
        }
    }

    private void createTransaction(Products products, double qty, String serialNumber){
        Long transactionId = posProcess.createTransaction(products, qty);
        POSApplication.getInstance().setCurrentTransaction(Integer.parseInt(transactionId.toString()));
        cache.saveString("currentTransaction", String.valueOf(POSApplication.getInstance().getCurrentTransaction()));
        posProcess.saveTransactionOrder(products, qty, serialNumber);
        posApplicationViewModel.setCurrentTransaction(POSApplication.getInstance().getCurrentTransaction());
        POSApplication.getInstance().setTotalQuantity(1);
        menu.applyMenuQuantity("1");
        //Save Audit Trail
        AuditTrail.getInstance().save(
            POSApplication.getInstance().getUserAuthentication().getId(),
            POSApplication.getInstance().getUserAuthentication().getName(),
            transactionsViewModel.getCurrentTransaction().getValue().getId(),
            "TRANSACTION",
            "Create transaction control number of " + transactionsViewModel.getCurrentTransaction().getValue().getControlNumber(),
            0,
            "",
            0,
            0
        );
        //Save Audit Trail
        AuditTrail.getInstance().save(
            POSApplication.getInstance().getUserAuthentication().getId(),
            POSApplication.getInstance().getUserAuthentication().getName(),
            transactionsViewModel.getCurrentTransaction().getValue().getId(),
            "TRANSACTION",
            "Update transaction control number of " + transactionsViewModel.getCurrentTransaction().getValue().getControlNumber() + " with product " + products.getName(),
            0,
            "",
            0,
            0
        );
    }

    private void checkHasCurrentTransaction(){
        if(cache.getString("currentTransaction") != BuildConfig.CACHE_DEFAULT_VALUE){
            POSApplication.getInstance().setCurrentTransaction(Integer.parseInt(cache.getString("currentTransaction")));
            if(posProcess.checkCurrentTransactionIsExisting()){
                posApplicationViewModel.setCurrentTransaction(POSApplication.getInstance().getCurrentTransaction());
            }
            else{
                POSApplication.getInstance().setCurrentTransaction(0);
                posApplicationViewModel.setCurrentTransaction(0);
                cache.clearString("currentTransaction");
            }
        }
    }

    private boolean checkHasExistingOpenPriceProductCurrentTransaction(int productId){
        List<Orders> orders = posProcess.getCurrentTransactionOrders();
        for(Orders item : orders){
            if(item.getProductId() == productId){
                return true;
            }
        }
        return false;
    }

    @Override
    public void resumeTransaction(int transactionId) {
        POSApplication.getInstance().setCurrentTransaction(transactionId);
        cache.saveString("currentTransaction", String.valueOf(transactionId));
        posApplicationViewModel.setCurrentTransaction(transactionId);
        //Save Audit Trail
        AuditTrail.getInstance().save(
            POSApplication.getInstance().getUserAuthentication().getId(),
            POSApplication.getInstance().getUserAuthentication().getName(),
            transactionId,
            "TRANSACTION",
            "Resume transaction control number of " + transactionsViewModel.getCurrentTransaction().getValue().getControlNumber(),
            0,
            "",
            0,
            0
        );
    }

    @Override
    public void backoutTransaction(int transactionId) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.getInstance().startLoadingDialog(MainActivity.this, "Backing out please wait....");
                    }
                });

                posProcess.backoutTransaction(transactionId);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Transactions transactions = transactionsViewModel.fetchTransaction(transactionId);
                                    List<Orders> orders = ordersViewModel.fetchTransactionOrders(transactionId);
                                    //Save Audit Trail
                                    AuditTrail.getInstance().save(
                                        POSApplication.getInstance().getUserAuthentication().getId(),
                                        POSApplication.getInstance().getUserAuthentication().getName(),
                                        transactionId,
                                        "TRANSACTION",
                                        "Transaction backout of control number " + transactions.getControlNumber(),
                                        0,
                                        "",
                                        0,
                                        0
                                    );
                                    Printer.getInstance().print(MainActivity.this, printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_BACKOUT), Printer.getInstance().backoutTransaction(transactions, orders));
                                    if(POSApplication.getInstance().getCurrentTransaction() == transactionId){
                                        POSApplication.getInstance().setCurrentTransaction(0);
                                        posApplicationViewModel.setCurrentTransaction(0);
                                        cache.clearString("currentTransaction");
                                    }
                                    LoadingDialog.getInstance().closeLoadingDialog();
                                } catch (ExecutionException e) {
                                    throw new RuntimeException(e);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, BuildConfig.APP_DEFAULT_LOADING_TIME);
                    }
                });

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(secondScreenLoginPresentation != null){
            secondScreenLoginPresentation.dismiss();
            secondScreenLoginPresentation = null;
        }
        if(socket != null){
            socket.disconnect();
            POSApplication.getInstance().setSocket(null);
        }
//        if(secondScreenMainPresentation != null){
//            secondScreenMainPresentation.dismiss();
//            secondScreenMainPresentation = null;
//        }
        unregisterReceiver(wifiReceiver);
        unregisterReceiver(bluetoothReceiver);
        detachObservers();
        LoadingDialog.getInstance().closeLoadingDialog();
    }

    @Override
    public void onOrderItemUpdateQuantity(int orderId, UserAuthentication authorizeUser) {
        try {
            Orders orders = ordersViewModel.fetchOrder(orderId);
            ChangeQuantityDialog changeQuantityDialog = ChangeQuantityDialog.newChangeQuantityDialog();
            changeQuantityDialog.setArgs(orders, this, authorizeUser);
            changeQuantityDialog.show(getSupportFragmentManager(), "Change Price Dialog");
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onOrderItemUpdatePrice(int orderId, UserAuthentication authorizeUser) {
        try {
            Orders orders = ordersViewModel.fetchOrder(orderId);
            ChangePriceDialog changePriceDialog = ChangePriceDialog.newChangePriceDialog();
            changePriceDialog.setArgs(orders, this, authorizeUser);
            changePriceDialog.show(getSupportFragmentManager(), "Change Price Dialog");
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onOrderItemReturnItem(int orderId) {
        try {
            Orders orders = ordersViewModel.fetchOrder(orderId);
            if(orders != null){
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            orders.setQty(orders.getQty() * -1);
                            orders.setGross(orders.getGross() * -1);
                            orders.setTotal(orders.getTotal() * -1);
                            orders.setVatableSales(orders.getVatableSales() * -1);
                            orders.setVatExemptSales(orders.getVatExemptSales() * -1);
                            orders.setVatAmount(orders.getVatAmount() * -1);
                            orders.setIsReturn(1);
                            orders.setIsSentToServer(0);
                            ordersViewModel.update(orders);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ordersViewModel.setCurrentTransactionOrders(posProcess.getCurrentTransactionOrders());
                                transactionsViewModel.setCurrentTransaction(posProcess.recomputeCurrentTransaction());
                            }
                        });

                    }
                });
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onOrderItemUnReturnItem(int orderId) {
        try {
            Orders orders = ordersViewModel.fetchOrder(orderId);
            if(orders != null){
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            orders.setQty(orders.getQty() * -1);
                            orders.setGross(orders.getGross() * -1);
                            orders.setTotal(orders.getTotal() * -1);
                            orders.setVatableSales(orders.getVatableSales() * -1);
                            orders.setVatExemptSales(orders.getVatExemptSales() * -1);
                            orders.setVatAmount(orders.getVatAmount() * -1);
                            orders.setIsReturn(0);
                            orders.setIsSentToServer(0);
                            ordersViewModel.update(orders);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ordersViewModel.setCurrentTransactionOrders(posProcess.getCurrentTransactionOrders());
                                transactionsViewModel.setCurrentTransaction(posProcess.recomputeCurrentTransaction());
                            }
                        });

                    }
                });
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTakeOrderItemClick(Transactions transactions, List<Orders> orders, List<Discounts> discounts, List<DiscountDetails> discountDetails, List<DiscountOtherInformations> discountOtherInformations) {
        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                Long transactionId = posProcess.processTakeOrderInformation(transactions, orders, discounts, discountDetails, discountOtherInformations);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        POSApplication.getInstance().setCurrentTransaction(transactionId.intValue());
                        cache.saveString("currentTransaction", String.valueOf(POSApplication.getInstance().getCurrentTransaction()));
                        posApplicationViewModel.setCurrentTransaction(POSApplication.getInstance().getCurrentTransaction());
                        posProcess.recomputeCurrentTransaction();
                    }
                });

            }
        });
    }

    @Override
    public void onChangeTotalSafeKeeping(double value, String method) {
        if(method.equals("SAFEKEEPING")){
            safekeepingViewModel.setSafekeepingTotalLiveData(value);
        } else if (method.equals("CASH FUND")) {
            cashFundViewModel.setCashFundTotalLiveData(value);
        } else if (method.equals("SPOT AUDIT")) {
            spotAuditViewModel.setSpotAuditTotalLiveData(value);
        }
    }

    private void requestPermissionUSB(){
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        PendingIntent permissionIntent;
        if(Build.VERSION.SDK_INT >= 34){
            permissionIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(BuildConfig.ACTION_USB_PERMISSION), PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_ALLOW_UNSAFE_IMPLICIT_INTENT);
        }
        else{
            permissionIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(BuildConfig.ACTION_USB_PERMISSION), PendingIntent.FLAG_IMMUTABLE);
        }
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        if(manager != null && deviceList != null){
            for (UsbDevice usbDevice : deviceList.values()) {
                try {
                    Devices devices = devicesViewModel.fetchDeviceSerialNumber(usbDevice.getSerialNumber());
                    if(devices != null){
                        devices.setDevice(usbDevice.getDeviceName());
                        devicesViewModel.update(devices);
                    }
                } catch (SecurityException | ExecutionException | InterruptedException e){
                    Log.d("requestPermissionUSB", "requestPermissionUSB: " + e.toString());
                    manager.requestPermission(usbDevice, permissionIntent);
                }
            }
        }
    }

    private void requestPermissionBluetooth(){
        connectBluetoothPrinters();
    }

    private void connectBluetoothPrinters(){
        try {
            POSApplication.getInstance().setBluetoothConnection((new BluetoothPrintersConnections()).getList());
        } catch (SecurityException e) {
            Log.d("bluetoothConnection", "bluetoothConnection " + e.toString());
            Toast.makeText(this, "Please grant the permission for bluetooth in order to use a bluetooth printer.", Toast.LENGTH_LONG).show();
        }
        if(POSApplication.getInstance().getBluetoothConnection() != null){
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
            for(BluetoothConnection item : POSApplication.getInstance().getBluetoothConnection()){
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Devices devices = devicesViewModel.fetchDeviceSerialNumber(item.getDevice().getAddress());
                            if(devices != null){
                                Log.d("bluetoothConnection", "bluetoothConnecting: " + devices.getDevice());
                                if(!item.isConnected()) item.connect();
                            }
                        } catch (SecurityException | ExecutionException | InterruptedException | EscPosConnectionException e) {
                            Log.d("bluetoothConnection", "bluetoothConnection failed: " + e.toString());
                        }

                    }
                });
            }
        }
    }

    public void reconnectBluetoothPrinter(){
        try {
            POSApplication.getInstance().setBluetoothConnection(null);
            POSApplication.getInstance().setBluetoothConnection((new BluetoothPrintersConnections()).getList());
        } catch (SecurityException e) {
            Log.d("bluetoothConnection", "bluetoothConnection " + e.toString());
            Toast.makeText(this, "Please grant the permission for bluetooth in order to use a bluetooth printer.", Toast.LENGTH_LONG).show();
        }
        if(POSApplication.getInstance().getBluetoothConnection() != null){
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
            for(BluetoothConnection item : POSApplication.getInstance().getBluetoothConnection()){
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Devices devices = devicesViewModel.fetchDeviceSerialNumber(item.getDevice().getAddress());
                            if(devices != null){
                                Log.d("bluetoothConnection", "bluetoothConnecting: " + devices.getDevice());
                                if(!item.isConnected()) item.connect();
                            }
                        } catch (SecurityException | ExecutionException | InterruptedException | EscPosConnectionException e) {
                            Log.d("bluetoothConnection", "bluetoothConnection failed: " + e.toString());
                        }

                    }
                });
            }
        }
    }

    public void disconnectBluetoothPrinter(){
        if(POSApplication.getInstance().getBluetoothConnection() != null){
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
            for(BluetoothConnection item : POSApplication.getInstance().getBluetoothConnection()){
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Devices devices = devicesViewModel.fetchDeviceSerialNumber(item.getDevice().getAddress());
                            if(devices != null){
                                Log.d("bluetoothDisconnect", "bluetoothDisconnect: " + devices.getDevice());
                                item.disconnect();
                            }
                        } catch (SecurityException | ExecutionException | InterruptedException e) {
                            Log.d("bluetoothDisconnect", "bluetoothDisconnect failed: " + e.toString());
                        }

                    }
                });
            }
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) { //This is for barcode scanning
        //barcode scanner
        int c = event.getUnicodeChar();
        //accept only 0..9 and ENTER
        if ((c>=48 && c<=57) || c==10){
            if (event.getAction()==0) {
                if (c >= 48 && c <= 57)
                    scanBarcode += "" + (char) c;
                else {
                    if (!scanBarcode.equals("")) {
                        final String b = scanBarcode;
                        scanBarcode = "";
                        try {
                            Products products = productsViewModel.barcode(b);
                            if(products != null){
                                if(!POSApplication.getInstance().checkCurrentTransaction()){
                                    if(products.getIsOpenPrice() == 1 && products.getWithSerial() == 0){
                                        OpenPriceDialog openPriceDialog = new OpenPriceDialog(this, products) {
                                            @Override
                                            public void confirm(Products openPriceProducts) {
                                                createTransaction(openPriceProducts, POSApplication.getInstance().getTotalQuantity(), "");
                                            }
                                        };
                                        openPriceDialog.setCancelable(false);
                                        openPriceDialog.show();
                                    } else if (products.getWithSerial() == 1 && products.getIsOpenPrice() == 0) {
                                        SerialNumberDialog serialNumberDialog = new SerialNumberDialog(this, products) {
                                            @Override
                                            public void confirm(Products withSerialProducts, String serialNumber) {
                                                createTransaction(withSerialProducts, POSApplication.getInstance().getTotalQuantity(), serialNumber);
                                            }
                                        };
                                        serialNumberDialog.setCancelable(false);
                                        serialNumberDialog.show();
                                    } else if (products.getWithSerial() == 1 && products.getIsOpenPrice() == 1) {
                                        OpenPriceDialog openPriceDialog = new OpenPriceDialog(this, products) {
                                            @Override
                                            public void confirm(Products openPriceProducts) {
                                                SerialNumberDialog serialNumberDialog = new SerialNumberDialog(MainActivity.this, openPriceProducts) {
                                                    @Override
                                                    public void confirm(Products withSerialProducts, String serialNumber) {
                                                        createTransaction(withSerialProducts, POSApplication.getInstance().getTotalQuantity(), serialNumber);
                                                    }
                                                };
                                                serialNumberDialog.setCancelable(false);
                                                serialNumberDialog.show();
                                            }
                                        };
                                        openPriceDialog.setCancelable(false);
                                        openPriceDialog.show();
                                    } else{
                                        createTransaction(products, 1, "");
                                    }
                                }
                                else{
                                    if(products.getIsOpenPrice() == 1 && products.getWithSerial() == 0){
                                        if(checkHasExistingOpenPriceProductCurrentTransaction(products.getCoreId())){
                                            Toast.makeText(this, "Item already exist with open price please adjust the quantity on the order panel.", Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            OpenPriceDialog openPriceDialog = new OpenPriceDialog(this, products) {
                                                @Override
                                                public void confirm(Products openPriceProducts) {
                                                    posProcess.saveTransactionOrder(openPriceProducts, POSApplication.getInstance().getTotalQuantity(), "");
                                                    posProcess.recomputeCurrentTransactionOrders();
                                                    transactionsViewModel.setCurrentTransaction(posProcess.recomputeCurrentTransaction());
                                                    ordersViewModel.setCurrentTransactionOrders(posProcess.getCurrentTransactionOrders());
                                                    //Save Audit Trail
                                                    AuditTrail.getInstance().save(
                                                        POSApplication.getInstance().getUserAuthentication().getId(),
                                                        POSApplication.getInstance().getUserAuthentication().getName(),
                                                        transactionsViewModel.getCurrentTransaction().getValue().getId(),
                                                        "ITEM OPEN PRICE",
                                                        "Update transaction control number of " + transactionsViewModel.getCurrentTransaction().getValue().getControlNumber() + " with product " + openPriceProducts.getName(),
                                                        0,
                                                        "",
                                                        openPriceProducts.getId(),
                                                        0
                                                    );
                                                }
                                            };
                                            openPriceDialog.setCancelable(false);
                                            openPriceDialog.show();
                                        }
                                    } else if (products.getWithSerial() == 1 && products.getIsOpenPrice() == 0) {
                                        SerialNumberDialog serialNumberDialog = new SerialNumberDialog(this, products) {
                                            @Override
                                            public void confirm(Products withSerialProducts, String serialNumber) {
                                                posProcess.saveTransactionOrder(withSerialProducts, POSApplication.getInstance().getTotalQuantity(), serialNumber);
                                                posProcess.recomputeCurrentTransactionOrders();
                                                transactionsViewModel.setCurrentTransaction(posProcess.recomputeCurrentTransaction());
                                                ordersViewModel.setCurrentTransactionOrders(posProcess.getCurrentTransactionOrders());
                                                //Save Audit Trail
                                                AuditTrail.getInstance().save(
                                                    POSApplication.getInstance().getUserAuthentication().getId(),
                                                    POSApplication.getInstance().getUserAuthentication().getName(),
                                                    transactionsViewModel.getCurrentTransaction().getValue().getId(),
                                                    "TRANSACTION",
                                                    "Update transaction control number of " + transactionsViewModel.getCurrentTransaction().getValue().getControlNumber() + " with product " + withSerialProducts.getName(),
                                                    0,
                                                    "",
                                                    0,
                                                    0
                                                );
                                            }
                                        };
                                        serialNumberDialog.setCancelable(false);
                                        serialNumberDialog.show();
                                    } else if (products.getWithSerial() == 1 && products.getIsOpenPrice() == 1) {
                                        OpenPriceDialog openPriceDialog = new OpenPriceDialog(this, products) {
                                            @Override
                                            public void confirm(Products openPriceProducts) {
                                                //Save Audit Trail
                                                AuditTrail.getInstance().save(
                                                    POSApplication.getInstance().getUserAuthentication().getId(),
                                                    POSApplication.getInstance().getUserAuthentication().getName(),
                                                    transactionsViewModel.getCurrentTransaction().getValue().getId(),
                                                    "TRANSACTION",
                                                    "Update transaction control number of " + transactionsViewModel.getCurrentTransaction().getValue().getControlNumber() + " with product " + openPriceProducts.getName(),
                                                    0,
                                                    "",
                                                    openPriceProducts.getId(),
                                                    0
                                                );
                                                SerialNumberDialog serialNumberDialog = new SerialNumberDialog(MainActivity.this, openPriceProducts) {
                                                    @Override
                                                    public void confirm(Products withSerialProducts, String serialNumber) {
                                                        posProcess.saveTransactionOrder(withSerialProducts, POSApplication.getInstance().getTotalQuantity(), serialNumber);
                                                        posProcess.recomputeCurrentTransactionOrders();
                                                        transactionsViewModel.setCurrentTransaction(posProcess.recomputeCurrentTransaction());
                                                        ordersViewModel.setCurrentTransactionOrders(posProcess.getCurrentTransactionOrders());
                                                        //Save Audit Trail
                                                        AuditTrail.getInstance().save(
                                                            POSApplication.getInstance().getUserAuthentication().getId(),
                                                            POSApplication.getInstance().getUserAuthentication().getName(),
                                                            transactionsViewModel.getCurrentTransaction().getValue().getId(),
                                                            "TRANSACTION",
                                                            "Update transaction control number of " + transactionsViewModel.getCurrentTransaction().getValue().getControlNumber() + " with product " + withSerialProducts.getName(),
                                                            0,
                                                            "",
                                                            0,
                                                            0
                                                        );
                                                    }
                                                };
                                                serialNumberDialog.setCancelable(false);
                                                serialNumberDialog.show();
                                            }
                                        };
                                        openPriceDialog.setCancelable(false);
                                        openPriceDialog.show();
                                    } else{
                                        posProcess.saveTransactionOrder(products, POSApplication.getInstance().getTotalQuantity(), "");
                                        posProcess.recomputeCurrentTransactionOrders();
                                        transactionsViewModel.setCurrentTransaction(posProcess.recomputeCurrentTransaction());
                                        ordersViewModel.setCurrentTransactionOrders(posProcess.getCurrentTransactionOrders());
                                        //Save Audit Trail
                                        AuditTrail.getInstance().save(
                                            POSApplication.getInstance().getUserAuthentication().getId(),
                                            POSApplication.getInstance().getUserAuthentication().getName(),
                                            transactionsViewModel.getCurrentTransaction().getValue().getId(),
                                            "TRANSACTION",
                                            "Update transaction control number of " + transactionsViewModel.getCurrentTransaction().getValue().getControlNumber() + " with product " + products.getName(),
                                            0,
                                            "",
                                            0,
                                            0
                                        );
                                    }
                                }
                            }
                            else{
                                Toast.makeText(this, "Product not found.", Toast.LENGTH_LONG).show();
                            }
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void CashDenominationSync(CashDenominationResponse cashDenominationResponse, Sync sync, boolean success) {
        if(success){
            LoadingDialog.getInstance().startLoadingDialog(MainActivity.this, sync.getName() + " is syncing please wait.");
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
            if(cashDenominationResponse.getData().size() != 0){
                cashDenominationResponse.getData().forEach(dataClass -> {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                CashDenomination cashDenomination = cashDenominationViewModel.fetchById(dataClass.getId());
                                if(cashDenomination != null){
                                    cashDenomination.setName(dataClass.getName());
                                    cashDenomination.setAmount(dataClass.getAmount());
                                    cashDenominationViewModel.update(cashDenomination);
                                }
                                else{
                                    cashDenominationViewModel.insert(new CashDenomination(
                                            dataClass.getId(),
                                            dataClass.getName(),
                                            dataClass.getAmount()
                                    ));
                                }
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                });
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sync.setUpdatedAt(date.now());
                        syncViewModel.update(sync);
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(MainActivity.this, "Done syncing cash denominations.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            else{
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(MainActivity.this, "There is no data to sync.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            //Shutdown the executor service
            executorService.shutdown();
        }
        else{
            try {
                Log.d("cashDenominationRun", "runError: 1");
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                Log.d("cashDenominationRun", "runError: 1 Done");
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void DepartmentsSync(DepartmentsResponse departmentsResponse, Sync sync, boolean success) {
        if(success){
            LoadingDialog.getInstance().startLoadingDialog(MainActivity.this, sync.getName() + " is syncing please wait.");
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
            if(departmentsResponse.getData().size() != 0){
                departmentsResponse.getData().forEach(dataClass -> {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Departments departments = departmentsViewModel.fetchDepartment(dataClass.getId());
                                if(departments != null){
                                    departments.setName(dataClass.getName());
                                    departments.setStatus(dataClass.getStatus().equals("active") ? 1 : 0);
                                    departmentsViewModel.update(departments);
                                }
                                else{
                                    departmentsViewModel.insert(new Departments(
                                        dataClass.getId(),
                                        "",
                                        dataClass.getName(),
                                        dataClass.getStatus().equals("active") ? 1 : 0
                                    ));
                                }
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    });
                });
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sync.setUpdatedAt(date.now());
                        syncViewModel.update(sync);
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(MainActivity.this, "Done syncing departments.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            else{
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(MainActivity.this, "There is no data to sync", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            //Shutdown the executor service
            executorService.shutdown();
        }
        else{
            try {
                Log.d("departmentRun", "runError: 1");
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                Log.d("departmentRun", "runError: 1 Done");
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void CategoriesSync(CategoriesResponse categoriesResponse, Sync sync, boolean success) {
        if(success){
            LoadingDialog.getInstance().startLoadingDialog(MainActivity.this, sync.getName() + " is syncing please wait.");
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
            if(categoriesResponse.getData().size() != 0){
                categoriesResponse.getData().forEach(dataClass -> {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Categories categories = categoriesViewModel.fetchCategory(dataClass.getId());
                                if(categories != null){
                                    categories.setName(dataClass.getName());
                                    categoriesViewModel.update(categories);
                                }
                                else{
                                    categoriesViewModel.insert(new Categories(
                                            dataClass.getId(),
                                            0,
                                            "",
                                            dataClass.getName(),
                                            1
                                    ));
                                }
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                });
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sync.setUpdatedAt(date.now());
                        syncViewModel.update(sync);
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(MainActivity.this, "Done syncing categories.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            else{
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(MainActivity.this, "There is no data to sync.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            //Shutdown the executor service
            executorService.shutdown();
        }
        else{
            try {
                Log.d("categoriesRun", "runError: 1");
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                Log.d("categoriesRun", "runError: 1 Done");
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void SubCategoriesSync(SubCategoriesResponse subCategoriesResponse, Sync sync, boolean success) {
        if(success){
            LoadingDialog.getInstance().startLoadingDialog(MainActivity.this, sync.getName() + " is syncing please wait.");
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
            if(subCategoriesResponse.getData().size() != 0){
                subCategoriesResponse.getData().forEach(dataClass -> {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                SubCategories subCategories = subCategoriesViewModel.fetchSubCategory(dataClass.getId());
                                if(subCategories != null){
                                    subCategories.setName(dataClass.getName());
                                    subCategoriesViewModel.update(subCategories);
                                }
                                else{
                                    subCategoriesViewModel.insert(new SubCategories(
                                            dataClass.getId(),
                                            0,
                                            dataClass.getName(),
                                            1
                                    ));
                                }
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    });
                });
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sync.setUpdatedAt(date.now());
                        syncViewModel.update(sync);
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(MainActivity.this, "Done syncing sub categories.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            else{
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(MainActivity.this, "There is no data to sync.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            //Shutdown the executor service
            executorService.shutdown();
        }
        else{
            try {
                Log.d("subCategoriesRun", "runError: 1");
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                Log.d("subCategoriesRun", "runError: 1 Done");
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void UsersSync(UsersResponse usersResponse, Sync sync, boolean success) {
        if(success){
            LoadingDialog.getInstance().startLoadingDialog(MainActivity.this, sync.getName() + " is syncing please wait.");
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
            if(usersResponse.getData().size() != 0){
                try {
                    rolesViewModel.removeRoles();
                    permissionsViewModel.removePermission();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                usersResponse.getData().forEach(dataClass -> {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Users users = usersViewModel.fetchById(dataClass.getId());
                                if(users != null){
                                    users.setName(dataClass.getName());
                                    users.setFirstname(dataClass.getFirst_name());
                                    users.setLastname(dataClass.getLast_name());
                                    users.setEmail(dataClass.getEmail());
                                    users.setEmailVerifiedAt(dataClass.getEmail_verified_at());
                                    users.setPassword(dataClass.getPassword());
                                    users.setPin(dataClass.getPin());
                                    users.setClientId(dataClass.getClient_id());
                                    users.setStatus(dataClass.getIs_active());
                                    dataClass.getRoles().forEach(itemRoles -> {
                                        //Check If Role Already Exist
                                        rolesViewModel.insert(new Roles(
                                                itemRoles.getId(),
                                                dataClass.getId(),
                                                itemRoles.getName()
                                        ));
                                        itemRoles.getPermissions().forEach(itemPermission -> {
                                            permissionsViewModel.insert(new Permissions(
                                                    itemPermission.getId(),
                                                    itemPermission.getParent_id(),
                                                    itemRoles.getId(),
                                                    itemPermission.getName(),
                                                    itemPermission.getLevel(),
                                                    dataClass.getId()
                                            ));
                                        });
                                    });
                                    usersViewModel.update(users);
                                }
                                else{
                                    usersViewModel.insert(new Users(
                                            dataClass.getId(),
                                            dataClass.getFirst_name(),
                                            dataClass.getLast_name(),
                                            "",
                                            dataClass.getName(),
                                            dataClass.getEmail(),
                                            dataClass.getEmail_verified_at(),
                                            dataClass.getUsername(),
                                            dataClass.getPassword(),
                                            dataClass.getPin(),
                                            0,
                                            "",
                                            dataClass.getClient_id(),
                                            dataClass.getIs_active()
                                    ));
                                    dataClass.getRoles().forEach(itemRoles -> {
                                        //Check If Role Already Exist
                                        rolesViewModel.insert(new Roles(
                                                itemRoles.getId(),
                                                dataClass.getId(),
                                                itemRoles.getName()
                                        ));
                                        itemRoles.getPermissions().forEach(itemPermission -> {
                                            permissionsViewModel.insert(new Permissions(
                                                    itemPermission.getId(),
                                                    itemPermission.getParent_id(),
                                                    itemRoles.getId(),
                                                    itemPermission.getName(),
                                                    itemPermission.getLevel(),
                                                    dataClass.getId()
                                            ));
                                        });
                                    });
                                }
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                });
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sync.setUpdatedAt(date.now());
                        syncViewModel.update(sync);
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(MainActivity.this, "Done syncing users.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            else{
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(MainActivity.this, "There is no data to sync.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            //Shutdown the executor service
            executorService.shutdown();
        }
        else{
            try {
                Log.d("userRun", "runError: 1");
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                Log.d("userRun", "runError: 1 Done");
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void PaymentTypeSync(PaymentTypesResponse paymentTypesResponse, Sync sync, boolean success) {
        if(success){
            LoadingDialog.getInstance().startLoadingDialog(MainActivity.this, sync.getName() + " is syncing please wait.");
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
            if(paymentTypesResponse.getData().size() != 0){
                paymentTypesResponse.getData().forEach(dataClass -> {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                PaymentTypes paymentTypes = paymentTypesViewModel.fetchById(dataClass.getId());
                                if(paymentTypes != null){
                                    paymentTypes.setName(dataClass.getName());
                                    paymentTypes.setIsAR(dataClass.getIs_ar());
                                    paymentTypes.setStatus(dataClass.getStatus().equals("active") ? 1 : 0);
                                    paymentTypesViewModel.update(paymentTypes);
                                    paymentTypeFieldsViewModel.delete(dataClass.getId());
                                    paymentTypeFieldOptionsViewModel.delete(dataClass.getId());
                                    dataClass.getFields().forEach(fieldsClass -> {
                                        paymentTypeFieldsViewModel.insert(new PaymentTypeFields(
                                                fieldsClass.getId(),
                                                dataClass.getId(),
                                                fieldsClass.getName(),
                                                fieldsClass.getField_type()
                                        ));
                                        if(fieldsClass.getOptions() != null){
                                            for(String item : fieldsClass.getOptions()){
                                                if(item != null){
                                                    paymentTypeFieldOptionsViewModel.insert(new PaymentTypeFieldOptions(
                                                            dataClass.getId(),
                                                            fieldsClass.getId(),
                                                            item
                                                    ));
                                                }
                                            }
                                        }
                                    });
                                }
                                else{
                                    paymentTypesViewModel.insert(new PaymentTypes(
                                            dataClass.getId(),
                                            dataClass.getName(),
                                            dataClass.getLogo(),
                                            dataClass.getIs_ar(),
                                            dataClass.getStatus().equals("active") ? 1 : 0
                                    ));
                                    dataClass.getFields().forEach(fieldsClass -> {
                                        paymentTypeFieldsViewModel.insert(new PaymentTypeFields(
                                                fieldsClass.getId(),
                                                dataClass.getId(),
                                                fieldsClass.getName(),
                                                fieldsClass.getField_type()
                                        ));
                                        if(fieldsClass.getOptions() != null){
                                            for(String item : fieldsClass.getOptions()){
                                                if(item != null){
                                                    paymentTypeFieldOptionsViewModel.insert(new PaymentTypeFieldOptions(
                                                            dataClass.getId(),
                                                            fieldsClass.getId(),
                                                            item
                                                    ));
                                                }
                                            }
                                        }
                                    });
                                }
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                });
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sync.setUpdatedAt(date.now());
                        syncViewModel.update(sync);
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(MainActivity.this, "Done syncing payment types.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            else{
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(MainActivity.this, "There is no data to sync.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            //Shutdown the executor service
            executorService.shutdown();
        }
        else{
            try {
                Log.d("paymentTypesRun", "runError: 1");
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                Log.d("paymentTypesRun", "runError: 1 Done");
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void DiscountTypeSync(DiscountTypesResponse discountTypesResponse, Sync sync, boolean success) {
        if(success){
            LoadingDialog.getInstance().startLoadingDialog(MainActivity.this, sync.getName() + " is syncing please wait.");
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
            if(discountTypesResponse.getData().size() != 0){
                discountTypesResponse.getData().forEach(dataClass -> {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                DiscountTypes discountTypes = discountTypesViewModel.fetchById(dataClass.getId());
                                if(discountTypes != null){
                                    discountTypes.setName(dataClass.getName());
                                    discountTypes.setCompanyId(dataClass.getCompany_id());
                                    discountTypes.setDepartmentId(dataClass.getDepartment_id());
                                    discountTypes.setDescription(dataClass.getDescription());
                                    discountTypes.setType(dataClass.getType());
                                    discountTypes.setDiscount(dataClass.getDiscount());
                                    discountTypes.setVatExempt(dataClass.getIs_vat_exempt() == 1 ? true : false);
                                    discountTypes.setManual(dataClass.getIs_manual() == 1 ? true : false);
                                    discountTypes.setStatus(dataClass.getStatus().equals("active") ? 1 : 0);
                                    discountTypesViewModel.update(discountTypes);
                                    discountTypeFieldsViewModel.delete(dataClass.getId());
                                    discountTypeFieldOptionsViewModel.delete(dataClass.getId());
                                    discountTypeDepartmentsViewModel.delete(dataClass.getId());
                                    dataClass.getFields().forEach(fieldsClass -> {
                                        discountTypeFieldsViewModel.insert(new DiscountTypeFields(
                                                fieldsClass.getId(),
                                                dataClass.getId(),
                                                fieldsClass.getName(),
                                                fieldsClass.getField_type(),
                                                fieldsClass.getIs_required()
                                        ));
                                        if(fieldsClass.getOptions() != null){
                                            for(String item : fieldsClass.getOptions()){
                                                if(item != null){
                                                    discountTypeFieldOptionsViewModel.insert(new DiscountTypeFieldOptions(
                                                            dataClass.getId(),
                                                            fieldsClass.getId(),
                                                            item
                                                    ));
                                                }
                                            }
                                        }
                                    });
                                    dataClass.getDepartments().forEach(departmentClass -> {
                                        discountTypeDepartmentsViewModel.insert(new DiscountTypeDepartments(
                                                departmentClass.getId(),
                                                dataClass.getId(),
                                                departmentClass.getName(),
                                                departmentClass.getDescription(),
                                                departmentClass.getStatus().equals("active") ? 1 : 0
                                        ));
                                    });
                                }
                                else{
                                    discountTypesViewModel.insert(new DiscountTypes(
                                            dataClass.getId(),
                                            dataClass.getCompany_id(),
                                            dataClass.getDepartment_id(),
                                            dataClass.getName(),
                                            dataClass.getDescription(),
                                            dataClass.getType(),
                                            dataClass.getDiscount(),
                                            dataClass.getIs_vat_exempt() == 1 ? true : false,
                                            dataClass.getStatus().equals("active") ? 1 : 0,
                                            dataClass.getIs_zero_rated() == 1 ? true : false,
                                            dataClass.getIs_manual() == 1 ? true : false
                                    ));
                                    dataClass.getFields().forEach(fieldsClass -> {
                                        discountTypeFieldsViewModel.insert(new DiscountTypeFields(
                                                fieldsClass.getId(),
                                                dataClass.getId(),
                                                fieldsClass.getName(),
                                                fieldsClass.getField_type(),
                                                fieldsClass.getIs_required()
                                        ));
                                        if(fieldsClass.getOptions() != null){
                                            for(String item : fieldsClass.getOptions()){
                                                if(item != null){
                                                    discountTypeFieldOptionsViewModel.insert(new DiscountTypeFieldOptions(
                                                            dataClass.getId(),
                                                            fieldsClass.getId(),
                                                            item
                                                    ));
                                                }
                                            }
                                        }
                                    });
                                    dataClass.getDepartments().forEach(departmentClass -> {
                                        discountTypeDepartmentsViewModel.insert(new DiscountTypeDepartments(
                                                departmentClass.getId(),
                                                dataClass.getId(),
                                                departmentClass.getName(),
                                                departmentClass.getDescription(),
                                                departmentClass.getStatus().equals("active") ? 1 : 0
                                        ));
                                    });
                                }
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                });
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sync.setUpdatedAt(date.now());
                        syncViewModel.update(sync);
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(MainActivity.this, "Done syncing discount types.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            else{
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(MainActivity.this, "There is no data to sync.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            //Shutdown the executor service
            executorService.shutdown();
        }
        else{
            try {
                Log.d("discountTypesRun", "runError: 1");
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                Log.d("discountTypesRun", "runError: 1 Done");
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void ChargeAccountSync(ChargeAccountResponse chargeAccountResponse, Sync sync, boolean success) {
        if(success){
            LoadingDialog.getInstance().startLoadingDialog(MainActivity.this, sync.getName() + " is syncing please wait.");
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
            if(chargeAccountResponse.getData().size() != 0){
                chargeAccountResponse.getData().forEach(dataClass -> {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ChargeAccount chargeAccount = chargeAccountViewModel.fetchById(dataClass.getId());
                                if(chargeAccount != null){
                                    chargeAccount.setName(dataClass.getName());
                                    chargeAccount.setCompanyId(dataClass.getCompany_id());
                                    chargeAccount.setCreditLimit(dataClass.getCredit_limit());
                                    chargeAccount.setAddress(dataClass.getAddress());
                                    chargeAccount.setContactNumber(dataClass.getContact_number());
                                    chargeAccount.setEmail(dataClass.getEmail());
                                    chargeAccountViewModel.update(chargeAccount);
                                }
                                else{
                                    chargeAccountViewModel.insert(new ChargeAccount(
                                            dataClass.getId(),
                                            dataClass.getCompany_id(),
                                            dataClass.getName(),
                                            dataClass.getCredit_limit(),
                                            dataClass.getAddress(),
                                            dataClass.getContact_number(),
                                            dataClass.getEmail()
                                    ));
                                }
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                });
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sync.setUpdatedAt(date.now());
                        syncViewModel.update(sync);
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(MainActivity.this, "Done syncing charge account.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            else{
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(MainActivity.this, "There is no data to sync.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            //Shutdown the executor service
            executorService.shutdown();
        }
        else{
            try {
                Log.d("chargeAccountRun", "runError: 1");
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                Log.d("chargeAccountRun", "runError: 1 Done");
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void ProductsSync(ProductsResponse productsResponse, Sync sync, boolean success) {
        if(success){
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
            LoadingDialog.getInstance().startLoadingDialog(MainActivity.this, sync.getName() + " is syncing please wait.");
            if(productsResponse.getData().size() != 0){
                productsResponse.getData().forEach(dataClass -> {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                Products products = productsViewModel.fetchById(dataClass.getId());
                                if(products != null){
                                    //Download Image
                                    String fileImage = products.getImage() != null ? products.getImage() : "";
                                    if(dataClass.getImage() != null && !dataClass.getImage().isEmpty()){
                                        if(!fileImage.equals(dataClass.getImage().substring(dataClass.getImage().lastIndexOf("/") + 1))){
                                            Download.getInstance().downloadFile(BuildConfig.IMAGE_URL + dataClass.getImage(), "Images");
                                            fileImage = dataClass.getImage().substring(dataClass.getImage().lastIndexOf("/") + 1);
                                        }
                                    }
                                    products.setName(dataClass.getName());
                                    products.setImage(fileImage);
                                    products.setBarcode(dataClass.getBarcode());
                                    products.setCode(dataClass.getCode());
                                    products.setDescription(dataClass.getDescription());
                                    products.setAbbreviation(dataClass.getAbbreviation());
                                    products.setPrice(dataClass.getSrp());
                                    products.setCost(dataClass.getCost());
                                    products.setWithSerial(dataClass.getWith_serial());
                                    products.setUnitId(dataClass.getUom_id());
                                    products.setDepartmentId(dataClass.getDepartment_id());
                                    products.setCategoryId(dataClass.getCategory_id());
                                    products.setSubCategoryId(dataClass.getSubcategory_id());
                                    products.setIsVatExempt(dataClass.getVat_exempt());
                                    products.setIsDiscountExempt(dataClass.getDiscount_exempt());
                                    products.setIsOpenPrice(dataClass.getOpen_price());
                                    products.setStatus(dataClass.getStatus().equals("active") ? 1 : 0);
                                    products.setShowInCashier(dataClass.getItem_type().getShow_in_cashier());
                                    products.setUnitName(dataClass.getUom().getName());
                                    products.setUnitDescription(dataClass.getUom().getDescription());
                                    productsViewModel.update(products);
                                    productsBundleItemViewModel.removeAllByProductId(dataClass.getId());
                                    if(dataClass.getBundled_items() != null){
                                        dataClass.getBundled_items().forEach(bundleClass -> {
                                            productsBundleItemViewModel.insert(new ProductsBundleItem(
                                                    dataClass.getId(),
                                                    bundleClass.getId()
                                            ));
                                        });
                                    }
                                    productLocationsViewModel.delete(dataClass.getId());
                                    if(!dataClass.getItem_locations().isEmpty()){
                                        dataClass.getItem_locations().forEach(itemLocationClass -> {
                                            productLocationsViewModel.insert(new ProductLocations(
                                                    dataClass.getId(),
                                                    itemLocationClass.getName(),
                                                    itemLocationClass.getStatus().equals("active") ? 1 : 0
                                            ));
                                        });
                                    }
                                }
                                else{
                                    //Download Image
                                    String fileImage = null;
                                    if(dataClass.getImage() != null && !dataClass.getImage().isEmpty()){
                                        Download.getInstance().downloadFile(BuildConfig.IMAGE_URL + dataClass.getImage(), "Images");
                                        fileImage = dataClass.getImage().substring(dataClass.getImage().lastIndexOf("/") + 1);
                                    }
                                    productsViewModel.insert(new Products(
                                            dataClass.getId(),
                                            fileImage,
                                            dataClass.getBarcode(),
                                            dataClass.getCode(),
                                            dataClass.getName(),
                                            dataClass.getDescription(),
                                            dataClass.getAbbreviation(),
                                            dataClass.getSrp(),
                                            dataClass.getCost(),
                                            dataClass.getMarkup(),
                                            dataClass.getWith_serial(),
                                            dataClass.getUom_id(),
                                            dataClass.getDepartment_id(),
                                            dataClass.getCategory_id(),
                                            dataClass.getSubcategory_id(),
                                            dataClass.getDiscount_exempt(),
                                            dataClass.getOpen_price(),
                                            0,
                                            dataClass.getStatus().equals("active") ? 1 : 0,
                                            dataClass.getVat_exempt(),
                                            dataClass.getItem_type().getShow_in_cashier(),
                                            dataClass.getUom().getName(),
                                            dataClass.getUom().getDescription()
                                    ));
                                    if(dataClass.getBundled_items() != null){
                                        dataClass.getBundled_items().forEach(bundleClass -> {
                                            productsBundleItemViewModel.insert(new ProductsBundleItem(
                                                    dataClass.getId(),
                                                    bundleClass.getId()
                                            ));
                                        });
                                    }
                                    if(!dataClass.getItem_locations().isEmpty()){
                                        dataClass.getItem_locations().forEach(itemLocationClass -> {
                                            productLocationsViewModel.insert(new ProductLocations(
                                                    dataClass.getId(),
                                                    itemLocationClass.getName(),
                                                    itemLocationClass.getStatus().equals("active") ? 1 : 0
                                            ));
                                        });
                                    }
                                }
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                });
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sync.setUpdatedAt(date.now());
                        syncViewModel.update(sync);
                        LoadingDialog.getInstance().closeLoadingDialog();
                        //This will update the data on the products on the menu
                        menu.sortProductDepartment();
                        Toast.makeText(MainActivity.this, "Done syncing products.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            else{
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(MainActivity.this, "There is no data to sync.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            //Shutdown the executor service
            executorService.shutdown();
        }
        else{
            try {
                Log.d("productsRun", "runError: 1");
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                Log.d("productsRun", "runError: 1 Done");
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void BranchSync(BranchInformationResponse branchInformationResponse, Sync sync, boolean success) {
        if(success){
            LoadingDialog.getInstance().startLoadingDialog(MainActivity.this, sync.getName() + " is syncing please wait.");
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Branch branch = branchViewModel.fetch();
                        if(branch != null){
                            branch.setClusterId(branchInformationResponse.getData().getCluster_id());
                            branch.setRegionId(branchInformationResponse.getData().getRegion_id());
                            branch.setProvinceId(branchInformationResponse.getData().getProvince_id());
                            branch.setCityId(branchInformationResponse.getData().getCity_id());
                            branch.setBarangayId(branchInformationResponse.getData().getBarangay_id());
                            branch.setStatus(branchInformationResponse.getData().getStatus());
                            branch.setName(branchInformationResponse.getData().getName());
                            branch.setCode(branchInformationResponse.getData().getCode());
                            branch.setUnitNumber(branchInformationResponse.getData().getUnit_floor_number());
                            branch.setStreet(branchInformationResponse.getData().getStreet());
                            branch.setSlug(branchInformationResponse.getData().getSlug());
                            branch.setClusterName(branchInformationResponse.getData().getCluster().getName());
                            branch.setRegionName(branchInformationResponse.getData().getRegion().getName());
                            branch.setProvinceName(branchInformationResponse.getData().getProvince().getName());
                            branch.setCityName(branchInformationResponse.getData().getCity().getName());
                            branch.setBarangayName(branchInformationResponse.getData().getBarangay().getName());
                            branch.setPhoneNumber(branchInformationResponse.getData().getPhone_number());
                            branchViewModel.update(branch);
                            cache.saveString("branchInfo", gson.toJson(branch));
                            POSApplication.getInstance().setBranch(branch);
                        }
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    sync.setUpdatedAt(date.now());
                                    syncViewModel.update(sync);
                                    LoadingDialog.getInstance().closeLoadingDialog();
                                    Toast.makeText(MainActivity.this, "Done syncing branch information.", Toast.LENGTH_LONG).show();
                                }
                            }, BuildConfig.APP_DEFAULT_LOADING_TIME);
                        }
                    });

                }
            });
            //Shutdown the executor service
            executorService.shutdown();
        }
        else{
            try {
                Log.d("BranchRun", "runError: 1");
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                Log.d("BranchRun", "runError: 1 Done");
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void CompanySync(CompanyInformationResponse companyInformationResponse, Sync sync, boolean success) {
        if(success){
            LoadingDialog.getInstance().startLoadingDialog(MainActivity.this, sync.getName() + " is syncing please wait.");
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Company company = companyViewModel.fetch();
                        if(company != null){
                            company.setRegisteredName(companyInformationResponse.getData().getCompany_registered_name());
                            company.setCompanyName(companyInformationResponse.getData().getCompany_name());
                            company.setLogo(companyInformationResponse.getData().getLogo());
                            company.setCountry(companyInformationResponse.getData().getCountry());
                            company.setPhoneNumber(companyInformationResponse.getData().getPhone_number());
                            company.setBarangayId(companyInformationResponse.getData().getBarangay_id());
                            company.setCityId(companyInformationResponse.getData().getCity_id());
                            company.setProvinceId(companyInformationResponse.getData().getProvince_id());
                            company.setRegionId(companyInformationResponse.getData().getRegion_id());
                            company.setSlug(companyInformationResponse.getData().getSlug());
                            company.setUnitFloorNumber(companyInformationResponse.getData().getUnit_floor_number());
                            company.setRegisteredName(companyInformationResponse.getData().getRegion().getName());
                            company.setProvinceName(companyInformationResponse.getData().getProvince().getName());
                            company.setCityName(companyInformationResponse.getData().getCity().getName());
                            company.setBarangayName(companyInformationResponse.getData().getBarangay().getName());
                            companyViewModel.update(company);
                            cache.saveString("companyInfo", gson.toJson(company));
                            POSApplication.getInstance().setCompany(company);
                        }
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    sync.setUpdatedAt(date.now());
                                    syncViewModel.update(sync);
                                    LoadingDialog.getInstance().closeLoadingDialog();
                                    Toast.makeText(MainActivity.this, "Done syncing company information.", Toast.LENGTH_LONG).show();
                                }
                            }, BuildConfig.APP_DEFAULT_LOADING_TIME);
                        }
                    });

                }
            });
            //Shutdown the executor service
            executorService.shutdown();
        }
        else{
            try {
                Log.d("CompanyRun", "runError: 1");
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                Log.d("CompanyRun", "runError: 1 Done");
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void PriceChangeReasonSync(PriceChangeReasonResponse priceChangeReasonResponse, Sync sync, boolean success) {
        if(success){
            LoadingDialog.getInstance().startLoadingDialog(MainActivity.this, sync.getName() + " is syncing please wait.");
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
            if(priceChangeReasonResponse.getData().size() != 0){
                priceChangeReasonResponse.getData().forEach(dataClass -> {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                PriceChangeReason priceChangeReason = priceChangeReasonViewModel.fetchByCoreId(dataClass.getId());
                                if(priceChangeReason != null){
                                    priceChangeReason.setName(dataClass.getName());
                                    priceChangeReason.setStatus(dataClass.getStatus().equals("active") ? 1 : 0);
                                    priceChangeReasonViewModel.update(priceChangeReason);
                                }
                                else{
                                    priceChangeReasonViewModel.insert(new PriceChangeReason(
                                            dataClass.getId(),
                                            dataClass.getCompany_id(),
                                            dataClass.getName(),
                                            dataClass.getStatus().equals("active") ? 1 : 0
                                    ));
                                }
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                });
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sync.setUpdatedAt(date.now());
                        syncViewModel.update(sync);
                        LoadingDialog.getInstance().closeLoadingDialog();
                        //This will update the data on the products on the menu
                        menu.sortProductDepartment();
                        Toast.makeText(MainActivity.this, "Done syncing price change reason.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            else{
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(MainActivity.this, "There is no data to sync.", Toast.LENGTH_LONG).show();
                    }
                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
            }
            //Shutdown the executor service
            executorService.shutdown();
        }
        else{
            try {
                Log.d("PriceChangeReasonRun", "runError: 1");
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                Log.d("PriceChangeReasonRun", "runError: 1 Done");
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}