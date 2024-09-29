package com.example.isyncpos.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.common.EncryptDecrypt;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.common.APIRequest;
import com.example.isyncpos.entity.Branch;
import com.example.isyncpos.entity.Company;
import com.example.isyncpos.entity.DeviceDetails;
import com.example.isyncpos.entity.MachineDetails;
import com.example.isyncpos.payload.ActivateMachineDeviceIdPayload;
import com.example.isyncpos.payload.ActivateMachinePayload;
import com.example.isyncpos.preferences.Cache;
import com.example.isyncpos.response.entity.ActivateMachineResponse;
import com.example.isyncpos.response.entity.ErrorBody;
import com.example.isyncpos.viewmodel.BranchViewModel;
import com.example.isyncpos.viewmodel.CompanyViewModel;
import com.example.isyncpos.viewmodel.DeviceDetailsViewModel;
import com.example.isyncpos.viewmodel.MachineDetailsViewModel;
import com.example.isyncpos.viewmodel.SyncViewModel;
import com.example.isyncpos.viewmodel.UsersViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class KioskSettingsDialog extends DialogFragment {

    private MachineDetailsViewModel machineDetailsViewModel;
    private BranchViewModel branchViewModel;
    private CompanyViewModel companyViewModel;
    private SyncViewModel syncViewModel;
    private UsersViewModel usersViewModel;
    private DeviceDetailsViewModel deviceDetailsViewModel;
    private MaterialButton btnKioskCancel, btnKioskConfirm;
    private AlertDialog alertDialog;
    private View view;
    private EditText txtKioskProductKey;
    private Gson gson;
    private Cache cache;
    private int machineNumber;
    private int deviceNumber = 0;

    interface RequestAPI {
        @Headers({
            "Content-Type: application/json",
            "accept: application/json"
        })
        @POST("/api/activate-machine")
        Call<ActivateMachineResponse> ActivateMachine(@Header("Authorization") String authorization, @Body ActivateMachinePayload activateMachinePayload);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/activate-machine")
        Call<ActivateMachineResponse> ActivateMachineDeviceId(@Header("Authorization") String authorization, @Body ActivateMachineDeviceIdPayload activateMachineDeviceIdPayload);
    }

    public static KioskSettingsDialog newKioskSettingsDialog(){
        return new KioskSettingsDialog();
    }

    public void setArgs(MachineDetailsViewModel machineDetailsViewModel, BranchViewModel branchViewModel, CompanyViewModel companyViewModel, SyncViewModel syncViewModel, DeviceDetailsViewModel deviceDetailsViewModel, UsersViewModel usersViewModel){
        this.machineDetailsViewModel = machineDetailsViewModel;
        this.branchViewModel = branchViewModel;
        this.companyViewModel = companyViewModel;
        this.syncViewModel = syncViewModel;
        this.deviceDetailsViewModel = deviceDetailsViewModel;
        this.usersViewModel = usersViewModel;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_login_settings, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        //Initialize
        initialize();
        checkMachineDetails();

        btnKioskCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnKioskConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateProductKey();
            }
        });

        alertDialog = builder.create();
        return alertDialog;
    }

    private void initialize(){
        btnKioskCancel = view.findViewById(R.id.btnKioskCancel);
        btnKioskConfirm = view.findViewById(R.id.btnKioskConfirm);
        txtKioskProductKey = view.findViewById(R.id.txtKioskProductKey);
        gson = new Gson();
        cache = new Cache(getContext());
    }

    private void checkMachineDetails(){
        try {
            MachineDetails machineDetails = machineDetailsViewModel.fetchMachineDetails();
            if(machineDetails != null){ //This will check if there a machine details and put the product key
                btnKioskConfirm.setEnabled(false);
                txtKioskProductKey.setEnabled(false);
                txtKioskProductKey.setText(machineDetails.getProductKey());
                DeviceDetails deviceDetails = deviceDetailsViewModel.fetch();
                Log.d("checkMachineDetails", "MachineNumber: " + machineDetails.getCoreId());
                machineNumber = machineDetails.getCoreId();
                Log.d("checkMachineDetails", "ProductKey: " + machineDetails.getProductKey());
                if(deviceDetails != null){
                    deviceNumber = deviceDetails.getCoreId();
                    Log.d("checkMachineDetails", "DeviceNumber: " + deviceDetails.getCoreId());
                }
            }
            else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if(Environment.isExternalStorageManager()){
                        if(txtKioskProductKey.getText().toString().isEmpty()){
                            File file = new File(Environment.getExternalStorageDirectory(), "Documents/machine.txt");
                            if(file.exists()){
                                if(file.canRead()){
                                    try {
                                        FileInputStream fileInputStream = new FileInputStream(file);
                                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                        StringBuilder stringBuilder = new StringBuilder();
                                        String line;
                                        while ((line = bufferedReader.readLine()) != null){
                                            stringBuilder.append(line);
                                        }
                                        String machineText = stringBuilder.toString();
                                        bufferedReader.close();
                                        inputStreamReader.close();
                                        fileInputStream.close();
                                        String[] res = machineText.split(",", 0);
                                        machineNumber = Integer.parseInt(res[0]);
                                        txtKioskProductKey.setText(res[1].toString());
                                        txtKioskProductKey.setEnabled(false);
                                        deviceNumber = Integer.parseInt(res[2]);
                                        Log.d("checkMachineDetails", "MachineNumber: " + machineNumber);
                                        Log.d("checkMachineDetails", "ProductKey: " + txtKioskProductKey);
                                        Log.d("checkMachineDetails", "DeviceNumber: " + deviceNumber);
                                    } catch (FileNotFoundException e) {
                                        throw new RuntimeException(e);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateProductKey(){
        if(txtKioskProductKey.getText().toString().isEmpty()){
            txtKioskProductKey.requestFocus();
            Toast.makeText(getContext(), "Please input the product key of the machine.", Toast.LENGTH_LONG).show();
        }
        else {
            LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Verifying product key please wait...");
            getDialog().hide();
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            if(deviceNumber != 0){
                requestAPI.ActivateMachineDeviceId("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new ActivateMachineDeviceIdPayload(txtKioskProductKey.getText().toString(), Build.SERIAL.toString(), Build.MODEL.toString(), Build.ID.toString(), Build.MANUFACTURER.toString(), Build.BOARD.toString(), deviceNumber)).enqueue(
                        new Callback<ActivateMachineResponse>() {
                            @Override
                            public void onResponse(Call<ActivateMachineResponse> call, Response<ActivateMachineResponse> response) {
                                if(response.isSuccessful()){
                                    if(!response.body().getData().getBranch().getStatus().equals("inactive")){
                                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                                        executorService.execute(new Runnable() {
                                            @Override
                                            public void run() {

                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            if(machineDetailsViewModel.fetchMachineDetails() != null) machineDetailsViewModel.remove(machineDetailsViewModel.fetchMachineDetails());
                                                            if(branchViewModel.fetch() != null) branchViewModel.remove(branchViewModel.fetch());
                                                            if(companyViewModel.fetch() != null) companyViewModel.remove(companyViewModel.fetch());
                                                        } catch (ExecutionException e) {
                                                            throw new RuntimeException(e);
                                                        } catch (InterruptedException e) {
                                                            throw new RuntimeException(e);
                                                        }
                                                    }
                                                });

                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        machineDetailsViewModel.insert(new MachineDetails(
                                                                response.body().getData().getId(),
                                                                response.body().getData().getProduct_key(),
                                                                response.body().getData().getMachine_number(),
                                                                response.body().getData().getSerial_number(),
                                                                response.body().getData().getMin(),
                                                                response.body().getData().getReceipt_header(),
                                                                response.body().getData().getReceipt_bottom_text(),
                                                                response.body().getData().getPermit_number(),
                                                                response.body().getData().getAccreditation_number(),
                                                                response.body().getData().getValid_from(),
                                                                response.body().getData().getValid_to(),
                                                                response.body().getData().getTin(),
                                                                response.body().getData().getLimit_amount(),
                                                                response.body().getData().getVat(),
                                                                response.body().getData().getOr_counter(),
                                                                response.body().getData().getX_reading_counter(),
                                                                response.body().getData().getZ_reading_counter(),
                                                                response.body().getData().getVoid_counter(),
                                                                response.body().getData().getType(),
                                                                response.body().getData().getStatus(),
                                                                1
                                                        ));
                                                        branchViewModel.insert(new Branch(
                                                                response.body().getData().getBranch().getId(),
                                                                response.body().getData().getBranch().getCompany_id(),
                                                                response.body().getData().getBranch().getCluster_id(),
                                                                response.body().getData().getBranch().getRegion_id(),
                                                                response.body().getData().getBranch().getProvince_id(),
                                                                response.body().getData().getBranch().getCity_id(),
                                                                response.body().getData().getBranch().getBarangay_id(),
                                                                response.body().getData().getBranch().getStatus(),
                                                                response.body().getData().getBranch().getName(),
                                                                response.body().getData().getBranch().getCode(),
                                                                response.body().getData().getBranch().getLocation(),
                                                                response.body().getData().getBranch().getUnit_number(),
                                                                response.body().getData().getBranch().getFloor_number(),
                                                                response.body().getData().getBranch().getStreet(),
                                                                response.body().getData().getBranch().getZip(),
                                                                response.body().getData().getBranch().getSlug(),
                                                                response.body().getData().getBranch().getPos_type(),
                                                                response.body().getData().getBranch().getCluster().getName(),
                                                                response.body().getData().getBranch().getRegion().getName(),
                                                                response.body().getData().getBranch().getProvince().getName(),
                                                                response.body().getData().getBranch().getCity().getName(),
                                                                response.body().getData().getBranch().getBarangay().getName(),
                                                                response.body().getData().getBranch().getPhone_number()
                                                        ));
                                                        companyViewModel.insert(new Company(
                                                                response.body().getData().getBranch().getCompany().getId(),
                                                                response.body().getData().getBranch().getCompany().getClient_id(),
                                                                response.body().getData().getBranch().getCompany().getCompany_registered_name(),
                                                                response.body().getData().getBranch().getCompany().getCompany_name(),
                                                                response.body().getData().getBranch().getCompany().getTrade_name(),
                                                                response.body().getData().getBranch().getCompany().getLogo(),
                                                                response.body().getData().getBranch().getCompany().getCountry(),
                                                                response.body().getData().getBranch().getCompany().getPhone_number(),
                                                                response.body().getData().getBranch().getCompany().getBarangay_id(),
                                                                response.body().getData().getBranch().getCompany().getCity_id(),
                                                                response.body().getData().getBranch().getCompany().getProvince_id(),
                                                                response.body().getData().getBranch().getCompany().getRegion_id(),
                                                                response.body().getData().getBranch().getCompany().getSlug(),
                                                                response.body().getData().getBranch().getCompany().getUnit_floor_number(),
                                                                response.body().getData().getBranch().getCompany().getRegion().getName(),
                                                                response.body().getData().getBranch().getCompany().getProvince().getName(),
                                                                response.body().getData().getBranch().getCompany().getCity().getName(),
                                                                response.body().getData().getBranch().getCompany().getBarangay().getName(),
                                                                response.body().getData().getBranch().getCompany().getPos_type()
                                                        ));
                                                        deviceDetailsViewModel.insert(new DeviceDetails(
                                                                response.body().getData().getDevice_info().getId(),
                                                                response.body().getData().getDevice_info().getPos_machine_id(),
                                                                response.body().getData().getDevice_info().getSerial(),
                                                                response.body().getData().getDevice_info().getModel(),
                                                                response.body().getData().getDevice_info().getAndroid_id(),
                                                                response.body().getData().getDevice_info().getManufacturer(),
                                                                response.body().getData().getDevice_info().getBoard(),
                                                                response.body().getData().getDevice_info().getStatus() == "active" ? 1 : 0
                                                        ));
                                                    }
                                                });

                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        createMachineTextFile(response.body().getData().getId(), txtKioskProductKey.getText().toString(), response.body().getData().getDevice_info().getId());
                                                        LoadingDialog.getInstance().closeLoadingDialog();
                                                        setBranchInfo(response);
                                                        setCompanyInfo(response);
                                                        setDeviceDetails(response);
                                                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                                                        dismiss();
                                                        sync();
                                                    }
                                                });

                                            }
                                        });
                                    }
                                    else{
                                        LoadingDialog.getInstance().closeLoadingDialog();
                                        Toast.makeText(getContext(), "Branch is inactive.", Toast.LENGTH_LONG).show();
                                        getDialog().show();
                                    }
                                }
                                else{
                                    LoadingDialog.getInstance().closeLoadingDialog();
                                    ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                    Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                                    getDialog().show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ActivateMachineResponse> call, Throwable t) {
                                getDialog().show();
                                LoadingDialog.getInstance().closeLoadingDialog();
                                Log.d("activateMachine", "onFailure: " + t.getMessage());
                            }
                        }
                );
            }
            else{
                requestAPI.ActivateMachine("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new ActivateMachinePayload(txtKioskProductKey.getText().toString(), Build.SERIAL.toString(), Build.MODEL.toString(), Build.ID.toString(), Build.MANUFACTURER.toString(), Build.BOARD.toString())).enqueue(
                        new Callback<ActivateMachineResponse>() {
                            @Override
                            public void onResponse(Call<ActivateMachineResponse> call, Response<ActivateMachineResponse> response) {
                                if(response.isSuccessful()){
                                    if(!response.body().getData().getBranch().getStatus().equals("inactive")){
                                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                                        executorService.execute(new Runnable() {
                                            @Override
                                            public void run() {

                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            if(machineDetailsViewModel.fetchMachineDetails() != null) machineDetailsViewModel.remove(machineDetailsViewModel.fetchMachineDetails());
                                                            if(branchViewModel.fetch() != null) branchViewModel.remove(branchViewModel.fetch());
                                                            if(companyViewModel.fetch() != null) companyViewModel.remove(companyViewModel.fetch());
                                                        } catch (ExecutionException e) {
                                                            throw new RuntimeException(e);
                                                        } catch (InterruptedException e) {
                                                            throw new RuntimeException(e);
                                                        }
                                                    }
                                                });

                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        machineDetailsViewModel.insert(new MachineDetails(
                                                                response.body().getData().getId(),
                                                                response.body().getData().getProduct_key(),
                                                                response.body().getData().getMachine_number(),
                                                                response.body().getData().getSerial_number(),
                                                                response.body().getData().getMin(),
                                                                response.body().getData().getReceipt_header(),
                                                                response.body().getData().getReceipt_bottom_text(),
                                                                response.body().getData().getPermit_number(),
                                                                response.body().getData().getAccreditation_number(),
                                                                response.body().getData().getValid_from(),
                                                                response.body().getData().getValid_to(),
                                                                response.body().getData().getTin(),
                                                                response.body().getData().getLimit_amount(),
                                                                response.body().getData().getVat(),
                                                                response.body().getData().getOr_counter(),
                                                                response.body().getData().getX_reading_counter(),
                                                                response.body().getData().getZ_reading_counter(),
                                                                0,
                                                                response.body().getData().getType(),
                                                                response.body().getData().getStatus(),
                                                                1
                                                        ));
                                                        branchViewModel.insert(new Branch(
                                                                response.body().getData().getBranch().getId(),
                                                                response.body().getData().getBranch().getCompany_id(),
                                                                response.body().getData().getBranch().getCluster_id(),
                                                                response.body().getData().getBranch().getRegion_id(),
                                                                response.body().getData().getBranch().getProvince_id(),
                                                                response.body().getData().getBranch().getCity_id(),
                                                                response.body().getData().getBranch().getBarangay_id(),
                                                                response.body().getData().getBranch().getStatus(),
                                                                response.body().getData().getBranch().getName(),
                                                                response.body().getData().getBranch().getCode(),
                                                                response.body().getData().getBranch().getLocation(),
                                                                response.body().getData().getBranch().getUnit_number(),
                                                                response.body().getData().getBranch().getFloor_number(),
                                                                response.body().getData().getBranch().getStreet(),
                                                                response.body().getData().getBranch().getZip(),
                                                                response.body().getData().getBranch().getSlug(),
                                                                response.body().getData().getBranch().getPos_type(),
                                                                response.body().getData().getBranch().getCluster().getName(),
                                                                response.body().getData().getBranch().getRegion().getName(),
                                                                response.body().getData().getBranch().getProvince().getName(),
                                                                response.body().getData().getBranch().getCity().getName(),
                                                                response.body().getData().getBranch().getBarangay().getName(),
                                                                response.body().getData().getBranch().getPhone_number()
                                                        ));
                                                        companyViewModel.insert(new Company(
                                                                response.body().getData().getBranch().getCompany().getId(),
                                                                response.body().getData().getBranch().getCompany().getClient_id(),
                                                                response.body().getData().getBranch().getCompany().getCompany_registered_name(),
                                                                response.body().getData().getBranch().getCompany().getCompany_name(),
                                                                response.body().getData().getBranch().getCompany().getTrade_name(),
                                                                response.body().getData().getBranch().getCompany().getLogo(),
                                                                response.body().getData().getBranch().getCompany().getCountry(),
                                                                response.body().getData().getBranch().getCompany().getPhone_number(),
                                                                response.body().getData().getBranch().getCompany().getBarangay_id(),
                                                                response.body().getData().getBranch().getCompany().getCity_id(),
                                                                response.body().getData().getBranch().getCompany().getProvince_id(),
                                                                response.body().getData().getBranch().getCompany().getRegion_id(),
                                                                response.body().getData().getBranch().getCompany().getSlug(),
                                                                response.body().getData().getBranch().getCompany().getUnit_floor_number(),
                                                                response.body().getData().getBranch().getCompany().getRegion().getName(),
                                                                response.body().getData().getBranch().getCompany().getProvince().getName(),
                                                                response.body().getData().getBranch().getCompany().getCity().getName(),
                                                                response.body().getData().getBranch().getCompany().getBarangay().getName(),
                                                                response.body().getData().getBranch().getCompany().getPos_type()
                                                        ));
                                                        deviceDetailsViewModel.insert(new DeviceDetails(
                                                                response.body().getData().getDevice_info().getId(),
                                                                response.body().getData().getDevice_info().getPos_machine_id(),
                                                                response.body().getData().getDevice_info().getSerial(),
                                                                response.body().getData().getDevice_info().getModel(),
                                                                response.body().getData().getDevice_info().getAndroid_id(),
                                                                response.body().getData().getDevice_info().getManufacturer(),
                                                                response.body().getData().getDevice_info().getBoard(),
                                                                response.body().getData().getDevice_info().getStatus() == "active" ? 1 : 0
                                                        ));
                                                    }
                                                });

                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        createMachineTextFile(response.body().getData().getId(), txtKioskProductKey.getText().toString(), response.body().getData().getDevice_info().getId());
                                                        LoadingDialog.getInstance().closeLoadingDialog();
                                                        setBranchInfo(response);
                                                        setCompanyInfo(response);
                                                        setDeviceDetails(response);
                                                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                                                        dismiss();
                                                        sync();
                                                    }
                                                });

                                            }
                                        });
                                    }
                                    else{
                                        LoadingDialog.getInstance().closeLoadingDialog();
                                        Toast.makeText(getContext(), "Branch is inactive.", Toast.LENGTH_LONG).show();
                                        getDialog().show();
                                    }
                                }
                                else{
                                    LoadingDialog.getInstance().closeLoadingDialog();
                                    ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                                    Toast.makeText(getContext(), errorBody.getMessage(), Toast.LENGTH_LONG).show();
                                    getDialog().show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ActivateMachineResponse> call, Throwable t) {
                                getDialog().show();
                                LoadingDialog.getInstance().closeLoadingDialog();
                                Log.d("activateMachine", "onFailure: " + t.getMessage());
                            }
                        }
                );
            }
        }
    }

    private void sync(){
        SyncDialog syncDialog = SyncDialog.newSyncDialog();
        syncDialog.setCancelable(false);
        syncDialog.setArgs(syncViewModel, usersViewModel);
        syncDialog.show(getActivity().getSupportFragmentManager(), "Sync Dialog");
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

    private void setBranchInfo(Response<ActivateMachineResponse> response){
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
                else{
                    Branch newBranch = new Branch(
                        response.body().getData().getBranch().getId(),
                        response.body().getData().getBranch().getCompany_id(),
                        response.body().getData().getBranch().getCluster_id(),
                        response.body().getData().getBranch().getRegion_id(),
                        response.body().getData().getBranch().getProvince_id(),
                        response.body().getData().getBranch().getCity_id(),
                        response.body().getData().getBranch().getBarangay_id(),
                        response.body().getData().getBranch().getStatus(),
                        response.body().getData().getBranch().getName(),
                        response.body().getData().getBranch().getCode(),
                        response.body().getData().getBranch().getLocation(),
                        response.body().getData().getBranch().getUnit_number(),
                        response.body().getData().getBranch().getFloor_number(),
                        response.body().getData().getBranch().getStreet(),
                        response.body().getData().getBranch().getZip(),
                        response.body().getData().getBranch().getSlug(),
                        response.body().getData().getBranch().getPos_type(),
                        response.body().getData().getBranch().getCluster().getName(),
                        response.body().getData().getBranch().getRegion().getName(),
                        response.body().getData().getBranch().getProvince().getName(),
                        response.body().getData().getBranch().getCity().getName(),
                        response.body().getData().getBranch().getBarangay().getName(),
                        response.body().getData().getBranch().getPhone_number()
                    );
                    cache.saveString("branchInfo", gson.toJson(newBranch));
                    POSApplication.getInstance().setBranch(newBranch);
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setCompanyInfo(Response<ActivateMachineResponse> response){
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
                else {
                    Company newCompany = new Company(
                            response.body().getData().getBranch().getCompany().getId(),
                            response.body().getData().getBranch().getCompany().getClient_id(),
                            response.body().getData().getBranch().getCompany().getCompany_registered_name(),
                            response.body().getData().getBranch().getCompany().getCompany_name(),
                            response.body().getData().getBranch().getCompany().getTrade_name(),
                            response.body().getData().getBranch().getCompany().getLogo(),
                            response.body().getData().getBranch().getCompany().getCountry(),
                            response.body().getData().getBranch().getCompany().getPhone_number(),
                            response.body().getData().getBranch().getCompany().getBarangay_id(),
                            response.body().getData().getBranch().getCompany().getCity_id(),
                            response.body().getData().getBranch().getCompany().getProvince_id(),
                            response.body().getData().getBranch().getCompany().getRegion_id(),
                            response.body().getData().getBranch().getCompany().getSlug(),
                            response.body().getData().getBranch().getCompany().getUnit_floor_number(),
                            response.body().getData().getBranch().getCompany().getRegion().getName(),
                            response.body().getData().getBranch().getCompany().getProvince().getName(),
                            response.body().getData().getBranch().getCompany().getCity().getName(),
                            response.body().getData().getBranch().getCompany().getBarangay().getName(),
                            response.body().getData().getBranch().getCompany().getPos_type()
                    );
                    cache.saveString("companyInfo", gson.toJson(newCompany));
                    POSApplication.getInstance().setCompany(newCompany);
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setDeviceDetails(Response<ActivateMachineResponse> response){
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
                else {
                    DeviceDetails newDeviceDetails = new DeviceDetails(
                        response.body().getData().getDevice_info().getId(),
                            response.body().getData().getDevice_info().getPos_machine_id(),
                            response.body().getData().getDevice_info().getSerial(),
                            response.body().getData().getDevice_info().getModel(),
                            response.body().getData().getDevice_info().getAndroid_id(),
                            response.body().getData().getDevice_info().getManufacturer(),
                            response.body().getData().getDevice_info().getBoard(),
                            response.body().getData().getDevice_info().getStatus() == "active" ? 1 : 0
                    );
                    cache.saveString("deviceDetailsInfo", gson.toJson(newDeviceDetails));
                    POSApplication.getInstance().setDeviceDetails(newDeviceDetails);
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
