package com.corenet.yohady.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {

    private int id;
    private String name;
    private String slug;
    private int parent;
    private Image image;

    protected Category(Parcel in) {
        id = in.readInt();
        name = in.readString();
        slug = in.readString();
        parent = in.readInt();
        image = in.readParcelable(Image.class.getClassLoader());
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public int getParent() {
        return parent;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(slug);
        dest.writeInt(parent);
        dest.writeParcelable(image, flags);
    }
}
