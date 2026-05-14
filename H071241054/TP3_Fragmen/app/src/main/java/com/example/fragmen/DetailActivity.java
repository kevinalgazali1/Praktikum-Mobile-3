package com.example.fragmen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    public static ArrayList<Book> favoriteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ivCover = findViewById(R.id.ivDetailCover);
        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvAuthor = findViewById(R.id.tvDetailAuthor);
        TextView tvYear = findViewById(R.id.tvDetailYear);
        TextView tvBlurb = findViewById(R.id.tvDetailBlurb);
        Button btnLike = findViewById(R.id.btnLike);

        Book buku = (Book) getIntent().getSerializableExtra("EXTRA_BOOK");

        if (buku != null) {
            ivCover.setImageResource(buku.getCoverImage());
            tvTitle.setText(buku.getTitle());
            tvAuthor.setText(buku.getAuthor());
            tvYear.setText("Tahun: " + buku.getYear());
            tvBlurb.setText(buku.getBlurb());

            boolean isAlreadyFavorite = false;
            for (Book fav : favoriteList) {
                // Kita cek berdasarkan judulnya
                if (fav.getTitle().equals(buku.getTitle())) {
                    isAlreadyFavorite = true;
                    break;
                }
            }

            if (isAlreadyFavorite) {
                btnLike.setText("Hapus dari Favorit");
            }

            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean found = false;

                    for (int i = 0; i < favoriteList.size(); i++) {
                        if (favoriteList.get(i).getTitle().equals(buku.getTitle())) {
                            favoriteList.remove(i);
                            btnLike.setText("Tambah ke Favorit");
                            Toast.makeText(DetailActivity.this, "Dihapus dari favorit", Toast.LENGTH_SHORT).show();
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        favoriteList.add(buku);
                        btnLike.setText("Hapus dari Favorit");
                        Toast.makeText(DetailActivity.this, "Dimasukkan ke favorit!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}