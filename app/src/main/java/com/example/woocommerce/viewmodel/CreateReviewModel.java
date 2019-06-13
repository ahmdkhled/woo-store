package com.example.woocommerce.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.woocommerce.repository.ReviewsRepo;

public class CreateReviewModel extends ViewModel {

    private MutableLiveData<Boolean> isReviewCreated;

    public void createReview(int productId,String reviewer, String reviewerEmail,String reviewe, int rating){
        ReviewsRepo.getInstance().createReview(productId,reviewer,reviewerEmail,reviewe,rating);
        isReviewCreated = ReviewsRepo.getInstance().getIsReviewsCreated();
    }

    public MutableLiveData<Boolean> getIsReviewCreated() {
        if(isReviewCreated == null) isReviewCreated = new MutableLiveData<>();
        return isReviewCreated;
    }
}
