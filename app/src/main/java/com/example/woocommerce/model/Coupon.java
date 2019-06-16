package com.example.woocommerce.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Coupon {

    @SerializedName("id")
    public Integer id;

    @SerializedName("code")
    public String code;

    @SerializedName("amount")
    public String amount;

    @SerializedName("date_created")
    public String dateCreated;

    @SerializedName("date_created_gmt")
    public String dateCreatedGmt;

    @SerializedName("date_modified")
    public String dateModified;

    @SerializedName("date_modified_gmt")
    public String dateModifiedGmt;

    @SerializedName("discount_type")
    public String discountType;

    @SerializedName("description")
    public String description;

    @SerializedName("date_expires")
    public Object dateExpires;

    @SerializedName("date_expires_gmt")
    public Object dateExpiresGmt;

    @SerializedName("usage_count")
    public Integer usageCount;

    @SerializedName("individual_use")
    public Boolean individualUse;

    @SerializedName("product_ids")
    public ArrayList<Object> productIds ;

    @SerializedName("excluded_product_ids")
    public ArrayList<Object> excludedProductIds;

    @SerializedName("usage_limit")
    public Object usageLimit;

    @SerializedName("usage_limit_per_user")
    public Object usageLimitPerUser;

    @SerializedName("limit_usage_to_x_items")
    public Object limitUsageToXItems;

    @SerializedName("free_shipping")
    public Boolean freeShipping;

    @SerializedName("product_categories")
    public ArrayList<Object> productCategories ;

    @SerializedName("excluded_product_categories")
    public ArrayList<Object> excludedProductCategories ;

    @SerializedName("exclude_sale_items")
    public Boolean excludeSaleItems;
    @SerializedName("minimum_amount")
    public String minimumAmount;
    @SerializedName("maximum_amount")
    public String maximumAmount;
    @SerializedName("email_restrictions")
    public ArrayList<Object> emailRestrictions ;
    @SerializedName("used_by")
    public ArrayList<Object> usedBy;
    @SerializedName("meta_data")
    public ArrayList<Object> metaData ;



}

