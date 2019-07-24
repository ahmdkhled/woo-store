package com.corenet.yohady.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ShippingLine implements Parcelable {
    
    private String method_id;
    private String method_title;
    private Integer total;


    protected ShippingLine(Parcel in) {
        method_id = in.readString();
        method_title = in.readString();
        if (in.readByte() == 0) {
            total = null;
        } else {
            total = in.readInt();
        }
    }

    public static final Creator<ShippingLine> CREATOR = new Creator<ShippingLine>() {
        @Override
        public ShippingLine createFromParcel(Parcel in) {
            return new ShippingLine(in);
        }

        @Override
        public ShippingLine[] newArray(int size) {
            return new ShippingLine[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(method_id);
        dest.writeString(method_title);
        if (total == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(total);
        }
    }
}
