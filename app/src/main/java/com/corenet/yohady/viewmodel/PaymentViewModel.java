package com.corenet.yohady.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.corenet.yohady.model.Coupon;
import com.corenet.yohady.model.Order;
import com.corenet.yohady.model.OrderPayload;
import com.corenet.yohady.model.Product;
import com.corenet.yohady.repository.OrdersRepo;
import com.corenet.yohady.repository.PaymentRepo;
import com.corenet.yohady.repository.ProductsRepo;

import java.util.ArrayList;

public class PaymentViewModel extends ViewModel {

    private MutableLiveData<Order> placeOrder;
    private MutableLiveData<ArrayList<Product>> products;
    private MutableLiveData<ArrayList<Coupon>> coupon;


    public void placeOrder(OrderPayload orderPayload){
        placeOrder=OrdersRepo.getInstance()
                .placeOrder(orderPayload);
    }

    public MutableLiveData<Order> placeOrder(){
        return placeOrder;
    }

    public MutableLiveData<Boolean> getOrderIsPlacing() {
        return OrdersRepo.getInstance().getOrderIsPlacing();
    }

    public MutableLiveData<String> getPlacingOrderError() {
        return OrdersRepo.getInstance().getPlacingOrderError();
    }

    public void getCarItems( String page, String per_page,
                             String search, String category,
                             String order_by, String order,
                             String min_price, String max_price,
                             String on_sale, String featured,
                             String stock_status, String status,
                             String context, String include,
                             String sku, String slug,
                             final String tag, String shipping_class){



        products = ProductsRepo.getInstance()
                .getProducts(page,per_page,search,category,order_by,order,
                        min_price,max_price,on_sale,featured,stock_status,
                        status,context,include,sku,slug,tag,shipping_class);

    }

    public MutableLiveData<ArrayList<Product>> getCarItems() {
        return products;
    }

    public MutableLiveData<Boolean> getIsCartItemsLoading() {
        return ProductsRepo.getInstance().getIsProductsLoading();
    }

    public MutableLiveData<String> getCartItemsLoadingError() {
        return ProductsRepo.getInstance().getProductsLoadingError();
    }

    /* get coupon details*/

    public MutableLiveData<ArrayList<Coupon>> getCoupon(String context, String page,
                                             String per_page, String search,
                                             String after, String before,
                                             String exclude, String include,
                                             String offset, String order,
                                             String orderby, String code){
        coupon= PaymentRepo.getInstance()
                .getCoupon(context,  page,
                        per_page,  search,
                        after,  before,
                        exclude,  include,
                        offset,  order,
                        orderby,  code);
        return coupon;

    }

    public MutableLiveData<ArrayList<Coupon>> getCoupon(){
        return coupon;

    }

    public MutableLiveData<Boolean> getIsCouponLoading() {
        return PaymentRepo.getInstance().getIsCouponLoading();
    }

    public MutableLiveData<String> getCouponLoadingError() {
        return PaymentRepo.getInstance().getCouponLoadingError();
    }

}
