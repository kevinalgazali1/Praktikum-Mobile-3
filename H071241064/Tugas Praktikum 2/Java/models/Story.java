package com.example.instagramapp.models;

public class Story {
    private String title;
    private String coverImage;
    private String content;
    private String avatarRes;
    private boolean isViewed;

    public Story(String title, String coverImage, String content) {
        this.title = title;
        this.coverImage = coverImage;
        this.content = content;
        this.avatarRes = coverImage;
        this.isViewed = false;
    }

    public Story(String username, String avatarRes, boolean isHomeStory) {
        this.title = username;
        this.coverImage = avatarRes;
        this.content = "";
        this.avatarRes = avatarRes;
        this.isViewed = false;
    }

    public String getTitle() { return title; }
    public String getCoverImage() { return coverImage; }
    public String getContent() { return content; }
    public String getAvatarRes() { return avatarRes; }
    public boolean isViewed() { return isViewed; }
    public void setViewed(boolean viewed) { isViewed = viewed; }
}