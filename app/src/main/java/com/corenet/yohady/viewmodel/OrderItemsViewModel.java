package com.corenet.yohady.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.corenet.yohady.model.Product;
import com.corenet.yohady.repository.ProductsRepo;

import java.util.ArrayList;

public class OrderItemsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Product>> Products;

    public void getProducts( String page, String per_page,
                             String search, String category,
                             String order_by, String order,
                             String min_price, String max_price,
                             String on_sale, String featured,
                             String stock_status, String status,
                             String context, String include,
                             String sku, String slug,

                             final String tag, String shipping_class){

        if (Products==null)
        Products = ProductsRepo.getInstance()
                .getProducts(page,per_page,search,category,order_by,order,
                        min_price,max_price,on_sale,featured,stock_status,
                        status,context,include,sku,slug,tag,shipping_class);


    }

    public MutableLiveData<ArrayList<Product>> getProducts() {
        return Products;
    }

    public MutableLiveData<Boolean> getIsProductsLoading() {
        return ProductsRepo.getInstance().getIsProductsLoading();
    }

    public MutableLiveData<String> getProductLoadingError() {
        return ProductsRepo.getInstance().getProductsLoadingError();
    }

}
