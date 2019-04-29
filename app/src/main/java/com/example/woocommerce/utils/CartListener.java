package com.example.woocommerce.utils;

public interface CartListener {
    public void increaseItemQuantity(String price);
    public void decreaseItemQuantity(String price);
}
