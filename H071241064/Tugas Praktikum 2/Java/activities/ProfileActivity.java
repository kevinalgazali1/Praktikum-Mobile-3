package com.example.instagramapp.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramapp.R;
import com.example.instagramapp.adapters.GridPostAdapter;
import com.example.instagramapp.adapters.HighlightAdapter;
import com.example.instagramapp.models.DataManager;
import com.example.instagramapp.models.Post;
import com.example.instagramapp.models.Story;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private GridPostAdapter gridPostAdapter;
    private List<Post> currentPosts;
    private boolean isMyProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        isMyProfile = getIntent().getBooleanExtra("isMyProfile", false);
        String username = getIntent().getStringExtra("username");
        String avatar   = getIntent().getStringExtra("avatar");

        ImageView ivBack = findViewById(R.id.ivBack);
        TextView tvToolbarUsername = findViewById(R.id.tvToolbarUsername);
        com.google.android.material.imageview.ShapeableImageView ivProfileAvatar =
                findViewById(R.id.ivProfileAvatar);
        TextView tvFullName  = findViewById(R.id.tvFullName);
        TextView tvBio       = findViewById(R.id.tvBio);
        TextView tvPosts     = findViewById(R.id.tvPosts);
        TextView tvFollowers = findViewById(R.id.tvFollowers);
        TextView tvFollowing = findViewById(R.id.tvFollowing);
        Button btnLeft  = findViewById(R.id.btnLeft);
        Button btnRight = findViewById(R.id.btnRight);

        ivBack.setOnClickListener(v -> finish());

        if (isMyProfile) {
            String savedUsername = DataManager.getInstance().getMyUsername();
            String savedFullName = DataManager.getInstance().getMyFullName();
            String savedBio      = DataManager.getInstance().getMyBio();

            tvToolbarUsername.setText(savedUsername);
            ivProfileAvatar.setImageResource(R.drawable.avatar_me);
            tvFullName.setText(savedFullName);
            tvBio.setText(savedBio);
            tvPosts.setText(String.valueOf(DataManager.getInstance().getMyProfilePosts().size()));
            tvFollowers.setText("1.2K");
            tvFollowing.setText("345");

            btnLeft.setText("Edit Profile");
            btnRight.setText("Share Profile");
            btnLeft.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EFEFEF")));
            btnRight.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EFEFEF")));
            btnLeft.setTextColor(Color.BLACK);
            btnRight.setTextColor(Color.BLACK);

            btnLeft.setOnClickListener(v -> {
                Intent intent = new Intent(this, EditProfileActivity.class);
                startActivityForResult(intent, 100);
            });
            btnRight.setOnClickListener(v ->
                    Toast.makeText(this, "Share Profile", Toast.LENGTH_SHORT).show());

            currentPosts = DataManager.getInstance().getMyProfilePosts();

            ivProfileAvatar.setOnClickListener(v -> openMyStory());

        } else {
            tvToolbarUsername.setText(username != null ? username : "user");
            int avatarResId = 0;
            if (avatar != null) {
                avatarResId = getResources().getIdentifier(avatar, "drawable", getPackageName());
            }
            ivProfileAvatar.setImageResource(
                    avatarResId != 0 ? avatarResId : R.drawable.placeholder_avatar);
            tvFullName.setText(username != null ? username : "User");
            tvBio.setText("Based in Jakarta\n Living every moment");
            tvPosts.setText("5");
            tvFollowers.setText("800");
            tvFollowing.setText("200");

            btnLeft.setText("Follow");
            btnLeft.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0095F6")));
            btnLeft.setTextColor(Color.WHITE);
            btnRight.setText("Message");
            btnRight.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EFEFEF")));
            btnRight.setTextColor(Color.BLACK);

            final boolean[] isFollowing = {false};
            btnLeft.setOnClickListener(v -> {
                isFollowing[0] = !isFollowing[0];
                if (isFollowing[0]) {
                    btnLeft.setText("Following");
                    btnLeft.setBackgroundTintList(
                            ColorStateList.valueOf(Color.parseColor("#EFEFEF")));
                    btnLeft.setTextColor(Color.BLACK);
                } else {
                    btnLeft.setText("Follow");
                    btnLeft.setBackgroundTintList(
                            ColorStateList.valueOf(Color.parseColor("#0095F6")));
                    btnLeft.setTextColor(Color.WHITE);
                }
            });
            btnRight.setOnClickListener(v ->
                    Toast.makeText(this, "Message " + username, Toast.LENGTH_SHORT).show());

            currentPosts = DataManager.getInstance().getPostsByUser(username, avatar);

            final String finalUsername = username;
            final String finalAvatar   = avatar;
            ivProfileAvatar.setOnClickListener(v -> openOtherStory(finalUsername, finalAvatar));
        }

        RecyclerView rvStories = findViewById(R.id.rvStories);
        rvStories.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvStories.setAdapter(
                new HighlightAdapter(this, DataManager.getInstance().getMyStories()));

        RecyclerView rvGrid = findViewById(R.id.rvGrid);
        rvGrid.setLayoutManager(new GridLayoutManager(this, 3));
        gridPostAdapter = new GridPostAdapter(
                this, currentPosts, username != null ? username : "myusername");
        rvGrid.setAdapter(gridPostAdapter);

        findViewById(R.id.ivNavHome).setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        findViewById(R.id.ivNavPost).setOnClickListener(v ->
                startActivity(new Intent(this, PostActivity.class)));
        findViewById(R.id.ivNavProfile).setOnClickListener(v -> {
            if (!isMyProfile) {
                Intent intent = new Intent(this, ProfileActivity.class);
                intent.putExtra("isMyProfile", true);
                startActivity(intent);
            }
        });
    }

    private void openMyStory() {
        List<Story> myStories = DataManager.getInstance().getMyStories();
        if (myStories == null || myStories.isEmpty()) {
            Toast.makeText(this, "No story", Toast.LENGTH_SHORT).show();
            return;
        }
        Story s = myStories.get(0);
        Intent intent = new Intent(this, DetailStoryActivity.class);
        intent.putExtra("title", s.getTitle());
        intent.putExtra("coverImage", s.getCoverImage());
        intent.putExtra("content", s.getContent());
        startActivity(intent);
    }

    private void openOtherStory(String username, String avatar) {
        Intent intent = new Intent(this, DetailStoryActivity.class);
        intent.putExtra("title", username != null ? username : "user");
        intent.putExtra("coverImage", avatar != null ? avatar : "placeholder_avatar");
        intent.putExtra("content", "");
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            TextView tvToolbarUsername = findViewById(R.id.tvToolbarUsername);
            TextView tvFullName = findViewById(R.id.tvFullName);
            TextView tvBio      = findViewById(R.id.tvBio);
            tvToolbarUsername.setText(DataManager.getInstance().getMyUsername());
            tvFullName.setText(DataManager.getInstance().getMyFullName());
            tvBio.setText(DataManager.getInstance().getMyBio());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isMyProfile) {
            gridPostAdapter.notifyDataSetChanged();
            TextView tvPosts = findViewById(R.id.tvPosts);
            tvPosts.setText(String.valueOf(
                    DataManager.getInstance().getMyProfilePosts().size()));
            TextView tvToolbarUsername = findViewById(R.id.tvToolbarUsername);
            TextView tvFullName = findViewById(R.id.tvFullName);
            TextView tvBio      = findViewById(R.id.tvBio);
            tvToolbarUsername.setText(DataManager.getInstance().getMyUsername());
            tvFullName.setText(DataManager.getInstance().getMyFullName());
            tvBio.setText(DataManager.getInstance().getMyBio());
        }
    }
}