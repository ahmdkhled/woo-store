package com.example.woocommerce.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.woocommerce.model.Review;
import com.example.woocommerce.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsRepo {
    private static ReviewsRepo reviewsRepo;
    private MutableLiveData<ArrayList<Review>> reviews=new MutableLiveData<>();
    private MutableLiveData<Boolean> isReviewsLoading=new MutableLiveData<>();
    private MutableLiveData<String> reviewsLoadingError=new MutableLiveData<>();

    public  static ReviewsRepo getInstance() {
        if (reviewsRepo==null)
            reviewsRepo=new ReviewsRepo();
        return reviewsRepo;
    }

    private ReviewsRepo() {
    }

    public MutableLiveData<ArrayList<Review>> getReviews(String page,int product_id){
        Log.d("REVIEWW", "getting Reviews: ");

        isReviewsLoading.setValue(true);
        RetrofitClient.getInstance()
                .getApiService()
                .getReviews(page,product_id)
                .enqueue(new Callback<ArrayList<Review>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                        reviews.setValue(response.body());
                        isReviewsLoading.setValue(false);

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                        isReviewsLoading.setValue(false);
                        reviewsLoadingError.setValue(t.getMessage());
                    }
                });
        return reviews;
    }

    public MutableLiveData<Boolean> getIsReviewsLoading() {
        return isReviewsLoading;
    }

    public MutableLiveData<String> getReviewsLoadingError() {
        return reviewsLoadingError;
    }
}
