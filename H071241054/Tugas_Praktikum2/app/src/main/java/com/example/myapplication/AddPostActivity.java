package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AddPostActivity extends AppCompatActivity {

    private ImageView ivPreview, btnCancel;
    private TextView btnPost, tvPrompt;
    private EditText etCaption;
    private Uri selectedImageUri;

    private final ActivityResultLauncher<String> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    
                    ivPreview.setImageURI(uri);
                    
                    ivPreview.setColorFilter(null);
                    ivPreview.setImageTintList(null);
                    
                    ivPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    
                    // Sembunyikan teks petunjuk
                    if (tvPrompt != null) {
                        tvPrompt.setVisibility(View.GONE);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        ivPreview = findViewById(R.id.iv_preview);
        btnCancel = findViewById(R.id.btn_cancel);
        btnPost = findViewById(R.id.btn_post);
        etCaption = findViewById(R.id.et_caption);
        tvPrompt = findViewById(R.id.tv_prompt);

        View layoutSelectImage = findViewById(R.id.layout_select_image);
        if (layoutSelectImage != null) {
            layoutSelectImage.setOnClickListener(v -> pickImageLauncher.launch("image/*"));
        } else {
            ivPreview.setOnClickListener(v -> pickImageLauncher.launch("image/*"));
        }

        btnCancel.setOnClickListener(v -> finish());

        btnPost.setOnClickListener(v -> {
            String caption = etCaption.getText().toString();
            if (selectedImageUri == null) {
                Toast.makeText(this, "Silakan pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
                return;
            }

            String localPath = saveImageLocally(selectedImageUri);
            if (localPath == null) {
                Toast.makeText(this, "Gagal memproses gambar", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("EXTRA_CAPTION", caption);
            resultIntent.putExtra("EXTRA_IMAGE_URI", localPath);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }

    private String saveImageLocally(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File file = new File(getFilesDir(), "post_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.close();
            if (inputStream != null) inputStream.close();
            return Uri.fromFile(file).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
