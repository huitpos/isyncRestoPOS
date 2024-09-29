package com.example.isyncpos.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.util.ObjectsCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.common.Compute;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.entity.CashDenomination;
import com.example.isyncpos.entity.CashFundDenomination;
import com.example.isyncpos.entity.SafekeepingDenomination;
import com.example.isyncpos.entity.SpotAuditDenomination;

import java.util.ArrayList;
import java.util.List;

public class CashDenominationAdapter extends ListAdapter<CashDenomination, CashDenominationAdapter.ViewHolder> {

    private CashDenominationListener cashDenominationListener;
    private List<SafekeepingDenomination> safekeepingDenominations = new ArrayList<>();
    private List<CashFundDenomination> cashFundDenominations = new ArrayList<>();
    private List<SpotAuditDenomination> spotAuditDenominations = new ArrayList<>();
    private Compute compute;
    private double totalSafekeeping;
    private String method;
    private Dates date;

    public interface CashDenominationListener{
        void onChangeTotalSafeKeeping(double value, String method);
    }

    public CashDenominationAdapter(){
        super(DIFF_CALLBACK);
        compute = new Compute();
        date = new Dates();
    }

    public void setMethod(String value){
        method = value;
    }

    private static final DiffUtil.ItemCallback<CashDenomination> DIFF_CALLBACK = new DiffUtil.ItemCallback<CashDenomination>() {
        @Override
        public boolean areItemsTheSame(CashDenomination oldItem, CashDenomination newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(CashDenomination oldItem, CashDenomination newItem) {
            return ObjectsCompat.equals(oldItem, newItem);
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_safekeeping, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void submitList(List<CashDenomination> list) {
        super.submitList(list);
        String treg = date.now();
        if(method.equals("SAFEKEEPING")){
            for(CashDenomination item : list){
                safekeepingDenominations.add(new SafekeepingDenomination(
                        POSApplication.getInstance().getMachineDetails().getCoreId(),
                        POSApplication.getInstance().getBranch().getCoreId(),
                        0,
                        item.getCoreId(),
                        item.getName(),
                        item.getAmount(),
                        0,
                        0,
                        0,
                        0,
                        treg,
                        0,
                        0,
                        1,
                        POSApplication.getInstance().getCompany().getCoreId()
                ));
            }
        } else if (method.equals("CASH FUND")) {
            for(CashDenomination item : list){
                cashFundDenominations.add(new CashFundDenomination(
                    POSApplication.getInstance().getMachineDetails().getCoreId(),
                    POSApplication.getInstance().getBranch().getCoreId(),
                    0,
                    item.getCoreId(),
                    item.getName(),
                    item.getAmount(),
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    treg,
                    POSApplication.getInstance().getCompany().getCoreId()
                ));
            }
        } else if (method.equals("SPOT AUDIT")) {
            for(CashDenomination item : list){
                spotAuditDenominations.add(new SpotAuditDenomination(
                   POSApplication.getInstance().getMachineDetails().getCoreId(),
                   POSApplication.getInstance().getBranch().getCoreId(),
                   0,
                   item.getCoreId(),
                   item.getName(),
                   item.getAmount(),
                   0,
                   0,
                   0,
                   0,
                   0,
                   0,
                    treg,
                    POSApplication.getInstance().getCompany().getCoreId()
                ));
            }
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CashDenomination cashDenominationList = getItem(position);
        holder.cashDenominationName.setText(cashDenominationList.getName());
        holder.cashDenominationQty.setHint(String.valueOf(cashDenominationList.getAmount()));
    }

    public List<SafekeepingDenomination> fetchSafekeepingList(){
        return safekeepingDenominations;
    }

    public List<CashFundDenomination> fetchCashFundList(){
        return cashFundDenominations;
    }

    public List<SpotAuditDenomination> fetchSpotAuditList(){
        return spotAuditDenominations;
    }

    private double recomputeTotalSafeKeeping(){
        totalSafekeeping = 0;
        if(method.equals("SAFEKEEPING")){
            for(SafekeepingDenomination item : safekeepingDenominations){
                totalSafekeeping += item.getTotal();
            }
        } else if(method.equals("CASH FUND")) {
            for(CashFundDenomination item : cashFundDenominations){
                totalSafekeeping += item.getTotal();
            }
        } else if (method.equals("SPOT AUDIT")) {
            for(SpotAuditDenomination item : spotAuditDenominations){
                totalSafekeeping += item.getTotal();
            }
        }
        return totalSafekeeping;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView cashDenominationName;
        private EditText cashDenominationQty;

        public ViewHolder(View itemView) {
            super(itemView);
            cashDenominationName = itemView.findViewById(R.id.labelCashDenominationName);
            cashDenominationQty = itemView.findViewById(R.id.txtCashDenominationQty);

            cashDenominationQty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(method.equals("SAFEKEEPING")){
                        if(!cashDenominationQty.getText().toString().isEmpty()){
                            safekeepingDenominations.get(getAbsoluteAdapterPosition()).setQty(Integer.parseInt(cashDenominationQty.getText().toString()));
                            safekeepingDenominations.get(getAbsoluteAdapterPosition()).setTotal(compute.gross(Double.parseDouble(cashDenominationQty.getText().toString()), safekeepingDenominations.get(getAbsoluteAdapterPosition()).getAmount()));
                            cashDenominationListener.onChangeTotalSafeKeeping(recomputeTotalSafeKeeping(), method);
                        }
                        else{
                            safekeepingDenominations.get(getAbsoluteAdapterPosition()).setQty(0);
                            safekeepingDenominations.get(getAbsoluteAdapterPosition()).setTotal(0);
                            cashDenominationListener.onChangeTotalSafeKeeping(recomputeTotalSafeKeeping(), method);
                        }
                    }
                    else if(method.equals("CASH FUND")){
                        if(!cashDenominationQty.getText().toString().isEmpty()){
                            cashFundDenominations.get(getAbsoluteAdapterPosition()).setQty(Integer.parseInt(cashDenominationQty.getText().toString()));
                            cashFundDenominations.get(getAbsoluteAdapterPosition()).setTotal(compute.gross(Double.parseDouble(cashDenominationQty.getText().toString()), cashFundDenominations.get(getAbsoluteAdapterPosition()).getAmount()));
                            cashDenominationListener.onChangeTotalSafeKeeping(recomputeTotalSafeKeeping(), method);
                        }
                        else{
                            cashFundDenominations.get(getAbsoluteAdapterPosition()).setQty(0);
                            cashFundDenominations.get(getAbsoluteAdapterPosition()).setTotal(0);
                            cashDenominationListener.onChangeTotalSafeKeeping(recomputeTotalSafeKeeping(), method);
                        }
                    }
                    else if (method.equals("SPOT AUDIT")) {
                        if(!cashDenominationQty.getText().toString().isEmpty()){
                            spotAuditDenominations.get(getAbsoluteAdapterPosition()).setQty(Integer.parseInt(cashDenominationQty.getText().toString()));
                            spotAuditDenominations.get(getAbsoluteAdapterPosition()).setTotal(compute.gross(Double.parseDouble(cashDenominationQty.getText().toString()), spotAuditDenominations.get(getAbsoluteAdapterPosition()).getAmount()));
                            cashDenominationListener.onChangeTotalSafeKeeping(recomputeTotalSafeKeeping(), method);
                        }
                        else{
                            spotAuditDenominations.get(getAbsoluteAdapterPosition()).setQty(0);
                            spotAuditDenominations.get(getAbsoluteAdapterPosition()).setTotal(0);
                            cashDenominationListener.onChangeTotalSafeKeeping(recomputeTotalSafeKeeping(), method);
                        }
                    }
                }
            });

        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if(recyclerView.getContext() instanceof CashDenominationAdapter.CashDenominationListener){
            cashDenominationListener = (CashDenominationAdapter.CashDenominationListener) recyclerView.getContext();
        }
        else{
            throw new RuntimeException(recyclerView.toString() + "must implement CashDenominationListener.");
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        safekeepingDenominations.clear();
        cashDenominationListener = null;
    }

}
