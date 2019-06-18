package com.example.woocommerce.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.woocommerce.model.Review;
import com.example.woocommerce.repository.ReviewsRepo;

import java.util.ArrayList;

public class ReviewsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Review>> reviews = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Review>> getReviews(String page,int product_id) {
//        if (reviews==null)
            reviews= ReviewsRepo.getInstance().getReviews(page,product_id);
        return reviews;
    }

    public MutableLiveData<ArrayList<Review>> getReviews() {
        if (reviews == null)reviews = new MutableLiveData<>();
        return reviews;
    }

    public void setReviews(MutableLiveData<ArrayList<Review>> reviews) {
        this.reviews = reviews;
    }

    public MutableLiveData<Boolean> getIsReviewsLoading() {
        return ReviewsRepo.getInstance().getIsReviewsLoading();
    }

    public MutableLiveData<String> getReviewsLoadingError() {
        return ReviewsRepo.getInstance().getReviewsLoadingError();
    }


}
