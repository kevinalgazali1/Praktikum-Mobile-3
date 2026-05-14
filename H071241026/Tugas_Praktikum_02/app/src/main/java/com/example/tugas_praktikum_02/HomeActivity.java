package com.example.tugas_praktikum_02;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugas_praktikum_02.adapter.FeedAdapter;
import com.example.tugas_praktikum_02.adapter.StoryAdapter;
import com.example.tugas_praktikum_02.model.Post;
import com.example.tugas_praktikum_02.model.Story;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvHomeFeed;
    private RecyclerView rvStories;

    private FeedAdapter feedAdapter;
    private StoryAdapter storyAdapter;

    private ArrayList<Post> posts;
    private ArrayList<Story> stories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvStories = findViewById(R.id.rv_stories);

        rvStories.setLayoutManager(
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false));

        stories = DataSource.getStories();

        storyAdapter =
                new StoryAdapter(
                        stories,
                        story -> {

                            Intent intent =
                                    new Intent(
                                            HomeActivity.this,
                                            DetailStoryActivity.class);

                            intent.putExtra("story", story);

                            startActivity(intent);

                        });

        rvStories.setAdapter(storyAdapter);

        rvHomeFeed =
                findViewById(R.id.rv_home_feed);

        rvHomeFeed.setLayoutManager(
                new LinearLayoutManager(this));

        posts =
                DataSource.getHomeFeed();

        feedAdapter =
                new FeedAdapter(
                        this,
                        posts,
                        post -> {

                            Intent intent =
                                    new Intent(
                                            HomeActivity.this,
                                            ProfileActivity.class);

                            intent.putExtra(
                                    "username",
                                    post.getUsername());

                            intent.putExtra(
                                    "profileImage",
                                    post.getProfileImage());

                            startActivity(intent);

                        });

        rvHomeFeed.setAdapter(feedAdapter);
    }
}