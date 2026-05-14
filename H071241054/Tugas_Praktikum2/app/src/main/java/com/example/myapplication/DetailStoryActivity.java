package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class DetailStoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);

        ImageView ivStoryImage = findViewById(R.id.ivStoryImage);
        TextView tvStoryTitle = findViewById(R.id.tvStoryTitle);
        ImageView btnCloseStory = findViewById(R.id.btnCloseStory);

        int storyImage = getIntent().getIntExtra("STORY_IMG", R.drawable.joy);
        String storyTitle = getIntent().getStringExtra("STORY_TITLE");

        ivStoryImage.setImageResource(storyImage);
        tvStoryTitle.setText(storyTitle);

        btnCloseStory.setOnClickListener(v -> finish());
    }
}