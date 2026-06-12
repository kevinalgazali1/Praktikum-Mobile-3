package com.example.cinetracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailFragment extends Fragment {

    private ImageView ivPoster;
    private TextView tvTitle, tvRating, tvOverview, tvYear;
    private LinearLayout llGenreTags;
    private Button btnWatchlist, btnShare;
    private DatabaseHelper dbHelper;

    private final ExecutorService executor  = Executors.newSingleThreadExecutor();
    private final Handler mainHandler       = new Handler(Looper.getMainLooper());

    public DetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivPoster     = view.findViewById(R.id.iv_detail_poster);
        tvTitle      = view.findViewById(R.id.tv_detail_title);
        tvRating     = view.findViewById(R.id.tv_detail_rating);
        tvOverview   = view.findViewById(R.id.tv_detail_overview);
        tvYear       = view.findViewById(R.id.tv_detail_year);
        llGenreTags  = view.findViewById(R.id.ll_genre_tags);
        btnWatchlist = view.findViewById(R.id.btn_add_watchlist);
        btnShare     = view.findViewById(R.id.btn_share);
        dbHelper     = new DatabaseHelper(getContext());

        FloatingActionButton fabBack = view.findViewById(R.id.fab_back);
        if (fabBack != null) {
            fabBack.setOnClickListener(v -> Navigation.findNavController(view).popBackStack());
        }

        View contentSection = view.findViewById(R.id.content_section);
        if (contentSection != null) {
            contentSection.startAnimation(
                    AnimationUtils.loadAnimation(getContext(), R.anim.item_slide_up));
        }

        Bundle args = getArguments();
        if (args == null) return;

        String title      = args.getString("title", "");
        String overview   = args.getString("overview", "");
        String posterPath = args.getString("poster_path", "");
        String genreNames = args.getString("genre_names", "");
        String releaseYear= args.getString("release_year", "");

        double rating = 0.0;
        try {
            rating = Double.parseDouble(args.getString("rating", "0.0"));
        } catch (NumberFormatException e) {
            rating = 0.0;
        }

        final double finalRating    = rating;
        final String finalTitle     = title;
        final String finalOverview  = overview;
        final String finalPoster    = posterPath;
        final String finalGenres    = genreNames;

        tvTitle.setText(title);
        tvRating.setText(String.format("%.1f", rating));
        tvOverview.setText(overview);

        // Tahun rilis
        if (tvYear != null) {
            tvYear.setVisibility(releaseYear.isEmpty() ? View.GONE : View.VISIBLE);
            tvYear.setText(releaseYear);
        }

        // Genre tags
        if (llGenreTags != null && !genreNames.isEmpty()) {
            String[] genres = genreNames.split(" • ");
            for (String genre : genres) {
                TextView tag = new TextView(getContext());
                tag.setText(genre.trim());
                tag.setTextSize(10f);
                tag.setTextColor(Color.WHITE);
                tag.setBackgroundColor(Color.parseColor("#88000000"));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMarginEnd(6);
                tag.setLayoutParams(params);
                tag.setPadding(16, 6, 16, 6);
                llGenreTags.addView(tag);
            }
        }

        Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w500" + posterPath)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(ivPoster);

        executor.execute(() -> {
            boolean inWatchlist = dbHelper.isMovieInWatchlist(finalTitle);
            mainHandler.post(() -> updateButtonState(inWatchlist));
        });

        btnWatchlist.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.btn_click));
            executor.execute(() -> {
                boolean inWatchlist = dbHelper.isMovieInWatchlist(finalTitle);
                if (inWatchlist) {
                    dbHelper.deleteWatchlist(finalTitle);
                    mainHandler.post(() -> {
                        updateButtonState(false);
                        Toast.makeText(getContext(),
                                "\"" + finalTitle + "\" dihapus dari Watchlist",
                                Toast.LENGTH_SHORT).show();
                    });
                } else {
                    boolean ok = dbHelper.insertWatchlist(
                            finalTitle, finalOverview, finalPoster, finalRating);
                    mainHandler.post(() -> {
                        if (ok) {
                            updateButtonState(true);
                            Toast.makeText(getContext(),
                                    "🎬 \"" + finalTitle + "\" ditambahkan ke Watchlist!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(),
                                    "Gagal menyimpan ke Watchlist",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        });

        if (btnShare != null) {
            btnShare.setOnClickListener(v -> {
                String shareText = "🎬 " + finalTitle
                        + "\n⭐ Rating: " + String.format("%.1f", finalRating)
                        + (!finalGenres.isEmpty() ? "\n🎭 " + finalGenres : "")
                        + "\n\n" + finalOverview
                        + "\n\nDikirim via CineTracker";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(shareIntent, "Bagikan film via..."));
            });
        }
    }

    private void updateButtonState(boolean inWatchlist) {
        if (btnWatchlist == null) return;
        if (inWatchlist) {
            btnWatchlist.setText("✓ Sudah di Watchlist");
            btnWatchlist.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(
                            Color.parseColor("#4CAF50")));
        } else {
            btnWatchlist.setText("＋  Add to Watchlist");
            btnWatchlist.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(
                            Color.parseColor("#C8960C")));
        }
        if (getContext() != null) {
            btnWatchlist.startAnimation(
                    AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        executor.shutdown();
    }
}
