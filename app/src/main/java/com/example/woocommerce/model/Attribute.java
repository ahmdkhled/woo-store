package com.example.woocommerce.model;

import java.util.ArrayList;

public class Attribute {

    private int id;
    private String name;
    private int position;
    private boolean visible;
    private boolean variation;
    private ArrayList<String> options;

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
}
