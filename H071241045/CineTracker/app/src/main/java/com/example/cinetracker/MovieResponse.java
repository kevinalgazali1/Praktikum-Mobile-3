package com.example.cinetracker;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class MovieResponse {

    @SerializedName("results")
    private List<Movie> results;

    // CONSTRUCTOR KOSONG (Wajib ada untuk Retrofit agar tidak crash saat parsing data)
    public MovieResponse() {
        this.results = new ArrayList<>();
    }

    public List<Movie> getResults() {
        return results != null ? results : new ArrayList<>();
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}