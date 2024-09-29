package com.example.isyncpos.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.util.ObjectsCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.common.Printer;
import com.example.isyncpos.entity.EndOfDay;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.viewmodel.EndOfDayViewModel;
import com.example.isyncpos.viewmodel.PermissionsViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupDevicesViewModel;
import com.example.isyncpos.viewmodel.RolesViewModel;
import com.example.isyncpos.viewmodel.UsersViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.concurrent.ExecutionException;

public class ZReadingAdapter extends ListAdapter<EndOfDay, ZReadingAdapter.ViewHolder> {

    private Activity activity;
    private EndOfDayViewModel endOfDayViewModel;
    private PrinterSetupDevicesViewModel printerSetupDevicesViewModel;
    private UsersViewModel usersViewModel;
    private RolesViewModel rolesViewModel;
    private PermissionsViewModel permissionsViewModel;
    private POSProcess posProcess;
    private Dates date;
    private Generate generate;

    public ZReadingAdapter(Activity activity){
        super(DIFF_CALLBACK);
        this.activity = activity;
    }

    private static final DiffUtil.ItemCallback<EndOfDay> DIFF_CALLBACK = new DiffUtil.ItemCallback<EndOfDay>() {
        @Override
        public boolean areItemsTheSame(EndOfDay oldItem, EndOfDay newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(EndOfDay oldItem, EndOfDay newItem) {
            return ObjectsCompat.equals(oldItem, newItem);
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_zreading, parent, false);
        initialize();
        initViewModel();
        return new ZReadingAdapter.ViewHolder(view);
    }

    private void initialize(){
        posProcess = POSApplication.getInstance().getPosProcess();
        date = new Dates();
        generate = new Generate();
    }

    private void initViewModel(){
        endOfDayViewModel = POSApplication.getInstance().getEndOfDayViewModel();
        printerSetupDevicesViewModel = POSApplication.getInstance().getPrinterSetupDevicesViewModel();
        usersViewModel = POSApplication.getInstance().getUsersViewModel();
        rolesViewModel = POSApplication.getInstance().getRolesViewModel();
        permissionsViewModel = POSApplication.getInstance().getPermissionsViewModel();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final EndOfDay endOfDayItemList = getItem(position);
        if(endOfDayItemList.getGeneratedDate() != null){
            holder.txtZReadingDate.setText(date.nowDateOnly(endOfDayItemList.getGeneratedDate()));
            holder.txtZReadingTime.setText(date.timeNow(endOfDayItemList.getGeneratedDate()));
        }
        else{
            holder.txtZReadingTime.setText("");
            holder.txtZReadingDate.setText("");
        }
        holder.txtZReadingDateCoverage.setText(date.nowDateOnly(endOfDayItemList.getTreg()) +" " + date.timeNow(endOfDayItemList.getTreg()));
        holder.txtZReadingCount.setText(String.valueOf(endOfDayItemList.getReadingNumber()));
        holder.txtZReadingVatableSales.setText(generate.toTwoDecimalWithComma(endOfDayItemList.getVatableSales()));
        holder.txtZReadingVatAmount.setText(generate.toTwoDecimalWithComma(endOfDayItemList.getVatAmount()));
        holder.txtZReadingVatExemptSales.setText(generate.toTwoDecimalWithComma(endOfDayItemList.getVatExemptSales()));
        holder.txtZReadingZeroRatedSales.setText(generate.toTwoDecimalWithComma(endOfDayItemList.getTotalZeroRatedAmount()));
        holder.txtZReadingTotalSales.setText(generate.toTwoDecimalWithComma(endOfDayItemList.getNetSales()));
        holder.txtZReadingGrossReceipt.setText(generate.toTwoDecimalWithComma(endOfDayItemList.getGrossSales()));
        holder.txtZReadingBegSI.setText(endOfDayItemList.getBeginningOR());
        holder.txtZReadingEndSI.setText(endOfDayItemList.getEndingOR());
        holder.txtZReadingItemCustCount.setText("0");
        holder.txtZReadingEndOfShift.setText(String.valueOf(endOfDayItemList.getShiftNumber()));
        holder.txtZReadingOldGrandTotal.setText(generate.toTwoDecimalWithComma(endOfDayItemList.getBeginningAmount()));
        holder.txtZReadingNewGrandTotal.setText(generate.toTwoDecimalWithComma(endOfDayItemList.getEndingAmount()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtZReadingDate, txtZReadingTime, txtZReadingDateCoverage, txtZReadingCount, txtZReadingVatableSales, txtZReadingVatAmount, txtZReadingVatExemptSales, txtZReadingZeroRatedSales, txtZReadingTotalSales, txtZReadingGrossReceipt, txtZReadingBegSI, txtZReadingEndSI, txtZReadingItemCustCount, txtZReadingEndOfShift, txtZReadingOldGrandTotal, txtZReadingNewGrandTotal;
        private MaterialButton btnViewZReadingReprint;

        public ViewHolder(View itemView) {
            super(itemView);
            btnViewZReadingReprint = itemView.findViewById(R.id.btnViewZReadingReprint);
            txtZReadingDate = itemView.findViewById(R.id.txtZReadingDate);
            txtZReadingTime = itemView.findViewById(R.id.txtZReadingTime);
            txtZReadingDateCoverage = itemView.findViewById(R.id.txtZReadingDateCoverage);
            txtZReadingCount = itemView.findViewById(R.id.txtZReadingCount);
            txtZReadingVatableSales = itemView.findViewById(R.id.txtZReadingVatableSales);
            txtZReadingVatAmount = itemView.findViewById(R.id.txtZReadingVatAmount);
            txtZReadingVatExemptSales = itemView.findViewById(R.id.txtZReadingVatExemptSales);
            txtZReadingZeroRatedSales = itemView.findViewById(R.id.txtZReadingZeroRatedSales);
            txtZReadingTotalSales = itemView.findViewById(R.id.txtZReadingTotalSales);
            txtZReadingGrossReceipt = itemView.findViewById(R.id.txtZReadingGrossReceipt);
            txtZReadingBegSI = itemView.findViewById(R.id.txtZReadingBegSI);
            txtZReadingEndSI = itemView.findViewById(R.id.txtZReadingEndSI);
            txtZReadingItemCustCount = itemView.findViewById(R.id.txtZReadingItemCustCount);
            txtZReadingEndOfShift = itemView.findViewById(R.id.txtZReadingEndOfShift);
            txtZReadingOldGrandTotal = itemView.findViewById(R.id.txtZReadingOldGrandTotal);
            txtZReadingNewGrandTotal = itemView.findViewById(R.id.txtZReadingNewGrandTotal);

            btnViewZReadingReprint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    processReprintEndOfDay(getItem(getAbsoluteAdapterPosition()).getId());
                }
            });

        }

    }

    private void processReprintEndOfDay(int endOfDayId){
        try {
            EndOfDay endOfDay = endOfDayViewModel.fetchEndOfDayInformationById(endOfDayId);
            if(endOfDay.getPrintString() != null){
                if(!endOfDay.getPrintString().isEmpty()){
                    Printer.getInstance().print(activity, printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_REPRINT_Z_READING), endOfDay.getPrintString());
                }
                else{
                    Toast.makeText(activity, "Cannot proceed reprint please contact technical support.", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(activity, "Cannot proceed reprint please contact technical support.", Toast.LENGTH_LONG).show();
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
