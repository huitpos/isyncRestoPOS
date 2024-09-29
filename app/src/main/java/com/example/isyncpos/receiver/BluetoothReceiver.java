package com.example.isyncpos.receiver;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.example.isyncpos.MainActivity;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.entity.Devices;
import com.example.isyncpos.viewmodel.DevicesViewModel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BluetoothReceiver extends BroadcastReceiver {

    private MainActivity mainActivity;

    public BluetoothReceiver(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("onReceive", "onReceive: " + action.toString());
        if(action != null){
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if(bluetoothAdapter != null && bluetoothAdapter.isEnabled()){
                if (action.toString().equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
                    Log.d("onReceiveDisconnected", "onReceiveDisconnected: ");
                    mainActivity.reconnectBluetoothPrinter();
                }
                else{
                    if(!action.toString().equals(BluetoothDevice.ACTION_ACL_CONNECTED)){
                        Log.d("onReceiveConnected", "onReceiveConnected: ");
                        mainActivity.reconnectBluetoothPrinter();
                    }
                }
            }
            else{
                mainActivity.disconnectBluetoothPrinter();
            }
        }
    }

}
