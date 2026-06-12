package com.example.cinetracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private static final String PREFS_NAME      = "cinetracker_prefs";
    private static final String KEY_THEME       = "dark_mode";
    private static final String KEY_MOVIE_CACHE = "movie_cache";

    private RecyclerView rvMovies;
    private SwipeRefreshLayout swipeRefresh;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList    = new ArrayList<>();
    private List<Movie> filteredList = new ArrayList<>();
    private LinearLayout layoutError;
    private android.widget.Button btnRefresh;
    private TextView btnThemeToggle;
    private TextView tvOfflineBanner;
    private EditText etSearch;
    private NavController navController;
    private TextView chipSortRating, chipSortAz, chipReset;
    private String currentSort = "none";

    public HomeFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController    = Navigation.findNavController(view);
        rvMovies         = view.findViewById(R.id.rv_movies);
        swipeRefresh     = view.findViewById(R.id.swipe_refresh);
        layoutError      = view.findViewById(R.id.layout_error);
        btnRefresh       = view.findViewById(R.id.btn_refresh);
        btnThemeToggle   = view.findViewById(R.id.btn_theme_toggle);
        etSearch         = view.findViewById(R.id.et_search);
        tvOfflineBanner  = view.findViewById(R.id.tv_offline_banner);
        chipSortRating   = view.findViewById(R.id.chip_sort_rating);
        chipSortAz       = view.findViewById(R.id.chip_sort_az);
        chipReset        = view.findViewById(R.id.chip_reset);

        updateThemeIcon();

        // SwipeRefreshLayout warna aksen
        if (swipeRefresh != null) {
            swipeRefresh.setColorSchemeColors(
                    android.graphics.Color.parseColor("#C8960C"),
                    android.graphics.Color.parseColor("#1B2A5E"));
            swipeRefresh.setOnRefreshListener(() -> getMoviesFromApi());
        }

        rvMovies.setLayoutManager(new GridLayoutManager(getContext(), 2));
        movieAdapter = new MovieAdapter(filteredList, movie -> {
            if (navController == null) return;
            Bundle args = new Bundle();
            args.putString("title",        movie.getTitle());
            args.putString("overview",     movie.getOverview());
            args.putString("poster_path",  movie.getPosterPath());
            args.putString("rating",       String.valueOf(movie.getVoteAverage()));
            args.putString("genre_names",  movie.getGenreNames());
            args.putString("release_year", movie.getReleaseYear());
            navController.navigate(R.id.action_home_to_detail, args);
        });
        rvMovies.setAdapter(movieAdapter);
        getMoviesFromApi();

        if (btnRefresh != null) btnRefresh.setOnClickListener(v -> getMoviesFromApi());

        if (btnThemeToggle != null) {
            btnThemeToggle.setOnClickListener(v -> {
                SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, 0);
                boolean isDark = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
                AppCompatDelegate.setDefaultNightMode(isDark
                        ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);
                prefs.edit().putBoolean(KEY_THEME, !isDark).apply();
                updateThemeIcon();
            });
        }

        if (etSearch != null) {
            etSearch.addTextChangedListener(new TextWatcher() {
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
    }

    private void updateThemeIcon() {
        if (btnThemeToggle == null) return;
        boolean isDark = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
        btnThemeToggle.setText(isDark ? "☀️" : "🌙");
    }

    private String getSearchQuery() {
        return etSearch != null ? etSearch.getText().toString() : "";
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

    private void applyFilterAndSort(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(movieList);
        } else {
            String lower = query.toLowerCase();
            for (Movie m : movieList) {
                if (m.getTitle().toLowerCase().contains(lower)) filteredList.add(m);
            }
        }
        if ("rating".equals(currentSort)) {
            Collections.sort(filteredList, (a, b) -> Double.compare(b.getVoteAverage(), a.getVoteAverage()));
        } else if ("az".equals(currentSort)) {
            Collections.sort(filteredList, (a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
        }
        if (movieAdapter != null) movieAdapter.notifyDataSetChanged();
    }

    private void saveMovieCache(List<Movie> movies) {
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, 0);
        String json = new Gson().toJson(movies);
        prefs.edit().putString(KEY_MOVIE_CACHE, json).apply();
    }

    private List<Movie> loadMovieCache() {
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, 0);
        String json = prefs.getString(KEY_MOVIE_CACHE, null);
        if (json == null) return new ArrayList<>();
        Type type = new TypeToken<List<Movie>>(){}.getType();
        return new Gson().fromJson(json, type);
    }

    private void getMoviesFromApi() {
        if (swipeRefresh == null) showLoading();
        try {
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<MovieResponse> call = apiService.getPopularMovies("b33157a8cf96eee140b61c1adf71ebb0", 1);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call,
                                       @NonNull Response<MovieResponse> response) {
                    if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
                    if (response.isSuccessful() && response.body() != null
                            && response.body().getResults() != null) {
                        List<Movie> results = response.body().getResults();
                        movieList.clear();
                        movieList.addAll(results);
                        saveMovieCache(results);
                        applyFilterAndSort(getSearchQuery());
                        showContent(false);
                    } else {
                        loadFromCache();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                    if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
                    loadFromCache();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
            loadFromCache();
        }
    }

    private void loadFromCache() {
        List<Movie> cached = loadMovieCache();
        if (!cached.isEmpty()) {
            movieList.clear();
            movieList.addAll(cached);
            applyFilterAndSort(getSearchQuery());
            showContent(true);
        } else {
            showError();
        }
    }

    private void showLoading() {
        if (layoutError != null) layoutError.setVisibility(View.GONE);
        if (rvMovies != null) rvMovies.setVisibility(View.VISIBLE);
        if (tvOfflineBanner != null) tvOfflineBanner.setVisibility(View.GONE);
    }

    private void showContent(boolean isOffline) {
        if (layoutError != null) layoutError.setVisibility(View.GONE);
        if (rvMovies != null) rvMovies.setVisibility(View.VISIBLE);
        if (tvOfflineBanner != null)
            tvOfflineBanner.setVisibility(isOffline ? View.VISIBLE : View.GONE);
    }

    private void showError() {
        if (layoutError != null) layoutError.setVisibility(View.VISIBLE);
        if (rvMovies != null) rvMovies.setVisibility(View.GONE);
        if (tvOfflineBanner != null) tvOfflineBanner.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        navController = null;
    }
}
