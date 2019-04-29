package com.example.woocommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {

    private int id;
    private String src;

    protected Image(Parcel in) {
        id = in.readInt();
        src = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getSrc() {
        return src;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(src);
    }
}
