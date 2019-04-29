package com.example.woocommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.woocommerce.R;
import com.example.woocommerce.adapter.ReviewsAdapter;
import com.example.woocommerce.model.Product;
import com.example.woocommerce.model.Review;
import com.example.woocommerce.viewmodel.ReviewsViewModel;

import java.util.ArrayList;

public class ReviewsFrag extends Fragment {
    RecyclerView reviewsRecycler;
    ProgressBar progressBar;
    ReviewsViewModel reviewsViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.reviews_frag,container,false);
        reviewsRecycler=v.findViewById(R.id.reviews_recycler);
        progressBar=v.findViewById(R.id.reviews_PB);
        Product product=((ProductDetailActivity)getActivity()).product;

        reviewsViewModel= ViewModelProviders.of(getActivity()).get(ReviewsViewModel.class);
        reviewsViewModel.getReviews("1",product.getId());
        observeReviews();
        observeReviewsLoading();
        observeReviewsLoadingError();

        return v;
    }

    void observeReviews(){
        reviewsViewModel.getReviews()
                .observe(getActivity(), new Observer<ArrayList<Review>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Review> reviews) {
                        Log.d("REVIEWW", "onChanged: "+reviews.size());
                        ReviewsAdapter reviewsAdapter=new ReviewsAdapter(getContext(),reviews);
                        reviewsRecycler.setAdapter(reviewsAdapter);
                        reviewsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                });
    }

    void observeReviewsLoading(){
        reviewsViewModel.getIsReviewsLoading()
                .observe(getActivity(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean!=null&&aBoolean)
                            progressBar.setVisibility(View.VISIBLE);
                        else
                            progressBar.setVisibility(View.GONE);
                    }
                });
    }

    void observeReviewsLoadingError(){
        reviewsViewModel.getReviewsLoadingError()
                .observe(getActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
