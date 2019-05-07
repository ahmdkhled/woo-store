package com.example.woocommerce.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.woocommerce.R;
import com.example.woocommerce.adapter.CheckoutAdapter;

public class CheckoutActivity extends AppCompatActivity {

    ViewPager recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        recyclerView=findViewById(R.id.checkout_pager);

        CheckoutAdapter adapter=new CheckoutAdapter(getSupportFragmentManager());
        recyclerView.setAdapter(adapter);
    }
}
