package com.corenet.yohady.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.corenet.yohady.utils.PrefManager;

public class ProductDetailViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> isItemSavedIntoCart;
    public ProductDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        if(isItemSavedIntoCart == null)isItemSavedIntoCart = new MutableLiveData<>();
    }

    public void addProductToCart(int id, int quantity){
        PrefManager manager = PrefManager.getInstance(getApplication());
        isItemSavedIntoCart.setValue(manager.addItemToCart(id,quantity));
    }

    public MutableLiveData<Integer> getIsItemSavedIntoCart() {
        if(isItemSavedIntoCart == null) isItemSavedIntoCart = new MutableLiveData<>();
        return isItemSavedIntoCart;
    }
}
