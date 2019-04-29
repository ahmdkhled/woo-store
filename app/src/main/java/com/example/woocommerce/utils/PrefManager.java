package com.example.woocommerce.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private static final String CART_SHARED_PREF = "cart_shared_pref";
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEditor;

    public PrefManager(Context context) {
        mSharedPref = context.getSharedPreferences(CART_SHARED_PREF,Context.MODE_PRIVATE);
    }
}
