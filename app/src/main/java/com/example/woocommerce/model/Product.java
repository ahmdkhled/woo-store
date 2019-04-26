package com.example.woocommerce.model;

import java.util.ArrayList;

public class Product {
    private int id;
    private String name;
    private String slug;
    private String date_created;
    private String date_created_gmt;
    private String date_modified;
    private String date_modified_gmt;
    private String type;
    private String status;
    private boolean featured;
    private String catalogVisibility;
    private String description;
    private String shortDescription;
    private String sku;
    private String price;
    private String regularPrice;
    private String salePrice;
    private String dateOnSaleFrom;
    private String dateOnSaleFromGmt;
    private String dateOnSaleTo;
    private String dateOnSaleToGmt;
    private String priceHtml;
    private Boolean onSale;
    private Boolean purchasable;
    private Integer totalSales;
    private Boolean virtual;
    private Boolean downloadable;
    private Integer downloadLimit;
    private Integer downloadExpiry;
    private String externalUrl;
    private String buttonText;
    private String taxStatus;
    private String taxClass;
    private Boolean manageStock;
    private int stockQuantity;
    private String stockStatus;
    private String backorders;
    private Boolean backordersAllowed;
    private Boolean backordered;
    private Boolean soldIndividually;
    private String weight;
    private Boolean shippingRequired;
    private Boolean shippingTaxable;
    private String shippingClass;
    private Integer shippingClassId;
    private Boolean reviewsAllowed;
    private String averageRating;
    private int ratingCount;
    private int parent_id;
    private int menu_order;
    private String purchase_note;
    private ArrayList<Image> images;
    private ArrayList<Category> categories;
    private ArrayList<Tag> tags;
    private ArrayList<Attribute> attributes;
    private ArrayList<DefaultAttribute> default_attributes;
    private ArrayList<Integer> related_ids;
    private ArrayList<Integer> upsell_ids;
    private ArrayList<Integer> cross_sell_ids;
    private ArrayList<Integer> grouped_products;
    private ArrayList<Integer> variations;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getDate_created() {
        return date_created;
    }

    public String getDate_created_gmt() {
        return date_created_gmt;
    }

    public String getDate_modified() {
        return date_modified;
    }

    public String getDate_modified_gmt() {
        return date_modified_gmt;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public boolean isFeatured() {
        return featured;
    }

    public String getCatalogVisibility() {
        return catalogVisibility;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getSku() {
        return sku;
    }

    public String getPrice() {
        return price;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public String getDateOnSaleFrom() {
        return dateOnSaleFrom;
    }

    public String getDateOnSaleFromGmt() {
        return dateOnSaleFromGmt;
    }

    public String getDateOnSaleTo() {
        return dateOnSaleTo;
    }

    public String getDateOnSaleToGmt() {
        return dateOnSaleToGmt;
    }

    public String getPriceHtml() {
        return priceHtml;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public Boolean getPurchasable() {
        return purchasable;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public Boolean getVirtual() {
        return virtual;
    }

    public Boolean getDownloadable() {
        return downloadable;
    }

    public Integer getDownloadLimit() {
        return downloadLimit;
    }

    public Integer getDownloadExpiry() {
        return downloadExpiry;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public String getButtonText() {
        return buttonText;
    }

    public String getTaxStatus() {
        return taxStatus;
    }

    public String getTaxClass() {
        return taxClass;
    }

    public Boolean getManageStock() {
        return manageStock;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public String getBackorders() {
        return backorders;
    }

    public Boolean getBackordersAllowed() {
        return backordersAllowed;
    }

    public Boolean getBackordered() {
        return backordered;
    }

    public Boolean getSoldIndividually() {
        return soldIndividually;
    }

    public String getWeight() {
        return weight;
    }

    public Boolean getShippingRequired() {
        return shippingRequired;
    }

    public Boolean getShippingTaxable() {
        return shippingTaxable;
    }

    public String getShippingClass() {
        return shippingClass;
    }

    public Integer getShippingClassId() {
        return shippingClassId;
    }

    public Boolean getReviewsAllowed() {
        return reviewsAllowed;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public int getParent_id() {
        return parent_id;
    }

    public int getMenu_order() {
        return menu_order;
    }

    public String getPurchase_note() {
        return purchase_note;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public ArrayList<DefaultAttribute> getDefault_attributes() {
        return default_attributes;
    }

    public ArrayList<Integer> getRelated_ids() {
        return related_ids;
    }

    public ArrayList<Integer> getUpsell_ids() {
        return upsell_ids;
    }

    public ArrayList<Integer> getCross_sell_ids() {
        return cross_sell_ids;
    }

    public ArrayList<Integer> getGrouped_products() {
        return grouped_products;
    }

    public ArrayList<Integer> getVariations() {
        return variations;
    }
}
