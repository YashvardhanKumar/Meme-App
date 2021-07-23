package com.example.newsapp;

public class Articles {
    private final String title;
    private final String description;
    private final String urlToImage;
    private final String url;
    private final String content;

    public Articles(String title, String description, String urlToImage, String url, String content) {
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
        this.url = url;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }


    public String getUrlToImage() {
        return urlToImage;
    }


    public String getUrl() {
        return url;
    }


    public String getContent() {
        return content;
    }

}