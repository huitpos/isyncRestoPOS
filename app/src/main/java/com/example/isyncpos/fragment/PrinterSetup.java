package com.example.isyncpos.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.adapters.PrinterSetupAdapter;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.entity.Devices;
import com.example.isyncpos.entity.PrinterSetupDevices;
import com.example.isyncpos.viewmodel.DevicesViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupDevicesViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrinterSetup extends Fragment {

    private ArrayAdapter<Devices> arrayDevicesAdapter;
    private Spinner spinnerAvailablePrinter;
    private MaterialButton btnPrinterSetupSelectAll, btnPrinterSetupAdd, btnPrinterSetupDeleteAll;
    private RecyclerView recyclerPrinterSetup;
    private PrinterSetupAdapter printerSetupAdapter;
    private DevicesViewModel devicesViewModel;
    private PrinterSetupViewModel printerSetupViewModel;
    private PrinterSetupDevicesViewModel printerSetupDevicesViewModel;
    private Dates date;
    private boolean isActiveFragment = false;

    public static PrinterSetup newPrinterSetup(){
        return new PrinterSetup();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_printer_setup, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();

        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            printerSetupViewModel.fetchAllCashierType().observe(getActivity(), new Observer<List<com.example.isyncpos.entity.PrinterSetup>>() {
                @Override
                public void onChanged(List<com.example.isyncpos.entity.PrinterSetup> printerSetups) {
                    if(isActiveFragment){
                        printerSetupAdapter.submitList(printerSetups);
                    }
                }
            });
        }
        else{
            printerSetupViewModel.fetchAllTakeOrderType().observe(getActivity(), new Observer<List<com.example.isyncpos.entity.PrinterSetup>>() {
                @Override
                public void onChanged(List<com.example.isyncpos.entity.PrinterSetup> printerSetups) {
                    if(isActiveFragment){
                        printerSetupAdapter.submitList(printerSetups);
                    }
                }
            });
        }

        devicesViewModel.fetchAll().observe(getActivity(), new Observer<List<Devices>>() {
            @Override
            public void onChanged(List<Devices> devices) {
                if(isActiveFragment){
                    arrayDevicesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, devices);
                    spinnerAvailablePrinter.setAdapter(arrayDevicesAdapter);
                }
            }
        });

        btnPrinterSetupSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printerSetupAdapter.selectAll();
            }
        });

        btnPrinterSetupAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<com.example.isyncpos.entity.PrinterSetup> selectedArrayList = printerSetupAdapter.getSelectedListPrinterSetup();
                if(selectedArrayList.size() != 0){
                    Devices selectedDevice = (Devices) spinnerAvailablePrinter.getSelectedItem();
                    if(selectedDevice != null){
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Adding devices to the setup please wait...");
                                    }
                                });

                                try {
                                    for(com.example.isyncpos.entity.PrinterSetup item : selectedArrayList){
                                        PrinterSetupDevices printerSetupDevices = printerSetupDevicesViewModel.validatePrinterSetupDevices(item.getId(), selectedDevice.getId());
                                        if(printerSetupDevices == null){
                                            printerSetupDevicesViewModel.insert(new PrinterSetupDevices(
                                                    item.getId(),
                                                    selectedDevice.getId(),
                                                    selectedDevice.getName(),
                                                    date.now()
                                            ));
                                        }
                                    }
                                } catch (ExecutionException e) {
                                    throw new RuntimeException(e);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        for(com.example.isyncpos.entity.PrinterSetup item : selectedArrayList){
                                            try {
                                                com.example.isyncpos.entity.PrinterSetup printerSetup = printerSetupViewModel.fetchPrinterSetup(item.getId());
                                                printerSetup.setDeviceCount(printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(item.getId()).size());
                                                printerSetupViewModel.update(printerSetup);
                                            } catch (ExecutionException e) {
                                                throw new RuntimeException(e);
                                            } catch (InterruptedException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                        printerSetupAdapter.clearSelectedItem();
                                        LoadingDialog.getInstance().closeLoadingDialog();
                                    }
                                });

                            }
                        });
                    }
                    else{
                        Toast.makeText(getContext(), "There is no selected device.", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "There is no selected items.", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnPrinterSetupDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to remove all devices in your printer setup?");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        printerSetupViewModel.clearDevices();
                        printerSetupDevicesViewModel.removeAll();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    private void initialize(){
        initViewModels();
        spinnerAvailablePrinter = getView().findViewById(R.id.spinnerAvailablePrinter);
        btnPrinterSetupSelectAll = getView().findViewById(R.id.btnPrinterSetupSelectAll);
        btnPrinterSetupAdd = getView().findViewById(R.id.btnPrinterSetupAdd);
        btnPrinterSetupDeleteAll = getView().findViewById(R.id.btnPrinterSetupDeleteAll);
        date = new Dates();
        initRecyclerView();
    }

    private void initViewModels(){
        this.devicesViewModel = POSApplication.getInstance().getDevicesViewModel();
        this.printerSetupViewModel = POSApplication.getInstance().getPrinterSetupViewModel();
        this.printerSetupDevicesViewModel = POSApplication.getInstance().getPrinterSetupDevicesViewModel();
    }

    private void initRecyclerView(){
        recyclerPrinterSetup = getView().findViewById(R.id.recyclerPrinterSetup);
        recyclerPrinterSetup.setHasFixedSize(true);
        recyclerPrinterSetup.setLayoutManager(new LinearLayoutManager(getContext()));
        printerSetupAdapter = new PrinterSetupAdapter(printerSetupViewModel, printerSetupDevicesViewModel, getContext(), getActivity());
        recyclerPrinterSetup.setAdapter(printerSetupAdapter);
    }

    public void setActiveFragment(boolean value){
        isActiveFragment = value;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            printerSetupViewModel.fetchAllCashierType().removeObservers(this);
        }
        else{
            printerSetupViewModel.fetchAllTakeOrderType().removeObservers(this);
        }
        devicesViewModel.fetchAll().removeObservers(this);
    }
}
