package com.example.fragmen; // Sesuaikan dengan package kamu

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Home extends Fragment {

    private RecyclerView rvBooks;
    private BookAdapter adapter;
    private ArrayList<Book> bookList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvBooks = view.findViewById(R.id.rvBooks);
        SearchView searchView = view.findViewById(R.id.searchView);

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

    private void filterBuku(String text) {
        ArrayList<Book> filteredList = new ArrayList<>();

        for (Book buku : bookList) {
            if (buku.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(buku);
            }
        }

        adapter.setListBuku(filteredList);
    }
}
