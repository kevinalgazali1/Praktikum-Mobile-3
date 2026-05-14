package com.example.instagramapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramapp.R;
import com.example.instagramapp.activities.DetailStoryActivity;
import com.example.instagramapp.models.Story;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {

    private final Context context;
    private final List<Story> stories;

    public StoryAdapter(Context context, List<Story> stories) {
        this.context = context;
        this.stories = stories;
    }

    public StoryAdapter(Context context, List<Story> stories, boolean ignored) {
        this(context, stories);
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_story, parent, false);
        return new StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        Story story = stories.get(position);
        holder.tvStoryName.setText(story.getTitle());

        int resId = context.getResources().getIdentifier(
                story.getAvatarRes(), "drawable", context.getPackageName());
        holder.ivStoryAvatar.setImageResource(resId != 0 ? resId : R.drawable.placeholder_avatar);

        if (story.isViewed()) {
            ColorMatrix m = new ColorMatrix();
            m.setSaturation(0);
            holder.ivStoryAvatar.setColorFilter(new ColorMatrixColorFilter(m));
            holder.ivRing.setImageResource(R.drawable.circle_story_border_grey);
        } else {
            holder.ivStoryAvatar.setColorFilter(null);
            holder.ivRing.setImageResource(R.drawable.circle_story_border);
        }

        holder.itemView.setOnClickListener(v -> {
            story.setViewed(true);
            notifyItemChanged(position);

            Intent intent = new Intent(context, DetailStoryActivity.class);
            intent.putExtra("title", story.getTitle());
            intent.putExtra("coverImage", story.getCoverImage());
            intent.putExtra("content", story.getContent());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return stories.size(); }

    static class StoryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRing;
        com.google.android.material.imageview.ShapeableImageView ivStoryAvatar;
        TextView tvStoryName;

        StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRing        = itemView.findViewById(R.id.ivRing);
            ivStoryAvatar = itemView.findViewById(R.id.ivStoryAvatar);
            tvStoryName   = itemView.findViewById(R.id.tvStoryName);
        }
    }
}