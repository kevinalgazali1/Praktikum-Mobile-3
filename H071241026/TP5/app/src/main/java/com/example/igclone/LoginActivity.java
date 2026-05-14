package com.example.igclone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    // Konstanta SharedPreferences
    public static final String PREF_NAME    = "IGClonePrefs";
    public static final String KEY_NIM      = "nim";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NAMA     = "nama";
    public static final String KEY_LOGIN    = "isLoggedIn";

    TextInputEditText etNim, etPassword;
    Button btnLogin, btnRegister;
    TextView tvForgot;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Kalau sudah login, langsung ke MainActivity
        if (prefs.getBoolean(KEY_LOGIN, false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        etNim      = findViewById(R.id.etNim);
        etPassword = findViewById(R.id.etPassword);
        btnLogin   = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        tvForgot   = findViewById(R.id.tvForgot);

        // Tombol login disabled dulu
        btnLogin.setEnabled(false);
        btnLogin.setAlpha(0.5f);

        // Aktifkan tombol login kalau field sudah diisi
        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                boolean nimOk  = !etNim.getText().toString().isEmpty();
                boolean passOk = !etPassword.getText().toString().isEmpty();
                btnLogin.setEnabled(nimOk && passOk);
                btnLogin.setAlpha((nimOk && passOk) ? 1f : 0.5f);
            }
        };
        etNim.addTextChangedListener(watcher);
        etPassword.addTextChangedListener(watcher);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nim      = etNim.getText().toString().trim();
                String password = etPassword.getText().toString();

                // Baca dari SharedPreferences
                String savedNim  = prefs.getString(KEY_NIM, "");
                String savedPass = prefs.getString(KEY_PASSWORD, "");

                if (savedNim.isEmpty()) {
                    Toast.makeText(LoginActivity.this,
                        "Akun tidak ditemukan! Silakan Register dulu.", Toast.LENGTH_SHORT).show();
                } else if (nim.equals(savedNim) && password.equals(savedPass)) {
                    // Simpan status login
                    prefs.edit().putBoolean(KEY_LOGIN, true).apply();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else if (!nim.equals(savedNim)) {
                    Toast.makeText(LoginActivity.this, "NIM tidak ditemukan!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Password salah!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Hubungi admin untuk reset password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
