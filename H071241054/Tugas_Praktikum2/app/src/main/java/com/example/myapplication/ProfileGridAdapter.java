package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProfileGridAdapter extends RecyclerView.Adapter<ProfileGridAdapter.ViewHolder> {

    private Context context;
    private List<Post> postList;

    public ProfileGridAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grid_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = postList.get(position);

        if (post.getImageUri() != null) {
            holder.ivGridImage.setImageURI(Uri.parse(post.getImageUri()));
        } else {
            holder.ivGridImage.setImageResource(post.getPostImage());
        }

        holder.ivGridImage.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailFeedActivity.class);
            intent.putExtra("PROFILE_IMG", post.getProfileImage());
            if (post.getImageUri() != null) {
                intent.putExtra("POST_IMG_URI", post.getImageUri());
            } else {
                intent.putExtra("POST_IMG", post.getPostImage());
            }
            intent.putExtra("USERNAME", post.getUsername());
            intent.putExtra("CAPTION", post.getCaption());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGridImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGridImage = itemView.findViewById(R.id.ivGridImage);
        }
    }
}
