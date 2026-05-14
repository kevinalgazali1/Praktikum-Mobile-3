package com.example.tugas_praktikum_02;

import com.example.tugas_praktikum_02.model.Post;
import com.example.tugas_praktikum_02.model.Story;

import java.util.ArrayList;

public class DataSource {
    public static ArrayList<Post> getHomeFeed() {

        ArrayList<Post> posts = new ArrayList<>();

        posts.add(new Post("Adel",
                R.drawable.adel,
                R.drawable.adel,
                "Halo dari Adel"));

        posts.add(new Post("Adit",
                R.drawable.adit,
                R.drawable.adit,
                "Postingan Adit"));

        posts.add(new Post("Anas",
                R.drawable.anas,
                R.drawable.anas,
                "Santai dulu"));

        posts.add(new Post("Dennis",
                R.drawable.dennis,
                R.drawable.dennis,
                "Hari cerah"));

        posts.add(new Post("Devi",
                R.drawable.devi,
                R.drawable.devi,
                "Mood bagus"));

        posts.add(new Post("Hjudin",
                R.drawable.hjudin,
                R.drawable.hjudin,
                "Ngopi dulu"));

        posts.add(new Post("Jarwo",
                R.drawable.jarwo,
                R.drawable.jarwo,
                "Weekend vibes"));

        posts.add(new Post("Mita",
                R.drawable.mita,
                R.drawable.mita,
                "Happy day"));

        posts.add(new Post("Sopo",
                R.drawable.sopo,
                R.drawable.sopo,
                "Santai sore"));

        posts.add(new Post("Ucup",
                R.drawable.ucup,
                R.drawable.ucup,
                "Semangat terus"));

        return posts;
    }

    // Feed profil (5 item)

    public static ArrayList<Post> getProfileFeed() {

        ArrayList<Post> posts = new ArrayList<>();

        posts.add(new Post(
                "adel",
                R.drawable.adel,
                R.drawable.feed1,
                "Postingan 1"));

        posts.add(new Post(
                "adel",
                R.drawable.adel,
                R.drawable.feed2,
                "Postingan 2"));

        posts.add(new Post(
                "adel",
                R.drawable.adel,
                R.drawable.feed3,
                "Postingan 3"));

        posts.add(new Post(
                "adel",
                R.drawable.adel,
                R.drawable.feed4,
                "Postingan 4"));

        posts.add(new Post(
                "adel",
                R.drawable.adel,
                R.drawable.feed5,
                "Postingan 5"));

        posts.add(new Post(
                "adel",
                R.drawable.adel,
                R.drawable.feed6,
                "Postingan 6"));

        posts.add(new Post(
                "adel",
                R.drawable.adel,
                R.drawable.feed7,
                "Postingan 7"));

        posts.add(new Post(
                "adel",
                R.drawable.adel,
                R.drawable.feed8,
                "Postingan 8"));

        posts.add(new Post(
                "adel",
                R.drawable.adel,
                R.drawable.feed9,
                "Postingan 9"));

        posts.add(new Post(
                "adel",
                R.drawable.adel,
                R.drawable.feed10,
                "Postingan 10"));

        return posts;
    }

    public static ArrayList<Story> getStories() {

        ArrayList<Story> stories =
                new ArrayList<>();

        stories.add(new Story(
                "Adel",
                R.drawable.adel));

        stories.add(new Story(
                "Adit",
                R.drawable.adit));

        stories.add(new Story(
                "Anas",
                R.drawable.anas));

        return stories;
    }


    public static ArrayList<Story> getHighlights() {

        ArrayList<Story> stories = new ArrayList<>();

        stories.add(new Story("Travel",
                R.drawable.adel));

        stories.add(new Story("Food",
                R.drawable.adit));

        stories.add(new Story("Friends",
                R.drawable.anas));

        stories.add(new Story("Work",
                R.drawable.devi));

        stories.add(new Story("Gym",
                R.drawable.jarwo));

        stories.add(new Story("Family",
                R.drawable.mita));

        stories.add(new Story("Pets",
                R.drawable.ucup));

        return stories;

    }
}