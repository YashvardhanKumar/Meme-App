package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

import static com.squareup.picasso.Picasso.*;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private final ArrayList<Articles> articlesArrayList;
    private final Context context;

    public NewsAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newss,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        Articles article = articlesArrayList.get(position);
        holder.subtitle.setText(article.getContent());
        holder.title.setText(article.getTitle());
        get().load(article.getUrlToImage()).into(holder.newsTV);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context,NewsOnClick.class);
            intent.putExtra("title",article.getTitle());
            intent.putExtra("content",article.getContent());
            intent.putExtra("desc",article.getDescription());
            intent.putExtra("image",article.getUrlToImage());
            intent.putExtra("url",article.getUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final TextView subtitle;
        private final ImageView newsTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.newsText);
            subtitle = itemView.findViewById(R.id.newsSubtitle);
            newsTV = itemView.findViewById(R.id.newsImage);

        }
    }
}
