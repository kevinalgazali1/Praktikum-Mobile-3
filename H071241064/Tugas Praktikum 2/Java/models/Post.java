package com.example.instagramapp.models;

public class Post {
    private String username;
    private String userAvatar;
    private String imageRes;
    private String caption;
    private int likes;
    private String timeAgo;
    private boolean isCustom;

    public Post(String username, String userAvatar, String imageRes, String caption, int likes, String timeAgo) {
        this.username = username;
        this.userAvatar = userAvatar;
        this.imageRes = imageRes;
        this.caption = caption;
        this.likes = likes;
        this.timeAgo = timeAgo;
        this.isCustom = false;
    }

    public Post(String username, String imageRes, String caption) {
        this.username = username;
        this.userAvatar = "avatar_me";
        this.imageRes = imageRes;
        this.caption = caption;
        this.likes = 0;
        this.timeAgo = "just now";
        this.isCustom = true;
    }

    public String getUsername() { return username; }
    public String getUserAvatar() { return userAvatar; }
    public String getImageRes() { return imageRes; }
    public String getCaption() { return caption; }
    public int getLikes() { return likes; }
    public String getTimeAgo() { return timeAgo; }
    public boolean isCustom() { return isCustom; }
    public void setImageRes(String imageRes) { this.imageRes = imageRes; }
}