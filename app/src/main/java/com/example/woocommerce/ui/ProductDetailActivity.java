package com.example.woocommerce.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        name=findViewById(R.id.product_name);
        price=findViewById(R.id.product_price);
        DetailsPager =findViewById(R.id.product_detail_viewPager);
        imagesPager =findViewById(R.id.product_images_viewPager);
        indicator=findViewById(R.id.product_images_indicator);
        tabLayout=findViewById(R.id.product_detail_tabLayout);

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
    }

    void populateProductDetail(Product product){
        name.setText(product.getName());
        price.setText(product.getPrice());
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