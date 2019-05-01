package com.example.woocommerce.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.woocommerce.model.Product;
import com.example.woocommerce.network.RetrofitClient;
import com.example.woocommerce.repository.ProductsRepo;
import java.util.ArrayList;

public class ProductsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Product>> RecentlyAddedproducts;
    private MutableLiveData<ArrayList<Product>> deals;

    public void getRecentlyAddedproducts(String page, String per_page,
                                         String search, String category,
                                          String order,
                                         String min_price, String max_price,
                                         String on_sale, String featured,
                                         String stock_status, String status,
                                         String context, String include,
                                         String sku, String slug,
                                         String tag, String shipping_class){


        if (RecentlyAddedproducts ==null){
            RecentlyAddedproducts = ProductsRepo.getInstance()
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


    public MutableLiveData<ArrayList<Product>> getRecentlyAddedproducts() {
        return RecentlyAddedproducts;
    }

    public MutableLiveData<ArrayList<Product>> getDeals() {
        return deals;
    }

    public MutableLiveData<Boolean> getIsRecentlyAddedProductsLoading() {
        return ProductsRepo.getInstance().getIsProductsLoading();
    }

    public MutableLiveData<String> getRecentlyAddedProductsLoadingError() {
        return ProductsRepo.getInstance().getProductsLoadingError();
    }



}
