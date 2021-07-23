
package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.ViewHolder> {
    private final ArrayList<CategoryModal> categoryModals;
    private final CategoryClickInterface categoryClickInterface;

    public CategoryAdaptor(ArrayList<CategoryModal> categoryModals, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryModals = categoryModals;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdaptor.ViewHolder holder, int position) {
        CategoryModal categoryModal = categoryModals.get(position);
        holder.categoryText.setText(categoryModal.getCategory());
        Picasso.get().load(categoryModal.getCateImageURL()).into(holder.categoryImage);
        holder.itemView.setOnClickListener(v -> categoryClickInterface.onCategoryClick(position));
    }

    @Override
    public int getItemCount() {
        return categoryModals.size();
    }
    public interface CategoryClickInterface{
        void onCategoryClick(int pos);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView categoryText;
        private final ImageView categoryImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.categoryText);
            categoryImage = itemView.findViewById(R.id.categoryImage);
        }
    }
}