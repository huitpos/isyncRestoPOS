package com.example.isyncpos.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.isyncpos.fragment.Menu;

public class WifiReceiver extends BroadcastReceiver {

    private Menu menu;

    public void setMenu(Menu menu){
        this.menu = menu;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null){
            if(networkInfo.isConnectedOrConnecting()){
                menu.testConnection();
            }
            else{
                menu.testConnection();
            }
        }
        else{
            menu.testConnection();
        }
    }



}
