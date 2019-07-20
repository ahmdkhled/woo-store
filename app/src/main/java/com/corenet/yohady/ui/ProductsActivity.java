package com.corenet.yohady.ui;

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

import com.corenet.yohady.R;
import com.corenet.yohady.adapter.ProductAdapter;
import com.corenet.yohady.model.Product;
import com.corenet.yohady.utils.BottomSheetListener;
import com.corenet.yohady.utils.EndlessRecyclerViewScrollListener;
import com.corenet.yohady.utils.PrefManager;
import com.corenet.yohady.viewmodel.ProductsViewModel;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity
        implements BottomSheetListener {

    private static final String TAG = "sortFeatureLogs";

    private static final String SORT_TARGET = "sortSearch";
    ProductsViewModel productsViewModel;
    RecyclerView recentlyAddedRecycler;
    Button sortBy;
    ProgressBar progressBar;
    ProgressBar loadMorePB;
    Toolbar mToolbar;
    TextView mToolbarTilte;
    String target="";
    /*
    * if mTypeFlag = false this means adapter will swap data
    * otherwise user scrolls down so adapter will add new products to old ones
    * */
    boolean mTypeFlag = false;
    public static final String TARGET_KEY="target_key";
    public static final String RA_TARGET="Recently Added";
    public static final String CATEGORIES_TARGET="Categories";
    public static final String DEALS_TARGET="Deals";
    public static final String BESTSELLERS_TARGET="Best Seller";
    public static final String SEARCH_TARGET="search_rarget";
    public static final String CATEGORY_INFO="category_info";
    public static final String SEARCH_INFO="search_tag";
    private String mSortByOption ="";
    GridLayoutManager layoutManager;
    ProductAdapter productsAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private TextView mCartBadgeTxt;
    private String categoryId;
    private String categoryName;
    boolean productsLoaded=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        recentlyAddedRecycler=findViewById(R.id.recentlyRecycler);
        progressBar=findViewById(R.id.products_PB);
        loadMorePB=findViewById(R.id.loadMorePB);
        sortBy=findViewById(R.id.sortBy_button);
        mToolbar=findViewById(R.id.toolbar);
        mToolbarTilte=findViewById(R.id.toolbar_title);


        productsViewModel =ViewModelProviders.of(this)
                .get(ProductsViewModel.class);

        layoutManager=new GridLayoutManager(this,2);


        target=getIntent().getStringExtra(TARGET_KEY);
        loadProducts(null,null,1);

        productsAdapter=new ProductAdapter(this,null,false);
        recentlyAddedRecycler.setAdapter(productsAdapter);
        recentlyAddedRecycler.setLayoutManager(layoutManager);

        if(target.equals(CATEGORIES_TARGET)){
            String[] categoryInfo = getIntent().getStringArrayExtra(CATEGORY_INFO);
            categoryId = categoryInfo[0];
            categoryName = categoryInfo[1];
            Log.d("IDIDIDI", "id: "+categoryId);


        }
        setupToolbar();



        sortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortByBottomSheet mSortBottomSheet=new SortByBottomSheet();
                mSortBottomSheet.show(getSupportFragmentManager(),mSortBottomSheet.getTag());
            }
        });

        scrollListener=new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(!target.isEmpty()) {
                    page++;
                    loadProducts(null, null, page);
                    loadMorePB.setVisibility(View.VISIBLE);
                    Log.d("PAGGIINGNG", "onLoadMore: " + page);
                }
            }
        };


        recentlyAddedRecycler.addOnScrollListener(scrollListener);
    }



    private void loadProducts(String order, String orderBy, int page) {
        Log.d("IDIDIDI", "target: "+categoryId);

        if(!target.equals(BESTSELLERS_TARGET)) {
            if (target.equals(RA_TARGET))
                loadRecentlyAddedProducts(order, orderBy,page);

            else if (target.equals(DEALS_TARGET))
                loadDeals(order, orderBy,page);

            else if (target.equals(CATEGORIES_TARGET)) {
                String[] categoryInfo = getIntent().getStringArrayExtra(CATEGORY_INFO);
                categoryId = categoryInfo[0];
                loadCategoryProducts(categoryId, order, orderBy,page);

            }
            else if (target.equals(SEARCH_TARGET)){
                String search=getIntent().getStringExtra(SEARCH_INFO);
                Log.d("SEARCHHHH", "has extra: "+getIntent().hasExtra(SEARCH_INFO)+getIntent().getStringExtra(SEARCH_INFO) );
                loadSearchProducts(search,order,order,page);
            }

            observeProducts();
            observeProductsLoading();
            observeProductsLoadingError();

        }else loadBestSellers(order,orderBy,page);




    }

    private void sortProducts(String order, String orderBy, int page) {
        Log.d(TAG, "sortProducts: inside");
        mTypeFlag = true;
        productsLoaded = false;
        mToolbarTilte.setText(!orderBy.equals("price")?"Sort By "+orderBy:
                order.equals("asc")?"Sort By "+orderBy+" from low to high":
                        "Sort By "+orderBy+" from high to low");
        productsViewModel.getProducts(String.valueOf(page),null,null,null,orderBy,
                order,null,null,null,null,null,null,
                null,null,null,null,null,null);


    }

    private void loadRecentlyAddedProducts(String order, String orderBy, int page) {
        productsViewModel.getProducts(String.valueOf(page),null,null,null,"date",
                null,null,null,null,null,null,null,
                null,null,null,null,null,null);
    }

    private void loadDeals(String order, String orderBy, int page) {
        productsViewModel.getProducts(String.valueOf(page),null,null,null,null,
                null,null,null,"true",null,null,null,
                null,null,null,null,null,null);
    }

    private void loadBestSellers(String order, String orderBy,int page) {
        Log.d("BESTSELLERRR", "loadBestSellers: ");
        productsViewModel.getBestSellers("month",null ,null,String.valueOf(page),null,
                null ,null ,"date",null,null,
                null, null,null,null,null,
                null,null, null,null,null);
        observeBestSeller();
        observebestSellerLoading();
        observebestSellersLoadingError();
    }

    private void loadCategoryProducts(String categoryId,String order, String orderBy,int page) {
        Log.d("fromProductRepo","loadCategoryProducts");
        productsViewModel.getProducts(String.valueOf(page),null,null,categoryId,null,
                null,null,null,null,null,null,null,
                null,null,null,null,null,null);
    }

    private void loadSearchProducts(String search,String order, String orderBy,int page){
        Log.d("SEARCHHHH", "loadSearchProducts: "+search);
        mTypeFlag = true;
        productsLoaded = false;
        target = "";
        mToolbarTilte.setText("Results For "+search);
        productsViewModel.getProducts(String.valueOf(page),null,search,null,orderBy
                ,order,null ,null,null,null,null,
                null,null,null,null,null,null,null);
    }

    void observeProducts(){
        Log.d(TAG, "observeProducts: init observer");
            productsViewModel.getProducts()
                    .observe(this, new Observer<ArrayList<Product>>() {
                        @Override
                        public void onChanged(@Nullable ArrayList<Product> products) {
                            if(products != null) {
                                //Log.d(TAG, "onChanged: products size is " + products.size());
                                //Log.d(TAG, "onChanged: first item is "+products.get(0).getName());
                                if(mTypeFlag){
                                    productsAdapter.swapDate(products);
                                    mTypeFlag = false;
                                }else {
                                    productsAdapter.addProducts(products);
                                    loadMorePB.setVisibility(View.GONE);
                                }
                                productsLoaded = true;
                            }else Log.d(TAG, "onChanged: products are null");

                        }});

    }

    void observeProductsLoadingError(){
        Log.d("fromProductRepo", "observeProductsLoadingError: ");
        productsViewModel
                .getProductLoadingError()
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Log.d(TAG, "onChanged: loading error "+s);
                        loadMorePB.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), s
                                , Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void observeProductsLoading(){
        Log.d(TAG, "observeProductsLoading: init observer");
        productsViewModel
                .getIsProductsLoading()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        Log.d(TAG, "onChanged: loading status has changed "+aBoolean);
                        Log.d(TAG, "onChanged: products loaded is "+productsLoaded);
                        if (aBoolean!=null&&aBoolean&&!productsLoaded) {
                            Log.d(TAG, "onChanged: progress bar is visible");
                            progressBar.setVisibility(View.VISIBLE);
                        }
                        else {
                            Log.d(TAG, "onChanged: progress bar is gone");
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    void observeBestSeller(){
        Log.d("BESTSELLERRR", "observeBestSeller: ");
        if (productsViewModel.getBestSellers().hasActiveObservers())
            return;
        productsViewModel.getBestSellers()
                .observe(this, new Observer<ArrayList<Product>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Product> products) {
                        Log.d("BESTSELLERRR","best seller loaded ");
                        productsAdapter.addProducts(products);
                        productsLoaded=true;
                        loadMorePB.setVisibility(View.GONE);
                    }
                });
    }

    void observebestSellersLoadingError(){
        productsViewModel
                .getBestSellersLoadingError()
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        loadMorePB.setVisibility(View.GONE);
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
                        if (aBoolean!=null&&aBoolean&&!productsLoaded)
                            progressBar.setVisibility(View.VISIBLE);
                        else
                            progressBar.setVisibility(View.GONE);
                    }
                });
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
        Log.d("HOMEEE", "onOptionsItemSelected: "+item.getItemId()+"  "+android.R.id.home);
        switch (item.getItemId()){
            case R.id.menu_cart:
                // go to cart activity
                startActivity(new Intent(ProductsActivity.this,CartActivity.class));
                break;
            case android.R.id.home:
                onBackPressed();
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

    void setupToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbarTilte.setText(!target.equals(CATEGORIES_TARGET)?target:categoryName);
        if (target.equals(SEARCH_TARGET)){
            String search=getIntent().getStringExtra(SEARCH_INFO);
            mToolbarTilte.setText(search);

        }
    }

    @Override
    public void onBottomSheetOptionClicked(String sortBy) {
        String orderBy = null;
        String order = "desc";
        Log.d("fromProductActivity","sort by "+sortBy);
        if(mSortByOption.isEmpty() || !mSortByOption.equals(sortBy)) {
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

            Log.d(TAG,"order_by = "+orderBy+" order = "+order);
            sortProducts(order,orderBy,1);
            observeProducts();
//            observeProductsLoading();
//            observeProductsLoadingError();

        }

    }


}