package com.example.woocommerce.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderPayload {

    private String payment_method;
    private String payment_method_title;
    private Boolean set_paid;
    private int total;
    private Billing billing;
    private Shipping shipping;
    @SerializedName("line_items")
    private ArrayList<CartItem> line_items;
    private ArrayList<ShippingLine> shipping_lines;
    private ArrayList<CouponLine> coupon_lines ;


    public OrderPayload() {
    }

    public OrderPayload(String payment_method, String payment_method_title,
                        Boolean set_paid, int total, Billing billing,
                        Shipping shipping, ArrayList<CartItem> line_items,
                        ArrayList<ShippingLine> shipping_lines,
                        ArrayList<CouponLine> coupon_lines) {
        this.payment_method = payment_method;
        this.payment_method_title = payment_method_title;
        this.set_paid = set_paid;
        this.total = total;
        this.billing = billing;
        this.shipping = shipping;
        this.line_items = line_items;
        this.shipping_lines = shipping_lines;
        this.coupon_lines = coupon_lines;
    }

    public String getPaymentMethod() {
        return payment_method;
    }

    public void setPaymentMethod(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPaymentMethodTitle() {
        return payment_method_title;
    }

    public void setPaymentMethodTitle(String paymentMethodTitle) {
        this.payment_method_title = paymentMethodTitle;
    }

    public Boolean getSetPaid() {
        return set_paid;
    }

    public void setSetPaid(Boolean setPaid) {
        this.set_paid = setPaid;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public ArrayList<CartItem> getLineItems() {
        return line_items;
    }

    public void setLineItems(ArrayList<CartItem> lineItems) {
        this.line_items = lineItems;
    }

    public ArrayList<ShippingLine> getShippingLines() {
        return shipping_lines;
    }

    public void setShippingLines(ArrayList<ShippingLine> shippingLines) {
        this.shipping_lines = shippingLines;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPayment_method_title() {
        return payment_method_title;
    }

    public void setPayment_method_title(String payment_method_title) {
        this.payment_method_title = payment_method_title;
    }

    public Boolean getSet_paid() {
        return set_paid;
    }

    public void setSet_paid(Boolean set_paid) {
        this.set_paid = set_paid;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<CartItem> getLine_items() {
        return line_items;
    }

    public void setLine_items(ArrayList<CartItem> line_items) {
        this.line_items = line_items;
    }

    public ArrayList<ShippingLine> getShipping_lines() {
        return shipping_lines;
    }

    public void setShipping_lines(ArrayList<ShippingLine> shipping_lines) {
        this.shipping_lines = shipping_lines;
    }

    public ArrayList<CouponLine> getCoupon_lines() {
        return coupon_lines;
    }

    public void setCoupon_lines(ArrayList<CouponLine> coupon_lines) {
        this.coupon_lines = coupon_lines;
    }
}
