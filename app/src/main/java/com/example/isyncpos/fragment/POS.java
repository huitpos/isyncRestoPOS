package com.example.isyncpos.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.common.Backup;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.entity.ApplicationSettings;
import com.example.isyncpos.entity.MachineDetails;
import com.example.isyncpos.preferences.Cache;
import com.example.isyncpos.viewmodel.ApplicationSettingsViewModel;
import com.example.isyncpos.viewmodel.MachineDetailsViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class POS extends Fragment {

    private boolean isActiveFragment = false;
    private EditText txtSafekeeping;
    private TextView labelRestoreDatabase;
    private MaterialButton btnRestoreDatabase;
    private MachineDetailsViewModel machineDetailsViewModel;
    private ApplicationSettingsViewModel applicationSettingsViewModel;
    private MachineDetails machineDetails;
    private Generate generate;
    private Cache cache;
    private Gson gson;
    private Backup backup;
    private Timer timerSafeKeeping;
    private ActivityResultLauncher<String[]> filePickerDatabaseRestoreLauncher;
    private Spinner spinnerAutomaticCashFund;
    private String[] automaticCashFundTypes = new String[]{"ENABLED", "DISABLED"};
    private ArrayAdapter<String> arrayAutomaticCashFundTypeAdapter;


    public static POS newPOS(){
        return new POS();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
        intiViewModel();
        intiActivityResults();
        initDefaultSafeKeeping();
        initRestoreDatabase();

        spinnerAutomaticCashFund.setAdapter(arrayAutomaticCashFundTypeAdapter);
        spinnerAutomaticCashFund.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                try {
                    String type = parent.getSelectedItem().toString();
                    ApplicationSettings applicationSettings = applicationSettingsViewModel.fetch();
                    if(applicationSettings != null){
                        if(type.equals("ENABLED")){
                            applicationSettings.setAutomaticCashFund(1);
                        } else if (type.equals("DISABLED")) {
                            applicationSettings.setAutomaticCashFund(0);
                        }
                        applicationSettingsViewModel.update(applicationSettings);
                    }
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setSelectionAutomaticCashFund();

        btnRestoreDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooserRestoreDatabase();
            }
        });

        txtSafekeeping.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(timerSafeKeeping != null){
                    timerSafeKeeping.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                timerSafeKeeping = new Timer();
                timerSafeKeeping.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            if(!txtSafekeeping.getText().toString().isEmpty()){
                                machineDetails.setCashRegisterLimitAmount(generate.toFourDecimal(Double.parseDouble(txtSafekeeping.getText().toString().replace(",", ""))));
                                machineDetailsViewModel.update(machineDetails);
                                cache.saveString("machineDetails", gson.toJson(machineDetails));
                                POSApplication.getInstance().setMachineDetails(machineDetails);
                            }
                            else {
                                machineDetails.setCashRegisterLimitAmount(0);
                                machineDetailsViewModel.update(machineDetails);
                                cache.saveString("machineDetails", gson.toJson(machineDetails));
                                POSApplication.getInstance().setMachineDetails(machineDetails);
                            }
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, 700);
            }
        });

    }

    private void initialize(){
        arrayAutomaticCashFundTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, automaticCashFundTypes);
        txtSafekeeping = getView().findViewById(R.id.txtSafekeeping);
        labelRestoreDatabase = getView().findViewById(R.id.labelRestoreDatabase);
        btnRestoreDatabase = getView().findViewById(R.id.btnRestoreDatabase);
        spinnerAutomaticCashFund = getView().findViewById(R.id.spinnerAutomaticCashFund);
        generate = new Generate();
        cache = new Cache(getContext());
        gson = new Gson();
        backup = POSApplication.getInstance().getBackup();
    }

    private void intiViewModel(){
        machineDetailsViewModel = POSApplication.getInstance().getMachineDetailsViewModel();
        applicationSettingsViewModel = POSApplication.getInstance().getApplicationSettingsViewModel();
    }

    private void initDefaultSafeKeeping(){
        try {
            machineDetails = machineDetailsViewModel.fetchMachineDetails();
            txtSafekeeping.setText(generate.toTwoDecimalWithComma(machineDetails.getCashRegisterLimitAmount()));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void initRestoreDatabase(){
        if(BuildConfig.ENV.equals("DEVELOPMENT") && POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            labelRestoreDatabase.setVisibility(View.VISIBLE);
            btnRestoreDatabase.setVisibility(View.VISIBLE);
        }
        else{
            labelRestoreDatabase.setVisibility(View.GONE);
            btnRestoreDatabase.setVisibility(View.GONE);
        }
    }

    private void showFileChooserRestoreDatabase(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirmation");
        builder.setMessage("This process will restart your application. Are you sure you want to restore your application data?");
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // "application/octet-stream", "application/vnd.sqlite3", "application/x-sqlite3"
                filePickerDatabaseRestoreLauncher.launch(new String[]{"application/octet-stream", "application/vnd.sqlite3", "application/x-sqlite3"});
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

    private void intiActivityResults(){
        filePickerDatabaseRestoreLauncher = registerForActivityResult(new ActivityResultContracts.OpenDocument(), result -> {
            if(result != null){
                LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Restoring data please wait....");
                final Handler handlerRestore = new Handler();
                handlerRestore.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    backup.processRestoreDatabase(result);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LoadingDialog.getInstance().changeLoadingMessage("Restore process is done restarting the application now...");
                                        final Handler handlerRestart = new Handler();
                                        handlerRestart.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                backup.restartApplication();
                                            }
                                        }, BuildConfig.APP_DEFAULT_LOADING_TIME);
                                    }
                                });

                            }
                        });
                    }
                }, 5000);
            }
        });
    }

    private void setSelectionAutomaticCashFund(){
        try {
            ApplicationSettings applicationSettings = applicationSettingsViewModel.fetch();
            if(applicationSettings != null){
                spinnerAutomaticCashFund.setSelection(applicationSettings.getAutomaticCashFund() == 0 ? 1 : 0);
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setActiveFragment(boolean value){
        isActiveFragment = value;
    }

}
