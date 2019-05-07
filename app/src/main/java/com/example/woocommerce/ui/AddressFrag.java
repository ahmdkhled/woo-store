package com.example.woocommerce.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.woocommerce.R;
import com.example.woocommerce.model.Billing;
import com.example.woocommerce.model.Shipping;

public class AddressFrag extends Fragment {

    TextInputLayout fname_IL,lname_IL,
            address1_IL,address2_IL,city,state_IL,
            zipcode_IL,country_IL,phoneNum_IL,email_IL;
    OnAddressFieldsValidated onAddressFieldsValidated;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.address_frag,container,false);
        fname_IL=v.findViewById(R.id.fname_textInputLayout);
        lname_IL=v.findViewById(R.id.lname_textInputLayout);
        address1_IL=v.findViewById(R.id.address1_textInputLayout);
        address2_IL=v.findViewById(R.id.address2_textInputLayout);
        city=v.findViewById(R.id.city_textInputLayout);
        state_IL=v.findViewById(R.id.state_textInputLayout);
        zipcode_IL=v.findViewById(R.id.zip_code_textInputLayout);
        country_IL=v.findViewById(R.id.country_textInputLayout);
        phoneNum_IL=v.findViewById(R.id.phone_textInputLayout);
        email_IL=v.findViewById(R.id.email_textInputLayout);

        Button procedToPayment=((CheckoutActivity)getActivity()).proceedToPayment;

        procedToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()){
                    onAddressFieldsValidated.onAddressFieldsValidated(getShippingAddress(),getBillingAddress());
                }else{
                    onAddressFieldsValidated.onAddressFieldsMissing();
                }

            }
        });

        return v;
    }

    private boolean validateInput(){
        boolean pass=true;
        TextInputLayout[] textInputLayouts=
                {fname_IL,lname_IL,address1_IL,city,state_IL,
                zipcode_IL,country_IL,phoneNum_IL,email_IL};
        for (TextInputLayout textInputLayout : textInputLayouts) {
            if (TextUtils.isEmpty(textInputLayout.getEditText().getText())) {
                textInputLayout.setError("Required");
                pass = false;
            } else {
                fname_IL.setError(null);
            }
        }

        return pass;
    }

    private Shipping getShippingAddress(){
        return new Shipping(fname_IL.getEditText().getText().toString(),
                            lname_IL.getEditText().getText().toString(),
                            address1_IL.getEditText().getText().toString(),
                            address2_IL.getEditText().getText().toString(),
                            city.getEditText().getText().toString(),
                            state_IL.getEditText().getText().toString(),
                            zipcode_IL.getEditText().getText().toString(),
                            country_IL.getEditText().getText().toString());
    }

    private Billing getBillingAddress(){
        return new Billing(fname_IL.getEditText().getText().toString(),
                lname_IL.getEditText().getText().toString(),
                address1_IL.getEditText().getText().toString(),
                address2_IL.getEditText().getText().toString(),
                city.getEditText().getText().toString(),
                state_IL.getEditText().getText().toString(),
                zipcode_IL.getEditText().getText().toString(),
                country_IL.getEditText().getText().toString(),
                email_IL.getEditText().getText().toString(),
                phoneNum_IL.getEditText().getText().toString());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAddressFieldsValidated= (OnAddressFieldsValidated) context;
    }

    interface OnAddressFieldsValidated{
        void onAddressFieldsValidated(Shipping shippingAddres,Billing billingAddress);
        void onAddressFieldsMissing();
    }
}
