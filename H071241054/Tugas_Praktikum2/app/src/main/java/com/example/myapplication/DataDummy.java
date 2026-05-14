package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class DataDummy {

    private static List<Post> homePosts;
    private static List<Post> profilePosts;

    public static List<Post> getHomePosts() {
        if (homePosts == null) {
            homePosts = new ArrayList<>();

            homePosts.add(new Post(
                    R.drawable.joy,
                    "marchegatriani_",
                    R.drawable.joy,
                    "Akuuu senang sekaliii"
            ));

            homePosts.add(new Post(
                    R.drawable.sadness,
                    "backburner_",
                    R.drawable.sadness,
                    "Lagi galau hari ini"
            ));

            homePosts.add(new Post(
                    R.drawable.angry,
                    "marah_mulu",
                    R.drawable.angry,
                    "Bisa ga, jangan buat gue marah?"
            ));

            homePosts.add(new Post(
                    R.drawable.gajahpink,
                    "gajahpink_joyful",
                    R.drawable.gajahpink,
                    "Kapan lagi lihat gajah warna pink?"
            ));

            homePosts.add(new Post(
                    R.drawable.anxiety,
                    "ims0afraid",
                    R.drawable.anxiety,
                    "Pliss jangan buat panik."
            ));

            homePosts.add(new Post(
                    R.drawable.fear,
                    "fearness",
                    R.drawable.fear,
                    "Hi, im fear."
            ));

            homePosts.add(new Post(
                    R.drawable.embrassement,
                    "malu_malu_kucing",
                    R.drawable.embrassement,
                    "hi."
            ));

            homePosts.add(new Post(
                    R.drawable.angry,
                    "apa_lo",
                    R.drawable.angry,
                    "Jangan senggol kalau lo gamau gua bacok."
            ));

            homePosts.add(new Post(
                    R.drawable.disgust,
                    "disgusting",
                    R.drawable.disgust,
                    "apa sih?"
            ));

            homePosts.add(new Post(
                    R.drawable.envy,
                    "environment",
                    R.drawable.envy,
                    "Haii aku envy."
            ));
        }
        return homePosts;
    }

    public static List<Post> getProfilePosts() {
        if (profilePosts == null) {
            profilePosts = new ArrayList<>();
            profilePosts.add(new Post(R.drawable.joy, "inside_out", R.drawable.angry, "Pemarah"));
            profilePosts.add(new Post(R.drawable.joy, "inside_out", R.drawable.embrassement, "Pemalu"));
            profilePosts.add(new Post(R.drawable.joy, "inside_out", R.drawable.envy, "Nda tau karena nd nonton s2."));
            profilePosts.add(new Post(R.drawable.joy, "inside_out", R.drawable.fear, "Nda tau karena nd nonton s2."));
            profilePosts.add(new Post(R.drawable.joy, "inside_out", R.drawable.anxiety, "Nda tau karena nd nonton s2."));
        }
        return profilePosts;
    }

    public static List<Highlight> getHighlights() {
        List<Highlight> highlights = new ArrayList<>();
        highlights.add(new Highlight(R.drawable.angry, "Marah"));
        highlights.add(new Highlight(R.drawable.disgust, "Disgust"));
        highlights.add(new Highlight(R.drawable.joy, "Joyfull"));
        highlights.add(new Highlight(R.drawable.sadness, "Sadness"));
        highlights.add(new Highlight(R.drawable.anxiety, "Anxi"));
        highlights.add(new Highlight(R.drawable.fear, "Fearness"));
        highlights.add(new Highlight(R.drawable.embrassement, "Embarassement"));
        return highlights;
    }
}
