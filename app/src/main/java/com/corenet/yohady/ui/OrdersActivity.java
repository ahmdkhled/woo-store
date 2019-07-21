package com.corenet.yohady.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.corenet.yohady.R;
import com.corenet.yohady.adapter.OrdersAdapter;
import com.corenet.yohady.model.Order;
import com.corenet.yohady.viewmodel.OrdersViewModel;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {

    OrdersViewModel ordersViewModel;
    OrdersAdapter ordersAdapter;
    RecyclerView ordersRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        ordersRecycler=findViewById(R.id.orders_recycler);
        ordersViewModel= ViewModelProviders.of(this).get(OrdersViewModel.class);
        ordersAdapter=new OrdersAdapter(this,null);
        ordersRecycler.setAdapter(ordersAdapter);
        ordersRecycler.setLayoutManager(new LinearLayoutManager(this));


        ordersViewModel.getOrders(null ,null,null,null,
                null,null,null,null,null,
                null,null,null,null,
                "7", null,null,null);

        observeOrdersLoading();
        observeOrdersLoadingError();

    }

    private void observeOrdersLoading(){
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
    private void observeOrdersLoadingError(){
        ordersViewModel.getOrderLoadingError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d("OORRDDER", "error: "+s);
            }
        });
    }
}
