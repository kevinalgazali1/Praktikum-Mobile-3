package com.example.libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private TextView tvEmpty;
    private ProgressBar progressBar;

    // TP4: ExecutorService untuk menjalankan tugas di background thread
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    // TP4: Handler untuk kembali ke Main Thread setelah background selesai
    private Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFav);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        progressBar = view.findViewById(R.id.progressBarFav); // TP4: inisialisasi ProgressBar

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadFavorites();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Perbarui daftar setiap kali fragment ditampilkan
        loadFavorites();
    }

    private void loadFavorites() {
        // TP4: Tampilkan ProgressBar saat background thread mulai berjalan
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.GONE);

        // TP4: Jalankan proses load data di background thread
        executor.execute(() -> {
            List<Book> favorites = BookRepository.getInstance().getFavoriteBooks();

            // Simulasi delay agar ProgressBar terlihat
            try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }

            // TP4: Kembali ke Main Thread untuk update UI
            handler.post(() -> {
                // TP4: Sembunyikan ProgressBar setelah data selesai dimuat
                progressBar.setVisibility(View.GONE);

                if (favorites.isEmpty()) {
                    tvEmpty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    tvEmpty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

                adapter = new BookAdapter(getContext(), favorites, book -> {
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra("book_id", book.getId());
                    startActivity(intent);
                });
                recyclerView.setAdapter(adapter);
            });
        });
    }
}