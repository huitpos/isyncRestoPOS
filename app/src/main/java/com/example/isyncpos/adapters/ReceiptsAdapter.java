package com.example.isyncpos.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.util.ObjectsCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.authentication.UserAuthentication;
import com.example.isyncpos.common.AuditTrail;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.common.Printer;
import com.example.isyncpos.common.Writer;
import com.example.isyncpos.dialog.AuthenticateDialog;
import com.example.isyncpos.dialog.VoidRemarksDialog;
import com.example.isyncpos.entity.DiscountDetails;
import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.entity.Discounts;
import com.example.isyncpos.entity.MachineDetails;
import com.example.isyncpos.entity.OfficialReceiptInformation;
import com.example.isyncpos.entity.Orders;
import com.example.isyncpos.entity.PaymentOtherInformations;
import com.example.isyncpos.entity.Payments;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.viewmodel.DiscountDetailsViewModel;
import com.example.isyncpos.viewmodel.DiscountOtherInformationsViewModel;
import com.example.isyncpos.viewmodel.DiscountsViewModel;
import com.example.isyncpos.viewmodel.MachineDetailsViewModel;
import com.example.isyncpos.viewmodel.OfficialReceiptInformationViewModel;
import com.example.isyncpos.viewmodel.OrdersViewModel;
import com.example.isyncpos.viewmodel.POSApplicationViewModel;
import com.example.isyncpos.viewmodel.PaymentOtherInformationsViewModel;
import com.example.isyncpos.viewmodel.PaymentsViewModel;
import com.example.isyncpos.viewmodel.PermissionsViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupDevicesViewModel;
import com.example.isyncpos.viewmodel.RolesViewModel;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.example.isyncpos.viewmodel.UsersViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReceiptsAdapter extends ListAdapter<Transactions, ReceiptsAdapter.ViewHolder> {

    private Activity activity;
    private TransactionsViewModel transactionsViewModel;
    private OrdersViewModel ordersViewModel;
    private PaymentsViewModel paymentsViewModel;
    private PaymentOtherInformationsViewModel paymentOtherInformationsViewModel;
    private PrinterSetupDevicesViewModel printerSetupDevicesViewModel;
    private DiscountsViewModel discountsViewModel;
    private DiscountOtherInformationsViewModel discountOtherInformationsViewModel;
    private DiscountDetailsViewModel discountDetailsViewModel;
    private OfficialReceiptInformationViewModel officialReceiptInformationViewModel;
    private MachineDetailsViewModel machineDetailsViewModel;
    private UsersViewModel usersViewModel;
    private RolesViewModel rolesViewModel;
    private PermissionsViewModel permissionsViewModel;
    private POSProcess posProcess;
    private Dates date;
    private Generate generate;
    private String viewType;

    public ReceiptsAdapter(Activity activity, String viewType){
        super(DIFF_CALLBACK);
        this.activity = activity;
        this.viewType = viewType;
    }

    private static final DiffUtil.ItemCallback<Transactions> DIFF_CALLBACK = new DiffUtil.ItemCallback<Transactions>() {
        @Override
        public boolean areItemsTheSame(Transactions oldItem, Transactions newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Transactions oldItem, Transactions newItem) {
            return ObjectsCompat.equals(oldItem, newItem);
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_receipt, parent, false);
        date = new Dates();
        generate = new Generate();
        posProcess = POSApplication.getInstance().getPosProcess();
        initViewModel();
        return new ReceiptsAdapter.ViewHolder(view);
    }

    private void initViewModel(){
        transactionsViewModel = POSApplication.getInstance().getTransactionsViewModel();
        ordersViewModel = POSApplication.getInstance().getOrdersViewModel();
        printerSetupDevicesViewModel = POSApplication.getInstance().getPrinterSetupDevicesViewModel();
        paymentsViewModel = POSApplication.getInstance().getPaymentsViewModel();
        discountsViewModel = POSApplication.getInstance().getDiscountsViewModel();
        machineDetailsViewModel = POSApplication.getInstance().getMachineDetailsViewModel();
        usersViewModel = POSApplication.getInstance().getUsersViewModel();
        rolesViewModel = POSApplication.getInstance().getRolesViewModel();
        permissionsViewModel = POSApplication.getInstance().getPermissionsViewModel();
        paymentOtherInformationsViewModel = POSApplication.getInstance().getPaymentOtherInformationsViewModel();
        discountOtherInformationsViewModel = POSApplication.getInstance().getDiscountOtherInformationsViewModel();
        discountDetailsViewModel = POSApplication.getInstance().getDiscountDetailsViewModel();
        officialReceiptInformationViewModel = POSApplication.getInstance().getOfficialReceiptInformationViewModel();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Transactions transactionItemList = getItem(position);
        holder.txtItemReceiptControlNumber.setText(transactionItemList.getControlNumber());
        holder.txtItemReceiptNumber.setText(transactionItemList.getReceiptNumber());
        holder.txtItemReceiptDate.setText(transactionItemList.getCompletedAt());
        holder.txtItemReceiptSubTotal.setText(generate.toTwoDecimalWithComma(transactionItemList.getGrossSales()));
        holder.txtItemReceiptDiscount.setText(generate.toTwoDecimalWithComma((transactionItemList.getDiscountAmount())));
        holder.txtItemReceiptService.setText(generate.toTwoDecimalWithComma(transactionItemList.getServiceCharge()));
        holder.txtItemReceiptTotal.setText(generate.toTwoDecimalWithComma(transactionItemList.getNetSales()));
        if(viewType.equals("RECEIPT")){
            if(transactionItemList.getIsVoid() == 1){
                holder.btnViewReceiptVoid.setEnabled(false);
                holder.btnViewReceiptVoid.setBackgroundColor(activity.getResources().getColor(R.color.lightGray, null));
                holder.txtItemReceiptStatus.setText("VOID");
                holder.txtItemReceiptStatus.setVisibility(View.VISIBLE);
            }
            else{
                holder.btnViewReceiptVoid.setEnabled(true);
                holder.btnViewReceiptVoid.setBackgroundColor(activity.getResources().getColor(R.color.acolor, null));
                holder.txtItemReceiptStatus.setText("");
                holder.txtItemReceiptStatus.setVisibility(View.GONE);
            }
        }
        else{
            if(transactionItemList.getIsVoid() == 1){
                holder.txtItemReceiptStatus.setText("VOID");
                holder.txtItemReceiptStatus.setVisibility(View.VISIBLE);
            }
            else{
                holder.txtItemReceiptStatus.setText("");
                holder.txtItemReceiptStatus.setVisibility(View.GONE);
            }
            holder.btnViewReceiptVoid.setEnabled(false);
            holder.btnViewReceiptVoid.setVisibility(View.GONE);
        }
        try {
            holder.receiptsOrderAdapter.submitList(ordersViewModel.fetchTransactionOrdersWithVoid(transactionItemList.getId()));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtItemReceiptControlNumber, txtItemReceiptNumber, txtItemReceiptDate, txtItemReceiptStatus, txtItemReceiptSubTotal, txtItemReceiptDiscount, txtItemReceiptService, txtItemReceiptTotal;
        private MaterialButton btnViewReceiptReprint, btnViewReceiptVoid;
        private RecyclerView recyclerReceiptItem;
        private ReceiptsOrderAdapter receiptsOrderAdapter;

        public ViewHolder(View itemView) {
            super(itemView);
            txtItemReceiptControlNumber = itemView.findViewById(R.id.txtItemReceiptControlNumber);
            txtItemReceiptNumber = itemView.findViewById(R.id.txtItemReceiptNumber);
            txtItemReceiptDate = itemView.findViewById(R.id.txtItemReceiptDate);
            txtItemReceiptStatus = itemView.findViewById(R.id.txtItemReceiptStatus);
            btnViewReceiptReprint = itemView.findViewById(R.id.btnViewReceiptReprint);
            btnViewReceiptVoid = itemView.findViewById(R.id.btnViewReceiptVoid);
            recyclerReceiptItem = itemView.findViewById(R.id.recyclerReceiptItem);
            txtItemReceiptSubTotal = itemView.findViewById(R.id.txtItemReceiptSubTotal);
            txtItemReceiptDiscount = itemView.findViewById(R.id.txtItemReceiptDiscount);
            txtItemReceiptService = itemView.findViewById(R.id.txtItemReceiptService);
            txtItemReceiptTotal = itemView.findViewById(R.id.txtItemReceiptTotal);
            receiptsOrderAdapter = new ReceiptsOrderAdapter();

            recyclerReceiptItem.setHasFixedSize(true);
            recyclerReceiptItem.setLayoutManager(new LinearLayoutManager(activity));
            recyclerReceiptItem.setAdapter(receiptsOrderAdapter);

            btnViewReceiptReprint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_VIEW_RECEIPT_REPRINT)){
                        processReprintReceipt(getItem(getAbsoluteAdapterPosition()).getId());
                    }
                    else{
                        AuthenticateDialog authenticateDialog = new AuthenticateDialog(activity) {
                            @Override
                            public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                if(success){
                                    processReprintReceipt(getItem(getAbsoluteAdapterPosition()).getId());
                                }
                                else {
                                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                                }
                            }
                        };
                        authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_VIEW_RECEIPT_REPRINT);
                        authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                        authenticateDialog.setCancelable(false);
                        authenticateDialog.show();
                    }
                }
            });

            btnViewReceiptVoid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setTitle("Confirmation");
                        builder.setMessage("Are you sure you want to void this receipt number " + getItem(getAbsoluteAdapterPosition()).getReceiptNumber() + "?");
                        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_VIEW_RECEIPT_VOID)){
                                    processVoidReceipt(getItem(getAbsoluteAdapterPosition()).getId(), POSApplication.getInstance().getUserAuthentication());
                                }
                                else{
                                    AuthenticateDialog authenticateDialog = new AuthenticateDialog(activity) {
                                        @Override
                                        public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                            if(success){
                                                processVoidReceipt(getItem(getAbsoluteAdapterPosition()).getId(), authorizeUser);
                                            }
                                            else{
                                                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    };
                                    authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_VIEW_RECEIPT_VOID);
                                    authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                                    authenticateDialog.setCancelable(false);
                                    authenticateDialog.show();
                                }
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

    private void processReprintReceipt(int transactionId){
        try {
            Transactions transactions = transactionsViewModel.fetchTransaction(transactionId);
            List<Orders> orders = transactions.getIsVoid() == 0 ? ordersViewModel.fetchTransactionOrders(transactionId) : ordersViewModel.fetchTransactionOrdersWithVoid(transactionId);
            List<Payments> payments = paymentsViewModel.fetchTransactionPayments(transactionId);
            List<Discounts> discounts = discountsViewModel.fetchDiscountsByTransactionId(transactionId);
            Printer.getInstance().print(activity, printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_REPRINT), Printer.getInstance().rePrint(transactions, orders, payments, discounts));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void processVoidReceipt(int transactionId, UserAuthentication authorizeUser){
        VoidRemarksDialog voidRemarksDialog = new VoidRemarksDialog(activity) {
            @Override
            public void confirm(String remarks) {
                try {
                    LoadingDialog.getInstance().startLoadingDialog(activity, "Voiding transaction please wait...");
                    List<Payments> payments = paymentsViewModel.fetchTransactionPayments(transactionId);
                    List<PaymentOtherInformations> paymentOtherInformations = paymentOtherInformationsViewModel.fetchPaymentOtherInformationByTransactionId(transactionId);
                    List<Orders> orders = ordersViewModel.fetchTransactionOrders(transactionId);
                    List<Discounts> discounts = discountsViewModel.fetchDiscountsByTransactionId(transactionId);
                    List<DiscountOtherInformations> discountOtherInformations = discountOtherInformationsViewModel.fetchDiscountOtherInformationByTransactionId(transactionId);
                    List<DiscountDetails> discountDetails = discountDetailsViewModel.fetchDiscountDetailsByTransactionId(transactionId);
                    OfficialReceiptInformation officialReceiptInformation = officialReceiptInformationViewModel.fetchByTransactionId(transactionId);
                    Transactions transactions = transactionsViewModel.fetchTransaction(transactionId);
                    MachineDetails machineDetails = machineDetailsViewModel.fetchMachineDetails();
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int totalVoidQty = 0;
                                int voidCounter = machineDetails.getVoidCounter() + 1;
                                machineDetails.setVoidCounter(voidCounter);
                                machineDetails.setIsSentToServer(0);
                                transactions.setIsVoid(1);
                                transactions.setVoidBy(authorizeUser.getName());
                                transactions.setVoidById(authorizeUser.getId()); //This id of the approve user
                                transactions.setVoidAt(date.now());
                                transactions.setVoidRemarks(remarks);
                                transactions.setVoidCounter(voidCounter);
                                transactions.setIsSentToServer(0);
                                for(Orders item : orders){
                                    totalVoidQty += item.getQty();
                                    item.setIsSentToServer(0);
                                    item.setIsVoid(1);
                                    item.setVoidBy(authorizeUser.getName());
                                    item.setVoidAt(date.now());
                                    ordersViewModel.update(item);
                                }
                                for(Payments item : payments){
                                    item.setIsSentToServer(0);
                                    item.setIsVoid(1);
                                    item.setVoidById(authorizeUser.getId());
                                    item.setVoidBy(authorizeUser.getName());
                                    item.setVoidAt(date.now());
                                    paymentsViewModel.update(item);
                                }
                                for(PaymentOtherInformations item : paymentOtherInformations){
                                    item.setIsSentToServer(0);
                                    item.setIsVoid(1);
                                    paymentOtherInformationsViewModel.update(item);
                                }
                                for(Discounts item : discounts){
                                    item.setIsSentToServer(0);
                                    item.setIsVoid(1);
                                    item.setVoidById(authorizeUser.getId());
                                    item.setVoidBy(authorizeUser.getName());
                                    item.setVoidAt(date.now());
                                    discountsViewModel.update(item);
                                }
                                for(DiscountOtherInformations item : discountOtherInformations){
                                    item.setIsSentToServer(0);
                                    item.setIsVoid(1);
                                    discountOtherInformationsViewModel.update(item);
                                }
                                for(DiscountDetails item : discountDetails){
                                    item.setIsSentToServer(0);
                                    item.setIsVoid(1);
                                    item.setVoidById(authorizeUser.getId());
                                    item.setVoidBy(authorizeUser.getName());
                                    item.setVoidAt(date.now());
                                    discountDetailsViewModel.update(item);
                                }
                                if(officialReceiptInformation != null){
                                    officialReceiptInformation.setIsSentToServer(0);
                                    officialReceiptInformation.setIsVoid(1);
                                    officialReceiptInformation.setVoidBy(authorizeUser.getId());
                                    officialReceiptInformation.setVoidName(authorizeUser.getName());
                                    officialReceiptInformation.setVoidAt(date.now());
                                    officialReceiptInformationViewModel.update(officialReceiptInformation);
                                }
                                transactions.setTotalVoidQty(totalVoidQty);
                                transactionsViewModel.update(transactions);
                                machineDetailsViewModel.update(machineDetails);
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
                                            try {
                                                AuditTrail.getInstance().save(
                                                        POSApplication.getInstance().getUserAuthentication().getId(),
                                                        POSApplication.getInstance().getUserAuthentication().getName(),
                                                        transactions.getId(),
                                                        "TRANSACTION POST VOID",
                                                        "Post void transaction with sales invoice of " + transactions.getReceiptNumber() + " with a control number of " + transactions.getControlNumber() + " cashier is " + POSApplication.getInstance().getUserAuthentication().getName() + " authorize by " + authorizeUser.getName(),
                                                        authorizeUser.getId(),
                                                        authorizeUser.getName(),
                                                        0,
                                                        0
                                                );
                                                Writer.getInstance().writeTransaction(activity, Printer.getInstance().voidReceipt(transactions, orders, payments, discounts));
                                                Printer.getInstance().print(activity, printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_POST_VOID), Printer.getInstance().voidReceipt(transactions, orders, payments, discounts));
                                                LoadingDialog.getInstance().closeLoadingDialog();
                                            } catch (ExecutionException e) {
                                                throw new RuntimeException(e);
                                            } catch (InterruptedException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    }, BuildConfig.APP_DEFAULT_LOADING_TIME);
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
        };
        voidRemarksDialog.setCancelable(false);
        voidRemarksDialog.show();
    }

}
