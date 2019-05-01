package com.example.woocommerce.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.woocommerce.model.Product;
import com.example.woocommerce.network.RetrofitClient;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsRepo {

    private static ProductsRepo productsRepo;
    private MutableLiveData<ArrayList<Product>> products=new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mSaleproducts=new MutableLiveData<>();
    private MutableLiveData<Boolean> isProductsLoading=new MutableLiveData<>();
    private MutableLiveData<String> productsLoadingError=new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mRecentProducts = new MutableLiveData<>();

    public static ProductsRepo getInstance() {
        if (productsRepo==null)
            productsRepo=new ProductsRepo();
        return productsRepo;
    }

    private ProductsRepo() {}
    
    private synchronized MutableLiveData<ArrayList<Product>>
    getProducts(final MutableLiveData<ArrayList<Product>> target, String page, String per_page,
                String search, String category,
                String order_by, String order,
                String min_price, String max_price,
                String on_sale, String featured,
                String stock_status, String status,
                String context, String include,
                String sku, String slug,

                final String tag, String shipping_class){

        isProductsLoading.setValue(true);
        Call<ArrayList<Product>> call = RetrofitClient.getInstance().getApiService()
                .getProducts(page,per_page,search,category,order_by,
                        order,min_price,max_price,on_sale,featured,
                        stock_status,status,context,include,sku,slug,tag,shipping_class);

        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                target.setValue(response.body());
                isProductsLoading.setValue(false);
            }
            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                productsLoadingError.setValue(t.getMessage());
                isProductsLoading.setValue(false);
            }
        });

        return products;
    }


    public MutableLiveData<ArrayList<Product>> getProducts(String page,  String per_page,
                                                                 String search,  String category,
                                                                 String order_by , String order,
                                                                 String min_price,  String max_price,
                                                                 String on_sale,  String featured,
                                                                 String stock_status,  String status,
                                                                 String context,  String include,
                                                                 String sku,  String slug,

                                                                 String tag,  String shipping_class) {
        getProducts(products, page,   per_page, search,category, order_by,order, min_price,
                max_price, on_sale,featured, stock_status,status, context,include, sku,slug,
                tag ,shipping_class);
        return products;
    }

    public MutableLiveData<ArrayList<Product>> getRecentProducts(String page,  String per_page,
                                                                 String search,  String category,
                                                                 String order,
                                                                 String min_price,  String max_price,
                                                                 String on_sale,  String featured,
                                                                 String stock_status,  String status,
                                                                 String context,  String include,
                                                                 String sku,  String slug,

                                                                 String tag,  String shipping_class) {
        getProducts(mRecentProducts, page,   per_page, search,category, "date",order, min_price,
                max_price, on_sale,featured, stock_status,status, context,include, sku,slug,
                tag ,shipping_class);
        return mRecentProducts;
    }

    public MutableLiveData<ArrayList<Product>> getDeals(String page, String per_page,
                                                        String search, String category,
                                                        String order_by, String order,
                                                        String min_price, String max_price,
                                                        String featured,
                                                        String stock_status, String status,
                                                        String context, String include,
                                                        String sku, String slug,

                                                        String tag, String shipping_class){


        getProducts(mSaleproducts,page,per_page, search, category, order_by, order, min_price, max_price,
                 "true", featured, stock_status, status, context, include, sku, slug,
                 tag, shipping_class);

        return mSaleproducts;
    }



    public MutableLiveData<Boolean> getIsProductsLoading() {
        return isProductsLoading;
    }

    public MutableLiveData<String> getProductsLoadingError() {
        return productsLoadingError;
    }


}
