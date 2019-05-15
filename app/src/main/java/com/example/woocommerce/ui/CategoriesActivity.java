package com.example.woocommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.woocommerce.R;
import com.example.woocommerce.adapter.CategoriesAdapter;
import com.example.woocommerce.model.Category;
import com.example.woocommerce.viewmodel.CategoriesViewModel;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {

    RecyclerView categoriesRecycler;
    CategoriesViewModel categoriesViewModel;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        categoriesRecycler=findViewById(R.id.categoriesRecycler);
        progressBar=findViewById(R.id.categories_PB);
        categoriesViewModel= ViewModelProviders.of(this).get(CategoriesViewModel.class);

        categoriesViewModel.getCategories(null,null,"0",null,null ,
                null ,null,null ,null,null,null);
        observeCategories();
        observeCategoriesError();
        observeCategoriesLoading();
    }

    void observeCategories(){
        categoriesViewModel.getCategories().observe(this, new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Category> categories) {
                if(categories == null)
                    Toast.makeText(getApplicationContext(),R.string.error_message, Toast.LENGTH_SHORT).show();
                else {
                    showCategories(categories);
                }
            }
        });
    }

    void observeCategoriesError(){
        categoriesViewModel.getCategoriesLoadingError()
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(getApplicationContext(), s
                                , Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void observeCategoriesLoading(){
        categoriesViewModel.getIsCategoriesLoading()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean!=null&&aBoolean)
                            progressBar.setVisibility(View.VISIBLE);
                        else
                            progressBar.setVisibility(View.GONE);

                    }
                });
    }

    void showCategories(ArrayList<Category> categoriesList){
        CategoriesAdapter categoriesAdapter=new CategoriesAdapter(this,categoriesList);
        StaggeredGridLayoutManager layoutManager=new
                StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        categoriesRecycler.setAdapter(categoriesAdapter);
        categoriesRecycler.setLayoutManager(layoutManager);
    }
}
