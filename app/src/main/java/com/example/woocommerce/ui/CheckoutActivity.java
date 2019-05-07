package com.example.woocommerce.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.woocommerce.R;
import com.example.woocommerce.adapter.CheckoutAdapter;
import com.example.woocommerce.model.Billing;
import com.example.woocommerce.model.Shipping;

public class CheckoutActivity extends AppCompatActivity implements AddressFrag.OnAddressFieldsValidated{

    ViewPager pager;
    TabLayout tabLayout;
    Button proceedToPayment;
    Shipping shippingAddress;
    Billing billingAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        pager=findViewById(R.id.checkout_pager);
        tabLayout=findViewById(R.id.checkout_tablayout);
        proceedToPayment=findViewById(R.id.proceedToPayment);
        getSupportActionBar().setElevation(0);

        CheckoutAdapter adapter=new CheckoutAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }


    @Override
    public void onAddressFieldsValidated(Shipping shippingAddress, Billing billingAddress) {
        this.shippingAddress=shippingAddress;
        this.billingAddress=billingAddress;
        pager.setCurrentItem(1);
    }

    @Override
    public void onAddressFieldsMissing() {
        Log.d("ADDRREESSS", "onAddressFieldsMissing: ");
    }
}
