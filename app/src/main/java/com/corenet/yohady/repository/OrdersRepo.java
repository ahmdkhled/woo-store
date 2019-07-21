package com.corenet.yohady.repository;

import android.arch.lifecycle.MutableLiveData;

import com.corenet.yohady.model.Order;
import com.corenet.yohady.model.OrderPayload;
import com.corenet.yohady.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersRepo {

    private static OrdersRepo ordersRepo;
    private MutableLiveData<Order> placeOrder;
    private MutableLiveData<Boolean> orderIsPlacing=new MutableLiveData<>();
    private MutableLiveData<String> placingOrderError=new MutableLiveData<>();

    private MutableLiveData<ArrayList<Order>> orders;
    private MutableLiveData<Boolean> isOrdersLoading=new MutableLiveData<>();
    private MutableLiveData<String> orderLoadingError=new MutableLiveData<>();


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

    public MutableLiveData<ArrayList<Order>> getOrders( String context, String page,
                                             String per_page, String search,
                                             String after, String before,
                                             String exclude, String include,
                                             String offset, String order,
                                             String orderby, String product,
                                             String status, String customer,
                                             String parent, String parent_exclude,
                                             String dp){
        orders=new MutableLiveData<>();
        isOrdersLoading.setValue(true);


        RetrofitClient
                .getInstance()
                .getApiService()
                .getOrders(context,page,per_page,search,after,before,exclude,include,offset,order,orderby,product,
                        status,customer,parent,parent_exclude,dp)
                .enqueue(new Callback<ArrayList<Order>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                        orders.setValue(response.body());
                        isOrdersLoading.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                        orderLoadingError.setValue(t.getMessage());
                        isOrdersLoading.setValue(false);
                    }
                });


        return orders;
        
    }
    

    public MutableLiveData<Boolean> getOrderIsPlacing() {
        return orderIsPlacing;
    }

    public MutableLiveData<String> getPlacingOrderError() {
        return placingOrderError;
    }

    public MutableLiveData<Boolean> getIsOrdersLoading() {
        return isOrdersLoading;
    }

    public MutableLiveData<String> getOrderLoadingError() {
        return orderLoadingError;
    }
}
