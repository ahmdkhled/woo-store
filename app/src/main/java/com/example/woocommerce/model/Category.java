package com.example.woocommerce.model;

public class Category {

    private int id;
    private String name;
    private String slug;
    private int parent;
    private Image image;

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
}
