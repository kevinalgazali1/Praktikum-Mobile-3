package com.example.tp1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    EditText edtName, edtUsername, edtBio;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        edtName = findViewById(R.id.edtName);
        edtUsername = findViewById(R.id.edtUsername);
        edtBio = findViewById(R.id.edtBio);
        btnSave = findViewById(R.id.btnSave);

        Intent intent = getIntent();

        edtName.setText(intent.getStringExtra("name"));
        edtUsername.setText(intent.getStringExtra("username"));
        edtBio.setText(intent.getStringExtra("bio"));

        btnSave.setOnClickListener(v -> {

            Intent resultIntent = new Intent();

            resultIntent.putExtra("name", edtName.getText().toString());
            resultIntent.putExtra("username", edtUsername.getText().toString());
            resultIntent.putExtra("bio", edtBio.getText().toString());

            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}