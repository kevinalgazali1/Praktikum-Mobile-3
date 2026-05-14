package com.example.tugas_praktikum_02;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tugas_praktikum_02.model.Post;

public class PostActivity extends AppCompatActivity {

    private ImageView ivPreview;
    private EditText etCaption;

    private int selectedImage =
            R.drawable.feed1; // default image

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ivPreview =
                findViewById(R.id.iv_preview);

        etCaption =
                findViewById(R.id.et_caption);

        Button btnUpload =
                findViewById(R.id.btn_upload);

        // tampilkan preview default

        ivPreview.setImageResource(
                selectedImage);

        // upload post

        btnUpload.setOnClickListener(v -> {

            String caption =
                    etCaption.getText().toString();

            if (!caption.isEmpty()) {

                Post newPost =
                        new Post(
                                "Adel",
                                R.drawable.adel,
                                selectedImage,
                                caption
                        );

                ProfileActivity.profilePosts
                        .add(0, newPost);

                finish();

            }

        });

    }

}