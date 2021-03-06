package com.corenet.yohady.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.corenet.yohady.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.corenet.yohady.model.Image;
import com.corenet.yohady.model.Product;
import com.corenet.yohady.utils.CartListener;
import com.corenet.yohady.utils.HtmlUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    private Context context;
    private List<Product> products;
    private List<Integer> quantities;
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
    public void onBindViewHolder(@NonNull final CartHolder holder, final int position) {
        final Product product = products.get(position);
        int itemQuantity = quantities.get(position);
        holder.mCartItemName.setText(product.getName());
        holder.mPrice.setText(HtmlUtil.toString(product.getPrice_html()));
        holder.mQuantityTxt.setText(String.valueOf(itemQuantity));
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
                int oldQuantity =Integer.valueOf(holder.mQuantityTxt.getText().toString());
                int newQuantity = oldQuantity + 1;
                holder.mQuantityTxt.setText(String.valueOf(newQuantity));
                quantities.set(position,newQuantity);
                mCartListener.increaseItemQuantity(position,newQuantity,product.getOn_sale() ? product.getSale_price() : product.getRegular_price());

            }
        });

        holder.mDecreaseQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int oldQuantity =Integer.valueOf(holder.mQuantityTxt.getText().toString());
                if(oldQuantity > 1) {
                    int newQuantity = oldQuantity - 1;
                    holder.mQuantityTxt.setText(String.valueOf(newQuantity));
                    mCartListener.decreaseItemQuantity(position,newQuantity,product.getOn_sale() ? product.getSale_price() : product.getRegular_price());
                }
            }
        });

        holder.mRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCartListener.removeItem(product,Integer.valueOf(holder.mQuantityTxt.getText().toString()),position,products.size());

            }
        });
    }

    public void removeItem(int position){
        products.remove(position);
        quantities.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (products != null && products.size() > 0) return products.size();
        return 0;
    }

    public void notifyAdapter(List<Product> products,List<Integer> quantities){
        this.products = products;
        this.quantities = quantities;
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
        @BindView(R.id.cart_item_old_price)
        TextView mOldPrice;
        @BindView(R.id.remove_cart_item)
        TextView mRemoveItem;
        public CartHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

        }
    }



}
