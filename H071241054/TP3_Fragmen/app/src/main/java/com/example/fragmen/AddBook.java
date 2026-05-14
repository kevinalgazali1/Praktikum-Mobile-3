package com.example.fragmen;

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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddBook extends Fragment {

    private ImageView ivCoverPreview;
    private Uri selectedImageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        ivCoverPreview = view.findViewById(R.id.ivCoverPreview);
        Button btnChooseImage = view.findViewById(R.id.btnChooseImage);
        EditText etTitle = view.findViewById(R.id.etTitle);
        EditText etAuthor = view.findViewById(R.id.etAuthor);
        EditText etYear = view.findViewById(R.id.etYear);
        EditText etBlurb = view.findViewById(R.id.etBlurb);
        Button btnSaveBook = view.findViewById(R.id.btnSaveBook);

        ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        ivCoverPreview.setImageURI(selectedImageUri);
                    }
                }
        );

        btnChooseImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT); // Pakai OPEN_DOCUMENT agar URI tetap bisa diakses nanti
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            galleryLauncher.launch(intent);
        });

        btnSaveBook.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String author = etAuthor.getText().toString().trim();
            String yearStr = etYear.getText().toString().trim();
            String blurb = etBlurb.getText().toString().trim();

            if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty() || blurb.isEmpty()) {
                Toast.makeText(getContext(), "Harap isi semua data buku!", Toast.LENGTH_SHORT).show();
            } else if (selectedImageUri == null) {
                Toast.makeText(getContext(), "Pilih gambar cover!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    int year = Integer.parseInt(yearStr);
                    Book newBook = new Book(title, author, year, blurb, selectedImageUri.toString());
                    Book.addBook(newBook);

                    Toast.makeText(getContext(), "Buku berhasil ditambahkan!", Toast.LENGTH_SHORT).show();

                    etTitle.setText("");
                    etAuthor.setText("");
                    etYear.setText("");
                    etBlurb.setText("");
                    ivCoverPreview.setImageResource(0);
                    selectedImageUri = null;

                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Tahun harus berupa angka!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}