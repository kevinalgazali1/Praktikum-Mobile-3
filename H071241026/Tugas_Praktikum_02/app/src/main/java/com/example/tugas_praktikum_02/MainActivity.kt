package com.example.tugas_praktikum_02

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Langsung pindah ke HomeActivity
        val intent =
            Intent(
                this@MainActivity,
                HomeActivity::class.java
            )

        startActivity(intent)

        // Tutup MainActivity supaya tidak balik lagi
        finish()
    }
}