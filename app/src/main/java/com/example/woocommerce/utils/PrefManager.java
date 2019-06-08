package com.example.woocommerce.utils;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.woocommerce.model.CartItem;
import com.example.woocommerce.model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrefManager {
    private static final String CART_SHARED_PREF = "cart_shared_pref";
    private static final String CART_EDITOR = "cart_editor";
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEditor;
    private static PrefManager instance;
    private MutableLiveData<Integer> mCartSize;

    public static PrefManager getInstance(Context context) {
        if(instance == null) instance = new PrefManager(context);
        return instance;
    }

    private PrefManager(Context context) {
        mSharedPref = context.getSharedPreferences(CART_SHARED_PREF,Context.MODE_PRIVATE);
        mCartSize = new MutableLiveData<>();
    }

    public int addItemToCart(int id, int quantity){
        if(!isItemAlreadeyAdded(id)) {
            CartItem cartItem = new CartItem(id, quantity);
            ArrayList<CartItem> cartItems = getCartItems();
            if (cartItems == null) cartItems = new ArrayList<>();
            cartItems.add(cartItem);
            Gson gson = new Gson();
            String cartItemsAsString = gson.toJson(cartItems);
            mEditor = mSharedPref.edit();
            mEditor.putString(CART_EDITOR, cartItemsAsString);
            mCartSize.setValue(cartItems.size());
            return mEditor.commit()? 1 : -1;
        }
        return 0;
    }

    public ArrayList<CartItem> getCartItems() {
        String cartItemsAsString = mSharedPref.getString(CART_EDITOR,null);
        Gson gson=new Gson();
        Type type = new TypeToken<ArrayList<CartItem>>() {}.getType();
        return gson.fromJson(cartItemsAsString,type);
    }

    private boolean isItemAlreadeyAdded(int id){
        ArrayList<CartItem> cartItems = getCartItems();
        if(cartItems != null && cartItems.size() > 0) {
            for (CartItem item : cartItems) {
                if (item.getId() == id) return true;
            }
        }
        return false;
    }

    public int getCartSize() {
        ArrayList<CartItem> cartItems = getCartItems();
        if(cartItems != null) {
            mCartSize.setValue(cartItems.size());
            return cartItems.size();
        }
        else {
            mCartSize.setValue(0);
            return 0;
        }
    }

    public void updateQuantity(int position, int newQuantity) {
        ArrayList<CartItem> cartItems = getCartItems();
        cartItems.get(position).setQuantity(newQuantity);
        deleteCartItems();
        Gson gson = new Gson();
        String json = gson.toJson(cartItems);
        mEditor = mSharedPref.edit();
        mEditor.putString(CART_EDITOR,json);
        mEditor.apply();
    }

    private void deleteCartItems() {
        mEditor=mSharedPref.edit();
        mEditor.putString(CART_EDITOR,"");
        mEditor.apply();
    }

    public List<Integer> getCartItemsQuantities() {
        ArrayList<CartItem> cartItems = getCartItems();
        ArrayList<Integer> cartItemsQuantities = new ArrayList<>();
        for(CartItem item : cartItems){
            Log.d("from_cart","q "+item.getQuantity());
            cartItemsQuantities.add(item.getQuantity());
        }

        return cartItemsQuantities;
    }

    public MutableLiveData<Integer> getmCartSize() {
        if(mCartSize == null) mCartSize = new MutableLiveData<>();
        return mCartSize;
    }

    public boolean deleteItem(int position) {
        ArrayList<CartItem> cartItems = getCartItems();
        Log.d("cart_activity","removed name "+cartItems.get(position).getQuantity());
        cartItems.remove(position);
        deleteCartItems();
        Gson gson = new Gson();
        String json = gson.toJson(cartItems);
        mEditor = mSharedPref.edit();
        mEditor.putString(CART_EDITOR,json);
        mCartSize.setValue(cartItems.size());
        return mEditor.commit();
    }
}
