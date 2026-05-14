package com.example.instagramapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.instagramapp.R;
import com.example.instagramapp.models.DataManager;
import com.example.instagramapp.models.Post;

public class PostActivity extends AppCompatActivity {

    private ImageView ivPreview;
    private EditText etCaption;
    private Uri selectedImageUri;
    private boolean imageSelected = false;

    private final ActivityResultLauncher<Intent> galleryLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    ivPreview.setImageURI(selectedImageUri);
                    ivPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageSelected = true;
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> finish());

        ivPreview = findViewById(R.id.ivPreview);
        etCaption = findViewById(R.id.etCaption);
        Button btnSelectImage = findViewById(R.id.btnSelectImage);
        Button btnShare = findViewById(R.id.btnShare);

        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncher.launch(intent);
        });

        btnShare.setOnClickListener(v -> {
            String caption = etCaption.getText().toString().trim();

            if (!imageSelected) {
                Toast.makeText(this, "Please select an image first!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (caption.isEmpty()) {
                Toast.makeText(this, "Please write a caption!", Toast.LENGTH_SHORT).show();
                return;
            }

            Post newPost = new Post("myusername", "placeholder_post", caption);
            newPost.setImageRes(selectedImageUri.toString());

            DataManager.getInstance().addProfilePost(newPost);

            Toast.makeText(this, "Post shared successfully! ✅", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("isMyProfile", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        ImageView ivNavHome = findViewById(R.id.ivNavHome);
        ImageView ivNavProfile = findViewById(R.id.ivNavProfile);
        ImageView ivNavPost = findViewById(R.id.ivNavPost);

        ivNavHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        ivNavProfile.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("isMyProfile", true);
            startActivity(intent);
        });

        ivNavPost.setOnClickListener(v -> {
        });
    }
}