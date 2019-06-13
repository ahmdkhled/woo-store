package com.example.woocommerce.ui;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.woocommerce.R;
import com.example.woocommerce.model.Review;
import com.example.woocommerce.viewmodel.CreateReviewModel;

public class ReviewActivity extends AppCompatActivity {

    @BindView(R.id.review_ratingBar)
    RatingBar mRating;
    @BindView(R.id.reviewer_edittext)
    AppCompatEditText mNameTxt;
    @BindView(R.id.reviewer_email_edittext)
    AppCompatEditText mEmailTxt;
    @BindView(R.id.review_edittext)
    AppCompatEditText mReviewTxt;
    @BindView(R.id.submit_review_btn)
    AppCompatButton mSubmitReview;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private int mProductId;
    private CreateReviewModel mViewModel;
    private Review newReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);


        // bind views
        ButterKnife.bind(this);

        // setup toolbar
        setSupportActionBar(mToolbar);

        // bind view model
        mViewModel = ViewModelProviders.of(this).get(CreateReviewModel.class);

        Intent intent = getIntent();
        if(intent != null){
            mProductId = intent.getIntExtra("product_id",-1);

        }

        mSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAvailablity()){
                    mProgressBar.setVisibility(View.VISIBLE);
                    mViewModel.createReview(mProductId,mReviewTxt.getText().toString(),
                            mNameTxt.getText().toString(), mEmailTxt.getText().toString(),
                            mRating.getNumStars());
                    newReview = new Review(mNameTxt.getText().toString(),mReviewTxt.getText().toString(),
                            mRating.getNumStars());
                    observeReviewCreation();
                }else  Toast.makeText(ReviewActivity.this, "missing fields", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void observeReviewCreation() {
        mViewModel.getIsReviewCreated().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                mProgressBar.setVisibility(View.GONE);
                if(aBoolean){
                    returnBack();
                }else Toast.makeText(ReviewActivity.this, "Something went wrong,please try again later ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void returnBack() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("new_review",newReview);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    private boolean checkAvailablity() {
        return (!TextUtils.isEmpty(mNameTxt.getText()) && !TextUtils.isEmpty(mEmailTxt.getText())
                && !TextUtils.isEmpty(mReviewTxt.getText()));

    }
}
