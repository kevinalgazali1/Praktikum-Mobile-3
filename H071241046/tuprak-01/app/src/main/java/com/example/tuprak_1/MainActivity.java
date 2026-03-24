package com.example.tuprak_1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvUsername, tvFullName, tvBio;
    private ImageView ivProfilePhoto;
    private Button btnToEdit;

    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();

                    String updatedName = data.getStringExtra("KEY_NAME");
                    String updatedUsername = data.getStringExtra("KEY_USERNAME");
                    String updatedBio = data.getStringExtra("KEY_BIO");

                    String photoUriStr = data.getStringExtra("KEY_PHOTO_URI");

                    tvFullName.setText(updatedName);
                    tvUsername.setText(updatedUsername);
                    tvBio.setText(updatedBio);

                    if (photoUriStr != null) {
                        Uri newPhotoUri = Uri.parse(photoUriStr);
                        ivProfilePhoto.setImageURI(newPhotoUri);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUsername = findViewById(R.id.tv_main_username);
        tvFullName = findViewById(R.id.tv_main_fullname);
        tvBio = findViewById(R.id.tv_main_bio);
        ivProfilePhoto = findViewById(R.id.iv_main_profile_photo);
        btnToEdit = findViewById(R.id.btn_to_edit_profile);

        btnToEdit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);

            intent.putExtra("CURRENT_NAME", tvFullName.getText().toString());
            intent.putExtra("CURRENT_USERNAME", tvUsername.getText().toString());
            intent.putExtra("CURRENT_BIO", tvBio.getText().toString());

            editProfileLauncher.launch(intent);
        });
    }
}