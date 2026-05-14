package com.example.instagramapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramapp.R;
import com.example.instagramapp.adapters.FeedAdapter;
import com.example.instagramapp.adapters.StoryAdapter;
import com.example.instagramapp.models.DataManager;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView rvStoryBar = findViewById(R.id.rvStoryBar);
        rvStoryBar.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvStoryBar.setAdapter(
                new StoryAdapter(this, DataManager.getInstance().getHomeStories(), false));

        RecyclerView rvFeed = findViewById(R.id.rvFeed);
        rvFeed.setLayoutManager(new LinearLayoutManager(this));
        rvFeed.setAdapter(
                new FeedAdapter(this, DataManager.getInstance().getHomeFeedPosts(), true));

        findViewById(R.id.ivNavProfile).setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("isMyProfile", true);
            startActivity(intent);
        });

        findViewById(R.id.ivNavPost).setOnClickListener(v ->
                startActivity(new Intent(this, PostActivity.class)));
    }
}