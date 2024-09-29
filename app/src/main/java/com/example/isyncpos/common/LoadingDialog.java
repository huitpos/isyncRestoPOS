package com.example.isyncpos.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.isyncpos.R;

public class LoadingDialog {

    private static LoadingDialog instance;
    private AlertDialog alertDialog;
    private TextView loadingMessage;

    public static LoadingDialog getInstance(){
        if(instance == null){
            instance = new LoadingDialog();
        }
        return instance;
    }

    public void startLoadingDialog(Activity activity, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_dialog, null);
        if(message == null){
            message = "Loading Please Wait......";
        }
        builder.setView(view);
        builder.setCancelable(false);
        loadingMessage = view.findViewById(R.id.lblLoadingMessage);
        loadingMessage.setText(message);
//        alertDialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void changeLoadingMessage(String message){
        if(alertDialog != null){
            loadingMessage.setText(message);
        }
    }

    public void closeLoadingDialog(){
        //Check if has a object instance of alertdialog
        if(alertDialog != null){
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

}
