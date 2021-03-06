package com.corenet.yohady.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.corenet.yohady.R;
import com.corenet.yohady.model.Image;
import com.corenet.yohady.model.Product;
import com.corenet.yohady.ui.ProductDetailActivity;
import com.corenet.yohady.utils.HtmlUtil;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private Context context;
    private ArrayList<Product> productsList;
    private boolean isMainSample;



    public ProductAdapter(Context context, ArrayList<Product> productsList, boolean isMainSample) {
        if (productsList==null) productsList=new ArrayList<>();
        this.context = context;
        this.productsList = productsList;
        this.isMainSample = isMainSample;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.product_row,parent,false);
        if (isMainSample){
            RecyclerView.LayoutParams layoutParams =new RecyclerView.LayoutParams(370, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5,5,5,5);
            v.setLayoutParams(layoutParams);
        }

        return new ProductHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product=productsList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(HtmlUtil.toString(product.getPrice_html()));
        ArrayList<Image> images=product.getImages();
        if (images!=null&&!images.isEmpty()){
            Glide.with(context).load(images.get(0).getSrc()).into(holder.image);
        }else {
            holder.image.setImageResource(R.drawable.notfound);
        }
//        if (product.getSale_price()!=null&& !TextUtils.isEmpty(product.getSale_price())){
//            holder.sale_price.setVisibility(View.VISIBLE);
//            holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//            holder.price.setTextColor(Color.parseColor("#A7A5A5"));
//            holder.price.setTypeface(holder.price.getTypeface(), Typeface.NORMAL);
//            holder.sale_price.setText(context.getString(R.string.product_price,product.getSale_price()));
//        }
//        Log.d("saleeeeeeee", "onBindViewHolder: "+product.getSale_price());
    }

    @Override
    public int getItemCount() {
        if(productsList != null && productsList.size() > 0) return productsList.size();
        return 0;
    }

    public void addProducts (ArrayList<Product> products) {
        this.productsList.addAll(products);
    }
    public void swapDate(ArrayList<Product> products) {
        productsList = products;
        notifyDataSetChanged();
    }

    class ProductHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name,price,sale_price;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.product_image);
            name=itemView.findViewById(R.id.product_name);
            price=itemView.findViewById(R.id.product_price);
            sale_price=itemView.findViewById(R.id.product_sale_price);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ProductDetailActivity.class);
                    intent.putExtra(ProductDetailActivity.PRODUCT_KEY,productsList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
