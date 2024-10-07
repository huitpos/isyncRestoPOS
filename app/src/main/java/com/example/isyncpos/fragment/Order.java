package com.example.isyncpos.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.adapters.OrderItemsAdapter;
import com.example.isyncpos.authentication.UserAuthentication;
import com.example.isyncpos.common.AuditTrail;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.common.Printer;
import com.example.isyncpos.dialog.AuthenticateDialog;
import com.example.isyncpos.dialog.CustomerNameDialog;
import com.example.isyncpos.dialog.DiscountDialog;
import com.example.isyncpos.dialog.PaymentDialog;
import com.example.isyncpos.entity.DiscountDetails;
import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.entity.Discounts;
import com.example.isyncpos.entity.Orders;
import com.example.isyncpos.entity.Payments;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.preferences.Cache;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.socket.payload.TakeOrderCompletePayload;
import com.example.isyncpos.viewmodel.AuditTrailsViewModel;
import com.example.isyncpos.viewmodel.CategoriesViewModel;
import com.example.isyncpos.viewmodel.DepartmentsViewModel;
import com.example.isyncpos.viewmodel.DiscountDetailsViewModel;
import com.example.isyncpos.viewmodel.DiscountOtherInformationsViewModel;
import com.example.isyncpos.viewmodel.DiscountsViewModel;
import com.example.isyncpos.viewmodel.OrdersViewModel;
import com.example.isyncpos.viewmodel.POSApplicationViewModel;
import com.example.isyncpos.viewmodel.PaymentsViewModel;
import com.example.isyncpos.viewmodel.PermissionsViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupDevicesViewModel;
import com.example.isyncpos.viewmodel.RolesViewModel;
import com.example.isyncpos.viewmodel.SubCategoriesViewModel;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.example.isyncpos.viewmodel.UsersViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.socket.client.Socket;

public class Order extends Fragment {

    private TextView labelSubTotal, labelDiscount, labelService, labelTotal, labelControlNumber;
    private MaterialButton btnOrderPrintReceipt, btnOrderPayment, btnOrderBackout, btnOrderingVoid, btnOrderingPauseTransaction, btnOrderingClearTransaction, btnOrderDiscount, btnOrderDrawer;
    private RecyclerView recyclerViewOrders;
    private OrderItemsAdapter orderItemsAdapter;
    private POSApplicationViewModel posApplicationViewModel;
    private TransactionsViewModel transactionsViewModel;
    private OrdersViewModel ordersViewModel;
    private AuditTrailsViewModel auditTrailsViewModel;
    private PrinterSetupDevicesViewModel printerSetupDevicesViewModel;
    private DepartmentsViewModel departmentsViewModel;
    private CategoriesViewModel categoriesViewModel;
    private SubCategoriesViewModel subCategoriesViewModel;
    private PaymentsViewModel paymentsViewModel;
    private DiscountsViewModel discountsViewModel;
    private DiscountDetailsViewModel discountDetailsViewModel;
    private DiscountOtherInformationsViewModel discountOtherInformationsViewModel;
    private UsersViewModel usersViewModel;
    private RolesViewModel rolesViewModel;
    private PermissionsViewModel permissionsViewModel;
    private Cache cache;
    private Generate generate;
    private Dates date;
    private POSProcess posProcess;
    private Socket socket;
    private Gson gson;

    public static Order newOrder(){
        return new Order();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getDeviceSize().equals("small")){
            return inflater.inflate(R.layout.fragment_order_small, container, false);
        }
        else{
            return inflater.inflate(R.layout.fragment_order, container, false);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        //Initialize
        initialize();

        //Set
        setOrderButtons();

        posApplicationViewModel.getCurrentTransactionId().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer transactionId) {
                try {
                    transactionsViewModel.setCurrentTransaction(transactionsViewModel.fetchTransaction(Integer.parseInt(transactionId.toString())));
                    ordersViewModel.setCurrentTransactionOrders(ordersViewModel.fetchTransactionOrders(Integer.parseInt(transactionId.toString())));
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        transactionsViewModel.getCurrentTransaction().observe(getActivity(), new Observer<Transactions>() {
            @Override
            public void onChanged(Transactions transactions) {
                if(transactions != null){
                    labelControlNumber.setText("#: " + transactions.getControlNumber());
                    labelSubTotal.setText(generate.toTwoDecimalWithComma(transactions.getGrossSales()));
                    labelDiscount.setText(generate.toTwoDecimalWithComma(transactions.getDiscountAmount()));
                    labelService.setText("0.00");
                    labelTotal.setText(generate.toTwoDecimalWithComma(transactions.getNetSales()));
                }
                else{
                    labelControlNumber.setText("#: ");
                    labelSubTotal.setText("0.00");
                    labelDiscount.setText("0.00");
                    labelService.setText("0.00");
                    labelTotal.setText("0.00");
                }
            }
        });

        ordersViewModel.getCurrentTransactionOrders().observe(getActivity(), new Observer<List<Orders>>() {
            @Override
            public void onChanged(List<Orders> orders) {
                orderItemsAdapter.submitList(orders);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerViewOrders.scrollToPosition(orderItemsAdapter.getUpdatedItemPosition());
                        orderItemsAdapter.clearSelectedListOrders();
                        orderItemsAdapter.notifyDataSetChanged();
                    }
                }, 200L);
            }
        });

        btnOrderPrintReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(POSApplication.getInstance().checkCurrentTransaction()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to print the statement of account of this transaction?");
                    builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_SOA)){
                                processPrintSOA();
                            }
                            else{
                                AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                                    @Override
                                    public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                        if(success){
                                            processPrintSOA();
                                        }
                                        else{
                                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                };
                                authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_SOA);
                                authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                                authenticateDialog.setCancelable(false);
                                authenticateDialog.show();
                            }
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{
                    Toast.makeText(getContext(), "There is no current transaction to print the statement of account.", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnOrderPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnOrderPayment.getText().equals("Payment")){ //Cashier Action
                    if(POSApplication.getInstance().checkCurrentTransaction()){
                        try {
                            List<Orders> orders = ordersViewModel.fetchTransactionOrders(POSApplication.getInstance().getCurrentTransaction());
                            if(orders.size() != 0){
                                if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_PAYMENTS)){
                                    PaymentDialog paymentDialog = PaymentDialog.newPaymentDialog();
                                    paymentDialog.setCancelable(false);
                                    paymentDialog.show(getActivity().getSupportFragmentManager(), "Payment Dialog");
                                }
                                else {
                                    AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                                        @Override
                                        public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                            if(success){
                                                PaymentDialog paymentDialog = PaymentDialog.newPaymentDialog();
                                                paymentDialog.setCancelable(false);
                                                paymentDialog.show(getActivity().getSupportFragmentManager(), "Payment Dialog");
                                            }
                                            else{
                                                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    };
                                    authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_PAYMENTS);
                                    authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                                    authenticateDialog.setCancelable(false);
                                    authenticateDialog.show();
                                }
                            }
                            else{
                                Toast.makeText(getContext(), "There is no item to payment.", Toast.LENGTH_LONG).show();
                            }
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "There is no current transaction to payment.", Toast.LENGTH_LONG).show();
                    }
                }
                else{ // Take Order Action
                    if(POSApplication.getInstance().checkCurrentTransaction()){
                        try {
                            Transactions transactions = transactionsViewModel.fetchTransaction(POSApplication.getInstance().getCurrentTransaction());
                            List<Orders> orders = ordersViewModel.fetchTransactionOrders(POSApplication.getInstance().getCurrentTransaction());
                            List<Discounts> discounts = discountsViewModel.fetchDiscountsByTransactionId(POSApplication.getInstance().getCurrentTransaction());
                            if(orders.size() != 0){
                                if(transactions.getCustomerName().equals("")){
                                    CustomerNameDialog customerNameDialog = new CustomerNameDialog(getActivity(), transactions) {
                                        @Override
                                        public void process(boolean success, Transactions transactions) {
                                            LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Completing the order please wait.");
                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        transactions.setTakeOrderId(POSApplication.getInstance().getMachineDetails().getCoreId());
                                                        transactions.setTakeOrderName(POSApplication.getInstance().getUserAuthentication().getName());
                                                        posProcess.SyncTakeOrderTransactionToServer(transactions);
                                                        orders.forEach(item -> {
                                                            posProcess.SyncTakeOrderOrdersToServer(item);
                                                        });
                                                        discounts.forEach(item -> {
                                                            try {
                                                                List<DiscountDetails> discountDetails = discountDetailsViewModel.fetchDiscountDetailsByDiscountId(item.getId());
                                                                List<DiscountOtherInformations> discountOtherInformation = discountOtherInformationsViewModel.fetchByTransactionDiscountId(item.getId());
                                                                discountDetails.forEach(itemDetails -> {
                                                                    posProcess.SyncTakeOrderDiscountDetailsToServer(itemDetails);
                                                                });
                                                                discountOtherInformation.forEach(itemOtherInformation -> {
                                                                    posProcess.SyncTakeOrderDiscountOtherInformationToServer(itemOtherInformation);
                                                                });
                                                                posProcess.SyncTakeOrderDiscountsToServer(item);
                                                            } catch (ExecutionException e) {
                                                                throw new RuntimeException(e);
                                                            } catch (InterruptedException e) {
                                                                throw new RuntimeException(e);
                                                            }
                                                        });
                                                        transactions.setIsComplete(1);
                                                        transactionsViewModel.update(transactions);
                                                        POSApplication.getInstance().setCurrentTransaction(0);
                                                        posApplicationViewModel.setCurrentTransaction(0);
                                                        cache.clearString("currentTransaction");
                                                        Printer.getInstance().print(getActivity(), printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_TAKE_ORDER_RECEIPT), Printer.getInstance().takeOrderReciept(transactions));
                                                        LoadingDialog.getInstance().closeLoadingDialog();
                                                        //Trigger Emit For Socket
                                                        socket.emit(POSApplication.getInstance().getCompany().getPosType() + "-complete-takeorder", gson.toJson(new TakeOrderCompletePayload(POSApplication.getInstance().getBranch().getCoreId())));
                                                    } catch (ExecutionException e) {
                                                        throw new RuntimeException(e);
                                                    } catch (InterruptedException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                }
                                            }, 1000);
                                        }
                                    };
                                    customerNameDialog.setCancelable(false);
                                    customerNameDialog.show();
                                }
                                else {
                                    LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Completing the order please wait.");
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                transactions.setTakeOrderId(POSApplication.getInstance().getMachineDetails().getCoreId());
                                                transactions.setTakeOrderName(POSApplication.getInstance().getUserAuthentication().getName());
                                                posProcess.SyncTakeOrderTransactionToServer(transactions);
                                                orders.forEach(item -> {
                                                    posProcess.SyncTakeOrderOrdersToServer(item);
                                                });
                                                transactions.setIsComplete(1);
                                                transactionsViewModel.update(transactions);
                                                POSApplication.getInstance().setCurrentTransaction(0);
                                                posApplicationViewModel.setCurrentTransaction(0);
                                                cache.clearString("currentTransaction");
                                                Printer.getInstance().print(getActivity(), printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_TAKE_ORDER_RECEIPT), Printer.getInstance().takeOrderReciept(transactions));
                                                LoadingDialog.getInstance().closeLoadingDialog();
                                                //Trigger Emit For Socket
                                                socket.emit(POSApplication.getInstance().getCompany().getPosType() + "-complete-takeorder", gson.toJson(new TakeOrderCompletePayload(POSApplication.getInstance().getBranch().getCoreId())));
                                            } catch (ExecutionException e) {
                                                throw new RuntimeException(e);
                                            } catch (InterruptedException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    }, 1000);
                                }
                            }
                            else{
                                Toast.makeText(getContext(), "There is no item to complete this transaction.", Toast.LENGTH_LONG).show();
                            }
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else{
                        Toast.makeText(getContext(), "There is no current transaction to complete.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnOrderingVoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(POSApplication.getInstance().checkCurrentTransaction()){
                    ArrayList<Orders> ordersList = orderItemsAdapter.getSelectedListOrders();
                    if (ordersList.size() != 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Confirmation");
                        builder.setMessage("Are you sure you want to void the selected items?");
                        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Validation if has a discount
                                if(!posProcess.checkSelectedTransactionOrdersHasDiscount(ordersList)){
                                    if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_ITEM_VOID)){
                                        processVoidItem(ordersList, POSApplication.getInstance().getUserAuthentication());
                                    }
                                    else{
                                        AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                                            @Override
                                            public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                                if(success){
                                                    processVoidItem(ordersList, authorizeUser);
                                                }
                                                else{
                                                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        };
                                        authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_ITEM_VOID);
                                        authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                                        authenticateDialog.setCancelable(false);
                                        authenticateDialog.show();
                                    }
                                }
                                else{
                                    Toast.makeText(getContext(), "Selected item has a discount please remove the discount before voiding the item.", Toast.LENGTH_LONG).show();
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
                    else{
                        Toast.makeText(getContext(), "There is no selected item for you to void/cancel.", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getContext(), "There is no transaction created.", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnOrderBackout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(POSApplication.getInstance().checkCurrentTransaction()){
                    try {
                        List<Payments> payments = paymentsViewModel.fetchTransactionPayments(POSApplication.getInstance().getCurrentTransaction());
                        List<Discounts> discounts = discountsViewModel.fetchDiscountsByTransactionId(POSApplication.getInstance().getCurrentTransaction());
                        if(payments.size() == 0 && discounts.size() == 0){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Confirmation");
                            builder.setMessage("Are you sure you want to backout this transaction?");
                            builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //This will check if the user has access
                                    if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_BACKOUT)){
                                        processBackout();
                                    }
                                    else{
                                        AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                                            @Override
                                            public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                                if(success){
                                                    processBackout();
                                                }
                                                else{
                                                    if(message != null){
                                                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }
                                        };
                                        authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_BACKOUT);
                                        authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                                        authenticateDialog.setCancelable(false);
                                        authenticateDialog.show();
                                    }
                                }
                            });
                            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        else{
                            Toast.makeText(getContext(), "Please remove all the payments and discounts before you can backout this transaction.", Toast.LENGTH_LONG).show();
                        }
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    Toast.makeText(getContext(), "There is no transaction to backout.", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnOrderingPauseTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(POSApplication.getInstance().checkCurrentTransaction()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to pause this transaction?");
                    builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_PAUSE_TRANSACTION)){
                                processPauseTransaction();
                            }
                            else{
                                AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                                    @Override
                                    public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                        if(success){
                                            processPauseTransaction();
                                        }
                                        else{
                                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                };
                                authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_PAUSE_TRANSACTION);
                                authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                                authenticateDialog.setCancelable(false);
                                authenticateDialog.show();
                            }
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{
                    Toast.makeText(getContext(), "There is no transaction to pause.", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnOrderingClearTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(POSApplication.getInstance().checkCurrentTransaction()){
                    if(posProcess.getCurrentTransactionOrders().size() != 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Confirmation");
                        builder.setMessage("Are you sure you want to clear this transaction?");
                        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(!posProcess.checkCurrentTransactionOrdersHasDiscount()){
                                    if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_CLEAR_TRANSACTION)){
                                        processClearTransaction();
                                    }
                                    else{
                                        AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                                            @Override
                                            public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                                if(success){
                                                    processClearTransaction();
                                                }
                                                else{
                                                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        };
                                        authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_CLEAR_TRANSACTION);
                                        authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                                        authenticateDialog.setCancelable(false);
                                        authenticateDialog.show();
                                    }
                                }
                                else{
                                    Toast.makeText(getContext(), "Some of the item has a discount please remove the it before clearing the transaction orders.", Toast.LENGTH_LONG).show();
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
                    else {
                        Toast.makeText(getContext(), "There is no orders to clear.", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getContext(), "There is no transaction to clear the orders.", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnOrderDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(POSApplication.getInstance().checkCurrentTransaction()){
                    ArrayList<Orders> ordersList = orderItemsAdapter.getSelectedListOrders();
                    if(ordersList.size() != 0){
                        if(!posProcess.checkSelectedTransactionOrdersHasReturn(ordersList)){
                            if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_DISCOUNTS)){
                                DiscountDialog discountDialog = DiscountDialog.newDiscountDialog();
                                discountDialog.setCancelable(false);
                                discountDialog.setArgs(ordersList, orderItemsAdapter, null);
                                discountDialog.show(getActivity().getSupportFragmentManager(), "Discount Dialog");
                            }
                            else{
                                AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                                    @Override
                                    public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                        if(success){
                                            DiscountDialog discountDialog = DiscountDialog.newDiscountDialog();
                                            discountDialog.setCancelable(false);
                                            discountDialog.setArgs(ordersList, orderItemsAdapter, authorizeUser);
                                            discountDialog.show(getActivity().getSupportFragmentManager(), "Discount Dialog");
                                        }
                                        else{
                                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                };
                                authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_DISCOUNTS);
                                authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                                authenticateDialog.setCancelable(false);
                                authenticateDialog.show();
                            }
                        }
                        else{
                            Toast.makeText(getContext(), "There is a return item selected please remove it to the selection.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        ArrayList<Orders> allOrdersList = orderItemsAdapter.selectAllListOrders();
                        if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_DISCOUNTS)){
                            DiscountDialog discountDialog = DiscountDialog.newDiscountDialog();
                            discountDialog.setCancelable(false);
                            discountDialog.setArgs(allOrdersList, orderItemsAdapter, null);
                            discountDialog.show(getActivity().getSupportFragmentManager(), "Discount Dialog");
                        }
                        else{
                            AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                                @Override
                                public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                    if(success){
                                        DiscountDialog discountDialog = DiscountDialog.newDiscountDialog();
                                        discountDialog.setCancelable(false);
                                        discountDialog.setArgs(allOrdersList, orderItemsAdapter, authorizeUser);
                                        discountDialog.show(getActivity().getSupportFragmentManager(), "Discount Dialog");
                                    }
                                    else{
                                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                            authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_DISCOUNTS);
                            authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                            authenticateDialog.setCancelable(false);
                            authenticateDialog.show();
                        }
                    }
                }
                else {
                    Toast.makeText(getContext(), "There is no current transaction to discount.", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnOrderDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_OPEN_DRAWER)){
                    Printer.getInstance().openCashDrawer(getActivity(), null);
                }
                else{
                    AuthenticateDialog authenticateDialog = new AuthenticateDialog(getActivity()) {
                        @Override
                        public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                            if(success){
                                Printer.getInstance().openCashDrawer(getActivity(), authorizeUser);
                            }
                            else{
                                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                            }
                        }
                    };
                    authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_CLEAR_TRANSACTION);
                    authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
                    authenticateDialog.setCancelable(false);
                    authenticateDialog.show();
                }
            }
        });

    }


    private void initialize(){
        initViewModels();
        btnOrderingClearTransaction = getView().findViewById(R.id.btnOrderingClearTransaction);
        btnOrderingPauseTransaction = getView().findViewById(R.id.btnOrderingPauseTransaction);
        btnOrderingVoid = getView().findViewById(R.id.btnOrderingVoid);
        btnOrderBackout = getView().findViewById(R.id.btnOrderBackout);
        btnOrderPrintReceipt = getView().findViewById(R.id.btnOrderPrintReceipt);
        btnOrderPayment = getView().findViewById(R.id.btnOrderPayment);
        btnOrderDiscount = getView().findViewById(R.id.btnOrderDiscount);
        btnOrderDrawer = getView().findViewById(R.id.btnOrderDrawer);
        labelSubTotal = getView().findViewById(R.id.labelSubTotal);
        labelDiscount = getView().findViewById(R.id.labelDiscount);
        labelService = getView().findViewById(R.id.labelService);
        labelTotal = getView().findViewById(R.id.labelTotal);
        labelControlNumber = getView().findViewById(R.id.labelControlNumber);
        cache = new Cache(getActivity());
        generate = new Generate();
        date = new Dates();
        socket = POSApplication.getInstance().getSocket();
        gson = new Gson();
        initRecyclerView();
    }

    private void initViewModels(){
        posApplicationViewModel = POSApplication.getInstance().getPosApplicationViewModel();
        transactionsViewModel = POSApplication.getInstance().getTransactionsViewModel();
        ordersViewModel = POSApplication.getInstance().getOrdersViewModel();
        auditTrailsViewModel = POSApplication.getInstance().getAuditTrailsViewModel();
        printerSetupDevicesViewModel = POSApplication.getInstance().getPrinterSetupDevicesViewModel();
        departmentsViewModel = POSApplication.getInstance().getDepartmentsViewModel();
        categoriesViewModel = POSApplication.getInstance().getCategoriesViewModel();
        subCategoriesViewModel = POSApplication.getInstance().getSubCategoriesViewModel();
        posProcess = POSApplication.getInstance().getPosProcess();
        paymentsViewModel = POSApplication.getInstance().getPaymentsViewModel();
        discountsViewModel = POSApplication.getInstance().getDiscountsViewModel();
        usersViewModel = POSApplication.getInstance().getUsersViewModel();
        rolesViewModel = POSApplication.getInstance().getRolesViewModel();
        permissionsViewModel = POSApplication.getInstance().getPermissionsViewModel();
        discountDetailsViewModel = POSApplication.getInstance().getDiscountDetailsViewModel();
        discountOtherInformationsViewModel = POSApplication.getInstance().getDiscountOtherInformationsViewModel();
    }

    private void initRecyclerView(){
        recyclerViewOrders = getView().findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setHasFixedSize(true);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        orderItemsAdapter = new OrderItemsAdapter(getActivity());
        recyclerViewOrders.setAdapter(orderItemsAdapter);
    }

    private void processBackout(){
        LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Backing out please wait...");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                //Backout Transaction
                posProcess.backoutTransaction(POSApplication.getInstance().getCurrentTransaction());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Transactions transactions = transactionsViewModel.fetchTransaction(POSApplication.getInstance().getCurrentTransaction());
                                    List<Orders> orders = ordersViewModel.fetchTransactionOrdersBackOut(POSApplication.getInstance().getCurrentTransaction());
                                    //Save Audit Trail
                                    AuditTrail.getInstance().save(
                                        POSApplication.getInstance().getUserAuthentication().getId(),
                                        POSApplication.getInstance().getUserAuthentication().getName(),
                                        POSApplication.getInstance().getCurrentTransaction(),
                                        "TRANSACTION",
                                        "Transaction backout of control number " + transactions.getControlNumber(),
                                        0,
                                        "",
                                        0,
                                        0
                                    );
                                    Printer.getInstance().print(getActivity(), printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_BACKOUT), Printer.getInstance().backoutTransaction(transactions, orders));
                                    POSApplication.getInstance().setCurrentTransaction(0);
                                    posApplicationViewModel.setCurrentTransaction(0);
                                    cache.clearString("currentTransaction");
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
    }

    private void processVoidItem(ArrayList<Orders> ordersList, UserAuthentication authorizeUser){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Voiding please wait...");
                    }
                });

                for(Orders item: ordersList){
                    try {
                        Orders order = ordersViewModel.fetchTransactionOrderWithReturn(item.getTransactionId(), item.getProductId());
                        if(order != null){
                            order.setIsVoid(1);
                            order.setVoidBy(authorizeUser.getName());
                            order.setVoidAt(date.now());
                            order.setIsSentToServer(0);
                            ordersViewModel.update(order);
                        }
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                //Recompute after void
                posProcess.recomputeCurrentTransaction();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Save Audit Trail
                                AuditTrail.getInstance().save(
                                    POSApplication.getInstance().getUserAuthentication().getId(),
                                    POSApplication.getInstance().getUserAuthentication().getName(),
                                    transactionsViewModel.getCurrentTransaction().getValue().getId(),
                                    "ITEM VOID",
                                    voidDescription(ordersList, authorizeUser),
                                    authorizeUser.getId(),
                                    authorizeUser.getName(),
                                    0,
                                    0
                                );
                                transactionsViewModel.setCurrentTransaction(posProcess.getCurrentTransaction());
                                ordersViewModel.setCurrentTransactionOrders(posProcess.getCurrentTransactionOrders());
                                orderItemsAdapter.clearSelectedListOrders();
                                LoadingDialog.getInstance().closeLoadingDialog();
                            }
                        }, BuildConfig.APP_DEFAULT_LOADING_TIME);
                    }
                });

            }
        });
    }

    private void processPrintSOA(){
        try {
            Transactions transactions = transactionsViewModel.fetchTransaction(POSApplication.getInstance().getCurrentTransaction());
            List<Orders> orders = ordersViewModel.fetchTransactionOrders(POSApplication.getInstance().getCurrentTransaction());
            List<Discounts> discounts = discountsViewModel.fetchDiscountsByTransactionId(POSApplication.getInstance().getCurrentTransaction());
            Printer.getInstance().print(getActivity(), printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_STATEMENT_OF_ACCOUNT), Printer.getInstance().statementOfAccount(transactions, orders, discounts));
            //Save Audit Trail
            AuditTrail.getInstance().save(
                POSApplication.getInstance().getUserAuthentication().getId(),
                POSApplication.getInstance().getUserAuthentication().getName(),
                POSApplication.getInstance().getCurrentTransaction(),
                "TRANSACTION",
                "Transaction print statement of account of control number " + transactions.getControlNumber(),
                0,
                "",
                0,
                0
            );
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void processPauseTransaction(){
        try {
            Transactions transactions = transactionsViewModel.fetchTransaction(POSApplication.getInstance().getCurrentTransaction());
            if(transactions.getCustomerName() != null){
                if(transactions.getCustomerName().equals("")){
                    CustomerNameDialog customerNameDialog = new CustomerNameDialog(getActivity(), transactions) {
                        @Override
                        public void process(boolean success, Transactions transactions) {
                            if(success){
                                //Save Audit Trail
                                AuditTrail.getInstance().save(
                                    POSApplication.getInstance().getUserAuthentication().getId(),
                                    POSApplication.getInstance().getUserAuthentication().getName(),
                                    POSApplication.getInstance().getCurrentTransaction(),
                                    "TRANSACTION",
                                    "Transaction pause of control number " + transactions.getControlNumber(),
                                    0,
                                    "",
                                    0,
                                    0
                                );
                                if(transactions.getIsResumePrinted() == 0){
                                    try {
                                        Printer.getInstance().print(getActivity(), printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_RESUME_TRANSACTION), Printer.getInstance().resumeTransaction(transactions));
                                        transactions.setIsResumePrinted(1);
                                        transactionsViewModel.pauseTransaction(transactions.getId());
                                    } catch (ExecutionException e) {
                                        throw new RuntimeException(e);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                transactionsViewModel.resumeTransaction(transactions.getId(), transactions.getCustomerName());
                                transactionsViewModel.setCurrentTransaction(null);
                                ordersViewModel.setCurrentTransactionOrders(null);
                                POSApplication.getInstance().setCurrentTransaction(0);
                                POSApplication.getInstance().setLastUpdateProductId(0);
                                posApplicationViewModel.setCurrentTransaction(0);
                                cache.clearString("currentTransaction");
                                orderItemsAdapter.clearSelectedListOrders();
                            }
                        }
                    };
                    customerNameDialog.setCancelable(false);
                    customerNameDialog.show();
                }
                else{
                    //Save Audit Trail
                    AuditTrail.getInstance().save(
                        POSApplication.getInstance().getUserAuthentication().getId(),
                        POSApplication.getInstance().getUserAuthentication().getName(),
                        POSApplication.getInstance().getCurrentTransaction(),
                        "TRANSACTION",
                        "Transaction pause of control number " + transactions.getControlNumber(),
                        0,
                        "",
                        0,
                        0
                    );
                    transactionsViewModel.setCurrentTransaction(null);
                    ordersViewModel.setCurrentTransactionOrders(null);
                    POSApplication.getInstance().setCurrentTransaction(0);
                    POSApplication.getInstance().setLastUpdateProductId(0);
                    posApplicationViewModel.setCurrentTransaction(0);
                    cache.clearString("currentTransaction");
                    orderItemsAdapter.clearSelectedListOrders();
                }
            }
            else {
                CustomerNameDialog customerNameDialog = new CustomerNameDialog(getActivity(), transactions) {
                    @Override
                    public void process(boolean success, Transactions transactions) {
                        if(success){
                            //Save Audit Trail
                            AuditTrail.getInstance().save(
                                POSApplication.getInstance().getUserAuthentication().getId(),
                                POSApplication.getInstance().getUserAuthentication().getName(),
                                POSApplication.getInstance().getCurrentTransaction(),
                                "TRANSACTION",
                                "Transaction pause of control number " + transactions.getControlNumber(),
                                0,
                                "",
                                0,
                                0
                            );
                            if(transactions.getIsResumePrinted() == 0){
                                try {
                                    Printer.getInstance().print(getActivity(), printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_RESUME_TRANSACTION), Printer.getInstance().resumeTransaction(transactions));
                                    transactions.setIsResumePrinted(1);
                                    transactionsViewModel.pauseTransaction(transactions.getId());
                                } catch (ExecutionException e) {
                                    throw new RuntimeException(e);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            transactionsViewModel.resumeTransaction(transactions.getId(), transactions.getCustomerName());
                            transactionsViewModel.setCurrentTransaction(null);
                            ordersViewModel.setCurrentTransactionOrders(null);
                            POSApplication.getInstance().setCurrentTransaction(0);
                            POSApplication.getInstance().setLastUpdateProductId(0);
                            posApplicationViewModel.setCurrentTransaction(0);
                            cache.clearString("currentTransaction");
                            orderItemsAdapter.clearSelectedListOrders();
                        }
                    }
                };
                customerNameDialog.setCancelable(false);
                customerNameDialog.show();
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void processClearTransaction(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Clearing transaction please wait...");
                    }
                });

                //This will clear the transaction
                ordersViewModel.clearTransactionOrders(POSApplication.getInstance().getCurrentTransaction());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //This will recompute the transaction after clearing
                        posProcess.recomputeCurrentTransaction();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    //Save Audit Trail
                                    AuditTrail.getInstance().save(
                                        POSApplication.getInstance().getUserAuthentication().getId(),
                                        POSApplication.getInstance().getUserAuthentication().getName(),
                                        POSApplication.getInstance().getCurrentTransaction(),
                                        "TRANSACTION",
                                        "Transaction clear of control number " + transactionsViewModel.fetchTransaction(POSApplication.getInstance().getCurrentTransaction()).getControlNumber(),
                                        0,
                                        "",
                                        0,
                                        0
                                    );
                                    transactionsViewModel.setCurrentTransaction(posProcess.getCurrentTransaction());
                                    ordersViewModel.setCurrentTransactionOrders(posProcess.getCurrentTransactionOrders());
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
    }

    private String getDeviceSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Log.d("height", String.valueOf(height));
        Log.d("width", String.valueOf(width));
        if (height >= BuildConfig.APP_DEFAULT_SMALL_DEVICE_MIN && height <= BuildConfig.APP_DEFAULT_SMALL_DEVICE_MAX) {
            Log.d("getDeviceSize", "small");
            return "small";
        } else {
            Log.d("getDeviceSize", "large");
            return "large";
        }
    }

    private void setOrderButtons(){
        if(POSApplication.getInstance().getCompany().getPosType().equals("retail")) btnOrderPrintReceipt.setEnabled(false);
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            btnOrderPayment.setText("Payment");
            btnOrderDrawer.setVisibility(View.VISIBLE);
        }
        else{
            btnOrderPayment.setText("Complete");
            btnOrderDrawer.setVisibility(View.GONE);
        }
    }

    private String voidDescription(ArrayList<Orders> orders, UserAuthentication authorizeUser){
        int counter = 0;
        String description = "Transaction product void on control number " + transactionsViewModel.getCurrentTransaction().getValue().getControlNumber() + " cashier is " + POSApplication.getInstance().getUserAuthentication().getName() + " authorize by " + authorizeUser.getName() + " products of";
        for(Orders item : orders){
            if(counter == 0){
                description += " " + item.getName();
                counter ++;
            }
            else{
                description += ", " + item.getName();
            }
        }
        return description;
    }

    public void enablePaymentButtonForTakeOrder(){
        if(!POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            btnOrderPayment.setEnabled(true);
        }
    }

    public void disablePaymentButtonForTakeOrder(){
        if(!POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            btnOrderPayment.setEnabled(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        posApplicationViewModel.getCurrentTransactionId().removeObservers(this);
        transactionsViewModel.getCurrentTransaction().removeObservers(this);
        ordersViewModel.getCurrentTransactionOrders().removeObservers(this);
    }

}
