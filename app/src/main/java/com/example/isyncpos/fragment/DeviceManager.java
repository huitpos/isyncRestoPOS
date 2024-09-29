package com.example.isyncpos.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.adapters.DevicesAdapter;
import com.example.isyncpos.dialog.AddDeviceDialog;
import com.example.isyncpos.entity.Devices;
import com.example.isyncpos.viewmodel.DevicesViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupDevicesViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class DeviceManager extends Fragment {

    private RecyclerView recyclerDevices;
    private DevicesAdapter devicesAdapter;
    private MaterialButton btnDeviceManagerAdd;
    private DevicesViewModel devicesViewModel;
    private PrinterSetupViewModel printerSetupViewModel;
    private PrinterSetupDevicesViewModel printerSetupDevicesViewModel;
    private boolean isActiveFragment = false;


    public static DeviceManager newDeviceManager(){
        return new DeviceManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_device_manager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();

        devicesViewModel.fetchAll().observe(getActivity(), new Observer<List<Devices>>() {
            @Override
            public void onChanged(List<Devices> devices) {
                devicesAdapter.submitList(devices);
            }
        });

        btnDeviceManagerAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDevice();
            }
        });

    }

    private void initialize(){
        initViewModel();
        btnDeviceManagerAdd = getActivity().findViewById(R.id.btnDeviceManagerAdd);
        initRecyclerView();
    }

    private void initViewModel(){
        devicesViewModel = POSApplication.getInstance().getDevicesViewModel();
        printerSetupDevicesViewModel = POSApplication.getInstance().getPrinterSetupDevicesViewModel();
        printerSetupViewModel = POSApplication.getInstance().getPrinterSetupViewModel();
    }

    private void initRecyclerView(){
        recyclerDevices = getView().findViewById(R.id.recyclerDevices);
        recyclerDevices.setHasFixedSize(true);
        recyclerDevices.setLayoutManager(new LinearLayoutManager(getContext()));
        devicesAdapter = new DevicesAdapter(getActivity(), devicesViewModel, printerSetupDevicesViewModel, printerSetupViewModel);
        recyclerDevices.setAdapter(devicesAdapter);
    }

    private void addDevice(){
        AddDeviceDialog addDeviceDialog = AddDeviceDialog.newAddDeviceDialog();
        addDeviceDialog.setCancelable(false);
        addDeviceDialog.show(getActivity().getSupportFragmentManager(), "Add Device Dialog");
    }

    public void setActiveFragment(boolean value){
        isActiveFragment = value;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        devicesViewModel.fetchAll().removeObservers(getActivity());
    }
}
