package com.example.libraryapp;

public class Book {
    private int id;
    private String title;
    private String author;
    private int year;
    private String blurb;
    private String genre;
    private float rating;
    private int coverResId;
    private boolean liked;
    private String review;
    private int pages;

    public Book(int id, String title, String author, int year, String blurb,
                String genre, float rating, int coverResId, String review, int pages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.genre = genre;
        this.rating = rating;
        this.coverResId = coverResId;
        this.liked = false;
        this.review = review;
        this.pages = pages;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getBlurb() { return blurb; }
    public String getGenre() { return genre; }
    public float getRating() { return rating; }
    public int getCoverResId() { return coverResId; }
    public boolean isLiked() { return liked; }
    public void setLiked(boolean liked) { this.liked = liked; }
    public String getReview() { return review; }
    public int getPages() { return pages; }
}