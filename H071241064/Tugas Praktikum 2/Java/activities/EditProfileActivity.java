package com.example.instagramapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.instagramapp.R;
import com.example.instagramapp.models.DataManager;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ImageView ivBack     = findViewById(R.id.ivBack);
        EditText etUsername  = findViewById(R.id.etUsername);
        EditText etFullName  = findViewById(R.id.etFullName);
        EditText etBio       = findViewById(R.id.etBio);
        Button btnSave       = findViewById(R.id.btnSave);

        etUsername.setText(DataManager.getInstance().getMyUsername());
        etFullName.setText(DataManager.getInstance().getMyFullName());
        etBio.setText(DataManager.getInstance().getMyBio());

        ivBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String newUsername = etUsername.getText().toString().trim();
            String newFullName = etFullName.getText().toString().trim();
            String newBio      = etBio.getText().toString().trim();

            if (newUsername.isEmpty()) {
                etUsername.setError("Username tidak boleh kosong");
                return;
            }

            DataManager.getInstance().setMyUsername(newUsername);
            DataManager.getInstance().setMyFullName(newFullName);
            DataManager.getInstance().setMyBio(newBio);

            Toast.makeText(this, "Profil berhasil disimpan", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        });
    }
}