package com.corenet.yohady.utils;

import com.corenet.yohady.model.CartItem;
import com.corenet.yohady.model.LineItem;
import com.corenet.yohady.model.Product;
import com.corenet.yohady.model.TopSeller;
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
    public static String getOrderItemsIdsAsString(ArrayList<LineItem> orderItems){
        StringBuilder sp=new StringBuilder();
        for (int i = 0; i < orderItems.size(); i++) {
            if (i<orderItems.size()-1)
                sp.append(orderItems.get(i).getProduct_id()).append(",");
            else
                sp.append(orderItems.get(i).getProduct_id());
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
