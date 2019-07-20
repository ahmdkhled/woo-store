package com.corenet.yohady.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Product implements Parcelable {
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
    private String regular_price;
    private String sale_price;
    private String dateOnSaleFrom;
    private String dateOnSaleFromGmt;
    private String dateOnSaleTo;
    private String dateOnSaleToGmt;
    private String price_html;
    private Boolean on_sale;
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
    private String average_rating;
    private int rating_count;
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


    protected Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        slug = in.readString();
        date_created = in.readString();
        date_created_gmt = in.readString();
        date_modified = in.readString();
        date_modified_gmt = in.readString();
        type = in.readString();
        status = in.readString();
        featured = in.readByte() != 0;
        catalogVisibility = in.readString();
        description = in.readString();
        shortDescription = in.readString();
        sku = in.readString();
        price = in.readString();
        regular_price = in.readString();
        sale_price = in.readString();
        dateOnSaleFrom = in.readString();
        dateOnSaleFromGmt = in.readString();
        dateOnSaleTo = in.readString();
        dateOnSaleToGmt = in.readString();
        price_html = in.readString();
        byte tmpOnSale = in.readByte();
        on_sale = tmpOnSale == 0 ? null : tmpOnSale == 1;
        byte tmpPurchasable = in.readByte();
        purchasable = tmpPurchasable == 0 ? null : tmpPurchasable == 1;
        if (in.readByte() == 0) {
            totalSales = null;
        } else {
            totalSales = in.readInt();
        }
        byte tmpVirtual = in.readByte();
        virtual = tmpVirtual == 0 ? null : tmpVirtual == 1;
        byte tmpDownloadable = in.readByte();
        downloadable = tmpDownloadable == 0 ? null : tmpDownloadable == 1;
        if (in.readByte() == 0) {
            downloadLimit = null;
        } else {
            downloadLimit = in.readInt();
        }
        if (in.readByte() == 0) {
            downloadExpiry = null;
        } else {
            downloadExpiry = in.readInt();
        }
        externalUrl = in.readString();
        buttonText = in.readString();
        taxStatus = in.readString();
        taxClass = in.readString();
        byte tmpManageStock = in.readByte();
        manageStock = tmpManageStock == 0 ? null : tmpManageStock == 1;
        stockQuantity = in.readInt();
        stockStatus = in.readString();
        backorders = in.readString();
        byte tmpBackordersAllowed = in.readByte();
        backordersAllowed = tmpBackordersAllowed == 0 ? null : tmpBackordersAllowed == 1;
        byte tmpBackordered = in.readByte();
        backordered = tmpBackordered == 0 ? null : tmpBackordered == 1;
        byte tmpSoldIndividually = in.readByte();
        soldIndividually = tmpSoldIndividually == 0 ? null : tmpSoldIndividually == 1;
        weight = in.readString();
        byte tmpShippingRequired = in.readByte();
        shippingRequired = tmpShippingRequired == 0 ? null : tmpShippingRequired == 1;
        byte tmpShippingTaxable = in.readByte();
        shippingTaxable = tmpShippingTaxable == 0 ? null : tmpShippingTaxable == 1;
        shippingClass = in.readString();
        if (in.readByte() == 0) {
            shippingClassId = null;
        } else {
            shippingClassId = in.readInt();
        }
        byte tmpReviewsAllowed = in.readByte();
        reviewsAllowed = tmpReviewsAllowed == 0 ? null : tmpReviewsAllowed == 1;
        average_rating = in.readString();
        rating_count = in.readInt();
        parent_id = in.readInt();
        menu_order = in.readInt();
        purchase_note = in.readString();
        images = in.createTypedArrayList(Image.CREATOR);
        categories = in.createTypedArrayList(Category.CREATOR);
        tags = in.createTypedArrayList(Tag.CREATOR);
        attributes = in.createTypedArrayList(Attribute.CREATOR);
        default_attributes = in.createTypedArrayList(DefaultAttribute.CREATOR);
        related_ids=in.readArrayList(Integer.class.getClassLoader());
        upsell_ids=in.readArrayList(Integer.class.getClassLoader());
        cross_sell_ids=in.readArrayList(Integer.class.getClassLoader());
        grouped_products=in.readArrayList(Integer.class.getClassLoader());
        variations=in.readArrayList(Integer.class.getClassLoader());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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

    public String getRegular_price() {
        return regular_price;
    }

    public String getSale_price() {
        return sale_price;
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

    public String getPrice_html() {
        return price_html;
    }

    public Boolean getOn_sale() {
        return on_sale;
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

    public String getAverage_rating() {
        return average_rating;
    }

    public int getRating_count() {
        return rating_count;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(slug);
        dest.writeString(date_created);
        dest.writeString(date_created_gmt);
        dest.writeString(date_modified);
        dest.writeString(date_modified_gmt);
        dest.writeString(type);
        dest.writeString(status);
        dest.writeByte((byte) (featured ? 1 : 0));
        dest.writeString(catalogVisibility);
        dest.writeString(description);
        dest.writeString(shortDescription);
        dest.writeString(sku);
        dest.writeString(price);
        dest.writeString(regular_price);
        dest.writeString(sale_price);
        dest.writeString(dateOnSaleFrom);
        dest.writeString(dateOnSaleFromGmt);
        dest.writeString(dateOnSaleTo);
        dest.writeString(dateOnSaleToGmt);
        dest.writeString(price_html);
        dest.writeByte((byte) (on_sale == null ? 0 : on_sale ? 1 : 2));
        dest.writeByte((byte) (purchasable == null ? 0 : purchasable ? 1 : 2));
        if (totalSales == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalSales);
        }
        dest.writeByte((byte) (virtual == null ? 0 : virtual ? 1 : 2));
        dest.writeByte((byte) (downloadable == null ? 0 : downloadable ? 1 : 2));
        if (downloadLimit == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(downloadLimit);
        }
        if (downloadExpiry == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(downloadExpiry);
        }
        dest.writeString(externalUrl);
        dest.writeString(buttonText);
        dest.writeString(taxStatus);
        dest.writeString(taxClass);
        dest.writeByte((byte) (manageStock == null ? 0 : manageStock ? 1 : 2));
        dest.writeInt(stockQuantity);
        dest.writeString(stockStatus);
        dest.writeString(backorders);
        dest.writeByte((byte) (backordersAllowed == null ? 0 : backordersAllowed ? 1 : 2));
        dest.writeByte((byte) (backordered == null ? 0 : backordered ? 1 : 2));
        dest.writeByte((byte) (soldIndividually == null ? 0 : soldIndividually ? 1 : 2));
        dest.writeString(weight);
        dest.writeByte((byte) (shippingRequired == null ? 0 : shippingRequired ? 1 : 2));
        dest.writeByte((byte) (shippingTaxable == null ? 0 : shippingTaxable ? 1 : 2));
        dest.writeString(shippingClass);
        if (shippingClassId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(shippingClassId);
        }
        dest.writeByte((byte) (reviewsAllowed == null ? 0 : reviewsAllowed ? 1 : 2));
        dest.writeString(average_rating);
        dest.writeInt(rating_count);
        dest.writeInt(parent_id);
        dest.writeInt(menu_order);
        dest.writeString(purchase_note);
        dest.writeTypedList(images);
        dest.writeTypedList(categories);
        dest.writeTypedList(tags);
        dest.writeTypedList(attributes);
        dest.writeTypedList(default_attributes);
        dest.writeList(related_ids);
        dest.writeList(upsell_ids);
        dest.writeList(cross_sell_ids);
        dest.writeList(grouped_products);
        dest.writeList(variations);
    }
}
