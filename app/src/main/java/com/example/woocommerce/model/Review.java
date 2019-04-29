package com.example.woocommerce.model;

public class Review {
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
}
