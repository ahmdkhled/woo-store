package com.corenet.yohady.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CouponLine implements Parcelable {

    private int id;
    private String code;
    private String discount;
    private String discount_tax;
    private ArrayList<MetaData> meta_data;

    public CouponLine(int id, String code, String discount, String discount_tax) {
        this.id = id;
        this.code = code;
        this.discount = discount;
        this.discount_tax = discount_tax;
    }

    protected CouponLine(Parcel in) {
        id = in.readInt();
        code = in.readString();
        discount = in.readString();
        discount_tax = in.readString();
        meta_data = in.createTypedArrayList(MetaData.CREATOR);
    }

    public static final Creator<CouponLine> CREATOR = new Creator<CouponLine>() {
        @Override
        public CouponLine createFromParcel(Parcel in) {
            return new CouponLine(in);
        }

        @Override
        public CouponLine[] newArray(int size) {
            return new CouponLine[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscount_tax() {
        return discount_tax;
    }

    public void setDiscount_tax(String discount_tax) {
        this.discount_tax = discount_tax;
    }

    public ArrayList<MetaData> getMeta_data() {
        return meta_data;
    }

    public void setMeta_data(ArrayList<MetaData> meta_data) {
        this.meta_data = meta_data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(code);
        dest.writeString(discount);
        dest.writeString(discount_tax);
        dest.writeTypedList(meta_data);
    }
}
