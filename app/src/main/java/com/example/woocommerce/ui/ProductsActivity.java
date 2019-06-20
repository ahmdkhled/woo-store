package com.example.woocommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woocommerce.R;
import com.example.woocommerce.adapter.ProductAdapter;
import com.example.woocommerce.model.Product;
import com.example.woocommerce.utils.BottomSheetListener;
import com.example.woocommerce.utils.EndlessRecyclerViewScrollListener;
import com.example.woocommerce.utils.PrefManager;
import com.example.woocommerce.viewmodel.ProductsViewModel;

import java.util.ArrayList;


public class ProductsActivity extends AppCompatActivity implements BottomSheetListener {

    public static final String SEARCH = "Results for ";
    public static final String SEARCH_QUERY = "search_query";
    private static final int SEARCH_VOICE_REQUEST_CODE = 1009;
    ProductsViewModel productsViewModel;
    RecyclerView recentlyAddedRecycler;
    Button sortBy;
    ProgressBar progressBar;
    ProgressBar loadMorePB;
    Toolbar mToolbar;
    TextView mToolbarTilte;
    String target = "";
    public static final String TARGET_KEY = "target_key";
    public static final String RA_TARGET = "Recently Added";
    public static final String CATEGORIES_TARGET = "Categories";
    public static final String DEALS_TARGET = "Deals";
    public static final String BESTSELLERS_TARGET = "Best Seller";
    public static final String CATEGORY_INFO = "category_info";
    private String mSortByOption;
    GridLayoutManager layoutManager;
    ProductAdapter productsAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private TextView mCartBadgeTxt;
    private String categoryId;
    private String categoryName;
    private Intent intent;
    private String searchQuery;
    private EditText mSearchEditTxt;
    private ImageView mVoiceSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        recentlyAddedRecycler = findViewById(R.id.recentlyRecycler);
        progressBar = findViewById(R.id.products_PB);
        loadMorePB = findViewById(R.id.loadMorePB);
        sortBy = findViewById(R.id.sortBy_button);
        mToolbar = findViewById(R.id.toolbar);
        mToolbarTilte = findViewById(R.id.toolbar_title);
        mSearchEditTxt = findViewById(R.id.search_edit_txt);
        mVoiceSearch = findViewById(R.id.search_voice);

        productsViewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);

        intent = getIntent();
        if (intent != null)
        {
            target = intent.getStringExtra(TARGET_KEY);
            // setup toolbar
            setSupportActionBar(mToolbar);
            mToolbarTilte.setText(target);
        }

        if (target.equals(CATEGORIES_TARGET))

        {
            String[] categoryInfo = getIntent().getStringArrayExtra(CATEGORY_INFO);
            categoryId = categoryInfo[0];
            categoryName = categoryInfo[1];
            mToolbarTilte.setText(categoryName);
        }

        if (target.equals(SEARCH))
        {
            searchQuery = getIntent().getStringExtra(SEARCH_QUERY);
            if (searchQuery != null) {
                mToolbarTilte.setText((new StringBuilder().append(SEARCH).append(searchQuery)).toString());
                mSearchEditTxt.setText(searchQuery);
            } else
                mToolbarTilte.setText((new StringBuilder().append(SEARCH).append(mSearchEditTxt.getText()
                        .toString())).toString());
        }

        layoutManager = new GridLayoutManager(this, 2);
        productsAdapter = new ProductAdapter(this, null, false);
        recentlyAddedRecycler.setAdapter(productsAdapter);
        recentlyAddedRecycler.setLayoutManager(layoutManager);
        requestProducts(1);
        if(!target.equals(BESTSELLERS_TARGET)) {
            observeProducts();
            observeProductsLoading();
            observeProductsLoadingError();
        }else {
            observeBestSeller();
            observebestSellerLoading();
            observebestSellersLoadingError();
        }

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page++;
                requestProducts(page);
//                loadMorePB.setVisibility(View.VISIBLE);
                Log.d("PAGGIINGNG", "onLoadMore: ");
            }
        };


        recentlyAddedRecycler.addOnScrollListener(scrollListener);

        // sort by bottom sheet
        sortBy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                SortByBottomSheet mSortBottomSheet = new SortByBottomSheet();
                mSortBottomSheet.show(getSupportFragmentManager(), mSortBottomSheet.getTag());
            }
        });

        // search by text
        mSearchEditTxt.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String query = mSearchEditTxt.getText().toString();
                    if (!query.isEmpty()) {
                        mToolbarTilte.setText((new StringBuilder().append(SEARCH).append(query)).toString());
                        mSearchEditTxt.setText(query);
                        doSearch(query);
                        observeProducts();
                        observeProductsLoadingError();
                        observeProductsLoading();
                    } else
                        Toast.makeText(ProductsActivity.this, "please enter something to search for ", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                }
            });


            // if user wanna search by voice
            mVoiceSearch.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice searching...");
                    startActivityForResult(intent, SEARCH_VOICE_REQUEST_CODE);
                }
            });

        }

        void requestProducts(int page) {
                if (target.equals(RA_TARGET))
                    productsViewModel.getProducts(String.valueOf(page), null, null, null, null,
                            null, null, null, null, null, null, null,
                            null, null, null, null, null, null);

                else if (target.equals(DEALS_TARGET))
                    productsViewModel.getProducts(String.valueOf(page), null, null, null, null,
                            null, null, null, "true", null, null, null,
                            null, null, null, null, null, null);

                else if (target.equals(BESTSELLERS_TARGET))
                    productsViewModel.getBestSellers("month", null, null, null, null,
                            null, null, "date", null, null,
                            null, null, null, null, null,
                            null, null, null, null, null);

                else if (target.equals(CATEGORIES_TARGET))
                    productsViewModel.getProducts(String.valueOf(page), null, null, categoryId, null,
                            null, null, null, null, null, null, null,
                            null, null, null, null, null, null);
                else if (target.equals(SEARCH)) doSearch(searchQuery);


        }


        private void doSearch(String searchQuery) {
            productsViewModel.getProducts(null, null, searchQuery, null, null,
                    null, null, null, null, null, null, null,
                    null, null, null, null, null, null);

        }

        void observeProducts() {
            Log.d("PAGGIINGNG", "observe products  ");
            if (!productsViewModel.getProducts().hasActiveObservers()) {
                productsViewModel.getProducts()
                        .observe(this, new Observer<ArrayList<Product>>() {
                            @Override
                            public void onChanged(@Nullable ArrayList<Product> products) {
                                if (products != null) {
                                    productsAdapter.swapDate(products);
                                    loadMorePB.setVisibility(View.GONE);
                                    if (products.size() == 0) {
                                        Toast.makeText(ProductsActivity.this, "No results found", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        }

        void observeProductsLoadingError() {
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

        void observeProductsLoading() {
            productsViewModel
                    .getIsProductsLoading()
                    .observe(this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(@Nullable Boolean aBoolean) {
                            if (aBoolean != null && aBoolean)
                                progressBar.setVisibility(View.VISIBLE);
                            else progressBar.setVisibility(View.GONE);

                        }
                    });
        }

        void observeBestSeller() {
            productsViewModel.getBestSellers()
                    .observe(this, new Observer<ArrayList<Product>>() {
                        @Override
                        public void onChanged(@Nullable ArrayList<Product> products) {
                            productsAdapter.addProducts(products);

                        }
                    });
        }

        void observebestSellersLoadingError() {
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

        void observebestSellerLoading() {
            productsViewModel
                    .getIsBestSellersLoading()
                    .observe(this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(@Nullable Boolean aBoolean) {
                            if (aBoolean != null && aBoolean)
                                progressBar.setVisibility(View.VISIBLE);
                            else
                                progressBar.setVisibility(View.GONE);
                        }
                    });
        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_activity_menu, menu);

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
            switch (item.getItemId()) {
                case R.id.menu_cart:
                    // go to cart activity
                    startActivity(new Intent(ProductsActivity.this, CartActivity.class));
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
                    if (cartSize != null) {
                        if (mCartBadgeTxt != null) {
                            if (cartSize == 0) {
                                if (mCartBadgeTxt.getVisibility() != View.GONE) {
                                    mCartBadgeTxt.setVisibility(View.GONE);
                                }
                            } else {
                                mCartBadgeTxt.setText(String.valueOf(cartSize));
                                if (mCartBadgeTxt.getVisibility() != View.VISIBLE) {
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
            Log.d("fromProductActivity", "sort by " + sortBy);
            if (mSortByOption == null || !mSortByOption.equals(sortBy)) {
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

                Log.d("fromProductActivity", "order_by = " + orderBy + " order = " + order);
                productsViewModel.getProducts(null, null, searchQuery, null, orderBy,
                        order, null, null, null, null, null, null,
                        null, null, null, null, null, null);


            }

        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == SEARCH_VOICE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
                ArrayList<String> wordList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (!wordList.isEmpty()) {
                    String query = wordList.get(0);
                    Log.d("search_feat", "what you said is " + query);
                    mToolbarTilte.setText((new StringBuilder().append(SEARCH).append(query)).toString());
                    mSearchEditTxt.setText(query);
                    doSearch(query);
                    observeProducts();
                    observeProductsLoadingError();
                    observeProductsLoading();
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

}
