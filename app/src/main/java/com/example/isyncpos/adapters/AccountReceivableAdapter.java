package com.example.isyncpos.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.util.ObjectsCompat;
import androidx.fragment.app.FragmentManager;
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
import com.example.isyncpos.dialog.ARPaymentDialog;
import com.example.isyncpos.dialog.AuthenticateDialog;
import com.example.isyncpos.dialog.ViewAccountReceivableDialog;
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
import com.example.isyncpos.entity.Users;
import com.example.isyncpos.objects.AccountReceivableTransactionsObject;
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

public class AccountReceivableAdapter extends ListAdapter<AccountReceivableTransactionsObject, AccountReceivableAdapter.ViewHolder> {

    private Activity activity;
    private FragmentManager fragmentManager;
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
    private POSApplicationViewModel posApplicationViewModel;
    private POSProcess posProcess;
    private Dates date;
    private Generate generate;
    private ViewAccountReceivableDialog viewAccountReceivableDialog;

    public AccountReceivableAdapter(Activity activity, FragmentManager fragmentManager, ViewAccountReceivableDialog viewAccountReceivableDialog){
        super(DIFF_CALLBACK);
        this.activity = activity;
        this.fragmentManager = fragmentManager;
        this.viewAccountReceivableDialog = viewAccountReceivableDialog;
    }

    private static final DiffUtil.ItemCallback<AccountReceivableTransactionsObject> DIFF_CALLBACK = new DiffUtil.ItemCallback<AccountReceivableTransactionsObject>() {
        @Override
        public boolean areItemsTheSame(AccountReceivableTransactionsObject oldItem, AccountReceivableTransactionsObject newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(AccountReceivableTransactionsObject oldItem, AccountReceivableTransactionsObject newItem) {
            return ObjectsCompat.equals(oldItem, newItem);
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_receipt_ar, parent, false);
        date = new Dates();
        generate = new Generate();
        posProcess = POSApplication.getInstance().getPosProcess();
        initViewModel();
        return new AccountReceivableAdapter.ViewHolder(view);
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
        posApplicationViewModel = POSApplication.getInstance().getPosApplicationViewModel();
        discountOtherInformationsViewModel = POSApplication.getInstance().getDiscountOtherInformationsViewModel();
        discountDetailsViewModel = POSApplication.getInstance().getDiscountDetailsViewModel();
        paymentOtherInformationsViewModel = POSApplication.getInstance().getPaymentOtherInformationsViewModel();
        officialReceiptInformationViewModel = POSApplication.getInstance().getOfficialReceiptInformationViewModel();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AccountReceivableTransactionsObject transactionItemList = getItem(position);
        holder.txtItemReceiptControlNumber.setText(transactionItemList.getControlNumber());
        holder.txtItemReceiptNumber.setText(transactionItemList.getReceiptNumber());
        holder.txtItemReceiptDate.setText(transactionItemList.getCompletedAt());
        holder.txtItemReceiptSubTotal.setText(generate.toTwoDecimalWithComma(transactionItemList.getGrossSales()));
        holder.txtItemReceiptDiscount.setText(generate.toTwoDecimalWithComma((transactionItemList.getDiscountAmount())));
        holder.txtItemReceiptService.setText(generate.toTwoDecimalWithComma(transactionItemList.getServiceCharge()));
        holder.txtItemReceiptTotal.setText(generate.toTwoDecimalWithComma(transactionItemList.getNetSales()));
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
        holder.accountReceivableOrderAdapter.submitList(transactionItemList.getOrders());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtItemReceiptControlNumber, txtItemReceiptNumber, txtItemReceiptDate, txtItemReceiptStatus, txtItemReceiptSubTotal, txtItemReceiptDiscount, txtItemReceiptService, txtItemReceiptTotal;
        private MaterialButton btnViewReceiptPayment, btnViewReceiptVoid;
        private RecyclerView recyclerReceiptItem;
        private AccountReceivableOrderAdapter accountReceivableOrderAdapter;

        public ViewHolder(View itemView) {
            super(itemView);
            txtItemReceiptControlNumber = itemView.findViewById(R.id.txtItemReceiptControlNumber);
            txtItemReceiptNumber = itemView.findViewById(R.id.txtItemReceiptNumber);
            txtItemReceiptDate = itemView.findViewById(R.id.txtItemReceiptDate);
            txtItemReceiptStatus = itemView.findViewById(R.id.txtItemReceiptStatus);
            btnViewReceiptPayment = itemView.findViewById(R.id.btnViewReceiptPayment);
            btnViewReceiptVoid = itemView.findViewById(R.id.btnViewReceiptVoid);
            recyclerReceiptItem = itemView.findViewById(R.id.recyclerReceiptItem);
            txtItemReceiptSubTotal = itemView.findViewById(R.id.txtItemReceiptSubTotal);
            txtItemReceiptDiscount = itemView.findViewById(R.id.txtItemReceiptDiscount);
            txtItemReceiptService = itemView.findViewById(R.id.txtItemReceiptService);
            txtItemReceiptTotal = itemView.findViewById(R.id.txtItemReceiptTotal);
            accountReceivableOrderAdapter = new AccountReceivableOrderAdapter();

            recyclerReceiptItem.setHasFixedSize(true);
            recyclerReceiptItem.setLayoutManager(new LinearLayoutManager(activity));
            recyclerReceiptItem.setAdapter(accountReceivableOrderAdapter);

            btnViewReceiptPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Recode This For AR Payment Permission
//                    if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_VIEW_RECEIPT_REPRINT)){
//                        processReprintReceipt(getItem(getAbsoluteAdapterPosition()).getId());
//                    }
//                    else{
//                        AuthenticateDialog authenticateDialog = new AuthenticateDialog(activity) {
//                            @Override
//                            public void authentication(boolean success, @Nullable String message) {
//                                if(success){
//                                    processReprintReceipt(getItem(getAbsoluteAdapterPosition()).getId());
//                                }
//                                else {
//                                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        };
//                        authenticateDialog.setPermissionId(BuildConfig.POS_ACCESS_VIEW_RECEIPT_REPRINT);
//                        authenticateDialog.setOfflineAuthenticateViewModels(usersViewModel, rolesViewModel, permissionsViewModel);
//                        authenticateDialog.setCancelable(false);
//                        authenticateDialog.show();
//                    }
                    processARPayment(getItem(getAbsoluteAdapterPosition()));
                }
            });

            btnViewReceiptVoid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setTitle("Confirmation");
                        builder.setMessage("Are you sure you want to void this account receivable transaction with control number of " + getItem(getAbsoluteAdapterPosition()).getControlNumber() + "?");
                        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(posProcess.checkForPermission(BuildConfig.POS_ACCESS_VIEW_RECEIPT_VOID)){
                                    processVoidReceipt(getItem(getAbsoluteAdapterPosition()), POSApplication.getInstance().getUserAuthentication());
                                }
                                else{
                                    AuthenticateDialog authenticateDialog = new AuthenticateDialog(activity) {
                                        @Override
                                        public void authentication(boolean success, @Nullable String message, UserAuthentication authorizeUser) {
                                            if(success){
                                                processVoidReceipt(getItem(getAbsoluteAdapterPosition()), authorizeUser);
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

    private void processARPayment(AccountReceivableTransactionsObject accountReceivableTransactionsObject){
        //This will check if the account receivable transaction is from this machine
        if(POSApplication.getInstance().getMachineDetails().getCoreId() == accountReceivableTransactionsObject.getMachineNumber()){
            processLocalARPayment(accountReceivableTransactionsObject);
        }
        else {
            //This will process the account receivable transaction that not associated on this machine
            Toast.makeText(activity, "This transaction is not made on this machine.", Toast.LENGTH_LONG).show();
        }
    }

    private void processLocalARPayment(AccountReceivableTransactionsObject accountReceivableTransactionsObject){
        try {
            Transactions transactions = transactionsViewModel.fetchTransaction(accountReceivableTransactionsObject.getId());
            if(transactions != null){
                POSApplication.getInstance().setCurrentARTransactionId(accountReceivableTransactionsObject.getId());
                posApplicationViewModel.setARCurrentTransaction(accountReceivableTransactionsObject.getId());
                ARPaymentDialog arPaymentDialog = ARPaymentDialog.newPaymentDialog();
                arPaymentDialog.setArgs(viewAccountReceivableDialog);
                arPaymentDialog.setCancelable(false);
                arPaymentDialog.show(fragmentManager, "AR Payment Dialog");
            }
            else
            {
                int transactionId = processSaveARTransaction(accountReceivableTransactionsObject);
                POSApplication.getInstance().setCurrentARTransactionId(transactionId);
                posApplicationViewModel.setARCurrentTransaction(transactionId);
                ARPaymentDialog arPaymentDialog = ARPaymentDialog.newPaymentDialog();
                arPaymentDialog.setArgs(viewAccountReceivableDialog);
                arPaymentDialog.setCancelable(false);
                arPaymentDialog.show(fragmentManager, "AR Payment Dialog");
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer processSaveARTransaction(AccountReceivableTransactionsObject accountReceivableTransactionsObject){
        try {
            //Insert the transaction information
            Transactions addTransaction = new Transactions(
                    accountReceivableTransactionsObject.getMachineNumber(),
                    accountReceivableTransactionsObject.getControlNumber(),
                    accountReceivableTransactionsObject.getReceiptNumber(),
                    accountReceivableTransactionsObject.getGrossSales(),
                    accountReceivableTransactionsObject.getNetSales(),
                    accountReceivableTransactionsObject.getVatableSales(),
                    accountReceivableTransactionsObject.getVatExemptSales(),
                    accountReceivableTransactionsObject.getVatAmount(),
                    accountReceivableTransactionsObject.getDiscountAmount(),
                    accountReceivableTransactionsObject.getVatExpense(),
                    accountReceivableTransactionsObject.getTenderAmount(),
                    accountReceivableTransactionsObject.getChange(),
                    accountReceivableTransactionsObject.getServiceCharge(),
                    accountReceivableTransactionsObject.getType(),
                    accountReceivableTransactionsObject.getCashierId(),
                    accountReceivableTransactionsObject.getCashierName(),
                    accountReceivableTransactionsObject.getTakeOrderId(),
                    accountReceivableTransactionsObject.getTakeOrderName(),
                    accountReceivableTransactionsObject.getTotalQuantity(),
                    accountReceivableTransactionsObject.getTotalUnitCost(),
                    accountReceivableTransactionsObject.getTotalVoidAmount(),
                    accountReceivableTransactionsObject.getShiftNumber(),
                    accountReceivableTransactionsObject.getIsVoid(),
                    accountReceivableTransactionsObject.getVoidById(),
                    accountReceivableTransactionsObject.getVoidBy(),
                    accountReceivableTransactionsObject.getVoidAt(),
                    accountReceivableTransactionsObject.getIsBackout(),
                    accountReceivableTransactionsObject.getIsBackoutId(),
                    accountReceivableTransactionsObject.getBackoutBy(),
                    accountReceivableTransactionsObject.getBackoutAt(),
                    accountReceivableTransactionsObject.getChargeAccountId(),
                    accountReceivableTransactionsObject.getChargeAccountName(),
                    accountReceivableTransactionsObject.getIsAccountReceivable(),
                    accountReceivableTransactionsObject.getIsSentToServer(),
                    accountReceivableTransactionsObject.getIsComplete(),
                    accountReceivableTransactionsObject.getCompletedAt(),
                    accountReceivableTransactionsObject.getIsCutoff(),
                    accountReceivableTransactionsObject.getCutoffId(),
                    accountReceivableTransactionsObject.getCutoffAt(),
                    accountReceivableTransactionsObject.getBranchId(),
                    accountReceivableTransactionsObject.getGuestName(),
                    accountReceivableTransactionsObject.getIsResumePrinted(),
                    accountReceivableTransactionsObject.getTreg(),
                    accountReceivableTransactionsObject.getTotalVoidQty(),
                    accountReceivableTransactionsObject.getIsReturn(),
                    accountReceivableTransactionsObject.getTotalReturnAmount(),
                    accountReceivableTransactionsObject.getTotalCashAmount(),
                    accountReceivableTransactionsObject.getVoidRemarks(),
                    accountReceivableTransactionsObject.getVoidCounter(),
                    accountReceivableTransactionsObject.getTotalZeroRatedAmount(),
                    accountReceivableTransactionsObject.getCustomerName(),
                    accountReceivableTransactionsObject.getRemarks(),
                    accountReceivableTransactionsObject.getCompanyId(),
                    accountReceivableTransactionsObject.getIsAccountReceivableRedeem(),
                    accountReceivableTransactionsObject.getAccountReceivableRedeemAt()
            );
            addTransaction.setId(accountReceivableTransactionsObject.getId());
            transactionsViewModel.insert(addTransaction);
            //Insert the transaction order information
            accountReceivableTransactionsObject.getOrders().forEach(item -> {
                try {
                    Orders addOrder = new Orders(
                            item.getMachineNumber(),
                            item.getTransactionId(),
                            item.getProductId(),
                            item.getCode(),
                            item.getName(),
                            item.getDescription(),
                            item.getAbbreviation(),
                            item.getCost(),
                            item.getQty(),
                            item.getAmount(),
                            item.getOriginalAmount(),
                            item.getGross(),
                            item.getTotal(),
                            item.getTotalCost(),
                            item.getIsVatable(),
                            item.getVatAmount(),
                            item.getVatableSales(),
                            item.getVatExemptSales(),
                            item.getDiscountAmount(),
                            item.getVatExpense(),
                            item.getDepartmentId(),
                            item.getDepartmentName(),
                            item.getCategoryId(),
                            item.getCategoryName(),
                            item.getSubCategoryId(),
                            item.getSubCategoryName(),
                            item.getUnitId(),
                            item.getUnitName(),
                            item.getIsDiscountExempt(),
                            item.getIsOpenPrice(),
                            item.getWithSerial(),
                            item.getIsVoid(),
                            item.getVoidBy(),
                            item.getVoidAt(),
                            item.getIsBackout(),
                            item.getIsBackoutId(),
                            item.getBackoutBy(),
                            item.getMinAmountSold(),
                            item.getIsPaid(),
                            item.getIsSentToServer(),
                            item.getIsCompleted(),
                            item.getCompletedAt(),
                            item.getBranchId(),
                            item.getShiftNumber(),
                            item.getCutOffId(),
                            item.getTreg(),
                            item.getIsCutOff(),
                            item.getCutOffAt(),
                            item.getDiscountDetailsId(),
                            item.getRemarks(),
                            item.getIsReturn(),
                            item.getSerialNumber(),
                            item.getIsZeroRated(),
                            item.getZeroRatedAmount(),
                            item.getCompanyId(),
                            item.getPriceChangeReasonId()
                    );
                    addOrder.setId(item.getId());
                    ordersViewModel.insert(addOrder);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            //Insert the transaction discount information
            accountReceivableTransactionsObject.getDiscounts().forEach(item -> {
                try {
                    Discounts addDiscount = new Discounts(
                            item.getMachineNumber(),
                            item.getBranchId(),
                            item.getTransactionId(),
                            item.getCustomDiscountId(),
                            item.getDiscountTypeId(),
                            item.getDiscountName(),
                            item.getValue(),
                            item.getDiscountAmount(),
                            item.getVatExemptAmount(),
                            item.getVatExpense(),
                            item.getType(),
                            item.getCashierId(),
                            item.getCashierName(),
                            item.getAuthorizeId(),
                            item.getAuthorizeName(),
                            item.getIsVoid(),
                            item.getVoidById(),
                            item.getVoidBy(),
                            item.getVoidAt(),
                            item.getIsSentToServer(),
                            item.getIsCutOff(),
                            item.getCutOffId(),
                            item.getShiftNumber(),
                            item.getTreg(),
                            item.getIsZeroRated(),
                            item.getGrossAmount(),
                            item.getNetAmount(),
                            item.getCompanyId()
                    );
                    addDiscount.setId(item.getId());
                    discountsViewModel.insert(addDiscount);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            //Insert transaction discount other information
            accountReceivableTransactionsObject.getDiscountOtherInformations().forEach(item -> {
                DiscountOtherInformations addDiscountOtherInformation = new DiscountOtherInformations(
                        item.getMachineId(),
                        item.getBranchId(),
                        item.getTransactionId(),
                        item.getDiscountId(),
                        item.getName(),
                        item.getValue(),
                        item.getIsCutOff(),
                        item.getCutOffId(),
                        item.getIsVoid(),
                        item.getIsSentToServer(),
                        item.getTreg(),
                        item.getCompanyId()
                );
                addDiscountOtherInformation.setId(item.getId());
                discountOtherInformationsViewModel.insert(addDiscountOtherInformation);
            });
            //Insert transaction discount details information
            accountReceivableTransactionsObject.getDiscountDetails().forEach(item -> {
                DiscountDetails addDiscountDetail = new DiscountDetails(
                        item.getDiscountId(),
                        item.getMachineNumber(),
                        item.getBranchId(),
                        item.getCustomDiscountId(),
                        item.getTransactionId(),
                        item.getOrderId(),
                        item.getDiscountTypeId(),
                        item.getValue(),
                        item.getDiscountAmount(),
                        item.getVatExemptAmount(),
                        item.getVatExpense(),
                        item.getType(),
                        item.getIsVoid(),
                        item.getVoidById(),
                        item.getVoidBy(),
                        item.getVoidAt(),
                        item.getIsSentToServer(),
                        item.getIsCutOff(),
                        item.getCutOffId(),
                        item.isVatExempt(),
                        item.getShiftNumber(),
                        item.getTreg(),
                        item.isZeroRated(),
                        item.getCompanyId()
                );
                addDiscountDetail.setId(item.getId());
                discountDetailsViewModel.insert(addDiscountDetail);
            });
            //Insert transaction payment information
            accountReceivableTransactionsObject.getPayments().forEach(item -> {
                try {
                    Payments addPayment = new Payments(
                            item.getMachineNumber(),
                            item.getBranchId(),
                            item.getTransactionId(),
                            item.getPaymentTypeId(),
                            item.getPaymentTypeName(),
                            item.getAmount(),
                            item.getIsAdvancePayment(),
                            item.getShiftNumber(),
                            item.getIsSentToServer(),
                            item.getCutOffId(),
                            item.getTreg(),
                            item.getIsCutOff(),
                            item.getCutOffAt(),
                            item.getIsVoid(),
                            item.getVoidBy(),
                            item.getVoidAt(),
                            item.getVoidById(),
                            item.getCompanyId(),
                            item.getIsAccountReceivable()
                    );
                    addPayment.setId(item.getId());
                    paymentsViewModel.insert(addPayment);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            //Insert transaction payment other information
            accountReceivableTransactionsObject.getPaymentOtherInformations().forEach(item -> {
                PaymentOtherInformations addPaymentOtherInformation = new PaymentOtherInformations(
                        item.getMachineId(),
                        item.getBranchId(),
                        item.getTransactionId(),
                        item.getPaymentId(),
                        item.getName(),
                        item.getValue(),
                        item.getIsCutOff(),
                        item.getCutOffId(),
                        item.getIsVoid(),
                        item.getIsSentToServer(),
                        item.getTreg(),
                        item.getCompanyId()
                );
                addPaymentOtherInformation.setId(item.getId());
                paymentOtherInformationsViewModel.insert(addPaymentOtherInformation);
            });
            //Insert transaction official receipt information
            if(accountReceivableTransactionsObject.getOfficialReceiptInformations() != null){
                OfficialReceiptInformation addOfficialReceiptInformation = new OfficialReceiptInformation(
                        accountReceivableTransactionsObject.getOfficialReceiptInformations().getTransactionId(),
                        accountReceivableTransactionsObject.getOfficialReceiptInformations().getName(),
                        accountReceivableTransactionsObject.getOfficialReceiptInformations().getAddress(),
                        accountReceivableTransactionsObject.getOfficialReceiptInformations().getTin(),
                        accountReceivableTransactionsObject.getOfficialReceiptInformations().getBusinessStyle(),
                        accountReceivableTransactionsObject.getOfficialReceiptInformations().getIsVoid(),
                        accountReceivableTransactionsObject.getOfficialReceiptInformations().getVoidBy(),
                        accountReceivableTransactionsObject.getOfficialReceiptInformations().getVoidName(),
                        accountReceivableTransactionsObject.getOfficialReceiptInformations().getVoidAt(),
                        accountReceivableTransactionsObject.getOfficialReceiptInformations().getIsSentToServer(),
                        accountReceivableTransactionsObject.getOfficialReceiptInformations().getTreg(),
                        accountReceivableTransactionsObject.getOfficialReceiptInformations().getMachineNumber(),
                        accountReceivableTransactionsObject.getOfficialReceiptInformations().getBranchId(),
                        accountReceivableTransactionsObject.getOfficialReceiptInformations().getCompanyId()
                );
                addOfficialReceiptInformation.setId(accountReceivableTransactionsObject.getOfficialReceiptInformations().getId());
                officialReceiptInformationViewModel.insert(addOfficialReceiptInformation);
            }
            return accountReceivableTransactionsObject.getId();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void processVoidReceipt(AccountReceivableTransactionsObject accountReceivableTransactionsObject, UserAuthentication authorizeUser){
        try {
            Transactions transactions = transactionsViewModel.fetchTransaction(accountReceivableTransactionsObject.getId());
            if(transactions != null) {
                VoidRemarksDialog voidRemarksDialog = new VoidRemarksDialog(activity) {
                    @Override
                    public void confirm(String remarks) {
                        try {
                            LoadingDialog.getInstance().startLoadingDialog(activity, "Voiding account receivable transaction please wait...");
                            List<Payments> payments = paymentsViewModel.fetchTransactionPayments(accountReceivableTransactionsObject.getId());
                            List<PaymentOtherInformations> paymentOtherInformations = paymentOtherInformationsViewModel.fetchPaymentOtherInformationByTransactionId(accountReceivableTransactionsObject.getId());
                            List<Orders> orders = ordersViewModel.fetchTransactionOrders(accountReceivableTransactionsObject.getId());
                            List<Discounts> discounts = discountsViewModel.fetchDiscountsByTransactionId(accountReceivableTransactionsObject.getId());
                            List<DiscountOtherInformations> discountOtherInformations = discountOtherInformationsViewModel.fetchDiscountOtherInformationByTransactionId(accountReceivableTransactionsObject.getId());
                            List<DiscountDetails> discountDetails = discountDetailsViewModel.fetchDiscountDetailsByTransactionId(accountReceivableTransactionsObject.getId());
                            OfficialReceiptInformation officialReceiptInformation = officialReceiptInformationViewModel.fetchByTransactionId(accountReceivableTransactionsObject.getId());
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
                                            //item.setVoidById(0);
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
                                                        viewAccountReceivableDialog.getAccountReceivableInformation();
                                                        AuditTrail.getInstance().save(
                                                                POSApplication.getInstance().getUserAuthentication().getId(),
                                                                POSApplication.getInstance().getUserAuthentication().getName(),
                                                                transactions.getId(),
                                                                "TRANSACTION VOID AR",
                                                                "Account receivable transaction void with a control number of " + transactions.getControlNumber() + " cashier is " + POSApplication.getInstance().getUserAuthentication().getName() + " authorize by " + authorizeUser.getName(),
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
            else{
                int transactionId = processSaveARTransaction(accountReceivableTransactionsObject);
                Transactions newTransactions = transactionsViewModel.fetchTransaction(transactionId);
                VoidRemarksDialog voidRemarksDialog = new VoidRemarksDialog(activity) {
                    @Override
                    public void confirm(String remarks) {
                        try {
                            LoadingDialog.getInstance().startLoadingDialog(activity, "Voiding account receivable transaction please wait...");
                            List<Payments> payments = paymentsViewModel.fetchTransactionPayments(transactionId);
                            List<PaymentOtherInformations> paymentOtherInformations = paymentOtherInformationsViewModel.fetchPaymentOtherInformationByTransactionId(transactionId);
                            List<Orders> orders = ordersViewModel.fetchTransactionOrders(transactionId);
                            List<Discounts> discounts = discountsViewModel.fetchDiscountsByTransactionId(transactionId);
                            List<DiscountOtherInformations> discountOtherInformations = discountOtherInformationsViewModel.fetchDiscountOtherInformationByTransactionId(transactionId);
                            List<DiscountDetails> discountDetails = discountDetailsViewModel.fetchDiscountDetailsByTransactionId(transactionId);
                            OfficialReceiptInformation officialReceiptInformation = officialReceiptInformationViewModel.fetchByTransactionId(transactionId);
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
                                        newTransactions.setIsVoid(1);
                                        newTransactions.setVoidBy(authorizeUser.getName());
                                        newTransactions.setVoidById(authorizeUser.getId()); //This id of the approve user
                                        newTransactions.setVoidAt(date.now());
                                        newTransactions.setVoidRemarks(remarks);
                                        newTransactions.setVoidCounter(voidCounter);
                                        newTransactions.setIsSentToServer(0);
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
                                        newTransactions.setTotalVoidQty(totalVoidQty);
                                        transactionsViewModel.update(newTransactions);
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
                                                        viewAccountReceivableDialog.getAccountReceivableInformation();
                                                        AuditTrail.getInstance().save(
                                                                POSApplication.getInstance().getUserAuthentication().getId(),
                                                                POSApplication.getInstance().getUserAuthentication().getName(),
                                                                transactions.getId(),
                                                                "TRANSACTION VOID AR",
                                                                "Account receivable transaction void with a control number of " + transactions.getControlNumber() + " cashier is " + POSApplication.getInstance().getUserAuthentication().getName() + " authorize by " + authorizeUser.getName(),
                                                                authorizeUser.getId(),
                                                                authorizeUser.getName(),
                                                                0,
                                                                0
                                                        );
                                                        Writer.getInstance().writeTransaction(activity, Printer.getInstance().voidReceipt(newTransactions, orders, payments, discounts));
                                                        Printer.getInstance().print(activity, printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_POST_VOID), Printer.getInstance().voidReceipt(newTransactions, orders, payments, discounts));
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
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
