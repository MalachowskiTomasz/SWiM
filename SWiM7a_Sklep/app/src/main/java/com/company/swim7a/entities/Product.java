package com.company.swim7a.entities;

public class Product {
    private String code;
    private String title;
    private float price;

    public Product(String code, String title, float price) {
        this.code = code;
        this.title = title;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }
}
