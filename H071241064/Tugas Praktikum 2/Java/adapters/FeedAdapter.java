package com.example.instagramapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramapp.R;
import com.example.instagramapp.activities.DetailPostActivity;
import com.example.instagramapp.activities.ProfileActivity;
import com.example.instagramapp.models.Post;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private Context context;
    private List<Post> posts;
    private boolean isHomeFeed;

    public FeedAdapter(Context context, List<Post> posts, boolean isHomeFeed) {
        this.context = context;
        this.posts = posts;
        this.isHomeFeed = isHomeFeed;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.tvUsername.setText(post.getUsername());
        holder.tvCaption.setText(post.getCaption());
        holder.tvLikes.setText(post.getLikes() + " likes");
        holder.tvTime.setText(post.getTimeAgo());

        int avatarResId = getDrawableId(post.getUserAvatar());
        if (avatarResId != 0) {
            holder.ivAvatar.setImageResource(avatarResId);
        } else {
            holder.ivAvatar.setImageResource(R.drawable.placeholder_avatar);
        }

        int postResId = getDrawableId(post.getImageRes());
        if (postResId != 0) {
            holder.ivPost.setImageResource(postResId);
        } else {
            holder.ivPost.setImageResource(R.drawable.placeholder_post);
        }

        if (isHomeFeed) {
            View.OnClickListener profileClick = v -> {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("username", post.getUsername());
                intent.putExtra("avatar", post.getUserAvatar());
                context.startActivity(intent);
            };
            holder.ivAvatar.setOnClickListener(profileClick);
            holder.tvUsername.setOnClickListener(profileClick);
        }

        holder.ivPost.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailPostActivity.class);
            intent.putExtra("imageRes", post.getImageRes());
            intent.putExtra("username", post.getUsername());
            intent.putExtra("caption", post.getCaption());
            intent.putExtra("likes", post.getLikes());
            intent.putExtra("timeAgo", post.getTimeAgo());
            intent.putExtra("avatar", post.getUserAvatar());
            context.startActivity(intent);
        });

        holder.ivLike.setOnClickListener(v -> {
            holder.ivLike.setImageResource(R.drawable.ic_heart_filled);
        });
    }

    private int getDrawableId(String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    @Override
    public int getItemCount() { return posts.size(); }

    static class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar, ivPost, ivLike, ivComment, ivShare, ivSave;
        TextView tvUsername, tvCaption, tvLikes, tvTime;

        FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            ivPost = itemView.findViewById(R.id.ivPost);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivComment = itemView.findViewById(R.id.ivComment);
            ivShare = itemView.findViewById(R.id.ivShare);
            ivSave = itemView.findViewById(R.id.ivSave);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}