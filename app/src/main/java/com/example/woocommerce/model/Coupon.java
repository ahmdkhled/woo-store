package com.example.woocommerce.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Coupon {

    @SerializedName("id")
    private Integer id;

    @SerializedName("code")
    private String code;

    @SerializedName("amount")
    private String amount;

    @SerializedName("date_created")
    private String dateCreated;

    @SerializedName("date_created_gmt")
    private String dateCreatedGmt;

    @SerializedName("date_modified")
    private String dateModified;

    @SerializedName("date_modified_gmt")
    private String dateModifiedGmt;

    @SerializedName("discount_type")
    private String discountType;

    @SerializedName("description")
    private String description;

    @SerializedName("date_expires")
    private Object dateExpires;

    @SerializedName("date_expires_gmt")
    private Object dateExpiresGmt;

    @SerializedName("usage_count")
    private Integer usageCount;

    @SerializedName("individual_use")
    private Boolean individualUse;

    @SerializedName("product_ids")
    private ArrayList<Object> productIds ;

    @SerializedName("excluded_product_ids")
    private ArrayList<Object> excludedProductIds;

    @SerializedName("usage_limit")
    private Object usageLimit;

    @SerializedName("usage_limit_per_user")
    private Object usageLimitPerUser;

    @SerializedName("limit_usage_to_x_items")
    private Object limitUsageToXItems;

    @SerializedName("free_shipping")
    private Boolean freeShipping;

    @SerializedName("product_categories")
    private ArrayList<Object> productCategories ;

    @SerializedName("excluded_product_categories")
    private ArrayList<Object> excludedProductCategories ;

    @SerializedName("exclude_sale_items")
    private Boolean excludeSaleItems;
    @SerializedName("minimum_amount")
    private String minimumAmount;
    @SerializedName("maximum_amount")
    private String maximumAmount;
    @SerializedName("email_restrictions")
    private ArrayList<Object> emailRestrictions ;
    @SerializedName("used_by")
    private ArrayList<Object> usedBy;
    @SerializedName("meta_data")
    private ArrayList<Object> metaData ;


    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getAmount() {
        return amount;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateCreatedGmt() {
        return dateCreatedGmt;
    }

    public String getDateModified() {
        return dateModified;
    }

    public String getDateModifiedGmt() {
        return dateModifiedGmt;
    }

    public String getDiscountType() {
        return discountType;
    }

    public String getDescription() {
        return description;
    }

    public Object getDateExpires() {
        return dateExpires;
    }

    public Object getDateExpiresGmt() {
        return dateExpiresGmt;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public Boolean getIndividualUse() {
        return individualUse;
    }

    public ArrayList<Object> getProductIds() {
        return productIds;
    }

    public ArrayList<Object> getExcludedProductIds() {
        return excludedProductIds;
    }

    public Object getUsageLimit() {
        return usageLimit;
    }

    public Object getUsageLimitPerUser() {
        return usageLimitPerUser;
    }

    public Object getLimitUsageToXItems() {
        return limitUsageToXItems;
    }

    public Boolean getFreeShipping() {
        return freeShipping;
    }

    public ArrayList<Object> getProductCategories() {
        return productCategories;
    }

    public ArrayList<Object> getExcludedProductCategories() {
        return excludedProductCategories;
    }

    public Boolean getExcludeSaleItems() {
        return excludeSaleItems;
    }

    public String getMinimumAmount() {
        return minimumAmount;
    }

    public String getMaximumAmount() {
        return maximumAmount;
    }

    public ArrayList<Object> getEmailRestrictions() {
        return emailRestrictions;
    }

    public ArrayList<Object> getUsedBy() {
        return usedBy;
    }

    public ArrayList<Object> getMetaData() {
        return metaData;
    }
}

