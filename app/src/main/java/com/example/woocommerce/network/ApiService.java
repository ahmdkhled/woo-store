package com.example.woocommerce.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("products")
    Call<ResponseBody> getProducts(@Query("page") String page,@Query("per_page") String per_page,
                                   @Query("search")String search,@Query("category") String category,
                                   @Query("order_by")String order_by,@Query("order") String order,
                                   @Query("min_price")String min_price,@Query("max_price") String max_price,
                                   @Query("on_sale")boolean on_sale,@Query("featured") boolean featured,
                                   @Query("stock_status")String stock_status,@Query("status") String status
                                   );
}
