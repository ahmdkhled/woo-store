package com.example.woocommerce.utils;

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
}
