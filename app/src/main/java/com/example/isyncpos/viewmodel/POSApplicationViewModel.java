package com.example.isyncpos.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class POSApplicationViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> transactionId = new MutableLiveData<>();
    private MutableLiveData<Integer> arTransactionId = new MutableLiveData<>();

    public POSApplicationViewModel(Application application){
        super(application);
    }

    public MutableLiveData<Integer> getCurrentTransactionId(){
        if(transactionId == null){
            transactionId.setValue(0);
        }
        return transactionId;
    }

    public void setCurrentTransaction(int transactionId){
        this.transactionId.setValue(transactionId);
    }

    public MutableLiveData<Integer> getCurrentARTransactionId(){
        if(arTransactionId == null){
            arTransactionId.setValue(0);
        }
        return arTransactionId;
    }

    public void setARCurrentTransaction(int transactionId){
        this.arTransactionId.setValue(transactionId);
    }

}
