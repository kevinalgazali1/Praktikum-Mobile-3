package com.example.igclone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etNim, etNama, etPassword, etConfirm;
    Button btnRegister;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNim      = findViewById(R.id.etRegNim);
        etNama     = findViewById(R.id.etRegNama);
        etPassword = findViewById(R.id.etRegPassword);
        etConfirm  = findViewById(R.id.etRegConfirm);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin    = findViewById(R.id.tvSudahPunyaAkun);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nim      = etNim.getText().toString().trim();
                String nama     = etNama.getText().toString().trim();
                String password = etPassword.getText().toString();
                String confirm  = etConfirm.getText().toString();

                // Validasi
                if (nim.isEmpty() || nama.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Semua field wajib diisi!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (nim.length() < 3) {
                    Toast.makeText(RegisterActivity.this, "NIM minimal 3 karakter!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 4) {
                    Toast.makeText(RegisterActivity.this, "Password minimal 4 karakter!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirm)) {
                    Toast.makeText(RegisterActivity.this, "Konfirmasi password tidak cocok!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Simpan ke SharedPreferences
                SharedPreferences prefs = getSharedPreferences(LoginActivity.PREF_NAME, Context.MODE_PRIVATE);
                prefs.edit()
                    .putString(LoginActivity.KEY_NIM, nim)
                    .putString(LoginActivity.KEY_NAMA, nama)
                    .putString(LoginActivity.KEY_PASSWORD, password)
                    .putBoolean(LoginActivity.KEY_LOGIN, true)
                    .apply();

                Toast.makeText(RegisterActivity.this,
                    "Registrasi berhasil! Selamat datang " + nama, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
