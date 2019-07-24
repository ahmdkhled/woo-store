package com.corenet.yohady.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class LineItem implements Parcelable {

    private int product_id;
    private int quantity;
    private int variation_id;
    private String tax_class;
    private int subtotal;
    private int subtotal_tax;
    private int total;
    private int total_tax;
    private int price;
    private String sku;
    private ArrayList<Tax> taxes;
    private ArrayList<MetaData> meta_data;


    public LineItem(Integer product_id, Integer quantity, int variation_id,
                    String tax_class, int subtotal, int subtotal_tax,
                    int total, int total_tax, int price, String sku,
                    ArrayList<Tax> taxes, ArrayList<MetaData> meta_data) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.variation_id = variation_id;
        this.tax_class = tax_class;
        this.subtotal = subtotal;
        this.subtotal_tax = subtotal_tax;
        this.total = total;
        this.total_tax = total_tax;
        this.price = price;
        this.sku = sku;
        this.taxes = taxes;
        this.meta_data = meta_data;
    }

    protected LineItem(Parcel in) {
        product_id = in.readInt();
        quantity = in.readInt();
        variation_id = in.readInt();
        tax_class = in.readString();
        subtotal = in.readInt();
        subtotal_tax = in.readInt();
        total = in.readInt();
        total_tax = in.readInt();
        price = in.readInt();
        sku = in.readString();
        meta_data = in.createTypedArrayList(MetaData.CREATOR);
    }

    public static final Creator<LineItem> CREATOR = new Creator<LineItem>() {
        @Override
        public LineItem createFromParcel(Parcel in) {
            return new LineItem(in);
        }

        @Override
        public LineItem[] newArray(int size) {
            return new LineItem[size];
        }
    };

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public int getVariation_id() {
        return variation_id;
    }

    public void setVariation_id(int variation_id) {
        this.variation_id = variation_id;
    }

    public String getTax_class() {
        return tax_class;
    }

    public void setTax_class(String tax_class) {
        this.tax_class = tax_class;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getSubtotal_tax() {
        return subtotal_tax;
    }

    public void setSubtotal_tax(int subtotal_tax) {
        this.subtotal_tax = subtotal_tax;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_tax() {
        return total_tax;
    }

    public void setTotal_tax(int total_tax) {
        this.total_tax = total_tax;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public ArrayList<Tax> getTaxes() {
        return taxes;
    }

    public void setTaxes(ArrayList<Tax> taxes) {
        this.taxes = taxes;
    }

    public ArrayList<MetaData> getMeta_data() {
        return meta_data;
    }

    public void setMeta_data(ArrayList<MetaData> meta_data) {
        this.meta_data = meta_data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(product_id);
        dest.writeInt(quantity);
        dest.writeInt(variation_id);
        dest.writeString(tax_class);
        dest.writeInt(subtotal);
        dest.writeInt(subtotal_tax);
        dest.writeInt(total);
        dest.writeInt(total_tax);
        dest.writeInt(price);
        dest.writeString(sku);
        dest.writeTypedList(meta_data);
    }
}
