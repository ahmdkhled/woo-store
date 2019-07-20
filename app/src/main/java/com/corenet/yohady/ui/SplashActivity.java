package com.corenet.yohady.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.corenet.yohady.R;

public class SplashActivity extends AppCompatActivity {

    TextView logo,slogan;
    ImageView logoImage;
    Handler handler;
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo=findViewById(R.id.logo);
        slogan=findViewById(R.id.slogan);
        logoImage=findViewById(R.id.logoImage);

        applyAnimation();

        handler=new Handler();
        runnable =new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }

        };

        handler.postDelayed(runnable,2100);

    }

    void applyAnimation(){
        AlphaAnimation alphaAnimation=new AlphaAnimation(.0f,1f);
        alphaAnimation.setDuration(2000);
        logo.startAnimation(alphaAnimation);

        slogan.animate();

    }


}
