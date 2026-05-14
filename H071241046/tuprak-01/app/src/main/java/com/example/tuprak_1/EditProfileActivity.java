package com.example.tuprak_1; //

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etName, etUsername, etBio;
    private ImageView ivEditPhoto;
    private TextView tvChangePhoto;
    private Button btnSave;

    private Uri selectedPhotoUri;

    private final ActivityResultLauncher<String> getContentLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedPhotoUri = uri;
                    ivEditPhoto.setImageURI(uri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etName = findViewById(R.id.et_edit_fullname);
        etUsername = findViewById(R.id.et_edit_username);
        etBio = findViewById(R.id.et_edit_bio);
        ivEditPhoto = findViewById(R.id.iv_edit_profile_photo);
        tvChangePhoto = findViewById(R.id.tv_change_photo_action);
        btnSave = findViewById(R.id.btn_save_edit_changes);

        etName.setText(getIntent().getStringExtra("CURRENT_NAME"));
        etUsername.setText(getIntent().getStringExtra("CURRENT_USERNAME"));
        etBio.setText(getIntent().getStringExtra("CURRENT_BIO"));

        tvChangePhoto.setOnClickListener(v -> {
            getContentLauncher.launch("image/*");
        });

        btnSave.setOnClickListener(v -> {
            String newName = etName.getText().toString();
            String newUsername = etUsername.getText().toString();
            String newBio = etBio.getText().toString();

            if (newUsername.isEmpty()) {
                Toast.makeText(this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent returnIntent = new Intent();
            returnIntent.putExtra("KEY_NAME", newName);
            returnIntent.putExtra("KEY_USERNAME", newUsername);
            returnIntent.putExtra("KEY_BIO", newBio);

            if (selectedPhotoUri != null) {
                returnIntent.putExtra("KEY_PHOTO_URI", selectedPhotoUri.toString());
            }

            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });
    }
}