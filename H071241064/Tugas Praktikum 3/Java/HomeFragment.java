package com.example.libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private EditText searchView;
    private ChipGroup chipGroup;
    private List<Book> filteredList;
    private String currentQuery = "";
    private String currentGenre = "Semua";

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
        searchView = view.findViewById(R.id.search_view);
        chipGroup = view.findViewById(R.id.chip_group_genre);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        filteredList = new ArrayList<>(BookRepository.getInstance().getAllBooks());

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
                applyFilter();
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
                applyFilter();
            });
            chipGroup.addView(chip);
        }
    }

    private void applyFilter() {
        List<Book> all = BookRepository.getInstance().getAllBooks();
        filteredList.clear();
        for (Book b : all) {
            boolean matchQuery = b.getTitle().toLowerCase().contains(currentQuery.toLowerCase())
                    || b.getAuthor().toLowerCase().contains(currentQuery.toLowerCase());
            boolean matchGenre = currentGenre.equals("Semua") || b.getGenre().equals(currentGenre);
            if (matchQuery && matchGenre) filteredList.add(b);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        applyFilter();
    }
}