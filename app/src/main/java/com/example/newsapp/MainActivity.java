package com.example.newsapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements CategoryAdaptor.CategoryClickInterface {
    private ProgressBar loading;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryModal> categoryModalArrayList;
    private CategoryAdaptor categoryAdaptor;
    private NewsAdapter newsAdapter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteCache(this);
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            assert children != null;
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
    @Override protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        RecyclerView news = findViewById(R.id.news);
        RecyclerView category = findViewById(R.id.categories);
            loading = findViewById(R.id.loading);
            articlesArrayList = new ArrayList<>();
            categoryModalArrayList = new ArrayList<>();
            newsAdapter = new NewsAdapter(articlesArrayList, this);
            categoryAdaptor = new CategoryAdaptor(categoryModalArrayList, this, this);
            news.setLayoutManager(new LinearLayoutManager(this));
            news.setAdapter(newsAdapter);
            category.setAdapter(categoryAdaptor);
            getCategories();
            getNews("All");
            newsAdapter.notifyDataSetChanged();
        }
        private void getCategories () {
            categoryModalArrayList.add(new CategoryModal("All", "https://images.unsplash.com/photo-1476242906366-d8eb64c2f661?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1908&q=80"));
            categoryModalArrayList.add(new CategoryModal("Technology", "https://images.unsplash.com/photo-1488590528505-98d2b5aba04b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
            categoryModalArrayList.add(new CategoryModal("Science", "https://images.unsplash.com/photo-1530973428-5bf2db2e4d71?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
            categoryModalArrayList.add(new CategoryModal("Sports", "https://images.unsplash.com/photo-1484482340112-e1e2682b4856?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=755&q=80"));
            categoryModalArrayList.add(new CategoryModal("General", "https://images.unsplash.com/photo-1432821596592-e2c18b78144f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
            categoryModalArrayList.add(new CategoryModal("Business", "https://images.unsplash.com/photo-1462206092226-f46025ffe607?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=753&q=80"));
            categoryModalArrayList.add(new CategoryModal("Entertainment", "https://images.unsplash.com/photo-1499364615650-ec38552f4f34?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=666&q=80"));
            categoryModalArrayList.add(new CategoryModal("Health", "https://images.unsplash.com/photo-1463740839922-2d3b7e426a56?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=819&q=80"));
            categoryAdaptor.notifyDataSetChanged();
        }
        private void getNews (String category){
            loading.setVisibility(View.VISIBLE);
            articlesArrayList.clear();
            String categoryURL = "https://newsapi.org/v2/top-headlines?category=" + category + "&apikey=879c7597063540bca9348fa44223aca8";
            String url = "https://newsapi.org/v2/top-headlines?excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apikey=879c7597063540bca9348fa44223aca8";
            String BASE_URL = "https://newsapi.org";
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
            Call<NewsModal> call;
            if (category.equals("All")) {
                call = retrofitAPI.getAllNews(url);
            } else {
                call = retrofitAPI.getAllNewsCategory(categoryURL);
            }
            call.enqueue(new Callback<NewsModal>() {
                @Override
                public void onResponse(@NotNull Call<NewsModal> call, @NotNull Response<NewsModal> response) {

                    try {
                        NewsModal newsModal = response.body();
                        loading.setVisibility(View.GONE);
                        assert newsModal != null;
                        ArrayList<Articles> articles = newsModal.getArticles();
                        for (int i = 0; i < articles.size(); i++) {
                            articlesArrayList.add(new Articles(articles.get(i).getTitle(), articles.get(i).getDescription(), articles.get(i).getUrlToImage(), articles.get(i).getUrl(), articles.get(i).getContent()));
                        }
                        newsAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Exception ", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(@NotNull Call<NewsModal> call, @NotNull Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed To load news", Toast.LENGTH_SHORT).show();
                }
            });
        }
        @Override
        public void onCategoryClick ( int pos){
            String category = categoryModalArrayList.get(pos).getCategory();
            getNews(category);
        }
    }