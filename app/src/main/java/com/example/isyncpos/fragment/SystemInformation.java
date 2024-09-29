package com.example.isyncpos.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.R;

public class SystemInformation extends Fragment {

    private boolean isActiveFragment = false;
    private TextView labelSystemPOSVersion;
    private View view;

    public static SystemInformation newSystemInformation(){
        return new SystemInformation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_system_info, container, false);
        initialize();
        setSystemInformation();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initialize(){
        labelSystemPOSVersion = view.findViewById(R.id.labelSystemPOSVersion);
    }

    private void setSystemInformation(){
        if(BuildConfig.ENV.equals("DEVELOPMENT")){
            labelSystemPOSVersion.setText("UAT " + BuildConfig.APP_VERSION);
        }
        else{
            labelSystemPOSVersion.setText("PROD " + BuildConfig.APP_VERSION);
        }
    }

    public void setActiveFragment(boolean value){
        isActiveFragment = value;
    }

}
