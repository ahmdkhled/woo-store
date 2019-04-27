package com.example.woocommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MetaData implements Parcelable {
    private int id;
    private String key;
    private String value;

    protected MetaData(Parcel in) {
        id = in.readInt();
        key = in.readString();
        value = in.readString();
    }

    public static final Creator<MetaData> CREATOR = new Creator<MetaData>() {
        @Override
        public MetaData createFromParcel(Parcel in) {
            return new MetaData(in);
        }

        @Override
        public MetaData[] newArray(int size) {
            return new MetaData[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(key);
        dest.writeString(value);
    }
}
