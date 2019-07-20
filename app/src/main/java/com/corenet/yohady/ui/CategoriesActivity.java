package com.corenet.yohady.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.corenet.yohady.R;
import com.corenet.yohady.adapter.CategoriesAdapter;
import com.corenet.yohady.model.Category;
import com.corenet.yohady.utils.PrefManager;
import com.corenet.yohady.viewmodel.CategoriesViewModel;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {

    RecyclerView categoriesRecycler;
    CategoriesViewModel categoriesViewModel;
    ProgressBar progressBar;
    private TextView mCartBadgeTxt;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        categoriesRecycler=findViewById(R.id.categoriesRecycler);
        progressBar=findViewById(R.id.categories_PB);
        mToolbar=findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu,menu);
        final MenuItem cartItem = menu.findItem(R.id.menu_cart);
        View view = cartItem.getActionView();
        mCartBadgeTxt = view.findViewById(R.id.cart_badge_txt);
        setupBadge();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(cartItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_cart:
                // go to cart activity
                startActivity(new Intent(CategoriesActivity.this,CartActivity.class));
                break;
        }
        return true;
    }

    private void setupBadge() {
        PrefManager manager = PrefManager.getInstance(this);
        manager.getCartSize();
        manager.getmCartSize().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer cartSize) {
                if(cartSize != null){
                    if(mCartBadgeTxt != null){
                        if(cartSize == 0){
                            if(mCartBadgeTxt.getVisibility() != View.GONE){
                                mCartBadgeTxt.setVisibility(View.GONE);
                            }
                        }else{
                            mCartBadgeTxt.setText(String.valueOf(cartSize));
                            if(mCartBadgeTxt.getVisibility() != View.VISIBLE){
                                mCartBadgeTxt.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            }
        });


    }
}
