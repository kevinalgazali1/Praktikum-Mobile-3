package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    ImageView ivEditProfilePic, btnBack;
    TextView tvEditPhoto, btnSimpan;
    EditText etNama, etUsername, etBio;
    Uri selectedImageUri;

    ActivityResultLauncher<Intent> openGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        selectedImageUri = data.getData();
                        ivEditProfilePic.setImageURI(selectedImageUri);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ivEditProfilePic = findViewById(R.id.iv_edit_profile_pic);
        tvEditPhoto = findViewById(R.id.tv_edit_photo);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnBack = findViewById(R.id.btn_back);
        etNama = findViewById(R.id.et_nama);
        etUsername = findViewById(R.id.et_username);
        etBio = findViewById(R.id.et_bio);

        Intent intent = getIntent();
        if (intent != null) {
            etNama.setText(intent.getStringExtra("EXTRA_NAMA"));
            etUsername.setText(intent.getStringExtra("EXTRA_USERNAME"));
            etBio.setText(intent.getStringExtra("EXTRA_BIO"));
            
            String imageUriString = intent.getStringExtra("EXTRA_IMAGE_URI");
            if (imageUriString != null) {
                selectedImageUri = Uri.parse(imageUriString);
                ivEditProfilePic.setImageURI(selectedImageUri);
            }
        }

        View.OnClickListener ubahFotoListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukaGaleri = new Intent(Intent.ACTION_GET_CONTENT);
                bukaGaleri.setType("image/*");
                openGallery.launch(Intent.createChooser(bukaGaleri, "Pilih Gambar"));
            }
        };

        ivEditProfilePic.setOnClickListener(ubahFotoListener);
        tvEditPhoto.setOnClickListener(ubahFotoListener);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("EXTRA_NAMA", etNama.getText().toString());
                resultIntent.putExtra("EXTRA_USERNAME", etUsername.getText().toString());
                resultIntent.putExtra("EXTRA_BIO", etBio.getText().toString());

                if (selectedImageUri != null) {
                    resultIntent.putExtra("EXTRA_IMAGE_URI", selectedImageUri.toString());
                }

                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
