package com.example.igclone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvWelcome;
    Button btnLogout, btnSetting;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences(LoginActivity.PREF_NAME, Context.MODE_PRIVATE);

        tvWelcome  = findViewById(R.id.tvWelcome);
        btnLogout  = findViewById(R.id.btnLogout);
        btnSetting = findViewById(R.id.btnSetting);

        // Ambil NIM dari SharedPreferences
        String nim = prefs.getString(LoginActivity.KEY_NIM, "User");
        tvWelcome.setText("Selamat datang " + nim);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set isLoggedIn = false di SharedPreferences
                prefs.edit().putBoolean(LoginActivity.KEY_LOGIN, false).apply();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });
    }
}
