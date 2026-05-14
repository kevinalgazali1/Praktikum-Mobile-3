package com.example.instagramapp.models;

public class User {
    private String username;
    private String fullName;
    private String avatarRes;
    private String bio;
    private int postsCount;
    private int followersCount;
    private int followingCount;

    public User(String username, String fullName, String avatarRes, String bio,
                int postsCount, int followersCount, int followingCount) {
        this.username = username;
        this.fullName = fullName;
        this.avatarRes = avatarRes;
        this.bio = bio;
        this.postsCount = postsCount;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
    }

    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public String getAvatarRes() { return avatarRes; }
    public String getBio() { return bio; }
    public int getPostsCount() { return postsCount; }
    public int getFollowersCount() { return followersCount; }
    public int getFollowingCount() { return followingCount; }
    public void setPostsCount(int postsCount) { this.postsCount = postsCount; }
}