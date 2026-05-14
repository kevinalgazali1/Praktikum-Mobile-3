package com.example.libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private EditText searchView;
    private ChipGroup chipGroup;
    private ProgressBar progressBar;

    private List<Book> filteredList;
    private String currentQuery = "";
    private String currentGenre = "Semua";

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_books);
        searchView   = view.findViewById(R.id.search_view);
        chipGroup    = view.findViewById(R.id.chip_group_genre);
        progressBar  = view.findViewById(R.id.progress_bar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        filteredList = new ArrayList<>();

        adapter = new BookAdapter(filteredList, book -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("book_id", book.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        setupGenreChips();

        searchView.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentQuery = s.toString();
                applyFilterAsync();
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void setupGenreChips() {
        List<String> genres = BookRepository.getInstance().getAllGenres();
        for (String genre : genres) {
            Chip chip = new Chip(requireContext());
            chip.setText(genre);
            chip.setCheckable(true);
            chip.setChecked(genre.equals("Semua"));
            chip.setOnClickListener(v -> {
                currentGenre = genre;
                applyFilterAsync();
            });
            chipGroup.addView(chip);
        }
    }


    private void applyFilterAsync() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        final String query = currentQuery;
        final String genre = currentGenre;

        executor.execute(() -> {
            List<Book> all = BookRepository.getInstance().getAllBooks();
            List<Book> result = new ArrayList<>();

            for (Book b : all) {
                boolean matchQuery = b.getTitle().toLowerCase().contains(query.toLowerCase())
                        || b.getAuthor().toLowerCase().contains(query.toLowerCase());
                boolean matchGenre = genre.equals("Semua") || b.getGenre().equals(genre);
                if (matchQuery && matchGenre) result.add(b);
            }

            try { Thread.sleep(300); } catch (InterruptedException ignored) {}

            mainHandler.post(() -> {
                if (!isAdded()) return;
                filteredList.clear();
                filteredList.addAll(result);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        applyFilterAsync();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}