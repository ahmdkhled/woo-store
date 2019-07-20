package com.corenet.yohady.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.corenet.yohady.R;
import com.corenet.yohady.model.Product;

public class OverviewFrag extends Fragment {

    TextView overview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.overview_frag,container,false);
        overview=v.findViewById(R.id.overview);
        if(getActivity() != null) {
            Product product = ((ProductDetailActivity) getActivity()).product;
            if(product.getDescription() != null){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Log.d("over_frag","desc "+Html.fromHtml(product.getDescription(), Html.FROM_HTML_MODE_COMPACT));
                }
            }

            if(product.getShortDescription() != null){
                Log.d("over_frag","desc "+product.getShortDescription());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                overview.setText(Html.fromHtml(product.getDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                overview.setText(Html.fromHtml(product.getDescription()));
            }
        }
        return v;
    }
}
