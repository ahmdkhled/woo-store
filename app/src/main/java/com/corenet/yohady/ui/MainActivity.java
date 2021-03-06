package com.corenet.yohady.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.corenet.yohady.R;
import com.corenet.yohady.adapter.CategoriesAdapter;
import com.corenet.yohady.adapter.ProductAdapter;
import com.corenet.yohady.model.Category;
import com.corenet.yohady.model.Product;
import com.corenet.yohady.utils.PrefManager;
import com.corenet.yohady.viewmodel.CategoriesViewModel;
import com.corenet.yohady.viewmodel.MainAcrivityViewModel;
import com.crashlytics.android.Crashlytics;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener{
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
    Toolbar mToolbar;
    MaterialSearchBar searchBar;
    Crashlytics crashlytics;

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
        mToolbar=findViewById(R.id.toolbar);
        searchBar=findViewById(R.id.search_layout);

        // setup toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.main_activity_title);
        crashlytics=Crashlytics.getInstance();
        searchBar.setOnSearchActionListener(this);
        setupDrawer();
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

        mainAcrivityViewModel.getRecentlyAddedproducts(null,"6",null,null,null,
                null,null,null,null,null,"publish",null,
                null,null,null,null,null);
        observeRecentlyAdded();
        observeRecentlyAddedError();
        observeRecentlyAddedLoading();

        mainAcrivityViewModel.getDeals(null,"8",null,null ,
                null ,null ,null ,null,null,
                null,"publish",null,null,null ,null,null,null);
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
                launchActivity(ProductsActivity.class,ProductsActivity.TARGET_KEY,ProductsActivity.RA_TARGET);

            }
        });

        showAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(CategoriesActivity.class,null,null);
            }
        });

        showAllDeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(ProductsActivity.class,ProductsActivity.TARGET_KEY,ProductsActivity.DEALS_TARGET);
            }
        });

        showAllBestSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(ProductsActivity.class,ProductsActivity.TARGET_KEY,ProductsActivity.BESTSELLERS_TARGET);
            }
        });




    }

    void observeCategories(){
        categoriesViewModel.getCategories().observe(this, new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Category> categories) {
                if(categories == null)
                    Toast.makeText(MainActivity.this, com.corenet.yohady.R.string.error_message, Toast.LENGTH_SHORT).show();
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

    private void setupDrawer(){
        // setup navigation drawer
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem homeItem = new PrimaryDrawerItem().withIdentifier(1)
                .withName("Home")
                .withIcon(R.drawable.ic_home_black_24dp);

        PrimaryDrawerItem dealsItem = new PrimaryDrawerItem().withIdentifier(2)
                .withName("Deals")
                .withIcon(R.drawable.ic_loyalty_black_24dp);
        PrimaryDrawerItem recentlyAddedItem = new PrimaryDrawerItem().withIdentifier(3)
                .withName("Recently Added")
                .withIcon(R.drawable.ic_fiber_new_black_24dp);
        PrimaryDrawerItem bestSellingItem = new PrimaryDrawerItem().withIdentifier(4)
                .withName("Best Selling")
                .withIcon(R.drawable.ic_star_black_24dp);
        PrimaryDrawerItem categoryItem = new PrimaryDrawerItem().withIdentifier(5)
                .withName("Categories")
                .withIcon(R.drawable.ic_view_list_black_24dp);

        PrimaryDrawerItem ordersItem = new PrimaryDrawerItem().withIdentifier(6)
                .withName("track orders")
                .withIcon(R.drawable.ic_local_shipping_black_24dp);

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .addDrawerItems(
                        homeItem,categoryItem,dealsItem,recentlyAddedItem,bestSellingItem,ordersItem

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        Log.d("material_drawer","position is : "+position);
                        switch ((int) drawerItem.getIdentifier()){
                            case 1 : // launch home_activity
                                launchActivity(MainActivity.class,null,null);
                                break;
                            case 2 : // launch deals_activity
                                launchActivity(ProductsActivity.class,ProductsActivity.TARGET_KEY,ProductsActivity.DEALS_TARGET);
                                break;
                            case 3 : // launch recently_added_activity
                                launchActivity(ProductsActivity.class,ProductsActivity.TARGET_KEY,ProductsActivity.RA_TARGET);
                                break;
                            case 4 : // launch best_selling_activity
                                launchActivity(ProductsActivity.class,ProductsActivity.TARGET_KEY,ProductsActivity.BESTSELLERS_TARGET);
                                break;
                            case 5 : // launch categories_activity
                                launchActivity(CategoriesActivity.class,null,null);
                                break;
                            case 6 : // launch orders_activity
                                launchActivity(OrdersActivity.class,null,null);
                                break;
                        }

                        return false;
                    }
                })
                .build();
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

    private void launchActivity(Class destination,String extraKey,String extraValue){
        Intent intent = new Intent(this,destination);
        if(extraKey != null && extraValue != null){
            intent.putExtra(extraKey,extraValue);
        }
        startActivity(intent);
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
        Log.d("SEARCHHHH", "onSearchStateChanged: "+enabled);
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        Intent intent=new Intent(getApplicationContext(),ProductsActivity.class);
        String search= String.valueOf(text);
        intent.putExtra(ProductsActivity.SEARCH_INFO,search);
        intent.putExtra(ProductsActivity.TARGET_KEY,ProductsActivity.SEARCH_TARGET);
        startActivity(intent);
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        Log.d("SEARCHHHH", "onButtonClicked: ");
    }
}