package com.example.woocommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.woocommerce.R;
import com.example.woocommerce.adapter.CategoriesAdapter;
import com.example.woocommerce.adapter.ProductAdapter;
import com.example.woocommerce.model.Category;
import com.example.woocommerce.model.Product;
import com.example.woocommerce.viewmodel.CategoriesViewModel;
import com.example.woocommerce.viewmodel.ProductsViewModel;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    RecyclerView categoriesRecycler,
            recentlyAddedRecycler;
    CategoriesAdapter categoriesAdapter;
    CategoriesViewModel categoriesViewModel;
    ProductsViewModel productsViewModel;
    TextView showAllCategories,showAllRecently;
    ArrayList<Category> categoriesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoriesRecycler=findViewById(R.id.categoriesRecycler);
        recentlyAddedRecycler=findViewById(R.id.RecentlyRecycler);
        showAllRecently=findViewById(R.id.see_all_recently);
        showAllCategories=findViewById(R.id.seeAllCategories);
        categoriesViewModel= ViewModelProviders
                .of(this)
                .get(CategoriesViewModel.class);
        productsViewModel=ViewModelProviders.of(this)
                .get(ProductsViewModel.class);


        categoriesViewModel.getCategories(null,"5","0",null,
                null,null,null,null,null,null,null);
        observeCategories();
        observeCategoriesError();

        productsViewModel.getProducts(null,"10",null,null,"date",null,
                null,null,null,null,null,"publish",null,
                null,null,null,null,null);
        observeRecentlyAdded();
        observeRecentlyAddedError();


        showAllRecently.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ProductsActivity.class);
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
        productsViewModel.getProducts()
                .observe(this, new Observer<ArrayList<Product>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Product> products) {
                        if(products == null)
                            Toast.makeText(MainActivity.this, com.example.woocommerce.R.string.error_message, Toast.LENGTH_SHORT).show();
                        else showRecentlyAddedProducts(products);
                    }
                });
    }

    void observeRecentlyAddedError(){
        productsViewModel
                .getProductsLoadingError()
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

    private void showRecentlyAddedProducts(ArrayList<Product> products) {
        ProductAdapter recentlyAddedAdapter=new ProductAdapter(this,products,true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this
                ,LinearLayoutManager.HORIZONTAL,false);
        recentlyAddedRecycler.setAdapter(recentlyAddedAdapter);
        recentlyAddedRecycler.setLayoutManager(layoutManager);

    }




}
