package com.example.isyncpos.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.util.ObjectsCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnections;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.common.Printer;
import com.example.isyncpos.entity.Devices;
import com.example.isyncpos.entity.PrinterSetup;
import com.example.isyncpos.entity.PrinterSetupDevices;
import com.example.isyncpos.viewmodel.DevicesViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupDevicesViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DevicesAdapter extends ListAdapter<Devices, DevicesAdapter.ViewHolder> {

    private Activity activity;
    private DevicesViewModel devicesViewModel;
    private PrinterSetupViewModel printerSetupViewModel;
    private PrinterSetupDevicesViewModel printerSetupDevicesViewModel;

    public DevicesAdapter(Activity activity, DevicesViewModel devicesViewModel, PrinterSetupDevicesViewModel printerSetupDevicesViewModel, PrinterSetupViewModel printerSetupViewModel){
        super(DIFF_CALLBACK);
        this.activity = activity;
        this.devicesViewModel = devicesViewModel;
        this.printerSetupDevicesViewModel = printerSetupDevicesViewModel;
        this.printerSetupViewModel = printerSetupViewModel;
    }

    private static final DiffUtil.ItemCallback<Devices> DIFF_CALLBACK = new DiffUtil.ItemCallback<Devices>() {
        @Override
        public boolean areItemsTheSame(Devices oldItem, Devices newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Devices oldItem, Devices newItem) {
            return ObjectsCompat.equals(oldItem, newItem);
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_devices, parent, false);
        DevicesAdapter.ViewHolder viewHolder = new DevicesAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Devices devicesItemsList = getItem(position);
        holder.itemDeviceName.setText(devicesItemsList.getName());
        if(devicesItemsList.getType().equals("USB")){
            UsbManager usbManager = (UsbManager) activity.getSystemService(Context.USB_SERVICE);
            UsbDevice usbDevice = (UsbDevice) usbManager.getDeviceList().get(devicesItemsList.getDevice());
            holder.itemDeviceConnection.setText(usbDevice != null && usbManager != null ? "Online" : "Offline");
        } else if (devicesItemsList.getType().equals("BLUETOOTH")) {
            BluetoothConnection[] bluetoothDevicesList = POSApplication.getInstance().getBluetoothConnection();
            if(bluetoothDevicesList != null){
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                holder.linearDeviceActions.setVisibility(View.GONE);
                                holder.linearDeviceLoading.setVisibility(View.VISIBLE);
                                holder.itemDeviceConnection.setText("Connecting to bluetooth device please wait...");
                            }
                        });

                        for(BluetoothConnection item : bluetoothDevicesList){
                            if (devicesItemsList.getSerialNumber().equals(item.getDevice().getAddress())){
                                try {
                                    if(!item.isConnected()){
                                        item.connect();
                                    }
                                    holder.isBluetoothConnected = true;
                                } catch (EscPosConnectionException e) {
                                    Log.d("bluetoothConnection", "bluetoothConnection: " + e.toString());
                                }
                            }
                        }

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                holder.linearDeviceActions.setVisibility(View.VISIBLE);
                                holder.linearDeviceLoading.setVisibility(View.GONE);
                                holder.itemDeviceConnection.setText(holder.isBluetoothConnected ? "Online" : "Offline");
                            }
                        });

                    }
                });
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView itemDeviceName, itemDeviceConnection;
        private MaterialButton btnItemDeviceTest, btnItemDeviceRemove;
        private LinearLayout linearDeviceLoading, linearDeviceActions;
        private boolean isBluetoothConnected = false;

        public ViewHolder(View itemView) {
            super(itemView);
            itemDeviceName = itemView.findViewById(R.id.itemDeviceName);
            itemDeviceConnection = itemView.findViewById(R.id.itemDeviceConnection);
            btnItemDeviceTest = itemView.findViewById(R.id.btnItemDeviceTest);
            btnItemDeviceRemove = itemView.findViewById(R.id.btnItemDeviceRemove);
            linearDeviceLoading = itemView.findViewById(R.id.linearDeviceLoading);
            linearDeviceActions = itemView.findViewById(R.id.linearDeviceActions);
            btnItemDeviceTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getItem(getAbsoluteAdapterPosition()).getType().equals("USB")){
                        UsbManager usbManager = (UsbManager) activity.getSystemService(Context.USB_SERVICE);
                        UsbDevice usbDevice = (UsbDevice) usbManager.getDeviceList().get(getItem(getAbsoluteAdapterPosition()).getDevice());
                        if(usbManager != null && usbDevice != null){
                            Printer.getInstance().testPrint(activity, getItem(getAbsoluteAdapterPosition()).getDevice(),getItem(getAbsoluteAdapterPosition()).getType());
                        }
                        else{
                            itemDeviceConnection.setText("Offline");
                            Toast.makeText(activity, "The printer is offline.", Toast.LENGTH_LONG).show();
                        }
                    } else if (getItem(getAbsoluteAdapterPosition()).getType().equals("BLUETOOTH")) {
                        BluetoothConnection[] bluetoothDevicesList = POSApplication.getInstance().getBluetoothConnection();
                        if(bluetoothDevicesList != null){
                            ExecutorService executorService = Executors.newSingleThreadExecutor();
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            linearDeviceActions.setVisibility(View.GONE);
                                            linearDeviceLoading.setVisibility(View.VISIBLE);
                                        }
                                    });

                                    for(BluetoothConnection item : bluetoothDevicesList){
                                        if (getItem(getAbsoluteAdapterPosition()).getSerialNumber().equals(item.getDevice().getAddress())){
                                            try {
                                                item.connect();
                                                isBluetoothConnected = true;
                                            } catch (EscPosConnectionException e) {
                                                Log.d("bluetoothConnection", "bluetoothConnection: " + e.toString());
                                                isBluetoothConnected = false;
                                            }
                                        }
                                    }

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            linearDeviceActions.setVisibility(View.VISIBLE);
                                            linearDeviceLoading.setVisibility(View.GONE);
                                            if(isBluetoothConnected){
                                                itemDeviceConnection.setText("Online");
                                                Printer.getInstance().testPrint(activity, getItem(getAbsoluteAdapterPosition()).getDevice(), getItem(getAbsoluteAdapterPosition()).getType());
                                            }
                                            else {
                                                itemDeviceConnection.setText("Offline");
                                                Toast.makeText(activity, "The printer is offline.", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                                }
                            });
                        }
                    }
                }
            });

            btnItemDeviceRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to remove this device " + getItem(getAbsoluteAdapterPosition()).getName() + "?");
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                LoadingDialog.getInstance().startLoadingDialog(activity,"Removing device please wait...");
                                BluetoothConnection[] bluetoothDevicesList = POSApplication.getInstance().getBluetoothConnection();
                                if(bluetoothDevicesList != null){
                                    for(BluetoothConnection item : bluetoothDevicesList){
                                        if (getItem(getAbsoluteAdapterPosition()).getSerialNumber().equals(item.getDevice().getAddress())){
                                            item.disconnect();
                                            isBluetoothConnected = false;
                                            break;
                                        }
                                    }
                                }
                                List<PrinterSetupDevices> printerSetupDevices = printerSetupDevicesViewModel.fetchPrinterSetupDevicesDeviceID(getItem(getAbsoluteAdapterPosition()).getId());
                                for(PrinterSetupDevices item : printerSetupDevices){
                                    PrinterSetup printerSetup = printerSetupViewModel.fetchPrinterSetup(item.getPrinterSetupId());
                                    if(printerSetup != null){
                                        printerSetup.setDeviceCount(printerSetup.getDeviceCount() - 1);
                                        printerSetupViewModel.update(printerSetup);
                                    }
                                    printerSetupDevicesViewModel.remove(item);
                                }
                                devicesViewModel.removeDevice(getItem(getAbsoluteAdapterPosition()).getId());
                                LoadingDialog.getInstance().closeLoadingDialog();
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

        }

    }
}
