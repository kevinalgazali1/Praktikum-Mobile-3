package com.example.tp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView txtDisplayName, txtUsername, txtBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        txtDisplayName = findViewById(R.id.txtDisplayName);
        txtUsername = findViewById(R.id.txtUsername);
        txtBio = findViewById(R.id.txtBio);

        // Default data awal
        txtDisplayName.setText("nac");
        txtUsername.setText("username");
        txtBio.setText("bio kosong");
    }

    public void openEditProfile(View view) {

        Intent intent = new Intent(this, EditProfileActivity.class);

        intent.putExtra("name", txtDisplayName.getText().toString());
        intent.putExtra("username", txtUsername.getText().toString());
        intent.putExtra("bio", txtBio.getText().toString());

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            String name = data.getStringExtra("name");
            String username = data.getStringExtra("username");
            String bio = data.getStringExtra("bio");

            txtDisplayName.setText(name);
            txtUsername.setText(username);
            txtBio.setText(bio);
        }
    }
}