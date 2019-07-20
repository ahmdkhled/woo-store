package com.corenet.yohady.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DefaultAttribute implements Parcelable {

    private int id;
    private String name;
    private String option;

    protected DefaultAttribute(Parcel in) {
        id = in.readInt();
        name = in.readString();
        option = in.readString();
    }

    public static final Creator<DefaultAttribute> CREATOR = new Creator<DefaultAttribute>() {
        @Override
        public DefaultAttribute createFromParcel(Parcel in) {
            return new DefaultAttribute(in);
        }

        @Override
        public DefaultAttribute[] newArray(int size) {
            return new DefaultAttribute[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOption() {
        return option;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(option);
    }
}
