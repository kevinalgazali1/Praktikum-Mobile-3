package com.example.fragmen;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Home extends Fragment {

    private RecyclerView rvBooks;
    private BookAdapter adapter;
    private ArrayList<Book> bookList;
    private ProgressBar progressBar;
    private TextView tvEmptyHome;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvBooks = view.findViewById(R.id.rvBooks);
        SearchView searchView = view.findViewById(R.id.searchView);
        progressBar = view.findViewById(R.id.progressBarHome);
        tvEmptyHome = view.findViewById(R.id.tvEmptyHome);

        bookList = Book.getAllBooks();

        Collections.sort(bookList, new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                return Integer.compare(b2.getYear(), b1.getYear());
            }
        });

        rvBooks.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookAdapter(getContext(), bookList);
        rvBooks.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterBuku(newText);
                return true;
            }
        });

        return view;
    }

    private Runnable searchRunnable;

    private void filterBuku(String text) {
        // Batalkan pencarian sebelumnya jika user masih mengetik (Debounce)
        if (searchRunnable != null) {
            handler.removeCallbacks(searchRunnable);
        }

        progressBar.setVisibility(View.VISIBLE);
        rvBooks.setVisibility(View.GONE);
        tvEmptyHome.setVisibility(View.GONE);

        // Buat tugas baru yang akan dijalankan setelah user berhenti mengetik selama 300ms
        searchRunnable = () -> {
            executor.execute(() -> {
                ArrayList<Book> filteredList = new ArrayList<>();
                for (Book buku : bookList) {
                    if (buku.getTitle().toLowerCase().contains(text.toLowerCase())) {
                        filteredList.add(buku);
                    }
                }

                try {
                    if (filteredList.isEmpty() && !text.isEmpty()) {
                        Thread.sleep(1500);
                    } else {
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(() -> {
                    adapter.setListBuku(filteredList);
                    progressBar.setVisibility(View.GONE);

                    if (filteredList.isEmpty()) {
                        tvEmptyHome.setVisibility(View.VISIBLE);
                        rvBooks.setVisibility(View.GONE);
                    } else {
                        tvEmptyHome.setVisibility(View.GONE);
                        rvBooks.setVisibility(View.VISIBLE);
                    }
                });
            });
        };

        // Jalankan tugas setelah 300ms
        handler.postDelayed(searchRunnable, 300);
    }
}
