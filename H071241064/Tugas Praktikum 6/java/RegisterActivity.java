package com.example.studentportal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNim, etPassword;
    private Button btnRegister;
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
        setContentView(R.layout.activity_register);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        etNim = findViewById(R.id.etNim);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnRegister.setOnClickListener(v -> attemptRegister());
    }

    private void attemptRegister() {
        String nim = etNim.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(nim)) {
            etNim.setError("NIM wajib diisi");
            etNim.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password wajib diisi");
            etPassword.requestFocus();
            return;
        }
        if (password.length() < 4) {
            etPassword.setError("Password minimal 4 karakter");
            etPassword.requestFocus();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nim", nim);
        editor.putString("password", password);
        editor.apply();

        Toast.makeText(this, "Registrasi berhasil! Silakan login.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}