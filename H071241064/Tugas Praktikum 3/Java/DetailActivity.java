package com.example.libraryapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private Book book;
    private ImageView ivLike;
    private boolean isLiked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int bookId = getIntent().getIntExtra("book_id", -1);
        book = BookRepository.getInstance().getBookById(bookId);

        if (book == null) {
            finish();
            return;
        }

        ImageView ivCoverBg = findViewById(R.id.iv_detail_cover);
        ImageView ivCoverFront = findViewById(R.id.iv_detail_cover_front);
        TextView tvTitle = findViewById(R.id.tv_detail_title);
        TextView tvAuthor = findViewById(R.id.tv_detail_author);
        TextView tvYear = findViewById(R.id.tv_detail_year);
        TextView tvGenre = findViewById(R.id.tv_detail_genre);
        TextView tvPages = findViewById(R.id.tv_detail_pages);
        TextView tvBlurb = findViewById(R.id.tv_detail_blurb);
        TextView tvReview = findViewById(R.id.tv_detail_review);
        RatingBar ratingBar = findViewById(R.id.rating_bar);
        TextView tvRatingNum = findViewById(R.id.tv_rating_num);
        ivLike = findViewById(R.id.iv_like);
        ImageView ivBack = findViewById(R.id.iv_back);

        ivCoverBg.setImageResource(book.getCoverResId());
        ivCoverFront.setImageResource(book.getCoverResId());
        tvTitle.setText(book.getTitle());
        tvAuthor.setText(book.getAuthor());
        tvYear.setText("Tahun " + book.getYear());
        tvGenre.setText(book.getGenre());
        tvPages.setText(book.getPages() > 0 ? "  \u2022  " + book.getPages() + " hal." : "");
        tvBlurb.setText(book.getBlurb());
        tvReview.setText(book.getReview());
        ratingBar.setRating(book.getRating());
        tvRatingNum.setText(book.getRating() > 0
                ? String.format("%.1f / 5.0", book.getRating())
                : "Belum ada rating");

        isLiked = book.isLiked();
        updateLikeIcon();

        ivLike.setOnClickListener(v -> {
            isLiked = !isLiked;
            book.setLiked(isLiked);
            updateLikeIcon();
            String msg = isLiked ? "Ditambahkan ke Favorit" : "Dihapus dari Favorit";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        ivBack.setOnClickListener(v -> finish());
    }

    private void updateLikeIcon() {
        ivLike.setImageResource(isLiked
                ? R.drawable.ic_heart_filled
                : R.drawable.ic_heart_outline);
    }
}