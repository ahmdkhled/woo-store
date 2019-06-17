package com.example.woocommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.woocommerce.R;
import com.example.woocommerce.model.Billing;
import com.example.woocommerce.model.CartItem;
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
    TextView total;
    ProgressBar progressBar;
    Shipping shipping;
    Billing billing;
    PaymentViewModel paymentViewModel;
    ArrayList<CartItem> cartItems;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.payment_frag,container,false);
        placeOrder=v.findViewById(R.id.placeOrder);
        progressBar=v.findViewById(R.id.order_PB);
        total=v.findViewById(R.id.total);
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
                Log.d("ORRDDDER", "onClick: ");
                paymentViewModel.placeOrder(getOrderPayload());
                observePlacingOrder();
                observeOrderPlacingError();
                observeIsOrderPlacing();
                placeOrder.setEnabled(false);

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
                        int t=ProductUtils.calculateTotalPrice(cartItems,products);
                        total.setText(String.valueOf(t));
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

}
