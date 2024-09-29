package com.example.isyncpos.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.util.ObjectsCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.entity.DiscountDetails;
import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.entity.Discounts;
import com.example.isyncpos.entity.Orders;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.viewmodel.DiscountDetailsViewModel;
import com.example.isyncpos.viewmodel.DiscountOtherInformationsViewModel;
import com.example.isyncpos.viewmodel.DiscountsViewModel;
import com.example.isyncpos.viewmodel.OrdersViewModel;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiscountItemsAdapter extends ListAdapter<Discounts, DiscountItemsAdapter.ViewHolder> {

    private TransactionsViewModel transactionsViewModel;
    private OrdersViewModel ordersViewModel;
    private DiscountsViewModel discountsViewModel;
    private DiscountDetailsViewModel discountDetailsViewModel;
    private DiscountOtherInformationsViewModel discountOtherInformationsViewModel;
    private Context context;
    private Activity activity;
    private POSProcess posProcess;
    private Dates date;

    public DiscountItemsAdapter(TransactionsViewModel transactionsViewModel, OrdersViewModel ordersViewModel, DiscountsViewModel discountsViewModel, DiscountDetailsViewModel discountDetailsViewModel, Context context, Activity activity, POSProcess posProcess, DiscountOtherInformationsViewModel discountOtherInformationsViewModel) {
        super(DIFF_CALLBACK);
        this.transactionsViewModel = transactionsViewModel;
        this.ordersViewModel = ordersViewModel;
        this.discountsViewModel = discountsViewModel;
        this.discountDetailsViewModel = discountDetailsViewModel;
        this.discountOtherInformationsViewModel = discountOtherInformationsViewModel;
        this.activity = activity;
        this.context = context;
        this.posProcess = posProcess;
        date = new Dates();
    }

    private static final DiffUtil.ItemCallback<Discounts> DIFF_CALLBACK = new DiffUtil.ItemCallback<Discounts>() {
        @Override
        public boolean areItemsTheSame(Discounts oldItem, Discounts newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Discounts oldItem, Discounts newItem) {
            return ObjectsCompat.equals(oldItem, newItem);
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_discount, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Discounts discountList = getItem(position);
        holder.txtItemDiscount.setText(discountList.getDiscountName() + " - " + discountList.getValue() + (discountList.getType().equals("percentage") ? "%" : ""));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtItemDiscount;
        private MaterialButton btnItemDiscountRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            txtItemDiscount = itemView.findViewById(R.id.txtItemDiscount);
            btnItemDiscountRemove = itemView.findViewById(R.id.btnItemDiscountRemove);

            btnItemDiscountRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to remove this discount?");
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                ExecutorService executorService = Executors.newSingleThreadExecutor();
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {

                                        activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                LoadingDialog.getInstance().startLoadingDialog(activity, "Removing discount please wait...");
                                            }
                                        });

                                        try {
                                            Discounts discounts = discountsViewModel.fetchDiscountByDiscountId(getItem(getAbsoluteAdapterPosition()).getId());
                                            List<DiscountDetails> discountDetailsList = discountDetailsViewModel.fetchDiscountDetailsByDiscountId(getItem(getAbsoluteAdapterPosition()).getId());
                                            List<DiscountOtherInformations> discountOtherInformations = discountOtherInformationsViewModel.fetchByTransactionDiscountId(getItem(getAbsoluteAdapterPosition()).getId());
                                            discounts.setIsSentToServer(0);
                                            discounts.setIsVoid(1);
                                            discounts.setVoidById(POSApplication.getInstance().getUserAuthentication().getId());
                                            discounts.setVoidBy(POSApplication.getInstance().getUserAuthentication().getName());
                                            discounts.setVoidAt(date.now());
                                            discountsViewModel.update(discounts);
                                            for(DiscountDetails item : discountDetailsList){
                                                item.setIsSentToServer(0);
                                                item.setIsVoid(1);
                                                item.setVoidBy(POSApplication.getInstance().getUserAuthentication().getName());
                                                item.setVoidById(POSApplication.getInstance().getUserAuthentication().getId());
                                                item.setVoidAt(date.now());
                                                discountDetailsViewModel.update(item);
                                            }
                                            for(DiscountOtherInformations item : discountOtherInformations){
                                                item.setIsVoid(1);
                                                item.setIsSentToServer(0);
                                                discountOtherInformationsViewModel.update(item);
                                            }
                                        } catch (ExecutionException e) {
                                            throw new RuntimeException(e);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }

                                        activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                final Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        LoadingDialog.getInstance().closeLoadingDialog();
                                                        posProcess.recomputeCurrentTransactionOrders();
                                                        transactionsViewModel.setCurrentTransaction(posProcess.recomputeCurrentTransaction());
                                                        ordersViewModel.setCurrentTransactionOrders(posProcess.getCurrentTransactionOrders());
                                                        discountsViewModel.setCurrentTransactionDiscounts(posProcess.getCurrentTransactionDiscounts());
                                                    }
                                                }, BuildConfig.APP_DEFAULT_LOADING_TIME);
                                            }
                                        });

                                    }
                                });
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

        }

    }

}
