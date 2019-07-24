package com.corenet.yohady.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.corenet.yohady.R;
import com.corenet.yohady.adapter.OrdersAdapter;
import com.corenet.yohady.model.Order;
import com.corenet.yohady.viewmodel.OrdersViewModel;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {

    OrdersViewModel ordersViewModel;
    OrdersAdapter ordersAdapter;
    RecyclerView ordersRecycler;
    ProgressBar ordersPB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        ordersRecycler=findViewById(R.id.orders_recycler);
        ordersPB=findViewById(R.id.orders_PB);
        ordersViewModel= ViewModelProviders.of(this).get(OrdersViewModel.class);
        ordersAdapter=new OrdersAdapter(this,null);
        ordersRecycler.setAdapter(ordersAdapter);
        ordersRecycler.setLayoutManager(new LinearLayoutManager(this));


        ordersViewModel.getOrders(null ,null,null,null,
                null,null,null,null,null,
                null,null,null,null,
                "7", null,null,null);

        observeOrders();
        observeOrdersLoading();
        observeOrdersLoadingError();

    }

    private void observeOrders(){
        ordersViewModel.getOrders()
                .observe(this, new Observer<ArrayList<Order>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Order> orders) {
                if (orders!=null&&orders.size()>0){
                    ordersAdapter.addOrders(orders);
                }

            }
        });
    }

    private void observeOrdersLoading(){
        ordersViewModel
                .getIsOrdersLoading()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean!=null&&aBoolean){
                            ordersPB.setVisibility(View.VISIBLE);
                        }else {
                            ordersPB.setVisibility(View.GONE);
                        }
                    }
                });
    }


    private void observeOrdersLoadingError(){
        ordersViewModel.getOrderLoadingError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d("OORRDDER", "Error loading Orders ");
            }
        });
    }
}
