package com.example.isyncpos.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.entity.Devices;
import com.example.isyncpos.listener.DoneOnEditorActionListener;
import com.example.isyncpos.viewmodel.DevicesViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddDeviceDialog extends DialogFragment {

    private MaterialButton btnDeviceNegative, btnDevicePositive;
    private Spinner spinnerDeviceType, spinnerDevices;
    private EditText txtDeviceName;
    private DevicesViewModel devicesViewModel;
    private AlertDialog alertDialog;
    private View view;
    private Generate generate;
    private Dates date;
    private String[] types = new String[]{"USB", "BLUETOOTH", "TCP/IP"};
    ArrayAdapter<String> arrayTypeAdapter;
    ArrayAdapter<Devices> arrayDevicesAdapter;

    public static AddDeviceDialog newAddDeviceDialog(){
        return new AddDeviceDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view = inflater.inflate(R.layout.dialog_device_add,null);
        builder.setView(view);
        alertDialog = builder.create();

        initialize();

        spinnerDeviceType.setAdapter(arrayTypeAdapter);

        spinnerDeviceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = parent.getSelectedItem().toString();
                if(type.equals("USB")){
                    listUSBDevices();
                } else if (type.equals("BLUETOOTH")) {
                    listBluetoothDevices();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDeviceNegative.setOnFocusChangeListener(new DoneOnEditorActionListener());
        btnDeviceNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnDevicePositive.setOnFocusChangeListener(new DoneOnEditorActionListener());
        btnDevicePositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtDeviceName.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please input the name for this device.", Toast.LENGTH_LONG).show();
                } else if (spinnerDevices.getSelectedItem() == null) {
                    Toast.makeText(getContext(), "There is no device selected.", Toast.LENGTH_LONG).show();
                } else{
                    confirm();
                }
            }
        });

        return alertDialog;
    }

    private void initialize(){
        initViewModels();
        arrayTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, types);
        btnDeviceNegative = view.findViewById(R.id.btnDeviceNegative);
        btnDevicePositive = view.findViewById(R.id.btnDevicePositive);
        spinnerDeviceType = view.findViewById(R.id.spinnerDeviceType);
        spinnerDevices = view.findViewById(R.id.spinnerDevices);
        txtDeviceName = view.findViewById(R.id.txtDeviceName);
        generate = new Generate();
        date = new Dates();
        listUSBDevices();
    }

    private void initViewModels(){
        devicesViewModel = POSApplication.getInstance().getDevicesViewModel();
    }

    private void listUSBDevices(){
        List<Devices> devices = new ArrayList<>();
        UsbManager manager = (UsbManager) getContext().getSystemService(Context.USB_SERVICE);
        PendingIntent permissionIntent;
        if(Build.VERSION.SDK_INT >= 34){
            permissionIntent = PendingIntent.getBroadcast(getContext(), 0, new Intent(BuildConfig.ACTION_USB_PERMISSION), PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_ALLOW_UNSAFE_IMPLICIT_INTENT);
        }
        else{
            permissionIntent = PendingIntent.getBroadcast(getContext(), 0, new Intent(BuildConfig.ACTION_USB_PERMISSION), PendingIntent.FLAG_IMMUTABLE);
//            OLD CODE
//            permissionIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(BuildConfig.ACTION_USB_PERMISSION), PendingIntent.FLAG_MUTABLE);
        }
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        if(manager != null && deviceList != null){
            for (UsbDevice usbDevice : deviceList.values()) {
                try {
                    devices.add(new Devices(
                            "",
                            "",
                            usbDevice.getVendorId(),
                            usbDevice.getProductId(),
                            usbDevice.getDeviceName(),
                            usbDevice.getSerialNumber(),
                            "",
                            ""
                    ));
                } catch (SecurityException e){
                    manager.requestPermission(usbDevice, permissionIntent);
                    dismiss();
                }
            }
            arrayDevicesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, devices);
            spinnerDevices.setAdapter(arrayDevicesAdapter);
        }
    }

    @SuppressLint("MissingPermission")
    private void listBluetoothDevices(){
        List<Devices> devices = new ArrayList<>();
        BluetoothConnection[] bluetoothDevicesList = POSApplication.getInstance().getBluetoothConnection();
        if(bluetoothDevicesList != null){
            for(BluetoothConnection bluetoothDevice : bluetoothDevicesList){
                devices.add(new Devices(
                        "",
                        spinnerDeviceType.getSelectedItem().toString(),
                        0,
                        0,
                        bluetoothDevice.getDevice().getName(),
                        bluetoothDevice.getDevice().getAddress(),
                        "",
                        ""
                ));
            }
            arrayDevicesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, devices);
            spinnerDevices.setAdapter(arrayDevicesAdapter);
        }
    }

    private void confirm(){
        getDialog().hide();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to save this device " + spinnerDevices.getSelectedItem().toString() + "?");
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Devices selectedDevice = (Devices) spinnerDevices.getSelectedItem();
                    Devices devices = devicesViewModel.validateDevice(txtDeviceName.getText().toString(), selectedDevice.getSerialNumber());
                    if(devices == null){
                        selectedDevice.setName(txtDeviceName.getText().toString());
                        selectedDevice.setType(spinnerDeviceType.getSelectedItem().toString());
                        selectedDevice.setTreg(date.now());
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Saving device please wait....");
                                    }
                                });

                                devicesViewModel.insert(selectedDevice);

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LoadingDialog.getInstance().closeLoadingDialog();
                                        Toast.makeText(getContext(), "Device has been saved.", Toast.LENGTH_LONG).show();
                                        dismiss();
                                    }
                                });

                            }
                        });
                    }
                    else{
                        Toast.makeText(getContext(), "This device is already save.", Toast.LENGTH_LONG).show();
                        getDialog().show();
                    }
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getDialog().show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
