package com.corenet.yohady.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.corenet.yohady.model.Order;
import com.corenet.yohady.repository.OrdersRepo;

import java.util.ArrayList;

public class OrdersViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Order>> orders;

    public void getOrders(String context, String page,
                                            String per_page, String search,
                                            String after, String before,
                                            String exclude, String include,
                                            String offset, String order,
                                            String orderby, String product,
                                            String status, String customer,
                                            String parent, String parent_exclude,
                                            String dp) {
        orders= OrdersRepo.getInstance().getOrders(context,page,per_page,search,after,before,exclude,include,
                offset,order,orderby,product,status,customer,parent,parent_exclude,dp);

    }

    public MutableLiveData<ArrayList<Order>> getOrders() {
        return orders;
    }

    public MutableLiveData<Boolean> getIsOrdersLoading() {
        return OrdersRepo.getInstance().getIsOrdersLoading();
    }

    public MutableLiveData<String> getOrderLoadingError() {
        return OrdersRepo.getInstance().getOrderLoadingError();
    }
}
