package com.example.instagramapp.activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.instagramapp.R;

public class DetailPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        String imageRes = getIntent().getStringExtra("imageRes");
        String username = getIntent().getStringExtra("username");
        String caption = getIntent().getStringExtra("caption");
        int likes = getIntent().getIntExtra("likes", 0);
        String timeAgo = getIntent().getStringExtra("timeAgo");
        String avatar = getIntent().getStringExtra("avatar");

        findViewById(R.id.ivBack).setOnClickListener(v -> finish());

        com.google.android.material.imageview.ShapeableImageView ivAvatar =
                findViewById(R.id.ivAvatar);
        ImageView ivPost = findViewById(R.id.ivPost);
        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvCaptionUsername = findViewById(R.id.tvCaptionUsername);
        TextView tvCaption = findViewById(R.id.tvCaption);
        TextView tvLikes = findViewById(R.id.tvLikes);
        TextView tvTime = findViewById(R.id.tvTime);
        ImageView ivLike = findViewById(R.id.ivLike);
        EditText etComment = findViewById(R.id.etComment);
        TextView tvSend = findViewById(R.id.tvSend);

        tvUsername.setText(username);
        tvCaptionUsername.setText(username);
        tvCaption.setText(caption);
        tvLikes.setText(likes + " likes");
        tvTime.setText(timeAgo);

        if (avatar != null) {
            int avatarResId = getResources().getIdentifier(avatar, "drawable", getPackageName());
            ivAvatar.setImageResource(avatarResId != 0 ? avatarResId : R.drawable.placeholder_avatar);
        }

        if (imageRes != null) {
            if (imageRes.startsWith("content://") || imageRes.startsWith("file://")) {
                ivPost.setImageURI(Uri.parse(imageRes));
            } else {
                int resId = getResources().getIdentifier(imageRes, "drawable", getPackageName());
                ivPost.setImageResource(resId != 0 ? resId : R.drawable.placeholder_post);
            }
        }

        ivLike.setOnClickListener(v ->
                ivLike.setImageResource(R.drawable.ic_heart_filled));

        tvSend.setOnClickListener(v -> sendComment(etComment));

        etComment.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendComment(etComment);
                return true;
            }
            return false;
        });
    }

    private void sendComment(EditText etComment) {
        String comment = etComment.getText().toString().trim();
        if (!comment.isEmpty()) {
            Toast.makeText(this, "Comment posted: " + comment, Toast.LENGTH_SHORT).show();
            etComment.setText("");
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etComment.getWindowToken(), 0);
        } else {
            Toast.makeText(this, "Write a comment first!", Toast.LENGTH_SHORT).show();
        }
    }
}