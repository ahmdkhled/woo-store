package com.example.woocommerce.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woocommerce.R;

public class ReviewsFrag extends Fragment {
    RecyclerView reviewsFrag;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.reviews_frag,container,false);
        reviewsFrag=v.findViewById(R.id.reviews_recycler);


        return v;
    }
}
