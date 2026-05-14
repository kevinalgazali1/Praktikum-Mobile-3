package com.example.fragmen;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable {
    private String title;
    private String author;
    private int year;
    private String blurb;
    private int coverImage;
    private String imageUri;
    private boolean isLiked;

    // List static untuk menampung semua buku selama aplikasi running
    private static ArrayList<Book> allBooks = new ArrayList<>();

    public Book(String title, String author, int year, String blurb, int coverImage) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.coverImage = coverImage;
        this.isLiked = false;
    }

    public Book(String title, String author, int year, String blurb, String imageUri) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.imageUri = imageUri;
        this.isLiked = false;
    }

    public static ArrayList<Book> getAllBooks() {
        if (allBooks.isEmpty()) {
            allBooks.addAll(getDummyData());
        }
        return allBooks;
    }

    public static void addBook(Book book) {
        allBooks.add(0, book);
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getBlurb() { return blurb; }
    public int getCoverImage() { return coverImage; }
    public String getImageUri() { return imageUri; }
    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }

    private static ArrayList<Book> getDummyData() {
        ArrayList<Book> listBuku = new ArrayList<>();
        int dummyCover = R.drawable.ic_launcher_background;
        listBuku.add(new Book("Babel", "R. F. Kuang", 2022, "Sebuah kisah fantasi sejarah tentang kekuatan bahasa, kekerasan kolonialisme, dan pengorbanan dalam perlawanan di Oxford.", R.drawable.babel));
        listBuku.add(new Book("Bumi", "Tere Liye", 2014, "Petualangan Raib, seorang remaja yang bisa menghilang, saat ia menemukan dunia paralel yang tersembunyi.", R.drawable.bumi));
        listBuku.add(new Book("Bulan", "Tere Liye", 2015, "Perjalanan Raib, Seli, dan Ali berlanjut ke Klan Matahari untuk mengikuti kompetisi tingkat dunia paralel.", R.drawable.bulan));
        listBuku.add(new Book("Matahari", "Tere Liye", 2016, "Persahabatan mereka diuji saat mencari pintu menuju Klan Bintang yang misterius di bawah kerak bumi.", R.drawable.matahari));
        listBuku.add(new Book("Bintang", "Tere Liye", 2017, "Puncak petualangan di Klan Bintang untuk menyelamatkan dunia paralel dari kehancuran total.", R.drawable.bintang));
        listBuku.add(new Book("Seporsi Mie Ayam Sebelum Mati", "Zuhad", 2023, "Kumpulan cerita pendek yang menyentuh tentang kehidupan, penyesalan, dan harapan di balik semangkuk mie ayam.", R.drawable.seporsi_mie_ayam));
        listBuku.add(new Book("Dilan 1990", "Pidi Baiq", 2014, "Kisah cinta ikonik antara Milea dan Dilan, anggota geng motor puitis di Bandung tahun 1990.", R.drawable.dilan));
        listBuku.add(new Book("Dunia Sophie", "Jostein Gaarder", 1991, "Sejarah filsafat Barat yang dikemas dalam bentuk novel misteri yang memikat tentang seorang gadis bernama Sophie.", R.drawable.dunia_sophie));
        listBuku.add(new Book("Filosofi Teras", "Henry Manampiring", 2019, "Panduan praktis menerapkan filosofi Stoisisme untuk hidup yang lebih tenang dan tangguh di zaman modern.", R.drawable.filosofi_teras));
        listBuku.add(new Book("Laut Bercerita", "Leila S. Chudori", 2017, "Novel sejarah yang sangat menyentuh tentang perjuangan aktivis dan kekejaman penghilangan paksa tahun 1998.", R.drawable.laut_bercerita));

        return listBuku;
    }
}