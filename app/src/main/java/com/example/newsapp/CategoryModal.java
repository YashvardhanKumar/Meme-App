package com.example.newsapp;

public class CategoryModal {
    private final String category;
    private final String cateImageURL;

    public String getCategory() {
        return category;
    }


    public String getCateImageURL() {
        return cateImageURL;
    }


    public CategoryModal(String category, String cateImageURL) {
        this.category = category;
        this.cateImageURL = cateImageURL;
    }
}
