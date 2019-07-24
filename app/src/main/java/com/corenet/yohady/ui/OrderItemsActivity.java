package com.corenet.yohady.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.corenet.yohady.R;
import com.corenet.yohady.adapter.OrderItemsAdapter;
import com.corenet.yohady.model.LineItem;
import com.corenet.yohady.model.Product;
import com.corenet.yohady.utils.ProductUtils;
import com.corenet.yohady.viewmodel.OrderItemsViewModel;

import java.util.ArrayList;

public class OrderItemsActivity extends AppCompatActivity {

    public static final String ORDER_ITEMS_KEY="order_items_key";
    RecyclerView orderItemsRecycler;
    OrderItemsViewModel orderItemsViewModel;
    private String TAG="ORDERITEMMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items);
        orderItemsRecycler=findViewById(R.id.orderItems_recycler);
        orderItemsViewModel= ViewModelProviders.of(this).get(OrderItemsViewModel.class);
        ArrayList<LineItem> orderItems=getIntent().getParcelableArrayListExtra(ORDER_ITEMS_KEY);
        Log.d(TAG, "list  "+orderItems.size());
        orderItemsViewModel.getProducts(null,null,null,null,null,null,
               null,null,null,null,null,null,null,
                ProductUtils.getOrderItemsIdsAsString(orderItems),null,null,null,null);


        observeOrderItems(orderItems);
        observeProductsLoading();
        observeProductsLoadingError();

    }

    void observeOrderItems(final ArrayList<LineItem> orderItems){
        orderItemsViewModel.getProducts()
                .observe(this, new Observer<ArrayList<Product>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Product> products) {
                        showOrderItems(orderItems,products);
                    }
                });

    }
    void observeProductsLoadingError(){
        orderItemsViewModel
                .getProductLoadingError()
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(getApplicationContext(), s
                                , Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void observeProductsLoading(){
        orderItemsViewModel
                .getIsProductsLoading()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean!=null&&aBoolean) {
                            Log.d(TAG, "onChanged: progress bar is visible");
                        }
                        else {
                            Log.d(TAG, "onChanged: progress bar is gone");
                        }
                    }
                });
    }

    void showOrderItems(ArrayList<LineItem> orderItems,ArrayList<Product> products){
        OrderItemsAdapter orderItemsAdapter=new OrderItemsAdapter(this,orderItems,products);
        orderItemsRecycler.setAdapter(orderItemsAdapter);
        orderItemsRecycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
