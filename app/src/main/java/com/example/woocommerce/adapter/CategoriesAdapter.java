package com.example.woocommerce.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.woocommerce.R;
import com.example.woocommerce.model.Category;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryHolder> {

    private Context context;
    private ArrayList<Category> categoriesList;

    public CategoriesAdapter(Context context, ArrayList<Category> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(250, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(5,5,5,5);
        v.setLayoutParams(layoutParams);
        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category=categoriesList.get(position);
        holder.name.setText(categoriesList.get(position).getName());

        if (category.getImage()!=null) {
            Glide.with(context)
                    .load(category.getImage().getSrc())
                    .into(holder.image);
            Log.d("CATTTTT","image src "+category.getImage().getSrc());
        }else {
            holder.image.setImageResource(R.drawable.notfound);
        }
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.category_image);
            name=itemView.findViewById(R.id.category_name);
            name.setTextSize(15);
        }
    }
}
