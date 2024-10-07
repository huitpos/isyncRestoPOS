package com.example.isyncpos.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.R;
import com.example.isyncpos.adapters.DiscountItemsAdapter;
import com.example.isyncpos.adapters.OrderItemsAdapter;
import com.example.isyncpos.authentication.UserAuthentication;
import com.example.isyncpos.common.AuditTrail;
import com.example.isyncpos.common.Compute;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.Font;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.common.LoadingDialog;
import com.example.isyncpos.entity.ChargeAccount;
import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.entity.DiscountTypeDepartments;
import com.example.isyncpos.entity.DiscountTypeFields;
import com.example.isyncpos.entity.DiscountTypes;
import com.example.isyncpos.entity.DiscountDetails;
import com.example.isyncpos.entity.Discounts;
import com.example.isyncpos.entity.Orders;
import com.example.isyncpos.entity.PaymentOtherInformations;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.process.POSProcess;
import com.example.isyncpos.viewmodel.DiscountOtherInformationsViewModel;
import com.example.isyncpos.viewmodel.DiscountTypeDepartmentsViewModel;
import com.example.isyncpos.viewmodel.DiscountTypeFieldOptionsViewModel;
import com.example.isyncpos.viewmodel.DiscountTypeFieldsViewModel;
import com.example.isyncpos.viewmodel.DiscountTypesViewModel;
import com.example.isyncpos.viewmodel.DiscountDetailsViewModel;
import com.example.isyncpos.viewmodel.DiscountsViewModel;
import com.example.isyncpos.viewmodel.OrdersViewModel;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiscountDialog extends DialogFragment implements View.OnClickListener {

    private RecyclerView recyclerDiscounts;
    private LinearLayout linearDiscountTypes;
    private MaterialButton btnDiscountBack;
    private DiscountTypesViewModel discountTypesViewModel;
    private DiscountDetailsViewModel discountDetailsViewModel;
    private DiscountsViewModel discountsViewModel;
    private TransactionsViewModel transactionsViewModel;
    private OrdersViewModel ordersViewModel;
    private DiscountTypeFieldsViewModel discountTypeFieldsViewModel;
    private DiscountTypeFieldOptionsViewModel discountTypeFieldOptionsViewModel;
    private DiscountOtherInformationsViewModel discountOtherInformationsViewModel;
    private DiscountTypeDepartmentsViewModel discountTypeDepartmentsViewModel;
    private AlertDialog alertDialog;
    private View view;
    private ArrayList<Orders> ordersList;
    private OrderItemsAdapter orderItemsAdapter;
    private DiscountItemsAdapter discountItemsAdapter;
    private POSProcess posProcess;
    private Compute compute;
    private Generate generate;
    private Dates date;
    private int selectedDiscountId = 0;
    private Long discountId;
    private double totalDiscountAmount = 0.00;
    private double totalVatExempt = 0.00;
    private double totalVatExpense = 0.00;
    private double totalGrossAmount = 0.00;
    private double totalNetAmount = 0.00;
    private UserAuthentication authorizeUser;

    public static DiscountDialog newDiscountDialog(){
        return new DiscountDialog();
    }

    public void setArgs(ArrayList<Orders> ordersList, OrderItemsAdapter orderItemsAdapter,@Nullable UserAuthentication authorizeUser){
        this.ordersList = ordersList;
        this.orderItemsAdapter = orderItemsAdapter;
        this.authorizeUser = authorizeUser;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_discounts, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        //Initialize
        initialize();
        initRecyclerView();

        discountsViewModel.getCurrentTransactionDiscounts().observe(getActivity(), new Observer<List<Discounts>>() {
            @Override
            public void onChanged(List<Discounts> discounts) {
                discountItemsAdapter.submitList(discounts);
            }
        });

        discountTypesViewModel.fetchAll().observe(getActivity(), new Observer<List<DiscountTypes>>() {
            @Override
            public void onChanged(List<DiscountTypes> discountTypes) {
                linearDiscountTypes.removeAllViews();
                for(DiscountTypes item : discountTypes){
                    MaterialButton materialButtonDiscounts = new MaterialButton(getContext());
                    materialButtonDiscounts.setTypeface(Font.getInstance().getSanFranciscoBoldFont(view.getResources()));
                    materialButtonDiscounts.setText(item.getName());
                    materialButtonDiscounts.setTextSize(12);
                    materialButtonDiscounts.setId(item.getCoreId());
//                    materialButtonDiscounts.setMinHeight(125);
                    materialButtonDiscounts.setCornerRadius(10);
                    materialButtonDiscounts.setTextColor(view.getResources().getColor(R.color.white, null));
                    materialButtonDiscounts.setIconTintResource(R.color.white);
                    materialButtonDiscounts.setOnClickListener((View.OnClickListener) DiscountDialog.this);
                    linearDiscountTypes.addView(materialButtonDiscounts);
                }
            }
        });

        btnDiscountBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderItemsAdapter.clearSelectedListOrders();
                dismiss();
            }
        });

        alertDialog = builder.create();
        return alertDialog;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        discountTypesViewModel.fetchAll().removeObservers(getActivity());
        discountsViewModel.getCurrentTransactionDiscounts().removeObservers(getActivity());
    }

    private void initialize(){
        btnDiscountBack = view.findViewById(R.id.btnDiscountBack);
        linearDiscountTypes = view.findViewById(R.id.linearDiscountTypes);
        recyclerDiscounts = view.findViewById(R.id.recyclerDiscounts);
        compute = new Compute();
        generate = new Generate();
        date = new Dates();
        posProcess = POSApplication.getInstance().getPosProcess();
        initViewModels();
    }

    private void initViewModels(){
        discountTypesViewModel = POSApplication.getInstance().getDiscountTypesViewModel();
        discountDetailsViewModel = POSApplication.getInstance().getDiscountDetailsViewModel();
        discountsViewModel = POSApplication.getInstance().getDiscountsViewModel();
        transactionsViewModel = POSApplication.getInstance().getTransactionsViewModel();
        ordersViewModel = POSApplication.getInstance().getOrdersViewModel();
        discountTypeFieldsViewModel = POSApplication.getInstance().getDiscountTypeFieldsViewModel();
        discountOtherInformationsViewModel = POSApplication.getInstance().getDiscountOtherInformationsViewModel();
        discountTypeFieldOptionsViewModel = POSApplication.getInstance().getDiscountTypeFieldOptionsViewModel();
        discountTypeDepartmentsViewModel = POSApplication.getInstance().getDiscountTypeDepartmentsViewModel();
    }

    private void initRecyclerView(){
        recyclerDiscounts = view.findViewById(R.id.recyclerDiscounts);
        recyclerDiscounts.setHasFixedSize(true);
        recyclerDiscounts.setLayoutManager(new LinearLayoutManager(getContext()));
        discountItemsAdapter = new DiscountItemsAdapter(transactionsViewModel, ordersViewModel, discountsViewModel, discountDetailsViewModel, getContext(), getActivity(), posProcess, discountOtherInformationsViewModel);
        recyclerDiscounts.setAdapter(discountItemsAdapter);
        discountsViewModel.setCurrentTransactionDiscounts(posProcess.getCurrentTransactionDiscounts());
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }

    private boolean checkIfItemIsDiscountExempt(int discountTypeId){
        try {
            DiscountTypes discountTypes = discountTypesViewModel.fetchById(discountTypeId);
            if(discountTypes != null){
                if(discountTypes.isVatExempt() || discountTypes.isZeroRated()){
                    for(Orders item : ordersList){
                        if(item.getIsDiscountExempt() == 1 || item.getIsVatable() == 0){
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                try {
                    MutableLiveData<List<Discounts>> mutableDiscountLiveData = discountsViewModel.getCurrentTransactionDiscounts();
                    if(mutableDiscountLiveData.getValue().size() != 0){
                        Toast.makeText(getContext(), "You can only apply one discount per transaction.", Toast.LENGTH_LONG).show();
                    } else if (checkIfItemIsDiscountExempt(v.getId())) {
                        DiscountTypes discountTypes = discountTypesViewModel.fetchById(v.getId());
                        if(discountTypes != null){
                            if(discountTypes.isVatExempt() || discountTypes.isZeroRated()){
                                for(Orders item : ordersList){
                                    if(item.getIsDiscountExempt() == 1 || item.getIsVatable() == 0){
                                        Toast.makeText(getContext(), "Cannot apply " + discountTypes.getName() + " to this item " + item.getName() + " please remove it.", Toast.LENGTH_LONG).show();
                                        break;
                                    }
                                }
                            }
                        }
                    } else{
                        DiscountTypes discountTypes = discountTypesViewModel.fetchById(v.getId());
                        if(discountTypes != null){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Confirmation");
                            builder.setMessage("Are you sure you want to apply this " + discountTypes.getName() + "?");
                            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        List<DiscountTypeFields> discountTypeFields = discountTypeFieldsViewModel.fetchByDiscountTypeId(v.getId());
                                        if(discountTypeFields != null && discountTypeFields.size() != 0){
                                            //This will check if require to override the value of the discount type
                                            if(discountTypes.isManual()){
                                                DiscountApplyManualDialog discountApplyManualDialog = new DiscountApplyManualDialog(getActivity(), discountTypes) {
                                                    @Override
                                                    public void process(boolean success, DiscountTypes discountTypes) {
                                                        CustomDialog customDialog = new CustomDialog(getActivity(), "Discount") {
                                                            @Override
                                                            public void confirmPayment(List<PaymentOtherInformations> paymentOtherInformations, ChargeAccount chargeAccount) {
                                                                //Do Nothing
                                                            }

                                                            @Override
                                                            public void confirmDiscount(List<DiscountOtherInformations> discountOtherInformations) {
                                                                ExecutorService executorService = Executors.newSingleThreadExecutor();
                                                                executorService.execute(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        getActivity().runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Recomputing transaction please wait...");
                                                                            }
                                                                        });

                                                                        //Save Discount Summary
                                                                        discountId = posProcess.saveTransactionDiscount(new Discounts(
                                                                                POSApplication.getInstance().getMachineDetails().getCoreId(),
                                                                                POSApplication.getInstance().getBranch().getCoreId(),
                                                                                POSApplication.getInstance().getCurrentTransaction(),
                                                                                0,
                                                                                discountTypes.getCoreId(),
                                                                                discountTypes.getName(),
                                                                                discountTypes.isZeroRated() ? 0 : discountTypes.getDiscount(),
                                                                                0,
                                                                                0,
                                                                                0,
                                                                                discountTypes.getType(),
                                                                                POSApplication.getInstance().getUserAuthentication().getId(),
                                                                                POSApplication.getInstance().getUserAuthentication().getName(),
                                                                                0,
                                                                                "",
                                                                                0,
                                                                                0,
                                                                                "",
                                                                                "",
                                                                                0,
                                                                                0,
                                                                                0,
                                                                                0,
                                                                                date.now(),
                                                                                discountTypes.isZeroRated() ? 1 : 0,
                                                                                0,
                                                                                0,
                                                                                POSApplication.getInstance().getCompany().getCoreId()
                                                                        ));
                                                                        //Reset Variables
                                                                        totalDiscountAmount = 0.00;
                                                                        totalVatExempt = 0.00;
                                                                        totalVatExpense = 0.00;
                                                                        totalGrossAmount = 0.00;
                                                                        totalNetAmount = 0.00;
                                                                        //Save Discount Details
                                                                        for(Orders item: ordersList){
                                                                            double discountAmount = 0.00;
                                                                            double vatExempt = 0.00;
                                                                            double vatExpense = 0.00;
                                                                            if(discountTypes.getType().equals("amount")){
                                                                                try {
                                                                                    Long totalCountDepartmentDiscount = discountTypeDepartmentsViewModel.fetchDiscountTypeDepartmentCount(discountTypes.getCoreId());
                                                                                    if(totalCountDepartmentDiscount.intValue() == 0){
                                                                                        if(discountTypes.isVatExempt()){
                                                                                            discountAmount = generate.toFourDecimal(compute.vatExempt(item.getGross()) - discountTypes.getDiscount());
                                                                                            vatExempt = generate.toFourDecimal(compute.vatExempt(item.getGross()));
                                                                                            vatExpense = generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount())));
                                                                                        }
                                                                                        else{
                                                                                            discountAmount = generate.toFourDecimal(discountTypes.getDiscount());
                                                                                        }
                                                                                    }
                                                                                    else {
                                                                                        DiscountTypeDepartments discountTypeDepartments = discountTypeDepartmentsViewModel.fetchByDepartmentDiscountId(item.getDepartmentId(), discountTypes.getCoreId());
                                                                                        if(discountTypeDepartments != null){
                                                                                            if(discountTypes.isVatExempt()){
                                                                                                discountAmount = generate.toFourDecimal(compute.vatExempt(item.getGross()) - discountTypes.getDiscount());
                                                                                                vatExempt = generate.toFourDecimal(compute.vatExempt(item.getGross()));
                                                                                                vatExpense = generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount())));
                                                                                            }
                                                                                            else{
                                                                                                discountAmount = generate.toFourDecimal(discountTypes.getDiscount());
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                } catch (ExecutionException e) {
                                                                                    throw new RuntimeException(e);
                                                                                } catch (InterruptedException e) {
                                                                                    throw new RuntimeException(e);
                                                                                }
                                                                            }
                                                                            else{
                                                                                //Percentage Discount
                                                                                try {
                                                                                    Long totalCountDepartmentDiscount = discountTypeDepartmentsViewModel.fetchDiscountTypeDepartmentCount(discountTypes.getCoreId());
                                                                                    if(totalCountDepartmentDiscount.intValue() == 0){
                                                                                        if(discountTypes.isVatExempt()){
                                                                                            discountAmount = generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount()) * compute.toPercentage(discountTypes.getDiscount()));
                                                                                            vatExempt = generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount()));
                                                                                            vatExpense = generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount())));
                                                                                        }
                                                                                        else{
                                                                                            discountAmount = generate.toFourDecimal((item.getQty() * item.getAmount()) * compute.toPercentage(discountTypes.getDiscount()));
                                                                                        }
                                                                                    }
                                                                                    else{
                                                                                        DiscountTypeDepartments discountTypeDepartments = discountTypeDepartmentsViewModel.fetchByDepartmentDiscountId(item.getDepartmentId(), discountTypes.getCoreId());
                                                                                        if(discountTypeDepartments != null){
                                                                                            if(discountTypes.isVatExempt()){
                                                                                                discountAmount = generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount()) * compute.toPercentage(discountTypes.getDiscount()));
                                                                                                vatExempt = generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount()));
                                                                                                vatExpense = generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount())));
                                                                                            }
                                                                                            else{
                                                                                                discountAmount = generate.toFourDecimal((item.getQty() * item.getAmount()) * compute.toPercentage(discountTypes.getDiscount()));
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                } catch (ExecutionException e) {
                                                                                    throw new RuntimeException(e);
                                                                                } catch (InterruptedException e) {
                                                                                    throw new RuntimeException(e);
                                                                                }
                                                                            }
                                                                            posProcess.saveTransactionDiscountDetails(new DiscountDetails(
                                                                                    Integer.parseInt(discountId.toString()),
                                                                                    POSApplication.getInstance().getMachineDetails().getCoreId(),
                                                                                    POSApplication.getInstance().getBranch().getCoreId(),
                                                                                    0,
                                                                                    POSApplication.getInstance().getCurrentTransaction(),
                                                                                    item.getId(),
                                                                                    discountTypes.getCoreId(),
                                                                                    discountTypes.getDiscount(),
                                                                                    discountAmount,
                                                                                    vatExempt,
                                                                                    vatExpense,
                                                                                    discountTypes.getType(),
                                                                                    0,
                                                                                    0,
                                                                                    "",
                                                                                    "",
                                                                                    0,
                                                                                    0,
                                                                                    0,
                                                                                    discountTypes.isVatExempt(),
                                                                                    0,
                                                                                    date.now(),
                                                                                    discountTypes.isZeroRated(),
                                                                                    POSApplication.getInstance().getCompany().getCoreId()
                                                                            ));
                                                                            totalDiscountAmount += discountAmount;
                                                                            totalVatExempt +=  vatExempt;
                                                                            totalVatExpense += vatExpense;
                                                                            totalGrossAmount += item.getGross();
                                                                            totalNetAmount += (item.getGross() - vatExpense) - discountAmount;
                                                                        }
                                                                        discountOtherInformations.forEach(item -> {
                                                                            item.setDiscountId(discountId.intValue());
                                                                            discountOtherInformationsViewModel.insert(item);
                                                                        });

                                                                        getActivity().runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                posProcess.recomputeCurrentTransactionDetailsDiscount();
                                                                                final Handler handler1 = new Handler();
                                                                                handler1.postDelayed(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        try {
                                                                                            //Save Audit Trail
                                                                                            saveAuditTrail();
                                                                                            Discounts discounts = discountsViewModel.fetchDiscountByDiscountId(Integer.parseInt(discountId.toString()));
                                                                                            discounts.setDiscountAmount(generate.toFourDecimal(totalDiscountAmount));
                                                                                            discounts.setVatExemptAmount(generate.toFourDecimal(totalVatExempt));
                                                                                            discounts.setVatExpense(generate.toFourDecimal(totalVatExpense));
                                                                                            discounts.setGrossAmount(generate.toFourDecimal(totalGrossAmount));
                                                                                            discounts.setNetAmount(generate.toFourDecimal(totalNetAmount));
                                                                                            posProcess.updateTransactionDiscount(discounts);
                                                                                            transactionsViewModel.setCurrentTransaction(posProcess.recomputeCurrentTransaction());
                                                                                            ordersViewModel.setCurrentTransactionOrders(posProcess.getCurrentTransactionOrders());
                                                                                            discountsViewModel.setCurrentTransactionDiscounts(posProcess.getCurrentTransactionDiscounts());
                                                                                            final Handler handler2 = new Handler();
                                                                                            handler2.postDelayed(new Runnable() {
                                                                                                @Override
                                                                                                public void run() {
                                                                                                    LoadingDialog.getInstance().closeLoadingDialog();
                                                                                                    Toast.makeText(getContext(), "Discount has been apply.", Toast.LENGTH_LONG).show();
                                                                                                    orderItemsAdapter.clearSelectedListOrders();
                                                                                                }
                                                                                            }, BuildConfig.APP_DEFAULT_LOADING_TIME);
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
                                                        };
                                                        customDialog.setDiscountTypeFields(discountTypeFields);
                                                        customDialog.setDiscountTypeFieldOptionsViewModel(discountTypeFieldOptionsViewModel);
                                                        customDialog.setCancelable(false);
                                                        customDialog.show();
                                                    }
                                                };
                                                discountApplyManualDialog.setCancelable(false);
                                                discountApplyManualDialog.show();
                                            }
                                            else{
                                                CustomDialog customDialog = new CustomDialog(getActivity(), "Discount") {
                                                    @Override
                                                    public void confirmPayment(List<PaymentOtherInformations> paymentOtherInformations, ChargeAccount chargeAccount) {
                                                        //Do Nothing
                                                    }

                                                    @Override
                                                    public void confirmDiscount(List<DiscountOtherInformations> discountOtherInformations) {
                                                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                                                        executorService.execute(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                getActivity().runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Recomputing transaction please wait...");
                                                                    }
                                                                });

                                                                //Save Discount Summary
                                                                discountId = posProcess.saveTransactionDiscount(new Discounts(
                                                                        POSApplication.getInstance().getMachineDetails().getCoreId(),
                                                                        POSApplication.getInstance().getBranch().getCoreId(),
                                                                        POSApplication.getInstance().getCurrentTransaction(),
                                                                        0,
                                                                        discountTypes.getCoreId(),
                                                                        discountTypes.getName(),
                                                                        discountTypes.isZeroRated() ? 0 : discountTypes.getDiscount(),
                                                                        0,
                                                                        0,
                                                                        0,
                                                                        discountTypes.getType(),
                                                                        POSApplication.getInstance().getUserAuthentication().getId(),
                                                                        POSApplication.getInstance().getUserAuthentication().getName(),
                                                                        0,
                                                                        "",
                                                                        0,
                                                                        0,
                                                                        "",
                                                                        "",
                                                                        0,
                                                                        0,
                                                                        0,
                                                                        0,
                                                                        date.now(),
                                                                        discountTypes.isZeroRated() ? 1 : 0,
                                                                        0,
                                                                        0,
                                                                        POSApplication.getInstance().getCompany().getCoreId()
                                                                ));
                                                                //Reset Variables
                                                                totalDiscountAmount = 0.00;
                                                                totalVatExempt = 0.00;
                                                                totalVatExpense = 0.00;
                                                                totalGrossAmount = 0.00;
                                                                totalNetAmount = 0.00;
                                                                //Save Discount Details
                                                                for(Orders item: ordersList){
                                                                    double discountAmount = 0.00;
                                                                    double vatExempt = 0.00;
                                                                    double vatExpense = 0.00;
                                                                    if(discountTypes.getType().equals("amount")){
                                                                        try {
                                                                            Long totalCountDepartmentDiscount = discountTypeDepartmentsViewModel.fetchDiscountTypeDepartmentCount(discountTypes.getCoreId());
                                                                            if(totalCountDepartmentDiscount.intValue() == 0){
                                                                                if(discountTypes.isVatExempt()){
                                                                                    discountAmount = generate.toFourDecimal(compute.vatExempt(item.getGross()) - discountTypes.getDiscount());
                                                                                    vatExempt = generate.toFourDecimal(compute.vatExempt(item.getGross()));
                                                                                    vatExpense = generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount())));
                                                                                }
                                                                                else{
                                                                                    discountAmount = generate.toFourDecimal(discountTypes.getDiscount());
                                                                                }
                                                                            }
                                                                            else {
                                                                                DiscountTypeDepartments discountTypeDepartments = discountTypeDepartmentsViewModel.fetchByDepartmentDiscountId(item.getDepartmentId(), discountTypes.getCoreId());
                                                                                if(discountTypeDepartments != null){
                                                                                    if(discountTypes.isVatExempt()){
                                                                                        discountAmount = generate.toFourDecimal(compute.vatExempt(item.getGross()) - discountTypes.getDiscount());
                                                                                        vatExempt = generate.toFourDecimal(compute.vatExempt(item.getGross()));
                                                                                        vatExpense = generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount())));
                                                                                    }
                                                                                    else{
                                                                                        discountAmount = generate.toFourDecimal(discountTypes.getDiscount());
                                                                                    }
                                                                                }
                                                                            }
                                                                        } catch (ExecutionException e) {
                                                                            throw new RuntimeException(e);
                                                                        } catch (InterruptedException e) {
                                                                            throw new RuntimeException(e);
                                                                        }
                                                                    }
                                                                    else{
                                                                        //Percentage Discount
                                                                        try {
                                                                            Long totalCountDepartmentDiscount = discountTypeDepartmentsViewModel.fetchDiscountTypeDepartmentCount(discountTypes.getCoreId());
                                                                            if(totalCountDepartmentDiscount.intValue() == 0){
                                                                                if(discountTypes.isVatExempt()){
                                                                                    discountAmount = generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount()) * compute.toPercentage(discountTypes.getDiscount()));
                                                                                    vatExempt = generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount()));
                                                                                    vatExpense = generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount())));
                                                                                }
                                                                                else{
                                                                                    discountAmount = generate.toFourDecimal((item.getQty() * item.getAmount()) * compute.toPercentage(discountTypes.getDiscount()));
                                                                                }
                                                                            }
                                                                            else{
                                                                                DiscountTypeDepartments discountTypeDepartments = discountTypeDepartmentsViewModel.fetchByDepartmentDiscountId(item.getDepartmentId(), discountTypes.getCoreId());
                                                                                if(discountTypeDepartments != null){
                                                                                    if(discountTypes.isVatExempt()){
                                                                                        discountAmount = generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount()) * compute.toPercentage(discountTypes.getDiscount()));
                                                                                        vatExempt = generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount()));
                                                                                        vatExpense = generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount())));
                                                                                    }
                                                                                    else{
                                                                                        discountAmount = generate.toFourDecimal((item.getQty() * item.getAmount()) * compute.toPercentage(discountTypes.getDiscount()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        } catch (ExecutionException e) {
                                                                            throw new RuntimeException(e);
                                                                        } catch (InterruptedException e) {
                                                                            throw new RuntimeException(e);
                                                                        }
                                                                    }
                                                                    posProcess.saveTransactionDiscountDetails(new DiscountDetails(
                                                                            Integer.parseInt(discountId.toString()),
                                                                            POSApplication.getInstance().getMachineDetails().getCoreId(),
                                                                            POSApplication.getInstance().getBranch().getCoreId(),
                                                                            0,
                                                                            POSApplication.getInstance().getCurrentTransaction(),
                                                                            item.getId(),
                                                                            discountTypes.getCoreId(),
                                                                            discountTypes.getDiscount(),
                                                                            discountAmount,
                                                                            vatExempt,
                                                                            vatExpense,
                                                                            discountTypes.getType(),
                                                                            0,
                                                                            0,
                                                                            "",
                                                                            "",
                                                                            0,
                                                                            0,
                                                                            0,
                                                                            discountTypes.isVatExempt(),
                                                                            0,
                                                                            date.now(),
                                                                            discountTypes.isZeroRated(),
                                                                            POSApplication.getInstance().getCompany().getCoreId()
                                                                    ));
                                                                    totalDiscountAmount += discountAmount;
                                                                    totalVatExempt +=  vatExempt;
                                                                    totalVatExpense += vatExpense;
                                                                    totalGrossAmount += item.getGross();
                                                                    totalNetAmount += (item.getGross() - vatExpense) - discountAmount;
                                                                }
                                                                discountOtherInformations.forEach(item -> {
                                                                    item.setDiscountId(discountId.intValue());
                                                                    discountOtherInformationsViewModel.insert(item);
                                                                });

                                                                getActivity().runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        posProcess.recomputeCurrentTransactionDetailsDiscount();
                                                                        final Handler handler1 = new Handler();
                                                                        handler1.postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                try {
                                                                                    //Save Audit Trail
                                                                                    saveAuditTrail();
                                                                                    Discounts discounts = discountsViewModel.fetchDiscountByDiscountId(Integer.parseInt(discountId.toString()));
                                                                                    discounts.setDiscountAmount(generate.toFourDecimal(totalDiscountAmount));
                                                                                    discounts.setVatExemptAmount(generate.toFourDecimal(totalVatExempt));
                                                                                    discounts.setVatExpense(generate.toFourDecimal(totalVatExpense));
                                                                                    discounts.setGrossAmount(generate.toFourDecimal(totalGrossAmount));
                                                                                    discounts.setNetAmount(generate.toFourDecimal(totalNetAmount));
                                                                                    posProcess.updateTransactionDiscount(discounts);
                                                                                    transactionsViewModel.setCurrentTransaction(posProcess.recomputeCurrentTransaction());
                                                                                    ordersViewModel.setCurrentTransactionOrders(posProcess.getCurrentTransactionOrders());
                                                                                    discountsViewModel.setCurrentTransactionDiscounts(posProcess.getCurrentTransactionDiscounts());
                                                                                    final Handler handler2 = new Handler();
                                                                                    handler2.postDelayed(new Runnable() {
                                                                                        @Override
                                                                                        public void run() {
                                                                                            LoadingDialog.getInstance().closeLoadingDialog();
                                                                                            Toast.makeText(getContext(), "Discount has been apply.", Toast.LENGTH_LONG).show();
                                                                                            orderItemsAdapter.clearSelectedListOrders();
                                                                                        }
                                                                                    }, BuildConfig.APP_DEFAULT_LOADING_TIME);
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
                                                };
                                                customDialog.setDiscountTypeFields(discountTypeFields);
                                                customDialog.setDiscountTypeFieldOptionsViewModel(discountTypeFieldOptionsViewModel);
                                                customDialog.setCancelable(false);
                                                customDialog.show();
                                            }
                                        }
                                        else{
                                            //This will check if require to override the value of the discount type
                                            if(discountTypes.isManual()){
                                                DiscountApplyManualDialog discountApplyManualDialog = new DiscountApplyManualDialog(getActivity(), discountTypes) {
                                                    @Override
                                                    public void process(boolean success, DiscountTypes discountTypes) {
                                                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                                                        executorService.execute(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                getActivity().runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Recomputing transaction please wait...");
                                                                    }
                                                                });

                                                                //Save Discount Summary
                                                                discountId = posProcess.saveTransactionDiscount(new Discounts(
                                                                        POSApplication.getInstance().getMachineDetails().getCoreId(),
                                                                        POSApplication.getInstance().getBranch().getCoreId(),
                                                                        POSApplication.getInstance().getCurrentTransaction(),
                                                                        0,
                                                                        discountTypes.getCoreId(),
                                                                        discountTypes.getName(),
                                                                        discountTypes.isZeroRated() ? 0 : discountTypes.getDiscount(),
                                                                        0,
                                                                        0,
                                                                        0,
                                                                        discountTypes.getType(),
                                                                        POSApplication.getInstance().getUserAuthentication().getId(),
                                                                        POSApplication.getInstance().getUserAuthentication().getName(),
                                                                        0,
                                                                        "",
                                                                        0,
                                                                        0,
                                                                        "",
                                                                        "",
                                                                        0,
                                                                        0,
                                                                        0,
                                                                        0,
                                                                        date.now(),
                                                                        discountTypes.isZeroRated() ? 1 : 0,
                                                                        0,
                                                                        0,
                                                                        POSApplication.getInstance().getCompany().getCoreId()
                                                                ));
                                                                //Reset Variables
                                                                totalDiscountAmount = 0.00;
                                                                totalVatExempt = 0.00;
                                                                totalVatExpense = 0.00;
                                                                totalGrossAmount = 0.00;
                                                                totalNetAmount = 0.00;
                                                                //Save Discount Details
                                                                for(Orders item: ordersList){
                                                                    double discountAmount = 0.00;
                                                                    double vatExempt = 0.00;
                                                                    double vatExpense = 0.00;
                                                                    if(discountTypes.getType().equals("amount")){
                                                                        try {
                                                                            Long totalCountDepartmentDiscount = discountTypeDepartmentsViewModel.fetchDiscountTypeDepartmentCount(discountTypes.getCoreId());
                                                                            if(totalCountDepartmentDiscount.intValue() == 0){
                                                                                if(discountTypes.isVatExempt()){
                                                                                    discountAmount = generate.toFourDecimal(compute.vatExempt(item.getGross()) - discountTypes.getDiscount());
                                                                                    vatExempt = generate.toFourDecimal(compute.vatExempt(item.getGross()));
                                                                                    vatExpense = generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount())));
                                                                                }
                                                                                else{
                                                                                    discountAmount = generate.toFourDecimal(discountTypes.getDiscount());
                                                                                }
                                                                            }
                                                                            else{
                                                                                DiscountTypeDepartments discountTypeDepartments = discountTypeDepartmentsViewModel.fetchByDepartmentDiscountId(item.getDepartmentId(), discountTypes.getCoreId());
                                                                                if(discountTypeDepartments != null){
                                                                                    if(discountTypes.isVatExempt()){
                                                                                        discountAmount = generate.toFourDecimal(compute.vatExempt(item.getGross()) - discountTypes.getDiscount());
                                                                                        vatExempt = generate.toFourDecimal(compute.vatExempt(item.getGross()));
                                                                                        vatExpense = generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount())));
                                                                                    }
                                                                                    else{
                                                                                        discountAmount = generate.toFourDecimal(discountTypes.getDiscount());
                                                                                    }
                                                                                }
                                                                            }
                                                                        } catch (ExecutionException e) {
                                                                            throw new RuntimeException(e);
                                                                        } catch (InterruptedException e) {
                                                                            throw new RuntimeException(e);
                                                                        }
                                                                    }
                                                                    else{
                                                                        //Discount Percentage
                                                                        try {
                                                                            Long totalCountDepartmentDiscount = discountTypeDepartmentsViewModel.fetchDiscountTypeDepartmentCount(discountTypes.getCoreId());
                                                                            if(totalCountDepartmentDiscount.intValue() == 0){
                                                                                if(discountTypes.isVatExempt()){
                                                                                    discountAmount = generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount()) * compute.toPercentage(discountTypes.getDiscount()));
                                                                                    vatExempt = generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount()));
                                                                                    vatExpense = generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount())));
                                                                                }
                                                                                else{
                                                                                    discountAmount = generate.toFourDecimal((item.getQty() * item.getAmount()) * compute.toPercentage(discountTypes.getDiscount()));
                                                                                }
                                                                            }
                                                                            else{
                                                                                DiscountTypeDepartments discountTypeDepartments = discountTypeDepartmentsViewModel.fetchByDepartmentDiscountId(item.getDepartmentId(), discountTypes.getCoreId());
                                                                                if(discountTypeDepartments != null){
                                                                                    if(discountTypes.isVatExempt()){
                                                                                        discountAmount = generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount()) * compute.toPercentage(discountTypes.getDiscount()));
                                                                                        vatExempt = generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount()));
                                                                                        vatExpense = generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount())));
                                                                                    }
                                                                                    else{
                                                                                        discountAmount = generate.toFourDecimal((item.getQty() * item.getAmount()) * compute.toPercentage(discountTypes.getDiscount()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        } catch (ExecutionException e) {
                                                                            throw new RuntimeException(e);
                                                                        } catch (InterruptedException e) {
                                                                            throw new RuntimeException(e);
                                                                        }
                                                                    }
                                                                    posProcess.saveTransactionDiscountDetails(new DiscountDetails(
                                                                            Integer.parseInt(discountId.toString()),
                                                                            POSApplication.getInstance().getMachineDetails().getCoreId(),
                                                                            POSApplication.getInstance().getBranch().getCoreId(),
                                                                            0,
                                                                            POSApplication.getInstance().getCurrentTransaction(),
                                                                            item.getId(),
                                                                            discountTypes.getCoreId(),
                                                                            discountTypes.getDiscount(),
                                                                            discountAmount,
                                                                            vatExempt,
                                                                            vatExpense,
                                                                            discountTypes.getType(),
                                                                            0,
                                                                            0,
                                                                            "",
                                                                            "",
                                                                            0,
                                                                            0,
                                                                            0,
                                                                            discountTypes.isVatExempt(),
                                                                            0,
                                                                            date.now(),
                                                                            discountTypes.isZeroRated(),
                                                                            POSApplication.getInstance().getCompany().getCoreId()
                                                                    ));
                                                                    totalDiscountAmount += discountAmount;
                                                                    totalVatExempt +=  vatExempt;
                                                                    totalVatExpense += vatExpense;
                                                                    totalGrossAmount += item.getGross();
                                                                    totalNetAmount += (item.getGross() - vatExpense) - discountAmount;
                                                                }

                                                                getActivity().runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        posProcess.recomputeCurrentTransactionDetailsDiscount();
                                                                        final Handler handler1 = new Handler();
                                                                        handler1.postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                try {
                                                                                    //Save Audit Trail
                                                                                    saveAuditTrail();
                                                                                    Discounts discounts = discountsViewModel.fetchDiscountByDiscountId(Integer.parseInt(discountId.toString()));
                                                                                    discounts.setDiscountAmount(generate.toFourDecimal(totalDiscountAmount));
                                                                                    discounts.setVatExemptAmount(generate.toFourDecimal(totalVatExempt));
                                                                                    discounts.setVatExpense(generate.toFourDecimal(totalVatExpense));
                                                                                    discounts.setGrossAmount(generate.toFourDecimal(totalGrossAmount));
                                                                                    discounts.setNetAmount(generate.toFourDecimal(totalNetAmount));
                                                                                    posProcess.updateTransactionDiscount(discounts);
                                                                                    transactionsViewModel.setCurrentTransaction(posProcess.recomputeCurrentTransaction());
                                                                                    ordersViewModel.setCurrentTransactionOrders(posProcess.getCurrentTransactionOrders());
                                                                                    discountsViewModel.setCurrentTransactionDiscounts(posProcess.getCurrentTransactionDiscounts());
                                                                                    final Handler handler2 = new Handler();
                                                                                    handler2.postDelayed(new Runnable() {
                                                                                        @Override
                                                                                        public void run() {
                                                                                            LoadingDialog.getInstance().closeLoadingDialog();
                                                                                            Toast.makeText(getContext(), "Discount has been apply.", Toast.LENGTH_LONG).show();
                                                                                            orderItemsAdapter.clearSelectedListOrders();
                                                                                        }
                                                                                    }, BuildConfig.APP_DEFAULT_LOADING_TIME);
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
                                                };
                                                discountApplyManualDialog.setCancelable(false);
                                                discountApplyManualDialog.show();
                                            }
                                            else{
                                                ExecutorService executorService = Executors.newSingleThreadExecutor();
                                                executorService.execute(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        getActivity().runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                LoadingDialog.getInstance().startLoadingDialog(getActivity(), "Recomputing transaction please wait...");
                                                            }
                                                        });

                                                        //Save Discount Summary
                                                        discountId = posProcess.saveTransactionDiscount(new Discounts(
                                                                POSApplication.getInstance().getMachineDetails().getCoreId(),
                                                                POSApplication.getInstance().getBranch().getCoreId(),
                                                                POSApplication.getInstance().getCurrentTransaction(),
                                                                0,
                                                                discountTypes.getCoreId(),
                                                                discountTypes.getName(),
                                                                discountTypes.isZeroRated() ? 0 : discountTypes.getDiscount(),
                                                                0,
                                                                0,
                                                                0,
                                                                discountTypes.getType(),
                                                                POSApplication.getInstance().getUserAuthentication().getId(),
                                                                POSApplication.getInstance().getUserAuthentication().getName(),
                                                                0,
                                                                "",
                                                                0,
                                                                0,
                                                                "",
                                                                "",
                                                                0,
                                                                0,
                                                                0,
                                                                0,
                                                                date.now(),
                                                                discountTypes.isZeroRated() ? 1 : 0,
                                                                0,
                                                                0,
                                                                POSApplication.getInstance().getCompany().getCoreId()
                                                        ));
                                                        //Reset Variables
                                                        totalDiscountAmount = 0.00;
                                                        totalVatExempt = 0.00;
                                                        totalVatExpense = 0.00;
                                                        totalGrossAmount = 0.00;
                                                        totalNetAmount = 0.00;
                                                        //Save Discount Details
                                                        for(Orders item: ordersList){
                                                            double discountAmount = 0.00;
                                                            double vatExempt = 0.00;
                                                            double vatExpense = 0.00;
                                                            if(discountTypes.getType().equals("amount")){
                                                                try {
                                                                    Long totalCountDepartmentDiscount = discountTypeDepartmentsViewModel.fetchDiscountTypeDepartmentCount(discountTypes.getCoreId());
                                                                    if(totalCountDepartmentDiscount.intValue() == 0){
                                                                        if(discountTypes.isVatExempt()){
                                                                            discountAmount = generate.toFourDecimal(compute.vatExempt(item.getGross()) - discountTypes.getDiscount());
                                                                            vatExempt = generate.toFourDecimal(compute.vatExempt(item.getGross()));
                                                                            vatExpense = generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount())));
                                                                        }
                                                                        else{
                                                                            discountAmount = generate.toFourDecimal(discountTypes.getDiscount());
                                                                        }
                                                                    }
                                                                    else{
                                                                        DiscountTypeDepartments discountTypeDepartments = discountTypeDepartmentsViewModel.fetchByDepartmentDiscountId(item.getDepartmentId(), discountTypes.getCoreId());
                                                                        if(discountTypeDepartments != null){
                                                                            if(discountTypes.isVatExempt()){
                                                                                discountAmount = generate.toFourDecimal(compute.vatExempt(item.getGross()) - discountTypes.getDiscount());
                                                                                vatExempt = generate.toFourDecimal(compute.vatExempt(item.getGross()));
                                                                                vatExpense = generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount())));
                                                                            }
                                                                            else{
                                                                                discountAmount = generate.toFourDecimal(discountTypes.getDiscount());
                                                                            }
                                                                        }
                                                                    }
                                                                } catch (ExecutionException e) {
                                                                    throw new RuntimeException(e);
                                                                } catch (InterruptedException e) {
                                                                    throw new RuntimeException(e);
                                                                }
                                                            }
                                                            else{
                                                                //Discount Percentage
                                                                try {
                                                                    Long totalCountDepartmentDiscount = discountTypeDepartmentsViewModel.fetchDiscountTypeDepartmentCount(discountTypes.getCoreId());
                                                                    if(totalCountDepartmentDiscount.intValue() == 0){
                                                                        if(discountTypes.isVatExempt()){
                                                                            discountAmount = generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount()) * compute.toPercentage(discountTypes.getDiscount()));
                                                                            vatExempt = generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount()));
                                                                            vatExpense = generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount())));
                                                                        }
                                                                        else{
                                                                            discountAmount = generate.toFourDecimal((item.getQty() * item.getAmount()) * compute.toPercentage(discountTypes.getDiscount()));
                                                                        }
                                                                    }
                                                                    else{
                                                                        DiscountTypeDepartments discountTypeDepartments = discountTypeDepartmentsViewModel.fetchByDepartmentDiscountId(item.getDepartmentId(), discountTypes.getCoreId());
                                                                        if(discountTypeDepartments != null){
                                                                            if(discountTypes.isVatExempt()){
                                                                                discountAmount = generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount()) * compute.toPercentage(discountTypes.getDiscount()));
                                                                                vatExempt = generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount()));
                                                                                vatExpense = generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount())));
                                                                            }
                                                                            else{
                                                                                discountAmount = generate.toFourDecimal((item.getQty() * item.getAmount()) * compute.toPercentage(discountTypes.getDiscount()));
                                                                            }
                                                                        }
                                                                    }
                                                                } catch (ExecutionException e) {
                                                                    throw new RuntimeException(e);
                                                                } catch (InterruptedException e) {
                                                                    throw new RuntimeException(e);
                                                                }
                                                            }
                                                            posProcess.saveTransactionDiscountDetails(new DiscountDetails(
                                                                    Integer.parseInt(discountId.toString()),
                                                                    POSApplication.getInstance().getMachineDetails().getCoreId(),
                                                                    POSApplication.getInstance().getBranch().getCoreId(),
                                                                    0,
                                                                    POSApplication.getInstance().getCurrentTransaction(),
                                                                    item.getId(),
                                                                    discountTypes.getCoreId(),
                                                                    discountTypes.getDiscount(),
                                                                    discountAmount,
                                                                    vatExempt,
                                                                    vatExpense,
                                                                    discountTypes.getType(),
                                                                    0,
                                                                    0,
                                                                    "",
                                                                    "",
                                                                    0,
                                                                    0,
                                                                    0,
                                                                    discountTypes.isVatExempt(),
                                                                    0,
                                                                    date.now(),
                                                                    discountTypes.isZeroRated(),
                                                                    POSApplication.getInstance().getCompany().getCoreId()
                                                            ));
                                                            totalDiscountAmount += discountAmount;
                                                            totalVatExempt +=  vatExempt;
                                                            totalVatExpense += vatExpense;
                                                            totalGrossAmount += item.getGross();
                                                            totalNetAmount += (item.getGross() - vatExpense) - discountAmount;
                                                        }

                                                        getActivity().runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                posProcess.recomputeCurrentTransactionDetailsDiscount();
                                                                final Handler handler1 = new Handler();
                                                                handler1.postDelayed(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        try {
                                                                            //Save Audit Trail
                                                                            saveAuditTrail();
                                                                            Discounts discounts = discountsViewModel.fetchDiscountByDiscountId(Integer.parseInt(discountId.toString()));
                                                                            discounts.setDiscountAmount(generate.toFourDecimal(totalDiscountAmount));
                                                                            discounts.setVatExemptAmount(generate.toFourDecimal(totalVatExempt));
                                                                            discounts.setVatExpense(generate.toFourDecimal(totalVatExpense));
                                                                            discounts.setGrossAmount(generate.toFourDecimal(totalGrossAmount));
                                                                            discounts.setNetAmount(generate.toFourDecimal(totalNetAmount));
                                                                            posProcess.updateTransactionDiscount(discounts);
                                                                            transactionsViewModel.setCurrentTransaction(posProcess.recomputeCurrentTransaction());
                                                                            ordersViewModel.setCurrentTransactionOrders(posProcess.getCurrentTransactionOrders());
                                                                            discountsViewModel.setCurrentTransactionDiscounts(posProcess.getCurrentTransactionDiscounts());
                                                                            final Handler handler2 = new Handler();
                                                                            handler2.postDelayed(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    LoadingDialog.getInstance().closeLoadingDialog();
                                                                                    Toast.makeText(getContext(), "Discount has been apply.", Toast.LENGTH_LONG).show();
                                                                                    orderItemsAdapter.clearSelectedListOrders();
                                                                                }
                                                                            }, BuildConfig.APP_DEFAULT_LOADING_TIME);
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
                                        }
                                    } catch (ExecutionException e) {
                                        throw new RuntimeException(e);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
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
                    }
                    break;
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
        }
    }

    private void saveAuditTrail(){
        AuditTrail.getInstance().save(
                POSApplication.getInstance().getUserAuthentication().getId(),
                POSApplication.getInstance().getUserAuthentication().getUsername(),
                POSApplication.getInstance().getCurrentTransaction(),
                "TRANSACTION APPLY DISCOUNT",
                productDescription(),
                authorizeUser != null ? authorizeUser.getId() : 0,
                authorizeUser != null ? authorizeUser.getName() : "",
                0,
                0
        );
    }

    private @NonNull String getDescription() {
        String description = "";
        if(authorizeUser != null){
            description = "Transaction apply discount on control number " + transactionsViewModel.getCurrentTransaction().getValue().getControlNumber() + " cashier is " + POSApplication.getInstance().getUserAuthentication().getName() + " authorize by " + authorizeUser.getName() + " products of";
        }
        else{
            description = "Transaction apply discount on control number " + transactionsViewModel.getCurrentTransaction().getValue().getControlNumber() + " cashier is " + POSApplication.getInstance().getUserAuthentication().getName() + " products of";
        }
        return description;
    }

    private String productDescription(){
        int counter = 0;
        String description = getDescription();
        for(Orders item : ordersList){
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

}
