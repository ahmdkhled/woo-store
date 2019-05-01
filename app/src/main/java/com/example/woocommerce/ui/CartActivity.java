package com.example.woocommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woocommerce.adapter.CartAdapter;
import com.example.woocommerce.model.CartItem;
import com.example.woocommerce.model.Product;
import com.example.woocommerce.utils.CartListener;
import com.example.woocommerce.utils.PrefManager;
import com.example.woocommerce.R;
import com.example.woocommerce.viewmodel.CartViewModel;
import com.example.woocommerce.viewmodel.ProductDetailViewModel;


import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity implements CartListener {

    @BindView(R.id.back_arrow)
    ImageView mBackArrowBtn;
    @BindView(R.id.done)
    ImageView mDoneBtn;
    @BindView(R.id.cart_title_txt)
    TextView mCartTitleTxt;
    @BindView(R.id.cart_total_txt)
    TextView mCartTotalTxt;
    @BindView(R.id.cart_total_value)
    TextView mCartTotalValueTxt;
    @BindView(R.id.cart_recycler_view)
    RecyclerView mCartRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.empty_cart)
    ViewGroup mEmptyCartView;
    @BindView(R.id.empty_cart_btn)
    Button mStartShoppingBtn;

    CartViewModel mViewModel;
    CartAdapter mCartAdapter;
    private float mTotalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // hide status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ButterKnife.bind(this);


        // init recycler view
        mCartRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mCartAdapter = new CartAdapter(this,null,this);
        mCartRecyclerView.setHasFixedSize(true);
        mCartRecyclerView.setAdapter(mCartAdapter);


        mViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        mViewModel.getCartItems();

        // observe getting cart items
        if(!mViewModel.getmCartItems().hasActiveObservers()) {
            mViewModel.getmCartItems().observe(this, new Observer<ArrayList<Product>>() {
                @Override
                public void onChanged(@Nullable ArrayList<Product> products) {
                    mEmptyCartView.setVisibility(View.GONE);
                    mDoneBtn.setVisibility(View.VISIBLE);
                    mCartAdapter.notifyAdapter(products);
                    calculateTotalPrice(products);
                }
            });
        }

        // observe if loading cart items is finished or not
        if(!mViewModel.getIsProductLoading().hasActiveObservers()) {
            mViewModel.getIsProductLoading().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    if (aBoolean) showProgressBar();
                    else hideProgressBar();
                }
            });
        }

        // observe if there is error while loading cart items
        if(!mViewModel.getProductLoadingError().hasActiveObservers()) {
            mViewModel.getProductLoadingError().observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    if(s != null) Toast.makeText(CartActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            });
        }

        // observe is cart is empty
        if(!mViewModel.getIsCartEmpty().hasActiveObservers()) {
            mViewModel.getIsCartEmpty().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    if (aBoolean) {
                        // empty cart
                        mEmptyCartView.setVisibility(View.VISIBLE);
                        mDoneBtn.setVisibility(View.GONE);
                        Toast.makeText(CartActivity.this, "Empty Cart", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


        mStartShoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this,MainActivity.class));
            }
        });

    }

    private void calculateTotalPrice(ArrayList<Product> products) {
        if(products != null && products.size() > 0) {
            for (Product product : products) {
                mTotalPrice += Integer.valueOf(product.getOn_sale()?product.getSale_price():product.getRegular_price());
            }
            mCartTotalValueTxt.setText(mTotalPrice+" EGP");
        }
    }

    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void increaseItemQuantity(String price) {
        mTotalPrice += Integer.valueOf(price);
        mCartTotalValueTxt.setText(String.valueOf(mTotalPrice)+" EGP");
    }

    @Override
    public void decreaseItemQuantity(String price) {
        mTotalPrice -= Integer.valueOf(price);
        mCartTotalValueTxt.setText(String.valueOf(mTotalPrice)+" EGP");
    }
}
