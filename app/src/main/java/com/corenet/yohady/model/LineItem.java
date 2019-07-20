package com.corenet.yohady.model;

import java.util.ArrayList;

class LineItem {

    private Integer product_id;
    private Integer quantity;
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
}
