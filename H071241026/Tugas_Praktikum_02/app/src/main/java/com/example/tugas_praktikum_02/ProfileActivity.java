package com.example.tugas_praktikum_02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugas_praktikum_02.adapter.ProfileFeedAdapter;
import com.example.tugas_praktikum_02.adapter.StoryAdapter;
import com.example.tugas_praktikum_02.model.Post;
import com.example.tugas_praktikum_02.model.Story;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    public static ArrayList<Post> profilePosts;

    private RecyclerView rvHighlights;
    private RecyclerView rvProfileFeed;

    private ProfileFeedAdapter profileFeedAdapter;

    private TextView tvPostCount;
    private TextView tvProfileName;

    private CircleImageView ivProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (profilePosts == null) {
            profilePosts = DataSource.getProfileFeed();
        }

        String username =
                getIntent().getStringExtra("username");

        int profileImg =
                getIntent().getIntExtra(
                        "profileImage",
                        R.drawable.adel);

        ivProfilePic =
                findViewById(R.id.iv_profile_pic);

        tvProfileName =
                findViewById(R.id.tv_profile_name);

        tvPostCount =
                findViewById(R.id.tv_post_count);

        rvHighlights =
                findViewById(R.id.rv_highlights);

        rvProfileFeed =
                findViewById(R.id.rv_profile_feed);

        // set foto profile
        ivProfilePic.setImageResource(profileImg);

        // set username
        if (username != null) {
            tvProfileName.setText("@" + username);
        }

        tvPostCount.setText(
                String.valueOf(profilePosts.size()));

        rvHighlights.setLayoutManager(
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false));

        StoryAdapter storyAdapter =
                new StoryAdapter(
                        DataSource.getHighlights(),
                        story -> {

                            Intent intent =
                                    new Intent(
                                            ProfileActivity.this,
                                            DetailStoryActivity.class);

                            intent.putExtra("story", story);

                            startActivity(intent);

                        });

        rvHighlights.setAdapter(storyAdapter);

        // GRID PROFILE POST

        rvProfileFeed.setLayoutManager(
                new GridLayoutManager(this, 3));

        profileFeedAdapter =
                new ProfileFeedAdapter(
                        profilePosts,
                        post -> {

                            Intent intent =
                                    new Intent(
                                            ProfileActivity.this,
                                            DetailFeedActivity.class);

                            intent.putExtra("post", post);

                            startActivity(intent);

                        });

        rvProfileFeed.setAdapter(profileFeedAdapter);

    }

    @Override
    protected void onResume() {

        super.onResume();

        if (profileFeedAdapter != null) {

            profileFeedAdapter.notifyDataSetChanged();

            tvPostCount.setText(
                    String.valueOf(profilePosts.size()));

        }

    }

}