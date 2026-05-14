package com.example.instagramapp.models;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager instance;
    private List<Post> homeFeedPosts;
    private List<Post> myProfilePosts;
    private List<Story> myStories;
    private List<Story> homeStories;

    private String myUsername = "myusername";
    private String myFullName = "My Full Name";
    private String myBio      = "Living my best life \nContent creator | Traveler";

    private DataManager() {
        initHomeFeed();
        initProfilePosts();
        initStories();
        initHomeStories();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public String getMyUsername() { return myUsername; }
    public String getMyFullName() { return myFullName; }
    public String getMyBio()      { return myBio; }

    public void setMyUsername(String username) { this.myUsername = username; }
    public void setMyFullName(String fullName) { this.myFullName = fullName; }
    public void setMyBio(String bio)           { this.myBio = bio; }

    private void initHomeStories() {
        homeStories = new ArrayList<>();
        homeStories.add(new Story("Your Story", "avatar_me", true));
        homeStories.add(new Story("alex_photo", "avatar1", true));
        homeStories.add(new Story("cooking_mia", "avatar2", true));
        homeStories.add(new Story("travel_jnk", "avatar3", true));
        homeStories.add(new Story("fit_life", "avatar4", true));
        homeStories.add(new Story("cityscape", "avatar5", true));
        homeStories.add(new Story("nature_exp", "avatar6", true));
        homeStories.add(new Story("book_corner", "avatar7", true));
        homeStories.add(new Story("pet_lover", "avatar8", true));
        homeStories.add(new Story("music_vibe", "avatar9", true));
    }

    private void initHomeFeed() {
        homeFeedPosts = new ArrayList<>();
        homeFeedPosts.add(new Post("alex_photo", "avatar1", "post1",
                "Golden hour at the beach Nothing beats this view!", 1240, "2h ago"));
        homeFeedPosts.add(new Post("cooking_with_mia", "avatar2", "post2",
                "Homemade pasta from scratch Recipe in bio!", 892, "3h ago"));
        homeFeedPosts.add(new Post("travel_junkie", "avatar3", "post3",
                "Lost in the streets of Tokyo #travel #japan", 3201, "5h ago"));
        homeFeedPosts.add(new Post("fit_lifestyle", "avatar4", "post4",
                "Morning workout done Start your day strong!", 567, "6h ago"));
        homeFeedPosts.add(new Post("cityscape_art", "avatar5", "post5",
                "The city never sleeps Shot at midnight.", 2104, "8h ago"));
        homeFeedPosts.add(new Post("nature_explorer", "avatar6", "post6",
                "Hiking to the summit 3 hours well spent!", 1876, "10h ago"));
        homeFeedPosts.add(new Post("book_corner", "avatar7", "post7",
                "Currently reading: The Midnight Library Anyone else love this?", 435, "12h ago"));
        homeFeedPosts.add(new Post("pet_lover99", "avatar8", "post8",
                "My golden retriever found his favorite spot Too cute!", 5432, "14h ago"));
        homeFeedPosts.add(new Post("music_vibes", "avatar9", "post9",
                "Late night studio session New EP dropping soon!", 988, "1d ago"));
        homeFeedPosts.add(new Post("fashion_forward", "avatar10", "post10",
                "OOTD: Minimal but make it chic #fashion #style", 2341, "1d ago"));
    }

    private void initProfilePosts() {
        myProfilePosts = new ArrayList<>();
        myProfilePosts.add(new Post("myusername", "avatar_me", "mypost1",
                "Weekend getaway to the mountains", 312, "1d ago"));
        myProfilePosts.add(new Post("myusername", "avatar_me", "mypost2",
                "Sunday brunch hits different", 198, "3d ago"));
        myProfilePosts.add(new Post("myusername", "avatar_me", "mypost3",
                "New setup unlocked 💻🎮", 445, "1w ago"));
        myProfilePosts.add(new Post("myusername", "avatar_me", "mypost4",
                "Sunset never gets old", 267, "2w ago"));
        myProfilePosts.add(new Post("myusername", "avatar_me", "mypost5",
                "Good vibes only ", 523, "3w ago"));
    }

    private void initStories() {
        myStories = new ArrayList<>();
        myStories.add(new Story("Travel", "story1",
                "My trip to Bali last month was incredible! The beaches, the food, and the culture were amazing."));
        myStories.add(new Story("Food", "story2",
                "Tried this new ramen place downtown. 10/10 would recommend!"));
        myStories.add(new Story("Pets", "story3",
                "Meet Luna, my new kitten! She loves sleeping on my keyboard."));
        myStories.add(new Story("Gym", "story4",
                "6 months of consistency. Results are showing! Never give up."));
        myStories.add(new Story("Tech", "story5",
                "Just upgraded my workstation. Productivity is through the roof!"));
        myStories.add(new Story("Art", "story6",
                "Started learning digital art. Here's my first attempt!"));
        myStories.add(new Story("Music", "story7",
                "Playing guitar for 2 years now. Check out this little cover I did!"));
    }

    public List<Post> getPostsByUser(String username, String avatar) {
        List<Post> userPosts = new ArrayList<>();
        for (Post post : homeFeedPosts) {
            if (post.getUsername().equals(username)) {
                userPosts.add(post);
            }
        }
        if (userPosts.isEmpty()) {
            String[] captions = {
                    "Living the dream ✨", "Good times 🙌",
                    "New day, new vibes", "Grateful for everything",
                    "Making memories"
            };
            String[] images = {"post1", "post2", "post3", "post4", "post5"};
            for (int i = 0; i < 5; i++) {
                userPosts.add(new Post(
                        username != null ? username : "user",
                        avatar != null ? avatar : "placeholder_avatar",
                        images[i], captions[i],
                        (int)(Math.random() * 1000), (i + 1) + "d ago"
                ));
            }
        }
        return userPosts;
    }

    public List<Post> getHomeFeedPosts()  { return homeFeedPosts; }
    public List<Post> getMyProfilePosts() { return myProfilePosts; }
    public List<Story> getMyStories()     { return myStories; }
    public List<Story> getHomeStories()   { return homeStories; }

    public void addProfilePost(Post post) {
        myProfilePosts.add(0, post);
    }
}