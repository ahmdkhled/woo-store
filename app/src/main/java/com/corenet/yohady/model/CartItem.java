package com.corenet.yohady.model;

public class CartItem {
    private int product_id;
    private int quantity;

    public CartItem(int product_id, int quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public int getId() {
        return product_id;
    }

    public void setId(int id) {
        this.product_id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
