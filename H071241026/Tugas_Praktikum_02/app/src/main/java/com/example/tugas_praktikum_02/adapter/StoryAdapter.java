package com.example.tugas_praktikum_02.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugas_praktikum_02.R;
import com.example.tugas_praktikum_02.model.Story;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryAdapter
        extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private ArrayList<Story> stories; //Menyimpan data highlight

    public interface OnStoryClickListener { //Menangani klik pada highlight
        void onStoryClick(Story story);
    }

    private OnStoryClickListener storyClickListener;

    public StoryAdapter( //Menerima data story dari Activity
            ArrayList<Story> stories,
            OnStoryClickListener listener) {

        this.stories = stories;
        this.storyClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( //Membuat tampilan highlight
            @NonNull ViewGroup parent,
            int viewType) {

        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_story, //tampilan highlight
                                parent,
                                false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( //Mengisi data ke tampilan highlight
            @NonNull ViewHolder holder,
            int position) {

        Story story = stories.get(position); //Mengambil data highlight berdasarkan posisi

        holder.ivStory.setImageResource(
                story.getCoverImage());

        holder.tvTitle.setText(
                story.getTitle());

        holder.itemView.setOnClickListener(v -> {

            if (storyClickListener != null) {
                storyClickListener.onStoryClick(story);
            }

        });
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder {

        CircleImageView ivStory;
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivStory =
                    itemView.findViewById(R.id.iv_story);

            tvTitle =
                    itemView.findViewById(R.id.tv_story_title);
        }
    }
}