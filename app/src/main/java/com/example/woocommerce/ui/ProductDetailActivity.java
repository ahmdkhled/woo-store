package com.example.woocommerce.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.woocommerce.R;
import com.example.woocommerce.adapter.DetailsAdapter;
import com.example.woocommerce.model.Product;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String PRODUCT_KEY="product_key";
    Product product;
    TextView name,price,sale_price;
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        name=findViewById(R.id.product_name);
        price=findViewById(R.id.product_price);
        viewPager=findViewById(R.id.product_detail_viewPager);
        tabLayout=findViewById(R.id.product_detail_tabLayout);

        product =getIntent().getParcelableExtra(PRODUCT_KEY);
        Log.d("PRODUCCTTT", "product : "+product.getName());
        populateProductDetail(product);
    }

    void populateProductDetail(Product product){
        name.setText(product.getName());
        price.setText(product.getPrice());
        DetailsAdapter detailsAdapter=new DetailsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(detailsAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}