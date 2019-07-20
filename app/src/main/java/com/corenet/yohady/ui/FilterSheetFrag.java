package com.corenet.yohady.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.corenet.yohady.R;

public class FilterSheetFrag extends BottomSheetDialogFragment {

    TextView date,pricrLTH,priceHTL,bestseller;

    public FilterSheetFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.filter_bottomsheet,container,false);
        date=view.findViewById(R.id.date);
        pricrLTH=view.findViewById(R.id.priceLTH);
        priceHTL=view.findViewById(R.id.priceHTL);
        bestseller=view.findViewById(R.id.bestSelling);
        return view;
    }
}
