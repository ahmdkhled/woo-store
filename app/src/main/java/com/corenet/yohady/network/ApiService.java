package com.corenet.yohady.network;

import com.corenet.yohady.model.Category;
import com.corenet.yohady.model.Coupon;
import com.corenet.yohady.model.Order;
import com.corenet.yohady.model.OrderPayload;
import com.corenet.yohady.model.Product;
import com.corenet.yohady.model.Review;
import com.corenet.yohady.model.TopSeller;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET(Constants.PRODUCTS_ENDPOINT)
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

    @GET(Constants.CATEGORIES_ENDPOINT)
    Call<ArrayList<Category>> getCategories(
                                @Query("page")String page,@Query("per_page") String per_page,
                                @Query("parent")  String parent,@Query("product") String product,
                                @Query("search")  String search,
                                @Query("include")  String include,@Query("exclude")  String exclude,
                                @Query("slug") String slug,@Query("hide_empty")  String hide_empty,
                                @Query("order_by")  String order_by,@Query("order")  String order
                                  );

    @GET(Constants.REVIEWS_ENDPOINT)
    Call<ArrayList<Review>> getReviews(@Query("page") String page,@Query("product") int product_id);

    @GET(Constants.TOPSELLERS_ENDPOINT)
    Call<ArrayList<TopSeller>> getTopSeller(@Query("period")String period,
                                            @Query("date_min")String date_min,
                                            @Query("date_max")String date_max);

    @GET(Constants.ORDERS_ENDPOINT)
    Call<ArrayList<Order>> getOrders(@Query("context") String context,@Query("page") String page,
                          @Query("per_page") String per_page,@Query("search") String search,
                          @Query("after") String after,@Query("before") String before,
                          @Query("exclude") String exclude,@Query("include") String include,
                          @Query("offset") String offset,@Query("order") String order,
                          @Query("orderby") String orderby,@Query("product") String product,
                          @Query("status") String status,@Query("customer") String customer,
                          @Query("parent") String parent,@Query("parent_exclude") String parent_exclude,
                          @Query("dp") String dp
    );

    @POST(Constants.ORDERS_ENDPOINT)
    Call<Order> createOrder(@Body OrderPayload orderPayload);

    @FormUrlEncoded
    @POST(Constants.REVIEWS_ENDPOINT)
    Call<Review> createReview(@Field("product_id") int productId, @Field("review") String review,
                              @Field("reviewer") String reviewer,@Field("reviewer_email") String reviewerEmail,
                              @Field("rating") int rating);

    @GET(Constants.COUPONS_ENDPOINT)
    Call<ArrayList<Coupon>> getCouponDetails(@Query("context") String context,@Query("page") String page,
                                  @Query("per_page") String per_page,@Query("search") String search,
                                  @Query("after") String after,@Query("before") String before,
                                  @Query("exclude") String exclude,@Query("include") String include,
                                  @Query("offset") String offset,@Query("order") String order,
                                  @Query("orderby") String orderby,@Query("code") String code

                                  );



}
