package com.example.woocommerce.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.woocommerce.model.Product;
import com.example.woocommerce.repository.ProductsRepo;
import com.example.woocommerce.ui.ProductDetailActivity;

import java.util.ArrayList;

public class ProductsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Product>> Products;
    private MutableLiveData<ArrayList<Product>> bestSellers;

    public void getProducts( String page, String per_page,
                             String search, String category,
                             String order_by, String order,
                             String min_price, String max_price,
                             String on_sale, String featured,
                             String stock_status, String status,
                             String context, String include,
                             String sku, String slug,

                             final String tag, String shipping_class){



        Log.d("fromProductRepo","getProducts vm"+category+order_by+search);
            Products = ProductsRepo.getInstance()
                    .getProducts(page,per_page,search,category,order_by,order,
                            min_price,max_price,on_sale,featured,stock_status,
                            status,context,include,sku,slug,tag,shipping_class);

    }

    public void getBestSellers(String period, String date_min, String date_max,
                               final String page, final String per_page,
                               final String search, final String category,
                               final String order_by, final String order,
                               final String min_price, final String max_price,
                               final String on_sale, final String featured,
                               final String stock_status, final String status,
                               final String context,
                               final String sku, final String slug,
                               final String tag, final String shipping_class
    ){
        if (bestSellers==null) {
            bestSellers=ProductsRepo.getInstance()
                    .getBestSellers(period, date_min, date_max,
                            page, per_page, search, category, order_by,
                            order, min_price, max_price, on_sale, featured,
                            stock_status, status, context, sku, slug, tag, shipping_class);
        }
    }

    public MutableLiveData<ArrayList<Product>> getProducts() {
        if(Products == null) Products = new MutableLiveData<>();
        return Products;
    }


    public MutableLiveData<Boolean> getIsProductsLoading() {
        return ProductsRepo.getInstance().getIsProductsLoading();
    }

    public MutableLiveData<String> getProductLoadingError() {
        return ProductsRepo.getInstance().getProductsLoadingError();
    }

    public MutableLiveData<ArrayList<Product>> getBestSellers() {
        return bestSellers;
    }

    public MutableLiveData<Boolean> getIsBestSellersLoading() {
        return ProductsRepo.getInstance().getIsBestSellersLoading();
    }
    public MutableLiveData<String> getBestSellersLoadingError(){
        return ProductsRepo.getInstance().getBestSellersLoadingError();
    }
}
