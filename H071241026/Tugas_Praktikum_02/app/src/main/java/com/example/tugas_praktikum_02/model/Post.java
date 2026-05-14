package com.example.tugas_praktikum_02.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {
    private String username;
    private int profileImage;
    private int postImage;
    private String caption;

    public Post(String username, int profileImage, int postImage, String caption) {
        this.username = username;
        this.profileImage = profileImage;
        this.postImage = postImage;
        this.caption = caption;
    }

    protected Post(Parcel in) {
        username = in.readString();
        profileImage = in.readInt();
        postImage = in.readInt();
        caption = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) { return new Post(in); }
        @Override
        public Post[] newArray(int size) { return new Post[size]; }
    };

    public String getUsername() { return username; }
    public int getProfileImage() { return profileImage; }
    public int getPostImage() { return postImage; }
    public String getCaption() { return caption; }
    public void setPostImage(int postImage) { this.postImage = postImage; }
    public void setCaption(String caption) { this.caption = caption; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeInt(profileImage);
        parcel.writeInt(postImage);
        parcel.writeString(caption);
    }
}