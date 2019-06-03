package com.example.woocommerce.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderPayload {

    private String payment_method;
    private String payment_method_title;
    private Boolean set_paid;
    private Billing billing;
    private Shipping shipping;
    @SerializedName("line_items")
    private ArrayList<CartItem> line_items;
    private ArrayList<ShippingLine> shipping_lines;

    public OrderPayload() {
    }

    public OrderPayload(String payment_method, String payment_method_title,
                        Boolean set_paid, Billing billing, Shipping shipping,
                        ArrayList<CartItem> line_items,
                        ArrayList<ShippingLine> shipping_lines) {
        this.payment_method = payment_method;
        this.payment_method_title = payment_method_title;
        this.set_paid = set_paid;
        this.billing = billing;
        this.shipping = shipping;
        this.line_items = line_items;
        this.shipping_lines = shipping_lines;
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
}
