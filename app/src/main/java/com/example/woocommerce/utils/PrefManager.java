package com.example.woocommerce.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.woocommerce.model.CartItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PrefManager {
    private static final String CART_SHARED_PREF = "cart_shared_pref";
    private static final String CART_EDITOR = "cart_editor";
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEditor;

    public PrefManager(Context context) {
        mSharedPref = context.getSharedPreferences(CART_SHARED_PREF,Context.MODE_PRIVATE);
    }

    public void addItemToCart(int id, int quantity){
        CartItem cartItem = new CartItem(id,quantity);
        ArrayList<CartItem> cartItems = getCartItems();
        if(cartItems == null)cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        Gson gson = new Gson();
        String cartItemsAsString = gson.toJson(cartItems);
        mEditor = mSharedPref.edit();
        mEditor.putString(CART_EDITOR,cartItemsAsString);
        mEditor.apply();
    }

    private ArrayList<CartItem> getCartItems() {
        String cartItemsAsString = mSharedPref.getString(CART_EDITOR,null);
        Gson gson=new Gson();
        Type type = new TypeToken<ArrayList<CartItem>>() {}.getType();
        return gson.fromJson(cartItemsAsString,type);
    }
}
