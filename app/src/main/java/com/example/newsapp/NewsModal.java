package com.example.newsapp;

import java.util.ArrayList;

public class NewsModal{
    private final ArrayList<Articles> articles;


    public ArrayList<Articles> getArticles() {
        return articles;
    }

    public NewsModal(int totalResults, String status, ArrayList<Articles> articles) {
        this.articles = articles;
    }
}