package com.example.woocommerce.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.woocommerce.model.Product;
import com.example.woocommerce.model.TopSeller;
import com.example.woocommerce.network.RetrofitClient;
import com.example.woocommerce.utils.ProductUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsRepo {


    private static final String TAG = "ProductsRepo";

    private static ProductsRepo productsRepo;
    private MutableLiveData<ArrayList<Product>> products;
    private MutableLiveData<ArrayList<Product>> mRecentProducts ;
    private MutableLiveData<ArrayList<Product>> mSaleproducts;
    private MutableLiveData<ArrayList<Product>> bestSellers;

    private MutableLiveData<Boolean> isProductsLoading=new MutableLiveData<>();
    private MutableLiveData<Boolean> isRecentlyAddedLoading=new MutableLiveData<>();
    private MutableLiveData<Boolean> isDealsLoading=new MutableLiveData<>();
    private MutableLiveData<Boolean> isBestSellersLoading=new MutableLiveData<>();


    private MutableLiveData<String> productsLoadingError=new MutableLiveData<>();
    private MutableLiveData<String> RecentlyAddedLoadingError=new MutableLiveData<>();
    private MutableLiveData<String> dealsLoadingError=new MutableLiveData<>();
    private MutableLiveData<String> bestSellersLoadingError=new MutableLiveData<>();

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
        Log.d(TAG, "getProducts: getting products");


        if (target.equals(products))
            isProductsLoading.setValue(true);
        else if (target.equals(mRecentProducts))
            isRecentlyAddedLoading.setValue(true);
        else if (target.equals(mSaleproducts))
            isDealsLoading.setValue(true);
        else if (target.equals(bestSellers))
            isBestSellersLoading.setValue(true);

        Call<ArrayList<Product>> call = RetrofitClient.getInstance().getApiService()
                .getProducts(page,per_page,search,category,order_by,
                        order,min_price,max_price,on_sale,featured,
                        stock_status,status,context,include,sku,slug,tag,shipping_class);

        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if(response.isSuccessful()) {
                    Log.d(TAG, "onResponse: successful call");
                    if (response.body() != null) {
                        Log.d(TAG, "onResponse: size " + response.body().size());
                        target.setValue(response.body());
                        if (target.equals(products))
                            isProductsLoading.setValue(false);
                        else if (target.equals(mRecentProducts))
                            isRecentlyAddedLoading.setValue(false);
                        else if (target.equals(mSaleproducts))
                            isDealsLoading.setValue(false);
                        else if (target.equals(bestSellers))
                            isBestSellersLoading.setValue(false);
                    }else Log.d(TAG, "onResponse: response is null");
                }else Log.d(TAG, "onResponse: response is failed");
            }
            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.d(TAG,"onFailure: error is "+t.getMessage());
                if (target.equals(products))
                    productsLoadingError.setValue(t.getMessage());
                else if (target.equals(mRecentProducts))
                    RecentlyAddedLoadingError.setValue(t.getMessage());
                else if (target.equals(mSaleproducts))
                    dealsLoadingError.setValue(t.getMessage());
                else if (target.equals(bestSellers))
                    bestSellersLoadingError.setValue(t.getMessage());


                if (target.equals(products))
                    isProductsLoading.setValue(false);
                else if (target.equals(mRecentProducts))
                    isRecentlyAddedLoading.setValue(false);
                else if (target.equals(mSaleproducts))
                    isDealsLoading.setValue(false);
                else if (target.equals(bestSellers))
                    isBestSellersLoading.setValue(false);
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

        Log.d("fromProductRepo","getProducts1"+category);
        products=new MutableLiveData<>();
        return getProducts(products, page,   per_page, search,category, order_by,order, min_price,
                max_price, on_sale,featured, stock_status,status, context,include, sku,slug,
                tag ,shipping_class);

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
        mRecentProducts=new MutableLiveData<>();
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

        mSaleproducts=new MutableLiveData<>();
        getProducts(mSaleproducts,page,per_page, search, category, order_by, order, min_price, max_price,
                 "true", featured, stock_status, status, context, include, sku, slug,
                 tag, shipping_class);

        return mSaleproducts;
    }

    public MutableLiveData<ArrayList<Product>> getBestSellers(String period, String date_min, String date_max,
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
        bestSellers=new MutableLiveData<>();
        isBestSellersLoading.setValue(true);
        RetrofitClient
                .getInstance()
                .getApiService()
                .getTopSeller(period,date_min,date_max)
                .enqueue(new Callback<ArrayList<TopSeller>>() {
                    @Override
                    public void onResponse(Call<ArrayList<TopSeller>> call, Response<ArrayList<TopSeller>> response) {
                        ArrayList<TopSeller> topSellerList=response.body();
                        if (topSellerList!=null){
                            if (topSellerList.isEmpty()){
                                bestSellers.setValue(new ArrayList<Product>());
                                return;
                            }
                            String include= ProductUtils.getProductIdsAsString(topSellerList);
                            getProducts(bestSellers,page,per_page, search, category, order_by, order, min_price, max_price,
                                    on_sale, featured, stock_status, status, context,include , sku, slug,
                                    tag, shipping_class);
                        }
                        else {
                            bestSellersLoadingError.setValue("error loading best sellers");
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<TopSeller>> call, Throwable t) {
                        Log.d("from_product_repo","best seller error "+t.getMessage());
                        bestSellersLoadingError.setValue("error loading best sellers");
                    }
                });
        return bestSellers;
    }


    public MutableLiveData<Boolean> getIsProductsLoading() {
        return isProductsLoading;
    }

    public MutableLiveData<Boolean> getIsRecentlyAddedLoading() {
        return isRecentlyAddedLoading;
    }

    public MutableLiveData<Boolean> getIsDealsLoading() {
        return isDealsLoading;
    }

    public MutableLiveData<Boolean> getIsBestSellersLoading() {
        return isBestSellersLoading;
    }


    public MutableLiveData<String> getProductsLoadingError() {
        return productsLoadingError;
    }

    public MutableLiveData<String> getRecentlyAddedLoadingError() { return RecentlyAddedLoadingError; }

    public MutableLiveData<String> getDealsLoadingError() {
        return dealsLoadingError;
    }

    public MutableLiveData<String> getBestSellersLoadingError() {
        return bestSellersLoadingError;
    }
}
