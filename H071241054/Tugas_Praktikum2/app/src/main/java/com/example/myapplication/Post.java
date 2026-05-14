package com.example.myapplication;

public class Post {
    private int profileImage;
    private String username;
    private int postImage;
    private String imageUri;
    private String caption;

    public Post(int profileImage, String username, int postImage, String caption) {
        this.profileImage = profileImage;
        this.username = username;
        this.postImage = postImage;
        this.caption = caption;
        this.imageUri = null;
    }

    public Post(int profileImage, String username, String imageUri, String caption) {
        this.profileImage = profileImage;
        this.username = username;
        this.imageUri = imageUri;
        this.caption = caption;
        this.postImage = 0;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public String getUsername() {
        return username;
    }

    public int getPostImage() {
        return postImage;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getCaption() {
        return caption;
    }
}
