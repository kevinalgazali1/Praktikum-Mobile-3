package com.example.libraryapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Calendar;

public class AddBookFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 101;

    private EditText etTitle, etAuthor, etYear, etBlurb, etGenre, etReview, etPages;
    private ImageView ivCoverPreview;
    private Uri selectedImageUri;
    private int nextId = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etTitle = view.findViewById(R.id.et_title);
        etAuthor = view.findViewById(R.id.et_author);
        etYear = view.findViewById(R.id.et_year);
        etBlurb = view.findViewById(R.id.et_blurb);
        etGenre = view.findViewById(R.id.et_genre);
        etReview = view.findViewById(R.id.et_review);
        etPages = view.findViewById(R.id.et_pages);
        ivCoverPreview = view.findViewById(R.id.iv_cover_preview);

        Button btnPickImage = view.findViewById(R.id.btn_pick_image);
        Button btnSave = view.findViewById(R.id.btn_save);

        btnPickImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        btnSave.setOnClickListener(v -> saveBook());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            ivCoverPreview.setImageURI(selectedImageUri);
        }
    }

    private void saveBook() {
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String yearStr = etYear.getText().toString().trim();
        String blurb = etBlurb.getText().toString().trim();
        String genre = etGenre.getText().toString().trim();
        String review = etReview.getText().toString().trim();
        String pagesStr = etPages.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty() || blurb.isEmpty()) {
            Toast.makeText(getContext(), "Lengkapi semua field wajib", Toast.LENGTH_SHORT).show();
            return;
        }

        int year;
        int pages = 0;
        try {
            year = Integer.parseInt(yearStr);
            if (!pagesStr.isEmpty()) pages = Integer.parseInt(pagesStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Tahun dan halaman harus berupa angka", Toast.LENGTH_SHORT).show();
            return;
        }

        Book newBook = new Book(nextId++, title, author, year, blurb,
                genre.isEmpty() ? "Lainnya" : genre,
                0f, R.drawable.cover_placeholder,
                review.isEmpty() ? "Belum ada ulasan." : review,
                pages);

        BookRepository.getInstance().addBook(newBook);

        Toast.makeText(getContext(), "Buku berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
        clearForm();
    }

    private void clearForm() {
        etTitle.setText("");
        etAuthor.setText("");
        etYear.setText("");
        etBlurb.setText("");
        etGenre.setText("");
        etReview.setText("");
        etPages.setText("");
        ivCoverPreview.setImageResource(R.drawable.cover_placeholder);
        selectedImageUri = null;
    }
}