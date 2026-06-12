# 🎬 CineTracker

Aplikasi Android untuk menemukan dan melacak film favorit kamu. Dibangun menggunakan Java dan Android SDK dengan data dari TMDB API.


## ✨ Fitur

- **Daftar Film Populer** — Menampilkan film populer terkini dari TMDB API
- **Search Film** — Cari film berdasarkan judul secara real-time
- **Sort Film** — Urutkan film berdasarkan rating tertinggi atau A–Z
- **Detail Film** — Lihat poster, rating, genre, tahun rilis, dan sinopsis lengkap
- **Watchlist** — Simpan film favorit ke watchlist lokal
- **Status Tonton** — Tandai film sebagai Belum Ditonton / Sedang Ditonton / Sudah Ditonton
- **Bagikan Film** — Share info film ke aplikasi lain (WhatsApp, Telegram, dll)
- **Offline Mode** — Menampilkan data film terakhir saat tidak ada koneksi internet
- **Pull to Refresh** — Tarik layar ke bawah untuk memperbarui data film
- **Dark / Light Theme** — Dukung dua tema tampilan yang bisa diubah kapan saja

---

## 🛠️ Teknologi yang Digunakan

| Teknologi | Kegunaan |
|---|---|
| Java | Bahasa pemrograman utama |
| Retrofit 2 | Mengambil data dari TMDB API |
| Gson | Parsing data JSON |
| Glide | Menampilkan gambar poster film |
| SQLite | Menyimpan watchlist secara lokal |
| SharedPreferences | Menyimpan preferensi tema dan cache film |
| Navigation Component | Navigasi antar fragment |
| Material Design 3 | Komponen UI (BottomSheet, MaterialButton, dll) |
| SwipeRefreshLayout | Pull to refresh |

---

## 🏗️ Arsitektur & Implementasi Teknis

### Activity
- `SplashActivity` — Launcher activity dengan animasi logo
- `MainActivity` — Activity utama yang menampung Navigation Host

### Fragment
- `HomeFragment` — Menampilkan daftar film populer dari API
- `DetailFragment` — Detail film lengkap dengan tombol watchlist dan share
- `WatchlistFragment` — Daftar film yang disimpan dengan fitur search, sort, dan status

### Networking (Retrofit)
Data film diambil dari [TMDB API](https://www.themoviedb.org/documentation/api) endpoint `movie/popular` menggunakan Retrofit dengan converter Gson. Terdapat mekanisme cache offline menggunakan SharedPreferences sehingga data tetap tampil saat tidak ada koneksi.

### Local Storage (SQLite)
Watchlist disimpan di database SQLite lokal melalui `DatabaseHelper`. Tabel `watchlist` menyimpan judul, overview, poster, rating, dan status tonton. Mendukung operasi insert, delete, update status, dan query.

### Background Thread
Operasi database (insert, delete, update, query) dijalankan di background thread menggunakan `ExecutorService` dan hasilnya dikirim ke main thread menggunakan `Handler`.

### Tema
Dua tema (dark/light) diimplementasikan menggunakan `AppCompatDelegate` dengan `DayNight` theme. Warna tema disimpan di `values/colors.xml` (light) dan `values-night/colors.xml` (dark). Preferensi tema disimpan di SharedPreferences.

---

## 🚀 Cara Menjalankan

### Prasyarat
- Android Studio Hedgehog atau lebih baru
- Android SDK 24+
- Koneksi internet untuk mengambil data film

### Langkah
1. Clone repository ini
   ```bash
   git clone https://github.com/kevinalgazali1/Praktikum-Mobile-3.git
   ```
2. Buka project di Android Studio
3. Tunggu Gradle sync selesai
4. Jalankan di emulator atau perangkat fisik

---

## 📦 Download APK


---

## 🔑 API

Aplikasi ini menggunakan [TMDB API](https://www.themoviedb.org/documentation/api).

---

## 👤 Author

**Shabrina Zahrah Ramadhani**

---

## 📄 Lisensi

Project ini dibuat untuk keperluan Tugas Final Lab Mobile 2026.