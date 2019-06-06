package com.example.woocommerce.utils;

import com.example.woocommerce.model.CartItem;
import com.example.woocommerce.model.Product;
import com.example.woocommerce.model.TopSeller;
import java.util.ArrayList;

public class ProductUtils {

    public static String getProductIdsAsString(ArrayList<TopSeller> topSellers){
        StringBuilder sp=new StringBuilder();
        for (int i = 0; i < topSellers.size(); i++) {
            if (i<topSellers.size()-1)
                sp.append(topSellers.get(i).getProduct_id()).append(",");
            else
                sp.append(topSellers.get(i).getProduct_id());
        }
        return sp.toString();
    }

    public static String getCartIdsAsString(ArrayList<CartItem> cartItems){
        StringBuilder sp=new StringBuilder();
        for (int i = 0; i < cartItems.size(); i++) {
            if (i<cartItems.size()-1)
                sp.append(cartItems.get(i).getId()).append(",");
            else
                sp.append(cartItems.get(i).getId());
        }
        return sp.toString();
    }

    public static int calculateTotalPrice(ArrayList<CartItem> cartItems, ArrayList<Product> products){
        int total=0;
        for (int i = 0; i < cartItems.size(); i++) {
            total+=cartItems.get(i).getQuantity()*Integer.valueOf(products.get(i).getPrice());
        }
        return total;
    }
}
