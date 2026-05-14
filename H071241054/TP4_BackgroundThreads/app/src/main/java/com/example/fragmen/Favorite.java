package com.example.fragmen;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Favorite extends Fragment {

    private RecyclerView rvFavorites;
    private TextView tvEmptyFavorite;
    private ProgressBar progressBar;
    private BookAdapter adapter;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvFavorites = view.findViewById(R.id.rvFavorites);
        tvEmptyFavorite = view.findViewById(R.id.tvEmptyFavorite);
        progressBar = view.findViewById(R.id.progressBarFavorite);
        
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new BookAdapter(getContext(), DetailActivity.favoriteList);
        rvFavorites.setAdapter(adapter);

        loadFavoriteBooks();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavoriteBooks();
    }

    private void loadFavoriteBooks() {
        progressBar.setVisibility(View.VISIBLE);
        rvFavorites.setVisibility(View.GONE);
        tvEmptyFavorite.setVisibility(View.GONE);

        executor.execute(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            handler.post(() -> {
                if (adapter != null) {
                    adapter.setListBuku(DetailActivity.favoriteList);
                }
                
                progressBar.setVisibility(View.GONE);
                
                if (DetailActivity.favoriteList.isEmpty()) {
                    rvFavorites.setVisibility(View.GONE);
                    tvEmptyFavorite.setVisibility(View.VISIBLE);
                } else {
                    rvFavorites.setVisibility(View.VISIBLE);
                    tvEmptyFavorite.setVisibility(View.GONE);
                }
            });
        });
    }
}
