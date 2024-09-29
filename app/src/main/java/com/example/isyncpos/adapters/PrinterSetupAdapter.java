package com.example.isyncpos.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.util.ObjectsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.R;
import com.example.isyncpos.common.Font;
import com.example.isyncpos.dialog.PrinterSetupInformationDialog;
import com.example.isyncpos.dialog.ResumeTransactionDialog;
import com.example.isyncpos.entity.PrinterSetup;
import com.example.isyncpos.entity.PrinterSetupDevices;
import com.example.isyncpos.viewmodel.PrinterSetupDevicesViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrinterSetupAdapter extends ListAdapter<PrinterSetup, PrinterSetupAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<PrinterSetup> selectedListPrinterSetup = new ArrayList<>();
    private PrinterSetupViewModel printerSetupViewModel;
    private PrinterSetupDevicesViewModel printerSetupDevicesViewModel;
    private Context context;
    private Activity activity;

    public PrinterSetupAdapter(PrinterSetupViewModel printerSetupViewModel, PrinterSetupDevicesViewModel printerSetupDevicesViewModel, Context context, Activity activity){
        super(DIFF_CALLBACK);
        this.printerSetupViewModel = printerSetupViewModel;
        this.printerSetupDevicesViewModel = printerSetupDevicesViewModel;
        this.context = context;
        this.activity = activity;
    }

    private static final DiffUtil.ItemCallback<PrinterSetup> DIFF_CALLBACK = new DiffUtil.ItemCallback<PrinterSetup>() {
        @Override
        public boolean areItemsTheSame(PrinterSetup oldItem, PrinterSetup newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(PrinterSetup oldItem, PrinterSetup newItem) {
            return ObjectsCompat.equals(oldItem, newItem);
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_printer_setup, parent, false);
        PrinterSetupAdapter.ViewHolder viewHolder = new PrinterSetupAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PrinterSetup printerSetupItemsList = getItem(position);
        holder.labelPrinterSetupName.setText(printerSetupItemsList.getName());
        //This for select all
        if(selectedListPrinterSetup.size() != 0){
            for(PrinterSetup item : selectedListPrinterSetup){
                if(item.getId() == printerSetupItemsList.getId()){
                    holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.yellow, null));
                    holder.isSelected = true;
                    break;
                }
            }
        }
        else{
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.white, null));
            holder.isSelected = false;
        }

        //This is for printer setup devices
        try {
            if(selectedListPrinterSetup.size() == 0){
                holder.linearPrinterSetupDevices.setVisibility(View.GONE);
                holder.linearPrinterSetupDevicesLoading.setVisibility(View.VISIBLE);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 0, 10, 0);
            holder.linearPrinterSetupDevices.removeAllViews();
            for(PrinterSetupDevices item : printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(printerSetupItemsList.getId())){
                MaterialButton materialButton = new MaterialButton(context);
                materialButton.setTypeface(Font.getInstance().getSanFranciscoBoldFont(context.getResources()));
                materialButton.setText(item.getName());
                materialButton.setTextSize(10);
                materialButton.setAlpha(.8f);
                materialButton.setId(item.getId());
                materialButton.setCornerRadius(60);
                materialButton.setBackgroundColor(context.getColor(R.color.acolor));
                materialButton.setTextColor(context.getResources().getColor(R.color.white, null));
                materialButton.setIconTintResource(R.color.white);
                materialButton.setLayoutParams(layoutParams);
                materialButton.setOnClickListener(PrinterSetupAdapter.this);
                holder.linearPrinterSetupDevices.addView(materialButton);
            }
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.linearPrinterSetupDevices.setVisibility(View.VISIBLE);
                    holder.linearPrinterSetupDevicesLoading.setVisibility(View.GONE);
                }
            }, 1000);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void selectAll(){
        selectedListPrinterSetup.clear();
        for(PrinterSetup item : getCurrentList()){
            selectedListPrinterSetup.add(item);
        }
        notifyDataSetChanged();
    }

    public void clearSelectedItem(){
        selectedListPrinterSetup.clear();
    }

    public ArrayList<PrinterSetup> getSelectedListPrinterSetup(){
        return selectedListPrinterSetup;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            default:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want remove this device?");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            PrinterSetupDevices printerSetupDevices = printerSetupDevicesViewModel.fetchPrinterSetupDevice(view.getId());
                            PrinterSetup printerSetup = printerSetupViewModel.fetchPrinterSetup(printerSetupDevices.getPrinterSetupId());
                            ExecutorService executorService = Executors.newSingleThreadExecutor();
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {

                                    printerSetupDevicesViewModel.remove(printerSetupDevices);

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                printerSetup.setDeviceCount(printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(printerSetup.getId()).size());
                                                printerSetupViewModel.update(printerSetup);
                                            } catch (ExecutionException e) {
                                                throw new RuntimeException(e);
                                            } catch (InterruptedException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    });

                                }
                            });
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView labelPrinterSetupName;
        private LinearLayout linearPrinterSetupDevices, linearPrinterSetupDevicesLoading;
        private MaterialButton btnPrinterSetupInformation;
        private boolean isSelected = false;

        public ViewHolder(View itemView) {
            super(itemView);
            labelPrinterSetupName = itemView.findViewById(R.id.labelPrinterSetupName);
            linearPrinterSetupDevices = itemView.findViewById(R.id.linearPrinterSetupDevices);
            linearPrinterSetupDevicesLoading = itemView.findViewById(R.id.linearPrinterSetupDevicesLoading);
            btnPrinterSetupInformation = itemView.findViewById(R.id.btnPrinterSetupInformation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isSelected){
                        itemView.setBackgroundColor(itemView.getResources().getColor(R.color.white, null));
                        isSelected = false;
                        selectedListPrinterSetup.remove(getItem(getAbsoluteAdapterPosition()));
                    }
                    else{
                        itemView.setBackgroundColor(itemView.getResources().getColor(R.color.yellow, null));
                        isSelected = true;
                        selectedListPrinterSetup.add(getItem(getAbsoluteAdapterPosition()));
                    }
                }
            });

            btnPrinterSetupInformation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrinterSetupInformationDialog printerSetupInformationDialog = PrinterSetupInformationDialog.newPrinterSetupInformationDialog();
                    printerSetupInformationDialog.setArgs(getItem(getAbsoluteAdapterPosition()));
                    printerSetupInformationDialog.setCancelable(false);
                    printerSetupInformationDialog.show(((FragmentActivity) activity).getSupportFragmentManager(), "Printer Setup Information Dialog");
                }
            });

        }

    }

}
