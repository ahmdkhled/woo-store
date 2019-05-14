package com.example.woocommerce.utils;

public interface CartListener {
    public void increaseItemQuantity(int position, int newQuqntity, String price);
    public void decreaseItemQuantity(int position, int newQuqntity, String price);
    public void removeItem(int position, int cartSize);
}
