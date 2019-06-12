package com.example.woocommerce.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.woocommerce.R;
import com.example.woocommerce.adapter.ProductMediaAdapter;
import com.example.woocommerce.adapter.ReviewsAdapter;
import com.example.woocommerce.model.Product;
import com.example.woocommerce.model.Review;
import com.example.woocommerce.utils.PrefManager;
import com.example.woocommerce.utils.HtmlUtil;
import com.example.woocommerce.viewmodel.ProductDetailViewModel;
import com.example.woocommerce.viewmodel.ReviewsViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String PRODUCT_KEY="product_key";
    public Product product;
    TextView name,price,sale_price;
    ViewPager DetailsPager,imagesPager;
    PageIndicatorView indicator;
    TabLayout tabLayout;
    Toolbar toolbar;
    ImageView navigationUp;
    Button mAddToCartBtn;
    RatingBar avgRating;
    ProductDetailViewModel mViewModel;
    private TextView mCartBadgeTxt;
    private TextView mProductDescriptionTxt;
    private RecyclerView mProductReviewsRecuRecyclerView;
    ReviewsViewModel mReviewsViewModel;
    private ReviewsAdapter reviewsAdapter;
    ShimmerFrameLayout mReviewsShimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        name=findViewById(R.id.product_name);
        price=findViewById(R.id.product_price);
        sale_price=findViewById(R.id.price_after);
        avgRating=findViewById(R.id.product_avgRating);
//        DetailsPager =findViewById(R.id.product_detail_viewPager);
        imagesPager =findViewById(R.id.product_images_viewPager);
        indicator=findViewById(R.id.product_images_indicator);
//        tabLayout=findViewById(R.id.product_detail_tabLayout);
        toolbar=findViewById(R.id.toolbar);
        navigationUp=findViewById(R.id.navigation_up);
        mAddToCartBtn=findViewById(R.id.add_to_cart_btn);
        mProductDescriptionTxt=findViewById(R.id.product_desc_content);
        mProductReviewsRecuRecyclerView=findViewById(R.id.reviews_recyclerview);
        mReviewsShimmer=findViewById(R.id.reviews_shimmer);





        mViewModel = ViewModelProviders.of(this).get(ProductDetailViewModel.class);
        mViewModel.init();

        mReviewsViewModel = ViewModelProviders.of(this).get(ReviewsViewModel.class);

        setSupportActionBar(toolbar);
        product =getIntent().getParcelableExtra(PRODUCT_KEY);
        //Log.d("PRODUCCTTT", "price "+ HtmlUtil.getCurrency(""));
        mReviewsViewModel.getReviews("1",product.getId());
        observeReviews();
        observeReviewsLoading();
        observeReviewsLoadingError();
        populateProductDetail(product);


        // init reviews recycler view with
        mProductReviewsRecuRecyclerView
                .setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mProductReviewsRecuRecyclerView.setHasFixedSize(true);
        reviewsAdapter = new ReviewsAdapter(this,new ArrayList<Review>());
        mProductReviewsRecuRecyclerView.setAdapter(reviewsAdapter);

        mAddToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddToCartBtn.setClickable(false);
                mViewModel.addProductToCart(product.getId(), 1);
                if (!mViewModel.getIsItemSavedIntoCart().hasActiveObservers()) {
                    mViewModel.getIsItemSavedIntoCart().observe(ProductDetailActivity.this, new Observer<Integer>() {
                        @Override
                        public void onChanged(@Nullable Integer i) {
                            switch (i) {
                                case 1: // successfully added to cart
                                    Toast.makeText(ProductDetailActivity.this, "product is successfully added to your cart", Toast.LENGTH_SHORT).show();
                                    break;
                                case -1: // something went wrong while saving product to cart
                                    Toast.makeText(ProductDetailActivity.this, "somthing went wrong, please try again", Toast.LENGTH_SHORT).show();
                                    break;
                                case 0: // product is already added before to cart
                                    Toast.makeText(ProductDetailActivity.this, "product is already added to cart", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            mAddToCartBtn.setClickable(true);

                        }
                    });
                }
            }
        });




        imagesPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicator.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        navigationUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    void populateProductDetail(Product product){
        // product name
        name.setText(product.getName());

        // product price
        String currency=HtmlUtil.getCurrency(product.getPrice_html());
        price.setText(getString(R.string.product_price,product.getPrice(),currency));



        // if there is a sale on product then display sale_price
        if (product.getSale_price()!=null&& !TextUtils.isEmpty(product.getSale_price())){
            sale_price.setVisibility(View.VISIBLE);
            price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            price.setTextColor(Color.parseColor("#A7A5A5"));
            price.setTypeface(price.getTypeface(), Typeface.NORMAL);
            sale_price.setText(getString(R.string.product_price,product.getSale_price(),currency));
        }

        // product average rating
        if (product.getAverage_rating()!=null)
            avgRating.setRating(Float.parseFloat(product.getAverage_rating()));

        // product desciption
        if(product.getDescription() != null && !product.getDescription().equals("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mProductDescriptionTxt.setText(Html.fromHtml(product.getDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                mProductDescriptionTxt.setText(Html.fromHtml(product.getDescription()));
            }
        }
        else{
            Log.d("single_item","there is no desc");
            mProductDescriptionTxt.setText("There is no description yet");
        }



//        DetailsAdapter detailsAdapter=new DetailsAdapter(getSupportFragmentManager());
//        DetailsPager.setAdapter(detailsAdapter);
//        tabLayout.setupWithViewPager(DetailsPager);

        // product images
        if (product.getImages()!=null&&!product.getImages().isEmpty()){
            ProductMediaAdapter productMediaAdapter=new ProductMediaAdapter(this,product.getImages());
            imagesPager.setAdapter(productMediaAdapter);
            indicator.setAnimationType(AnimationType.WORM);
            indicator.setCount(product.getImages().size());
        }


    }

    void observeReviews(){
        mReviewsViewModel.getReviews()
                .observe(this, new Observer<ArrayList<Review>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Review> reviews) {
                        Log.d("REVIEWW", "onChanged: "+reviews.size());
                        if(reviews.size() == 0){
                            mProductReviewsRecuRecyclerView.setVisibility(View.GONE);
                        }
                        reviewsAdapter.swapAdapter(reviews);

                    }
                });
    }

    void observeReviewsLoading(){
        mReviewsViewModel.getIsReviewsLoading()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean!=null&&!aBoolean) {
                            mReviewsShimmer.stopShimmer();
                            mReviewsShimmer.setVisibility(View.GONE);
                        }

                    }
                });
    }

    void observeReviewsLoadingError(){
        mReviewsViewModel.getReviewsLoadingError()
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(ProductDetailActivity.this, s, Toast.LENGTH_SHORT).show();
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
        switch (item.getItemId()){
            case R.id.menu_cart:
                // go to cart activity
                startActivity(new Intent(ProductDetailActivity.this,CartActivity.class));
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
    protected void onResume() {
        super.onResume();
        mReviewsShimmer.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mReviewsShimmer.stopShimmer();
    }
}
