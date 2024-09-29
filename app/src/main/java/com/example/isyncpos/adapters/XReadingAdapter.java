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
import com.example.isyncpos.entity.CutOff;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.viewmodel.CutOffViewModel;
import com.example.isyncpos.viewmodel.PermissionsViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupDevicesViewModel;
import com.example.isyncpos.viewmodel.RolesViewModel;
import com.example.isyncpos.viewmodel.UsersViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.concurrent.ExecutionException;

public class XReadingAdapter extends ListAdapter<CutOff, XReadingAdapter.ViewHolder> {

    private Activity activity;
    private CutOffViewModel cutOffViewModel;
    private PrinterSetupDevicesViewModel printerSetupDevicesViewModel;
    private UsersViewModel usersViewModel;
    private RolesViewModel rolesViewModel;
    private PermissionsViewModel permissionsViewModel;
    private POSProcess posProcess;
    private Dates date;
    private Generate generate;

    public XReadingAdapter(Activity activity){
        super(DIFF_CALLBACK);
        this.activity = activity;
    }

    private static final DiffUtil.ItemCallback<CutOff> DIFF_CALLBACK = new DiffUtil.ItemCallback<CutOff>() {
        @Override
        public boolean areItemsTheSame(CutOff oldItem, CutOff newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(CutOff oldItem, CutOff newItem) {
            return ObjectsCompat.equals(oldItem, newItem);
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_xreading, parent, false);
        initialize();
        initViewModel();
        return new XReadingAdapter.ViewHolder(view);
    }

    private void initialize(){
        posProcess = POSApplication.getInstance().getPosProcess();
        date = new Dates();
        generate = new Generate();
    }

    private void initViewModel(){
        cutOffViewModel = POSApplication.getInstance().getCutOffViewModel();
        printerSetupDevicesViewModel = POSApplication.getInstance().getPrinterSetupDevicesViewModel();
        usersViewModel = POSApplication.getInstance().getUsersViewModel();
        rolesViewModel = POSApplication.getInstance().getRolesViewModel();
        permissionsViewModel = POSApplication.getInstance().getPermissionsViewModel();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CutOff cutOffItemList = getItem(position);
        holder.txtXReadingDate.setText(date.nowDateOnly(cutOffItemList.getTreg()));
        holder.txtXReadingTime.setText(date.timeNow(cutOffItemList.getTreg()));
        holder.txtXReadingCashierName.setText(cutOffItemList.getCashierName());
        holder.txtXReadingCount.setText(String.valueOf(cutOffItemList.getReadingNumber()));
        holder.txtXReadingVatableSales.setText(generate.toTwoDecimalWithComma(cutOffItemList.getVatableSales()));
        holder.txtXReadingVatAmount.setText(generate.toTwoDecimalWithComma(cutOffItemList.getVatAmount()));
        holder.txtXReadingVatExemptSales.setText(generate.toTwoDecimalWithComma(cutOffItemList.getVatExemptSales()));
        holder.txtXReadingZeroRatedSales.setText(generate.toTwoDecimalWithComma(cutOffItemList.getTotalZeroRatedAmount()));
        holder.txtXReadingGrossSales.setText(generate.toTwoDecimalWithComma(cutOffItemList.getGrossSales()));
        holder.txtXReadingBegSI.setText(cutOffItemList.getBeginningOR());
        holder.txtXReadingEndSI.setText(cutOffItemList.getEndingOR());
        holder.txtXReadingItemCustCount.setText("0");
        holder.txtXReadingEndOfShift.setText(String.valueOf(cutOffItemList.getShiftNumber()));
        holder.txtXReadingOldGrandTotal.setText(generate.toTwoDecimalWithComma(cutOffItemList.getBeginningAmount()));
        holder.txtXReadingNewGrandTotal.setText(generate.toTwoDecimalWithComma(cutOffItemList.getEndingAmount()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtXReadingDate, txtXReadingTime, txtXReadingCashierName, txtXReadingCount, txtXReadingVatableSales, txtXReadingVatAmount, txtXReadingVatExemptSales, txtXReadingZeroRatedSales, txtXReadingGrossSales, txtXReadingBegSI, txtXReadingEndSI, txtXReadingItemCustCount, txtXReadingEndOfShift, txtXReadingOldGrandTotal, txtXReadingNewGrandTotal;
        private MaterialButton btnViewXReadingReprint;

        public ViewHolder(View itemView) {
            super(itemView);
            btnViewXReadingReprint = itemView.findViewById(R.id.btnViewXReadingReprint);
            txtXReadingDate = itemView.findViewById(R.id.txtXReadingDate);
            txtXReadingTime = itemView.findViewById(R.id.txtXReadingTime);
            txtXReadingCashierName = itemView.findViewById(R.id.txtXReadingCashierName);
            txtXReadingCount = itemView.findViewById(R.id.txtXReadingCount);
            txtXReadingVatableSales = itemView.findViewById(R.id.txtXReadingVatableSales);
            txtXReadingVatAmount = itemView.findViewById(R.id.txtXReadingVatAmount);
            txtXReadingVatExemptSales = itemView.findViewById(R.id.txtXReadingVatExemptSales);
            txtXReadingZeroRatedSales = itemView.findViewById(R.id.txtXReadingZeroRatedSales);
            txtXReadingGrossSales = itemView.findViewById(R.id.txtXReadingGrossSales);
            txtXReadingBegSI = itemView.findViewById(R.id.txtXReadingBegSI);
            txtXReadingEndSI = itemView.findViewById(R.id.txtXReadingEndSI);
            txtXReadingItemCustCount = itemView.findViewById(R.id.txtXReadingItemCustCount);
            txtXReadingEndOfShift = itemView.findViewById(R.id.txtXReadingEndOfShift);
            txtXReadingOldGrandTotal = itemView.findViewById(R.id.txtXReadingOldGrandTotal);
            txtXReadingNewGrandTotal = itemView.findViewById(R.id.txtXReadingNewGrandTotal);

            btnViewXReadingReprint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    processReprintCutOff(getItem(getAbsoluteAdapterPosition()).getId());
                }
            });

        }

    }

    private void processReprintCutOff(int cutOffId){
        try {
            CutOff cutOff = cutOffViewModel.fetchCutOffInformationById(cutOffId);
            if(cutOff.getPrintString() != null){
                if(!cutOff.getPrintString().isEmpty()){
                    Printer.getInstance().print(activity, printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_REPRINT_X_READING), cutOff.getPrintString());
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
