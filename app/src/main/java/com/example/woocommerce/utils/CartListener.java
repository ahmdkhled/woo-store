package com.example.woocommerce.utils;

import com.example.woocommerce.model.Product;

public interface CartListener {
    public void increaseItemQuantity(int position, int newQuqntity, String price);
    public void decreaseItemQuantity(int position, int newQuqntity, String price);
    public void removeItem(Product deletedProduct,int quantity, int position, int cartSize);
}
