package com.example.instagramapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramapp.R;
import com.example.instagramapp.activities.DetailPostActivity;
import com.example.instagramapp.models.Post;

import java.util.List;

public class GridPostAdapter extends RecyclerView.Adapter<GridPostAdapter.GridViewHolder> {

    private Context context;
    private List<Post> posts;
    private String ownerUsername;

    public GridPostAdapter(Context context, List<Post> posts, String ownerUsername) {
        this.context = context;
        this.posts = posts;
        this.ownerUsername = ownerUsername;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grid_post, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        Post post = posts.get(position);

        String imageRes = post.getImageRes();
        if (imageRes != null && (imageRes.startsWith("content://") || imageRes.startsWith("file://"))) {
            holder.ivGridImage.setImageURI(Uri.parse(imageRes));
        } else {
            int resId = context.getResources().getIdentifier(
                    imageRes, "drawable", context.getPackageName());
            holder.ivGridImage.setImageResource(resId != 0 ? resId : R.drawable.placeholder_post);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailPostActivity.class);
            intent.putExtra("imageRes", post.getImageRes());
            intent.putExtra("username", post.getUsername());
            intent.putExtra("caption", post.getCaption());
            intent.putExtra("likes", post.getLikes());
            intent.putExtra("timeAgo", post.getTimeAgo());
            intent.putExtra("avatar", post.getUserAvatar());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return posts.size(); }

    static class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGridImage;

        GridViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGridImage = itemView.findViewById(R.id.ivGridImage);
        }
    }
}