package com.example.studentportal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etNim, etPassword;
    private Button btnLogin, btnRegister;
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

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        if (sharedPreferences.getBoolean("is_logged_in", false)) {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        etNim = findViewById(R.id.etNim);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> attemptLogin());
        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void attemptLogin() {
        String nim = etNim.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(nim) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "NIM dan Password wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        String savedNim = sharedPreferences.getString("nim", "");
        String savedPassword = sharedPreferences.getString("password", "");

        if (nim.equals(savedNim) && password.equals(savedPassword)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("is_logged_in", true);
            editor.apply();

            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "NIM atau Password salah", Toast.LENGTH_SHORT).show();
        }
    }
}