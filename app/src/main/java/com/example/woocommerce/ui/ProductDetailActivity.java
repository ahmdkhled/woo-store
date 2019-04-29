package com.example.woocommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woocommerce.R;
import com.example.woocommerce.adapter.DetailsAdapter;
import com.example.woocommerce.model.Product;
import com.example.woocommerce.viewmodel.ProductDetailViewModel;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String PRODUCT_KEY="product_key";
    Product product;
    TextView name,price,sale_price;
    ViewPager viewPager;
    TabLayout tabLayout;
    Button mAddToCartBtn;
    ProductDetailViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        name=findViewById(R.id.product_name);
        price=findViewById(R.id.product_price);
        viewPager=findViewById(R.id.product_detail_viewPager);
        tabLayout=findViewById(R.id.product_detail_tabLayout);
        mAddToCartBtn=findViewById(R.id.addToCart);


        mViewModel = ViewModelProviders.of(this).get(ProductDetailViewModel.class);
        mViewModel.init();

        product =getIntent().getParcelableExtra(PRODUCT_KEY);
        Log.d("PRODUCCTTT", "product : "+product.getName());
        populateProductDetail(product);

        mAddToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddToCartBtn.setClickable(false);
                mViewModel.addProductToCart(product.getId(),1);
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
                            startActivity(new Intent(ProductDetailActivity.this, CartActivity.class));
                        }
                    });

                }
            }
        });
    }

    void populateProductDetail(Product product){
        name.setText(product.getName());
        price.setText(product.getPrice());
        DetailsAdapter detailsAdapter=new DetailsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(detailsAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}