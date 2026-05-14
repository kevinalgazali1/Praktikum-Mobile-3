package com.example.instagramapp.activities;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.instagramapp.R;

public class DetailStoryActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Handler handler = new Handler();
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);

        String title = getIntent().getStringExtra("title");
        String coverImage = getIntent().getStringExtra("coverImage");
        String content = getIntent().getStringExtra("content");

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> finish());

        ImageView ivStoryImage = findViewById(R.id.ivStoryImage);
        com.google.android.material.imageview.ShapeableImageView ivStoryUserAvatar =
                findViewById(R.id.ivStoryUserAvatar);
        TextView tvStoryTitle = findViewById(R.id.tvStoryTitle);
        TextView tvStoryContent = findViewById(R.id.tvStoryContent);
        progressBar = findViewById(R.id.progressBar);

        tvStoryTitle.setText(title);
        tvStoryContent.setText(content != null && !content.isEmpty() ? content : "No content available.");

        // Set fullscreen story image
        if (coverImage != null) {
            int resId = getResources().getIdentifier(coverImage, "drawable", getPackageName());
            ivStoryImage.setImageResource(resId != 0 ? resId : R.drawable.placeholder_story);
            ivStoryUserAvatar.setImageResource(resId != 0 ? resId : R.drawable.placeholder_avatar);
        }

        // Progress bar auto-close seperti Instagram
        progressBar.setMax(100);
        startProgress();
    }

    private void startProgress() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress += 2;
                progressBar.setProgress(progress);
                if (progress < 100) {
                    handler.postDelayed(this, 100);
                } else {
                    finish();
                }
            }
        }, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}