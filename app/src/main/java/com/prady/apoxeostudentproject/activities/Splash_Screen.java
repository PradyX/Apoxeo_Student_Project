package com.prady.apoxeostudentproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;
import com.prady.apoxeostudentproject.R;


public class Splash_Screen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1600;
    Animation animation;
    ProgressBar progressBar;
    LottieAnimationView lottieAnimationView, lottieAnimationView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        lottieAnimationView = (LottieAnimationView) findViewById(R.id.animation_view);
        lottieAnimationView2 = (LottieAnimationView) findViewById(R.id.animation_view2);

        animation = AnimationUtils.loadAnimation(this, R.anim.splah_slide_up);
        lottieAnimationView.startAnimation(animation);
        lottieAnimationView2.startAnimation(animation);
//        textView.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(Splash_Screen.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
