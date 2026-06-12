package com.example.cinetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "watchlist.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_NAME    = "watchlist";
    public static final String COLUMN_ID      = "id";
    public static final String COLUMN_TITLE   = "title";
    public static final String COLUMN_OVERVIEW= "overview";
    public static final String COLUMN_POSTER  = "poster_path";
    public static final String COLUMN_RATING  = "rating";
    public static final String COLUMN_STATUS  = "watch_status";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID       + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE    + " TEXT UNIQUE, " +
                COLUMN_OVERVIEW + " TEXT, " +
                COLUMN_POSTER   + " TEXT, " +
                COLUMN_RATING   + " REAL, " +
                COLUMN_STATUS   + " TEXT DEFAULT 'Belum Ditonton')";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {

            // Tambah kolom status tanpa hapus data lama
            db.execSQL("ALTER TABLE " + TABLE_NAME +
                    " ADD COLUMN " + COLUMN_STATUS + " TEXT DEFAULT 'Belum Ditonton'");
        }
    }

    public boolean insertWatchlist(String title, String overview, String posterPath, double rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,    title);
        cv.put(COLUMN_OVERVIEW, overview);
        cv.put(COLUMN_POSTER,   posterPath);
        cv.put(COLUMN_RATING,   rating);
        cv.put(COLUMN_STATUS,   "Belum Ditonton");
        long result = db.insertWithOnConflict(TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        return result != -1;
    }

    public Cursor getAllWatchlist() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean deleteWatchlist(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_NAME, COLUMN_TITLE + "=?", new String[]{title});
        return rows > 0;
    }

    public boolean isMovieInWatchlist(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT 1 FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + "=?",
                new String[]{title});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Update status tonton
    public boolean updateWatchStatus(String title, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STATUS, status);
        int rows = db.update(TABLE_NAME, cv, COLUMN_TITLE + "=?", new String[]{title});
        return rows > 0;
    }

    // Ambil status tonton berdasarkan judul
    public String getWatchStatus(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + COLUMN_STATUS + " FROM " + TABLE_NAME +
                        " WHERE " + COLUMN_TITLE + "=?", new String[]{title});
        String status = "Belum Ditonton";
        if (cursor.moveToFirst()) {
            status = cursor.getString(0);
        }
        cursor.close();
        return status;
    }
}
