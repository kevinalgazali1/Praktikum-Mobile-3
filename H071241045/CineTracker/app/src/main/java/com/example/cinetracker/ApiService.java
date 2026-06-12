package com.example.cinetracker; // Sesuaikan dengan package aplikasimu

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    // Perintah GET untuk mengambil data dari endpoint "movie/popular"
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );
}