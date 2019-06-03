package com.example.woocommerce.model;

public class ShippingLine {
    
    private String method_id;
    private String method_title;
    private Integer total;



    public String getMethodId() {
        return method_id;
    }

    public void setMethodId(String methodId) {
        this.method_id = methodId;
    }

    public String getMethodTitle() {
        return method_title;
    }

    public void setMethodTitle(String methodTitle) {
        this.method_title = methodTitle;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
