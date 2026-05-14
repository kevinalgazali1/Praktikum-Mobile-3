package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class DetailFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feed);

        ImageView btnBack = findViewById(R.id.btn_back);
        ImageView ivProfile = findViewById(R.id.ivProfileUser);
        ImageView ivPost = findViewById(R.id.ivPostImage);
        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvCaption = findViewById(R.id.tvCaption);

        int profileImage = getIntent().getIntExtra("PROFILE_IMG", R.drawable.ic_launcher_background);
        String username = getIntent().getStringExtra("USERNAME");
        String caption = getIntent().getStringExtra("CAPTION");
        
        ivProfile.setImageResource(profileImage);
        tvUsername.setText(username);
        tvCaption.setText(username + " " + caption);

        if (getIntent().hasExtra("POST_IMG_URI")) {
            String uriString = getIntent().getStringExtra("POST_IMG_URI");
            Uri imageUri = Uri.parse(uriString);
            
            ivPost.setImageURI(imageUri);
            
        } else {
            int postImage = getIntent().getIntExtra("POST_IMG", R.drawable.ic_launcher_background);
            ivPost.setImageResource(postImage);
        }

        tvUsername.setTextColor(getResources().getColor(android.R.color.white));
        tvCaption.setTextColor(getResources().getColor(android.R.color.white));

        btnBack.setOnClickListener(v -> finish());
    }
}
