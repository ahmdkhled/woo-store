package com.corenet.yohady.model;

import android.os.Parcel;
import android.os.Parcelable;

class Tax implements Parcelable {
    private int id;
    private double total;
    private double sub_total;

    protected Tax(Parcel in) {
        id = in.readInt();
        total = in.readDouble();
        sub_total = in.readDouble();
    }

    public static final Creator<Tax> CREATOR = new Creator<Tax>() {
        @Override
        public Tax createFromParcel(Parcel in) {
            return new Tax(in);
        }

        @Override
        public Tax[] newArray(int size) {
            return new Tax[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSub_total() {
        return sub_total;
    }

    public void setSub_total(double sub_total) {
        this.sub_total = sub_total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(total);
        dest.writeDouble(sub_total);
    }
}
