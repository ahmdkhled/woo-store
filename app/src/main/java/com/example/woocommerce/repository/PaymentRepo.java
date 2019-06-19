package com.example.woocommerce.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.woocommerce.model.Coupon;
import com.example.woocommerce.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRepo {

    private static PaymentRepo paymentRepo;
    private MutableLiveData<ArrayList<Coupon>> coupon;
    private MutableLiveData<Boolean> isCouponLoading=new MutableLiveData<>();
    private MutableLiveData<String> couponLoadingError=new MutableLiveData<>();

    public static PaymentRepo getInstance() {
        if (paymentRepo==null)
            paymentRepo=new PaymentRepo();
        return paymentRepo;
    }

    public MutableLiveData<ArrayList<Coupon>> getCoupon(String context, String page,
                                             String per_page, String search,
                                             String after, String before,
                                             String exclude, String include,
                                             String offset, String order,
                                             String orderby, String code){
        coupon=new MutableLiveData<>();
        isCouponLoading.setValue(true);
        RetrofitClient
                .getInstance()
                .getApiService()
                .getCouponDetails(context,  page,
                          per_page,  search,
                          after,  before,
                          exclude,  include,
                          offset,  order,
                          orderby,  code)
                .enqueue(new Callback<ArrayList<Coupon>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Coupon>> call, Response<ArrayList<Coupon>> response) {
                        coupon.setValue(response.body());
                        Log.d("COUPOON", "coupon fetched from repo : "+response.code());

                        isCouponLoading.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Coupon>> call, Throwable t) {
                        couponLoadingError.setValue(t.getMessage());
                        isCouponLoading.setValue(false);
                        Log.d("COUPOON", "coupon error: "+t.getMessage());

                    }
                });
        return coupon;
    }

    public MutableLiveData<Boolean> getIsCouponLoading() {
        return isCouponLoading;
    }

    public MutableLiveData<String> getCouponLoadingError() {
        return couponLoadingError;
    }
}
