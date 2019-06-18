package com.example.woocommerce.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.woocommerce.model.Review;
import com.example.woocommerce.repository.ReviewsRepo;

public class CreateReviewModel extends ViewModel {

    private MutableLiveData<Boolean> isReviewCreated;
    private MutableLiveData<Review> mCreatedReview;

    public void createReview(int productId,String review,String reviewer, String reviewerEmail, int rating){
        mCreatedReview = ReviewsRepo.getInstance().createReview(productId,review,reviewer,reviewerEmail,rating);
        isReviewCreated = ReviewsRepo.getInstance().getIsReviewsCreated();
    }

    public MutableLiveData<Boolean> getIsReviewCreated() {
        if(isReviewCreated == null) isReviewCreated = new MutableLiveData<>();
        return isReviewCreated;
    }
}
