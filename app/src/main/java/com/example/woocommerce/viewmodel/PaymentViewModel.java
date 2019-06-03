package com.example.woocommerce.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.woocommerce.model.Order;
import com.example.woocommerce.model.OrderPayload;
import com.example.woocommerce.repository.OrdersRepo;

public class PaymentViewModel extends ViewModel {

    private MutableLiveData<Order> placeOrder;

    public void placeOrder(OrderPayload orderPayload){
        placeOrder=OrdersRepo.getInstance()
                .placeOrder(orderPayload);
    }

    public MutableLiveData<Order> placeOrder(){
        return placeOrder;
    }

    public MutableLiveData<Boolean> getOrderIsPlacing() {
        return OrdersRepo.getInstance().getOrderIsPlacing();
    }

    public MutableLiveData<String> getPlacingOrderError() {
        return OrdersRepo.getInstance().getPlacingOrderError();
    }
}
