package com.corenet.yohady.adapter;

import android.content.Context;
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
import com.corenet.yohady.model.LineItem;
import com.corenet.yohady.model.Product;
import com.corenet.yohady.utils.HtmlUtil;

import java.util.ArrayList;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemHolder> {

    private Context context;
    private ArrayList<LineItem> orderItems;
    private ArrayList<Product> products;

    public OrderItemsAdapter(Context context, ArrayList<LineItem> orderItems, ArrayList<Product> products) {
        this.context = context;
        this.orderItems = orderItems;
        this.products = products;
    }

    @NonNull
    @Override
    public OrderItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.order_item_row,viewGroup,false);
        return new OrderItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemHolder holder, int i) {
        LineItem orderItem=orderItems.get(i);
        Product product=products.get(i);

        holder.quantity.setText(context.getString(R.string.product_quantity,orderItem.getQuantity()));
        holder.price.setText(HtmlUtil.toString(product.getPrice_html()));
        holder.name.setText(product.getName());

        ArrayList<Image> images=product.getImages();
        if (images!=null&&!images.isEmpty()){
            Glide.with(context).load(images.get(0).getSrc()).into(holder.image);
        }else {
            holder.image.setImageResource(R.drawable.notfound);
        }


    }

    @Override
    public int getItemCount() {
        if (orderItems==null)
            return 0;
        return orderItems.size();
    }

    class OrderItemHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name,price,quantity;
        public OrderItemHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.item_image);
            name=itemView.findViewById(R.id.item_name);
            price=itemView.findViewById(R.id.item_price);
            quantity=itemView.findViewById(R.id.item_quantity);
        }
    }
}
