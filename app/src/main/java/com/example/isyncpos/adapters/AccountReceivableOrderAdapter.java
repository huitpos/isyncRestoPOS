package com.example.isyncpos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.util.ObjectsCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.R;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.entity.Orders;
import com.example.isyncpos.objects.AccountReceivableOrdersObject;


public class AccountReceivableOrderAdapter extends ListAdapter<AccountReceivableOrdersObject, AccountReceivableOrderAdapter.ViewHolder> {

    private Generate generate;

    public AccountReceivableOrderAdapter(){
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<AccountReceivableOrdersObject> DIFF_CALLBACK = new DiffUtil.ItemCallback<AccountReceivableOrdersObject>() {
        @Override
        public boolean areItemsTheSame(AccountReceivableOrdersObject oldItem, AccountReceivableOrdersObject newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(AccountReceivableOrdersObject oldItem, AccountReceivableOrdersObject newItem) {
            return ObjectsCompat.equals(oldItem, newItem);
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_receipt_order, parent, false);
        generate = new Generate();
        return new AccountReceivableOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AccountReceivableOrdersObject orderItemList = getItem(position);
        if(orderItemList.getIsCompleted() != 0){
            holder.txtReceiptItemDescription.setVisibility(View.VISIBLE);
            holder.txtReceiptItemQty.setVisibility(View.VISIBLE);
            holder.txtReceiptItemAmount.setVisibility(View.VISIBLE);
            holder.txtReceiptItemDescription.setText(orderItemList.getName());
            holder.txtReceiptItemQty.setText(generate.toTwoDecimalWithComma(orderItemList.getQty()));
            holder.txtReceiptItemAmount.setText(generate.toTwoDecimalWithComma(orderItemList.getTotal()));
        }
        else{
            holder.txtReceiptItemDescription.setVisibility(View.GONE);
            holder.txtReceiptItemQty.setVisibility(View.GONE);
            holder.txtReceiptItemAmount.setVisibility(View.GONE);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtReceiptItemDescription, txtReceiptItemQty, txtReceiptItemAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            txtReceiptItemDescription = itemView.findViewById(R.id.txtReceiptItemDescription);
            txtReceiptItemQty = itemView.findViewById(R.id.txtReceiptItemQty);
            txtReceiptItemAmount = itemView.findViewById(R.id.txtReceiptItemAmount);
        }
    }

}
