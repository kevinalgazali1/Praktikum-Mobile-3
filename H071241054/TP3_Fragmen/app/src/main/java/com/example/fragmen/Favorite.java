package com.example.fragmen;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Favorite extends Fragment {

    private RecyclerView rvFavorites;
    private TextView tvEmptyFavorite;
    private BookAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvFavorites = view.findViewById(R.id.rvFavorites);
        tvEmptyFavorite = view.findViewById(R.id.tvEmptyFavorite);
        
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new BookAdapter(getContext(), DetailActivity.favoriteList);
        rvFavorites.setAdapter(adapter);

        updateVisibility();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.setListBuku(DetailActivity.favoriteList);
        }
        updateVisibility();
    }

    private void updateVisibility() {
        if (DetailActivity.favoriteList.isEmpty()) {
            rvFavorites.setVisibility(View.GONE);
            tvEmptyFavorite.setVisibility(View.VISIBLE);
        } else {
            rvFavorites.setVisibility(View.VISIBLE);
            tvEmptyFavorite.setVisibility(View.GONE);
        }
    }
}
