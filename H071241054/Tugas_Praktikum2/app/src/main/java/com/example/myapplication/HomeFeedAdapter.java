package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeFeedAdapter extends RecyclerView.Adapter<HomeFeedAdapter.ViewHolder> {

    private List<Post> postList;
    private Context context;

    public HomeFeedAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = postList.get(position);

        holder.ivProfile.setImageResource(post.getProfileImage());
        
        if (post.getImageUri() != null) {
            holder.ivPostImage.setImageURI(Uri.parse(post.getImageUri()));
        } else {
            holder.ivPostImage.setImageResource(post.getPostImage());
        }
        
        holder.tvUsername.setText(post.getUsername());
        holder.tvCaption.setText(post.getUsername() + " " + post.getCaption());

        // Klik header (foto profil/username) ke halaman Profil
        holder.layoutHeader.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        });

        // Klik gambar atau caption ke halaman Detail
        View.OnClickListener toDetail = v -> {
            Intent intent = new Intent(context, DetailFeedActivity.class);
            intent.putExtra("PROFILE_IMG", post.getProfileImage());
            intent.putExtra("USERNAME", post.getUsername());
            intent.putExtra("CAPTION", post.getCaption());
            if (post.getImageUri() != null) {
                intent.putExtra("POST_IMG_URI", post.getImageUri());
            } else {
                intent.putExtra("POST_IMG", post.getPostImage());
            }
            context.startActivity(intent);
        };

        holder.ivPostImage.setOnClickListener(toDetail);
        holder.tvCaption.setOnClickListener(toDetail);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile, ivPostImage;
        TextView tvUsername, tvCaption;
        LinearLayout layoutHeader;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.ivProfileUser);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            layoutHeader = itemView.findViewById(R.id.layoutHeader);
        }
    }
}
