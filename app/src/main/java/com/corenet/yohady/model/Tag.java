package com.corenet.yohady.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tag implements Parcelable {

    private int id;
    private String name;
    private String slug;

    protected Tag(Parcel in) {
        id = in.readInt();
        name = in.readString();
        slug = in.readString();
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(slug);
    }
}
