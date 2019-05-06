package com.example.woocommerce.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.woocommerce.model.CartItem;
import com.example.woocommerce.model.Product;
import com.example.woocommerce.repository.ProductsRepo;
import com.example.woocommerce.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private ProductsRepo productsRepo;
    private MutableLiveData<ArrayList<Product>> mCartItems;
    private MutableLiveData<Boolean> isCartEmpty;
    private MutableLiveData<Boolean> isProductLoading;
    private MutableLiveData<String> productLoadingError;
    private PrefManager prefManager;

    public CartViewModel(@NonNull Application application) {
        super(application);
        productsRepo = ProductsRepo.getInstance();
        prefManager = PrefManager.getInstance(getApplication());
        if(isCartEmpty == null) isCartEmpty = new MutableLiveData<>();
    }

    public void getCartItems(){

        String cartItemsIds = getCartItemsIds();
        if(cartItemsIds != null) {
            mCartItems = productsRepo.getProducts(null, String.valueOf(getCartSize()), null, null, null, null, null,
                    null, null, null, null, "publish", null, cartItemsIds,
                    null, null, null, null);
            isCartEmpty.setValue(false);
            isProductLoading = productsRepo.getIsProductsLoading();
            productLoadingError = productsRepo.getProductsLoadingError();
        }else isCartEmpty.setValue(true);

    }

    public MutableLiveData<ArrayList<Product>> getmCartItems() {
        if (mCartItems == null)mCartItems = new MutableLiveData<>();
        return mCartItems;
    }

    public MutableLiveData<Boolean> getIsProductLoading() {
        if(isProductLoading == null) isProductLoading = new MutableLiveData<>();
        return isProductLoading;
    }

    public MutableLiveData<String> getProductLoadingError() {
        if(productLoadingError == null) productLoadingError = new MutableLiveData<>();
        return productLoadingError;
    }

    public MutableLiveData<Boolean> getIsCartEmpty() {
        if(isCartEmpty == null)isCartEmpty = new MutableLiveData<>();
        return isCartEmpty;
    }

    private String getCartItemsIds() {
        StringBuilder cartItemsIds = new StringBuilder();
        ArrayList<CartItem> cartItems = prefManager.getCartItems();
        if(cartItems != null && cartItems.size() > 0) {
            for (CartItem item : cartItems) {
                cartItemsIds.append(item.getId()+",");
            }
            cartItemsIds.deleteCharAt(cartItemsIds.length()-1);
            return cartItemsIds.toString();
        }

        return null;

    }

    private int getCartSize(){
        return prefManager.getCartSize();
    }


    public void updateItemQuantity(int position, int newQuantity) {
        prefManager.updateQuantity(position, newQuantity);
    }

    public List<Integer> getCartItemsQuantities() {
       return prefManager.getCartItemsQuantities();
    }
}
