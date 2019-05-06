package com.example.woocommerce.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    @BindView(R.id.btn_popularity)
    Button mPopularityBtn;
    @BindView(R.id.btn_avg_rate)
    Button mAvgRateBtn;
    @BindView(R.id.btn_latest)
    Button mLatestBtn;
    @BindView(R.id.btn_price_low_high)
    Button mPriceLowToHighBtn;
    @BindView(R.id.btn_price_high_low)
    Button mPriceHighToLowBtn;

    private BottomSheetListener mListener;
    private Button msortBy = null;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sort_by_bottom_sheet,container,false);
        Log.d("fromBottomSheet","onCreateView");

        ButterKnife.bind(this,view);
        setupFonts();

        mPopularityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msortBy = mPopularityBtn;
                mListener.onBottomSheetOptionClicked(SORT_BY_POPULARITY);
                dismiss();
            }
        });

        mAvgRateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msortBy = mAvgRateBtn;
                mListener.onBottomSheetOptionClicked(SORT_BY_AVG_RATE);
                dismiss();
            }
        });


        mLatestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msortBy = mLatestBtn;
                mListener.onBottomSheetOptionClicked(SORT_BY_LATEST);
                dismiss();
            }
        });


        mPriceHighToLowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msortBy = mPriceHighToLowBtn;
                mListener.onBottomSheetOptionClicked(SORT_BY_PRICE_HIGH_LOW);
                dismiss();
            }
        });


        mPriceLowToHighBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msortBy = mPriceLowToHighBtn;
                mListener.onBottomSheetOptionClicked(SORT_BY_PRICE_LOW_HIGH);
                dismiss();
            }
        });

        return view;
    }

    private void setupFonts() {
        mPopularityBtn.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                getString(R.string.roboto_regular)));
        mLatestBtn.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                getString(R.string.roboto_regular)));
        mAvgRateBtn.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                getString(R.string.roboto_regular)));
        mPriceHighToLowBtn.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                getString(R.string.roboto_regular)));
        mPriceLowToHighBtn.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                getString(R.string.roboto_regular)));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(msortBy == null)Log.d("sheet","sort by null");
        else Log.d("sheet","sort by not null");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (BottomSheetListener) context;
    }


}
