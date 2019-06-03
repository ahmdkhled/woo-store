package com.example.woocommerce.model;

import java.util.ArrayList;

public class Order {

    private Integer id;
    private Integer parent_id;
    private String number;
    private String order_key;
    private String created_via;
    private String version;
    private String status;
    private String currency;
    private String date_created;
    private String date_created_gmt;
    private String date_modified;
    private String date_modified_gmt;
    private String discount_total;
    private String discount_tax;
    private String shipping_total;
    private String shipping_tax;
    private String cart_tax;
    private String total;
    private String total_tax;
    private Boolean prices_include_tax;
    private Integer customer_id;
    private String customer_ip_address;
    private String customer_user_agent;
    private String customer_note;
    private Billing billing;
    private Shipping shipping;
    private String payment_method;
    private String payment_method_title;
    private String transaction_id;
    private String datePaid;
    private String date_paid_gmt;
    private String date_completed;
    private String date_completed_gmt;
    private String cart_hash;
    private ArrayList<MetaData> meta_data ;
    private ArrayList<LineItem> line_items ;
    private ArrayList<TaxLine> tax_lines ;
    private ArrayList<ShippingLine> shipping_lines ;
    private ArrayList<Object> fee_lines ;
    private ArrayList<Object> coupon_lines ;
    private ArrayList<Object> refunds ;


    public Integer getId() {
        return id;
    }

    public Integer getParentId() {
        return parent_id;
    }

    public String getNumber() {
        return number;
    }

    public String getOrder_key() {
        return order_key;
    }

    public String getCreated_via() {
        return created_via;
    }

    public String getVersion() {
        return version;
    }

    public String getStatus() {
        return status;
    }

    public String getCurrency() {
        return currency;
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

    public String getDiscount_total() {
        return discount_total;
    }

    public String getDiscount_tax() {
        return discount_tax;
    }

    public String getShipping_total() {
        return shipping_total;
    }

    public String getShipping_tax() {
        return shipping_tax;
    }

    public String getCart_tax() {
        return cart_tax;
    }

    public String getTotal() {
        return total;
    }

    public String getTotal_tax() {
        return total_tax;
    }

    public Boolean getPrices_include_tax() {
        return prices_include_tax;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public String getCustomer_ip_address() {
        return customer_ip_address;
    }

    public String getCustomer_user_agent() {
        return customer_user_agent;
    }

    public String getCustomer_note() {
        return customer_note;
    }

    public Billing getBilling() {
        return billing;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public String getPayment_method_title() {
        return payment_method_title;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public String getDatePaid() {
        return datePaid;
    }

    public String getDate_paid_gmt() {
        return date_paid_gmt;
    }

    public Object getDate_completed() {
        return date_completed;
    }

    public Object getDate_completed_gmt() {
        return date_completed_gmt;
    }

    public String getCart_hash() {
        return cart_hash;
    }

    public ArrayList<MetaData> getMeta_data() {
        return meta_data;
    }

    public ArrayList<LineItem> getLine_items() {
        return line_items;
    }

    public ArrayList<TaxLine> getTax_lines() {
        return tax_lines;
    }

    public ArrayList<ShippingLine> getShipping_lines() {
        return shipping_lines;
    }

    public ArrayList<Object> getFee_lines() {
        return fee_lines;
    }

    public ArrayList<Object> getCoupon_lines() {
        return coupon_lines;
    }

    public ArrayList<Object> getRefunds() {
        return refunds;
    }
}
