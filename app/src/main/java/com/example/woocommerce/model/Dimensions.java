package com.example.woocommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Dimensions implements Parcelable {

    private String width;
    private String length;
    private String height;

    protected Dimensions(Parcel in) {
        width = in.readString();
        length = in.readString();
        height = in.readString();
    }

    public static final Creator<Dimensions> CREATOR = new Creator<Dimensions>() {
        @Override
        public Dimensions createFromParcel(Parcel in) {
            return new Dimensions(in);
        }

        @Override
        public Dimensions[] newArray(int size) {
            return new Dimensions[size];
        }
    };

    public String getWidth() {
        return width;
    }

    public String getLength() {
        return length;
    }

    public String getHeight() {
        return height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(width);
        dest.writeString(length);
        dest.writeString(height);
    }
}
