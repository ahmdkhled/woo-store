package com.example.woocommerce.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.woocommerce.R;
import com.example.woocommerce.model.Product;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String PRODUCT_KEY="product_key";
    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        product =getIntent().getParcelableExtra(PRODUCT_KEY);
        Log.d("PRODUCCTTT", "product : "+product.getName());
    }
}