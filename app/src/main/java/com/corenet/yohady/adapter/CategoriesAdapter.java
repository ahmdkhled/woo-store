package com.corenet.yohady.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.corenet.yohady.R;
import com.corenet.yohady.model.Category;
import com.corenet.yohady.ui.ProductsActivity;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryHolder> {

    private Context context;
    private ArrayList<Category> categoriesList;
    private int layout;
    private boolean isMain;

    public CategoriesAdapter(Context context, ArrayList<Category> categoriesList,boolean isMain) {
        this.context = context;
        this.categoriesList = categoriesList;
        this.isMain=isMain;
    }

    public CategoriesAdapter(Context context, ArrayList<Category> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(!isMain)layout = R.layout.category_item;
        else layout = R.layout.category_main_row;
        View v= LayoutInflater.from(context).inflate(layout,parent,false);

        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category=categoriesList.get(position);
        if(!category.getName().equals("Uncategorized")) {
            holder.name.setText(categoriesList.get(position).getName());

            if (category.getImage() != null) {
                Glide.with(context)
                        .load(category.getImage().getSrc())
                        .into(holder.image);
                Log.d("CATTTTT", "image src " + category.getImage().getSrc());
            } else {
                holder.image.setImageResource(R.drawable.notfound);
            }

        }
    }

    @Override
    public int getItemCount() {
        if(categoriesList != null && categoriesList.size() > 0) return categoriesList.size();
        return 0;
    }

    class CategoryHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.category_image);
            name=itemView.findViewById(R.id.category_name);

//            if (isMainSample){
//                ConstraintLayout.LayoutParams params=new ConstraintLayout
//                        .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300);
//                image.setLayoutParams(params);
//            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ProductsActivity.class);
                    intent.putExtra(ProductsActivity.TARGET_KEY,ProductsActivity.CATEGORIES_TARGET);
                    intent.putExtra(ProductsActivity.CATEGORY_INFO,
                            new String[]{
                            String.valueOf(categoriesList.get(getAdapterPosition()).getId()),
                            categoriesList.get(getAdapterPosition()).getName()});
                    context.startActivity(intent);

                }
            });
        }
    }
}
