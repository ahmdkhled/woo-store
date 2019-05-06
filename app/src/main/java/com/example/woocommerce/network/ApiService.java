package com.example.woocommerce.network;

import com.example.woocommerce.model.Category;
import com.example.woocommerce.model.Product;
import com.example.woocommerce.model.Review;
import com.example.woocommerce.model.TopSeller;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("products")
    Call<ArrayList<Product>> getProducts(@Query("page") String page, @Query("per_page") String per_page,
                                         @Query("search")String search, @Query("category") String category,
                                         @Query("orderby")String order_by, @Query("order") String order,
                                         @Query("min_price")String min_price, @Query("max_price") String max_price,
                                         @Query("on_sale")String on_sale, @Query("featured") String featured,
                                         @Query("stock_status")String stock_status, @Query("status") String status,
                                         @Query("context")String context, @Query("include") String include,
                                         @Query("sku")String sku, @Query("slug") String slug,
                                         @Query("tag")String tag, @Query("shipping_class") String shipping_class
                                         );

    @GET("products/categories")
    Call<ArrayList<Category>> getCategories(
                                @Query("page")String page,@Query("per_page") String per_page,
                                @Query("parent")  String parent,@Query("product") String product,
                                @Query("search")  String search,
                                @Query("include")  String include,@Query("exclude")  String exclude,
                                @Query("slug") String slug,@Query("hide_empty")  String hide_empty,
                                @Query("order_by")  String order_by,@Query("order")  String order
                                  );

    @GET("products/reviews")
    Call<ArrayList<Review>> getReviews(@Query("page") String page,@Query("product") int product_id);

    @GET("reports/top_sellers")
    Call<ArrayList<TopSeller>> getTopSeller(@Query("period")String period,
                                            @Query("date_min")String date_min,
                                            @Query("date_max")String date_max);
}
