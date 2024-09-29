package com.example.isyncpos;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isyncpos.adapters.SyncAdapter;
import com.example.isyncpos.authentication.ServerUserAuthentication;
import com.example.isyncpos.authentication.UserAuthentication;
import com.example.isyncpos.common.AuditTrail;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.Download;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.dialog.AuthenticateDialog;
import com.example.isyncpos.dialog.KioskSettingsDialog;
import com.example.isyncpos.entity.ApplicationSettings;
import com.example.isyncpos.entity.AuditTrails;
import com.example.isyncpos.entity.AuthenticatedMachineUser;
import com.example.isyncpos.entity.Branch;
import com.example.isyncpos.entity.CashDenomination;
import com.example.isyncpos.entity.Categories;
import com.example.isyncpos.entity.ChargeAccount;
import com.example.isyncpos.entity.Company;
import com.example.isyncpos.entity.Departments;
import com.example.isyncpos.entity.DeviceDetails;
import com.example.isyncpos.entity.DiscountTypeDepartments;
import com.example.isyncpos.entity.DiscountTypeFieldOptions;
import com.example.isyncpos.entity.DiscountTypeFields;
import com.example.isyncpos.entity.DiscountTypes;
import com.example.isyncpos.entity.MachineDetails;
import com.example.isyncpos.entity.PaymentTypeFieldOptions;
import com.example.isyncpos.entity.PaymentTypeFields;
import com.example.isyncpos.entity.PaymentTypes;
import com.example.isyncpos.entity.Permissions;
import com.example.isyncpos.entity.PriceChangeReason;
import com.example.isyncpos.entity.PrinterSetup;
import com.example.isyncpos.entity.ProductLocations;
import com.example.isyncpos.entity.Products;
import com.example.isyncpos.entity.ProductsBundleItem;
import com.example.isyncpos.entity.Roles;
import com.example.isyncpos.entity.SubCategories;
import com.example.isyncpos.entity.Sync;
import com.example.isyncpos.entity.Upload;
import com.example.isyncpos.entity.Users;
import com.example.isyncpos.listener.DoneOnEditorActionListener;
import com.example.isyncpos.preferences.Cache;
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
import com.example.isyncpos.viewmodel.CategoriesViewModel;
import com.example.isyncpos.viewmodel.ChargeAccountViewModel;
import com.example.isyncpos.viewmodel.CompanyViewModel;
import com.example.isyncpos.viewmodel.DepartmentsViewModel;
import com.example.isyncpos.viewmodel.DeviceDetailsViewModel;
import com.example.isyncpos.viewmodel.DiscountTypeDepartmentsViewModel;
import com.example.isyncpos.viewmodel.DiscountTypeFieldOptionsViewModel;
import com.example.isyncpos.viewmodel.DiscountTypeFieldsViewModel;
import com.example.isyncpos.viewmodel.DiscountTypesViewModel;
import com.example.isyncpos.viewmodel.MachineDetailsViewModel;
import com.example.isyncpos.viewmodel.PaymentTypeFieldOptionsViewModel;
import com.example.isyncpos.viewmodel.PaymentTypeFieldsViewModel;
import com.example.isyncpos.viewmodel.PaymentTypesViewModel;
import com.example.isyncpos.viewmodel.PermissionsViewModel;
import com.example.isyncpos.viewmodel.PriceChangeReasonViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupViewModel;
import com.example.isyncpos.viewmodel.ProductLocationsViewModel;
import com.example.isyncpos.viewmodel.ProductsBundleItemViewModel;
import com.example.isyncpos.viewmodel.ProductsViewModel;
import com.example.isyncpos.viewmodel.RolesViewModel;
import com.example.isyncpos.viewmodel.SubCategoriesViewModel;
import com.example.isyncpos.viewmodel.SyncViewModel;
import com.example.isyncpos.viewmodel.UploadViewModel;
import com.example.isyncpos.viewmodel.UsersViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class LoginActivity extends AppCompatActivity implements SyncAdapter.SyncListener {

    private TextView lblVersion;
    private EditText textUsername, textPassword;
    private MaterialButton loginButton, SettingsButton;
    private SyncViewModel syncViewModel;
    private CashDenominationViewModel cashDenominationViewModel;
    private DepartmentsViewModel departmentsViewModel;
    private CategoriesViewModel categoriesViewModel;
    private SubCategoriesViewModel subCategoriesViewModel;
    private UsersViewModel usersViewModel;
    private PaymentTypesViewModel paymentTypesViewModel;
    private DiscountTypesViewModel discountTypesViewModel;
    private ChargeAccountViewModel chargeAccountViewModel;
    private ProductsViewModel productsViewModel;
    private MachineDetailsViewModel machineDetailsViewModel;
    private AuditTrailsViewModel auditTrailsViewModel;
    private AuthenticatedMachineUserViewModel authenticatedMachineUserViewModel;
    private BranchViewModel branchViewModel;
    private CompanyViewModel companyViewModel;
    private ProductsBundleItemViewModel productsBundleItemViewModel;
    private DeviceDetailsViewModel deviceDetailsViewModel;
    private PaymentTypeFieldsViewModel paymentTypeFieldsViewModel;
    private PaymentTypeFieldOptionsViewModel paymentTypeFieldOptionsViewModel;
    private DiscountTypeFieldsViewModel discountTypeFieldsViewModel;
    private DiscountTypeFieldOptionsViewModel discountTypeFieldOptionsViewModel;
    private DiscountTypeDepartmentsViewModel discountTypeDepartmentsViewModel;
    private RolesViewModel rolesViewModel;
    private PermissionsViewModel permissionsViewModel;
    private PrinterSetupViewModel printerSetupViewModel;
    private ApplicationSettingsViewModel applicationSettingsViewModel;
    private UploadViewModel uploadViewModel;
    private PriceChangeReasonViewModel priceChangeReasonViewModel;
    private ProductLocationsViewModel productLocationsViewModel;
    private Cache cache;
    private Dates date;
    private Gson gson = new Gson();
    private MachineDetails machineDetails;
    private String[] permissions = new String[]{
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.BLUETOOTH,
        Manifest.permission.BLUETOOTH_ADMIN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.BLUETOOTH_SCAN
//        Manifest.permission.CAMERA
    };
    private int totalProductToSync = 0;
    private Handler productSyncHandler = new Handler();
    private Runnable productSyncRunnable;
    private SecondScreenLoginPresentation secondScreenPresentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialize
        initialize();
        checkForClearCache();
        initMachineTextFile();
        checkEnvironment();
        checkLogin();
        initLoginSecondScreen();

        //Set
        if(BuildConfig.ENV.equals("DEVELOPMENT")){
            lblVersion.setText("VERSION BUILD: UAT " + BuildConfig.APP_VERSION);
        }
        else{
            lblVersion.setText("VERSION BUILD: PROD " + BuildConfig.APP_VERSION);
        }

        loginButton.setOnFocusChangeListener(new DoneOnEditorActionListener());
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setEnabled(false);
                if(checkAuthenticatedMachineUser() && checkMachineDetails()){
                    authenticate(textUsername.getText().toString(), textPassword.getText().toString());
                }
                else{
                    loginButton.setEnabled(true);
                    Toast.makeText(getApplication(), "There is no machine details.", Toast.LENGTH_LONG).show();
                }
            }
        });

        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kioskSettings();
            }
        });

        //Set Audit Trail Args
        AuditTrail.getInstance().setArgs(auditTrailsViewModel);

        //This will override the back button on the phone for your app
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });

        //This will test for the error logs of the application when it crush
        //Comment this if you are not testing anymore
        //throw new RuntimeException("Test Exception");

    }

    private void initialize(){
        cache = new Cache(getApplicationContext());
        textUsername = findViewById(R.id.textUsername);
        textPassword = findViewById(R.id.textPassword);
        loginButton = findViewById(R.id.btnAuthenticate);
        lblVersion = findViewById(R.id.lblVersion);
        SettingsButton = findViewById(R.id.btnKioskConfirm);
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        auditTrailsViewModel = new ViewModelProvider(this).get(AuditTrailsViewModel.class);
        machineDetailsViewModel = new ViewModelProvider(this).get(MachineDetailsViewModel.class);
        authenticatedMachineUserViewModel = new ViewModelProvider(this).get(AuthenticatedMachineUserViewModel.class);
        syncViewModel = new ViewModelProvider(this).get(SyncViewModel.class);
        cashDenominationViewModel = new ViewModelProvider(this).get(CashDenominationViewModel.class);
        departmentsViewModel = new ViewModelProvider(this).get(DepartmentsViewModel.class);
        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        subCategoriesViewModel = new ViewModelProvider(this).get(SubCategoriesViewModel.class);
        paymentTypesViewModel = new ViewModelProvider(this).get(PaymentTypesViewModel.class);
        discountTypesViewModel = new ViewModelProvider(this).get(DiscountTypesViewModel.class);
        chargeAccountViewModel = new ViewModelProvider(this).get(ChargeAccountViewModel.class);
        productsViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);
        branchViewModel = new ViewModelProvider(this).get(BranchViewModel.class);
        companyViewModel = new ViewModelProvider(this).get(CompanyViewModel.class);
        productsBundleItemViewModel = new ViewModelProvider(this).get(ProductsBundleItemViewModel.class);
        deviceDetailsViewModel = new ViewModelProvider(this).get(DeviceDetailsViewModel.class);
        paymentTypeFieldsViewModel = new ViewModelProvider(this).get(PaymentTypeFieldsViewModel.class);
        paymentTypeFieldOptionsViewModel = new ViewModelProvider(this).get(PaymentTypeFieldOptionsViewModel.class);
        discountTypeFieldsViewModel = new ViewModelProvider(this).get(DiscountTypeFieldsViewModel.class);
        discountTypeFieldOptionsViewModel = new ViewModelProvider(this).get(DiscountTypeFieldOptionsViewModel.class);
        discountTypeDepartmentsViewModel = new ViewModelProvider(this).get(DiscountTypeDepartmentsViewModel.class);
        rolesViewModel = new ViewModelProvider(this).get(RolesViewModel.class);
        permissionsViewModel = new ViewModelProvider(this).get(PermissionsViewModel.class);
        printerSetupViewModel = new ViewModelProvider(this).get(PrinterSetupViewModel.class);
        applicationSettingsViewModel = new ViewModelProvider(this).get(ApplicationSettingsViewModel.class);
        uploadViewModel = new ViewModelProvider(this).get(UploadViewModel.class);
        priceChangeReasonViewModel = new ViewModelProvider(this).get(PriceChangeReasonViewModel.class);
        productLocationsViewModel = new ViewModelProvider(this).get(ProductLocationsViewModel.class);
        date = new Dates();
        initApplicationPermission();
        checkBranchInfo();
        checkCompanyInfo();
        checkDeviceDetails();
        checkAuthMachineUser();
    }

    private void initMachineTextFile(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if(Environment.isExternalStorageManager()){
                File file = new File(Environment.getExternalStorageDirectory(), "Documents/machine.txt");
                if(!file.exists()){
                    try {
                        MachineDetails machDetails = machineDetailsViewModel.fetchMachineDetails();
                        DeviceDetails devDetails = deviceDetailsViewModel.fetch();
                        if(machDetails != null && devDetails != null){
                            createMachineTextFile(machDetails.getCoreId(), machDetails.getProductKey(), devDetails.getCoreId());
                        }
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void initManageApplicationStorage(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if(!Environment.isExternalStorageManager()){
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }
    }

    private void initLoginSecondScreen(){
        DisplayManager displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = displayManager.getDisplays();
        if(displays.length > 1){
            Display secondDisplay = displays[1];
            secondScreenPresentation = new SecondScreenLoginPresentation(this, secondDisplay);
            secondScreenPresentation.show();
        }
        else{
            Log.d("SecondScreen", "No second display available");
        }
    }

    private void createMachineTextFile(int machineId, String productKey, int deviceId){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if(Environment.isExternalStorageManager()){
                    String content = String.valueOf(machineId) + "," + productKey + "," + String.valueOf(deviceId);
                    File file = new File(Environment.getExternalStorageDirectory(), "Documents/machine.txt");
                    if (file.exists()){
                        file.delete();
                    }
                    File root = new File(Environment.getExternalStorageDirectory(), "Documents");
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(root, "machine.txt"));
                    fileOutputStream.write(content.getBytes());
                    fileOutputStream.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkMachineDetails(){
        //Check if has cache for machine details
        if(cache.getString("machineDetails").equals(BuildConfig.CACHE_DEFAULT_VALUE)){
            try {
                machineDetails = machineDetailsViewModel.fetchMachineDetails();
                if(machineDetails != null){
                    POSApplication.getInstance().setMachineDetails(machineDetails);
                    cache.saveString("machineDetails", gson.toJson(machineDetails));
                    return true;
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            machineDetails = gson.fromJson(cache.getString("machineDetails"), MachineDetails.class);
            POSApplication.getInstance().setMachineDetails(machineDetails);
            return true;
        }
        return false;
    }

    private void checkForClearCache(){
        try {
            machineDetails = machineDetailsViewModel.fetchMachineDetails();
            if(machineDetails == null){
                cache.clearCache();
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void authenticate(String username, String password){
        if(username.isEmpty()) {
            Toast.makeText(this, "Please input a username.", Toast.LENGTH_LONG).show();
            textUsername.requestFocus();
            loginButton.setEnabled(true);
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Please input a password.", Toast.LENGTH_LONG).show();
            textPassword.requestFocus();
            loginButton.setEnabled(true);
        }
        else{
            LoadingDialog.getInstance().startLoadingDialog(LoginActivity.this, "Authenticating...");
            try {
                Users users = usersViewModel.authenticate(username);
                if(users != null){
                    if(BCrypt.verifyer().verify(password.toCharArray(), users.getPassword()).verified){
                        List<Roles> rolesList = rolesViewModel.fetchByUserId(users.getCoreId());
                        UserAuthentication userAuthentication = new UserAuthentication(users.getCoreId(), users.getName(), users.getUsername(), users.getAccessToken(), rolesList);
                        POSApplication.getInstance().setUserAuthentication(userAuthentication);
                        cache.saveString("userAuthentication", gson.toJson(userAuthentication));
                        //Save Audit Trail
                        AuditTrail.getInstance().save(users.getCoreId(), users.getName(), 0, "LOGIN", "Successful login attempt using credential of " + username, 0, "", 0, 0);
                        startMainActivity();
                    }
                    else{
                        clearLoginForm();
                        loginButton.setEnabled(true);
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Toast.makeText(getApplication(), "Invalid username or password.", Toast.LENGTH_LONG).show();
                        AuditTrail.getInstance().save(users.getCoreId(), users.getName(), 0, "LOGIN", "Failed login attempt using credential of " + username, 0, "", 0, 0);
                    }
                }
                else{
                    clearLoginForm();
                    loginButton.setEnabled(true);
                    LoadingDialog.getInstance().closeLoadingDialog();
                    Toast.makeText(getApplication(), "Invalid username or password.", Toast.LENGTH_LONG).show();
                    AuditTrail.getInstance().save(0, "", 0, "LOGIN", "Failed login attempt using credential of " + username, 0, "", 0, 0);
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void kioskSettings(){
        try {
            //This will check if you have a permission on the device to manage it
            initManageApplicationStorage();
            AuthenticatedMachineUser authenticatedMachineUser = authenticatedMachineUserViewModel.fetch();
            if(authenticatedMachineUser != null){
                POSApplication.getInstance().setServerUserAuthentication(new ServerUserAuthentication(authenticatedMachineUser.getId(), authenticatedMachineUser.getName(), authenticatedMachineUser.getUsername(), authenticatedMachineUser.getToken()));
                cache.saveString("serverUserAuthentication", gson.toJson(POSApplication.getInstance().getServerUserAuthentication()));
                KioskSettingsDialog kioskSettingsDialog = KioskSettingsDialog.newKioskSettingsDialog();
                kioskSettingsDialog.setArgs(
                    machineDetailsViewModel,
                    branchViewModel,
                    companyViewModel,
                    syncViewModel,
                    deviceDetailsViewModel,
                    usersViewModel
                );
                kioskSettingsDialog.setCancelable(false);
                kioskSettingsDialog.show(getSupportFragmentManager(), "Kiosk Settings");
            }
            else{
                AuthenticateDialog authenticateDialog = new AuthenticateDialog(this) {
                    @Override
                    public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                        if(success){
                            ExecutorService executorService = Executors.newSingleThreadExecutor();
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {

                                    sync();

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //The Code Is For Here
                                            KioskSettingsDialog kioskSettingsDialog = KioskSettingsDialog.newKioskSettingsDialog();
                                            kioskSettingsDialog.setArgs(
                                                machineDetailsViewModel,
                                                branchViewModel,
                                                companyViewModel,
                                                syncViewModel,
                                                deviceDetailsViewModel,
                                                usersViewModel
                                            );
                                            kioskSettingsDialog.setCancelable(false);
                                            kioskSettingsDialog.show(getSupportFragmentManager(), "Kiosk Settings");
                                        }
                                    });

                                }
                            });
                        }
                        else{
                            Toast.makeText(getContext(), "Invalid username and password.", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                authenticateDialog.setOnlineAuthenticateViewModels(authenticatedMachineUserViewModel);
                authenticateDialog.setAuthenticateLocal(false);
                authenticateDialog.show();
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void sync(){
        try {
            List<Sync> syncs = syncViewModel.fetchSyncList();
            if(syncs.isEmpty()){
                syncViewModel.insert(new Sync("Cash Denominations", 0, 0, date.now(), ""));
                syncViewModel.insert(new Sync("Departments", 0, 0, date.now(), ""));
                syncViewModel.insert(new Sync("Categories", 0, 0, date.now(), ""));
                syncViewModel.insert(new Sync("Sub Categories", 0,0, date.now(), ""));
                syncViewModel.insert(new Sync("Users", 0, 0, date.now(), ""));
                syncViewModel.insert(new Sync("Payment Type", 0, 0, date.now(), ""));
                syncViewModel.insert(new Sync("Discount Type", 0, 0, date.now(), ""));
                syncViewModel.insert(new Sync("Charge Account", 0, 0, date.now(), ""));
                syncViewModel.insert(new Sync("Products", 0, 0, date.now(), ""));
                syncViewModel.insert(new Sync("Price Change Reason", 0, 0, date.now(), ""));
                syncViewModel.insert(new Sync("Branch Information", 1, 1, date.now(), date.now()));
                syncViewModel.insert(new Sync("Company Information", 1, 1, date.now(), date.now()));
            }
            List<PrinterSetup> printerSetups = printerSetupViewModel.fetchPrinsterSetupList();
            if(printerSetups.isEmpty()){
                printerSetupViewModel.insert(new PrinterSetup("RESUME TRANSACTION", 0, 1, 0, "cashier", date.now()));
                printerSetupViewModel.insert(new PrinterSetup("BACKOUT", 0, 1, 0, "both", date.now()));
                printerSetupViewModel.insert(new PrinterSetup("PAYOUT", 0, 1, 0, "cashier", date.now()));
                printerSetupViewModel.insert(new PrinterSetup("CASH FUND", 0, 1, 0, "cashier", date.now()));
                printerSetupViewModel.insert(new PrinterSetup("STATEMENT OF ACCOUNT",0, 1, 0, "cashier", date.now()));
                printerSetupViewModel.insert(new PrinterSetup("OFFICIAL RECEIPT", 0, 1, 1, "cashier", date.now()));
                printerSetupViewModel.insert(new PrinterSetup("X READING", 0, 1, 0, "cashier", date.now()));
                printerSetupViewModel.insert(new PrinterSetup("Z READING", 0, 1, 0, "cashier", date.now()));
                printerSetupViewModel.insert(new PrinterSetup("PRODUCT LIST", 0, 1, 0, "cashier", date.now()));
                printerSetupViewModel.insert(new PrinterSetup("POST VOID", 0, 1, 0, "cashier", date.now()));
                printerSetupViewModel.insert(new PrinterSetup("ITEM VOID", 0, 1, 0, "both", date.now()));
                printerSetupViewModel.insert(new PrinterSetup("REPRINT", 0, 1, 0, "cashier", date.now()));
                printerSetupViewModel.insert(new PrinterSetup("SAFEKEEPING", 0, 1, 0, "cashier", date.now()));
                printerSetupViewModel.insert(new PrinterSetup("SPOT AUDIT", 0, 1, 0, "cashier", date.now()));
                printerSetupViewModel.insert(new PrinterSetup("TAKE ORDER RECEIPT", 0, 1, 0, "take order", date.now()));
                printerSetupViewModel.insert(new PrinterSetup("REPRINT X READING", 0, 1, 0, "cashier", date.now()));
                printerSetupViewModel.insert(new PrinterSetup("REPRINT Z READING", 0, 1, 0, "cashier", date.now()));
            }
            ApplicationSettings applicationSettings = applicationSettingsViewModel.fetch();
            if(applicationSettings == null){
                applicationSettingsViewModel.insert(new ApplicationSettings(0, 45, 70, 1, 0));
            }
            List<Upload> uploads = uploadViewModel.fetchAll();
            if(uploads.isEmpty()){
                uploadViewModel.insert(new Upload("Transactions", "cashier"));
                uploadViewModel.insert(new Upload("Orders", "cashier"));
                uploadViewModel.insert(new Upload("Payments", "cashier"));
                uploadViewModel.insert(new Upload("Machine Details", "cashier"));
                uploadViewModel.insert(new Upload("Discounts", "cashier"));
                uploadViewModel.insert(new Upload("Discount Details", "cashier"));
                uploadViewModel.insert(new Upload("Cutoff", "cashier"));
                uploadViewModel.insert(new Upload("End Of Day", "cashier"));
                uploadViewModel.insert(new Upload("Audit Trails", "cashier"));
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearLoginForm(){
        textUsername.setText("");
        textPassword.setText("");
        textUsername.requestFocus();
    }

    private void checkBranchInfo(){
        if(cache.getString("branchInfo") != BuildConfig.CACHE_DEFAULT_VALUE){
            Branch branch = gson.fromJson(cache.getString("branchInfo"), Branch.class);
            POSApplication.getInstance().setBranch(branch);
        }
        else{
            try {
                Branch branch = branchViewModel.fetch();
                if(branch != null){
                    cache.saveString("branchInfo", gson.toJson(branch));
                    POSApplication.getInstance().setBranch(branch);
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void checkCompanyInfo(){
        if(cache.getString("companyInfo") != BuildConfig.CACHE_DEFAULT_VALUE){
            Company company = gson.fromJson(cache.getString("companyInfo"), Company.class);
            POSApplication.getInstance().setCompany(company);
        }
        else{
            try {
                Company company = companyViewModel.fetch();
                if(company != null){
                    cache.saveString("companyInfo", gson.toJson(company));
                    POSApplication.getInstance().setCompany(company);
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void checkDeviceDetails(){
        if(cache.getString("deviceDetailsInfo") != BuildConfig.CACHE_DEFAULT_VALUE){
            DeviceDetails deviceDetails = gson.fromJson(cache.getString("deviceDetailsInfo"), DeviceDetails.class);
            POSApplication.getInstance().setDeviceDetails(deviceDetails);
        }
        else{
            try {
                DeviceDetails deviceDetails = deviceDetailsViewModel.fetch();
                if(deviceDetails != null){
                    cache.saveString("deviceDetailsInfo", gson.toJson(deviceDetails));
                    POSApplication.getInstance().setDeviceDetails(deviceDetails);
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void checkAuthMachineUser(){
        if(cache.getString("serverUserAuthentication") != BuildConfig.CACHE_DEFAULT_VALUE){
            ServerUserAuthentication serverUserAuthentication = gson.fromJson(cache.getString("serverUserAuthentication"), ServerUserAuthentication.class);
            POSApplication.getInstance().setServerUserAuthentication(serverUserAuthentication);
        }
        else{
            try {
                AuthenticatedMachineUser authenticatedMachineUser = authenticatedMachineUserViewModel.fetch();
                if(authenticatedMachineUser != null){
                    cache.saveString("userAuthentication", gson.toJson(POSApplication.getInstance().getServerUserAuthentication()));
                    POSApplication.getInstance().setServerUserAuthentication(new ServerUserAuthentication(authenticatedMachineUser.getId(), authenticatedMachineUser.getName(), authenticatedMachineUser.getUsername(), authenticatedMachineUser.getToken()));
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void checkLogin(){
        if(BuildConfig.POS_CHECK_CACHE_LOGIN != 0){
            if(checkAuthenticatedMachineUser()){
                if(cache.getString("userAuthentication") != BuildConfig.CACHE_DEFAULT_VALUE){
                    UserAuthentication userAuthentication = gson.fromJson(cache.getString("userAuthentication"), UserAuthentication.class);
                    machineDetails = gson.fromJson(cache.getString("machineDetails"), MachineDetails.class);
                    POSApplication.getInstance().setMachineDetails(machineDetails);
                    POSApplication.getInstance().setUserAuthentication(userAuthentication);
                    checkBranchInfo();
                    checkCompanyInfo();
                    checkDeviceDetails();
                    checkAuthMachineUser();
                    startMainActivity();
                    finish();
                }
            }
        }
    }

    private boolean checkAuthenticatedMachineUser(){
        if(cache.getString("serverUserAuthentication") != BuildConfig.CACHE_DEFAULT_VALUE){
            ServerUserAuthentication serverUserAuthentication = gson.fromJson(cache.getString("serverUserAuthentication"), ServerUserAuthentication.class);
            POSApplication.getInstance().setServerUserAuthentication(serverUserAuthentication);
            return true;
        }
        else{
            try {
                AuthenticatedMachineUser authenticatedMachineUser = authenticatedMachineUserViewModel.fetch();
                if(authenticatedMachineUser != null){
                    POSApplication.getInstance().setServerUserAuthentication(new ServerUserAuthentication(authenticatedMachineUser.getId(), authenticatedMachineUser.getName(), authenticatedMachineUser.getUsername(), authenticatedMachineUser.getToken()));
                    cache.saveString("serverUserAuthentication", gson.toJson(POSApplication.getInstance().getServerUserAuthentication()));
                    return true;
                }
                else{
                    return false;
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void startMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    private boolean hasPermissions(Context context, String... permissions){
        if(context != null && permissions != null){
            for(String item : permissions){
                if(ActivityCompat.checkSelfPermission(context, item) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    private Boolean initApplicationPermission(){
        if(!hasPermissions(this, permissions)){
            ActivityCompat.requestPermissions(this, permissions, 1);
            return false;
        }
        return true;
    }

    private void checkEnvironment(){
        if(BuildConfig.ENV.equals("PRODUCTION")){
            textUsername.setText("");
            textPassword.setText("");
        }
    }

    private void updateSync(int syncId){
        try {
            Sync syncDataSecond = syncViewModel.fetchById(syncId);
            syncDataSecond.setIsSync(1);
            syncDataSecond.setUpdatedAt(date.now());
            if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
            syncViewModel.update(syncDataSecond);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void CashDenominationSync(CashDenominationResponse cashDenominationResponse, Sync sync, boolean success) {
        if(success){
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
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
            try {
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                //Shutdown the executor service
                executorService.shutdown();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
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
            try {
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                //Shutdown the executor service
                executorService.shutdown();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
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
            try {
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                //Shutdown the executor service
                executorService.shutdown();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
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
            try {
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                //Shutdown the executor service
                executorService.shutdown();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
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
            try {
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                //Shutdown the executor service
                executorService.shutdown();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
            paymentTypesResponse.getData().forEach(dataClass -> {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            PaymentTypes paymentTypes = paymentTypesViewModel.fetchById(dataClass.getId());
                            if(paymentTypes != null){
                                paymentTypes.setName(dataClass.getName());
                                paymentTypes.setIsAR(dataClass.getIs_ar());
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
            try {
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                //Shutdown the executor service
                executorService.shutdown();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
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
            try {
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                //Shutdown the executor service
                executorService.shutdown();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
    public void ChargeAccountSync(ChargeAccountResponse chargeAccountResponse, Sync sync,boolean success) {
        if(success){
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
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
            try {
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                //Shutdown the executor service
                executorService.shutdown();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
            totalProductToSync = productsResponse.getData().size();
            productSyncRunnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Long productCount = productsViewModel.fetchProductCount();
                        if(productCount.intValue() >= totalProductToSync){
                            productSyncHandler.removeCallbacks(productSyncRunnable);
                            updateSync(sync.getId());
                            executorService.shutdown();
                        }
                        else{
                            productSyncHandler.postDelayed(productSyncRunnable, 1500);
                        }
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            productSyncHandler.post(productSyncRunnable);
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
                }
            });
            try {
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                //Shutdown the executor service
                executorService.shutdown();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
                }
            });
            try {
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                //Shutdown the executor service
                executorService.shutdown();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
            ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
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
            try {
                Sync syncDataSecond = syncViewModel.fetchById(sync.getId());
                syncDataSecond.setIsSync(1);
                syncDataSecond.setUpdatedAt(date.now());
                if(syncDataSecond.getIsFirstSync() == 0) syncDataSecond.setIsFirstSync(1);
                syncViewModel.update(syncDataSecond);
                //Shutdown the executor service
                executorService.shutdown();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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

    @Override
    protected void onDestroy() {
        LoadingDialog.getInstance().closeLoadingDialog();
        if(secondScreenPresentation != null){
            secondScreenPresentation.dismiss();
            secondScreenPresentation = null;
        }
        productSyncHandler.removeCallbacks(productSyncRunnable);
        super.onDestroy();
    }

}