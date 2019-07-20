package com.corenet.yohady.model;

import java.util.ArrayList;

class TaxLine {

    public Integer id;
    public String rate_code;
    public Integer rate_id;
    public String label;
    public Boolean compound;
    public String tax_total;
    public String shipping_tax_total;
    public ArrayList<MetaData> meta_data ;
}
