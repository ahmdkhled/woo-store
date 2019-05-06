package com.example.woocommerce.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.woocommerce.R;
import com.example.woocommerce.utils.BottomSheetListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SortByBottomSheet extends BottomSheetDialogFragment {

    public static final String SORT_BY_POPULARITY = "popularity";
    public static final String SORT_BY_AVG_RATE = "avg_rate";
    public static final String SORT_BY_LATEST = "latest";
    public static final String SORT_BY_PRICE_LOW_HIGH = "price_low_high";
    public static final String SORT_BY_PRICE_HIGH_LOW = "price_high_low";
    @BindView(R.id.radio_popularity)
    RadioButton mRadioPopularity;
    @BindView(R.id.radio_avg_rate)
    RadioButton mRadioAvgRate;
    @BindView(R.id.radio_latest)
    RadioButton mRadioLatest;
    @BindView(R.id.radio_price_low_high)
    RadioButton mRadioPriceLowToHigh;
    @BindView(R.id.radio_price_high_low)
    RadioButton mRadioPriceHighToLow;

    private BottomSheetListener mListener;
    private RadioButton msortBy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sort_by_bottom_sheet,container,false);

        ButterKnife.bind(this,view);

        mRadioPopularity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onBottomSheetOptionClicked(SORT_BY_POPULARITY);
                dismiss();
            }
        });

        mRadioAvgRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onBottomSheetOptionClicked(SORT_BY_AVG_RATE);
                dismiss();
            }
        });


        mRadioLatest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onBottomSheetOptionClicked(SORT_BY_LATEST);
                dismiss();
            }
        });


        mRadioPriceHighToLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onBottomSheetOptionClicked(SORT_BY_PRICE_HIGH_LOW);
                dismiss();
            }
        });


        mRadioPriceLowToHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onBottomSheetOptionClicked(SORT_BY_PRICE_LOW_HIGH);
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (BottomSheetListener) context;
    }
}
