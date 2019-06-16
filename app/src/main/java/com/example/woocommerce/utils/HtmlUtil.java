package com.example.woocommerce.utils;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;


public class HtmlUtil {

    public static Spanned toString(String htmlText){
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result= Html.fromHtml(htmlText, android.text.Html.FROM_HTML_MODE_COMPACT);
        } else {
            result= Html.fromHtml(htmlText);
        }
        return result;
    }

    public static String  getCurrency(String htmlText){
        String text=toString(htmlText).toString();
        StringBuilder currency=new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (currency.length()==3)
                return currency.toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (Character.isAlphabetic(text.charAt(i))){
                    currency.append(text.charAt(i));
                }

            }else{
                if (Character.getType(text.charAt(i))==Character.UPPERCASE_LETTER||
                        Character.getType(text.charAt(i))==Character.LOWERCASE_LETTER){
                    currency.append(text.charAt(i));
                }
            }
        }
        String className="woocommerce-Price-currencySymbol";
        return htmlText.substring(htmlText.lastIndexOf(className)+className.length()+2,htmlText.indexOf("</span>"));
    }
}
