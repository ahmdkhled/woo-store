package com.corenet.yohady.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.corenet.yohady.adapter.CartAdapter;
import com.corenet.yohady.model.Product;
import com.corenet.yohady.utils.CartListener;
import com.corenet.yohady.R;
import com.corenet.yohady.viewmodel.CartViewModel;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity implements CartListener {

    @BindView(R.id.back_arrow)
    ImageView mBackArrowBtn;
    @BindView(R.id.proceed_to_checkout_btn)
    Button mDoneBtn;
    @BindView(R.id.sub_total_txt)
    TextView mCartSubTotalTxt;
    @BindView(R.id.sub_total_value)
    TextView mCartSubTotalValue;
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
//        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        ButterKnife.bind(this);


        // init recycler view
        mCartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                    if(products != null && products.size() > 0) {
                        mEmptyCartView.setVisibility(View.GONE);
                        mDoneBtn.setVisibility(View.VISIBLE);
                        mCartSubTotalValue.setVisibility(View.VISIBLE);
                        mCartSubTotalTxt.setVisibility(View.VISIBLE);
                        List<Integer> cartItemsQuantities = getCartItemsQuantities();
                        mCartAdapter.notifyAdapter(products, cartItemsQuantities);
                        calculateTotalPrice(products, cartItemsQuantities);
                    }
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
                        mCartSubTotalValue.setVisibility(View.GONE);
                        mCartSubTotalTxt.setVisibility(View.GONE);
                        Toast.makeText(CartActivity.this, "Empty Cart", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        mBackArrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),CheckoutActivity.class);
                startActivity(intent);
            }
        });

        // if there are no items in cart
        mStartShoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this,MainActivity.class));
            }
        });


    }

    private List<Integer> getCartItemsQuantities() {
        return mViewModel.getCartItemsQuantities();
    }


    private void calculateTotalPrice(ArrayList<Product> products, List<Integer> quantities) {
        if(products != null && products.size() > 0) {
            mTotalPrice = 0;
            for(int i = 0; i < products.size(); i++){
                mTotalPrice += (Integer.valueOf(products.get(i).getOn_sale()?
                        products.get(i).getSale_price():products.get(i).getRegular_price()))
                        *quantities.get(i);
            }
            mCartSubTotalValue.setText(mTotalPrice+" EGP");
        }
    }

    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void increaseItemQuantity(int position, int newQuqntity, String price) {
        mTotalPrice += Integer.valueOf(price);
        mCartSubTotalValue.setText(String.valueOf(mTotalPrice)+" EGP");
        mViewModel.updateItemQuantity(position,newQuqntity);
    }

    @Override
    public void decreaseItemQuantity(int position, int newQuqntity, String price) {
        mTotalPrice -= Integer.valueOf(price);
        mCartSubTotalValue.setText(getString(R.string.product_price,String.valueOf(mTotalPrice)));
        mViewModel.updateItemQuantity(position,newQuqntity);
    }

    @Override
    public void removeItem(final Product deletedProduct, final int quantity, final int position, final int cartSize) {
        Log.d("cart_activity","removed position "+position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You are about delete this item from your cart.\nAre you sure");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mCartAdapter.removeItem(position);
                mTotalPrice -= quantity * Integer.valueOf(deletedProduct.getOn_sale()?deletedProduct.getSale_price():deletedProduct.getRegular_price());
                mCartSubTotalValue.setText(String.valueOf(getString(R.string.product_price,String.valueOf(mTotalPrice))));
                showProgressBar();
                mViewModel.removeCartItem(position);
                mViewModel.getIsItemsDeleted().observe(CartActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        hideProgressBar();
                        if(aBoolean)
                            Toast.makeText(CartActivity.this, R.string.success_deleted, Toast.LENGTH_SHORT).show();
                        else Toast.makeText(CartActivity.this, R.string.error_message, Toast.LENGTH_SHORT).show();
                        if(cartSize == 1) mViewModel.getIsCartEmpty().setValue(true);
                    }
                });
            }
        });

        builder.setNegativeButton("Cancel",null);
        builder.show();

    }
}
