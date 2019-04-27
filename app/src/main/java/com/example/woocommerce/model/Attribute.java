package com.example.woocommerce.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Attribute implements Parcelable {

    private int id;
    private String name;
    private int position;
    private boolean visible;
    private boolean variation;
    private ArrayList<String> options;

    protected Attribute(Parcel in) {
        id = in.readInt();
        name = in.readString();
        position = in.readInt();
        visible = in.readByte() != 0;
        variation = in.readByte() != 0;
        options = in.createStringArrayList();
    }

    public static final Creator<Attribute> CREATOR = new Creator<Attribute>() {
        @Override
        public Attribute createFromParcel(Parcel in) {
            return new Attribute(in);
        }

        @Override
        public Attribute[] newArray(int size) {
            return new Attribute[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isVariation() {
        return variation;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(position);
        dest.writeByte((byte) (visible ? 1 : 0));
        dest.writeByte((byte) (variation ? 1 : 0));
        dest.writeStringList(options);
    }
}
