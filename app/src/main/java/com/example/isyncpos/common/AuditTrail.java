package com.example.isyncpos.common;


import com.example.isyncpos.POSApplication;
import com.example.isyncpos.entity.AuditTrails;
import com.example.isyncpos.viewmodel.AuditTrailsViewModel;

public class AuditTrail {

    private static AuditTrail instance;
    private AuditTrailsViewModel auditTrailsViewModel;

    public static AuditTrail getInstance(){
        if(instance == null){
            instance = new AuditTrail();
        }
        return instance;
    }

    public void setArgs(AuditTrailsViewModel auditTrailsViewModel){
        this.auditTrailsViewModel = auditTrailsViewModel;
    }

    public void save(int userId, String userName, int transactionId, String action, String description, int authorizeId, String authorizeName, int orderId, int priceChangeReasonId){
        Dates date = new Dates();
        auditTrailsViewModel.insert(new AuditTrails(
            POSApplication.getInstance().getMachineDetails().getCoreId(),
            POSApplication.getInstance().getBranch().getCoreId(),
            userId,
            userName,
            transactionId,
            action,
            description,
            authorizeId,
            authorizeName,
            0,
            date.now(),
            orderId,
            priceChangeReasonId,
            POSApplication.getInstance().getCompany().getCoreId()
        ));
    }

}
