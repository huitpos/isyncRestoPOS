package com.example.isyncpos.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.LoginActivity;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.SettingsActivity;
import com.example.isyncpos.authentication.UserAuthentication;
import com.example.isyncpos.common.Backup;
import com.example.isyncpos.common.EncryptDecrypt;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.common.APIRequest;
import com.example.isyncpos.dialog.AuthenticateDialog;
import com.example.isyncpos.dialog.CashFundDialog;
import com.example.isyncpos.dialog.CutoffDialog;
import com.example.isyncpos.dialog.OrdersDialog;
import com.example.isyncpos.dialog.PayoutDialog;
import com.example.isyncpos.dialog.ResumeTransactionDialog;
import com.example.isyncpos.dialog.SafekeepingDialog;
import com.example.isyncpos.dialog.SpotAuditDialog;
import com.example.isyncpos.dialog.SyncDialog;
import com.example.isyncpos.dialog.ViewAccountReceivableDialog;
import com.example.isyncpos.dialog.ViewReceiptDialog;
import com.example.isyncpos.entity.CutOff;
import com.example.isyncpos.entity.DiscountDetails;
import com.example.isyncpos.entity.Discounts;
import com.example.isyncpos.entity.EndOfDay;
import com.example.isyncpos.entity.MachineDetails;
import com.example.isyncpos.entity.Orders;
import com.example.isyncpos.entity.Payments;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.entity.Upload;
import com.example.isyncpos.preferences.Cache;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.response.entity.ErrorBody;
import com.example.isyncpos.response.entity.TestConnectionResponse;
import com.example.isyncpos.viewmodel.CutOffViewModel;
import com.example.isyncpos.viewmodel.DiscountDetailsViewModel;
import com.example.isyncpos.viewmodel.DiscountsViewModel;
import com.example.isyncpos.viewmodel.EndOfDayProductsViewModel;
import com.example.isyncpos.viewmodel.EndOfDayViewModel;
import com.example.isyncpos.viewmodel.MachineDetailsViewModel;
import com.example.isyncpos.viewmodel.OrdersViewModel;
import com.example.isyncpos.viewmodel.PaymentsViewModel;
import com.example.isyncpos.viewmodel.PermissionsViewModel;
import com.example.isyncpos.viewmodel.RolesViewModel;
import com.example.isyncpos.viewmodel.SyncViewModel;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.example.isyncpos.viewmodel.UploadViewModel;
import com.example.isyncpos.viewmodel.UsersViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public class Navigation extends Fragment {

    private MaterialButton btnNavLogout, btnNavSettings, btnNavResumeTransaction, btnNavBackup, btnNavPayout, btnNavSafekeeping, btnNavViewReceipt, btnNavSync, btnNavCutoff, btnNavCashFund, btnNavSpotAudit, btnNavOrders, btnNavUpload, btnNavARRedeeming;
    private SyncViewModel syncViewModel;
    private TransactionsViewModel transactionsViewModel;
    private OrdersViewModel ordersViewModel;
    private PaymentsViewModel paymentsViewModel;
    private MachineDetailsViewModel machineDetailsViewModel;
    private DiscountsViewModel discountsViewModel;
    private DiscountDetailsViewModel discountDetailsViewModel;
    private CutOffViewModel cutOffViewModel;
    private EndOfDayViewModel endOfDayViewModel;
    private UploadViewModel uploadViewModel;
    private EndOfDayProductsViewModel endOfDayProductsViewModel;
    private UsersViewModel usersViewModel;
    private RolesViewModel rolesViewModel;
    private PermissionsViewModel permissionsViewModel;
    private Cache cache;
    private POSProcess posProcess;
    private Gson gson;
    private Backup backup;

    interface RequestAPI {
        @Headers({
                "accept: application/json"
        })
        @GET("/api/test-connection")
        Call<TestConnectionResponse> TestConnection(@Header("Authorization") String authorization);
    }

    public static Navigation newNavigation(){
        return new Navigation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        //Initialize
        initialize();

        //Set
        setNavigationButtons();

        btnNavLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to logout?");
                builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnNavSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings();
            }
        });

        btnNavResumeTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeTransaction();
            }
        });

        btnNavBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirmation");
                builder.setMessage("This process will restart your application. Are you sure you want to backup your application data?");
                builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_BACKUP)){
                            backup();
                        }
                        else{
                            AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                                @Override
                                public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                    if(success){
                                        backup();
                                    }
                                    else{
                                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                            authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_BACKUP);
                            authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                            authenticateDialog.setCancelable(false);
                            authenticateDialog.show();
                        }
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnNavPayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payouts();
            }
        });

        btnNavSafekeeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                safekeeping();
            }
        });

        btnNavViewReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewReceipt();
            }
        });

        btnNavSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sync();
            }
        });

        btnNavCutoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cutoff();
            }
        });

        btnNavCashFund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashFund();
            }
        });

        btnNavSpotAudit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spotAudit();
            }
        });

        btnNavOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orders();
            }
        });

        btnNavUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirmation");
                builder.setMessage("Upload unsync data to the server?");
                builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_UPLOAD_SERVER)){
                            uploadToServer();
                        }
                        else{
                            AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                                @Override
                                public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                    if(success){
                                        uploadToServer();
                                    }
                                    else{
                                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                            authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_UPLOAD_SERVER);
                            authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                            authenticateDialog.setCancelable(false);
                            authenticateDialog.show();
                        }
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnNavARRedeeming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountReceivables();
            }
        });

    }

    private void initialize(){
        initViewModels();
        cache = new Cache(getActivity());
        gson = new Gson();
        backup = new Backup(getActivity(), getContext());
        POSApplication.getInstance().setBackup(backup);
        posProcess = POSApplication.getInstance().getPosProcess();
        btnNavSettings = getView().findViewById(R.id.btnNavSettings);
        btnNavLogout = getView().findViewById(R.id.btnNavLogout);
        btnNavResumeTransaction = getView().findViewById(R.id.btnNavResumeTransaction);
        btnNavBackup = getView().findViewById(R.id.btnNavBackup);
        btnNavPayout = getView().findViewById(R.id.btnNavPayout);
        btnNavSafekeeping = getView().findViewById(R.id.btnNavSafekeeping);
        btnNavViewReceipt = getView().findViewById(R.id.btnNavViewReceipt);
        btnNavSync = getView().findViewById(R.id.btnNavSync);
        btnNavCutoff = getView().findViewById(R.id.btnNavCutoff);
        btnNavCashFund = getView().findViewById(R.id.btnNavCashFund);
        btnNavSpotAudit = getView().findViewById(R.id.btnNavSpotAudit);
        btnNavOrders = getView().findViewById(R.id.btnNavOrders);
        btnNavUpload = getView().findViewById(R.id.btnNavUpload);
        btnNavARRedeeming = getView().findViewById(R.id.btnNavARRedeeming);
    }

    private void initViewModels(){
        syncViewModel = POSApplication.getInstance().getSyncViewModel();
        transactionsViewModel = POSApplication.getInstance().getTransactionsViewModel();
        ordersViewModel = POSApplication.getInstance().getOrdersViewModel();
        paymentsViewModel = POSApplication.getInstance().getPaymentsViewModel();
        machineDetailsViewModel = POSApplication.getInstance().getMachineDetailsViewModel();
        discountsViewModel = POSApplication.getInstance().getDiscountsViewModel();
        discountDetailsViewModel = POSApplication.getInstance().getDiscountDetailsViewModel();
        cutOffViewModel = POSApplication.getInstance().getCutOffViewModel();
        endOfDayViewModel = POSApplication.getInstance().getEndOfDayViewModel();
        uploadViewModel = POSApplication.getInstance().getUploadViewModel();
        endOfDayProductsViewModel = POSApplication.getInstance().getEndOfDayProductsViewModel();
        usersViewModel = POSApplication.getInstance().getUsersViewModel();
        rolesViewModel = POSApplication.getInstance().getRolesViewModel();
        permissionsViewModel = POSApplication.getInstance().getPermissionsViewModel();
    }

    private void setNavigationButtons(){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            btnNavOrders.setVisibility(View.VISIBLE);
            btnNavViewReceipt.setVisibility(View.VISIBLE);
            btnNavCutoff.setVisibility(View.VISIBLE);
            btnNavPayout.setVisibility(View.VISIBLE);
            btnNavCashFund.setVisibility(View.VISIBLE);
            btnNavSafekeeping.setVisibility(View.VISIBLE);
            btnNavSpotAudit.setVisibility(View.VISIBLE);
            btnNavBackup.setVisibility(View.VISIBLE);
            if(BuildConfig.APP_NAVIGATION_HAS_AR == 1){
                btnNavARRedeeming.setVisibility(View.VISIBLE);
            }
            else{
                btnNavARRedeeming.setVisibility(View.GONE);
            }
        }
        else{
            btnNavOrders.setVisibility(View.GONE);
            btnNavViewReceipt.setVisibility(View.GONE);
            btnNavCutoff.setVisibility(View.GONE);
            btnNavPayout.setVisibility(View.GONE);
            btnNavCashFund.setVisibility(View.GONE);
            btnNavSafekeeping.setVisibility(View.GONE);
            btnNavSpotAudit.setVisibility(View.GONE);
            btnNavBackup.setVisibility(View.GONE);
            btnNavARRedeeming.setVisibility(View.GONE);
        }
    }

    private void Settings(){
        if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_SETTINGS)){
            cache.clearString("CURRENT_SETTINGS_TAB");
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            startActivity(intent);
        }
        else{
            AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                @Override
                public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                    if(success){
                        cache.clearString("CURRENT_SETTINGS_TAB");
                        Intent intent = new Intent(getContext(), SettingsActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }
                }
            };
            authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_SETTINGS);
            authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
            authenticateDialog.setCancelable(false);
            authenticateDialog.show();
        }
    }

    private void resumeTransaction(){
        if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_RESUME_TRANSACTION)){
            ResumeTransactionDialog resumeTransactionDialog = ResumeTransactionDialog.newResumeTransactionDialog();
            resumeTransactionDialog.setCancelable(false);
            resumeTransactionDialog.show(getActivity().getSupportFragmentManager(), "Resume Transaction Dialog");
        }
        else{
            AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                @Override
                public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                    if(success){
                        ResumeTransactionDialog resumeTransactionDialog = ResumeTransactionDialog.newResumeTransactionDialog();
                        resumeTransactionDialog.setCancelable(false);
                        resumeTransactionDialog.show(getActivity().getSupportFragmentManager(), "Resume Transaction Dialog");
                    }
                    else{
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }
                }
            };
            authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_RESUME_TRANSACTION);
            authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
            authenticateDialog.setCancelable(false);
            authenticateDialog.show();
        }
    }

    public void logout(){
        LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Logging out please wait....");
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                POSApplication.getInstance().removeUserAuthentication();
                cache.clearString("userAuthentication");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }, BuildConfig.APP_DEFAULT_LOADING_TIME);
    }

    private void payouts(){
        if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_PAYOUT)){
            PayoutDialog payoutDialog = PayoutDialog.newPayoutDialog();
            payoutDialog.setCancelable(false);
            payoutDialog.show(getActivity().getSupportFragmentManager(), "Payouts Dialog");
        }
        else{
            AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                @Override
                public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                    if(success){
                        PayoutDialog payoutDialog = PayoutDialog.newPayoutDialog();
                        payoutDialog.setCancelable(false);
                        payoutDialog.show(getActivity().getSupportFragmentManager(), "Payouts Dialog");
                    }
                    else{
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }
                }
            };
            authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_PAYOUT);
            authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
            authenticateDialog.setCancelable(false);
            authenticateDialog.show();
        }
    }

    private void viewReceipt(){
        if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_VIEW_RECEIPT)){
            ViewReceiptDialog viewReceiptDialog = ViewReceiptDialog.newViewReceiptDialog();
            viewReceiptDialog.setCancelable(false);
            viewReceiptDialog.show(getActivity().getSupportFragmentManager(), "View Receipt");
        }
        else{
            AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                @Override
                public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                    if(success){
                        ViewReceiptDialog viewReceiptDialog = ViewReceiptDialog.newViewReceiptDialog();
                        viewReceiptDialog.setCancelable(false);
                        viewReceiptDialog.show(getActivity().getSupportFragmentManager(), "View Receipt");
                    }
                    else{
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }
                }
            };
            authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_VIEW_RECEIPT);
            authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
            authenticateDialog.setCancelable(false);
            authenticateDialog.show();
        }
    }

    private void backup(){
        backup.processBackupDatabase();
    }

    private void safekeeping(){
        if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_SAFEKEEPING)){
            SafekeepingDialog safekeepingDialog = SafekeepingDialog.newSafekeepingDialog();
            safekeepingDialog.setCancelable(false);
            safekeepingDialog.show(getActivity().getSupportFragmentManager(), "Safekeeping Dialog");
        }
        else{
            AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                @Override
                public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                    if(success){
                        SafekeepingDialog safekeepingDialog = SafekeepingDialog.newSafekeepingDialog();
                        safekeepingDialog.setCancelable(false);
                        safekeepingDialog.show(getActivity().getSupportFragmentManager(), "Safekeeping Dialog");
                    }
                    else{
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }
                }
            };
            authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_SAFEKEEPING);
            authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
            authenticateDialog.setCancelable(false);
            authenticateDialog.show();
        }
    }

    private void sync(){
        //Check first it has a connection to the server before process the account receivable
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Checking connectivity please wait...");
        requestAPI.TestConnection("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken())).enqueue(new Callback<TestConnectionResponse>() {
            @Override
            public void onResponse(Call<TestConnectionResponse> call, Response<TestConnectionResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().isSuccess()) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.getInstance().closeLoadingDialog();
                                if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_SYNC_DATA)){
                                    SyncDialog syncDialog = SyncDialog.newSyncDialog();
                                    syncDialog.setCancelable(false);
                                    syncDialog.setArgs(syncViewModel, usersViewModel);
                                    syncDialog.show(getActivity().getSupportFragmentManager(), "Sync Dialog");
                                }
                                else{
                                    AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                                        @Override
                                        public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                            if(success){
                                                SyncDialog syncDialog = SyncDialog.newSyncDialog();
                                                syncDialog.setCancelable(false);
                                                syncDialog.setArgs(syncViewModel, usersViewModel);
                                                syncDialog.show(getActivity().getSupportFragmentManager(), "Sync Dialog");
                                            }
                                            else{
                                                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    };
                                    authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_SYNC_DATA);
                                    authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                                    authenticateDialog.setCancelable(false);
                                    authenticateDialog.show();
                                }
                            }
                        }, 1500);
                    }
                    else{
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.getInstance().closeLoadingDialog();
                                Toast.makeText(getContext(), "Please connect to the internet to access this function of the application.", Toast.LENGTH_LONG).show();
                            }
                        }, 1500);
                    }
                }
                else{
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoadingDialog.getInstance().closeLoadingDialog();
                            ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                            Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }, 1500);
                }
            }

            @Override
            public void onFailure(Call<TestConnectionResponse> call, Throwable t) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Log.d("testConnection", "onFailure: " + t.getMessage());
                        Toast.makeText(getContext(), "Please connect to the internet to access this function of the application.", Toast.LENGTH_LONG).show();
                    }
                }, 1500);
            }
        });
    }

    private void cutoff(){
        if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_CUTOFF)){
            CutoffDialog cutoffDialog = CutoffDialog.newCutoffDialog();
            cutoffDialog.setCancelable(false);
            cutoffDialog.show(getActivity().getSupportFragmentManager(), "Cutoff Dialog");
        }
        else{
            AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                @Override
                public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                    if(success){
                        CutoffDialog cutoffDialog = CutoffDialog.newCutoffDialog();
                        cutoffDialog.setCancelable(false);
                        cutoffDialog.show(getActivity().getSupportFragmentManager(), "Cutoff Dialog");
                    }
                    else{
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }
                }
            };
            authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_CUTOFF);
            authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
            authenticateDialog.setCancelable(false);
            authenticateDialog.show();
        }
    }

    private void cashFund(){
        CashFundDialog cashFundDialog = CashFundDialog.newCashFundDialog();
        cashFundDialog.setCancelable(false);
        cashFundDialog.show(getActivity().getSupportFragmentManager(), "Cash Fund Dialog");
    }

    private void spotAudit(){
        try {
            List<Transactions> transactions = transactionsViewModel.fetchTransactionsToCutOff();
            if(transactions.size() != 0){
                if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_SPOT_AUDIT)){
                    SpotAuditDialog spotAuditDialog = SpotAuditDialog.newSpotAuditDialog();
                    spotAuditDialog.setCancelable(false);
                    spotAuditDialog.setTransactionsList(transactions);
                    spotAuditDialog.show(getActivity().getSupportFragmentManager(), "Spot Audit Dialog");
                }
                else{
                    AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                        @Override
                        public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                            if(success){
                                SpotAuditDialog spotAuditDialog = SpotAuditDialog.newSpotAuditDialog();
                                spotAuditDialog.setCancelable(false);
                                spotAuditDialog.setTransactionsList(transactions);
                                spotAuditDialog.show(getActivity().getSupportFragmentManager(), "Spot Audit Dialog");
                            }
                            else{
                                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                            }
                        }
                    };
                    authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_SPOT_AUDIT);
                    authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                    authenticateDialog.setCancelable(false);
                    authenticateDialog.show();
                }
            }
            else {
                Toast.makeText(getContext(), "There is no transaction to spot audit.", Toast.LENGTH_LONG).show();
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void orders(){
        //Check first it has a connection to the server before process the take order
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Checking connectivity please wait...");
        requestAPI.TestConnection("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken())).enqueue(new Callback<TestConnectionResponse>() {
            @Override
            public void onResponse(Call<TestConnectionResponse> call, Response<TestConnectionResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().isSuccess()) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.getInstance().closeLoadingDialog();
                                OrdersDialog ordersDialog = OrdersDialog.newOrdersDialog();
                                ordersDialog.setCancelable(false);
                                ordersDialog.show(getActivity().getSupportFragmentManager(), "Take Order Dialog");
                            }
                        }, 1500);
                    }
                    else{
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.getInstance().closeLoadingDialog();
                                Toast.makeText(getContext(), "Please connect to the internet to access this function of the application.", Toast.LENGTH_LONG).show();
                            }
                        }, 1500);
                    }
                }
                else{
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoadingDialog.getInstance().closeLoadingDialog();
                            ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                            Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }, 1500);
                }
            }

            @Override
            public void onFailure(Call<TestConnectionResponse> call, Throwable t) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Log.d("testConnection", "onFailure: " + t.getMessage());
                        Toast.makeText(getContext(), "Please connect to the internet to access this function of the application.", Toast.LENGTH_LONG).show();
                    }
                }, 1500);
            }
        });
    }

    private void uploadToServer(){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Checking connectivity please wait...");
        requestAPI.TestConnection("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()))
            .enqueue(new Callback<TestConnectionResponse>() {
                @Override
                public void onResponse(Call<TestConnectionResponse> call, Response<TestConnectionResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        List<Upload> uploads = uploadViewModel.fetchAll();
                                        if(uploads.size() != 0){
                                            LoadingDialog.getInstance().changeLoadingMessage("Uploading data please wait...");
                                            ExecutorService executorServices = Executors.newSingleThreadExecutor();
                                            executorServices.execute(new Runnable() {
                                                @Override
                                                public void run() {
                                                    //Start For Loop
                                                    for(Upload item : uploads){
                                                        switch (item.getName()) {
                                                            case "Transactions":
                                                                try {
                                                                    List<Transactions> transactions = transactionsViewModel.fetchCompleteTransactionToUpload();
                                                                    transactions.forEach(trans -> {
                                                                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                                                                        executorService.execute(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                posProcess.SyncTransactionToServer(trans);
                                                                            }
                                                                        });
                                                                    });
                                                                    break;
                                                                } catch (ExecutionException e) {
                                                                    throw new RuntimeException(e);
                                                                } catch (InterruptedException e) {
                                                                    throw new RuntimeException(e);
                                                                }
                                                            case "Orders":
                                                                try {
                                                                    List<Orders> orders = ordersViewModel.fetchCompleteOrderToUpload();
                                                                    orders.forEach(ord -> {
                                                                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                                                                        executorService.execute(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                posProcess.SyncOrderToServer(ord);
                                                                            }
                                                                        });
                                                                    });
                                                                    break;
                                                                } catch (ExecutionException e) {
                                                                    throw new RuntimeException(e);
                                                                } catch (InterruptedException e) {
                                                                    throw new RuntimeException(e);
                                                                }
                                                            case "Payments":
                                                                try {
                                                                    List<Payments> payments = paymentsViewModel.fetchCompletePaymentToUpload();
                                                                    payments.forEach(pay -> {
                                                                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                                                                        executorService.execute(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                posProcess.SyncPaymentToServer(pay);
                                                                            }
                                                                        });
                                                                    });
                                                                    break;
                                                                } catch (ExecutionException e) {
                                                                    throw new RuntimeException(e);
                                                                } catch (InterruptedException e) {
                                                                    throw new RuntimeException(e);
                                                                }
                                                            case "Machine Details":
                                                                try {
                                                                    MachineDetails machineDetails = machineDetailsViewModel.fetchMachineDetailsToUpload();
                                                                    if(machineDetails != null){
                                                                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                                                                        executorService.execute(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                posProcess.SyncMachineDetailsToServer(machineDetails);
                                                                            }
                                                                        });
                                                                    }
                                                                    break;
                                                                } catch (ExecutionException e) {
                                                                    throw new RuntimeException(e);
                                                                } catch (InterruptedException e) {
                                                                    throw new RuntimeException(e);
                                                                }
                                                            case "Discounts":
                                                                try {
                                                                    List<Discounts> discounts = discountsViewModel.fetchCompleteDiscountsToUpload();
                                                                    discounts.forEach(disc -> {
                                                                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                                                                        executorService.execute(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                posProcess.SyncDiscountToServer(disc);
                                                                            }
                                                                        });
                                                                    });
                                                                    break;
                                                                } catch (ExecutionException e) {
                                                                    throw new RuntimeException(e);
                                                                } catch (InterruptedException e) {
                                                                    throw new RuntimeException(e);
                                                                }
                                                            case "Discount Details":
                                                                try {
                                                                    List<DiscountDetails> discountDetails = discountDetailsViewModel.fetchCompleteDiscountDetailsToUpload();
                                                                    discountDetails.forEach(discDit -> {
                                                                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                                                                        executorService.execute(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                posProcess.SyncDiscountDetailsToServer(discDit);
                                                                            }
                                                                        });
                                                                    });
                                                                    break;
                                                                } catch (ExecutionException e) {
                                                                    throw new RuntimeException(e);
                                                                } catch (InterruptedException e) {
                                                                    throw new RuntimeException(e);
                                                                }
                                                            case "Cutoff":
                                                                try {
                                                                    List<CutOff> cutOffs = cutOffViewModel.fetchCompleteCutOffToUpload();
                                                                    cutOffs.forEach(cut -> {
                                                                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                                                                        executorService.execute(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                posProcess.SyncCutOffDetailsToServer(cut);
                                                                            }
                                                                        });
                                                                    });
                                                                } catch (ExecutionException e) {
                                                                    throw new RuntimeException(e);
                                                                } catch (InterruptedException e) {
                                                                    throw new RuntimeException(e);
                                                                }
                                                                break;
                                                            case "End Of Day":
                                                                try {
                                                                    List<EndOfDay> endOfDays = endOfDayViewModel.fetchCompleteEndOfDayToUpload();
                                                                    endOfDays.forEach(end -> {
                                                                        ExecutorService executorService = Executors.newFixedThreadPool(POSApplication.getInstance().getAvailableThreads());
                                                                        executorService.execute(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                try {
                                                                                    posProcess.SyncEndOfDayDetailsToServer(end, endOfDayProductsViewModel.fetchByEndOfDayId(end.getId()));
                                                                                } catch (ExecutionException e) {
                                                                                    throw new RuntimeException(e);
                                                                                } catch (InterruptedException e) {
                                                                                    throw new RuntimeException(e);
                                                                                }
                                                                            }
                                                                        });
                                                                    });
                                                                } catch (ExecutionException e) {
                                                                    throw new RuntimeException(e);
                                                                } catch (InterruptedException e) {
                                                                    throw new RuntimeException(e);
                                                                }
                                                                break;
                                                            default:
                                                        }
                                                    }
                                                    //End For Loop
                                                    getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            LoadingDialog.getInstance().closeLoadingDialog();
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
                            }, 1500);
                        }
                        else{
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    LoadingDialog.getInstance().closeLoadingDialog();
                                    Toast.makeText(getContext(), "Please connect to the internet to process the upload to server.", Toast.LENGTH_LONG).show();
                                }
                            }, 1500);
                        }
                    }
                    else{
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.getInstance().closeLoadingDialog();
                                ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }, 1500);
                    }
                }

                @Override
                public void onFailure(Call<TestConnectionResponse> call, Throwable t) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoadingDialog.getInstance().closeLoadingDialog();
                            Log.d("testConnection", "onFailure: " + t.getMessage());
                            Toast.makeText(getContext(), "Please connect to the internet to process the upload to server.", Toast.LENGTH_LONG).show();
                        }
                    }, 1500);
                }
            });
    }

    private void accountReceivables(){
        //Check first it has a connection to the server before process the account receivable
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Checking connectivity please wait...");
        requestAPI.TestConnection("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken())).enqueue(new Callback<TestConnectionResponse>() {
            @Override
            public void onResponse(Call<TestConnectionResponse> call, Response<TestConnectionResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().isSuccess()) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.getInstance().closeLoadingDialog();
                                ViewAccountReceivableDialog viewAccountReceivableDialog = ViewAccountReceivableDialog.newViewAccountReceivableDialog();
                                viewAccountReceivableDialog.setCancelable(false);
                                viewAccountReceivableDialog.show(getActivity().getSupportFragmentManager(), "View Account Receivable Dialog");
                            }
                        }, 1500);
                    }
                    else{
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LoadingDialog.getInstance().closeLoadingDialog();
                                Toast.makeText(getContext(), "Please connect to the internet to access this function of the application.", Toast.LENGTH_LONG).show();
                            }
                        }, 1500);
                    }
                }
                else{
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoadingDialog.getInstance().closeLoadingDialog();
                            ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                            Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }, 1500);
                }
            }

            @Override
            public void onFailure(Call<TestConnectionResponse> call, Throwable t) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.getInstance().closeLoadingDialog();
                        Log.d("testConnection", "onFailure: " + t.getMessage());
                        Toast.makeText(getContext(), "Please connect to the internet to access this function of the application.", Toast.LENGTH_LONG).show();
                    }
                }, 1500);
            }
        });
    }

}
