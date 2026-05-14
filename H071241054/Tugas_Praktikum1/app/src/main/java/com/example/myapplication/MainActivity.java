package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    CardView btnEditProfil;
    TextView tvNama, tvUsername, tvBio;
    ImageView ivProfilePic, ivBottomProfilePic;
    Uri currentImageUri;

    ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();

                        String namaBaru = data.getStringExtra("EXTRA_NAMA");
                        String usernameBaru = data.getStringExtra("EXTRA_USERNAME");
                        String bioBaru = data.getStringExtra("EXTRA_BIO");
                        String uriString = data.getStringExtra("EXTRA_IMAGE_URI");

                        if (namaBaru != null) tvNama.setText(namaBaru);
                        if (usernameBaru != null) tvUsername.setText(usernameBaru);
                        if (bioBaru != null) tvBio.setText(bioBaru);

                        if (uriString != null) {
                            currentImageUri = Uri.parse(uriString);
                            ivProfilePic.setImageURI(currentImageUri);
                            ivBottomProfilePic.setImageURI(currentImageUri);
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEditProfil = findViewById(R.id.btn_edit_profil);
        tvNama = findViewById(R.id.nama_akun);
        tvBio = findViewById(R.id.tv_bio);
        tvUsername = findViewById(R.id.tv_username_top);
        ivProfilePic = findViewById(R.id.iv_main_profile_pic);
        ivBottomProfilePic = findViewById(R.id.iv_bottom_profile_pic);

        btnEditProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                
                intent.putExtra("EXTRA_NAMA", tvNama.getText().toString());
                intent.putExtra("EXTRA_USERNAME", tvUsername.getText().toString());
                intent.putExtra("EXTRA_BIO", tvBio.getText().toString());
                
                if (currentImageUri != null) {
                    intent.putExtra("EXTRA_IMAGE_URI", currentImageUri.toString());
                }
                
                editProfileLauncher.launch(intent);
            }
        });
    }
}
