package com.corenet.yohady.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable{
    private Integer id;
    private String dateCreated;
    private String dateCreatedGmt;
    private Integer productId;
    private String status;
    private String reviewer;
    private String reviewerEmail;
    private String review;
    private Integer rating;
    private Boolean verified;
    private ReviewerAvatarUrls reviewerAvatarUrls;

    public Review(String reviewer, String review, Integer rating) {
        this.reviewer = reviewer;
        this.review = review;
        this.rating = rating;
    }

    protected Review(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        dateCreated = in.readString();
        dateCreatedGmt = in.readString();
        if (in.readByte() == 0) {
            productId = null;
        } else {
            productId = in.readInt();
        }
        status = in.readString();
        reviewer = in.readString();
        reviewerEmail = in.readString();
        review = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readInt();
        }
        byte tmpVerified = in.readByte();
        verified = tmpVerified == 0 ? null : tmpVerified == 1;
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateCreatedGmt() {
        return dateCreatedGmt;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getStatus() {
        return status;
    }

    public String getReviewer() {
        return reviewer;
    }

    public String getReviewerEmail() {
        return reviewerEmail;
    }

    public String getReview() {
        return review;
    }

    public Integer getRating() {
        return rating;
    }

    public Boolean getVerified() {
        return verified;
    }

    public ReviewerAvatarUrls getReviewerAvatarUrls() {
        return reviewerAvatarUrls;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(dateCreated);
        parcel.writeString(dateCreatedGmt);
        if (productId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(productId);
        }
        parcel.writeString(status);
        parcel.writeString(reviewer);
        parcel.writeString(reviewerEmail);
        parcel.writeString(review);
        if (rating == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(rating);
        }
        parcel.writeByte((byte) (verified == null ? 0 : verified ? 1 : 2));
    }
}
