package com.example.woocommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.woocommerce.R;
import com.example.woocommerce.adapter.CategoriesAdapter;
import com.example.woocommerce.adapter.ProductAdapter;
import com.example.woocommerce.model.Category;
import com.example.woocommerce.model.Product;
import com.example.woocommerce.utils.PrefManager;
import com.example.woocommerce.viewmodel.CategoriesViewModel;
import com.example.woocommerce.viewmodel.MainAcrivityViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    RecyclerView categoriesRecycler,
            recentlyAddedRecycler,
            dealsRecycler,
            bestSellerRecycler;
    CategoriesAdapter categoriesAdapter;
    CategoriesViewModel categoriesViewModel;
    MainAcrivityViewModel mainAcrivityViewModel;
    TextView showAllCategories,showAllRecently,
            showAllDeals,showAllBestSeller;
    ArrayList<Category> categoriesList;
    ShimmerFrameLayout categoriesShimmer,
                recentlyAddedShimmer,
                dealsShimmer,
                bestsellerShimmer;
    TextView mCartBadgeTxt;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

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
        showAllDeals=findViewById(R.id.see_all_deals);
        showAllBestSeller=findViewById(R.id.see_all_bestsellers);
        categoriesShimmer=findViewById(R.id.categoriesShimmer);
        recentlyAddedShimmer=findViewById(R.id.recentlyAdded_shimmer);
        dealsShimmer=findViewById(R.id.deals_shimmer);
        bestsellerShimmer=findViewById(R.id.bestSeller_shimmer);
        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navigationView);
        toolbar=findViewById(R.id.toolbar);

        // setup toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.main_activity_title);

        categoriesViewModel= ViewModelProviders
                .of(this)
                .get(CategoriesViewModel.class);
        mainAcrivityViewModel =ViewModelProviders.of(this)
                .get(MainAcrivityViewModel.class);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,
                toolbar,R.string.open_drawer, R.string.close_drawer);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);

        categoriesViewModel.getCategories(null,"5","0",null,
                null,null,null,null,null,null,null);
        observeCategories();
        observeCategoriesError();
        observeCategoriesLoading();

        mainAcrivityViewModel.getRecentlyAddedproducts(null,"6",null,null,null,
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

        showAllDeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ProductsActivity.class);
                intent.putExtra(ProductsActivity.TARGET_KEY,ProductsActivity.DEALS_TARGET);
                startActivity(intent);
            }
        });

        showAllBestSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ProductsActivity.class);
                intent.putExtra(ProductsActivity.TARGET_KEY,ProductsActivity.BESTSELLERS_TARGET);
                startActivity(intent);
            }
        });


        // setup navigation drawer
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1)
                .withName("Home")
                .withIcon(R.drawable.ic_home_black_24dp)
                .withBadge("1")
                .withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.colorPrimary));
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2)
                .withName("Setting")
                .withIcon(R.drawable.ic_settings_black_24dp);

        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3)
                .withName("Account")
                .withIcon(R.drawable.ic_account_box_black_24dp);

        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4)
                .withName("Deals")
                .withIcon(R.drawable.deals);

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,item2,item3,item4,
                        new DividerDrawerItem()

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        return true;
                    }
                })
                .build();


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
                        //Log.d("from_product_repo","observeProducts");
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
                            dealsShimmer.stopShimmer();
                            dealsShimmer.setVisibility(View.GONE);
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
                            bestsellerShimmer.setVisibility(View.GONE);
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
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        categoriesShimmer.startShimmer();
        recentlyAddedShimmer.startShimmer();
        dealsShimmer.startShimmer();
        bestsellerShimmer.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        categoriesShimmer.stopShimmer();
        recentlyAddedShimmer.stopShimmer();
        dealsShimmer.stopShimmer();
        bestsellerShimmer.stopShimmer();
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
                startActivity(new Intent(MainActivity.this,CartActivity.class));
                break;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
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
