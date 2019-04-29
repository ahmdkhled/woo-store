package com.example.woocommerce.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.woocommerce.R;
import com.example.woocommerce.model.Review;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder> {

    private Context context;
    private ArrayList<Review> reviewsList;

    public ReviewsAdapter(Context context, ArrayList<Review> reviewsList) {
        this.context = context;
        this.reviewsList = reviewsList;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.review_row,parent,false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        Review review=reviewsList.get(position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.review.setText(Html.fromHtml(review.getReview(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.review.setText(Html.fromHtml(review.getReview()));
        }
        holder.review.setText(holder.review.getText().toString().replace("\n", " "));
        holder.reviewer.setText(review.getReviewer());
        holder.ratingBar.setRating(review.getRating());
    }

    @Override
    public int getItemCount() {
        if (reviewsList==null)
            return 0;
        return reviewsList.size();
    }

    class ReviewHolder extends RecyclerView.ViewHolder{
        RatingBar ratingBar;
        TextView reviewer,review;
        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar=itemView.findViewById(R.id.review_ratingBar);
            reviewer=itemView.findViewById(R.id.reviewer_name);
            review=itemView.findViewById(R.id.review_content);
        }
    }
}
