package com.example.tugas_praktikum_02.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Story implements Parcelable {
    private String title;
    private int coverImage;

    public Story(String title, int coverImage) {
        this.title = title;
        this.coverImage = coverImage;
    }

    protected Story(Parcel in) {
        title = in.readString();
        coverImage = in.readInt();
    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel in) { return new Story(in); }
        @Override
        public Story[] newArray(int size) { return new Story[size]; }
    };

    public String getTitle() { return title; }
    public int getCoverImage() { return coverImage; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeInt(coverImage);
    }
}