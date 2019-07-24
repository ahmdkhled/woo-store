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
    ProgressBar orderItemsPB;
    OrderItemsViewModel orderItemsViewModel;
    private String TAG="ORDERITEMMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items);
        orderItemsRecycler=findViewById(R.id.orderItems_recycler);
        orderItemsPB=findViewById(R.id.orderItem_PB);
        ArrayList<LineItem> orderItems=getIntent().getParcelableArrayListExtra(ORDER_ITEMS_KEY);
        if (orderItems==null){
            Toast.makeText(this, R.string.error_loading_order_Items, Toast.LENGTH_SHORT).show();
            finish();
        }

        orderItemsViewModel= ViewModelProviders.of(this).get(OrderItemsViewModel.class);
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
                            orderItemsPB.setVisibility(View.VISIBLE);
                        }
                        else {
                            orderItemsPB.setVisibility(View.GONE);
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
