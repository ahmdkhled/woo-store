package com.corenet.yohady.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.corenet.yohady.R;
import com.corenet.yohady.adapter.ProductMediaAdapter;
import com.corenet.yohady.adapter.ReviewsAdapter;
import com.corenet.yohady.model.Product;
import com.corenet.yohady.model.Review;
import com.corenet.yohady.utils.PrefManager;
import com.corenet.yohady.utils.HtmlUtil;
import com.corenet.yohady.viewmodel.ProductDetailViewModel;
import com.corenet.yohady.viewmodel.ReviewsViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String PRODUCT_KEY="product_key";
    private static final int REVIEW_REQUEST_CODE =1005 ;
    public Product product;
    TextView name,price;
    ViewPager imagesPager;
    PageIndicatorView indicator;
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
    TextView mAddReview,mToolbarTitle;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        name=findViewById(R.id.product_name);
        price=findViewById(R.id.product_price);
        avgRating=findViewById(R.id.product_avgRating);
//        DetailsPager =findViewById(R.id.product_detail_viewPager);
        imagesPager =findViewById(R.id.product_images_viewPager);
        indicator=findViewById(R.id.product_images_indicator);
//        tabLayout=findViewById(R.id.product_detail_tabLayout);
        toolbar=findViewById(R.id.toolbar);
        navigationUp=findViewById(R.id.back_arrow);
        mAddToCartBtn=findViewById(R.id.add_to_cart_btn);
        mProductDescriptionTxt=findViewById(R.id.product_desc_content);
        mProductReviewsRecuRecyclerView=findViewById(R.id.reviews_recyclerview);
        mReviewsShimmer=findViewById(R.id.reviews_shimmer);
        mAddReview=findViewById(R.id.add_review_btn);
        mToolbarTitle=findViewById(R.id.toolbar_title);
        mToolbar=findViewById(R.id.toolbar);


        // setup toolbar
        setSupportActionBar(mToolbar);




        mViewModel = ViewModelProviders.of(this).get(ProductDetailViewModel.class);
        mViewModel.init();

        mReviewsViewModel = ViewModelProviders.of(this).get(ReviewsViewModel.class);

        setSupportActionBar(toolbar);
        product =getIntent().getParcelableExtra(PRODUCT_KEY);
        //Log.d("PRODUCCTTT", "price "+ HtmlUtil.getCurrency(""));
        // set product_name as toolbar_title
        mToolbarTitle.setText(product.getName());

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

        mAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailActivity.this,ReviewActivity.class);
                intent.putExtra("product_id",product.getId());
                startActivityForResult(intent,REVIEW_REQUEST_CODE);
            }
        });

    }

    void populateProductDetail(Product product){
        // product name
        name.setText(product.getName());

        // product price
        price.setText(HtmlUtil.toString(product.getPrice_html()));


        // product average rating
        if (product.getAverage_rating()!=null)
            avgRating.setRating(Float.parseFloat(product.getAverage_rating()));

        // product desciption
        if(product.getDescription() != null && !product.getDescription().equals("")) {
            mProductDescriptionTxt.setText(HtmlUtil.toString(product.getDescription()));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REVIEW_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Review newReview = data.getParcelableExtra("new_review");
            reviewsAdapter.addNewReview(newReview);
        }
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
