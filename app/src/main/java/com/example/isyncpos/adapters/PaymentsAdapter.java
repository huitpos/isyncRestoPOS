package com.example.isyncpos.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.util.ObjectsCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.entity.Orders;
import com.example.isyncpos.entity.PaymentOtherInformations;
import com.example.isyncpos.entity.PaymentTypes;
import com.example.isyncpos.entity.Payments;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.viewmodel.PaymentOtherInformationsViewModel;
import com.example.isyncpos.viewmodel.PaymentTypesViewModel;
import com.example.isyncpos.viewmodel.PaymentsViewModel;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PaymentsAdapter extends ListAdapter<Payments, PaymentsAdapter.ViewHolder> {

    private Generate generate;
    private TransactionsViewModel transactionsViewModel;
    private PaymentsViewModel paymentsViewModel;
    private PaymentOtherInformationsViewModel paymentOtherInformationsViewModel;
    private PaymentTypesViewModel paymentTypesViewModel;
    private POSProcess posProcess;
    private Activity activity;
    private Dates date;

    public PaymentsAdapter(Activity activity){
        super(DIFF_CALLBACK);
        this.activity = activity;
    }

    private static final DiffUtil.ItemCallback<Payments> DIFF_CALLBACK = new DiffUtil.ItemCallback<Payments>() {
        @Override
        public boolean areItemsTheSame(Payments oldItem, Payments newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Payments oldItem, Payments newItem) {
            return ObjectsCompat.equals(oldItem, newItem);
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_payment, parent, false);
        initViewModel();
        initPOSProcess();
        generate = new Generate();
        date = new Dates();
        return new PaymentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Payments paymentItemList = getItem(position);
        String advancePayment = paymentItemList.getIsAdvancePayment() == 1 ? "(Advance Payment)" : "";
        holder.labelPaymentAmount.setText(paymentItemList.getPaymentTypeName() + " - " + generate.toTwoDecimalWithComma(paymentItemList.getAmount()) + " " + advancePayment);
    }

    private void initViewModel(){
        paymentsViewModel = POSApplication.getInstance().getPaymentsViewModel();
        transactionsViewModel = POSApplication.getInstance().getTransactionsViewModel();
        paymentOtherInformationsViewModel = POSApplication.getInstance().getPaymentOtherInformationsViewModel();
        paymentTypesViewModel = POSApplication.getInstance().getPaymentTypesViewModel();
    }

    private void initPOSProcess(){
        posProcess = POSApplication.getInstance().getPosProcess();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView labelPaymentAmount;

        private MaterialButton btnPaymentRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            labelPaymentAmount = itemView.findViewById(R.id.labelPaymentAmount);
            btnPaymentRemove = itemView.findViewById(R.id.btnPaymentRemove);

            btnPaymentRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to remove this payment information?");
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ExecutorService executorService = Executors.newSingleThreadExecutor();
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        Payments payments = paymentsViewModel.fetchPaymentById(getItem(getAbsoluteAdapterPosition()).getId());
                                        payments.setIsSentToServer(0);
                                        payments.setIsVoid(1);
                                        payments.setVoidById(POSApplication.getInstance().getUserAuthentication().getId());
                                        payments.setVoidBy(POSApplication.getInstance().getUserAuthentication().getName());
                                        payments.setVoidAt(date.now());
                                        //Void Here
                                        List<PaymentOtherInformations> paymentOtherInformations = paymentOtherInformationsViewModel.fetchByPaymentId(payments.getId());
                                        if(paymentOtherInformations != null){
                                            paymentOtherInformations.forEach(item -> {
                                                item.setIsVoid(1);
                                                item.setIsSentToServer(0);
                                                paymentOtherInformationsViewModel.update(item);
                                            });
                                        }
                                        paymentsViewModel.update(payments);
                                        //This will check if the payment type is AR and revert it.
                                        PaymentTypes paymentTypes = paymentTypesViewModel.fetchById(payments.getPaymentTypeId());
                                        if(paymentTypes.getIsAR() == 1){
                                            transactionsViewModel.updateTransactionAR(POSApplication.getInstance().getCurrentTransaction(), 0);
                                            transactionsViewModel.updateChargeAccount(POSApplication.getInstance().getCurrentTransaction(), 0, "");
                                        }
                                    } catch (ExecutionException e) {
                                        throw new RuntimeException(e);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            transactionsViewModel.setCurrentTransaction(posProcess.recomputeCurrentTransaction());
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
