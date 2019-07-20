package com.corenet.yohady.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.corenet.yohady.model.Product;
import com.corenet.yohady.repository.ProductsRepo;

import java.util.ArrayList;

public class MainAcrivityViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Product>> RecentlyAddedProducts;
    private MutableLiveData<ArrayList<Product>> deals;
    private MutableLiveData<ArrayList<Product>> bestSellers;



    public void getRecentlyAddedproducts(String page, String per_page,
                                         String search, String category,
                                          String order,
                                         String min_price, String max_price,
                                         String on_sale, String featured,
                                         String stock_status, String status,
                                         String context, String include,
                                         String sku, String slug,
                                         String tag, String shipping_class){


        if (RecentlyAddedProducts ==null){
            RecentlyAddedProducts = ProductsRepo.getInstance()
                        .getRecentProducts(page,per_page,search,category,order,
                                min_price,max_price,on_sale,featured,stock_status,
                                status,context,include,sku,slug,tag,shipping_class);
        }
    }

    public void getDeals( String page, String per_page,
                          String search, String category,
                          String order_by, String order,
                          String min_price, String max_price,
                          String featured,
                          String stock_status, String status,
                          String context, String include,
                          String sku, String slug,
                          String tag, String shipping_class){
        if (deals==null){
            deals=ProductsRepo.getInstance()
                    .getDeals(page,per_page,search,category,order_by,order,
                            min_price,max_price,featured,stock_status,
                            status,context,include,sku,slug,tag,shipping_class);

        }

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



    public MutableLiveData<ArrayList<Product>> getRecentlyAddedProducts() {
        return RecentlyAddedProducts;
    }

    public MutableLiveData<ArrayList<Product>> getDeals() {
        return deals;
    }

    public MutableLiveData<ArrayList<Product>> getBestSellers() {
        return bestSellers;
    }

    public MutableLiveData<Boolean> getIsRecentlyAddedProductsLoading() {
        return ProductsRepo.getInstance().getIsRecentlyAddedLoading();
    }

    public MutableLiveData<Boolean> getIsDealsLoading() {
        return ProductsRepo.getInstance().getIsDealsLoading();
    }

    public MutableLiveData<Boolean> getIsBestSellersLoading() {
        return ProductsRepo.getInstance().getIsBestSellersLoading();
    }

    public MutableLiveData<String> getRecentlyAddedProductsLoadingError() {
        return ProductsRepo.getInstance().getRecentlyAddedLoadingError();
    }

    public MutableLiveData<String> getDealsLoadingError(){
        return ProductsRepo.getInstance().getDealsLoadingError();
    }

    public MutableLiveData<String> getBestSellersLoadingError(){
        return ProductsRepo.getInstance().getBestSellersLoadingError();
    }





}
