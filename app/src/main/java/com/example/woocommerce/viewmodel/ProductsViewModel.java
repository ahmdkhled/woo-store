package com.example.woocommerce.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.woocommerce.model.Product;
import com.example.woocommerce.repository.ProductsRepo;
import java.util.ArrayList;

public class ProductsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Product>> products;

    public MutableLiveData<ArrayList<Product>> getProducts(String page,  String per_page,
                                                           String search,  String category,
                                                           String order_by,  String order,
                                                           String min_price,  String max_price,
                                                           String on_sale,  String featured,
                                                           String stock_status,  String status,
                                                           String context,  String include,
                                                           String sku,  String slug,
                                                           String tag,  String shipping_class){
        if (products==null){
            products= ProductsRepo.getInstance()
                        .getProducts(page,per_page,search,category,order_by,order,
                                min_price,max_price,on_sale,featured,stock_status,
                                status,context,include,sku,slug,tag,shipping_class);
        }
        return products;
    }

    public MutableLiveData<ArrayList<Product>> getProducts() {
        return products;
    }

    public MutableLiveData<Boolean> getIsProductsLoading() {
        return ProductsRepo.getInstance().getIsProductsLoading();
    }

    public MutableLiveData<String> getProductsLoadingError() {
        return ProductsRepo.getInstance().getProductsLoadingError();
    }
}
