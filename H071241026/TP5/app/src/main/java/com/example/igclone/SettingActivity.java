package com.example.igclone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingActivity extends AppCompatActivity {

    public static final String KEY_DARK_MODE = "darkMode";

    ImageButton btnBack;
    TextView tvNimSetting, tvLogout, tvSpData;
    SwitchMaterial switchDark;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        prefs = getSharedPreferences(LoginActivity.PREF_NAME, Context.MODE_PRIVATE);

        btnBack      = findViewById(R.id.btnBack);
        tvNimSetting = findViewById(R.id.tvNimSetting);
        switchDark   = findViewById(R.id.switchDarkMode);
        tvLogout     = findViewById(R.id.tvLogoutSetting);
        tvSpData     = findViewById(R.id.tvSharedPrefsData);

        // Baca data dari SharedPreferences
        String nim      = prefs.getString(LoginActivity.KEY_NIM, "—");
        String nama     = prefs.getString(LoginActivity.KEY_NAMA, "—");
        boolean isDark  = prefs.getBoolean(KEY_DARK_MODE, true);
        boolean isLogin = prefs.getBoolean(LoginActivity.KEY_LOGIN, false);

        tvNimSetting.setText("NIM: " + nim + "  |  Nama: " + nama);

        // Tampilkan isi SharedPreferences
        updateSpDisplay(nim, nama, isLogin, isDark);

        // Set posisi switch sesuai nilai tersimpan
        switchDark.setChecked(isDark);

        switchDark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Simpan preferensi dark mode ke SharedPreferences
                prefs.edit().putBoolean(KEY_DARK_MODE, isChecked).apply();

                // Terapkan tema
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

                // Update tampilan data SP
                String n  = prefs.getString(LoginActivity.KEY_NIM, "—");
                String nm = prefs.getString(LoginActivity.KEY_NAMA, "—");
                boolean il = prefs.getBoolean(LoginActivity.KEY_LOGIN, false);
                updateSpDisplay(n, nm, il, isChecked);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().putBoolean(LoginActivity.KEY_LOGIN, false).apply();
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateSpDisplay(String nim, String nama, boolean isLogin, boolean isDark) {
        String data = "[SharedPreferences: IGClonePrefs]\n"
                + "├─ nim        = \"" + nim + "\"\n"
                + "├─ nama       = \"" + nama + "\"\n"
                + "├─ isLoggedIn = " + isLogin + "\n"
                + "└─ darkMode   = " + isDark;
        tvSpData.setText(data);
    }
}
