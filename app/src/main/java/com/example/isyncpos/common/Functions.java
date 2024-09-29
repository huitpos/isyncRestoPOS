package com.example.isyncpos.common;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.isyncpos.POSApplication;
import com.example.isyncpos.entity.CutOffDepartments;
import com.example.isyncpos.entity.CutOffDiscounts;
import com.example.isyncpos.entity.CutOffPayments;
import com.example.isyncpos.entity.CutOffProducts;
import com.example.isyncpos.entity.Departments;
import com.example.isyncpos.entity.DiscountDetails;
import com.example.isyncpos.entity.DiscountOtherInformations;
import com.example.isyncpos.entity.EndOfDayDepartments;
import com.example.isyncpos.entity.EndOfDayDiscounts;
import com.example.isyncpos.entity.EndOfDayPayments;
import com.example.isyncpos.entity.EndOffDayProducts;
import com.example.isyncpos.entity.Orders;
import com.example.isyncpos.entity.PaymentOtherInformations;
import com.example.isyncpos.entity.Payments;
import com.example.isyncpos.entity.SafekeepingDenomination;
import com.example.isyncpos.viewmodel.PaymentOtherInformationsViewModel;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Functions {

    private static Functions instance;

    public synchronized static Functions getInstance(){
        if(instance == null){
            instance = new Functions();
        }
        return instance;
    }

    public DiscountDetails discountDetailsFindByOrderId(List<DiscountDetails> discountDetails, int orderId){
        for(DiscountDetails item : discountDetails){
            if(item.getIsVoid() == 0){
                if(item.getOrderId() == orderId){
                    return item;
                }
            }
        }
        return null;
    }

    public Boolean isPaymentCashOnly(List<Payments> payments){
        List<Payments> paymentData = payments.stream().filter(item -> !"Cash".equals(item.getPaymentTypeName())).collect(Collectors.toList());
        if(paymentData.size() != 0){
            return false;
        }
        else{
            return true;
        }
    }

    public String filterByPaymentTypes(List<Payments> payments, PaymentOtherInformationsViewModel paymentOtherInformationsViewModel){
        Generate generate = new Generate();
        String content = "";
        for(Payments item : payments){
            try {
                content += "[L]" + item.getPaymentTypeName() + "[R]" + generate.toTwoDecimalWithComma(item.getAmount()) + "\n";
                List<PaymentOtherInformations> paymentOtherInformations = paymentOtherInformationsViewModel.fetchByPaymentId(item.getId());
                if(paymentOtherInformations.size() != 0){
                    content += "[L]\n";
                    for(PaymentOtherInformations otherInformation : paymentOtherInformations){
                        content += "[L]" + otherInformation.getName() + "[R]" + otherInformation.getValue() + "\n";
                    }
                    content += "[L]\n";
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return content;
    }

    public List<CutOffDepartments> filterByDepartmentCutOff(List<CutOffDepartments> cutOffDepartments, int departmentId, String departmentName, double amount, double qty, int cutOffId, String treg){
        Generate generate = new Generate();
        Optional<CutOffDepartments> cutOffDepartmentsData = cutOffDepartments.stream().filter(item -> departmentId == item.getDepartmentId()).findFirst();
        if(cutOffDepartmentsData.isPresent()){
            cutOffDepartmentsData.get().setAmount(generate.toFourDecimal(cutOffDepartmentsData.get().getAmount() + amount));
            cutOffDepartmentsData.get().setTransactionCount(generate.toFourDecimal(cutOffDepartmentsData.get().getTransactionCount() + qty));
        }
        else{
            cutOffDepartments.add(new CutOffDepartments(
                POSApplication.getInstance().getMachineDetails().getCoreId(),
                POSApplication.getInstance().getBranch().getCoreId(),
                cutOffId,
                departmentId,
                departmentName,
                qty,
                amount,
                0,
                0,
                treg,
                POSApplication.getInstance().getCompany().getCoreId()
            ));
        }
        return cutOffDepartments;
    }

    public List<CutOffDiscounts> filterByDiscountCutOff(List<CutOffDiscounts> cutOffDiscounts, int discountTypeId, String discountName, double amount, int qty, int cutOffId, String treg, int isZeroRated){
        Generate generate = new Generate();
        Optional<CutOffDiscounts> cutOffDiscountsData = cutOffDiscounts.stream().filter(item -> discountTypeId == item.getDiscountTypeId()).findFirst();
        if(cutOffDiscountsData.isPresent()){
            cutOffDiscountsData.get().setAmount(generate.toFourDecimal(cutOffDiscountsData.get().getAmount() + amount));
            cutOffDiscountsData.get().setTransactionCount(cutOffDiscountsData.get().getTransactionCount() + qty);
        }
        else{
            cutOffDiscounts.add(new CutOffDiscounts(
                POSApplication.getInstance().getMachineDetails().getCoreId(),
                POSApplication.getInstance().getBranch().getCoreId(),
                cutOffId,
                discountTypeId,
                discountName,
                qty,
                generate.toFourDecimal(amount),
                0,
                0,
                treg,
                isZeroRated,
                POSApplication.getInstance().getCompany().getCoreId()
            ));
        }
        return cutOffDiscounts;
    }

    public List<CutOffPayments> filterByPaymentsCutOff(List<CutOffPayments> cutOffPayments, int paymentTypeId, String paymentName, double amount, int qty, int cutOffId, String treg){
        Generate generate = new Generate();
        Optional<CutOffPayments> cutOffPaymentsData = cutOffPayments.stream().filter(item -> paymentTypeId == item.getPaymentTypeId()).findFirst();
        if(cutOffPaymentsData.isPresent()){
            cutOffPaymentsData.get().setAmount(generate.toFourDecimal(cutOffPaymentsData.get().getAmount() + amount));
            cutOffPaymentsData.get().setTransactionCount(cutOffPaymentsData.get().getTransactionCount() + qty);
        }
        else{
            cutOffPayments.add(new CutOffPayments(
                POSApplication.getInstance().getMachineDetails().getCoreId(),
                POSApplication.getInstance().getBranch().getCoreId(),
                cutOffId,
                paymentTypeId,
                paymentName,
                qty,
                generate.toFourDecimal(amount),
                0,
                0,
                treg,
                POSApplication.getInstance().getCompany().getCoreId()
            ));
        }
        return cutOffPayments;
    }

    public List<CutOffProducts> filterByCutOffProducts(List<CutOffProducts> cutOffProducts, int productId, int cutOffId, double qty, String treg){
        Optional<CutOffProducts> cutOffProductsData = cutOffProducts.stream().filter(item -> productId == item.getProductId()).findFirst();
        if(cutOffProductsData.isPresent()){
            cutOffProductsData.get().setQty(cutOffProductsData.get().getQty() + qty);
        }
        else{
            cutOffProducts.add(new CutOffProducts(
                cutOffId,
                productId,
                qty,
                1,
                treg,
                0,
                0,
                treg,
                POSApplication.getInstance().getMachineDetails().getCoreId(),
                POSApplication.getInstance().getBranch().getCoreId(),
                POSApplication.getInstance().getCompany().getCoreId()
            ));
        }
        return cutOffProducts;
    }

    public List<EndOffDayProducts> filterByCutOffProductsToEndOfDayProducts(List<EndOffDayProducts> endOffDayProducts, int productId, int endOfDayId, double qty, String treg){
        Optional<EndOffDayProducts> endOffDayProductsData = endOffDayProducts.stream().filter(item -> productId == item.getProductId()).findFirst();
        if(endOffDayProductsData.isPresent()){
            endOffDayProductsData.get().setQty(endOffDayProductsData.get().getQty() + qty);
        }
        else{
            endOffDayProducts.add(new EndOffDayProducts(
                endOfDayId,
                productId,
                qty,
                0,
                treg,
                POSApplication.getInstance().getMachineDetails().getCoreId(),
                POSApplication.getInstance().getBranch().getCoreId(),
                POSApplication.getInstance().getCompany().getCoreId()
            ));
        }
        return endOffDayProducts;
    }

    public List<SafekeepingDenomination> filterBySafeKeepingDenomination(List<SafekeepingDenomination> safekeepingDenominations, int cashDenominationId, String cashDenominationName, double amount, int qty, double total){
        Generate generate = new Generate();
        Optional<SafekeepingDenomination> safekeepingDenominationData = safekeepingDenominations.stream().filter(item -> cashDenominationId == item.getCashDenominationId()).findFirst();
        if(safekeepingDenominationData.isPresent()){
            safekeepingDenominationData.get().setQty(safekeepingDenominationData.get().getQty() + qty);
            safekeepingDenominationData.get().setTotal(generate.toFourDecimal(safekeepingDenominationData.get().getTotal() + total));
        }
        else{
            safekeepingDenominations.add(new SafekeepingDenomination(
                0,
                0,
                0,
                cashDenominationId,
                cashDenominationName,
                amount,
                qty,
                total,
                0,
                0,
                "",
                0,
                0,
                0,
                POSApplication.getInstance().getCompany().getCoreId()
            ));
        }
        return safekeepingDenominations;
    }

    public List<EndOfDayDepartments> filterByDepartmentEndOfDay(List<EndOfDayDepartments> endOfDayDepartments, int departmentId, String name, double transactionCount, double amount, int endOfDayId, String treg){
        Generate generate = new Generate();
        Optional<EndOfDayDepartments> endOfDayDepartmentsData = endOfDayDepartments.stream().filter(item -> departmentId == item.getDepartmentId()).findFirst();
        if(endOfDayDepartmentsData.isPresent()){
            endOfDayDepartmentsData.get().setTransactionCount(generate.toFourDecimal(endOfDayDepartmentsData.get().getTransactionCount() + transactionCount));
            endOfDayDepartmentsData.get().setAmount(generate.toFourDecimal(endOfDayDepartmentsData.get().getAmount() + amount));
        }
        else{
            endOfDayDepartments.add(new EndOfDayDepartments(
                    POSApplication.getInstance().getMachineDetails().getCoreId(),
                    POSApplication.getInstance().getBranch().getCoreId(),
                    endOfDayId,
                    departmentId,
                    name,
                    transactionCount,
                    generate.toFourDecimal(amount),
                    0,
                    treg,
                    POSApplication.getInstance().getCompany().getCoreId()
            ));
        }
        return endOfDayDepartments;
    }

    public List<EndOfDayDiscounts> filterByDiscountsEndOfDay(List<EndOfDayDiscounts> endOfDayDiscounts, int discountTypeId, String name, int transactionCount, double amount, int endOfDayId, String treg, int isZeroRated){
        Generate generate = new Generate();
        Optional<EndOfDayDiscounts> endOfDayDiscountsData = endOfDayDiscounts.stream().filter(item -> discountTypeId == item.getDiscountTypeId()).findFirst();
        if(endOfDayDiscountsData.isPresent()){
            endOfDayDiscountsData.get().setTransactionCount(endOfDayDiscountsData.get().getTransactionCount() + transactionCount);
            endOfDayDiscountsData.get().setAmount(generate.toFourDecimal(endOfDayDiscountsData.get().getAmount() + amount));
        }
        else{
            endOfDayDiscounts.add(new EndOfDayDiscounts(
                    POSApplication.getInstance().getMachineDetails().getCoreId(),
                    POSApplication.getInstance().getBranch().getCoreId(),
                    endOfDayId,
                    discountTypeId,
                    name,
                    transactionCount,
                    amount,
                    0,
                    treg,
                    isZeroRated,
                    POSApplication.getInstance().getCompany().getCoreId()
            ));
        }
        return endOfDayDiscounts;
    }

    public List<EndOfDayPayments> filterByPaymentsEndOfDay(List<EndOfDayPayments> endOfDayPayments, int paymentId, String name, int transactionCount, double amount, int endOfDayId, String treg){
        Generate generate = new Generate();
        Optional<EndOfDayPayments> endOfDayPaymentsData = endOfDayPayments.stream().filter(item -> paymentId == item.getPaymentTypeId()).findFirst();
        if(endOfDayPaymentsData.isPresent()){
            endOfDayPaymentsData.get().setTransactionCount(endOfDayPaymentsData.get().getTransactionCount() + transactionCount);
            endOfDayPaymentsData.get().setAmount(generate.toFourDecimal(endOfDayPaymentsData.get().getAmount() + amount));
        }
        else{
            endOfDayPayments.add(new EndOfDayPayments(
                    POSApplication.getInstance().getMachineDetails().getCoreId(),
                    POSApplication.getInstance().getBranch().getCoreId(),
                    endOfDayId,
                    paymentId,
                    name,
                    transactionCount,
                    amount,
                    0,
                    treg,
                    POSApplication.getInstance().getCompany().getCoreId()
            ));
        }
        return endOfDayPayments;
    }

    @Nullable
    public DiscountDetails filterByDiscountDetailsOrder(List<DiscountDetails> discountDetails, int orderId){
        Optional<DiscountDetails> discountDetailsData = discountDetails.stream().filter(item -> orderId == item.getOrderId()).findFirst();
        if(discountDetailsData.isPresent()){
            return discountDetailsData.get();
        }
        return null;
    }

    public List<DiscountDetails> filterByDiscountDetailsByDiscountId(List<DiscountDetails> discountDetails, int discountId, int newDiscountId){
        for(DiscountDetails item: discountDetails){
            if(item.getDiscountId() == discountId){
                item.setDiscountId(newDiscountId);
            }
        }
        return discountDetails;
    }

    public List<DiscountOtherInformations> filterByDiscountOtherInformationByDiscountId(List<DiscountOtherInformations> discountOtherInformations, int discountId, int newDiscountId){
        for(DiscountOtherInformations item: discountOtherInformations){
            if(item.getDiscountId() == discountId){
                item.setDiscountId(newDiscountId);
            }
        }
        return discountOtherInformations;
    }

}
