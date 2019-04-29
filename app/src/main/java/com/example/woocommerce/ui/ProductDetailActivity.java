package com.example.woocommerce.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woocommerce.R;
import com.example.woocommerce.adapter.DetailsAdapter;
import com.example.woocommerce.adapter.ProductMediaAdapter;
import com.example.woocommerce.model.Product;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String PRODUCT_KEY="product_key";
    public Product product;
    TextView name,price,sale_price;
    ViewPager DetailsPager,imagesPager;
    PageIndicatorView indicator;
    TabLayout tabLayout;
    Toolbar toolbar;
    ImageView navigationUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        name=findViewById(R.id.product_name);
        price=findViewById(R.id.product_price);
        sale_price=findViewById(R.id.price_after);
        DetailsPager =findViewById(R.id.product_detail_viewPager);
        imagesPager =findViewById(R.id.product_images_viewPager);
        indicator=findViewById(R.id.product_images_indicator);
        tabLayout=findViewById(R.id.product_detail_tabLayout);
        toolbar=findViewById(R.id.toolbar);
        navigationUp=findViewById(R.id.navigation_up);

        setSupportActionBar(toolbar);
        product =getIntent().getParcelableExtra(PRODUCT_KEY);
        Log.d("PRODUCCTTT", "product : "+product.getName());
        populateProductDetail(product);

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
        name.setText(product.getName());
        price.setText(product.getPrice());

        if (product.getSale_price()!=null&& !TextUtils.isEmpty(product.getSale_price())){
            sale_price.setVisibility(View.VISIBLE);
            price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            price.setTextColor(Color.parseColor("#A7A5A5"));
            price.setTypeface(price.getTypeface(), Typeface.NORMAL);
            sale_price.setText(product.getSale_price());
        }

        DetailsAdapter detailsAdapter=new DetailsAdapter(getSupportFragmentManager());
        DetailsPager.setAdapter(detailsAdapter);
        tabLayout.setupWithViewPager(DetailsPager);

        if (product.getImages()!=null&&!product.getImages().isEmpty()){
            ProductMediaAdapter productMediaAdapter=new ProductMediaAdapter(this,product.getImages());
            imagesPager.setAdapter(productMediaAdapter);
            indicator.setAnimationType(AnimationType.WORM);
            indicator.setCount(product.getImages().size());
        }


    }
}