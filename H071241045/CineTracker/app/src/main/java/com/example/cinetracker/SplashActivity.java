package com.example.cinetracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.iv_splash_logo);
        TextView appName = findViewById(R.id.tv_app_name);
        TextView tagline = findViewById(R.id.tv_tagline);

        if (logo != null) {
            logo.setVisibility(View.INVISIBLE);
            logo.postDelayed(() -> {
                logo.setVisibility(View.VISIBLE);
                logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.item_slide_up));
            }, 100);
        }
        if (appName != null) {
            appName.setVisibility(View.INVISIBLE);
            appName.postDelayed(() -> {
                appName.setVisibility(View.VISIBLE);
                appName.startAnimation(AnimationUtils.loadAnimation(this, R.anim.item_slide_up));
            }, 300);
        }
        if (tagline != null) {
            tagline.setVisibility(View.INVISIBLE);
            tagline.postDelayed(() -> {
                tagline.setVisibility(View.VISIBLE);
                tagline.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
            }, 600);
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            // Intent membawa data versi app ke MainActivity (poin 2 modul: Intent untuk komunikasi)
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra("launch_source", "splash");
            intent.putExtra("app_version", "1.0");
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }, 2500);
    }
}
