package com.example.woocommerce.repository;

import android.arch.lifecycle.MutableLiveData;

import com.example.woocommerce.model.Order;
import com.example.woocommerce.model.OrderPayload;
import com.example.woocommerce.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersRepo {

    private static OrdersRepo ordersRepo;
    private MutableLiveData<Order> placeOrder;
    private MutableLiveData<Boolean> orderIsPlacing=new MutableLiveData<>();
    private MutableLiveData<String> placingOrderError=new MutableLiveData<>();

    public static OrdersRepo getInstance() {
        if (ordersRepo==null)
            ordersRepo=new OrdersRepo();
        return ordersRepo;
    }

    public MutableLiveData<Order> placeOrder(OrderPayload orderPayload){
        placeOrder=new MutableLiveData<>();
        orderIsPlacing.setValue(true);
        RetrofitClient
                .getInstance()
                .getApiService()
                .createOrder(orderPayload)
                .enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        placeOrder.setValue(response.body());
                        orderIsPlacing.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        placingOrderError.setValue(t.getMessage());
                    }
                });
        return placeOrder;
    }


    public MutableLiveData<Boolean> getOrderIsPlacing() {
        return orderIsPlacing;
    }

    public MutableLiveData<String> getPlacingOrderError() {
        return placingOrderError;
    }
}
