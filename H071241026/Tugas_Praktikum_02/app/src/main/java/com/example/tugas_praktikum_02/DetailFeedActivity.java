package com.example.tugas_praktikum_02;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tugas_praktikum_02.model.Post;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feed);

        Post post =
                getIntent().getParcelableExtra("post");

        if (post != null) {

            CircleImageView ivDetailProfile =
                    findViewById(R.id.iv_detail_profile);

            TextView tvDetailUsername =
                    findViewById(R.id.tv_detail_username);

            ImageView ivDetailPost =
                    findViewById(R.id.iv_detail_post);

            TextView tvDetailCaption =
                    findViewById(R.id.tv_detail_caption);

            ivDetailProfile.setImageResource(
                    post.getProfileImage());

            tvDetailUsername.setText(
                    post.getUsername());

            ivDetailPost.setImageResource(
                    post.getPostImage());

            tvDetailCaption.setText(
                    post.getCaption());
        }
    }
}