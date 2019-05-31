package com.example.woocommerce.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.woocommerce.R;
import com.example.woocommerce.model.Shipping;

public class PaymentFrag extends Fragment {

    public static final String ADDRESS_KEY="address_key";
    Button placeOrder;
    Shipping shipping;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.payment_frag,container,false);
        placeOrder=v.findViewById(R.id.placeOrder);
        Bundle b=getArguments();
        if (b != null&&b.containsKey(ADDRESS_KEY)) {
            shipping=b.getParcelable(ADDRESS_KEY);
        }

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }


}
