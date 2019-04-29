package com.example.woocommerce.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.woocommerce.R;
import com.example.woocommerce.model.Product;

public class OverviewFrag extends Fragment {

    TextView overview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.overview_frag,container,false);
        overview=v.findViewById(R.id.overview);
        Product product=((ProductDetailActivity)getActivity()).product;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            overview.setText(Html.fromHtml(product.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            overview.setText(Html.fromHtml(product.getDescription()));
        }
        return v;
    }
}
