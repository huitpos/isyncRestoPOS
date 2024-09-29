package com.example.isyncpos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.isyncpos.fragment.DeviceManager;
import com.example.isyncpos.fragment.POS;
import com.example.isyncpos.fragment.PrinterSetup;
import com.example.isyncpos.fragment.SystemInformation;
import com.example.isyncpos.preferences.Cache;
import com.example.isyncpos.viewmodel.DevicesViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupDevicesViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupViewModel;
import com.google.android.material.button.MaterialButton;

public class SettingsActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private SystemInformation systemInformation;
    private DeviceManager deviceManager;
    private PrinterSetup printerSetup;
    private POS pos;
    private MaterialButton btnSettingsBack, btnSettingsPrinterSetup, btnSettingsSystemInfo, btnSettingsDeviceManager, btnSettingsPOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Initialize
        initialize();

        btnSettingsSystemInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemInformation();
            }
        });

        btnSettingsPrinterSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterSetup();
            }
        });

        btnSettingsDeviceManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceManager();
            }
        });

        btnSettingsPOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos();
            }
        });

        btnSettingsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                back();
            }
        });

    }

    private void initialize(){
        systemInformation = SystemInformation.newSystemInformation();
        printerSetup = PrinterSetup.newPrinterSetup();
        deviceManager = DeviceManager.newDeviceManager();
        pos = POS.newPOS();
        btnSettingsBack = findViewById(R.id.btnSettingsBack);
        btnSettingsPOS = findViewById(R.id.btnSettingsPOS);
        btnSettingsPrinterSetup = findViewById(R.id.btnSettingsPrinterSetup);
        btnSettingsSystemInfo = findViewById(R.id.btnSettingsSystemInfo);
        btnSettingsDeviceManager = findViewById(R.id.btnSettingsDeviceManager);
        initFirstFragment();
    }

    private void back(){
        finish();
    }

    private void initFirstFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        systemInformation.setActiveFragment(true);
        fragmentTransaction.replace(R.id.fragmentSettings, systemInformation);
        fragmentTransaction.commit();
    }

    private void SystemInformation(){
        setIsActiveFragmentsFalse();
        systemInformation.setActiveFragment(true);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragmentSettings, systemInformation);
        fragmentTransaction.commit();
    }

    private void DeviceManager(){
        setIsActiveFragmentsFalse();
        deviceManager.setActiveFragment(true);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragmentSettings, deviceManager);
        fragmentTransaction.commit();
    }

    private void PrinterSetup(){
        setIsActiveFragmentsFalse();
        printerSetup.setActiveFragment(true);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragmentSettings, printerSetup);
        fragmentTransaction.commit();
    }

    private void pos(){
        setIsActiveFragmentsFalse();
        pos.setActiveFragment(true);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragmentSettings, pos);
        fragmentTransaction.commit();
    }

    private void setIsActiveFragmentsFalse(){
        systemInformation.setActiveFragment(false);
        deviceManager.setActiveFragment(false);
        printerSetup.setActiveFragment(false);
        pos.setActiveFragment(false);
    }

}
