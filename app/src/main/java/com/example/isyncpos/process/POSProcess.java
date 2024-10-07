package com.example.isyncpos.process;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.isyncpos.BuildConfig;
import com.example.isyncpos.POSApplication;
import com.example.isyncpos.common.AuditTrail;
import com.example.isyncpos.common.Compute;
import com.example.isyncpos.common.Dates;
import com.example.isyncpos.common.EncryptDecrypt;
import com.example.isyncpos.common.Functions;
import com.example.isyncpos.common.Generate;
import com.example.isyncpos.common.Printer;
import com.example.isyncpos.common.APIRequest;
import com.example.isyncpos.common.Writer;
import com.example.isyncpos.entity.AuditTrails;
import com.example.isyncpos.entity.CashFund;
import com.example.isyncpos.entity.CashFundDenomination;
import com.example.isyncpos.entity.Categories;
import com.example.isyncpos.entity.CutOff;
import com.example.isyncpos.entity.CutOffDepartments;
import com.example.isyncpos.entity.CutOffDiscounts;
import com.example.isyncpos.entity.CutOffPayments;
import com.example.isyncpos.entity.CutOffProducts;
import com.example.isyncpos.entity.Departments;
import com.example.isyncpos.entity.DiscountDetails;
import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.entity.DiscountTypeDepartments;
import com.example.isyncpos.entity.Discounts;
import com.example.isyncpos.entity.EndOfDay;
import com.example.isyncpos.entity.EndOfDayDepartments;
import com.example.isyncpos.entity.EndOfDayDiscounts;
import com.example.isyncpos.entity.EndOfDayPayments;
import com.example.isyncpos.entity.EndOffDayProducts;
import com.example.isyncpos.entity.MachineDetails;
import com.example.isyncpos.entity.OfficialReceiptInformation;
import com.example.isyncpos.entity.Orders;
import com.example.isyncpos.entity.PaymentOtherInformations;
import com.example.isyncpos.entity.PaymentTypes;
import com.example.isyncpos.entity.Payments;
import com.example.isyncpos.entity.Permissions;
import com.example.isyncpos.entity.Products;
import com.example.isyncpos.entity.Safekeeping;
import com.example.isyncpos.entity.SafekeepingDenomination;
import com.example.isyncpos.entity.SpotAudit;
import com.example.isyncpos.entity.SpotAuditDenomination;
import com.example.isyncpos.entity.SubCategories;
import com.example.isyncpos.entity.Transactions;
import com.example.isyncpos.fragment.Menu;
import com.example.isyncpos.fragment.Navigation;
import com.example.isyncpos.payload.BranchAuditTrailsSyncPayload;
import com.example.isyncpos.payload.BranchCashFundDenominationSyncPayload;
import com.example.isyncpos.payload.BranchCashFundSyncPayload;
import com.example.isyncpos.payload.BranchCutOffDepartmentsSyncPayload;
import com.example.isyncpos.payload.BranchCutOffDiscountsSyncPayload;
import com.example.isyncpos.payload.BranchCutOffPaymentsSyncPayload;
import com.example.isyncpos.payload.BranchCutOffProductsSyncPayload;
import com.example.isyncpos.payload.BranchCutOffSyncPayload;
import com.example.isyncpos.payload.BranchDiscountDetailsSyncPayload;
import com.example.isyncpos.payload.BranchDiscountDetailsTakeOrderSyncPayload;
import com.example.isyncpos.payload.BranchDiscountOtherInformationsSyncPayload;
import com.example.isyncpos.payload.BranchDiscountOtherInformationsTakeOrderSyncPayload;
import com.example.isyncpos.payload.BranchDiscountSyncPayload;
import com.example.isyncpos.payload.BranchDiscountTakeOrderSyncPayload;
import com.example.isyncpos.payload.BranchEndOfDayDepartmentsSyncPayload;
import com.example.isyncpos.payload.BranchEndOfDayDiscountsSyncPayload;
import com.example.isyncpos.payload.BranchEndOfDayPaymentsSyncPayload;
import com.example.isyncpos.payload.BranchEndOfDayProductsSyncPayload;
import com.example.isyncpos.payload.BranchEndOfDaySyncPayload;
import com.example.isyncpos.payload.BranchMachineDetailsSyncPayload;
import com.example.isyncpos.payload.BranchOfficialReceiptInformationSyncPayload;
import com.example.isyncpos.payload.BranchOrderSyncPayload;
import com.example.isyncpos.payload.BranchOrderTakeOrderSyncPayload;
import com.example.isyncpos.payload.BranchPaymentOtherInformationsSyncPayload;
import com.example.isyncpos.payload.BranchPaymentSyncPayload;
import com.example.isyncpos.payload.BranchSpotAuditDenominationSyncPayload;
import com.example.isyncpos.payload.BranchSpotAuditSyncPayload;
import com.example.isyncpos.payload.BranchTransactionSyncPayload;
import com.example.isyncpos.payload.BranchTransactionTakeOrderSyncPayload;
import com.example.isyncpos.payload.SafekeepingDenominationPayload;
import com.example.isyncpos.payload.SafekeepingPayload;
import com.example.isyncpos.response.entity.BranchAuditTrailsSyncResponse;
import com.example.isyncpos.response.entity.BranchCashFundDenominationSyncResponse;
import com.example.isyncpos.response.entity.BranchCashFundSyncResponse;
import com.example.isyncpos.response.entity.BranchCutOffDepartmentsSyncResponse;
import com.example.isyncpos.response.entity.BranchCutOffDiscountsSyncResponse;
import com.example.isyncpos.response.entity.BranchCutOffPaymentsSyncResponse;
import com.example.isyncpos.response.entity.BranchCutOffProductsSyncResponse;
import com.example.isyncpos.response.entity.BranchCutOffSyncResponse;
import com.example.isyncpos.response.entity.BranchDiscountDetailsSyncResponse;
import com.example.isyncpos.response.entity.BranchDiscountOtherInformationsSyncResponse;
import com.example.isyncpos.response.entity.BranchDiscountsSyncResponse;
import com.example.isyncpos.response.entity.BranchEndOfDayDepartmentsSyncResponse;
import com.example.isyncpos.response.entity.BranchEndOfDayDiscountsSyncResponse;
import com.example.isyncpos.response.entity.BranchEndOfDayPaymentsSyncResponse;
import com.example.isyncpos.response.entity.BranchEndOfDayProductsSyncResponse;
import com.example.isyncpos.response.entity.BranchEndOfDaySyncResponse;
import com.example.isyncpos.response.entity.BranchMachineDetailsSyncResponse;
import com.example.isyncpos.response.entity.BranchOfficialReceiptInformationSyncResponse;
import com.example.isyncpos.response.entity.BranchOrderSyncResponse;
import com.example.isyncpos.response.entity.BranchPaymentOtherInformationsSyncResponse;
import com.example.isyncpos.response.entity.BranchPaymentSyncResponse;
import com.example.isyncpos.response.entity.BranchSafekeepingDenominationSyncResponse;
import com.example.isyncpos.response.entity.BranchSafekeepingSyncResponse;
import com.example.isyncpos.response.entity.BranchSpotAuditDenominationSyncResponse;
import com.example.isyncpos.response.entity.BranchSpotAuditSyncResponse;
import com.example.isyncpos.response.entity.BranchTransactionSyncResponse;
import com.example.isyncpos.response.entity.ErrorBody;
import com.example.isyncpos.viewmodel.AuditTrailsViewModel;
import com.example.isyncpos.viewmodel.CashFundDenominationViewModel;
import com.example.isyncpos.viewmodel.CashFundViewModel;
import com.example.isyncpos.viewmodel.CategoriesViewModel;
import com.example.isyncpos.viewmodel.CutOffDepartmentsViewModel;
import com.example.isyncpos.viewmodel.CutOffDiscountsViewModel;
import com.example.isyncpos.viewmodel.CutOffPaymentsViewModel;
import com.example.isyncpos.viewmodel.CutOffProductsViewModel;
import com.example.isyncpos.viewmodel.CutOffViewModel;
import com.example.isyncpos.viewmodel.DepartmentsViewModel;
import com.example.isyncpos.viewmodel.DiscountDetailsViewModel;
import com.example.isyncpos.viewmodel.DiscountOtherInformationsViewModel;
import com.example.isyncpos.viewmodel.DiscountTypeDepartmentsViewModel;
import com.example.isyncpos.viewmodel.DiscountsViewModel;
import com.example.isyncpos.viewmodel.EndOfDayDepartmentsViewModel;
import com.example.isyncpos.viewmodel.EndOfDayDiscountsViewModel;
import com.example.isyncpos.viewmodel.EndOfDayPaymentsViewModel;
import com.example.isyncpos.viewmodel.EndOfDayProductsViewModel;
import com.example.isyncpos.viewmodel.EndOfDayViewModel;
import com.example.isyncpos.viewmodel.MachineDetailsViewModel;
import com.example.isyncpos.viewmodel.OfficialReceiptInformationViewModel;
import com.example.isyncpos.viewmodel.OrdersViewModel;
import com.example.isyncpos.viewmodel.PaymentOtherInformationsViewModel;
import com.example.isyncpos.viewmodel.PaymentTypesViewModel;
import com.example.isyncpos.viewmodel.PaymentsViewModel;
import com.example.isyncpos.viewmodel.PayoutsViewModel;
import com.example.isyncpos.viewmodel.PermissionsViewModel;
import com.example.isyncpos.viewmodel.PrinterSetupDevicesViewModel;
import com.example.isyncpos.viewmodel.SafekeepingDenominationViewModel;
import com.example.isyncpos.viewmodel.SafekeepingViewModel;
import com.example.isyncpos.viewmodel.SpotAuditDenominationViewModel;
import com.example.isyncpos.viewmodel.SpotAuditViewModel;
import com.example.isyncpos.viewmodel.SubCategoriesViewModel;
import com.example.isyncpos.viewmodel.TransactionsViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class POSProcess {

    interface RequestAPI {
        @Headers({
            "Content-Type: application/json",
            "accept: application/json"
        })
        @POST("/api/branch-transactions")
        Call<BranchTransactionSyncResponse> BranchTransactionSync(@Header("Authorization") String authorization, @Body BranchTransactionSyncPayload branchTransactionSyncPayload, @Query("device_id") int deviceId);

        @Headers({
            "Content-Type: application/json",
            "accept: application/json"
        })
        @POST("/api/branch-orders")
        Call<BranchOrderSyncResponse> BranchOrderSync(@Header("Authorization") String authorization, @Body BranchOrderSyncPayload branchOrderSyncPayload, @Query("device_id") int deviceId);

        @Headers({
            "Content-Type: application/json",
            "accept: application/json"
        })
        @POST("/api/branch-payments")
        Call<BranchPaymentSyncResponse> BranchPaymentSync(@Header("Authorization") String authorization, @Body BranchPaymentSyncPayload branchPaymentSyncPayload, @Query("device_id") int deviceId);

        @Headers({
            "Content-Type: application/json",
            "accept: application/json"
        })
        @PUT("/api/machines/{machineId}")
        Call<BranchMachineDetailsSyncResponse> BranchMachineDetailsSync(@Header("Authorization") String authorization, @Path("machineId") int machineId, @Body BranchMachineDetailsSyncPayload branchMachineDetailsSyncPayload);

        @Headers({
            "Content-Type: application/json",
            "accept: application/json"
        })
        @POST("/api/discounts")
        Call<BranchDiscountsSyncResponse> BranchDiscountSync(@Header("Authorization") String authorization, @Body BranchDiscountSyncPayload branchDiscountSyncPayload, @Query("device_id") int deviceId);

        @Headers({
            "Content-Type: application/json",
            "accept: application/json"
        })
        @POST("/api/discount-details")
        Call<BranchDiscountDetailsSyncResponse> BranchDiscountDetailsSync(@Header("Authorization") String authorization, @Body BranchDiscountDetailsSyncPayload branchDiscountDetailsSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/cut-offs")
        Call<BranchCutOffSyncResponse> BranchCutOffSync(@Header("Authorization") String authorization, @Body BranchCutOffSyncPayload branchCutOffSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/end-of-days")
        Call<BranchEndOfDaySyncResponse> BranchEndOfDaySync(@Header("Authorization") String authorization, @Body BranchEndOfDaySyncPayload branchEndOfDaySyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/safekeeping")
        Call<BranchSafekeepingSyncResponse> BranchSafekeepingSync(@Header("Authorization") String authorization, @Body SafekeepingPayload safekeepingPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/safekeeping-denominations")
        Call<BranchSafekeepingDenominationSyncResponse> BranchSafekeepingDenominationsSync(@Header("Authorization") String authorization, @Body SafekeepingDenominationPayload safekeepingDenominationPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/payment-other-informations")
        Call<BranchPaymentOtherInformationsSyncResponse> BranchPaymentOtherInformationSync(@Header("Authorization") String authorization, @Body BranchPaymentOtherInformationsSyncPayload branchPaymentOtherInformationsSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/discount-other-informations")
        Call<BranchDiscountOtherInformationsSyncResponse> BranchDiscountOtherInformationSync(@Header("Authorization") String authorization, @Body BranchDiscountOtherInformationsSyncPayload branchDiscountOtherInformationsSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/cut-off-departments")
        Call<BranchCutOffDepartmentsSyncResponse> BranchCutOffDepartmentsSync(@Header("Authorization") String authorization, @Body BranchCutOffDepartmentsSyncPayload branchCutOffDepartmentsSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/cut-off-discounts")
        Call<BranchCutOffDiscountsSyncResponse> BranchCutOffDiscountsSync(@Header("Authorization") String authorization, @Body BranchCutOffDiscountsSyncPayload branchCutOffDiscountsSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/cut-off-payments")
        Call<BranchCutOffPaymentsSyncResponse> BranchCutOffPaymentsSync(@Header("Authorization") String authorization, @Body BranchCutOffPaymentsSyncPayload branchCutOffPaymentsSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/end-of-day-departments")
        Call<BranchEndOfDayDepartmentsSyncResponse> BranchEndOfDayDepartmentsSync(@Header("Authorization") String authorization, @Body BranchEndOfDayDepartmentsSyncPayload branchEndOfDayDepartmentsSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/end-of-day-discounts")
        Call<BranchEndOfDayDiscountsSyncResponse> BranchEndOfDayDiscountsSync(@Header("Authorization") String authorization, @Body BranchEndOfDayDiscountsSyncPayload branchEndOfDayDiscountsSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/end-of-day-payments")
        Call<BranchEndOfDayPaymentsSyncResponse> BranchEndOfDayPaymentsSync(@Header("Authorization") String authorization, @Body BranchEndOfDayPaymentsSyncPayload branchEndOfDayPaymentsSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/cash-funds")
        Call<BranchCashFundSyncResponse> BranchCashFundsSync(@Header("Authorization") String authorization, @Body BranchCashFundSyncPayload branchCashFundSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/cash-fund-denominations")
        Call<BranchCashFundDenominationSyncResponse> BranchCashFundDenominationsSync(@Header("Authorization") String authorization, @Body BranchCashFundDenominationSyncPayload branchCashFundDenominationSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/audit-trails")
        Call<BranchAuditTrailsSyncResponse> BranchAuditTrailsSync(@Header("Authorization") String authorization, @Body BranchAuditTrailsSyncPayload branchAuditTrailsSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/official-receipt-informations")
        Call<BranchOfficialReceiptInformationSyncResponse> BranchOfficialReceiptInformationSync(@Header("Authorization") String authorization, @Body BranchOfficialReceiptInformationSyncPayload branchOfficialReceiptInformationSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/spot-audits")
        Call<BranchSpotAuditSyncResponse> BranchSpotAuditSync(@Header("Authorization") String authorization, @Body BranchSpotAuditSyncPayload branchSpotAuditSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/spot-audit-denominations")
        Call<BranchSpotAuditDenominationSyncResponse> BranchSpotAuditDenominationSync(@Header("Authorization") String authorization, @Body BranchSpotAuditDenominationSyncPayload branchSpotAuditDenominationSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/cut-off-products")
        Call<BranchCutOffProductsSyncResponse> BranchCutOffProductsSync(@Header("Authorization") String authorization, @Body BranchCutOffProductsSyncPayload branchCutOffProductsSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/end-of-day-products")
        Call<BranchEndOfDayProductsSyncResponse> BranchEndOfDayProductsSync(@Header("Authorization") String authorization, @Body BranchEndOfDayProductsSyncPayload branchEndOfDayProductsSyncPayload, @Query("device_id") int deviceId);

        //Take Order
        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/branch-take-order-transactions")
        Call<BranchTransactionSyncResponse> BranchTakeOrderTransactionSync(@Header("Authorization") String authorization, @Body BranchTransactionTakeOrderSyncPayload branchTransactionTakeOrderSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/branch-take-order-orders")
        Call<BranchOrderSyncResponse> BranchTakeOrderOrdersSync(@Header("Authorization") String authorization, @Body BranchOrderTakeOrderSyncPayload branchOrderTakeOrderSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/take-order-discounts")
        Call<BranchDiscountsSyncResponse> BranchTakeOrderDiscountsSync(@Header("Authorization") String authorization, @Body BranchDiscountTakeOrderSyncPayload branchDiscountTakeOrderSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/take-order-discount-details")
        Call<BranchDiscountDetailsSyncResponse> BranchTakeOrderDiscountDetailsSync(@Header("Authorization") String authorization, @Body BranchDiscountDetailsTakeOrderSyncPayload branchDiscountDetailsTakeOrderSyncPayload, @Query("device_id") int deviceId);

        @Headers({
                "Content-Type: application/json",
                "accept: application/json"
        })
        @POST("/api/take-order-discount-other-informations")
        Call<BranchDiscountOtherInformationsSyncResponse> BranchTakeOrderDiscountOtherInformationSync(@Header("Authorization") String authorization, @Body BranchDiscountOtherInformationsTakeOrderSyncPayload branchDiscountOtherInformationsTakeOrderSyncPayload, @Query("device_id") int deviceId);

    }

    private TransactionsViewModel transactionsViewModel;
    private OrdersViewModel ordersViewModel;
    private DepartmentsViewModel departmentsViewModel;
    private CategoriesViewModel categoriesViewModel;
    private SubCategoriesViewModel subCategoriesViewModel;
    private AuditTrailsViewModel auditTrailsViewModel;
    private PaymentsViewModel paymentsViewModel;
    private MachineDetailsViewModel machineDetailsViewModel;
    private PrinterSetupDevicesViewModel printerSetupDevicesViewModel;
    private DiscountsViewModel discountsViewModel;
    private DiscountDetailsViewModel discountDetailsViewModel;
    private CutOffViewModel cutOffViewModel;
    private EndOfDayViewModel endOfDayViewModel;
    private PayoutsViewModel payoutsViewModel;
    private SafekeepingViewModel safekeepingViewModel;
    private SafekeepingDenominationViewModel safekeepingDenominationViewModel;
    private CutOffDepartmentsViewModel cutOffDepartmentsViewModel;
    private CutOffDiscountsViewModel cutOffDiscountsViewModel;
    private CutOffPaymentsViewModel cutOffPaymentsViewModel;
    private EndOfDayDepartmentsViewModel endOfDayDepartmentsViewModel;
    private EndOfDayDiscountsViewModel endOfDayDiscountsViewModel;
    private EndOfDayPaymentsViewModel endOfDayPaymentsViewModel;
    private CashFundViewModel cashFundViewModel;
    private CashFundDenominationViewModel cashFundDenominationViewModel;
    private PaymentOtherInformationsViewModel paymentOtherInformationsViewModel;
    private DiscountOtherInformationsViewModel discountOtherInformationsViewModel;
    private SpotAuditViewModel spotAuditViewModel;
    private SpotAuditDenominationViewModel spotAuditDenominationViewModel;
    private DiscountTypeDepartmentsViewModel discountTypeDepartmentsViewModel;
    private CutOffProductsViewModel cutOffProductsViewModel;
    private EndOfDayProductsViewModel endOfDayProductsViewModel;
    private PermissionsViewModel permissionsViewModel;
    private OfficialReceiptInformationViewModel officialReceiptInformationViewModel;
    private PaymentTypesViewModel paymentTypesViewModel;
    private Generate generate;
    private Dates date;
    private Compute compute;
    private Gson gson;
    private Functions functions;
    private Menu menu;
    private Navigation navigation;

    public POSProcess(
        TransactionsViewModel transactionsViewModel,
        OrdersViewModel ordersViewModel,
        DepartmentsViewModel departmentsViewModel,
        CategoriesViewModel categoriesViewModel,
        SubCategoriesViewModel subCategoriesViewModel,
        AuditTrailsViewModel auditTrailsViewModel,
        PaymentsViewModel paymentsViewModel,
        MachineDetailsViewModel machineDetailsViewModel,
        PrinterSetupDevicesViewModel printerSetupDevicesViewModel,
        DiscountsViewModel discountsViewModel,
        DiscountDetailsViewModel discountDetailsViewModel,
        CutOffViewModel cutOffViewModel,
        EndOfDayViewModel endOfDayViewModel,
        PayoutsViewModel payoutsViewModel,
        SafekeepingViewModel safekeepingViewModel,
        CutOffDepartmentsViewModel cutOffDepartmentsViewModel,
        CutOffDiscountsViewModel cutOffDiscountsViewModel,
        CutOffPaymentsViewModel cutOffPaymentsViewModel,
        SafekeepingDenominationViewModel safekeepingDenominationViewModel,
        EndOfDayDepartmentsViewModel endOfDayDepartmentsViewModel,
        EndOfDayDiscountsViewModel endOfDayDiscountsViewModel,
        EndOfDayPaymentsViewModel endOfDayPaymentsViewModel,
        CashFundViewModel cashFundViewModel,
        CashFundDenominationViewModel cashFundDenominationViewModel,
        PaymentOtherInformationsViewModel paymentOtherInformationsViewModel,
        DiscountOtherInformationsViewModel discountOtherInformationsViewModel,
        SpotAuditViewModel spotAuditViewModel,
        SpotAuditDenominationViewModel spotAuditDenominationViewModel,
        DiscountTypeDepartmentsViewModel discountTypeDepartmentsViewModel,
        Menu menu,
        CutOffProductsViewModel cutOffProductsViewModel,
        EndOfDayProductsViewModel endOfDayProductsViewModel,
        PermissionsViewModel permissionsViewModel,
        Navigation navigation,
        OfficialReceiptInformationViewModel officialReceiptInformationViewModel,
        PaymentTypesViewModel paymentTypesViewModel
    ) {
        this.transactionsViewModel = transactionsViewModel;
        this.ordersViewModel = ordersViewModel;
        this.departmentsViewModel = departmentsViewModel;
        this.categoriesViewModel = categoriesViewModel;
        this.subCategoriesViewModel = subCategoriesViewModel;
        this.auditTrailsViewModel = auditTrailsViewModel;
        this.paymentsViewModel = paymentsViewModel;
        this.machineDetailsViewModel = machineDetailsViewModel;
        this.printerSetupDevicesViewModel = printerSetupDevicesViewModel;
        this.discountsViewModel = discountsViewModel;
        this.discountDetailsViewModel = discountDetailsViewModel;
        this.cutOffViewModel = cutOffViewModel;
        this.endOfDayViewModel = endOfDayViewModel;
        this.payoutsViewModel = payoutsViewModel;
        this.safekeepingViewModel = safekeepingViewModel;
        this.cutOffDepartmentsViewModel = cutOffDepartmentsViewModel;
        this.cutOffDiscountsViewModel = cutOffDiscountsViewModel;
        this.cutOffPaymentsViewModel = cutOffPaymentsViewModel;
        this.safekeepingDenominationViewModel = safekeepingDenominationViewModel;
        this.endOfDayDepartmentsViewModel = endOfDayDepartmentsViewModel;
        this.endOfDayDiscountsViewModel = endOfDayDiscountsViewModel;
        this.endOfDayPaymentsViewModel = endOfDayPaymentsViewModel;
        this.cashFundViewModel = cashFundViewModel;
        this.cashFundDenominationViewModel = cashFundDenominationViewModel;
        this.paymentOtherInformationsViewModel = paymentOtherInformationsViewModel;
        this.discountOtherInformationsViewModel = discountOtherInformationsViewModel;
        this.spotAuditViewModel = spotAuditViewModel;
        this.spotAuditDenominationViewModel = spotAuditDenominationViewModel;
        this.discountTypeDepartmentsViewModel = discountTypeDepartmentsViewModel;
        this.cutOffProductsViewModel = cutOffProductsViewModel;
        this.endOfDayProductsViewModel = endOfDayProductsViewModel;
        this.permissionsViewModel = permissionsViewModel;
        this.officialReceiptInformationViewModel = officialReceiptInformationViewModel;
        this.paymentTypesViewModel = paymentTypesViewModel;
        generate = new Generate();
        date = new Dates();
        compute = new Compute();
        gson = new Gson();
        functions = new Functions();
        this.menu = menu;
        this.navigation = navigation;
    }

    public Long createTransaction(Products products, double qty){
        try {
            return transactionsViewModel.insert(new com.example.isyncpos.entity.Transactions(
                    POSApplication.getInstance().getMachineDetails().getCoreId(),
                    generate.controlNumber(),
                    "",
                    generate.toFourDecimal(compute.gross(qty, products.getPrice())),
                    generate.toFourDecimal(compute.total(0, qty, products.getPrice())),
                    generate.toFourDecimal(compute.vatableSales(qty * products.getPrice())),
                    0,
                    generate.toFourDecimal(compute.vatAmount(compute.vatableSales(qty * products.getPrice()))),
                    0,
                    0,
                    0,
                    0,
                    0,
                    "TAKEOUT",
                    POSApplication.getInstance().getUserAuthentication().getId(),
                    POSApplication.getInstance().getUserAuthentication().getName(),
                    0,
                    "",
                    0,
                    generate.toFourDecimal(compute.total(0, qty, products.getCost())),
                    0,
                    "1",
                    0,
                    0,
                    "",
                    "",
                    0,
                    0,
                    "",
                    "",
                    0,
                    "",
                    0,
                    0,
                    0,
                    "",
                    0,
                    0,
                    "",
                    POSApplication.getInstance().getBranch().getCoreId(),
                   "",
                    0,
                    date.now(),
                    0,
                    0,
                    0,
                    0,
                    "",
                    0,
                    0,
                    "",
                    "",
                    POSApplication.getInstance().getCompany().getCoreId(),
                    0,
                    ""
            ));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveTransactionOrder(Products products, double qty, String serialNumber){
        try {
            Orders orders = ordersViewModel.fetchTransactionOrder(POSApplication.getInstance().getCurrentTransaction(), products.getCoreId());
            if(orders == null){
                Departments departments = departmentsViewModel.fetchDepartment(products.getDepartmentId());
                Categories categories = categoriesViewModel.fetchCategory(products.getCategoryId());
                SubCategories subCategories = subCategoriesViewModel.fetchSubCategory(products.getSubCategoryId());
                ordersViewModel.insert(new Orders(
                        POSApplication.getInstance().getMachineDetails().getCoreId(),
                        POSApplication.getInstance().getCurrentTransaction(),
                        products.getCoreId(),
                        products.getCode(),
                        products.getName(),
                        products.getDescription(),
                        products.getAbbreviation(),
                        products.getCost(),
                        qty,
                        products.getPrice(),
                        products.getPrice(),
                        generate.toFourDecimal(compute.gross(qty, products.getPrice())),
                        generate.toFourDecimal(compute.total(0, qty, products.getPrice())),
                        generate.toFourDecimal(compute.total(0, qty, products.getCost())),
                        products.getIsVatExempt() == 0 ? 1 : 0,
                        products.getIsVatExempt() == 0 ? generate.toFourDecimal(compute.vatAmount(compute.vatableSales(qty * products.getPrice()))) : 0,
                        products.getIsVatExempt() == 0 ? generate.toFourDecimal(compute.vatableSales(qty * products.getPrice())) : 0,
                        products.getIsVatExempt() == 1 ? generate.toFourDecimal(qty * products.getPrice()) : 0,
                        0,
                        0,
                        products.getDepartmentId(),
                        departments != null ? departments.getName() : null,
                        products.getCategoryId(),
                        categories != null ? categories.getName() : null,
                        products.getSubCategoryId(),
                        subCategories != null ? subCategories.getName() : null,
                        products.getUnitId(),
                        "",
                        products.getIsDiscountExempt(),
                        products.getIsOpenPrice(),
                        products.getWithSerial(),
                        0,
                        "",
                        "",
                        0,
                        0,
                        "",
                        products.getMinAmountSold(),
                        0,
                        0,
                        0,
                        "",
                        POSApplication.getInstance().getBranch().getCoreId(),
                        1,
                        0,
                        date.now(),
                        0,
                        "",
                        0,
                        "",
                        0,
                        serialNumber,
                        0,
                        0,
                        POSApplication.getInstance().getCompany().getCoreId(),
                        0
                ));
                POSApplication.getInstance().setLastUpdateProductId(products.getCoreId());
            }
            else{
                if(orders.getWithSerial() == 1){
                    Departments departments = departmentsViewModel.fetchDepartment(products.getDepartmentId());
                    Categories categories = categoriesViewModel.fetchCategory(products.getCategoryId());
                    SubCategories subCategories = subCategoriesViewModel.fetchSubCategory(products.getSubCategoryId());
                    ordersViewModel.insert(new Orders(
                            POSApplication.getInstance().getMachineDetails().getCoreId(),
                            POSApplication.getInstance().getCurrentTransaction(),
                            products.getCoreId(),
                            products.getCode(),
                            products.getName(),
                            products.getDescription(),
                            products.getAbbreviation(),
                            products.getCost(),
                            qty,
                            products.getPrice(),
                            products.getPrice(),
                            generate.toFourDecimal(compute.gross(qty, products.getPrice())),
                            generate.toFourDecimal(compute.total(0, qty, products.getPrice())),
                            generate.toFourDecimal(compute.total(0, qty, products.getCost())),
                            products.getIsVatExempt() == 0 ? 1 : 0,
                            products.getIsVatExempt() == 0 ? generate.toFourDecimal(compute.vatAmount(compute.vatableSales(qty * products.getPrice()))) : 0,
                            products.getIsVatExempt() == 0 ? generate.toFourDecimal(compute.vatableSales(qty * products.getPrice())) : 0,
                            products.getIsVatExempt() == 1 ? generate.toFourDecimal(qty * products.getPrice()) : 0,
                            0,
                            0,
                            products.getDepartmentId(),
                            departments != null ? departments.getName() : null,
                            products.getCategoryId(),
                            categories != null ? categories.getName() : null,
                            products.getSubCategoryId(),
                            subCategories != null ? subCategories.getName() : null,
                            products.getUnitId(),
                            "",
                            products.getIsDiscountExempt(),
                            products.getIsOpenPrice(),
                            products.getWithSerial(),
                            0,
                            "",
                            "",
                            0,
                            0,
                            "",
                            products.getMinAmountSold(),
                            0,
                            0,
                            0,
                            "",
                            POSApplication.getInstance().getBranch().getCoreId(),
                            1,
                            0,
                            date.now(),
                            0,
                            "",
                            0,
                            "",
                            0,
                            serialNumber,
                            0,
                            0,
                            POSApplication.getInstance().getCompany().getCoreId(),
                            0
                    ));
                }
                else{
                    orders.setQty(orders.getQty() + qty);
                    orders.setGross(generate.toFourDecimal(compute.gross(orders.getQty(), products.getPrice())));
                    orders.setTotal(generate.toFourDecimal(compute.total(orders.getTotal(), qty, products.getPrice())));
                    if(orders.getIsVatable() == 1){
                        orders.setVatableSales(generate.toFourDecimal(compute.vatableSales(orders.getGross())));
                        orders.setVatAmount(generate.toFourDecimal(compute.vatAmount(compute.vatableSales(orders.getGross()))));
                        orders.setVatExemptSales(0);
                    }
                    else{
                        orders.setVatExemptSales(generate.toFourDecimal(qty * products.getPrice()));
                        orders.setVatableSales(0);
                        orders.setVatAmount(0);
                    }
                    orders.setTotalCost(generate.toFourDecimal(compute.total(orders.getTotalCost(), qty, products.getCost())));
                    orders.setIsSentToServer(0);
                    POSApplication.getInstance().setLastUpdateProductId(products.getCoreId());
                    ordersViewModel.update(orders);
                }
            }
            POSApplication.getInstance().setTotalQuantity(1);
            menu.applyMenuQuantity("1");
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void backoutTransaction(int transactionId){
        try {
            Transactions transactions = transactionsViewModel.fetchTransaction(transactionId);
            List<Orders> orders = ordersViewModel.fetchTransactionOrdersWithVoid(transactionId);
            transactions.setIsBackout(1);
            transactions.setIsBackoutId(POSApplication.getInstance().getUserAuthentication().getId());
            transactions.setBackoutBy(POSApplication.getInstance().getUserAuthentication().getName());
            transactions.setIsSentToServer(0);
            transactions.setBackoutAt(date.now());
            for(Orders item : orders){
                item.setIsBackout(1);
                item.setIsBackoutId(POSApplication.getInstance().getUserAuthentication().getId());
                item.setBackoutBy(POSApplication.getInstance().getUserAuthentication().getName());
                item.setIsSentToServer(0);
                ordersViewModel.update(item);
            }
            transactionsViewModel.backoutTransaction(
                transactions.getId(),
                transactions.getIsBackoutId(),
                transactions.getBackoutBy(),
                transactions.getBackoutAt()
            );
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Transactions getCurrentTransaction(){
        try {
            return transactionsViewModel.fetchTransaction(POSApplication.getInstance().getCurrentTransaction());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Transactions getCurrentARTransaction(){
        try {
            return transactionsViewModel.fetchTransaction(POSApplication.getInstance().getCurrentARTransactionId());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Orders> getCurrentTransactionOrders(){
        try {
            return ordersViewModel.fetchTransactionOrders(POSApplication.getInstance().getCurrentTransaction());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Discounts> getCurrentTransactionDiscounts(){
        try {
            return discountsViewModel.fetchDiscountsByTransactionId(POSApplication.getInstance().getCurrentTransaction());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Transactions recomputeCurrentTransaction(){
        try {
            double grossSales = 0, netSales = 0, vatableSales = 0, vatExemptSales = 0, vatAmount = 0, serviceCharge = 0, totalCost = 0, discountAmount = 0, voidAmount = 0, tenderAmount = 0, changeAmount = 0, vatExpense = 0, totalQuantity = 0, totalReturnAmount = 0, totalCashAmount = 0, zeroRatedAmount = 0;
            double othersPayment = 0;
            int totalVoidQty = 0;
            Transactions transactions = transactionsViewModel.fetchTransaction(POSApplication.getInstance().getCurrentTransaction());
            List<Orders> orderList = ordersViewModel.fetchTransactionOrdersWithVoid(POSApplication.getInstance().getCurrentTransaction());
            Double totalCashPayment = paymentsViewModel.fetchTransactionPaymentCashSum(POSApplication.getInstance().getCurrentTransaction());
            Double totalOthersPayment = paymentsViewModel.fetchTransactionOthersSum(POSApplication.getInstance().getCurrentTransaction());
            if(totalCashPayment != null){
                tenderAmount = totalCashPayment;
            }
            if(totalOthersPayment != null){
                othersPayment = totalOthersPayment;
            }
            for(Orders orders : orderList){
                if(orders.getIsVoid() != 1){
                    grossSales += orders.getGross();
                    discountAmount += orders.getIsReturn() != 1 ? orders.getDiscountAmount() : 0;
                    netSales += orders.getTotal();
                    if(transactions.getNetSales() == 0){
                        vatableSales += 0;
                        vatExemptSales += 0;
                        vatAmount += 0;
                    }
                    else {
                        vatableSales += orders.getVatableSales();
                        vatExemptSales += orders.getVatExemptSales();
                        vatAmount += orders.getVatAmount();
                    }
                    serviceCharge += orders.getIsReturn() != 1 ? 0 : 0;
                    totalCost += orders.getIsReturn() != 1 ? orders.getTotalCost() : 0;
                    vatExpense += orders.getIsReturn() != 1 ? orders.getVatExpense() : 0;
                    totalQuantity += orders.getIsReturn() != 1 ? orders.getQty() : 0;
                    if(orders.getIsReturn() == 1) totalReturnAmount += orders.getTotal();
                    if(orders.getIsZeroRated() == 1) zeroRatedAmount += orders.getZeroRatedAmount();
                }
                else{
                    totalVoidQty += orders.getIsReturn() != 1 ? orders.getQty() : 0;
                    voidAmount += orders.getIsReturn() != 1 ? orders.getTotal() : 0;
                }
            }
            double currentNetSales = netSales < 0 ? 0 : netSales;
            if(tenderAmount != 0){
                changeAmount = tenderAmount - (generate.toTwoDecimal(currentNetSales) - othersPayment);
            }
            totalCashAmount = tenderAmount - changeAmount;
            //Change Logic Here
            transactions.setChange(generate.toFourDecimal(changeAmount < 0 ? 0.00 : changeAmount));
            transactions.setGrossSales(generate.toFourDecimal(grossSales));
            transactions.setNetSales(generate.toFourDecimal(netSales));
            transactions.setDiscountAmount(generate.toFourDecimal(discountAmount));
            transactions.setVatableSales(generate.toFourDecimal(vatableSales));
            transactions.setVatExemptSales(generate.toFourDecimal(vatExemptSales));
            transactions.setVatAmount(generate.toFourDecimal(vatAmount));
            transactions.setServiceCharge(generate.toFourDecimal(serviceCharge));
            transactions.setTotalUnitCost(generate.toFourDecimal(totalCost));
            transactions.setTotalVoidAmount(generate.toFourDecimal(voidAmount));
            transactions.setTenderAmount(generate.toFourDecimal(tenderAmount));
            transactions.setVatExpense(generate.toFourDecimal(vatExpense));
            transactions.setTotalQuantity(totalQuantity);
            transactions.setTotalVoidQty(totalVoidQty);
            transactions.setTotalReturnAmount(generate.toFourDecimal(totalReturnAmount));
            transactions.setIsSentToServer(0);
            transactions.setTotalCashAmount(generate.toFourDecimal(totalCashAmount));
            transactions.setTotalZeroRatedAmount(generate.toFourDecimal(zeroRatedAmount));
            transactionsViewModel.updateRecomputeTransaction(
                    transactions.getId(),
                    transactions.getChange(),
                    transactions.getGrossSales(),
                    transactions.getNetSales(),
                    transactions.getDiscountAmount(),
                    transactions.getVatableSales(),
                    transactions.getVatExemptSales(),
                    transactions.getVatAmount(),
                    transactions.getServiceCharge(),
                    transactions.getTotalUnitCost(),
                    transactions.getTotalVoidAmount(),
                    transactions.getTenderAmount(),
                    transactions.getVatExpense(),
                    transactions.getTotalQuantity(),
                    transactions.getTotalVoidQty(),
                    transactions.getTotalReturnAmount(),
                    transactions.getTotalCashAmount(),
                    transactions.getTotalZeroRatedAmount()
            );
            return transactions;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Transactions recomputeCurrentARTransaction(){
        try {
            double netSales = 0, tenderAmount = 0, changeAmount = 0, totalCashAmount = 0;
            double othersPayment = 0;
            Transactions transactions = transactionsViewModel.fetchTransaction(POSApplication.getInstance().getCurrentARTransactionId());
            List<Orders> orderList = ordersViewModel.fetchTransactionOrdersWithVoid(POSApplication.getInstance().getCurrentARTransactionId());
            Double totalCashPayment = paymentsViewModel.fetchTransactionPaymentCashSum(POSApplication.getInstance().getCurrentARTransactionId());
            Double totalOthersPayment = paymentsViewModel.fetchARTransactionOthersSum(POSApplication.getInstance().getCurrentARTransactionId());
            if(totalCashPayment != null){
                tenderAmount = totalCashPayment;
            }
            if(totalOthersPayment != null){
                othersPayment = totalOthersPayment;
            }
            for(Orders orders : orderList){
                if(orders.getIsVoid() != 1){
                    netSales += orders.getTotal();
                }
            }
            double currentNetSales = netSales < 0 ? 0 : netSales;
            if(tenderAmount != 0){
                changeAmount = tenderAmount - (generate.toTwoDecimal(currentNetSales) - othersPayment);
            }
            totalCashAmount = tenderAmount - changeAmount;
            //Change Logic Here
            transactions.setChange(generate.toFourDecimal(changeAmount < 0 ? 0.00 : changeAmount));
            transactions.setTenderAmount(generate.toFourDecimal(tenderAmount));
            transactions.setIsSentToServer(0);
            transactions.setTotalCashAmount(generate.toFourDecimal(totalCashAmount));
            transactionsViewModel.updateRecomputeARTransaction(
                    transactions.getId(),
                    transactions.getChange(),
                    transactions.getTenderAmount(),
                    transactions.getTotalCashAmount()
            );
            return transactions;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkCurrentTransactionIsExisting(){
        try {
            Transactions transactions = transactionsViewModel.fetchTransaction(POSApplication.getInstance().getCurrentTransaction());
            if(transactions != null){
                return true;
            }
            else{
                return false;
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkForEndOfDayProcess(){
        try {
            List<String> today = cutOffViewModel.checkForEndOfDayProcess();
            if(today.size() != 0){
                return true;
            }
            else{
                return false;
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public String getForEndOfDayProcessDate(){
        try {
            List<String> today = cutOffViewModel.checkForEndOfDayProcess();
            for(String item : today){
                return item;
            }
            return null;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Payments> getCurrentTransactionPayments(){
        try {
            return paymentsViewModel.fetchTransactionPayments(POSApplication.getInstance().getCurrentTransaction());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Long saveTransactionPayment(Payments payments){
        try {
            return paymentsViewModel.insert(payments);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveTransactionPaymentOthers(PaymentOtherInformations paymentOtherInformations){
        paymentOtherInformationsViewModel.insert(paymentOtherInformations);
    }

    public boolean checkCurrentTransactionPayments(){
        try {
            Transactions transactions = transactionsViewModel.fetchTransaction(POSApplication.getInstance().getCurrentTransaction());
            Double totalPayment = paymentsViewModel.fetchTransactionPaymentsSum(POSApplication.getInstance().getCurrentTransaction());
            if(transactions != null){
                if(totalPayment != null){
                    if(totalPayment >= generate.toTwoDecimal(transactions.getNetSales())){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    if(transactions.getNetSales() <= 0){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }
            else{
                return false;
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void completeCurrentTransaction(Activity activity, int transactionId){
        try {
            int isReturn = 0;
            Transactions transactions = transactionsViewModel.fetchTransaction(transactionId);
            List<Orders> orders = ordersViewModel.fetchTransactionOrders(transactionId);
            List<Payments> payments = paymentsViewModel.fetchTransactionPayments(transactionId);
            List<Discounts> discounts = discountsViewModel.fetchDiscountsByTransactionId(transactionId);
            MachineDetails machineDetails = machineDetailsViewModel.fetchMachineDetails();
            //Save Audit Trail
            AuditTrail.getInstance().save(
                POSApplication.getInstance().getUserAuthentication().getId(),
                POSApplication.getInstance().getUserAuthentication().getName(),
                transactions.getId(),
                "TRANSACTION",
                "Complete transaction for control number of " + transactions.getControlNumber() + " with a sales invoice number " + generate.officialReceipt(machineDetails.getOrCounter() + 1),
                0,
                "",
                0,
                0
            );
            //Process The Complete Transaction
            transactions.setIsComplete(1);
            transactions.setCompletedAt(date.now());
            transactions.setCashierId(POSApplication.getInstance().getUserAuthentication().getId());
            transactions.setCashierName(POSApplication.getInstance().getUserAuthentication().getName());
            transactions.setIsSentToServer(0);
            for(Orders item : orders){
                if(item.getIsReturn() == 1 && isReturn == 0) isReturn = 1;
                item.setIsSentToServer(0);
                item.setIsCompleted(1);
                item.setCompletedAt(date.now());
                ordersViewModel.update(item);
            }
            if(isReturn != 0){
                if(transactions.getNetSales() < 0){
                    PaymentTypes paymentTypeCash = paymentTypesViewModel.fetchById(1);
                    paymentsViewModel.insert(new Payments(
                            POSApplication.getInstance().getMachineDetails().getCoreId(),
                            POSApplication.getInstance().getBranch().getCoreId(),
                            transactions.getId(),
                            paymentTypeCash.getCoreId(),
                            paymentTypeCash.getName(),
                            generate.toFourDecimal(transactions.getNetSales()),
                            0,
                            1,
                            0,
                            0,
                            date.now(),
                            0,
                            "",
                            0,
                            "",
                            "",
                            0,
                            POSApplication.getInstance().getCompany().getCoreId(),
                            0
                    ));
                    transactions.setTotalCashAmount(generate.toFourDecimal(transactions.getNetSales()));
                    transactions.setIsReturn(1);
                }
            }
            //Check If The Transaction Is AR Then Do Not Increment The OR Counter
            if(transactions.getIsAccountReceivable() != 1){
                machineDetails.setOrCounter(machineDetails.getOrCounter() + 1);
                machineDetails.setIsSentToServer(0);
                transactions.setReceiptNumber(generate.officialReceipt(machineDetails.getOrCounter()));
            }
            //Update Informations
            transactionsViewModel.completeTransaction(
                    transactions.getId(),
                    transactions.getCompletedAt(),
                    transactions.getCashierId(),
                    transactions.getCashierName(),
                    transactions.getReceiptNumber(),
                    transactions.getIsReturn(),
                    transactions.getTotalCashAmount()
            );
            machineDetailsViewModel.update(machineDetails);
            Printer.getInstance().print(activity, printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_OFFICIAL_RECEIPT), Printer.getInstance().officialReceipt(transactions, orders, payments, discounts));
            Writer.getInstance().writeTransaction(activity, Printer.getInstance().officialReceipt(transactions, orders, payments, discounts));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean completeCurrentARTransaction(Activity activity, int transactionId){
        try {
            Transactions transactions = transactionsViewModel.fetchTransaction(transactionId);
            Double totalPayment = paymentsViewModel.fetchARTransactionPaymentsSum(transactionId);
            //Check For Complete Balance Then Generate SI Number And Print The Receipt
            if(transactions.getNetSales() <= totalPayment){
                List<Orders> orders = ordersViewModel.fetchTransactionOrders(transactionId);
                List<Payments> payments = paymentsViewModel.fetchTransactionPayments(transactionId);
                List<Discounts> discounts = discountsViewModel.fetchDiscountsByTransactionId(transactionId);
                MachineDetails machineDetails = machineDetailsViewModel.fetchMachineDetails();
                //Save Audit Trail
                AuditTrail.getInstance().save(
                        POSApplication.getInstance().getUserAuthentication().getId(),
                        POSApplication.getInstance().getUserAuthentication().getName(),
                        transactions.getId(),
                        "TRANSACTION",
                        "Complete account receivable transaction for control number of " + transactions.getControlNumber() + " with a sales invoice number " + generate.officialReceipt(machineDetails.getOrCounter() + 1),
                        0,
                        "",
                        0,
                        0
                );
                //Process The Complete Account Receivable Transaction
                transactions.setCashierId(POSApplication.getInstance().getUserAuthentication().getId());
                transactions.setCashierName(POSApplication.getInstance().getUserAuthentication().getName());
                transactions.setIsSentToServer(0);
                transactions.setIsAccountReceivableRedeem(1);
                transactions.setAccountReceivableRedeemAt(date.now());
                //Create OR For Account Receivable
                machineDetails.setOrCounter(machineDetails.getOrCounter() + 1);
                machineDetails.setIsSentToServer(0);
                transactions.setReceiptNumber(generate.officialReceipt(machineDetails.getOrCounter()));
                //Update Informations
                transactionsViewModel.completeARTransaction(
                        transactions.getId(),
                        transactions.getCashierId(),
                        transactions.getCashierName(),
                        transactions.getIsAccountReceivableRedeem(),
                        transactions.getAccountReceivableRedeemAt(),
                        transactions.getReceiptNumber()
                );
                machineDetailsViewModel.update(machineDetails);
                Printer.getInstance().print(activity, printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_OFFICIAL_RECEIPT), Printer.getInstance().officialReceipt(transactions, orders, payments, discounts));
                Writer.getInstance().writeTransaction(activity, Printer.getInstance().officialReceipt(transactions, orders, payments, discounts));
                return true;
            }
            else{
                return false;
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Long saveTransactionDiscount(Discounts discounts){
        try {
            return discountsViewModel.insert(discounts);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTransactionDiscount(Discounts discounts){
        discountsViewModel.update(discounts);
    }

    public void saveTransactionDiscountDetails(DiscountDetails discountDetails){
        discountDetailsViewModel.insert(discountDetails);
    }

    public void recomputeCurrentTransactionDetailsDiscount(){
        try {
            List<Orders> orderList = ordersViewModel.fetchTransactionOrdersWithVoid(POSApplication.getInstance().getCurrentTransaction());
            List<DiscountDetails> discountDetailsList = discountDetailsViewModel.fetchDiscountDetailsByTransactionId(POSApplication.getInstance().getCurrentTransaction());
            for(Orders item : orderList){
                if(item.getIsVoid() != 1){
                    DiscountDetails discountDetails = Functions.getInstance().discountDetailsFindByOrderId(discountDetailsList, item.getId());
                    if(discountDetails != null){
                        if(discountDetails.getType().equals("amount")){
                            //Discount Amount
                            Long totalCountDiscountDepartment = discountTypeDepartmentsViewModel.fetchDiscountTypeDepartmentCount(discountDetails.getDiscountTypeId());
                            if(totalCountDiscountDepartment.intValue() == 0){
                                if(discountDetails.isVatExempt()){
                                    item.setVatableSales(generate.toFourDecimal(0));
                                    item.setVatAmount(generate.toFourDecimal(0));
                                    item.setDiscountAmount(generate.toFourDecimal(discountDetails.getDiscountAmount()));
                                    item.setVatExemptSales(generate.toFourDecimal(discountDetails.getVatExemptAmount()));
                                    item.setVatExpense(generate.toFourDecimal(discountDetails.getVatExpense()));
                                    item.setGross(generate.toFourDecimal(compute.gross(item.getQty(), item.getAmount())));
                                    item.setTotal(generate.toFourDecimal((compute.gross(item.getQty(), item.getAmount()) - discountDetails.getVatExpense()) - discountDetails.getDiscountAmount()));
                                } else if (discountDetails.isZeroRated()) {
                                    if(item.getIsVatable() == 1){
                                        item.setIsZeroRated(1);
                                        item.setVatableSales(generate.toFourDecimal(0));
                                        item.setVatAmount(generate.toFourDecimal(0));
                                        item.setVatExemptSales(0);
                                        item.setVatExpense(0);
                                        item.setDiscountAmount(0);
                                        item.setZeroRatedAmount(generate.toFourDecimal(compute.vatableSales(item.getQty() * item.getAmount())));
                                        item.setGross(generate.toFourDecimal(item.getGross() - compute.vatAmount(item.getZeroRatedAmount())));
                                        item.setTotal(generate.toFourDecimal(item.getTotal() - compute.vatAmount(item.getZeroRatedAmount())));
                                    }
                                    else{
                                        item.setIsZeroRated(0);
                                        item.setVatableSales(generate.toFourDecimal(0));
                                        item.setVatAmount(generate.toFourDecimal(0));
                                        item.setVatExemptSales(generate.toFourDecimal(item.getTotal()));
                                        item.setVatExpense(0);
                                        item.setDiscountAmount(0);
                                        item.setZeroRatedAmount(0);
                                        item.setGross(generate.toFourDecimal(item.getTotal()));
                                        item.setTotal(generate.toFourDecimal(item.getTotal()));
                                    }
                                } else{
                                    item.setTotal(generate.toFourDecimal(item.getGross() - discountDetails.getDiscountAmount()));
                                    item.setVatableSales(item.getIsVatable() == 1 ? generate.toFourDecimal(compute.vatableSales((item.getQty() * item.getAmount()) - discountDetails.getDiscountAmount())) : 0);
                                    item.setVatAmount(item.getIsVatable() == 1 ? generate.toFourDecimal(compute.vatAmount(compute.vatableSales((item.getQty() * item.getAmount()) - discountDetails.getDiscountAmount()))) : 0);
                                    item.setDiscountAmount(generate.toFourDecimal(discountDetails.getDiscountAmount()));
                                    item.setVatExemptSales(item.getIsVatable() == 0 ? generate.toFourDecimal(item.getTotal()) : 0);
                                    item.setVatExpense(0);
                                }
                            }
                            else{
                                DiscountTypeDepartments discountTypeDepartments = discountTypeDepartmentsViewModel.fetchByDepartmentDiscountId(item.getDepartmentId(), discountDetails.getDiscountTypeId());
                                if(discountTypeDepartments != null){
                                    if(discountDetails.isVatExempt()){
                                        item.setVatableSales(generate.toFourDecimal(0));
                                        item.setVatAmount(generate.toFourDecimal(0));
                                        item.setDiscountAmount(generate.toFourDecimal(discountDetails.getDiscountAmount()));
                                        item.setVatExemptSales(generate.toFourDecimal(discountDetails.getVatExemptAmount()));
                                        item.setVatExpense(generate.toFourDecimal(discountDetails.getVatExpense()));
                                        item.setGross(generate.toFourDecimal(compute.gross(item.getQty(), item.getAmount())));
                                        item.setTotal(generate.toFourDecimal((compute.gross(item.getQty(), item.getAmount()) - discountDetails.getVatExpense()) - discountDetails.getDiscountAmount()));
                                    } else if (discountDetails.isZeroRated()) {
                                        if(item.getIsVatable() == 1){
                                            item.setIsZeroRated(1);
                                            item.setVatableSales(generate.toFourDecimal(0));
                                            item.setVatAmount(generate.toFourDecimal(0));
                                            item.setVatExemptSales(0);
                                            item.setVatExpense(0);
                                            item.setDiscountAmount(0);
                                            item.setZeroRatedAmount(generate.toFourDecimal(compute.vatableSales(item.getQty() * item.getAmount())));
                                            item.setGross(generate.toFourDecimal(item.getGross() - compute.vatAmount(item.getZeroRatedAmount())));
                                            item.setTotal(generate.toFourDecimal(item.getTotal() - compute.vatAmount(item.getZeroRatedAmount())));
                                        }
                                        else{
                                            item.setIsZeroRated(0);
                                            item.setVatableSales(generate.toFourDecimal(0));
                                            item.setVatAmount(generate.toFourDecimal(0));
                                            item.setVatExemptSales(generate.toFourDecimal(item.getTotal()));
                                            item.setVatExpense(0);
                                            item.setDiscountAmount(0);
                                            item.setZeroRatedAmount(0);
                                            item.setGross(generate.toFourDecimal(item.getTotal()));
                                            item.setTotal(generate.toFourDecimal(item.getTotal()));
                                        }
                                    } else{
                                        item.setTotal(generate.toFourDecimal(item.getGross() - discountDetails.getDiscountAmount()));
                                        item.setVatableSales(item.getIsVatable() == 1 ? generate.toFourDecimal(compute.vatableSales((item.getQty() * item.getAmount()) - discountDetails.getDiscountAmount())) : 0);
                                        item.setVatAmount(item.getIsVatable() == 1 ? generate.toFourDecimal(compute.vatAmount(compute.vatableSales((item.getQty() * item.getAmount()) - discountDetails.getDiscountAmount()))) : 0);
                                        item.setDiscountAmount(generate.toFourDecimal(discountDetails.getDiscountAmount()));
                                        item.setVatExemptSales(item.getIsVatable() == 0 ? generate.toFourDecimal(item.getTotal()) : 0);
                                        item.setVatExpense(0);
                                    }
                                }
                            }
                        }
                        else{
                            //Discount Percentage
                            Long totalCountDiscountDepartment = discountTypeDepartmentsViewModel.fetchDiscountTypeDepartmentCount(discountDetails.getDiscountTypeId());
                            if(totalCountDiscountDepartment.intValue() == 0){
                                if(discountDetails.isVatExempt()){
                                    item.setVatableSales(generate.toFourDecimal(0));
                                    item.setVatAmount(generate.toFourDecimal(0));
                                    item.setDiscountAmount(generate.toFourDecimal(discountDetails.getDiscountAmount()));
                                    item.setVatExemptSales(generate.toFourDecimal(discountDetails.getVatExemptAmount()));
                                    item.setVatExpense(generate.toFourDecimal(discountDetails.getVatExpense()));
                                    item.setGross(generate.toFourDecimal(compute.gross(item.getQty(), item.getAmount())));
                                    item.setTotal(generate.toFourDecimal((compute.gross(item.getQty(), item.getAmount()) - discountDetails.getVatExpense()) - discountDetails.getDiscountAmount()));
                                } else if (discountDetails.isZeroRated()) {
                                    if(item.getIsVatable() == 1){
                                        item.setIsZeroRated(1);
                                        item.setVatableSales(generate.toFourDecimal(0));
                                        item.setVatAmount(generate.toFourDecimal(0));
                                        item.setVatExemptSales(0);
                                        item.setVatExpense(0);
                                        item.setDiscountAmount(0);
                                        item.setZeroRatedAmount(generate.toFourDecimal(compute.vatableSales(item.getQty() * item.getAmount())));
                                        item.setGross(generate.toFourDecimal(item.getGross() - compute.vatAmount(item.getZeroRatedAmount())));
                                        item.setTotal(generate.toFourDecimal(item.getTotal() - compute.vatAmount(item.getZeroRatedAmount())));
                                    }
                                    else{
                                        item.setIsZeroRated(0);
                                        item.setVatableSales(generate.toFourDecimal(0));
                                        item.setVatAmount(generate.toFourDecimal(0));
                                        item.setVatExemptSales(generate.toFourDecimal(item.getTotal()));
                                        item.setVatExpense(0);
                                        item.setDiscountAmount(0);
                                        item.setZeroRatedAmount(0);
                                        item.setGross(generate.toFourDecimal(item.getTotal()));
                                        item.setTotal(generate.toFourDecimal(item.getTotal()));
                                    }
                                } else{
                                    item.setTotal(generate.toFourDecimal(item.getGross() - discountDetails.getDiscountAmount()));
                                    item.setVatableSales(item.getIsVatable() == 1 ? generate.toFourDecimal(compute.vatableSales((item.getQty() * item.getAmount()) - discountDetails.getDiscountAmount())) : 0);
                                    item.setVatAmount(item.getIsVatable() == 1 ? generate.toFourDecimal(compute.vatAmount(compute.vatableSales((item.getQty() * item.getAmount()) - discountDetails.getDiscountAmount()))) : 0);
                                    item.setDiscountAmount(generate.toFourDecimal(discountDetails.getDiscountAmount()));
                                    item.setVatExemptSales(item.getIsVatable() == 0 ? generate.toFourDecimal(item.getTotal()) : 0);
                                    item.setVatExpense(0);
                                }
                            }
                            else{
                                DiscountTypeDepartments discountTypeDepartments = discountTypeDepartmentsViewModel.fetchByDepartmentDiscountId(item.getDepartmentId(), discountDetails.getDiscountTypeId());
                                if(discountTypeDepartments != null){
                                    if(discountDetails.isVatExempt()){
                                        item.setVatableSales(generate.toFourDecimal(0));
                                        item.setVatAmount(generate.toFourDecimal(0));
                                        item.setDiscountAmount(generate.toFourDecimal(discountDetails.getDiscountAmount()));
                                        item.setVatExemptSales(generate.toFourDecimal(discountDetails.getVatExemptAmount()));
                                        item.setVatExpense(generate.toFourDecimal(discountDetails.getVatExpense()));
                                        item.setGross(generate.toFourDecimal(compute.gross(item.getQty(), item.getAmount())));
                                        item.setTotal(generate.toFourDecimal((compute.gross(item.getQty(), item.getAmount()) - discountDetails.getVatExpense()) - discountDetails.getDiscountAmount()));
                                    } else if (discountDetails.isZeroRated()) {
                                        if(item.getIsVatable() == 1){
                                            item.setIsZeroRated(1);
                                            item.setVatableSales(generate.toFourDecimal(0));
                                            item.setVatAmount(generate.toFourDecimal(0));
                                            item.setVatExemptSales(0);
                                            item.setVatExpense(0);
                                            item.setDiscountAmount(0);
                                            item.setZeroRatedAmount(generate.toFourDecimal(compute.vatableSales(item.getQty() * item.getAmount())));
                                            item.setGross(generate.toFourDecimal(item.getGross() - compute.vatAmount(item.getZeroRatedAmount())));
                                            item.setTotal(generate.toFourDecimal(item.getTotal() - compute.vatAmount(item.getZeroRatedAmount())));
                                        }
                                        else{
                                            item.setIsZeroRated(0);
                                            item.setVatableSales(generate.toFourDecimal(0));
                                            item.setVatAmount(generate.toFourDecimal(0));
                                            item.setVatExemptSales(generate.toFourDecimal(item.getTotal()));
                                            item.setVatExpense(0);
                                            item.setDiscountAmount(0);
                                            item.setZeroRatedAmount(0);
                                            item.setGross(generate.toFourDecimal(item.getTotal()));
                                            item.setTotal(generate.toFourDecimal(item.getTotal()));
                                        }
                                    } else{
                                        item.setTotal(generate.toFourDecimal(item.getGross() - discountDetails.getDiscountAmount()));
                                        item.setVatableSales(item.getIsVatable() == 1 ? generate.toFourDecimal(compute.vatableSales((item.getQty() * item.getAmount()) - discountDetails.getDiscountAmount())) : 0);
                                        item.setVatAmount(item.getIsVatable() == 1 ? generate.toFourDecimal(compute.vatAmount(compute.vatableSales((item.getQty() * item.getAmount()) - discountDetails.getDiscountAmount()))) : 0);
                                        item.setDiscountAmount(generate.toFourDecimal(discountDetails.getDiscountAmount()));
                                        item.setVatExemptSales(item.getIsVatable() == 0 ? generate.toFourDecimal(item.getTotal()) : 0);
                                        item.setVatExpense(0);
                                    }
                                }
                            }
                        }
                        item.setDiscountDetailsId(discountDetails.getId());
                    }
                    ordersViewModel.update(item);
                }
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void recomputeCurrentTransactionOrders(){
        try {
            List<Orders> orderList = ordersViewModel.fetchTransactionOrdersWithVoid(POSApplication.getInstance().getCurrentTransaction());
            List<Discounts> discountsList = discountsViewModel.fetchDiscountsByTransactionIdWithVoid(POSApplication.getInstance().getCurrentTransaction());
            List<DiscountDetails> discountDetailsList = discountDetailsViewModel.fetchDiscountDetailsByTransactionIdWithVoid(POSApplication.getInstance().getCurrentTransaction());
            //This will compute the orders
            for(Orders item : orderList){
                if(item.getIsVoid() != 1){
                    DiscountDetails discountDetails = Functions.getInstance().discountDetailsFindByOrderId(discountDetailsList, item.getId());
                    if(discountDetails != null && discountDetails.getIsVoid() != 1){
                        //Recompute the discount details
                        if(discountDetails.getType().equals("amount")){
                            Long totalCountDiscountDepartment = discountTypeDepartmentsViewModel.fetchDiscountTypeDepartmentCount(discountDetails.getDiscountTypeId());
                            if(totalCountDiscountDepartment.intValue() == 0){
                                if(discountDetails.isVatExempt()){
                                    discountDetails.setDiscountAmount(generate.toFourDecimal(compute.vatExempt(item.getGross()) - discountDetails.getValue()));
                                    discountDetails.setVatExemptAmount(generate.toFourDecimal(compute.vatExempt(item.getGross())));
                                }
                                else{
                                    discountDetails.setDiscountAmount(generate.toFourDecimal(discountDetails.getValue()));
                                }
                                //==========================================================================D
                                if(discountDetails.isVatExempt()){
                                    item.setVatableSales(generate.toFourDecimal(0));
                                    item.setVatAmount(generate.toFourDecimal(0));
                                    item.setDiscountAmount(generate.toFourDecimal(discountDetails.getDiscountAmount()));
                                    item.setVatExemptSales(generate.toFourDecimal(discountDetails.getVatExemptAmount()));
                                    item.setVatExpense(generate.toFourDecimal(discountDetails.getVatExpense()));
                                    item.setGross(generate.toFourDecimal(compute.gross(item.getQty(), item.getAmount())));
                                    item.setTotal(generate.toFourDecimal((compute.gross(item.getQty(), item.getAmount()) - discountDetails.getVatExpense()) - discountDetails.getDiscountAmount()));
                                } else if (discountDetails.isZeroRated()) {
                                    if(item.getIsVatable() == 1){
                                        item.setIsZeroRated(1);
                                        item.setVatableSales(generate.toFourDecimal(0));
                                        item.setVatAmount(generate.toFourDecimal(0));
                                        item.setVatExemptSales(0);
                                        item.setVatExpense(0);
                                        item.setDiscountAmount(0);
                                        item.setZeroRatedAmount(generate.toFourDecimal(compute.vatableSales(item.getQty() * item.getAmount())));
                                        item.setGross(generate.toFourDecimal(item.getGross() - compute.vatAmount(item.getZeroRatedAmount())));
                                        item.setTotal(generate.toFourDecimal(item.getTotal() - compute.vatAmount(item.getZeroRatedAmount())));
                                    }
                                    else{
                                        item.setIsZeroRated(0);
                                        item.setVatableSales(generate.toFourDecimal(0));
                                        item.setVatAmount(generate.toFourDecimal(0));
                                        item.setVatExemptSales(generate.toFourDecimal(item.getTotal()));
                                        item.setVatExpense(0);
                                        item.setDiscountAmount(0);
                                        item.setZeroRatedAmount(0);
                                        item.setGross(generate.toFourDecimal(item.getTotal()));
                                        item.setTotal(generate.toFourDecimal(item.getTotal()));
                                    }
                                } else{
                                    item.setGross(generate.toFourDecimal(item.getQty() * item.getAmount()));
                                    item.setTotal(generate.toFourDecimal((item.getQty() * item.getAmount()) - discountDetails.getDiscountAmount()));
                                    item.setVatAmount(item.getIsVatable() == 1 ? generate.toFourDecimal(compute.vatAmount(compute.vatableSales((item.getQty() * item.getAmount()) - discountDetails.getDiscountAmount()))) : 0);
                                    item.setVatableSales(item.getIsVatable() == 1 ? generate.toFourDecimal(compute.vatableSales((item.getQty() * item.getAmount()) - discountDetails.getDiscountAmount())) : 0);
                                    item.setDiscountAmount(generate.toFourDecimal(discountDetails.getDiscountAmount()));
                                    item.setVatExemptSales(item.getIsVatable() == 0 ? item.getTotal() : 0);
                                    item.setVatExpense(0);
                                }
                            }
                            else{
                                DiscountTypeDepartments discountTypeDepartments = discountTypeDepartmentsViewModel.fetchByDepartmentDiscountId(item.getDepartmentId(), discountDetails.getDiscountTypeId());
                                if(discountTypeDepartments != null){
                                    if(discountDetails.isVatExempt()){
                                        discountDetails.setDiscountAmount(generate.toFourDecimal(compute.vatExempt(item.getGross()) - discountDetails.getValue()));
                                        discountDetails.setVatExemptAmount(generate.toFourDecimal(compute.vatExempt(item.getGross())));
                                    }
                                    else{
                                        discountDetails.setDiscountAmount(generate.toFourDecimal(discountDetails.getValue()));
                                    }
                                    //==========================================================================D
                                    if(discountDetails.isVatExempt()){
                                        item.setVatableSales(generate.toFourDecimal(0));
                                        item.setVatAmount(generate.toFourDecimal(0));
                                        item.setDiscountAmount(generate.toFourDecimal(discountDetails.getDiscountAmount()));
                                        item.setVatExemptSales(generate.toFourDecimal(discountDetails.getVatExemptAmount()));
                                        item.setVatExpense(generate.toFourDecimal(discountDetails.getVatExpense()));
                                        item.setGross(generate.toFourDecimal(compute.gross(item.getQty(), item.getAmount())));
                                        item.setTotal(generate.toFourDecimal((compute.gross(item.getQty(), item.getAmount()) - discountDetails.getVatExpense()) - discountDetails.getDiscountAmount()));
                                    } else if (discountDetails.isZeroRated()) {
                                        if(item.getIsVatable() == 1){
                                            item.setIsZeroRated(1);
                                            item.setVatableSales(generate.toFourDecimal(0));
                                            item.setVatAmount(generate.toFourDecimal(0));
                                            item.setVatExemptSales(0);
                                            item.setVatExpense(0);
                                            item.setDiscountAmount(0);
                                            item.setZeroRatedAmount(generate.toFourDecimal(compute.vatableSales(item.getQty() * item.getAmount())));
                                            item.setGross(generate.toFourDecimal(item.getGross() - compute.vatAmount(item.getZeroRatedAmount())));
                                            item.setTotal(generate.toFourDecimal(item.getTotal() - compute.vatAmount(item.getZeroRatedAmount())));
                                        }
                                        else{
                                            item.setIsZeroRated(0);
                                            item.setVatableSales(generate.toFourDecimal(0));
                                            item.setVatAmount(generate.toFourDecimal(0));
                                            item.setVatExemptSales(generate.toFourDecimal(item.getTotal()));
                                            item.setVatExpense(0);
                                            item.setDiscountAmount(0);
                                            item.setZeroRatedAmount(0);
                                            item.setGross(generate.toFourDecimal(item.getTotal()));
                                            item.setTotal(generate.toFourDecimal(item.getTotal()));
                                        }
                                    } else{
                                        item.setGross(generate.toFourDecimal(item.getQty() * item.getAmount()));
                                        item.setTotal(generate.toFourDecimal((item.getQty() * item.getAmount()) - discountDetails.getDiscountAmount()));
                                        item.setVatAmount(item.getIsVatable() == 1 ? generate.toFourDecimal(compute.vatAmount(compute.vatableSales((item.getQty() * item.getAmount()) - discountDetails.getDiscountAmount()))) : 0);
                                        item.setVatableSales(item.getIsVatable() == 1 ? generate.toFourDecimal(compute.vatableSales((item.getQty() * item.getAmount()) - discountDetails.getDiscountAmount())) : 0);
                                        item.setDiscountAmount(generate.toFourDecimal(discountDetails.getDiscountAmount()));
                                        item.setVatExemptSales(item.getIsVatable() == 0 ? item.getTotal() : 0);
                                        item.setVatExpense(0);
                                    }
                                }
                            }
                        }
                        else{
                            //Validation For Department Discount
                            try {
                                Long totalCountDiscountDepartment = discountTypeDepartmentsViewModel.fetchDiscountTypeDepartmentCount(discountDetails.getDiscountTypeId());
                                if(totalCountDiscountDepartment.intValue() == 0){
                                    //Percentage Discount
                                    if(discountDetails.isVatExempt()){
                                        discountDetails.setDiscountAmount(generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount()) * compute.toPercentage(discountDetails.getValue())));
                                        discountDetails.setVatExemptAmount(generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount())));
                                        discountDetails.setVatExpense(generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount()))));
                                    }
                                    else{
                                        discountDetails.setDiscountAmount(generate.toFourDecimal((item.getQty() * item.getAmount()) * compute.toPercentage(discountDetails.getValue())));
                                    }
                                    //==========================================================================D
                                    if(discountDetails.isVatExempt()){
                                        item.setVatableSales(generate.toFourDecimal(0));
                                        item.setVatAmount(generate.toFourDecimal(0));
                                        item.setDiscountAmount(generate.toFourDecimal(discountDetails.getDiscountAmount()));
                                        item.setVatExemptSales(generate.toFourDecimal(discountDetails.getVatExemptAmount()));
                                        item.setVatExpense(generate.toFourDecimal(discountDetails.getVatExpense()));
                                        item.setGross(generate.toFourDecimal(compute.gross(item.getQty(), item.getAmount())));
                                        item.setTotal(generate.toFourDecimal((compute.gross(item.getQty(), item.getAmount()) - discountDetails.getVatExpense()) - discountDetails.getDiscountAmount()));
                                    } else if (discountDetails.isZeroRated()) {
                                        if(item.getIsVatable() == 1){
                                            item.setIsZeroRated(1);
                                            item.setVatableSales(generate.toFourDecimal(0));
                                            item.setVatAmount(generate.toFourDecimal(0));
                                            item.setVatExemptSales(0);
                                            item.setVatExpense(0);
                                            item.setDiscountAmount(0);
                                            item.setZeroRatedAmount(generate.toFourDecimal(compute.vatableSales(item.getQty() * item.getAmount())));
                                            item.setGross(generate.toFourDecimal(item.getGross() - compute.vatAmount(item.getZeroRatedAmount())));
                                            item.setTotal(generate.toFourDecimal(item.getTotal() - compute.vatAmount(item.getZeroRatedAmount())));
                                        }
                                        else{
                                            item.setIsZeroRated(0);
                                            item.setVatableSales(generate.toFourDecimal(0));
                                            item.setVatAmount(generate.toFourDecimal(0));
                                            item.setVatExemptSales(generate.toFourDecimal(item.getTotal()));
                                            item.setVatExpense(0);
                                            item.setDiscountAmount(0);
                                            item.setZeroRatedAmount(0);
                                            item.setGross(generate.toFourDecimal(item.getTotal()));
                                            item.setTotal(generate.toFourDecimal(item.getTotal()));
                                        }
                                    } else{
                                        item.setGross(generate.toFourDecimal(item.getQty() * item.getAmount()));
                                        item.setTotal(generate.toFourDecimal(item.getGross() - discountDetails.getDiscountAmount()));
                                        item.setVatAmount(item.getIsVatable() == 1 ? generate.toFourDecimal(compute.vatAmount(compute.vatableSales((item.getQty() * item.getAmount()) - discountDetails.getDiscountAmount()))) : 0);
                                        item.setVatableSales(item.getIsVatable() == 1 ? generate.toFourDecimal(compute.vatableSales((item.getQty() * item.getAmount()) - discountDetails.getDiscountAmount())) : 0);
                                        item.setDiscountAmount(generate.toFourDecimal(discountDetails.getDiscountAmount()));
                                        item.setVatExemptSales(item.getIsVatable() == 0 ? item.getTotal() : 0);
                                        item.setVatExpense(0);
                                    }
                                }
                                else{
                                    DiscountTypeDepartments discountTypeDepartments = discountTypeDepartmentsViewModel.fetchByDepartmentDiscountId(item.getDepartmentId(), discountDetails.getDiscountTypeId());
                                    if(discountTypeDepartments != null){
                                        //Percentage Discount
                                        if(discountDetails.isVatExempt()){
                                            discountDetails.setDiscountAmount(generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount()) * compute.toPercentage(discountDetails.getValue())));
                                            discountDetails.setVatExemptAmount(generate.toFourDecimal(compute.vatExempt(item.getQty() * item.getAmount())));
                                            discountDetails.setVatExpense(generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount()))));
                                        }
                                        else{
                                            discountDetails.setDiscountAmount(generate.toFourDecimal((item.getQty() * item.getAmount()) * compute.toPercentage(discountDetails.getValue())));
                                        }
                                        //==========================================================================D
                                        if(discountDetails.isVatExempt()){
                                            item.setVatableSales(generate.toFourDecimal(0));
                                            item.setVatAmount(generate.toFourDecimal(0));
                                            item.setDiscountAmount(generate.toFourDecimal(discountDetails.getDiscountAmount()));
                                            item.setVatExemptSales(generate.toFourDecimal(discountDetails.getVatExemptAmount()));
                                            item.setVatExpense(generate.toFourDecimal(discountDetails.getVatExpense()));
                                            item.setGross(generate.toFourDecimal(compute.gross(item.getQty(), item.getAmount())));
                                            item.setTotal(generate.toFourDecimal((compute.gross(item.getQty(), item.getAmount()) - discountDetails.getVatExpense()) - discountDetails.getDiscountAmount()));
                                        } else if (discountDetails.isZeroRated()) {
                                            if(item.getIsVatable() == 1){
                                                item.setIsZeroRated(1);
                                                item.setVatableSales(generate.toFourDecimal(0));
                                                item.setVatAmount(generate.toFourDecimal(0));
                                                item.setVatExemptSales(0);
                                                item.setVatExpense(0);
                                                item.setDiscountAmount(0);
                                                item.setZeroRatedAmount(generate.toFourDecimal(compute.vatableSales(item.getQty() * item.getAmount())));
                                                item.setGross(generate.toFourDecimal(item.getGross() - compute.vatAmount(item.getZeroRatedAmount())));
                                                item.setTotal(generate.toFourDecimal(item.getTotal() - compute.vatAmount(item.getZeroRatedAmount())));
                                            }
                                            else{
                                                item.setIsZeroRated(0);
                                                item.setVatableSales(generate.toFourDecimal(0));
                                                item.setVatAmount(generate.toFourDecimal(0));
                                                item.setVatExemptSales(generate.toFourDecimal(item.getTotal()));
                                                item.setVatExpense(0);
                                                item.setDiscountAmount(0);
                                                item.setZeroRatedAmount(0);
                                                item.setGross(generate.toFourDecimal(item.getTotal()));
                                                item.setTotal(generate.toFourDecimal(item.getTotal()));
                                            }
                                        } else{
                                            item.setGross(generate.toFourDecimal(item.getQty() * item.getAmount()));
                                            item.setTotal(generate.toFourDecimal(item.getGross() - discountDetails.getDiscountAmount()));
                                            item.setVatAmount(item.getIsVatable() == 1 ? generate.toFourDecimal(compute.vatAmount(compute.vatableSales((item.getQty() * item.getAmount()) - discountDetails.getDiscountAmount()))) : 0);
                                            item.setVatableSales(item.getIsVatable() == 1 ? generate.toFourDecimal(compute.vatableSales((item.getQty() * item.getAmount()) - discountDetails.getDiscountAmount())) : 0);
                                            item.setDiscountAmount(generate.toFourDecimal(discountDetails.getDiscountAmount()));
                                            item.setVatExemptSales(item.getIsVatable() == 0 ? item.getTotal() : 0);
                                            item.setVatExpense(0);
                                        }
                                    }
                                }
                            } catch (ExecutionException ex) {
                                throw new RuntimeException(ex);
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        discountDetailsViewModel.update(discountDetails);
                    }
                    else{
                        item.setGross(generate.toFourDecimal(item.getQty() * item.getAmount()));
                        item.setTotal(generate.toFourDecimal(item.getQty() * item.getAmount()));
                        item.setDiscountDetailsId(0);
                        if(item.getIsVatable() == 1){
                            item.setVatableSales(generate.toFourDecimal(compute.vatableSales(item.getQty() * item.getAmount())));
                            item.setVatAmount(generate.toFourDecimal(compute.vatAmount(compute.vatableSales(item.getQty() * item.getAmount()))));
                            item.setVatExemptSales(0);
                        }
                        else{
                            item.setVatableSales(0);
                            item.setVatAmount(0);
                            item.setVatExemptSales(generate.toFourDecimal(item.getQty() * item.getAmount()));
                        }
                        item.setIsZeroRated(0);
                        item.setZeroRatedAmount(0);
                        item.setDiscountAmount(0);
                        item.setVatExpense(0);
                    }
                    ordersViewModel.update(item);
                }
            }
            //This will recompute the discount summary
            for(Discounts item : discountsList){
                if(item.getIsVoid() == 0){
                    List<DiscountDetails> discountDetailsList1 = discountDetailsViewModel.fetchDiscountDetailsByDiscountIdWithVoid(item.getId());
                    double totalVatExempt = 0.00;
                    double totalDiscountAmount = 0.00;
                    double totalVatExpense = 0.00;
                    for(DiscountDetails value : discountDetailsList1){
                        if(value.getIsVoid() == 0){
                            totalVatExempt += value.getVatExemptAmount();
                            totalDiscountAmount += value.getDiscountAmount();
                            totalVatExpense += value.getVatExpense();
                        }
                    }
                    item.setVatExemptAmount(generate.toFourDecimal(totalVatExempt));
                    item.setDiscountAmount(generate.toFourDecimal(totalDiscountAmount));
                    item.setVatExpense(generate.toFourDecimal(totalVatExpense));
                    discountsViewModel.update(item);
                }
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkSelectedTransactionOrdersHasDiscount(ArrayList<Orders> ordersArrayList){
        for (Orders item : ordersArrayList){
            if(item.getDiscountDetailsId() != 0){
                return true;
            }
        }
        return false;
    }

    public boolean checkSelectedTransactionOrdersHasReturn(ArrayList<Orders> ordersArrayList){
        for (Orders item : ordersArrayList){
            if(item.getIsReturn() != 0){
                return true;
            }
        }
        return false;
    }

    public boolean checkCurrentTransactionOrdersHasDiscount(){
        try {
            List<Orders> orderList = ordersViewModel.fetchTransactionOrdersWithVoid(POSApplication.getInstance().getCurrentTransaction());
            for (Orders item : orderList){
                if(item.getIsVoid() == 0){
                    if(item.getDiscountDetailsId() != 0){
                        return true;
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

    public void cutOffTransactions(Activity activity, List<Transactions> transactions){
        try {
            double totalPayout = 0.00, totalSafeKeeping = 0.00, begAmount = 0.00, endAmount = 0.00, grossSales = 0.00, netSales = 0.00, vatabaleSales = 0.00, vatExempt = 0.00, vatAmount = 0.00, vatExpense = 0.00, voidAmount = 0.00, totalServiceCharge = 0.00, totalCost = 0.00, totalChange = 0.00, totalDiscountAmount = 0.00, totalShortOver = 0.00, totalZeroRatedAmount = 0.00, totalReturn = 0.00;
            int totalVoidQty = 0;
            int totalTransactions = transactions.size();
            String treg = date.now();
            String begOR = transactions.get(0).getReceiptNumber();
            String endOR = transactions.get(transactions.size() - 1).getReceiptNumber();
            MachineDetails machineDetails = machineDetailsViewModel.fetchMachineDetails();
            machineDetails.setXreadingCounter(machineDetails.getXreadingCounter() + 1);
            machineDetails.setIsSentToServer(0);
            machineDetailsViewModel.update(machineDetails);
            CutOff lastCutOff = cutOffViewModel.fetchLastCutOffInformation();
            List<CutOffDepartments> cutOffDepartments = new ArrayList<>();
            List<CutOffDiscounts> cutOffDiscounts = new ArrayList<>();
            List<CutOffPayments> cutOffPayments = new ArrayList<>();
            List<SafekeepingDenomination> safekeepingDenominations = new ArrayList<>();
            List<CutOffProducts> cutOffProducts = new ArrayList<>();
            if(lastCutOff != null){
                begAmount = lastCutOff.getEndingAmount();
                endAmount = lastCutOff.getEndingAmount();
            }
            //Create new cutoff information
            Long cutOffId = cutOffViewModel.insert(new CutOff(
                    POSApplication.getInstance().getMachineDetails().getCoreId(),
                    POSApplication.getInstance().getBranch().getCoreId(),
                    machineDetails.getXreadingCounter(),
                    "",
                    "",
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    POSApplication.getInstance().getUserAuthentication().getId(),
                    POSApplication.getInstance().getUserAuthentication().getName(),
                    0,
                    "APPROVER NAME",
                    1,
                    0,
                    1,
                    treg,
                    0,
                    0,
                    "",
                    0,
                    POSApplication.getInstance().getCompany().getCoreId(),
                    0
            ));
            for(Transactions item : transactions){
                if(item.getIsVoid() != 1){
                    grossSales += item.getGrossSales();
                    netSales += item.getNetSales();
                    endAmount += item.getNetSales();
                    vatabaleSales += item.getVatableSales();
                    vatExempt += item.getVatExemptSales();
                    vatAmount += item.getVatAmount();
                    vatExpense += item.getVatExpense();
                    totalServiceCharge += item.getServiceCharge();
                    totalCost += item.getTotalUnitCost();
                    totalChange += item.getChange();
                    totalDiscountAmount += item.getDiscountAmount();
                    totalZeroRatedAmount += item.getTotalZeroRatedAmount();
                    //Remove X Reading Computation
                    //totalVoidQty += item.getTotalVoidQty();
                    //voidAmount += item.getTotalVoidAmount();
                }
                else{
                    //Remove X Reading Computation
                    //totalVoidQty += item.getTotalVoidQty();
                    //New Code
                    totalVoidQty += 1;
                    voidAmount += item.getNetSales();
                }
                item.setIsSentToServer(0);
                item.setCutoffId(cutOffId.intValue());
                item.setCutoffAt(date.now());
                item.setIsCutoff(1);
                List<Orders> orders = ordersViewModel.fetchTransactionOrders(item.getId());
                for(Orders itemOrder : orders){
                    itemOrder.setIsSentToServer(0);
                    itemOrder.setCutOffId(cutOffId.intValue());
                    itemOrder.setIsCutOff(1);
                    cutOffDepartments = functions.filterByDepartmentCutOff(cutOffDepartments, itemOrder.getDepartmentId(), itemOrder.getDepartmentName(), itemOrder.getTotal(), itemOrder.getQty(), cutOffId.intValue(), treg);
                    ordersViewModel.update(itemOrder);
                    //For Cutoff Products
                    cutOffProducts = functions.filterByCutOffProducts(cutOffProducts, itemOrder.getProductId(), cutOffId.intValue(), itemOrder.getQty(), treg);
                    //Compute Total Return
                    if(itemOrder.getIsReturn() == 1 && item.getNetSales() < 0) totalReturn += itemOrder.getTotal();
                }
                List<Payments> payments = paymentsViewModel.fetchTransactionPayments(item.getId());
                for(Payments itemPayment : payments){
                    itemPayment.setIsSentToServer(0);
                    itemPayment.setIsCutOff(1);
                    itemPayment.setCutOffId(cutOffId.intValue());
                    if(itemPayment.getIsAccountReceivable() != 1){
                        cutOffPayments = functions.filterByPaymentsCutOff(cutOffPayments, itemPayment.getPaymentTypeId(), itemPayment.getPaymentTypeName(), !itemPayment.getPaymentTypeName().equals("Cash") ? itemPayment.getAmount() : itemPayment.getAmount() - item.getChange(), 1, cutOffId.intValue(), treg);
                    }
                    paymentsViewModel.update(itemPayment);
                }
                List<Discounts> discounts = discountsViewModel.fetchDiscountsByTransactionId(item.getId());
                for(Discounts itemDiscount : discounts){
                    itemDiscount.setIsSentToServer(0);
                    itemDiscount.setIsCutOff(1);
                    itemDiscount.setCutOffId(cutOffId.intValue());
                    List<DiscountDetails> discountDetailsList = discountDetailsViewModel.fetchDiscountDetailsByDiscountId(itemDiscount.getId());
                    for(DiscountDetails itemDiscountDetails : discountDetailsList){
                        itemDiscountDetails.setIsSentToServer(0);
                        itemDiscountDetails.setCutOffId(cutOffId.intValue());
                        itemDiscountDetails.setIsCutOff(1);
                        discountDetailsViewModel.update(itemDiscountDetails);
                    }
                    cutOffDiscounts = functions.filterByDiscountCutOff(cutOffDiscounts, itemDiscount.getDiscountTypeId(), itemDiscount.getDiscountName(), item.getDiscountAmount(), 1, cutOffId.intValue(), treg, itemDiscount.getIsZeroRated());
                    discountsViewModel.update(itemDiscount);
                }
                transactionsViewModel.update(item);
            }
            //Save CutOff Departments
            for(CutOffDepartments cutOffDepItem : cutOffDepartments){
                cutOffDepartmentsViewModel.insert(cutOffDepItem);
            }
            //Save CutOff Discounts
            for(CutOffDiscounts cutOffDiscItem : cutOffDiscounts){
                cutOffDiscountsViewModel.insert(cutOffDiscItem);
            }
            //Save CutOff Payments
            for(CutOffPayments cutOffPayItem : cutOffPayments){
                cutOffPaymentsViewModel.insert(cutOffPayItem);
            }
            //Save CutOff Products
            for(CutOffProducts cutOffProductItem : cutOffProducts){
                cutOffProductsViewModel.insert(cutOffProductItem);
            }
            //Process Safekeeping (ALL)
            //Get The Last SK Generated (FIX THIS)
            List<Safekeeping> safekeepings = safekeepingViewModel.fetchSafekeepingToCutOff();
            for(Safekeeping item : safekeepings){
                item.setIsSentToServer(0);
                item.setIsCutOff(1);
                item.setCutOffId(cutOffId.intValue());
                List<SafekeepingDenomination> safekeepingDenominationsData = safekeepingDenominationViewModel.fetchSafekeepingDenominationBySafekeepingId(item.getId());
                for(SafekeepingDenomination itemDenomination : safekeepingDenominationsData){
                    itemDenomination.setIsSentToServer(0);
                    itemDenomination.setIsCutOff(1);
                    itemDenomination.setCutOffId(cutOffId.intValue());
                    safekeepingDenominations = functions.filterBySafeKeepingDenomination(safekeepingDenominations, itemDenomination.getCashDenominationId(), itemDenomination.getName(), itemDenomination.getAmount(), itemDenomination.getQty(), itemDenomination.getTotal());
                    safekeepingDenominationViewModel.update(itemDenomination);
                }
                totalSafeKeeping += item.getAmount();
                totalShortOver += item.getShortOver();
                safekeepingViewModel.update(item);
            }
            //Process Safekeeping (LAST)
            //This Will Get The Last Safekeeping Made
            Safekeeping lastSK = safekeepings.get(safekeepings.size() - 1);
            List<SafekeepingDenomination> lastSKDenominations = safekeepingDenominationViewModel.fetchSafekeepingDenominationBySafekeepingId(lastSK.getId());
            //Process Payment Others
            List<PaymentOtherInformations> paymentOtherInformations = paymentOtherInformationsViewModel.fetchPaymentOtherInformationToCutOff();
            for(PaymentOtherInformations item : paymentOtherInformations){
                item.setIsSentToServer(0);
                item.setIsCutOff(1);
                item.setCutOffId(cutOffId.intValue());
                paymentOtherInformationsViewModel.update(item);
            }
            //Process Discount Others
            List<DiscountOtherInformations> discountOtherInformations = discountOtherInformationsViewModel.fetchDiscountOtherInformationToCutOff();
            for(DiscountOtherInformations item : discountOtherInformations){
                item.setIsSentToServer(0);
                item.setIsCutOff(1);
                item.setCutOffId(cutOffId.intValue());
                discountOtherInformationsViewModel.update(item);
            }
            //Process Backout Transctions To CutOff
            List<Transactions> transactionBackouts = transactionsViewModel.fetchBackoutTransactionsToCutOff();
            for(Transactions item : transactionBackouts){
                item.setIsCutoff(1);
                item.setIsSentToServer(0);
                item.setCutoffId(cutOffId.intValue());
                item.setCutoffAt(treg);
                transactionsViewModel.update(item);
            }
            //Process Cash Funds
            List<CashFund> cashFunds = cashFundViewModel.fetchCashFundToCutOff();
            for(CashFund item : cashFunds){
                item.setCutOffId(cutOffId.intValue());
                item.setIsSentToServer(0);
                item.setIsCutOff(1);
                List<CashFundDenomination> cashFundDenominations = cashFundDenominationViewModel.fetchByCashFundId(item.getId());
                for(CashFundDenomination cashFundDenominationItem : cashFundDenominations){
                    cashFundDenominationItem.setCutOffId(cutOffId.intValue());
                    cashFundDenominationItem.setIsCutOff(1);
                    cashFundDenominationItem.setIsSentToServer(0);
                    cashFundDenominationViewModel.update(cashFundDenominationItem);
                }
                cashFundViewModel.update(item);
            }
            CutOff cutOff = cutOffViewModel.fetchCutOffInformationById(cutOffId.intValue());
            cutOff.setBeginningOR(begOR);
            cutOff.setEndingOR(endOR);
            cutOff.setBeginningAmount(generate.toFourDecimal(begAmount));
            cutOff.setEndingAmount(generate.toFourDecimal(endAmount));
            cutOff.setTotalTransactions(totalTransactions);
            cutOff.setGrossSales(generate.toFourDecimal(grossSales));
            cutOff.setNetSales(generate.toFourDecimal(netSales));
            cutOff.setVatableSales(generate.toFourDecimal(vatabaleSales));
            cutOff.setVatExemptSales(generate.toFourDecimal(vatExempt));
            cutOff.setVatAmount(generate.toFourDecimal(vatAmount));
            cutOff.setVatExpense(generate.toFourDecimal(vatExpense));
            cutOff.setVoidAmount(generate.toFourDecimal(voidAmount));
            cutOff.setTotalChange(generate.toFourDecimal(totalChange));
            cutOff.setTotalPayout(generate.toFourDecimal(totalPayout));
            cutOff.setTotalServiceCharge(generate.toFourDecimal(totalServiceCharge));
            cutOff.setTotalDiscountAmount(generate.toFourDecimal(totalDiscountAmount));
            cutOff.setTotalCost(generate.toFourDecimal(totalCost));
            cutOff.setTotalSK(generate.toFourDecimal(totalSafeKeeping));
            cutOff.setVoidQty(totalVoidQty);
            cutOff.setTotalShortOver(generate.toFourDecimal(totalShortOver));
            cutOff.setTotalZeroRatedAmount(generate.toFourDecimal(totalZeroRatedAmount));
            cutOff.setPrintString(Printer.getInstance().cutOff(cutOff, cutOffDiscounts, cutOffPayments, lastSKDenominations, cashFunds, cutOffDepartments));
            cutOff.setTotalReturn(generate.toFourDecimal(totalReturn));
            cutOff.setIsSentToServer(0);
            cutOff.setIsComplete(1);
            cutOffViewModel.update(cutOff);
            //Set The Content
            String printContent = Printer.getInstance().cutOff(cutOff, cutOffDiscounts, cutOffPayments, lastSKDenominations, cashFunds, cutOffDepartments);
            Printer.getInstance().print(activity, printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_X_READING), printContent.replace("{{HEADER}}", ""));
            Writer.getInstance().writeTransaction(activity, printContent.replace("{{HEADER}}", ""));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void processEndOfDay(Activity activity, List<CutOff> cutOffs, boolean isCurrentDate){
        try {
            double totalPayout = 0.00, totalSafeKeeping = 0.00, begAmount = 0.00, endAmount = 0.00, grossSales = 0.00, netSales = 0.00, vatabaleSales = 0.00, vatExempt = 0.00, vatAmount = 0.00, vatExpense = 0.00, voidAmount = 0.00, totalServiceCharge = 0.00, totalCost = 0.00, totalChange = 0.00, totalDiscountAmount = 0.00, totalShortOver = 0.00, totalZeroRatedAmount = 0.00, totalReturn = 0.00;
            int totalTransactions = 0, totalVoidQty = 0;
            String treg = "";
            String begOR = cutOffs.get(0).getBeginningOR();
            String endOR = cutOffs.get(cutOffs.size() - 1).getEndingOR();
            int begReadingNumber = cutOffs.get(0).getReadingNumber();
            int endReadingNumber = cutOffs.get(cutOffs.size() - 1).getReadingNumber();
            begAmount = cutOffs.get(0).getBeginningAmount();
            endAmount = cutOffs.get(cutOffs.size() - 1).getEndingAmount();
            MachineDetails machineDetails = machineDetailsViewModel.fetchMachineDetails();
            machineDetails.setZreadingCounter(machineDetails.getZreadingCounter() + 1);
            machineDetails.setIsSentToServer(0);
            machineDetailsViewModel.update(machineDetails);
            List<EndOfDayDepartments> endOfDayDepartments = new ArrayList<>();
            List<EndOfDayDiscounts> endOfDayDiscounts = new ArrayList<>();
            List<EndOfDayPayments> endOfDayPayments = new ArrayList<>();
            List<EndOffDayProducts> endOffDayProducts = new ArrayList<>();
            List<CutOffProducts> cutOffProducts = cutOffProductsViewModel.fetchCutOffProductsToEndOfDay();
            List<CashFund> cashFunds = cashFundViewModel.fetchCasFundToEndOfDay();
            List<Safekeeping> safekeepings = safekeepingViewModel.fetchSafekeepingToEndOfDay();
            //This will check if current date is being process
            if(isCurrentDate){
                treg = date.now();
            }
            else{
                treg = cutOffs.get(cutOffs.size() - 1).getTreg();
            }
            Long endOfDayId = endOfDayViewModel.insert(new EndOfDay(
                    POSApplication.getInstance().getMachineDetails().getCoreId(),
                    POSApplication.getInstance().getBranch().getCoreId(),
                    machineDetails.getZreadingCounter(),
                    begOR,
                    endOR,
                    generate.toFourDecimal(begAmount),
                    generate.toFourDecimal(endAmount),
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    POSApplication.getInstance().getUserAuthentication().getId(),
                    POSApplication.getInstance().getUserAuthentication().getName(),
                    0,
                    "APPROVER NAME",
                    0,
                    1,
                    treg,
                    0,
                    0,
                    date.now(),
                    0,
                    begReadingNumber,
                    endReadingNumber,
                    "",
                    POSApplication.getInstance().getCompany().getCoreId(),
                    0
            ));
            for(CutOff item : cutOffs){
                grossSales += item.getGrossSales();
                netSales += item.getNetSales();
                vatabaleSales += item.getVatableSales();
                vatExempt += item.getVatExemptSales();
                vatAmount += item.getVatAmount();
                vatExpense += item.getVatExpense();
                voidAmount += item.getVoidAmount();
                totalServiceCharge += item.getTotalServiceCharge();
                totalCost += item.getTotalCost();
                totalChange += item.getTotalChange();
                totalDiscountAmount += item.getTotalDiscountAmount();
                totalTransactions += item.getTotalTransactions();
                totalVoidQty += item.getVoidQty();
                totalShortOver += item.getTotalShortOver();
                totalZeroRatedAmount += item.getTotalZeroRatedAmount();
                totalReturn += item.getTotalReturn();
                item.setIsSentToServer(0);
                item.setEndOfDayId(endOfDayId.intValue());
                List<CutOffDepartments> cutOffDepartments = cutOffDepartmentsViewModel.fetchByCutOffId(item.getId());
                for(CutOffDepartments itemDepartments : cutOffDepartments){
                    itemDepartments.setEndOfDayId(endOfDayId.intValue());
                    itemDepartments.setIsSentToServer(0);
                    endOfDayDepartments = functions.filterByDepartmentEndOfDay(endOfDayDepartments, itemDepartments.getDepartmentId(), itemDepartments.getName(), itemDepartments.getTransactionCount(), itemDepartments.getAmount(), endOfDayId.intValue(), treg);
                    cutOffDepartmentsViewModel.update(itemDepartments);
                }
                List<CutOffDiscounts> cutOffDiscounts = cutOffDiscountsViewModel.fetchByCutOffId(item.getId());
                for(CutOffDiscounts itemDiscounts : cutOffDiscounts){
                    itemDiscounts.setEndOfDayId(endOfDayId.intValue());
                    itemDiscounts.setIsSentToServer(0);
                    endOfDayDiscounts = functions.filterByDiscountsEndOfDay(endOfDayDiscounts, itemDiscounts.getDiscountTypeId(), itemDiscounts.getName(), itemDiscounts.getTransactionCount(), itemDiscounts.getAmount(), endOfDayId.intValue(), treg, itemDiscounts.getIsZeroRated());
                    cutOffDiscountsViewModel.update(itemDiscounts);
                }
                List<CutOffPayments> cutOffPayments = cutOffPaymentsViewModel.fetchByCutOffId(item.getId());
                for(CutOffPayments itemPayments : cutOffPayments){
                    itemPayments.setEndOfDayId(endOfDayId.intValue());
                    itemPayments.setIsSentToServer(0);
                    endOfDayPayments = functions.filterByPaymentsEndOfDay(endOfDayPayments, itemPayments.getPaymentTypeId(), itemPayments.getName(), itemPayments.getTransactionCount(), itemPayments.getAmount(), endOfDayId.intValue(), treg);
                    cutOffPaymentsViewModel.update(itemPayments);
                }
                cutOffViewModel.update(item);
            }
            //Setup Here The CutOff Products
            for(CutOffProducts item : cutOffProducts){
                endOffDayProducts = functions.filterByCutOffProductsToEndOfDayProducts(endOffDayProducts, item.getProductId(), endOfDayId.intValue(), item.getQty(), treg);
                item.setEndOfDayId(endOfDayId.intValue());
                item.setIsSentToServer(0);
                cutOffProductsViewModel.update(item);
            }
            //End Of Day Cash Funds
            for(CashFund item : cashFunds){
                item.setEndOfDayId(endOfDayId.intValue());
                item.setIsSentToServer(0);
                List<CashFundDenomination> cashFundDenominations = cashFundDenominationViewModel.fetchByCashFundId(item.getId());
                for(CashFundDenomination cashFundDenominationItem : cashFundDenominations){
                    cashFundDenominationItem.setEndOfDayId(endOfDayId.intValue());
                    cashFundDenominationItem.setIsSentToServer(0);
                    cashFundDenominationViewModel.update(cashFundDenominationItem);
                }
                cashFundViewModel.update(item);
            }
            //End Of Day Safekeepings
            for(Safekeeping item : safekeepings){
                item.setEndOfDayId(endOfDayId.intValue());
                item.setIsSentToServer(0);
                List<SafekeepingDenomination> safekeepingDenominations = safekeepingDenominationViewModel.fetchSafekeepingDenominationBySafekeepingId(item.getId());
                for(SafekeepingDenomination safeKeepingDenominationItem : safekeepingDenominations){
                    safeKeepingDenominationItem.setEndOfDayId(endOfDayId.intValue());
                    safeKeepingDenominationItem.setIsSentToServer(0);
                    safekeepingDenominationViewModel.update(safeKeepingDenominationItem);
                }
                safekeepingViewModel.update(item);
            }
            //
            for(EndOfDayDepartments item : endOfDayDepartments){
                endOfDayDepartmentsViewModel.insert(item);
            }
            for(EndOfDayDiscounts item : endOfDayDiscounts){
                endOfDayDiscountsViewModel.insert(item);
            }
            for(EndOfDayPayments item : endOfDayPayments){
                endOfDayPaymentsViewModel.insert(item);
            }
            for(EndOffDayProducts item : endOffDayProducts){
                endOfDayProductsViewModel.insert(item);
            }
            //This Will Process The Remaining Backout Transaction To Cut Off It (When Ask For End Of Day To Backout The Transactions)
            List<Transactions> transactionBackoutCutOffs = transactionsViewModel.fetchBackoutTransactionsToCutOff();
            for(Transactions item : transactionBackoutCutOffs){
                item.setIsSentToServer(0);
                item.setIsCutoff(1);
                item.setCutoffAt(cutOffs.get(cutOffs.size() - 1).getTreg());
                item.setCutoffId(cutOffs.get(cutOffs.size() - 1).getId());
                transactionsViewModel.update(item);
            }
            EndOfDay endOfDay = endOfDayViewModel.fetchEndOfDayInformationById(endOfDayId.intValue());
            endOfDay.setGrossSales(generate.toFourDecimal(grossSales));
            endOfDay.setNetSales(generate.toFourDecimal(netSales));
            endOfDay.setVatableSales(generate.toFourDecimal(vatabaleSales));
            endOfDay.setVatExemptSales(generate.toFourDecimal(vatExempt));
            endOfDay.setVatAmount(generate.toFourDecimal(vatAmount));
            endOfDay.setVatExpense(generate.toFourDecimal(vatExpense));
            endOfDay.setVoidAmount(generate.toFourDecimal(voidAmount));
            endOfDay.setTotalServiceCharge(generate.toFourDecimal(totalServiceCharge));
            endOfDay.setTotalCost(generate.toFourDecimal(totalCost));
            endOfDay.setTotalChange(generate.toFourDecimal(totalChange));
            endOfDay.setTotalDiscountAmount(generate.toFourDecimal(totalDiscountAmount));
            endOfDay.setTotalTransactions(totalTransactions);
            endOfDay.setTotalPayout(generate.toFourDecimal(totalPayout));
            endOfDay.setTotalSK(generate.toFourDecimal(totalSafeKeeping));
            endOfDay.setVoidQty(totalVoidQty);
            endOfDay.setTotalShortOver(generate.toFourDecimal(totalShortOver));
            endOfDay.setTotalZeroRatedAmount(generate.toFourDecimal(totalZeroRatedAmount));
            endOfDay.setTotalReturn(generate.toFourDecimal(totalReturn));
            endOfDay.setIsSentToServer(0);
            endOfDay.setIsComplete(1);
            endOfDay.setPrintString(Printer.getInstance().endOfDay(endOfDay, endOfDayDepartments, endOfDayDiscounts, endOfDayPayments));
            endOfDayViewModel.update(endOfDay);
            //Set Content Printer
            String printerContent = Printer.getInstance().endOfDay(endOfDay, endOfDayDepartments, endOfDayDiscounts, endOfDayPayments);
            Printer.getInstance().print(activity, printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_Z_READING), printerContent.replace("{{HEADER}}", ""));
            Writer.getInstance().writeTransaction(activity, printerContent.replace("{{HEADER}}", ""));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void processSpotAudit(Activity activity, List<Transactions> transactions, double safekeepingAmount, double safekeepingShortOver, List<SpotAuditDenomination> spotAuditDenominations){
        try {
            double totalPayout = 0.00, totalSafeKeeping = 0.00, begAmount = 0.00, endAmount = 0.00, grossSales = 0.00, netSales = 0.00, vatabaleSales = 0.00, vatExempt = 0.00, vatAmount = 0.00, vatExpense = 0.00, voidAmount = 0.00, totalServiceCharge = 0.00, totalCost = 0.00, totalChange = 0.00, totalDiscountAmount = 0.00, totalShortOver = 0.00, skAmount = 0.00;
            int totalVoidQty = 0;
            int totalTransactions = transactions.size();
            String treg = date.now();
            String begOR = transactions.get(0).getReceiptNumber();
            String endOR = transactions.get(transactions.size() - 1).getReceiptNumber();
            CutOff lastCutOff = cutOffViewModel.fetchLastCutOffInformation();
            if(lastCutOff != null){
                begAmount = lastCutOff.getEndingAmount();
                endAmount = lastCutOff.getEndingAmount();
            }
            Long spotAuditId = spotAuditViewModel.insert(new SpotAudit(
                   POSApplication.getInstance().getMachineDetails().getCoreId(),
                   POSApplication.getInstance().getBranch().getCoreId(),
                    "",
                    "",
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    generate.toFourDecimal(safekeepingAmount),
                    0,
                    0,
                    POSApplication.getInstance().getUserAuthentication().getId(),
                    POSApplication.getInstance().getUserAuthentication().getName(),
                    0,
                    "APPROVER NAME",
                    0,
                    0,
                    0,
                    0,
                    treg,
                    generate.toFourDecimal(safekeepingShortOver),
                    POSApplication.getInstance().getCompany().getCoreId()
            ));
            for(Transactions item : transactions){
                if(item.getIsVoid() != 1){
                    grossSales += item.getGrossSales();
                    netSales += item.getNetSales();
                    endAmount += item.getNetSales();
                    vatabaleSales += item.getVatableSales();
                    vatExempt += item.getVatExemptSales();
                    vatAmount += item.getVatAmount();
                    vatExpense += item.getVatExpense();
                    totalServiceCharge += item.getServiceCharge();
                    totalCost += item.getTotalUnitCost();
                    totalChange += item.getChange();
                    totalDiscountAmount += item.getDiscountAmount();
                    //Remove X Reading Computation
                    //totalVoidQty += item.getTotalVoidQty();
                    //voidAmount += item.getTotalVoidAmount();
                }
                else{
                    //Remove X Reading Computation
                    //totalVoidQty += item.getTotalVoidQty();
                    //New Code
                    totalVoidQty += 1;
                    voidAmount += item.getNetSales();
                }
            }
            //Process Safekeeping
            List<Safekeeping> safekeepings = safekeepingViewModel.fetchSafekeepingToCutOff();
            for(Safekeeping item : safekeepings){
                totalSafeKeeping += item.getAmount();
                totalShortOver += item.getShortOver();
            }
            for(SpotAuditDenomination item : spotAuditDenominations){
                item.setSpotAuditId(spotAuditId.intValue());
                spotAuditDenominationViewModel.insert(item);
            }
            SpotAudit spotAudit = spotAuditViewModel.fetchById(spotAuditId.intValue());
            spotAudit.setBeginningOR(begOR);
            spotAudit.setEndingOR(endOR);
            spotAudit.setBeginningAmount(generate.toFourDecimal(begAmount));
            spotAudit.setEndingAmount(generate.toFourDecimal(endAmount));
            spotAudit.setTotalTransactions(totalTransactions);
            spotAudit.setGrossSales(generate.toFourDecimal(grossSales));
            spotAudit.setNetSales(generate.toFourDecimal(netSales));
            spotAudit.setVatableSales(generate.toFourDecimal(vatabaleSales));
            spotAudit.setVatExemptSales(generate.toFourDecimal(vatExempt));
            spotAudit.setVatAmount(generate.toFourDecimal(vatAmount));
            spotAudit.setVatExpense(generate.toFourDecimal(vatExpense));
            spotAudit.setVoidAmount(generate.toFourDecimal(voidAmount));
            spotAudit.setTotalChange(generate.toFourDecimal(totalChange));
            spotAudit.setTotalPayout(generate.toFourDecimal(totalPayout));
            spotAudit.setTotalServiceCharge(generate.toFourDecimal(totalServiceCharge));
            spotAudit.setTotalDiscountAmount(generate.toFourDecimal(totalDiscountAmount));
            spotAudit.setTotalCost(generate.toFourDecimal(totalCost));
            spotAudit.setTotalSK(generate.toFourDecimal(totalSafeKeeping));
            spotAudit.setVoidQty(totalVoidQty);
            spotAudit.setTotalShortOver(generate.toFourDecimal(totalShortOver));
            spotAudit.setIsSentToServer(0);
            spotAuditViewModel.update(spotAudit);
            Printer.getInstance().print(activity, printerSetupDevicesViewModel.fetchPrinterSetupDevicesPrinterSetupID(BuildConfig.APP_SPOT_AUDIT), Printer.getInstance().spotAudit(spotAudit, spotAuditDenominations));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Long processTakeOrderInformation(Transactions transactions, List<Orders> orders, List<Discounts> discounts, List<DiscountDetails> discountDetails, List<DiscountOtherInformations> discountOtherInformations){
        try {
            //Variables
            Long transactionId, orderId, discountId, discountDetailsId;
            //Set The Base Transaction
            transactionId = transactionsViewModel.insert(new Transactions(
                    POSApplication.getInstance().getMachineDetails().getCoreId(),
                    generate.controlNumber(),
                    "",
                    transactions.getGrossSales(),
                    transactions.getNetSales(),
                    transactions.getVatableSales(),
                    transactions.getVatExemptSales(),
                    transactions.getVatAmount(),
                    transactions.getDiscountAmount(),
                    transactions.getVatExpense(),
                    transactions.getTenderAmount(),
                    transactions.getChange(),
                    transactions.getServiceCharge(),
                    transactions.getType(),
                    POSApplication.getInstance().getUserAuthentication().getId(),
                    POSApplication.getInstance().getUserAuthentication().getName(),
                    transactions.getTakeOrderId(),
                    transactions.getTakeOrderName(),
                    transactions.getTotalQuantity(),
                    transactions.getTotalUnitCost(),
                    transactions.getTotalVoidAmount(),
                    "1",
                    0,
                    0,
                    "",
                    "",
                    0,
                    0,
                    "",
                    "",
                    0,
                    "",
                    0,
                    0,
                    0,
                    "",
                    0,
                    0,
                    "",
                    POSApplication.getInstance().getBranch().getCoreId(),
                    transactions.getGuestName(),
                    0,
                    transactions.getTreg(),
                    transactions.getTotalVoidQty(),
                    transactions.getIsReturn(),
                    transactions.getTotalReturnAmount(),
                    transactions.getTotalCashAmount(),
                    transactions.getVoidRemarks(),
                    transactions.getVoidCounter(),
                    transactions.getTotalZeroRatedAmount(),
                    transactions.getCustomerName(),
                    transactions.getRemarks(),
                    POSApplication.getInstance().getCompany().getCoreId(),
                    0,
                    ""
            ));
            //Set Discounts
            for(Discounts item: discounts){
                discountId = discountsViewModel.insert(new Discounts(
                        item.getMachineNumber(),
                        item.getBranchId(),
                        transactionId.intValue(),
                        item.getCustomDiscountId(),
                        item.getDiscountTypeId(),
                        item.getDiscountName(),
                        item.getValue(),
                        item.getDiscountAmount(),
                        item.getVatExemptAmount(),
                        item.getVatExpense(),
                        item.getType(),
                        POSApplication.getInstance().getUserAuthentication().getId(),
                        POSApplication.getInstance().getUserAuthentication().getName(),
                        item.getAuthorizeId(),
                        item.getAuthorizeName(),
                        item.getIsVoid(),
                        item.getVoidById(),
                        item.getVoidBy(),
                        item.getVoidAt(),
                        0,
                        0,
                        0,
                        item.getShiftNumber(),
                        item.getTreg(),
                        item.getIsZeroRated(),
                        item.getGrossAmount(),
                        item.getNetAmount(),
                        POSApplication.getInstance().getCompany().getCoreId()
                ));
                //This Will Set The New Discount Id
                discountDetails = functions.filterByDiscountDetailsByDiscountId(discountDetails, item.getId(), discountId.intValue());
                discountOtherInformations = functions.filterByDiscountOtherInformationByDiscountId(discountOtherInformations, item.getId(), discountId.intValue());
            }
            for(DiscountOtherInformations item: discountOtherInformations){
                discountOtherInformationsViewModel.insert(new DiscountOtherInformations(
                        item.getMachineId(),
                        item.getBranchId(),
                        transactionId.intValue(),
                        item.getDiscountId(),
                        item.getName(),
                        item.getValue(),
                        0,
                        0,
                        0,
                        0,
                        item.getTreg(),
                        POSApplication.getInstance().getCompany().getCoreId()
                ));
            }
            for(Orders item : orders){
                orderId = ordersViewModel.insertTakeOrder(new Orders(
                   POSApplication.getInstance().getMachineDetails().getCoreId(),
                   transactionId.intValue(),
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
                        POSApplication.getInstance().getBranch().getCoreId(),
                        item.getShiftNumber(),
                        0,
                        item.getTreg(),
                        0,
                        "",
                        item.getDiscountDetailsId(),
                        item.getRemarks(),
                        item.getIsReturn(),
                        item.getSerialNumber() == null ? "" : item.getSerialNumber(),
                        item.getIsZeroRated(),
                        item.getZeroRatedAmount(),
                        POSApplication.getInstance().getCompany().getCoreId(),
                        item.getPriceChangeReasonId()
                ));
                DiscountDetails itemDiscountDetails = functions.filterByDiscountDetailsOrder(discountDetails, item.getId());
                if(itemDiscountDetails != null){
                    discountDetailsId = discountDetailsViewModel.insertTakeOrderDiscountDetails(new DiscountDetails(
                            itemDiscountDetails.getDiscountId(),
                            itemDiscountDetails.getMachineNumber(),
                            itemDiscountDetails.getBranchId(),
                            itemDiscountDetails.getCustomDiscountId(),
                            transactionId.intValue(),
                            orderId.intValue(),
                            itemDiscountDetails.getDiscountTypeId(),
                            itemDiscountDetails.getValue(),
                            itemDiscountDetails.getDiscountAmount(),
                            itemDiscountDetails.getVatExemptAmount(),
                            itemDiscountDetails.getVatExpense(),
                            itemDiscountDetails.getType(),
                            itemDiscountDetails.getIsVoid(),
                            itemDiscountDetails.getVoidById(),
                            itemDiscountDetails.getVoidBy(),
                            itemDiscountDetails.getVoidAt(),
                            0,
                            0,
                            0,
                            itemDiscountDetails.isVatExempt(),
                            itemDiscountDetails.getShiftNumber(),
                            itemDiscountDetails.getTreg(),
                            itemDiscountDetails.isZeroRated(),
                            POSApplication.getInstance().getCompany().getCoreId()
                    ));
                    Orders updateOrders = ordersViewModel.fetchOrder(orderId.intValue());
                    updateOrders.setDiscountDetailsId(discountDetailsId.intValue());
                    ordersViewModel.update(updateOrders);
                }
            }
            return transactionId;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean checkForPermission(int permissionId){
        try {
            Permissions permissions = permissionsViewModel.fetchByRoleUserIdPermissionId(POSApplication.getInstance().getUserAuthentication().getRoles().get(0).getCoreId(), POSApplication.getInstance().getUserAuthentication().getId(), permissionId);
            if(permissions != null){
                return true;
            }
            else{
                return false;
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //Sync To Server Functions
    public void SyncTransactionToServer(Transactions transactions){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchTransactionSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchTransactionSyncPayload(
                    transactions.getId(),
                    transactions.getMachineNumber(),
                    transactions.getCashierId(),
                    transactions.getTakeOrderId() == 0 ? null : String.valueOf(transactions.getTakeOrderId()),
                    transactions.getVoidById() == 0 ? null : String.valueOf(transactions.getVoidById()),
                    transactions.getIsBackoutId() == 0 ? null : String.valueOf(transactions.getIsBackoutId()),
                    transactions.getChargeAccountId() == 0 ? null : String.valueOf(transactions.getChargeAccountId()),
                    transactions.getCutoffId(),
                    transactions.getBranchId(),
                    transactions.getControlNumber(),
                    transactions.getReceiptNumber(),
                    transactions.getType(),
                    transactions.getCashierName(),
                    transactions.getTakeOrderName(),
                    transactions.getShiftNumber(),
                    transactions.getVoidBy(),
                    transactions.getVoidAt(),
                    transactions.getBackoutBy(),
                    transactions.getChargeAccountName(),
                    transactions.getCompletedAt(),
                    transactions.getCutoffAt(),
                    transactions.getGrossSales(),
                    transactions.getNetSales(),
                    transactions.getVatableSales(),
                    transactions.getVatExemptSales(),
                    transactions.getVatAmount(),
                    transactions.getDiscountAmount(),
                    transactions.getTenderAmount(),
                    transactions.getChange(),
                    transactions.getServiceCharge(),
                    transactions.getTotalUnitCost(),
                    transactions.getTotalVoidAmount(),
                    transactions.getIsVoid() == 1 ? true : false,
                    transactions.getIsBackout() == 1 ? true : false,
                    transactions.getIsAccountReceivable() == 1 ? true : false,
                    transactions.getIsSentToServer() == 1 ? true : false,
                    transactions.getIsComplete() == 1 ? true : false,
                    transactions.getIsCutoff() == 1 ? true : false,
                    transactions.getTreg(),
                    transactions.getTotalQuantity(),
                    transactions.getTotalVoidQty(),
                    transactions.getVatExpense(),
                    transactions.getIsReturn() == 1 ? true : false,
                    transactions.getTotalReturnAmount(),
                    transactions.getTotalCashAmount(),
                    transactions.getVoidCounter(),
                    transactions.getVoidRemarks(),
                    transactions.getCustomerName(),
                    transactions.getRemarks(),
                    transactions.getCompanyId(),
                    transactions.getIsAccountReceivableRedeem(),
                    transactions.getAccountReceivableRedeemAt()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchTransactionSyncResponse>() {
                @Override
                public void onResponse(Call<BranchTransactionSyncResponse> call, Response<BranchTransactionSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            transactionsViewModel.updateTransactionSentToServer(transactions.getId());
                            Log.d("ToSyncServerTransaction", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncServerTransaction", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncServerTransaction", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchTransactionSyncResponse> call, Throwable t) {
                    Log.d("ToSyncServerTransaction", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncOrderToServer(Orders orders){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchOrderSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchOrderSyncPayload(
                    orders.getId(),
                    orders.getMachineNumber(),
                    orders.getTransactionId(),
                    orders.getProductId(),
                    orders.getDepartmentId(),
                    orders.getCategoryId(),
                    orders.getSubCategoryId(),
                    orders.getUnitId(),
                    orders.getIsBackoutId() == 0 ? null : String.valueOf(orders.getIsBackoutId()),
                    orders.getBranchId(),
                    orders.getCode(),
                    orders.getName(),
                    orders.getDescription(),
                    orders.getAbbreviation(),
                    orders.getDepartmentName(),
                    orders.getCategoryName(),
                    orders.getSubCategoryName(),
                    orders.getUnitName(),
                    orders.getVoidBy(),
                    orders.getVoidAt(),
                    orders.getBackoutBy(),
                    orders.getCompletedAt(),
                    orders.getCost(),
                    orders.getQty(),
                    orders.getAmount(),
                    orders.getOriginalAmount(),
                    orders.getGross(),
                    orders.getTotal(),
                    orders.getTotalCost(),
                    orders.getVatAmount(),
                    orders.getVatableSales(),
                    orders.getVatExemptSales(),
                    orders.getDiscountAmount(),
                    orders.getMinAmountSold(),
                    orders.getIsVatable() == 1 ? true : false,
                    orders.getIsVoid() == 1 ? true : false,
                    orders.getIsBackout() == 1 ? true : false,
                    orders.getIsPaid() == 1 ? true : false,
                    orders.getIsSentToServer() == 1 ? true : false,
                    orders.getIsCompleted() == 1 ? true : false,
                    orders.getIsCutOff() == 1 ? true : false,
                    orders.getCutOffId(),
                    orders.getCutOffAt(),
                    orders.getTreg(),
                    orders.getIsDiscountExempt() == 1 ? true : false,
                    orders.getIsOpenPrice() == 1 ? true : false,
                    orders.getWithSerial() == 1 ? true : false,
                    orders.getVatExpense(),
                    orders.getIsReturn() == 1 ? true : false,
                    orders.getIsZeroRated() == 1 ? true : false,
                    orders.getCompanyId(),
                    orders.getPriceChangeReasonId()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchOrderSyncResponse>() {
                @Override
                public void onResponse(Call<BranchOrderSyncResponse> call, Response<BranchOrderSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            ordersViewModel.updateOrderSentToServer(orders.getId());
                            Log.d("ToSyncServerOrder", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncServerOrder", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncServerOrder", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchOrderSyncResponse> call, Throwable t) {
                    Log.d("ToSyncServerOrder", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncPaymentToServer(Payments payments){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchPaymentSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchPaymentSyncPayload(
                    payments.getId(),
                    payments.getMachineNumber(),
                    payments.getBranchId(),
                    payments.getTransactionId(),
                    payments.getPaymentTypeId(),
                    payments.getCutOffId(),
                    payments.getPaymentTypeName(),
                    payments.getCutOffAt(),
                    payments.getAmount(),
                    payments.getIsAdvancePayment() == 1 ? true : false,
                    payments.getIsCutOff() == 1 ? true : false,
                    payments.getTreg(),
                    1,
                    payments.getIsVoid() == 1 ? true : false,
                    payments.getCompanyId(),
                    payments.getIsAccountReceivable()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchPaymentSyncResponse>() {
                @Override
                public void onResponse(Call<BranchPaymentSyncResponse> call, Response<BranchPaymentSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            paymentsViewModel.updatePaymentSentToServer(payments.getId());
                            Log.d("ToSyncServerPayment", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncServerPayment", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncServerPayment", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchPaymentSyncResponse> call, Throwable t) {
                    Log.d("ToSyncServerPayment", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncMachineDetailsToServer(MachineDetails machineDetails){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchMachineDetailsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), POSApplication.getInstance().getMachineDetails().getCoreId(), new BranchMachineDetailsSyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    machineDetails.getOrCounter(),
                    machineDetails.getXreadingCounter(),
                    machineDetails.getZreadingCounter(),
                    machineDetails.getVoidCounter()
            )).enqueue(new Callback<BranchMachineDetailsSyncResponse>() {
                @Override
                public void onResponse(Call<BranchMachineDetailsSyncResponse> call, Response<BranchMachineDetailsSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            machineDetailsViewModel.updateMachineDetailsSentToServer(machineDetails.getId());
                            Log.d("ToSyncServerMachineDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncServerMachineDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncServerPayment", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchMachineDetailsSyncResponse> call, Throwable t) {
                    Log.d("ToSyncServerMachineDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncDiscountToServer(Discounts discounts){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchDiscountSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchDiscountSyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    discounts.getId(),
                    discounts.getMachineNumber(),
                    discounts.getBranchId(),
                    discounts.getTransactionId(),
                    discounts.getCustomDiscountId(),
                    discounts.getDiscountTypeId(),
                    discounts.getCashierId(),
                    discounts.getAuthorizeId() != 0 ? String.valueOf(discounts.getAuthorizeId()) : null,
                    discounts.getVoidById(),
                    discounts.getCutOffId(),
                    discounts.getShiftNumber(),
                    discounts.getValue(),
                    discounts.getDiscountAmount(),
                    discounts.getVatExemptAmount(),
                    discounts.getIsVoid() == 1 ? true : false,
                    discounts.getIsSentToServer() == 1 ? true : false,
                    discounts.getIsCutOff() == 1 ? true : false,
                    discounts.getDiscountName(),
                    discounts.getType(),
                    discounts.getCashierName(),
                    discounts.getAuthorizeName(),
                    discounts.getVoidBy(),
                    discounts.getVoidAt(),
                    discounts.getTreg(),
                    discounts.getVatExpense(),
                    discounts.getIsZeroRated() == 1 ? true : false,
                    discounts.getGrossAmount(),
                    discounts.getNetAmount(),
                    discounts.getCompanyId()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchDiscountsSyncResponse>() {
                @Override
                public void onResponse(Call<BranchDiscountsSyncResponse> call, Response<BranchDiscountsSyncResponse> response) {
                    if(response.isSuccessful()){
                        discountsViewModel.updateDiscountSentToServer(discounts.getId());
                        Log.d("ToSyncDiscounts", "onResponseSuccess: " + response.body().getMessage());
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncDiscounts", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchDiscountsSyncResponse> call, Throwable t) {
                    Log.d("ToSyncDiscounts", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncDiscountDetailsToServer(DiscountDetails discountDetails){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchDiscountDetailsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchDiscountDetailsSyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    discountDetails.getId(),
                    discountDetails.getDiscountId(),
                    discountDetails.getMachineNumber(),
                    discountDetails.getBranchId(),
                    discountDetails.getCustomDiscountId(),
                    discountDetails.getTransactionId(),
                    discountDetails.getOrderId(),
                    discountDetails.getDiscountTypeId(),
                    discountDetails.getVoidById(),
                    discountDetails.getCutOffId(),
                    discountDetails.getValue(),
                    discountDetails.getDiscountAmount(),
                    discountDetails.getVatExemptAmount(),
                    discountDetails.getType(),
                    discountDetails.getVoidBy(),
                    discountDetails.getVoidAt(),
                    String.valueOf(discountDetails.getShiftNumber()),
                    discountDetails.getTreg(),
                    discountDetails.getIsVoid() == 1 ? true : false,
                    discountDetails.getIsSentToServer() == 1 ? true : false,
                    discountDetails.getIsCutOff() == 1 ? true : false,
                    discountDetails.isVatExempt(),
                    discountDetails.getVatExpense(),
                    discountDetails.isZeroRated(),
                    discountDetails.getCompanyId()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchDiscountDetailsSyncResponse>() {
                @Override
                public void onResponse(Call<BranchDiscountDetailsSyncResponse> call, Response<BranchDiscountDetailsSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            discountDetailsViewModel.updateDiscountDetailsSentToServer(discountDetails.getId());
                            Log.d("ToSyncDiscountDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncDiscountDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncDiscountDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchDiscountDetailsSyncResponse> call, Throwable t) {
                    Log.d("ToSyncDiscountDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncCutOffDetailsToServer(CutOff cutOff){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchCutOffSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchCutOffSyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    cutOff.getId(),
                    cutOff.getEndOfDayId(),
                    cutOff.getMachineNumber(),
                    cutOff.getBranchId(),
                    cutOff.getTotalTransactions(),
                    cutOff.getCashierId(),
                    cutOff.getAdminId(),
                    cutOff.getIsSentToServer(),
                    cutOff.getReadingNumber(),
                    cutOff.getVoidQty(),
                    cutOff.getBeginningOR(),
                    cutOff.getEndingOR(),
                    cutOff.getCashierName(),
                    cutOff.getAdminName(),
                    String.valueOf(cutOff.getShiftNumber()),
                    cutOff.getTreg(),
                    cutOff.getBeginningAmount(),
                    cutOff.getEndingAmount(),
                    cutOff.getGrossSales(),
                    cutOff.getNetSales(),
                    cutOff.getVatableSales(),
                    cutOff.getVatExemptSales(),
                    cutOff.getVatAmount(),
                    cutOff.getVatExpense(),
                    cutOff.getVoidAmount(),
                    cutOff.getTotalChange(),
                    cutOff.getTotalPayout(),
                    cutOff.getTotalServiceCharge(),
                    cutOff.getTotalDiscountAmount(),
                    cutOff.getTotalCost(),
                    cutOff.getTotalSK(),
                    cutOff.getTotalShortOver(),
                    cutOff.getPrintString(),
                    cutOff.getIsComplete(),
                    cutOff.getCompanyId(),
                    cutOff.getTotalReturn()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchCutOffSyncResponse>() {
                @Override
                public void onResponse(Call<BranchCutOffSyncResponse> call, Response<BranchCutOffSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            cutOffViewModel.updateCutOffSentToServer(cutOff.getId());
                            //This will auto logout when all process of the cutoff is done
                            if(cutOff.getIsComplete() == 1){
                                navigation.logout();
                            }
                            Log.d("ToSyncCutOffDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncCutOffDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncCutOffDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchCutOffSyncResponse> call, Throwable t) {
                    Log.d("ToSyncCutOffDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncEndOfDayDetailsToServer(EndOfDay endOfDay, List<EndOffDayProducts> endOffDayProducts){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchEndOfDaySync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchEndOfDaySyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    endOfDay.getId(),
                    endOfDay.getMachineNumber(),
                    endOfDay.getBranchId(),
                    endOfDay.getTotalTransactions(),
                    endOfDay.getCashierId(),
                    endOfDay.getAdminId(),
                    endOfDay.getIsSentToServer(),
                    endOfDay.getReadingNumber(),
                    endOfDay.getVoidQty(),
                    endOfDay.getBeginningOR(),
                    endOfDay.getEndingOR(),
                    endOfDay.getCashierName(),
                    endOfDay.getAdminName(),
                    String.valueOf(endOfDay.getShiftNumber()),
                    endOfDay.getTreg(),
                    endOfDay.getBeginningAmount(),
                    endOfDay.getEndingAmount(),
                    endOfDay.getGrossSales(),
                    endOfDay.getNetSales(),
                    endOfDay.getVatableSales(),
                    endOfDay.getVatExemptSales(),
                    endOfDay.getVatAmount(),
                    endOfDay.getVatExpense(),
                    endOfDay.getVoidAmount(),
                    endOfDay.getTotalChange(),
                    endOfDay.getTotalPayout(),
                    endOfDay.getTotalServiceCharge(),
                    endOfDay.getTotalDiscountAmount(),
                    endOfDay.getTotalCost(),
                    endOfDay.getTotalSK(),
                    endOfDay.getTotalShortOver(),
                    endOffDayProducts,
                    endOfDay.getIsComplete(),
                    endOfDay.getGeneratedDate(),
                    endOfDay.getBeginningCutOffCounter(),
                    endOfDay.getEndingCutOffCounter(),
                    endOfDay.getPrintString(),
                    endOfDay.getCompanyId(),
                    endOfDay.getTotalReturn()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchEndOfDaySyncResponse>() {
                @Override
                public void onResponse(Call<BranchEndOfDaySyncResponse> call, Response<BranchEndOfDaySyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            for(EndOffDayProducts item : endOffDayProducts){
                                SyncEndOfDayProductsToServer(item);
                            }
                            endOfDayViewModel.updateEndOfDaySentToServer(endOfDay.getId());
                            //This will auto logout when all process of the end of day is done
                            if(endOfDay.getIsComplete() == 1){
                                navigation.logout();
                            }
                            Log.d("ToSyncEndOfDayDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncEndOfDayDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncEndOfDayDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchEndOfDaySyncResponse> call, Throwable t) {
                    Log.d("ToSyncEndOfDayDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncSafekeepingDetailsToServer(Safekeeping safekeeping){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchSafekeepingSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new SafekeepingPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    safekeeping.getId(),
                    safekeeping.getMachineNumber(),
                    safekeeping.getBranchId(),
                    safekeeping.getCashierId(),
                    safekeeping.getAuthorizeId(),
                    safekeeping.getCutOffId(),
                    safekeeping.getShiftNumber(),
                    safekeeping.getEndOfDayId(),
                    safekeeping.getAmount(),
                    safekeeping.getShortOver(),
                    safekeeping.getCashierName(),
                    safekeeping.getAuthorizeName(),
                    safekeeping.getTreg(),
                    safekeeping.getIsCutOff() == 1 ? true : false,
                    safekeeping.getIsSentToServer() == 1 ? true : false,
                    safekeeping.getIsAuto() == 1 ? true : false,
                    safekeeping.getCompanyId()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchSafekeepingSyncResponse>() {
                @Override
                public void onResponse(Call<BranchSafekeepingSyncResponse> call, Response<BranchSafekeepingSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            safekeepingViewModel.updateSafekeepingSentToServer(safekeeping.getId());
                            Log.d("ToSyncSafekeepingDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncSafekeepingDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncSafekeepingDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchSafekeepingSyncResponse> call, Throwable t) {
                    Log.d("ToSyncSafekeepingDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncSafekeepingDenominationDetailsToServer(SafekeepingDenomination safekeepingDenomination){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchSafekeepingDenominationsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new SafekeepingDenominationPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    safekeepingDenomination.getBranchId(),
                    safekeepingDenomination.getMachineNumber(),
                    safekeepingDenomination.getId(),
                    safekeepingDenomination.getSafekeepingId(),
                    safekeepingDenomination.getCashDenominationId(),
                    safekeepingDenomination.getQty(),
                    safekeepingDenomination.getShiftNumber(),
                    safekeepingDenomination.getCutOffId(),
                    safekeepingDenomination.getEndOfDayId(),
                    safekeepingDenomination.getAmount(),
                    safekeepingDenomination.getTotal(),
                    safekeepingDenomination.getName(),
                    safekeepingDenomination.getIsCutOff() == 1 ? true : false,
                    safekeepingDenomination.getIsSentToServer() == 1 ? true : false,
                    safekeepingDenomination.getCompanyId()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchSafekeepingDenominationSyncResponse>() {
                @Override
                public void onResponse(Call<BranchSafekeepingDenominationSyncResponse> call, Response<BranchSafekeepingDenominationSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            safekeepingDenominationViewModel.updateSafekeepingDenominationSentToServer(safekeepingDenomination.getId());
                            Log.d("ToSyncSafekeepingDenominationDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncSafekeepingDenominationDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncSafekeepingDenominationDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchSafekeepingDenominationSyncResponse> call, Throwable t) {
                    Log.d("ToSyncSafekeepingDenominationDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncPaymentOtherInformationDetailsToServer(PaymentOtherInformations paymentOtherInformations){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")) {
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchPaymentOtherInformationSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchPaymentOtherInformationsSyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    paymentOtherInformations.getId(),
                    paymentOtherInformations.getMachineId(),
                    paymentOtherInformations.getBranchId(),
                    paymentOtherInformations.getTransactionId(),
                    paymentOtherInformations.getPaymentId(),
                    paymentOtherInformations.getName(),
                    paymentOtherInformations.getValue(),
                    paymentOtherInformations.getIsCutOff() == 1 ? true : false,
                    paymentOtherInformations.getCutOffId(),
                    paymentOtherInformations.getIsVoid() == 1 ? true : false,
                    paymentOtherInformations.getIsSentToServer() == 1 ? true : false,
                    paymentOtherInformations.getTreg(),
                    paymentOtherInformations.getCompanyId()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchPaymentOtherInformationsSyncResponse>() {
                @Override
                public void onResponse(Call<BranchPaymentOtherInformationsSyncResponse> call, Response<BranchPaymentOtherInformationsSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            paymentOtherInformationsViewModel.updatePaymentOtherInformationSentToServer(paymentOtherInformations.getId());
                            Log.d("ToSyncPaymentOtherInformationDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncPaymentOtherInformationDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncPaymentOtherInformationDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchPaymentOtherInformationsSyncResponse> call, Throwable t) {
                    Log.d("ToSyncPaymentOtherInformationDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncDiscountOtherInformationDetailsToServer(DiscountOtherInformations discountOtherInformations){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")) {
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchDiscountOtherInformationSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchDiscountOtherInformationsSyncPayload(
                POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    discountOtherInformations.getId(),
                    discountOtherInformations.getMachineId(),
                    discountOtherInformations.getBranchId(),
                    discountOtherInformations.getTransactionId(),
                    discountOtherInformations.getDiscountId(),
                    discountOtherInformations.getName(),
                    discountOtherInformations.getValue(),
                    discountOtherInformations.getIsCutOff() == 1 ? true : false,
                    discountOtherInformations.getCutOffId(),
                    discountOtherInformations.getIsVoid() == 1 ? true : false,
                    discountOtherInformations.getIsSentToServer() == 1 ? true : false,
                    discountOtherInformations.getTreg(),
                    discountOtherInformations.getCompanyId()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchDiscountOtherInformationsSyncResponse>() {
                @Override
                public void onResponse(Call<BranchDiscountOtherInformationsSyncResponse> call, Response<BranchDiscountOtherInformationsSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            discountOtherInformationsViewModel.updateDiscountOtherInformationSentToServer(discountOtherInformations.getId());
                            Log.d("ToSyncDiscountOtherInformationDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncDiscountOtherInformationDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncDiscountOtherInformationDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchDiscountOtherInformationsSyncResponse> call, Throwable t) {
                    Log.d("ToSyncDiscountOtherInformationDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncCutOffDepartmentsDetailsToServer(CutOffDepartments cutOffDepartments){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")) {
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchCutOffDepartmentsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchCutOffDepartmentsSyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    cutOffDepartments.getId(),
                    cutOffDepartments.getMachineNumber(),
                    cutOffDepartments.getBranchId(),
                    cutOffDepartments.getCutOffId(),
                    cutOffDepartments.getDepartmentId(),
                    cutOffDepartments.getName(),
                    cutOffDepartments.getTransactionCount(),
                    cutOffDepartments.getAmount(),
                    cutOffDepartments.getEndOfDayId(),
                    cutOffDepartments.getIsSentToServer() == 1 ? true : false,
                    cutOffDepartments.getTreg(),
                    cutOffDepartments.getCutOffId() != 0 ? true : false,
                    cutOffDepartments.getCompanyId()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchCutOffDepartmentsSyncResponse>() {
                @Override
                public void onResponse(Call<BranchCutOffDepartmentsSyncResponse> call, Response<BranchCutOffDepartmentsSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            cutOffDepartmentsViewModel.updateCutOffDepartmentsSentToServer(cutOffDepartments.getId());
                            Log.d("ToSyncCutOffDepartmentsDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncCutOffDepartmentsDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncCutOffDepartmentsDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchCutOffDepartmentsSyncResponse> call, Throwable t) {
                    Log.d("ToSyncCutOffDepartmentsDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncCutOffDiscountsDetailsToServer(CutOffDiscounts cutOffDiscounts){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")) {
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchCutOffDiscountsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchCutOffDiscountsSyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    cutOffDiscounts.getId(),
                    cutOffDiscounts.getMachineNumber(),
                    cutOffDiscounts.getBranchId(),
                    cutOffDiscounts.getCutOffId(),
                    cutOffDiscounts.getDiscountTypeId(),
                    cutOffDiscounts.getName(),
                    cutOffDiscounts.getTransactionCount(),
                    cutOffDiscounts.getAmount(),
                    cutOffDiscounts.getEndOfDayId(),
                    cutOffDiscounts.getIsSentToServer() == 1 ? true : false,
                    cutOffDiscounts.getTreg(),
                    cutOffDiscounts.getCutOffId() != 0 ? true : false,
                    cutOffDiscounts.getCompanyId(),
                    cutOffDiscounts.getIsZeroRated()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchCutOffDiscountsSyncResponse>() {
                @Override
                public void onResponse(Call<BranchCutOffDiscountsSyncResponse> call, Response<BranchCutOffDiscountsSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            cutOffDiscountsViewModel.updateCutOffDiscountsSentToServer(cutOffDiscounts.getId());
                            Log.d("ToSyncCutOffDiscountsDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncCutOffDiscountsDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncCutOffDiscountsDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchCutOffDiscountsSyncResponse> call, Throwable t) {
                    Log.d("ToSyncCutOffDiscountsDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncCutOffPaymentsDetailsToServer(CutOffPayments cutOffPayments){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")) {
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchCutOffPaymentsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchCutOffPaymentsSyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    cutOffPayments.getId(),
                    cutOffPayments.getMachineNumber(),
                    cutOffPayments.getBranchId(),
                    cutOffPayments.getCutOffId(),
                    cutOffPayments.getPaymentTypeId(),
                    cutOffPayments.getName(),
                    cutOffPayments.getTransactionCount(),
                    cutOffPayments.getAmount(),
                    cutOffPayments.getEndOfDayId(),
                    cutOffPayments.getIsSentToServer() == 1 ? true : false,
                    cutOffPayments.getTreg(),
                    cutOffPayments.getCutOffId() != 0 ? true : false,
                    cutOffPayments.getCompanyId()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchCutOffPaymentsSyncResponse>() {
                @Override
                public void onResponse(Call<BranchCutOffPaymentsSyncResponse> call, Response<BranchCutOffPaymentsSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            cutOffPaymentsViewModel.updateCutOffPaymentsSentToServer(cutOffPayments.getId());
                            Log.d("ToSyncCutOffPaymentsDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncCutOffPaymentsDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncCutOffPaymentsDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchCutOffPaymentsSyncResponse> call, Throwable t) {
                    Log.d("ToSyncCutOffPaymentsDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncEndOfDayDepartmentsDetailsToServer(EndOfDayDepartments endOfDayDepartments){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")) {
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchEndOfDayDepartmentsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchEndOfDayDepartmentsSyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    endOfDayDepartments.getId(),
                    endOfDayDepartments.getMachineNumber(),
                    endOfDayDepartments.getBranchId(),
                    endOfDayDepartments.getEndOfDayId(),
                    endOfDayDepartments.getDepartmentId(),
                    endOfDayDepartments.getName(),
                    endOfDayDepartments.getTransactionCount(),
                    endOfDayDepartments.getAmount(),
                    endOfDayDepartments.getIsSentToServer() == 1 ? true : false,
                    endOfDayDepartments.getTreg(),
                    endOfDayDepartments.getCompanyId()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchEndOfDayDepartmentsSyncResponse>() {
                @Override
                public void onResponse(Call<BranchEndOfDayDepartmentsSyncResponse> call, Response<BranchEndOfDayDepartmentsSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            endOfDayDepartmentsViewModel.updateEndOfDayDepartmentsSentToServer(endOfDayDepartments.getId());
                            Log.d("ToSyncEndOfDayDepartmentsDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncEndOfDayDepartmentsDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncEndOfDayDepartmentsDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchEndOfDayDepartmentsSyncResponse> call, Throwable t) {
                    Log.d("ToSyncEndOfDayDepartmentsDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncEndOfDayDiscountsDetailsToServer(EndOfDayDiscounts endOfDayDiscounts){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")) {
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchEndOfDayDiscountsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchEndOfDayDiscountsSyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    endOfDayDiscounts.getId(),
                    endOfDayDiscounts.getMachineNumber(),
                    endOfDayDiscounts.getBranchId(),
                    endOfDayDiscounts.getEndOfDayId(),
                    endOfDayDiscounts.getDiscountTypeId(),
                    endOfDayDiscounts.getName(),
                    endOfDayDiscounts.getTransactionCount(),
                    endOfDayDiscounts.getAmount(),
                    endOfDayDiscounts.getIsSentToServer() == 1 ? true : false,
                    endOfDayDiscounts.getTreg(),
                    endOfDayDiscounts.getCompanyId(),
                    endOfDayDiscounts.getIsZeroRated()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchEndOfDayDiscountsSyncResponse>() {
                @Override
                public void onResponse(Call<BranchEndOfDayDiscountsSyncResponse> call, Response<BranchEndOfDayDiscountsSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            endOfDayDiscountsViewModel.updateEndOfDayDiscountsSentToServer(endOfDayDiscounts.getId());
                            Log.d("ToSyncEndOfDayDiscountsDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncEndOfDayDiscountsDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncEndOfDayDiscountsDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchEndOfDayDiscountsSyncResponse> call, Throwable t) {
                    Log.d("ToSyncEndOfDayDiscountsDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncEndOfDayPaymentsDetailsToServer(EndOfDayPayments endOfDayPayments){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")) {
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchEndOfDayPaymentsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchEndOfDayPaymentsSyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    endOfDayPayments.getId(),
                    endOfDayPayments.getMachineNumber(),
                    endOfDayPayments.getBranchId(),
                    endOfDayPayments.getEndOfDayId(),
                    endOfDayPayments.getPaymentTypeId(),
                    endOfDayPayments.getName(),
                    endOfDayPayments.getTransactionCount(),
                    endOfDayPayments.getAmount(),
                    endOfDayPayments.getIsSentToServer() == 1 ? true : false,
                    endOfDayPayments.getTreg(),
                    endOfDayPayments.getCompanyId()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchEndOfDayPaymentsSyncResponse>() {
                @Override
                public void onResponse(Call<BranchEndOfDayPaymentsSyncResponse> call, Response<BranchEndOfDayPaymentsSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            endOfDayPaymentsViewModel.updateEndOfDayPaymentsSentToServer(endOfDayPayments.getId());
                            Log.d("ToSyncEndOfDayPaymentsDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncEndOfDayPaymentsDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncEndOfDayPaymentsDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchEndOfDayPaymentsSyncResponse> call, Throwable t) {
                    Log.d("ToSyncEndOfDayPaymentsDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncCashFundsToServer(CashFund cashFund){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")) {
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchCashFundsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchCashFundSyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    cashFund.getId(),
                    cashFund.getMachineNumber(),
                    cashFund.getBranchId(),
                    cashFund.getAmount(),
                    cashFund.getCashierId(),
                    cashFund.getCashierName(),
                    cashFund.getIsCutOff() == 1 ? true : false,
                    cashFund.getCutOffId(),
                    cashFund.getEndOfDayId(),
                    cashFund.getIsSentToServer() == 1 ? true : false,
                    cashFund.getShiftNumber(),
                    cashFund.getTreg(),
                    cashFund.getCompanyId()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchCashFundSyncResponse>() {
                @Override
                public void onResponse(Call<BranchCashFundSyncResponse> call, Response<BranchCashFundSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            cashFundViewModel.updateCashFundSentToServer(cashFund.getId());
                            Log.d("ToSyncCashFundsDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncCashFundsDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncCashFundsDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchCashFundSyncResponse> call, Throwable t) {
                    Log.d("ToSyncCashFundsDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncCashFundDenominationsToServer(CashFundDenomination cashFundDenomination){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")) {
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchCashFundDenominationsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchCashFundDenominationSyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    cashFundDenomination.getId(),
                    cashFundDenomination.getMachineNumber(),
                    cashFundDenomination.getBranchId(),
                    cashFundDenomination.getCashFundId(),
                    cashFundDenomination.getCashDenominationId(),
                    cashFundDenomination.getName(),
                    cashFundDenomination.getAmount(),
                    cashFundDenomination.getQty(),
                    cashFundDenomination.getTotal(),
                    cashFundDenomination.getIsCutOff() == 1 ? true : false,
                    cashFundDenomination.getCutOffId(),
                    cashFundDenomination.getEndOfDayId(),
                    cashFundDenomination.getIsSentToServer() == 1 ? true : false,
                    cashFundDenomination.getShiftNumber(),
                    cashFundDenomination.getTreg(),
                    cashFundDenomination.getCompanyId()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchCashFundDenominationSyncResponse>() {
                @Override
                public void onResponse(Call<BranchCashFundDenominationSyncResponse> call, Response<BranchCashFundDenominationSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            cashFundDenominationViewModel.updateCashFundDenominationSentToServer(cashFundDenomination.getId());
                            Log.d("ToSyncCashFundDenominationsDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncCashFundDenominationsDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncCashFundDenominationsDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchCashFundDenominationSyncResponse> call, Throwable t) {
                    Log.d("ToSyncCashFundDenominationsDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncAuditTrailsToServer(AuditTrails auditTrails){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")) {
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchAuditTrailsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchAuditTrailsSyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    auditTrails.getId(),
                    auditTrails.getMachineNumber(),
                    auditTrails.getBranchId(),
                    auditTrails.getUserId(),
                    auditTrails.getUserName(),
                    auditTrails.getTransactionId(),
                    auditTrails.getAction(),
                    auditTrails.getDescription(),
                    auditTrails.getAuthorizeId(),
                    auditTrails.getAuthorizeName(),
                    auditTrails.getIsSentToServer() == 1 ? true : false,
                    auditTrails.getTreg(),
                    auditTrails.getOrderId(),
                    auditTrails.getPriceChangeReasonId(),
                    auditTrails.getCompanyId()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchAuditTrailsSyncResponse>() {
                @Override
                public void onResponse(Call<BranchAuditTrailsSyncResponse> call, Response<BranchAuditTrailsSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            auditTrailsViewModel.updateAuditTrailSentToServer(auditTrails.getId());
                            Log.d("ToSyncAuditTrailsDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncAuditTrailsDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncAuditTrailsDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchAuditTrailsSyncResponse> call, Throwable t) {
                    Log.d("ToSyncAuditTrailsDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncOfficialReceiptInformationToServer(OfficialReceiptInformation officialReceiptInformation){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")){
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchOfficialReceiptInformationSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchOfficialReceiptInformationSyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    officialReceiptInformation.getId(),
                    officialReceiptInformation.getMachineNumber(),
                    officialReceiptInformation.getBranchId(),
                    officialReceiptInformation.getCompanyId(),
                    officialReceiptInformation.getTransactionId(),
                    officialReceiptInformation.getName(),
                    officialReceiptInformation.getAddress(),
                    officialReceiptInformation.getTin(),
                    officialReceiptInformation.getBusinessStyle(),
                    officialReceiptInformation.getIsVoid() == 1 ? true : false,
                    officialReceiptInformation.getVoidBy(),
                    officialReceiptInformation.getVoidName(),
                    officialReceiptInformation.getVoidAt(),
                    officialReceiptInformation.getIsSentToServer() == 1 ? true : false,
                    officialReceiptInformation.getTreg()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchOfficialReceiptInformationSyncResponse>() {
                @Override
                public void onResponse(Call<BranchOfficialReceiptInformationSyncResponse> call, Response<BranchOfficialReceiptInformationSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            officialReceiptInformationViewModel.updateOfficialReceiptInformationSentToServer(officialReceiptInformation.getId());
                            Log.d("ToSyncOfficalReceiptInformationDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncOfficalReceiptInformationDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncOfficalReceiptInformationDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchOfficialReceiptInformationSyncResponse> call, Throwable t) {
                    Log.d("ToSyncOfficalReceiptInformationDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncSpotAuditToServer(SpotAudit spotAudit){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")) {
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchSpotAuditSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchSpotAuditSyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    spotAudit.getId(),
                    spotAudit.getMachineNumber(),
                    spotAudit.getBranchId(),
                    spotAudit.getCompanyId(),
                    spotAudit.getBeginningOR(),
                    spotAudit.getEndingOR(),
                    spotAudit.getBeginningAmount(),
                    spotAudit.getEndingAmount(),
                    spotAudit.getTotalTransactions(),
                    spotAudit.getGrossSales(),
                    spotAudit.getNetSales(),
                    spotAudit.getVatableSales(),
                    spotAudit.getVatExemptSales(),
                    spotAudit.getVatAmount(),
                    spotAudit.getVatExpense(),
                    spotAudit.getVoidQty(),
                    spotAudit.getVoidAmount(),
                    spotAudit.getTotalChange(),
                    spotAudit.getTotalPayout(),
                    spotAudit.getTotalServiceCharge(),
                    spotAudit.getTotalDiscountAmount(),
                    spotAudit.getTotalCost(),
                    spotAudit.getSafekeepingAmount(),
                    spotAudit.getSafekeepingShortOver(),
                    spotAudit.getTotalSK(),
                    spotAudit.getTotalShortOver(),
                    spotAudit.getCashierId(),
                    spotAudit.getCashierName(),
                    spotAudit.getAdminId(),
                    spotAudit.getAdminName(),
                    spotAudit.getShiftNumber(),
                    spotAudit.getIsCutOff() == 1 ? true : false,
                    spotAudit.getCutOffId(),
                    spotAudit.getIsSentToServer() == 1 ? true : false,
                    spotAudit.getTreg()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchSpotAuditSyncResponse>() {
                @Override
                public void onResponse(Call<BranchSpotAuditSyncResponse> call, Response<BranchSpotAuditSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            spotAuditViewModel.updateSpotAuditSentToServer(spotAudit.getId());
                            Log.d("ToSyncSpotAuditDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncSpotAuditDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncSpotAuditDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchSpotAuditSyncResponse> call, Throwable t) {
                    Log.d("ToSyncSpotAuditDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncSpotAuditDenominationToServer(SpotAuditDenomination spotAuditDenomination){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")) {
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchSpotAuditDenominationSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchSpotAuditDenominationSyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    spotAuditDenomination.getId(),
                    spotAuditDenomination.getMachineNumber(),
                    spotAuditDenomination.getBranchId(),
                    spotAuditDenomination.getCompanyId(),
                    spotAuditDenomination.getSpotAuditId(),
                    spotAuditDenomination.getCashDenominationId(),
                    spotAuditDenomination.getName(),
                    spotAuditDenomination.getAmount(),
                    spotAuditDenomination.getQty(),
                    spotAuditDenomination.getTotal(),
                    spotAuditDenomination.getIsCutOff() == 1 ? true : false,
                    spotAuditDenomination.getCutOffId(),
                    spotAuditDenomination.getIsSentToServer() == 1 ? true : false,
                    spotAuditDenomination.getShiftNumber(),
                    spotAuditDenomination.getTreg()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchSpotAuditDenominationSyncResponse>() {
                @Override
                public void onResponse(Call<BranchSpotAuditDenominationSyncResponse> call, Response<BranchSpotAuditDenominationSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            spotAuditDenominationViewModel.updateSpotAuditDenominationSentToServer(spotAuditDenomination.getId());
                            Log.d("ToSyncSpotAuditDenominationDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncSpotAuditDenominationDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncSpotAuditDenominationDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchSpotAuditDenominationSyncResponse> call, Throwable t) {
                    Log.d("ToSyncSpotAuditDenominationDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void SyncCutOffProductsToServer(CutOffProducts cutOffProducts){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")) {
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchCutOffProductsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchCutOffProductsSyncPayload(
                    POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    cutOffProducts.getId(),
                    cutOffProducts.getMachineNumber(),
                    cutOffProducts.getBranchId(),
                    cutOffProducts.getCompanyId(),
                    cutOffProducts.getCutOffId(),
                    cutOffProducts.getProductId(),
                    cutOffProducts.getQty(),
                    cutOffProducts.getIsCutOff() == 1 ? true : false,
                    cutOffProducts.getCutOffAt(),
                    cutOffProducts.getEndOfDayId(),
                    cutOffProducts.getIsSentToServer() == 1 ? true : false,
                    cutOffProducts.getTreg()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchCutOffProductsSyncResponse>() {
                @Override
                public void onResponse(Call<BranchCutOffProductsSyncResponse> call, Response<BranchCutOffProductsSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            cutOffProductsViewModel.updateCutOffProductsSentToServer(cutOffProducts.getId());
                            Log.d("ToSyncCutOffProductsDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncCutOffProductsDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncCutOffProductsDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchCutOffProductsSyncResponse> call, Throwable t) {
                    Log.d("ToSyncCutOffProductsDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    private void SyncEndOfDayProductsToServer(EndOffDayProducts endOffDayProducts){
        if(POSApplication.getInstance().getMachineDetails().getMachineType().equals("cashier")) {
            RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
            requestAPI.BranchEndOfDayProductsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchEndOfDayProductsSyncPayload(
                POSApplication.getInstance().getDeviceDetails().getCoreId(),
                    endOffDayProducts.getId(),
                    endOffDayProducts.getMachineNumber(),
                    endOffDayProducts.getBranchId(),
                    endOffDayProducts.getCompanyId(),
                    endOffDayProducts.getEndOfDayId(),
                    endOffDayProducts.getProductId(),
                    endOffDayProducts.getQty(),
                    endOffDayProducts.getIsSentToServer() == 1 ? true : false,
                    endOffDayProducts.getTreg()
            ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchEndOfDayProductsSyncResponse>() {
                @Override
                public void onResponse(Call<BranchEndOfDayProductsSyncResponse> call, Response<BranchEndOfDayProductsSyncResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess()){
                            endOfDayProductsViewModel.updateEndOffDayProductsSentToServer(endOffDayProducts.getId());
                            Log.d("ToSyncEndOfDayProductsDetails", "onResponseSuccess: " + response.body().getMessage());
                        }
                        else{
                            Log.d("ToSyncEndOfDayProductsDetails", "onResponseError: " + response.body().getMessage());
                        }
                    }
                    else{
                        ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                        Log.d("ToSyncEndOfDayProductsDetails", "onResponseError: " + errorBody.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BranchEndOfDayProductsSyncResponse> call, Throwable t) {
                    Log.d("ToSyncEndOfDayProductsDetails", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    //Take Order Sync
    public void SyncTakeOrderTransactionToServer(Transactions transactions){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.BranchTakeOrderTransactionSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchTransactionTakeOrderSyncPayload(
                transactions.getId(),
                transactions.getMachineNumber(),
                transactions.getCashierId(),
                transactions.getTakeOrderId() == 0 ? null : String.valueOf(transactions.getTakeOrderId()),
                transactions.getVoidById() == 0 ? null : String.valueOf(transactions.getVoidById()),
                transactions.getIsBackoutId() == 0 ? null : String.valueOf(transactions.getIsBackoutId()),
                transactions.getChargeAccountId() == 0 ? null : String.valueOf(transactions.getChargeAccountId()),
                transactions.getCutoffId(),
                transactions.getBranchId(),
                transactions.getControlNumber(),
                transactions.getReceiptNumber(),
                transactions.getType(),
                transactions.getCashierName(),
                transactions.getTakeOrderName(),
                transactions.getShiftNumber(),
                transactions.getVoidBy(),
                transactions.getVoidAt(),
                transactions.getBackoutBy(),
                transactions.getChargeAccountName(),
                transactions.getCompletedAt(),
                transactions.getCutoffAt(),
                transactions.getGrossSales(),
                transactions.getNetSales(),
                transactions.getVatableSales(),
                transactions.getVatExemptSales(),
                transactions.getVatAmount(),
                transactions.getDiscountAmount(),
                transactions.getTenderAmount(),
                transactions.getChange(),
                transactions.getServiceCharge(),
                transactions.getTotalUnitCost(),
                transactions.getTotalVoidAmount(),
                transactions.getIsVoid() == 1 ? true : false,
                transactions.getIsBackout() == 1 ? true : false,
                transactions.getIsAccountReceivable() == 1 ? true : false,
                transactions.getIsSentToServer() == 1 ? true : false,
                false,
                transactions.getIsCutoff() == 1 ? true : false,
                transactions.getTreg(),
                transactions.getTotalQuantity(),
                transactions.getTotalVoidQty(),
                transactions.getVatExpense(),
                transactions.getIsReturn() == 1 ? true : false,
                0,
                0,
                0,
                transactions.getCustomerName()
        ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchTransactionSyncResponse>() {
            @Override
            public void onResponse(Call<BranchTransactionSyncResponse> call, Response<BranchTransactionSyncResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        Log.d("ToSyncTakeOrderTransactionToServer", "onResponseSuccess: " + response.body().getMessage());
                    }
                    else{
                        Log.d("ToSyncTakeOrderTransactionToServer", "onResponseError: " + response.body().getMessage());
                    }
                }
                else{
                    ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                    Log.d("ToSyncTakeOrderTransactionToServer", "onResponseError: " + errorBody.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BranchTransactionSyncResponse> call, Throwable t) {
                Log.d("ToSyncServerTransaction", "onFailure: " + t.getMessage());
            }
        });
    }

    public void SyncTakeOrderTransactionCompleteToServer(Transactions transactions){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.BranchTakeOrderTransactionSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchTransactionTakeOrderSyncPayload(
                transactions.getId(),
                transactions.getMachineNumber(),
                transactions.getCashierId(),
                transactions.getTakeOrderId() == 0 ? null : String.valueOf(transactions.getTakeOrderId()),
                transactions.getVoidById() == 0 ? null : String.valueOf(transactions.getVoidById()),
                transactions.getIsBackoutId() == 0 ? null : String.valueOf(transactions.getIsBackoutId()),
                transactions.getChargeAccountId() == 0 ? null : String.valueOf(transactions.getChargeAccountId()),
                transactions.getCutoffId(),
                transactions.getBranchId(),
                transactions.getControlNumber(),
                transactions.getReceiptNumber(),
                transactions.getType(),
                transactions.getCashierName(),
                transactions.getTakeOrderName(),
                transactions.getShiftNumber(),
                transactions.getVoidBy(),
                transactions.getVoidAt(),
                transactions.getBackoutBy(),
                transactions.getChargeAccountName(),
                transactions.getCompletedAt(),
                transactions.getCutoffAt(),
                transactions.getGrossSales(),
                transactions.getNetSales(),
                transactions.getVatableSales(),
                transactions.getVatExemptSales(),
                transactions.getVatAmount(),
                transactions.getDiscountAmount(),
                transactions.getTenderAmount(),
                transactions.getChange(),
                transactions.getServiceCharge(),
                transactions.getTotalUnitCost(),
                transactions.getTotalVoidAmount(),
                transactions.getIsVoid() == 1 ? true : false,
                transactions.getIsBackout() == 1 ? true : false,
                transactions.getIsAccountReceivable() == 1 ? true : false,
                transactions.getIsSentToServer() == 1 ? true : false,
                true,
                transactions.getIsCutoff() == 1 ? true : false,
                transactions.getTreg(),
                transactions.getTotalQuantity(),
                transactions.getTotalVoidQty(),
                transactions.getVatExpense(),
                transactions.getIsReturn() == 1 ? true : false,
                0,
                0,
                0,
                transactions.getCustomerName()
        ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchTransactionSyncResponse>() {
            @Override
            public void onResponse(Call<BranchTransactionSyncResponse> call, Response<BranchTransactionSyncResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        Log.d("ToSyncTakeOrderTransactionToServer", "onResponseSuccess: " + response.body().getMessage());
                    }
                    else{
                        Log.d("ToSyncTakeOrderTransactionToServer", "onResponseError: " + response.body().getMessage());
                    }
                }
                else{
                    ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                    Log.d("ToSyncTakeOrderTransactionToServer", "onResponseError: " + errorBody.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BranchTransactionSyncResponse> call, Throwable t) {
                Log.d("ToSyncServerTransaction", "onFailure: " + t.getMessage());
            }
        });
    }

    public void SyncTakeOrderOrdersToServer(Orders orders){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.BranchTakeOrderOrdersSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchOrderTakeOrderSyncPayload(
                orders.getId(),
                orders.getMachineNumber(),
                orders.getTransactionId(),
                orders.getProductId(),
                orders.getDepartmentId(),
                orders.getCategoryId(),
                orders.getSubCategoryId(),
                orders.getUnitId(),
                orders.getIsBackoutId() == 0 ? null : String.valueOf(orders.getIsBackoutId()),
                orders.getBranchId(),
                orders.getCode(),
                orders.getName(),
                orders.getDescription(),
                orders.getAbbreviation(),
                orders.getDepartmentName(),
                orders.getCategoryName(),
                orders.getSubCategoryName(),
                orders.getUnitName(),
                orders.getVoidBy(),
                orders.getVoidAt(),
                orders.getBackoutBy(),
                orders.getCompletedAt(),
                orders.getCost(),
                orders.getQty(),
                orders.getAmount(),
                orders.getOriginalAmount(),
                orders.getGross(),
                orders.getTotal(),
                orders.getTotalCost(),
                orders.getVatAmount(),
                orders.getVatableSales(),
                orders.getVatExemptSales(),
                orders.getDiscountAmount(),
                orders.getMinAmountSold(),
                orders.getIsVatable() == 1 ? true : false,
                orders.getIsVoid() == 1 ? true : false,
                orders.getIsBackout() == 1 ? true : false,
                orders.getIsPaid() == 1 ? true : false,
                orders.getIsSentToServer() == 1 ? true : false,
                orders.getIsCompleted() == 1 ? true : false,
                orders.getIsCutOff() == 1 ? true : false,
                orders.getCutOffId(),
                orders.getCutOffAt(),
                orders.getTreg(),
                orders.getIsDiscountExempt() == 1 ? true : false,
                orders.getIsOpenPrice() == 1 ? true : false,
                orders.getWithSerial() == 1 ? true : false,
                orders.getVatExpense(),
                orders.getIsReturn() == 1 ? true : false
        ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchOrderSyncResponse>() {
            @Override
            public void onResponse(Call<BranchOrderSyncResponse> call, Response<BranchOrderSyncResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        Log.d("ToSyncTakeOrderOrdersToServer", "onResponseSuccess: " + response.body().getMessage());
                    }
                    else{
                        Log.d("ToSyncTakeOrderOrdersToServer", "onResponseError: " + response.body().getMessage());
                    }
                }
                else{
                    ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                    Log.d("ToSyncTakeOrderOrdersToServer", "onResponseError: " + errorBody.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BranchOrderSyncResponse> call, Throwable t) {
                Log.d("ToSyncTakeOrderOrdersToServer", "onFailure: " + t.getMessage());
            }
        });
    }

    public void SyncTakeOrderDiscountsToServer(Discounts discounts){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.BranchTakeOrderDiscountsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchDiscountTakeOrderSyncPayload(
                POSApplication.getInstance().getDeviceDetails().getCoreId(),
                discounts.getId(),
                discounts.getMachineNumber(),
                discounts.getBranchId(),
                discounts.getTransactionId(),
                discounts.getCustomDiscountId(),
                discounts.getDiscountTypeId(),
                discounts.getCashierId(),
                String.valueOf(discounts.getAuthorizeId()),
                discounts.getVoidById(),
                discounts.getCutOffId(),
                discounts.getShiftNumber(),
                discounts.getValue(),
                discounts.getDiscountAmount(),
                discounts.getVatExemptAmount(),
                discounts.getIsVoid() == 1 ? true : false,
                discounts.getIsSentToServer() == 1 ? true : false,
                discounts.getIsCutOff() == 1 ? true : false,
                discounts.getDiscountName(),
                discounts.getType(),
                discounts.getCashierName(),
                discounts.getAuthorizeName(),
                discounts.getVoidBy(),
                discounts.getVoidAt(),
                discounts.getTreg(),
                discounts.getVatExpense(),
                discounts.getIsZeroRated() == 1 ? true : false
        ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchDiscountsSyncResponse>() {
            @Override
            public void onResponse(Call<BranchDiscountsSyncResponse> call, Response<BranchDiscountsSyncResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        Log.d("ToSyncTakeOrderDiscountsToServer", "onResponseSuccess: " + response.body().getMessage());
                    }
                    else{
                        Log.d("ToSyncTakeOrderDiscountsToServer", "onResponseError: " + response.body().getMessage());
                    }
                }
                else{
                    ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                    Log.d("ToSyncTakeOrderDiscountsToServer", "onResponseError: " + errorBody.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BranchDiscountsSyncResponse> call, Throwable t) {
                Log.d("ToSyncTakeOrderDiscountsToServer", "onFailure: " + t.getMessage());
            }
        });
    }

    public void SyncTakeOrderDiscountDetailsToServer(DiscountDetails discountDetails){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.BranchTakeOrderDiscountDetailsSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchDiscountDetailsTakeOrderSyncPayload(
                POSApplication.getInstance().getDeviceDetails().getCoreId(),
                discountDetails.getId(),
                discountDetails.getDiscountId(),
                discountDetails.getMachineNumber(),
                discountDetails.getBranchId(),
                discountDetails.getCustomDiscountId(),
                discountDetails.getTransactionId(),
                discountDetails.getOrderId(),
                discountDetails.getDiscountTypeId(),
                discountDetails.getVoidById(),
                discountDetails.getCutOffId(),
                discountDetails.getValue(),
                discountDetails.getDiscountAmount(),
                discountDetails.getVatExemptAmount(),
                discountDetails.getType(),
                discountDetails.getVoidBy(),
                discountDetails.getVoidAt(),
                String.valueOf(discountDetails.getShiftNumber()),
                discountDetails.getTreg(),
                discountDetails.getIsVoid() == 1 ? true : false,
                discountDetails.getIsSentToServer() == 1 ? true : false,
                discountDetails.getIsCutOff() == 1 ? true : false,
                discountDetails.isVatExempt(),
                discountDetails.getVatExpense(),
                discountDetails.isZeroRated(),
                discountDetails.getCompanyId()
        ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchDiscountDetailsSyncResponse>() {
            @Override
            public void onResponse(Call<BranchDiscountDetailsSyncResponse> call, Response<BranchDiscountDetailsSyncResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        Log.d("ToSyncTakeOrderDiscountDetailsToServer", "onResponseSuccess: " + response.body().getMessage());
                    }
                    else{
                        Log.d("ToSyncTakeOrderDiscountDetailsToServer", "onResponseError: " + response.body().getMessage());
                    }
                }
                else{
                    ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                    Log.d("ToSyncTakeOrderDiscountDetailsToServer", "onResponseError: " + errorBody.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BranchDiscountDetailsSyncResponse> call, Throwable t) {
                Log.d("ToSyncTakeOrderDiscountDetailsToServer", "onFailure: " + t.getMessage());
            }
        });
    }

    public void SyncTakeOrderDiscountOtherInformationToServer(DiscountOtherInformations discountOtherInformations){
        RequestAPI requestAPI = APIRequest.getInstance().create(RequestAPI.class);
        requestAPI.BranchTakeOrderDiscountOtherInformationSync("Bearer " + EncryptDecrypt.getInstance().decrypt(POSApplication.getInstance().getServerUserAuthentication().getToken()), new BranchDiscountOtherInformationsTakeOrderSyncPayload(
                POSApplication.getInstance().getDeviceDetails().getCoreId(),
                discountOtherInformations.getId(),
                discountOtherInformations.getMachineId(),
                discountOtherInformations.getBranchId(),
                discountOtherInformations.getTransactionId(),
                discountOtherInformations.getDiscountId(),
                discountOtherInformations.getName(),
                discountOtherInformations.getValue(),
                discountOtherInformations.getIsCutOff() == 1 ? true : false,
                discountOtherInformations.getCutOffId(),
                discountOtherInformations.getIsVoid() == 1 ? true : false,
                discountOtherInformations.getIsSentToServer() == 1 ? true : false,
                discountOtherInformations.getTreg()
        ), POSApplication.getInstance().getDeviceDetails().getCoreId()).enqueue(new Callback<BranchDiscountOtherInformationsSyncResponse>() {
            @Override
            public void onResponse(Call<BranchDiscountOtherInformationsSyncResponse> call, Response<BranchDiscountOtherInformationsSyncResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        Log.d("ToSyncTakeOrderDiscountOtherInformationToServer", "onResponseSuccess: " + response.body().getMessage());
                    }
                    else{
                        Log.d("ToSyncTakeOrderDiscountOtherInformationToServer", "onResponseError: " + response.body().getMessage());
                    }
                }
                else{
                    ErrorBody errorBody = gson.fromJson(response.errorBody().charStream(), ErrorBody.class);
                    Log.d("ToSyncTakeOrderDiscountOtherInformationToServer", "onResponseError: " + errorBody.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BranchDiscountOtherInformationsSyncResponse> call, Throwable t) {
                Log.d("ToSyncTakeOrderDiscountOtherInformationToServer", "onFailure: " + t.getMessage());
            }
        });
    }

}
