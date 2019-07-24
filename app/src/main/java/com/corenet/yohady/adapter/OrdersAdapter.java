package com.corenet.yohady.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.corenet.yohady.R;
import com.corenet.yohady.model.Order;
import com.corenet.yohady.ui.OrderItemsActivity;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderHolder> {

    private Context context;
    private ArrayList<Order> orders;

    public OrdersAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.order_row,viewGroup,false);
        return new OrderHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int i) {
        Order order=orders.get(i);
        holder.id.setText(String.valueOf(order.getId()));
        holder.id.setText(order.getTotal());
        holder.date.setText(order.getDate_created());
        holder.status.setText(order.getStatus());
    }

    @Override
    public int getItemCount() {
        if (orders==null)
            return 0;
        return orders.size();
    }

    public void addOrders(ArrayList<Order> orders){
        if (this.orders==null)
            this.orders=new ArrayList<>();
        this.orders.addAll(orders);
        notifyDataSetChanged();
    }

    class OrderHolder extends RecyclerView.ViewHolder{
        TextView id,date,total,status;
        Button seeDetails;
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.order_id);
            date=itemView.findViewById(R.id.order_date);
            total=itemView.findViewById(R.id.order_total);
            status=itemView.findViewById(R.id.order_status);
            seeDetails=itemView.findViewById(R.id.see_order_details);

            seeDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, OrderItemsActivity.class);
                    intent.putExtra(OrderItemsActivity.ORDER_ITEMS_KEY,orders.get(getAdapterPosition()).getLine_items());
                    context.startActivity(intent);

                }
            });

        }
    }
}
