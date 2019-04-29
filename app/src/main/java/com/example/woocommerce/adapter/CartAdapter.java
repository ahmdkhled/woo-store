package com.example.woocommerce.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.woocommerce.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.woocommerce.model.Image;
import com.example.woocommerce.model.Product;
import com.example.woocommerce.utils.CartListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    private Context context;
    private List<Product> products;
    private CartListener mCartListener;



    public CartAdapter(Context context, List<Product> products, CartListener cartListener) {
        this.context = context;
        this.products = products;
        this.mCartListener = cartListener;
    }


    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartHolder(LayoutInflater.from(context).inflate(R.layout.cart_item_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CartHolder holder, int position) {
        final Product product = products.get(position);
        holder.mCartItemName.setText(product.getName());
        holder.mPrice.setText(product.getOn_sale()?product.getSale_price():product.getRegular_price()+" EGP");
        holder.mQuantityTxt.setText("1");
        List<Image> images = product.getImages();
        if(images != null && images.size() > 0){
            Glide.with(context)
                    .load(images.get(0).getSrc())
                    .apply(new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.notfound))
                    .into(holder.mCartItemImage);
        }else holder.mCartItemImage.setImageResource(R.drawable.notfound);


        holder.mIncreaseQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("fromcartadapter","increase");
                int oldQuantity =Integer.valueOf(holder.mQuantityTxt.getText().toString());
                Log.d("fromcartadapter","increase old : "+oldQuantity);
                Log.d("fromcartadapter","stock is : "+product.getStockQuantity());

                    int newQuantity = oldQuantity + 1;
                    Log.d("fromcartadapter","increase new "+ newQuantity);
                    holder.mQuantityTxt.setText(String.valueOf(newQuantity));
                    mCartListener.increaseItemQuantity(product.getOn_sale() ? product.getSale_price() : product.getRegular_price());

            }
        });

        holder.mDecreaseQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int oldQuantity =Integer.valueOf(holder.mQuantityTxt.getText().toString());
                if(oldQuantity > 1) {
                    int newQuantity = oldQuantity - 1;
                    holder.mQuantityTxt.setText(newQuantity + "");
                    mCartListener.decreaseItemQuantity(product.getOn_sale() ? product.getSale_price() : product.getRegular_price());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (products != null && products.size() > 0) return products.size();
        return 0;
    }

    public void notifyAdapter(List<Product> products){
        this.products = products;
        this.notifyDataSetChanged();
    }

    public class CartHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cart_item_image)
        ImageView mCartItemImage;
        @BindView(R.id.cart_item_name)
        TextView mCartItemName;
        @BindView(R.id.decrease_quantity_btn)
        ImageButton mDecreaseQuantityBtn;
        @BindView(R.id.increase_quantity_btn)
        ImageButton mIncreaseQuantityBtn;
        @BindView(R.id.cart_item_quantity)
        TextView mQuantityTxt;
        @BindView(R.id.cart_item_price)
        TextView mPrice;
        public CartHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

        }
    }



}
