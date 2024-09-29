package com.example.isyncpos.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.authentication.UserAuthentication;
import com.example.isyncpos.common.AuditTrail;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.EncryptDecrypt;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.common.APIRequest;
import com.example.isyncpos.entity.CutOff;
import com.example.isyncpos.entity.EndOfDay;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.preferences.Cache;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.response.entity.ErrorBody;
import com.example.isyncpos.response.entity.TestConnectionResponse;
import com.example.isyncpos.viewmodel.CutOffViewModel;
import com.example.isyncpos.viewmodel.EndOfDayViewModel;
import com.example.isyncpos.viewmodel.PermissionsViewModel;
import com.example.isyncpos.viewmodel.RolesViewModel;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
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

public class CutoffDialog extends DialogFragment {

    private MaterialButton btnXReading, btnZReading, btnNegative;
    private AlertDialog alertDialog;
    private TransactionsViewModel transactionsViewModel;
    private CutOffViewModel cutOffViewModel;
    private EndOfDayViewModel endOfDayViewModel;
    private UsersViewModel usersViewModel;
    private RolesViewModel rolesViewModel;
    private PermissionsViewModel permissionsViewModel;
    private POSProcess posProcess;
    private View view;
    private Dates date;
    private Cache cache;
    private Gson gson;

    interface RequestAPI {
        @Headers({
                "accept: application/json"
        })
        @GET("/api/test-connection")
        Call<TestConnectionResponse> TestConnection(@Header("Authorization") String authorization);
    }

    public static CutoffDialog newCutoffDialog(){
        return new CutoffDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_cutoff, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        //Initialize
        initialize();
        initViewModel();

        btnXReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check first it has a connection to the server before process the x reading
                RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
                LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Checking connectivity please wait...");
                requestAPI.TestConnection("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken())).enqueue(new Callback<TestConnectionResponse>() {
                    @Override
                    public void onResponse(Call<TestConnectionResponse> call, Response<TestConnectionResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body().isSuccess()){
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        LoadingDialog.getInstance().closeLoadingDialog();
                                        processXReading();
                                    }
                                }, 1500);
                            }
                            else{
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        LoadingDialog.getInstance().closeLoadingDialog();
                                        Toast.makeText(getContext(), "Please connect to the internet to process the x reading.", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getContext(), "Please connect to the internet to process the x reading.", Toast.LENGTH_LONG).show();
                            }
                        }, 1500);
                    }
                });
            }
        });

        btnZReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check first it has a connection to the server before process the x reading
                RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
                LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Checking connectivity please wait...");
                requestAPI.TestConnection("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken())).enqueue(new Callback<TestConnectionResponse>() {
                    @Override
                    public void onResponse(Call<TestConnectionResponse> call, Response<TestConnectionResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body().isSuccess()){
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        LoadingDialog.getInstance().closeLoadingDialog();
                                        processZReading();
                                    }
                                }, 1500);
                            }
                            else{
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        LoadingDialog.getInstance().closeLoadingDialog();
                                        Toast.makeText(getContext(), "Please connect to the internet to process the z reading.", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getContext(), "Please connect to the internet to process the z reading.", Toast.LENGTH_LONG).show();
                            }
                        }, 1500);
                    }
                });
            }
        });

        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        alertDialog = builder.create();
        return alertDialog;
    }

    private void initViewModel() {
        cutOffViewModel = POSApplication.getInstance().getCutOffViewModel();
        transactionsViewModel = POSApplication.getInstance().getTransactionsViewModel();
        endOfDayViewModel = POSApplication.getInstance().getEndOfDayViewModel();
        usersViewModel = POSApplication.getInstance().getUsersViewModel();
        rolesViewModel = POSApplication.getInstance().getRolesViewModel();
        permissionsViewModel = POSApplication.getInstance().getPermissionsViewModel();
    }

    private void initialize() {
        btnXReading = view.findViewById(R.id.btnXReading);
        btnZReading = view.findViewById(R.id.btnZReading);
        btnNegative = view.findViewById(R.id.btnNegative);
        posProcess = POSApplication.getInstance().getPosProcess();
        date = new Dates();
        cache = new Cache(getContext());
        gson = new Gson();
    }

    private void safeKeepingCutOff(List<Transactions> transactions){
        SafekeepingDialog safekeepingDialog = SafekeepingDialog.newSafekeepingDialog();
        safekeepingDialog.setCancelable(false);
        safekeepingDialog.setIsCutOff(true);
        safekeepingDialog.setTransactions(transactions);
        safekeepingDialog.setCutoffDialog(this);
        safekeepingDialog.show(getActivity().getSupportFragmentManager(), "Safekeeping CutOff Dialog");
    }

    private void processXReading(){
        try {
            List<Transactions> transactions = transactionsViewModel.fetchTransactionsToCutOff();
            if(transactions.size() != 0){
                getDialog().hide();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to process the cutoff?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_CUTOFF_XREADING)){
                            safeKeepingCutOff(transactions);
                        }
                        else{
                            AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                                @Override
                                public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                    if(success){
                                        safeKeepingCutOff(transactions);
                                    }
                                    else{
                                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                            authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_CUTOFF_XREADING);
                            authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                            authenticateDialog.setCancelable(false);
                            authenticateDialog.show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else {
                Toast.makeText(getContext(), "There is no transaction to cutoff.", Toast.LENGTH_LONG).show();
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void processZReading(){
        try {
            if(!posProcess.checkForEndOfDayProcess()){
                //Check First If There Is A Pending Transactions
                List<Transactions> transactions = transactionsViewModel.fetchResumeTransactionsList();
                if(transactions.size() != 0){
                    Toast.makeText(getContext(), "There is a pending transactions please backout it before you process the end of day.", Toast.LENGTH_LONG).show();
                }
                else{
                    List<CutOff> cutOffs = cutOffViewModel.fetchCutOffToEndOfDay();
                    if(cutOffs.size() != 0){
                        EndOfDay endOfDay = endOfDayViewModel.fetchEndOfDayNow(date.nowDateOnly(date.now()));
                        if(endOfDay == null){
                            getDialog().hide();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Confirmation");
                            builder.setMessage("Are you sure you want to process the end of day for today?");
                            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_CUTOFF_ZREADING)){
                                        LoadingDialog.getInstance().startLoadingDialog(getActivity(), "End of day in progress please wait...");
                                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                                        executorService.execute(new Runnable() {
                                            @Override
                                            public void run() {

                                                posProcess.processEndOfDay(getActivity(), cutOffs, true);

                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        AuditTrail.getInstance().save(
                                                                POSApplication.getInstance().getUserAuthentication().getId(),
                                                                POSApplication.getInstance().getUserAuthentication().getUsername(),
                                                                0,
                                                                "END OF DAY",
                                                                "End of day process by cashier " + POSApplication.getInstance().getUserAuthentication().getName() + ".",
                                                                POSApplication.getInstance().getUserAuthentication().getId(),
                                                                POSApplication.getInstance().getUserAuthentication().getName(),
                                                                0,
                                                                0
                                                        );
                                                        dismiss();
                                                    }
                                                });

                                            }
                                        });
                                    }
                                    else{
                                        AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                                            @Override
                                            public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                                if(success){
                                                    LoadingDialog.getInstance().startLoadingDialog(getActivity(), "End of day in progress please wait...");
                                                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                                                    executorService.execute(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            posProcess.processEndOfDay(getActivity(), cutOffs, true);

                                                            getActivity().runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    AuditTrail.getInstance().save(
                                                                            POSApplication.getInstance().getUserAuthentication().getId(),
                                                                            POSApplication.getInstance().getUserAuthentication().getUsername(),
                                                                            0,
                                                                            "END OF DAY",
                                                                            "End of day process by cashier " + POSApplication.getInstance().getUserAuthentication().getName() + " authorize by " + authorizeUser.getName() + ".",
                                                                            authorizeUser.getId(),
                                                                            authorizeUser.getName(),
                                                                            0,
                                                                            0
                                                                    );
                                                                    dismiss();
                                                                }
                                                            });

                                                        }
                                                    });
                                                }
                                                else{
                                                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        };
                                        authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_CUTOFF_ZREADING);
                                        authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                                        authenticateDialog.setCancelable(false);
                                        authenticateDialog.show();
                                    }
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getDialog().show();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        else{
                            Toast.makeText(getContext(), "There is end of day process for today please wait for the next day to process again.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(getContext(), "There is no cutoff information to end of day.", Toast.LENGTH_LONG).show();
                    }
                }
            }
            else{
                String today = posProcess.getForEndOfDayProcessDate();
                List<CutOff> cutOffs = cutOffViewModel.fetchCutOffToEndOfDayByDate(today);
                if(cutOffs.size() != 0){
                    getDialog().hide();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to process the end of day for "+ today +"?");
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_CUTOFF_ZREADING)){
                                LoadingDialog.getInstance().startLoadingDialog(getActivity(), "End of day for "+ today +" in progress please wait...");
                                ExecutorService executorService = Executors.newSingleThreadExecutor();
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {

                                        posProcess.processEndOfDay(getActivity(), cutOffs, false);

                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                dismiss();
                                            }
                                        });

                                    }
                                });
                            }
                            else{
                                AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                                    @Override
                                    public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                        if(success){
                                            LoadingDialog.getInstance().startLoadingDialog(getActivity(), "End of day for "+ today +" in progress please wait...");
                                            ExecutorService executorService = Executors.newSingleThreadExecutor();
                                            executorService.execute(new Runnable() {
                                                @Override
                                                public void run() {

                                                    posProcess.processEndOfDay(getActivity(), cutOffs, false);

                                                    getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            dismiss();
                                                        }
                                                    });

                                                }
                                            });
                                        }
                                        else{
                                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                };
                                authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_CUTOFF_ZREADING);
                                authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                                authenticateDialog.setCancelable(false);
                                authenticateDialog.show();
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getDialog().show();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{
                    Toast.makeText(getContext(), "There is no cutoff information to end of day.", Toast.LENGTH_LONG).show();
                }
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
