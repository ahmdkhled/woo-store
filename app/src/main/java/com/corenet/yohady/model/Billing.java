package com.corenet.yohady.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Billing implements Parcelable {
    private String first_name;
    private String last_name;
    private String address_1;
    private String address_2;
    private String city;
    private String state;
    private String postcode;
    private String country;
    private String email;
    private String phone;

    public Billing() {
    }

    public Billing(String first_name, String last_name, String address_1,
                   String address_2, String city, String state,
                   String postcode, String country,
                   String email, String phone) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.address_1 = address_1;
        this.address_2 = address_2;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
        this.country = country;
        this.email = email;
        this.phone = phone;
    }

    protected Billing(Parcel in) {
        first_name = in.readString();
        last_name = in.readString();
        address_1 = in.readString();
        address_2 = in.readString();
        city = in.readString();
        state = in.readString();
        postcode = in.readString();
        country = in.readString();
        email = in.readString();
        phone = in.readString();
    }

    public static final Creator<Billing> CREATOR = new Creator<Billing>() {
        @Override
        public Billing createFromParcel(Parcel in) {
            return new Billing(in);
        }

        @Override
        public Billing[] newArray(int size) {
            return new Billing[size];
        }
    };

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAddress_1() {
        return address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(address_1);
        dest.writeString(address_2);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(postcode);
        dest.writeString(country);
        dest.writeString(email);
        dest.writeString(phone);
    }
}
