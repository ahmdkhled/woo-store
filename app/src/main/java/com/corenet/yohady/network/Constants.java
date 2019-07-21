package com.corenet.yohady.network;

public class Constants {

    static final String BASE_URL="https://yohady.com/";
    private static final String WOOCOMMERCE_PATH="wp-json/wc/v3/";
    private static final String WORDPRESS_PATH="/wp/v2/";

    public static String getWcEndpoint(String endpoint){
        return BASE_URL+WOOCOMMERCE_PATH+endpoint;
    }

    public static String getWpEndpoint(String endpoint){
        return BASE_URL+WORDPRESS_PATH+endpoint;
    }

    static final String PRODUCTS_ENDPOINT=BASE_URL+WOOCOMMERCE_PATH+"PRODUCTS";
    static final String CATEGORIES_ENDPOINT=BASE_URL+WOOCOMMERCE_PATH+"products/categories";
    static final String REVIEWS_ENDPOINT=BASE_URL+WOOCOMMERCE_PATH+"products/reviews";
    static final String TOPSELLERS_ENDPOINT=BASE_URL+WOOCOMMERCE_PATH+"reports/top_sellers";
    static final String ORDERS_ENDPOINT=BASE_URL+WOOCOMMERCE_PATH+"orders";
    public static final String COUPONS_ENDPOINT=BASE_URL+WOOCOMMERCE_PATH+"coupons";
}
