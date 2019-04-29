package com.example.woocommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.woocommerce.R;
import com.example.woocommerce.adapter.RecentlyAddedAdapter;
import com.example.woocommerce.model.Product;
import com.example.woocommerce.viewmodel.ProductsViewModel;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    ProductsViewModel productsViewModel;
    RecyclerView recentlyAddedRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        recentlyAddedRecycler=findViewById(R.id.recentlyRecycler);
        productsViewModel=ViewModelProviders.of(this)
                .get(ProductsViewModel.class);


        productsViewModel.getProducts(null,"10",null,null,"date",null,
                null,null,null,null,null,"publish",null,
                null,null,null,null,null);

        observeRecentlyAdded();
        observeRecentlyAddedError();

    }

    void observeRecentlyAdded(){
        productsViewModel.getProducts()
                .observe(this, new Observer<ArrayList<Product>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Product> products) {
                        showRecentlyAddedProducts(products);
                    }
                });
    }

    void observeRecentlyAddedError(){
        productsViewModel
                .getProductsLoadingError()
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(getApplicationContext(), s
                                , Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showRecentlyAddedProducts(ArrayList<Product> products) {
        RecentlyAddedAdapter recentlyAddedAdapter=new RecentlyAddedAdapter(this,products);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recentlyAddedRecycler.setAdapter(recentlyAddedAdapter);
        recentlyAddedRecycler.setLayoutManager(layoutManager);

    }

}