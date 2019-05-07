package com.example.woocommerce.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.woocommerce.R;
import com.example.woocommerce.adapter.CheckoutAdapter;

public class CheckoutActivity extends AppCompatActivity implements AddressFrag.OnAddressFieldsValidated{

    ViewPager recyclerView;
     Button proceedToPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        recyclerView=findViewById(R.id.checkout_pager);
        proceedToPayment=findViewById(R.id.proceedToPayment);

        CheckoutAdapter adapter=new CheckoutAdapter(getSupportFragmentManager());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAddressFieldsValidated() {
        Log.d("ADDRREESSS", "onAddressFieldsValidated: ");
    }

    @Override
    public void onAddressFieldsMissing() {
        Log.d("ADDRREESSS", "onAddressFieldsMissing: ");
    }
}
