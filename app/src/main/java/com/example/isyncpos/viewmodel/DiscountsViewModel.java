package com.example.isyncpos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.isyncpos.entity.Discounts;
import com.example.isyncpos.entity.Orders;
import com.example.isyncpos.repositories.DiscountsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DiscountsViewModel extends AndroidViewModel {

    private DiscountsRepository discountsRepository;
    private MutableLiveData<List<Discounts>> currentTransactionDiscounts = new MutableLiveData<>();
    private LiveData<List<Discounts>> toSyncDiscountsLivaData;

    public DiscountsViewModel(Application application){
        super(application);
        discountsRepository = new DiscountsRepository(application);
        toSyncDiscountsLivaData = discountsRepository.fetchCompleteDiscountsToSync();
    }

    public Long insert(Discounts discounts) throws ExecutionException, InterruptedException {
        return discountsRepository.insert(discounts);
    }

    public void update(Discounts discounts){
        discountsRepository.update(discounts);
    }

    public List<Discounts> fetchDiscountsByTransactionId(int transactionId) throws ExecutionException, InterruptedException {
        return discountsRepository.fetchDiscountsByTransactionId(transactionId);
    }

    public List<Discounts> fetchCompleteDiscountsToUpload() throws ExecutionException, InterruptedException {
        return discountsRepository.fetchCompleteDiscountsToUpload();
    }

    public Discounts fetchDiscountByDiscountId(int discountId) throws ExecutionException, InterruptedException {
        return discountsRepository.fetchDiscountByDiscountId(discountId);
    }

    public List<Discounts> fetchDiscountsByTransactionIdWithVoid(int transactionId) throws ExecutionException, InterruptedException {
        return discountsRepository.fetchDiscountsByTransactionIdWithVoid(transactionId);
    }

    public LiveData<List<Discounts>> fetchCompleteDiscountsToSync() {
        return toSyncDiscountsLivaData;
    }

    public void updateDiscountSentToServer(int discountId){
        discountsRepository.updateDiscountSentToServer(discountId);
    }

    public void setCurrentTransactionDiscounts(List<Discounts> discounts) {
        this.currentTransactionDiscounts.setValue(discounts);
    }

    public MutableLiveData<List<Discounts>> getCurrentTransactionDiscounts(){
        if(currentTransactionDiscounts == null){
            currentTransactionDiscounts.setValue(null);
        }
        return currentTransactionDiscounts;
    }

}
