package com.example.cinetracker;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WatchlistFragment extends Fragment {

    private RecyclerView rvWatchlist;
    private View tvEmpty;
    private TextView tvWatchlistCount;
    private EditText etSearchWatchlist;
    private TextView chipSortRating, chipSortAz, chipReset;
    private WatchlistAdapter watchlistAdapter;

    private List<Movie> allMovies      = new ArrayList<>();
    private List<Movie> filteredMovies = new ArrayList<>();
    private DatabaseHelper dbHelper;
    private String currentSort = "none";

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler      = new Handler(Looper.getMainLooper());

    public WatchlistFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_watchlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvWatchlist       = view.findViewById(R.id.rv_watchlist);
        tvEmpty           = view.findViewById(R.id.tv_empty_watchlist);
        tvWatchlistCount  = view.findViewById(R.id.tv_watchlist_count);
        etSearchWatchlist = view.findViewById(R.id.et_search_watchlist);
        chipSortRating    = view.findViewById(R.id.chip_sort_rating_wl);
        chipSortAz        = view.findViewById(R.id.chip_sort_az_wl);
        chipReset         = view.findViewById(R.id.chip_reset_wl);
        dbHelper          = new DatabaseHelper(getContext());

        rvWatchlist.setLayoutManager(new LinearLayoutManager(getContext()));
        watchlistAdapter = new WatchlistAdapter(
                filteredMovies,
                (movie, position) -> {
                    executor.execute(() -> {
                        dbHelper.deleteWatchlist(movie.getTitle());
                        mainHandler.post(() -> {
                            allMovies.remove(movie);
                            filteredMovies.remove(position);
                            watchlistAdapter.notifyItemRemoved(position);
                            watchlistAdapter.notifyItemRangeChanged(position, filteredMovies.size());
                            checkEmpty();
                            updateCount();
                            Toast.makeText(getContext(),
                                    "\"" + movie.getTitle() + "\" dihapus dari Watchlist",
                                    Toast.LENGTH_SHORT).show();
                        });
                    });
                },
                (movie, position) -> showStatusBottomSheet(movie, position)
        );
        rvWatchlist.setAdapter(watchlistAdapter);

        if (etSearchWatchlist != null) {
            etSearchWatchlist.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int i, int c, int a) {}
                @Override public void onTextChanged(CharSequence s, int i, int b, int c) {
                    applyFilterAndSort(s.toString());
                }
                @Override public void afterTextChanged(Editable s) {}
            });
        }

        if (chipSortRating != null) {
            chipSortRating.setOnClickListener(v -> {
                currentSort = "rating";
                setChipActive(chipSortRating);
                setChipInactive(chipSortAz);
                setChipDim(chipReset);
                applyFilterAndSort(getSearchQuery());
            });
        }
        if (chipSortAz != null) {
            chipSortAz.setOnClickListener(v -> {
                currentSort = "az";
                setChipActive(chipSortAz);
                setChipInactive(chipSortRating);
                setChipDim(chipReset);
                applyFilterAndSort(getSearchQuery());
            });
        }
        if (chipReset != null) {
            chipReset.setOnClickListener(v -> {
                currentSort = "none";
                setChipInactive(chipSortRating);
                setChipInactive(chipSortAz);
                setChipDim(chipReset);
                applyFilterAndSort(getSearchQuery());
            });
        }

        loadWatchlistFromDb();
    }

    private void showStatusBottomSheet(Movie movie, int position) {
        if (getContext() == null) return;

        BottomSheetDialog bottomSheet = new BottomSheetDialog(getContext());
        View sheetView = LayoutInflater.from(getContext())
                .inflate(R.layout.bottom_sheet_status, null);
        bottomSheet.setContentView(sheetView);

        TextView tvMovieTitle = sheetView.findViewById(R.id.tv_sheet_movie_title);
        tvMovieTitle.setText(movie.getTitle());

        LinearLayout optBelum  = sheetView.findViewById(R.id.opt_belum_ditonton);
        LinearLayout optSedang = sheetView.findViewById(R.id.opt_sedang_ditonton);
        LinearLayout optSudah  = sheetView.findViewById(R.id.opt_sudah_ditonton);
        TextView tvCancel      = sheetView.findViewById(R.id.tv_sheet_cancel);

        updateSheetSelection(sheetView, movie.getWatchStatus());

        optBelum.setOnClickListener(v  -> updateStatus(movie, position, "Belum Ditonton",  bottomSheet, sheetView));
        optSedang.setOnClickListener(v -> updateStatus(movie, position, "Sedang Ditonton", bottomSheet, sheetView));
        optSudah.setOnClickListener(v  -> updateStatus(movie, position, "Sudah Ditonton",  bottomSheet, sheetView));
        tvCancel.setOnClickListener(v  -> bottomSheet.dismiss());

        bottomSheet.show();
    }

    private void updateStatus(Movie movie, int position, String newStatus,
                              BottomSheetDialog sheet, View sheetView) {
        executor.execute(() -> {
            dbHelper.updateWatchStatus(movie.getTitle(), newStatus);
            mainHandler.post(() -> {
                movie.setWatchStatus(newStatus);
                watchlistAdapter.notifyItemChanged(position);
                sheet.dismiss();
                Toast.makeText(getContext(), "Status: " + newStatus, Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void updateSheetSelection(View sheetView, String currentStatus) {
        View optBelum  = sheetView.findViewById(R.id.opt_belum_ditonton);
        View optSedang = sheetView.findViewById(R.id.opt_sedang_ditonton);
        View optSudah  = sheetView.findViewById(R.id.opt_sudah_ditonton);

        optBelum.setBackgroundColor( "Belum Ditonton".equals(currentStatus)  ? 0x221B2A5E : android.graphics.Color.TRANSPARENT);
        optSedang.setBackgroundColor("Sedang Ditonton".equals(currentStatus) ? 0x22C8960C : android.graphics.Color.TRANSPARENT);
        optSudah.setBackgroundColor( "Sudah Ditonton".equals(currentStatus)  ? 0x224CAF50 : android.graphics.Color.TRANSPARENT);

        sheetView.findViewById(R.id.check_belum).setVisibility(
                "Belum Ditonton".equals(currentStatus)  ? View.VISIBLE : View.GONE);
        sheetView.findViewById(R.id.check_sedang).setVisibility(
                "Sedang Ditonton".equals(currentStatus) ? View.VISIBLE : View.GONE);
        sheetView.findViewById(R.id.check_sudah).setVisibility(
                "Sudah Ditonton".equals(currentStatus)  ? View.VISIBLE : View.GONE);
    }

    private String getSearchQuery() {
        return etSearchWatchlist != null ? etSearchWatchlist.getText().toString() : "";
    }

    private void applyFilterAndSort(String query) {
        filteredMovies.clear();
        if (query.isEmpty()) {
            filteredMovies.addAll(allMovies);
        } else {
            String lower = query.toLowerCase();
            for (Movie m : allMovies) {
                if (m.getTitle().toLowerCase().contains(lower)) filteredMovies.add(m);
            }
        }
        if ("rating".equals(currentSort)) {
            Collections.sort(filteredMovies,
                    (a, b) -> Double.compare(b.getVoteAverage(), a.getVoteAverage()));
        } else if ("az".equals(currentSort)) {
            Collections.sort(filteredMovies,
                    (a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
        }
        watchlistAdapter.notifyDataSetChanged();
        updateCount();
    }

    private void setChipActive(TextView chip) {
        if (chip == null || getContext() == null) return;
        chip.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_chip_active));
        chip.setTextColor(android.graphics.Color.WHITE);
    }

    private void setChipInactive(TextView chip) {
        if (chip == null || getContext() == null) return;
        chip.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_chip_inactive));
        chip.setTextColor(ContextCompat.getColor(getContext(), R.color.ct_accent));
    }

    private void setChipDim(TextView chip) {
        if (chip == null || getContext() == null) return;
        chip.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_chip_dim));
        chip.setTextColor(ContextCompat.getColor(getContext(), R.color.ct_text_dim));
    }

    private void loadWatchlistFromDb() {
        executor.execute(() -> {
            List<Movie> result = new ArrayList<>();
            try {
                Cursor cursor = dbHelper.getAllWatchlist();
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        Movie movie = new Movie();
                        movie.setTitle(cursor.getString(
                                cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE)));
                        movie.setOverview(cursor.getString(
                                cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_OVERVIEW)));
                        movie.setPosterPath(cursor.getString(
                                cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_POSTER)));
                        movie.setVoteAverage(cursor.getDouble(
                                cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RATING)));
                        int statusCol = cursor.getColumnIndex(DatabaseHelper.COLUMN_STATUS);
                        if (statusCol >= 0) {
                            movie.setWatchStatus(cursor.getString(statusCol));
                        }
                        result.add(movie);
                    } while (cursor.moveToNext());
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            mainHandler.post(() -> {
                allMovies.clear();
                allMovies.addAll(result);
                applyFilterAndSort(getSearchQuery());
                checkEmpty();
                updateCount();
            });
        });
    }

    private void updateCount() {
        if (tvWatchlistCount != null) {
            int count = filteredMovies.size();
            tvWatchlistCount.setText(count + (count == 1 ? " film" : " films"));
        }
    }

    private void checkEmpty() {
        if (tvEmpty == null) return;
        if (filteredMovies.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            tvEmpty.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
            rvWatchlist.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvWatchlist.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        executor.shutdown();
    }
}
