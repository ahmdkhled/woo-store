package com.example.woocommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woocommerce.R;
import com.example.woocommerce.adapter.ProductAdapter;
import com.example.woocommerce.model.Product;
import com.example.woocommerce.utils.BottomSheetListener;
import com.example.woocommerce.utils.PrefManager;
import com.example.woocommerce.viewmodel.ProductsViewModel;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity implements BottomSheetListener,
        MaterialSearchBar.OnSearchActionListener{

    public static final String SEARCH = "Results for ";
    public static final String SEARCH_QUERY = "search_query";
    ProductsViewModel productsViewModel;
    RecyclerView recentlyAddedRecycler;
    Button sortBy;
    ProgressBar progressBar;
    Toolbar mToolbar;
    TextView mToolbarTilte;
    String target="";
    public static final String TARGET_KEY="target_key";
    public static final String RA_TARGET="Recently Added";
    public static final String CATEGORIES_TARGET="Categories";
    public static final String DEALS_TARGET="Deals";
    public static final String BESTSELLERS_TARGET="Best Seller";
    public static final String CATEGORY_INFO="category_info";
    private String mSortByOption;
    private TextView mCartBadgeTxt;
    private String categoryId;
    private String categoryName;
    private Intent intent;
    private MaterialSearchBar mSearchBar;
    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        recentlyAddedRecycler=findViewById(R.id.recentlyRecycler);
        progressBar=findViewById(R.id.products_PB);
        sortBy=findViewById(R.id.sortBy_button);
        mToolbar=findViewById(R.id.toolbar);
        mToolbarTilte=findViewById(R.id.toolbar_title);
        mSearchBar=findViewById(R.id.searchBar);





        productsViewModel =ViewModelProviders.of(this)
                .get(ProductsViewModel.class);

        intent = getIntent();
        if(intent != null) {
            target = intent.getStringExtra(TARGET_KEY);
            // setup toolbar
            setSupportActionBar(mToolbar);
            mToolbarTilte.setText(target);
        }

        if(target.equals(CATEGORIES_TARGET)){
            String[] categoryInfo = getIntent().getStringArrayExtra(CATEGORY_INFO);
            categoryId = categoryInfo[0];
            categoryName = categoryInfo[1];
            mToolbarTilte.setText(categoryName);

        }

        if(target.equals(SEARCH)){
            searchQuery = getIntent().getStringExtra(SEARCH_QUERY);
            mToolbarTilte
                    .setText((new StringBuilder().append(SEARCH).append(searchQuery)).toString());

        }



        // load products
        loadProducts(null,null);


        sortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortByBottomSheet mSortBottomSheet=new SortByBottomSheet();
                mSortBottomSheet.show(getSupportFragmentManager(),mSortBottomSheet.getTag());
            }
        });


        mSearchBar.setOnSearchActionListener(this);
        

    }

    private void loadProducts(String order, String orderBy) {
        if(!target.equals(BESTSELLERS_TARGET)) {
            if (target.equals(RA_TARGET))
                loadRecentlyAddedProducts(order, orderBy);

            else if (target.equals(DEALS_TARGET))
                loadDeals(order, orderBy);

            else if (target.equals(CATEGORIES_TARGET)) {
                loadCategoryProducts(categoryId, order, orderBy);

            }else if(target.equals(SEARCH)){
                doSearch(searchQuery);
            }

            observeProducts();
            observeProductsLoading();
            observeProductsLoadingError();

        }else loadBestSellers(order,orderBy);




    }

    private void doSearch(String searchQuery) {
        productsViewModel.getProducts(null,null,searchQuery,null,null,
                null,null,null,null,null,null,null,
                null,null,null,null,null,null);
    }

    private void loadRecentlyAddedProducts(String order, String orderBy) {
        productsViewModel.getProducts(null,null,null,null,"date",
                null,null,null,null,null,null,null,
                null,null,null,null,null,null);
    }

    private void loadDeals(String order, String orderBy) {
        productsViewModel.getProducts(null,null,null,null,null,
                null,null,null,"true",null,null,null,
                null,null,null,null,null,null);
    }

    private void loadBestSellers(String order, String orderBy) {
        productsViewModel.getBestSellers("month",null ,null,null,null,
                null ,null ,"date",null,null,
                null, null,null,null,null,
                null,null, null,null,null);
        observeBestSeller();
        observebestSellerLoading();
        observebestSellersLoadingError();
    }

    private void loadCategoryProducts(String categoryId,String order, String orderBy) {
        Log.d("fromProductRepo","loadCategoryProducts");
        productsViewModel.getProducts(null,null,null,categoryId,null,
                null,null,null,null,null,null,null,
                null,null,null,null,null,null);
    }

    void observeProducts(){
        if(!productsViewModel.getProducts().hasActiveObservers()) {
            productsViewModel.getProducts()
                    .observe(this, new Observer<ArrayList<Product>>() {
                        @Override
                        public void onChanged(@Nullable ArrayList<Product> products) {
//                            Log.d("fromProductActivity", "products " + products.size());
                            if(products != null) {
                                showProducts(products);
                                if (products.size() == 0){
                                    Toast.makeText(ProductsActivity.this, "No results found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
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
                        if (aBoolean!=null&&aBoolean)
                            progressBar.setVisibility(View.VISIBLE);
                        else
                            progressBar.setVisibility(View.GONE);
                    }
                });
    }

    void observeBestSeller(){
        productsViewModel.getBestSellers()
                .observe(this, new Observer<ArrayList<Product>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Product> products) {
                        showProducts(products);
                    }
                });
    }

    void observebestSellersLoadingError(){
        productsViewModel
                .getBestSellersLoadingError()
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(getApplicationContext(), s
                                , Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void observebestSellerLoading(){
        productsViewModel
                .getIsBestSellersLoading()
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

    private void showProducts(ArrayList<Product> products) {
        ProductAdapter recentlyAddedAdapter=new ProductAdapter(this,products,false);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recentlyAddedRecycler.setAdapter(recentlyAddedAdapter);
        recentlyAddedRecycler.setLayoutManager(layoutManager);

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
                startActivity(new Intent(ProductsActivity.this,CartActivity.class));
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

    @Override
    public void onBottomSheetOptionClicked(String sortBy) {
        String orderBy = null;
        String order = "desc";
        Log.d("fromProductActivity","sort by "+sortBy);
        if(mSortByOption == null || !mSortByOption.equals(sortBy)) {
            mSortByOption = sortBy;
            switch (sortBy) {
                case SortByBottomSheet.SORT_BY_POPULARITY:
                    orderBy = "popularity";
                    break;
                case SortByBottomSheet.SORT_BY_LATEST:
                    orderBy = "date";
                    break;
                case SortByBottomSheet.SORT_BY_AVG_RATE:
                    orderBy = "rating";
                    break;
                case SortByBottomSheet.SORT_BY_PRICE_LOW_HIGH:
                    orderBy = "price";
                    order = "asc";
                    break;
                case SortByBottomSheet.SORT_BY_PRICE_HIGH_LOW:
                    orderBy = "price";
                    break;
            }

            Log.d("fromProductActivity","order_by = "+orderBy+" order = "+order);
            loadProducts(order,orderBy);
        }

    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        mToolbarTilte
                .setText((new StringBuilder().append(SEARCH).append(text.toString())).toString());
        doSearch(text.toString());
        observeProducts();
        observeProductsLoading();
        observeProductsLoadingError();
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }
}
