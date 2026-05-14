package com.example.studentportal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences themePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        themePrefs = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        boolean isDark = themePrefs.getBoolean("dark_mode", false);
        if (isDark) {
            setTheme(R.style.Theme_StudentPortal_Dark);
        } else {
            setTheme(R.style.Theme_StudentPortal_Light);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        String nim = sharedPreferences.getString("nim", "Mahasiswa");

        TextView tvWelcome = findViewById(R.id.tvWelcome);
        TextView tvNim = findViewById(R.id.tvNim);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnSettings = findViewById(R.id.btnSettings);

        tvNim.setText(nim);

        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("is_logged_in", false);
            editor.apply();
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, SettingsActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isDark = themePrefs.getBoolean("dark_mode", false);
        boolean wasApplied = themePrefs.getBoolean("theme_applied", false);
        if (wasApplied) {
            SharedPreferences.Editor editor = themePrefs.edit();
            editor.putBoolean("theme_applied", false);
            editor.apply();
            recreate();
        }
    }
}