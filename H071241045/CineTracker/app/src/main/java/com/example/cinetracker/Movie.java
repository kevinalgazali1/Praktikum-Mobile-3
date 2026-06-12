package com.example.cinetracker;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Movie {
    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    @SerializedName("release_date")
    private String releaseDate;

    // Field lokal untuk status watchlist (tidak dari API)
    private String watchStatus = "Belum Ditonton";

    public Movie() {}

    public String getTitle() { return title != null ? title : "No Title"; }
    public void setTitle(String title) { this.title = title; }

    public String getOverview() { return overview != null ? overview : "No Description"; }
    public void setOverview(String overview) { this.overview = overview; }

    public String getPosterPath() { return posterPath != null ? posterPath : ""; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public double getVoteAverage() { return voteAverage; }
    public void setVoteAverage(double voteAverage) { this.voteAverage = voteAverage; }

    public List<Integer> getGenreIds() { return genreIds; }
    public void setGenreIds(List<Integer> genreIds) { this.genreIds = genreIds; }

    public String getReleaseDate() { return releaseDate != null ? releaseDate : ""; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    // Ambil tahun dari releaseDate format "YYYY-MM-DD"
    public String getReleaseYear() {
        if (releaseDate != null && releaseDate.length() >= 4) {
            return releaseDate.substring(0, 4);
        }
        return "";
    }

    // Konversi genre_ids ke nama genre
    public String getGenreNames() {
        if (genreIds == null || genreIds.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(genreIds.size(), 3); i++) {
            if (i > 0) sb.append(" • ");
            sb.append(getGenreName(genreIds.get(i)));
        }
        return sb.toString();
    }

    private String getGenreName(int id) {
        switch (id) {
            case 28: return "Action";
            case 12: return "Adventure";
            case 16: return "Animation";
            case 35: return "Comedy";
            case 80: return "Crime";
            case 99: return "Documentary";
            case 18: return "Drama";
            case 10751: return "Family";
            case 14: return "Fantasy";
            case 36: return "History";
            case 27: return "Horror";
            case 10402: return "Music";
            case 9648: return "Mystery";
            case 10749: return "Romance";
            case 878: return "Sci-Fi";
            case 10770: return "TV Movie";
            case 53: return "Thriller";
            case 10752: return "War";
            case 37: return "Western";
            default: return "Other";
        }
    }

    public String getWatchStatus() { return watchStatus != null ? watchStatus : "Belum Ditonton"; }
    public void setWatchStatus(String watchStatus) { this.watchStatus = watchStatus; }
}
