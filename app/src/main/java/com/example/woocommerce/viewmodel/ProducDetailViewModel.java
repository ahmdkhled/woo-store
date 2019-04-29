package com.example.woocommerce.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.woocommerce.utils.PrefManager;

public class ProducDetailViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isItemSavedIntoCart;
    public ProducDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void addProductToCart(int id, int quantity){
        PrefManager manager = new PrefManager(getApplication());
        isItemSavedIntoCart.setValue(manager.addItemToCart(id,quantity));
    }

    public MutableLiveData<Boolean> getIsItemSavedIntoCart() {
        if(isItemSavedIntoCart == null) isItemSavedIntoCart = new MutableLiveData<>();
        return isItemSavedIntoCart;
    }
}
