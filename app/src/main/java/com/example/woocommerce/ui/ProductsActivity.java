package com.example.woocommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.woocommerce.R;
import com.example.woocommerce.adapter.ProductAdapter;
import com.example.woocommerce.model.Product;
import com.example.woocommerce.viewmodel.MainAcrivityViewModel;
import com.example.woocommerce.viewmodel.ProductsViewModel;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    ProductsViewModel productsViewModel;
    RecyclerView recentlyAddedRecycler;
    String target="";
    public static final String TARGET_KEY="target_key";
    public static final String RA_TARGET="recently_added";
    public static final String CATEGORIES_TARGET="category";
    public static final String DEALS_TARGET="deals";
    public static final String BESTSELLERS_TARGET="best_seller";
    public static final String CATEGORY_ID="category_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        recentlyAddedRecycler=findViewById(R.id.recentlyRecycler);
        productsViewModel =ViewModelProviders.of(this)
                .get(ProductsViewModel.class);

        target=getIntent().getStringExtra(TARGET_KEY);
        if (target.equals(RA_TARGET))
            productsViewModel.getProducts(null,null,null,null,null,
                null,null,null,null,null,null,null,
                null,null,null,null,null,null);

        else if (target.equals(DEALS_TARGET))
            productsViewModel.getProducts(null,null,null,null,null,
                    null,null,null,"true",null,null,null,
                    null,null,null,null,null,null);

        else if (target.equals(CATEGORIES_TARGET)){
            int categoruId=getIntent().getIntExtra(CATEGORY_ID,-1);
            productsViewModel.getProducts(null,null,null, String.valueOf(categoruId),null,
                    null,null,null,null,null,null,null,
                    null,null,null,null,null,null);
        }

        observeRecentlyAdded();
        observeProductsLoading();
        observeProductsLoadingError();

    }

    void observeRecentlyAdded(){
        if (productsViewModel.getProducts().hasObservers())
            return;
        productsViewModel.getProducts()
                .observe(this, new Observer<ArrayList<Product>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Product> products) {
                        showProducts(products);
                    }
                });
    }

    void observeProductsLoadingError(){
        productsViewModel
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
        productsViewModel
                .getIsProductsLoading()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {

                    }
                });
    }

    private void showProducts(ArrayList<Product> products) {
        ProductAdapter recentlyAddedAdapter=new ProductAdapter(this,products,false);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recentlyAddedRecycler.setAdapter(recentlyAddedAdapter);
        recentlyAddedRecycler.setLayoutManager(layoutManager);

    }

}
