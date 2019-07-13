package com.example.woocommerce.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.woocommerce.R;
import com.example.woocommerce.utils.PrefManager;

public class OrderSummaryActivity extends AppCompatActivity {

    Button continueShopping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        continueShopping=findViewById(R.id.continueShopping);

        continueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                PrefManager.getInstance(getApplicationContext()).deleteCartItems();
                finish();
            }
        });
    }
}
