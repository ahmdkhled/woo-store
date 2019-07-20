package com.corenet.yohady.ui;

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
import android.widget.TextView;

import com.corenet.yohady.R;
import com.corenet.yohady.utils.BottomSheetListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SortByBottomSheet extends BottomSheetDialogFragment {

    public static final String SORT_BY_POPULARITY = "popularity";
    public static final String SORT_BY_AVG_RATE = "avg_rate";
    public static final String SORT_BY_LATEST = "latest";
    public static final String SORT_BY_PRICE_LOW_HIGH = "price_low_high";
    public static final String SORT_BY_PRICE_HIGH_LOW = "price_high_low";
    @BindView(R.id.by_popularity_txt)
    TextView mPopularityOption;
    @BindView(R.id.by_date_txt)
    TextView mLatestOption;
    @BindView(R.id.by_avg_txt)
    TextView mAvgOption;
    @BindView(R.id.by_price_low_high_txt)
    TextView mPriceLowToHighOption;
    @BindView(R.id.by_price_high_low_txt)
    TextView mPriceHighToLowOption;

    private BottomSheetListener mListener;
    private String msortBy = null;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sort_by_bottom_sheet,container,false);
        Log.d("fromBottomSheet","onCreateView");

        ButterKnife.bind(this,view);
        setupFonts();

        mPopularityOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msortBy = SORT_BY_POPULARITY;
                mListener.onBottomSheetOptionClicked(SORT_BY_POPULARITY);
                dismiss();
            }
        });

        mAvgOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msortBy = SORT_BY_AVG_RATE;
                mListener.onBottomSheetOptionClicked(SORT_BY_AVG_RATE);
                dismiss();
            }
        });


        mLatestOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msortBy = SORT_BY_LATEST;
                mListener.onBottomSheetOptionClicked(SORT_BY_LATEST);
                dismiss();
            }
        });


        mPriceLowToHighOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msortBy = SORT_BY_PRICE_LOW_HIGH;
                mListener.onBottomSheetOptionClicked(SORT_BY_PRICE_LOW_HIGH);
                dismiss();
            }
        });


        mPriceHighToLowOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msortBy = SORT_BY_PRICE_HIGH_LOW;
                mListener.onBottomSheetOptionClicked(SORT_BY_PRICE_HIGH_LOW);
                dismiss();
            }
        });

        return view;
    }

    private void setupFonts() {
        mPopularityOption.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                getString(R.string.roboto_regular)));
        mLatestOption.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                getString(R.string.roboto_regular)));
        mAvgOption.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                getString(R.string.roboto_regular)));
        mPriceHighToLowOption.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                getString(R.string.roboto_regular)));
        mPriceLowToHighOption.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
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
