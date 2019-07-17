package com.example.woocommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woocommerce.R;
import com.example.woocommerce.model.Billing;
import com.example.woocommerce.model.CartItem;
import com.example.woocommerce.model.Coupon;
import com.example.woocommerce.model.Order;
import com.example.woocommerce.model.OrderPayload;
import com.example.woocommerce.model.Product;
import com.example.woocommerce.model.Shipping;
import com.example.woocommerce.model.ShippingLine;
import com.example.woocommerce.network.RetrofitClient;
import com.example.woocommerce.utils.PrefManager;
import com.example.woocommerce.utils.ProductUtils;
import com.example.woocommerce.viewmodel.PaymentViewModel;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentFrag extends Fragment {

    public static final String ADDRESS_KEY="address_key";
    public static final String BILLING_KEY="billing_key";
    Button placeOrder;
    TextView mTotalTxt,mSubTotalTxt,mShippingCostTxt;
    EditText couponInput;
    Button applyCoupon;
    ProgressBar progressBar;
    Shipping shipping;
    Billing billing;
    PaymentViewModel paymentViewModel;
    ArrayList<CartItem> cartItems;
    private int shippingCost=-1;
    private double subTotal;
    private int total;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.payment_frag,container,false);
        placeOrder=v.findViewById(R.id.placeOrder);
        progressBar=v.findViewById(R.id.order_PB);
        mTotalTxt=v.findViewById(R.id.total);
        mSubTotalTxt=v.findViewById(R.id.sub_total);
        couponInput=v.findViewById(R.id.coupon_input);
        applyCoupon=v.findViewById(R.id.apply_coupon_btn);
        mShippingCostTxt=v.findViewById(R.id.shpiingCost);

        Bundle b=getArguments();
        if (b != null&&b.containsKey(ADDRESS_KEY)) {
            shipping=b.getParcelable(ADDRESS_KEY);
            billing=b.getParcelable(BILLING_KEY);
        }
        paymentViewModel= ViewModelProviders.of(getActivity())
                .get(PaymentViewModel.class);

        PrefManager prefManager=PrefManager.getInstance(getContext());
        cartItems =prefManager.getCartItems();

        paymentViewModel.getCarItems(null,String.valueOf(cartItems.size()),null,null,
                null,null,null,null,null,null,
        null ,null ,null , ProductUtils.getCartIdsAsString(cartItems)
                ,null,null,null,null);

        observeCartItems();
        observeCartItemsLoading();
        observeCartItemsLoadingError();
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentViewModel.placeOrder(getOrderPayload());
                observePlacingOrder();
                observeOrderPlacingError();
                observeIsOrderPlacing();
                placeOrder.setEnabled(false);

            }
        });

        shippingCost=15;
        applyCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String couponCode=couponInput.getText().toString();
                if (TextUtils.isEmpty(couponCode))
                    return;
                Log.d("COUPOON", "apply clicked: ");
                paymentViewModel.getCoupon(null,null,
                        null,null,null,
                        null,null,null,
                        null,null,null,couponCode);
                observeCoupon();
                observeIsCouponLoading();
                observeCouponLoadingError();

            }
        });

        return v;
    }

    private OrderPayload getOrderPayload(){

        ArrayList<ShippingLine> shippingLine=new ArrayList<>();
        return new OrderPayload(
                "cod",
                "cash on delivery",
                false,
                billing,
                shipping,
                cartItems,
                shippingLine

        );
    }

    void observePlacingOrder(){
        paymentViewModel.placeOrder()
                .observe(getActivity(), new Observer<Order>() {
                    @Override
                    public void onChanged(@Nullable Order order) {
                        Log.d("ORRDDDER", "done : ");
                        Intent intent=new Intent(getContext(),OrderSummaryActivity.class);
                        startActivity(intent);
                    }
                });
    }

    void observeOrderPlacingError(){
        paymentViewModel.getPlacingOrderError()
                .observe(getActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Log.d("ORRDDDER", "error: "+s);
                    }
                });
    }

    void observeIsOrderPlacing(){
        paymentViewModel.getOrderIsPlacing()
                .observe(getActivity(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean!=null&&aBoolean){
                            progressBar.setVisibility(View.VISIBLE);
                            placeOrder.setText("");
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            placeOrder.setText("place order");
                            placeOrder.setEnabled(true);
                        }
                    }
                });
    }

    void observeCartItems(){
        paymentViewModel.getCarItems()
                .observe(getActivity(), new Observer<ArrayList<Product>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Product> products) {
                        Log.d("ORRDDDER", "products loaded : ");
                        subTotal=ProductUtils.calculateTotalPrice(cartItems,products);
                        total = (int)subTotal+shippingCost;
                        mSubTotalTxt.setText(getString(R.string.product_price,String.valueOf(subTotal)));
                        mTotalTxt.setText(getString(R.string.product_price,String.valueOf(total)));

                    }
                });
    }

    void observeCartItemsLoadingError(){
        paymentViewModel.getCartItemsLoadingError()
                .observe(getActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Log.d("ORRDDDER", "error: "+s);
                    }
                });
    }

    void observeCartItemsLoading(){
        paymentViewModel.getIsCartItemsLoading()
                .observe(getActivity(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean!=null&&aBoolean){

                        }
                        else{

                        }
                    }
                });
    }

    void observeCoupon(){
        paymentViewModel
                .getCoupon()
                .observe(getActivity(), new Observer<ArrayList<Coupon>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Coupon> coupons) {
                        handleCoupon(coupons);
                        Log.d("COUPOON", "coupon query: "+coupons.size());

                    }
                });
    }

    void observeIsCouponLoading(){
        paymentViewModel
                .getIsCouponLoading()
                .observe(getActivity(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                     //if (aBoolean!=null&&aBoolean)
                         
                    }
                });
    }
    
    void observeCouponLoadingError(){
        paymentViewModel
                .getCouponLoadingError()
                .observe(getActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void handleCoupon(ArrayList<Coupon> coupons){
        double subTotal=0.0;
        int freeShipping=0;

        if (coupons==null||coupons.isEmpty()){
            Toast.makeText(getContext(), R.string.invalid_coupon, Toast.LENGTH_SHORT).show();
            return;
        }
        Coupon coupon=coupons.get(0);

        if (coupon.getUsageLimit()==coupon.getUsageCount()){
            Toast.makeText(getContext(), R.string.deprecated_coupon, Toast.LENGTH_SHORT).show();
        }

        Log.d("COUPOON", coupon.getMinimumAmount());

        if (coupon.getFreeShipping()){
            shippingCost=0;
            mShippingCostTxt.setText(String.valueOf(shippingCost));
        }
        if (coupon.getDiscountType().equals("percent")){
            subTotal=this.subTotal*(1- (Double.valueOf(coupon.getAmount())/100));

        }
        if (coupon.getDiscountType().equals("fixed_cart")){
            subTotal=this.subTotal-Double.valueOf(coupon.getAmount());

        }

        mSubTotalTxt.setText(getString(R.string.product_price,String.valueOf(subTotal)));
        mTotalTxt.setText(getString(R.string.product_price,String.valueOf(subTotal+shippingCost)));

    }

}
