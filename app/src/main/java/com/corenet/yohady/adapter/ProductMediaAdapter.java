package com.corenet.yohady.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.corenet.yohady.R;
import com.corenet.yohady.model.Image;

import java.util.ArrayList;

public class ProductMediaAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<Image> imagesList;

    public ProductMediaAdapter(Context context, ArrayList<Image> imagesList) {
        this.context = context;
        this.imagesList = imagesList;
    }

    @Override
    public int getCount() {
        return imagesList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view=LayoutInflater.from(context)
                .inflate(R.layout.product_images_slider,container,false);

        ImageView imageView=view.findViewById(R.id.product_slider_image);
        Glide.with(context)
                .load(imagesList.get(position).getSrc())
                .into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
