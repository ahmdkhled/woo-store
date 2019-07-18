package com.example.woocommerce.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.woocommerce.R;
import com.example.woocommerce.adapter.CheckoutAdapter;
import com.example.woocommerce.model.Billing;
import com.example.woocommerce.model.Shipping;

public class CheckoutActivity extends AppCompatActivity implements AddressFrag.OnAddressFieldsValidated{

    AddressFrag addressFrag;
    PaymentFrag paymentFrag;
    String activeFrag;
    Toolbar toolbar;
    public static final String ADDRESS_FRAG_TAG="address_frag_tag";
    public static final String PAYMENT_FRAG_TAG="payment_frag_tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addressFrag= (AddressFrag) getSupportFragmentManager()
                .findFragmentByTag(ADDRESS_FRAG_TAG);
        if (addressFrag==null)
            addressFrag=new AddressFrag();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.checkout_activity,addressFrag,ADDRESS_FRAG_TAG)
                .commit();
        activeFrag=ADDRESS_FRAG_TAG;

    }


    @Override
    public void onAddressFieldsValidated(Shipping shippingAddres, Billing billingAddress) {
        getSupportFragmentManager()
        .beginTransaction()
        .hide(addressFrag)
        .commit();

        paymentFrag=new PaymentFrag();
        Bundle b= new Bundle();
        b.putParcelable(PaymentFrag.ADDRESS_KEY,shippingAddres);
        b.putParcelable(PaymentFrag.BILLING_KEY,billingAddress);
        paymentFrag.setArguments(b);
        getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.checkout_activity,paymentFrag,ADDRESS_FRAG_TAG)
                    .commit();
        activeFrag=PAYMENT_FRAG_TAG;


    }

    @Override
    public void onAddressFieldsMissing() {

    }

    @Override
    public void onBackPressed() {
        if (activeFrag.equals(ADDRESS_FRAG_TAG)){
            super.onBackPressed();
            Log.d("BAAACKKK", "back from address: ");
        }
        else if (activeFrag.equals(PAYMENT_FRAG_TAG)){
            activeFrag=ADDRESS_FRAG_TAG;
            getSupportFragmentManager().beginTransaction()
                    .hide(paymentFrag)
                    .show(addressFrag)
                    .commit();
            Log.d("BAAACKKK", "back from payment: ");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            onBackPressed();
        return true;
    }
}
