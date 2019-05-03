package com.example.woocommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.woocommerce.R;
import com.example.woocommerce.adapter.CategoriesAdapter;
import com.example.woocommerce.adapter.ProductAdapter;
import com.example.woocommerce.model.Category;
import com.example.woocommerce.model.Product;
import com.example.woocommerce.viewmodel.CategoriesViewModel;
import com.example.woocommerce.viewmodel.MainAcrivityViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    RecyclerView categoriesRecycler,
            recentlyAddedRecycler,
            dealsRecycler,
            bestSellerRecycler;
    CategoriesAdapter categoriesAdapter;
    CategoriesViewModel categoriesViewModel;
    MainAcrivityViewModel mainAcrivityViewModel;
    TextView showAllCategories,showAllRecently;
    ArrayList<Category> categoriesList;
    ShimmerFrameLayout categoriesShimmer
            ,recentlyAddedShimmer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoriesRecycler=findViewById(R.id.categoriesRecycler);
        recentlyAddedRecycler=findViewById(R.id.RecentlyRecycler);
        dealsRecycler=findViewById(R.id.deals_recycler);
        bestSellerRecycler=findViewById(R.id.bestRecycler);
        showAllRecently=findViewById(R.id.see_all_recently);
        showAllCategories=findViewById(R.id.seeAllCategories);
        categoriesShimmer=findViewById(R.id.categoriesShimmer);
        recentlyAddedShimmer=findViewById(R.id.recentlyAdded_shimmer);
        categoriesViewModel= ViewModelProviders
                .of(this)
                .get(CategoriesViewModel.class);
        mainAcrivityViewModel =ViewModelProviders.of(this)
                .get(MainAcrivityViewModel.class);


        categoriesViewModel.getCategories(null,"5","0",null,
                null,null,null,null,null,null,null);
        observeCategories();
        observeCategoriesError();
        observeCategoriesLoading();

        mainAcrivityViewModel.getRecentlyAddedproducts(null,"5",null,null,null,
                null,null,null,null,null,"publish",null,
                null,null,null,null,null);
        observeRecentlyAdded();
        observeRecentlyAddedError();
        observeRecentlyAddedLoading();

        mainAcrivityViewModel.getDeals(null,"8",null,null ,
                null ,null ,null ,null,null,
                null,null,null,null,null ,null,null,null);
        observeDeals();
        observeDealsError();
        observeDealsLoading();

        mainAcrivityViewModel.getBestSellers("month",null ,null,null,null,
                                        null ,null ,"date",null,null,
                                     null, null,null,null,null,
                                    null,null, null,null,null);
        observeBestSeller();
        observeBestSellerError();
        observeBestSellersLoading();

        showAllRecently.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ProductsActivity.class);
                intent.putExtra(ProductsActivity.TARGET_KEY,ProductsActivity.RA_TARGET);
                startActivity(intent);
            }
        });

        showAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CategoriesActivity.class);
                startActivity(intent);
            }
        });


    }

    void observeCategories(){
        categoriesViewModel.getCategories().observe(this, new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Category> categories) {
                if(categories == null)
                    Toast.makeText(MainActivity.this, com.example.woocommerce.R.string.error_message, Toast.LENGTH_SHORT).show();
                else {
                    showCategories(categories);
                    categoriesList=categories;

                }
            }
        });
    }

    void observeCategoriesLoading(){
        categoriesViewModel
                .getIsCategoriesLoading()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean!=null&&!aBoolean){
                            categoriesShimmer.stopShimmer();
                            categoriesShimmer.setVisibility(View.GONE);
                        }
                    }
                });
    }

    void observeCategoriesError(){
        categoriesViewModel.getCategoriesLoadingError()
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(MainActivity.this, s
                                , Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void observeRecentlyAdded(){
        mainAcrivityViewModel.getRecentlyAddedProducts()
                .observe(this, new Observer<ArrayList<Product>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Product> products) {
                        //Log.d("from_product_repo","observeRecentlyAdded");
                        if(products == null)
                            Toast.makeText(MainActivity.this,
                                    R.string.error_message
                                    , Toast.LENGTH_SHORT).show();
                        else showProducts(recentlyAddedRecycler,products);
                    }
                });
    }

    void observeDeals(){

        mainAcrivityViewModel.getDeals()
                .observe(this, new Observer<ArrayList<Product>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Product> products) {
                        //Log.d("from_product_repo","observeDeals");
                        if(products == null)
                            Toast.makeText(MainActivity.this,
                                    R.string.error_message
                                    , Toast.LENGTH_SHORT).show();
                        else showProducts(dealsRecycler,products);
                    }
                });
    }

    void observeBestSeller(){

        mainAcrivityViewModel.getBestSellers()
                .observe(this, new Observer<ArrayList<Product>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Product> products) {
                        Log.d("from_product_repo","observe bestseller "+products.size());
                        if(products == null)
                            Toast.makeText(MainActivity.this,
                                    R.string.error_message
                                    , Toast.LENGTH_SHORT).show();
                        else showProducts(bestSellerRecycler,products);
                    }
                });
    }

    void observeRecentlyAddedLoading(){
        mainAcrivityViewModel
                .getIsRecentlyAddedProductsLoading()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean!=null&&!aBoolean){
                            recentlyAddedShimmer.stopShimmer();
                            recentlyAddedShimmer.setVisibility(View.GONE);
                        }
                    }
                });
    }

    void observeDealsLoading(){
        mainAcrivityViewModel
                .getIsDealsLoading()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean!=null&&!aBoolean){

                        }
                    }
                });
    }

    void observeBestSellersLoading(){
        mainAcrivityViewModel
                .getIsBestSellersLoading()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean!=null&&!aBoolean){

                        }
                    }
                });
    }

    void observeRecentlyAddedError(){
        mainAcrivityViewModel
                .getRecentlyAddedProductsLoadingError()
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(MainActivity.this, s
                                , Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void observeDealsError(){
        mainAcrivityViewModel
                .getDealsLoadingError()
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(MainActivity.this, s
                                , Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void observeBestSellerError(){
        mainAcrivityViewModel
                .getBestSellersLoadingError()
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(MainActivity.this, s
                                , Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showCategories(ArrayList<Category> categories) {
        categoriesAdapter=new CategoriesAdapter(this,categories,true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this
                ,LinearLayoutManager.HORIZONTAL,false);
        categoriesRecycler.setAdapter(categoriesAdapter);
        categoriesRecycler.setLayoutManager(layoutManager);

    }


    private void showProducts(RecyclerView  recyclerView, ArrayList<Product> products) {
        ProductAdapter productAdapter=new ProductAdapter(this,products,true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this
                ,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        categoriesShimmer.startShimmer();
        recentlyAddedShimmer.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        categoriesShimmer.stopShimmer();
        recentlyAddedShimmer.stopShimmer();
    }
}
