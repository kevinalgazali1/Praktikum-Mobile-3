package com.example.tugas_praktikum_02;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tugas_praktikum_02.model.Story;

public class DetailStoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);

        Story story =
                getIntent().getParcelableExtra("story");

        if (story != null) {

            ImageView ivStoryFull =
                    findViewById(R.id.iv_story_full);

            TextView tvStoryTitle =
                    findViewById(R.id.tv_story_title_detail);

            TextView tvClose =
                    findViewById(R.id.tv_close_story);

            ivStoryFull.setImageResource(
                    story.getCoverImage());

            tvStoryTitle.setText(
                    story.getTitle());

            // tombol tutup

            tvClose.setOnClickListener(v -> finish());
        }
    }
}