package com.example.tugas_praktikum_02.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugas_praktikum_02.R;
import com.example.tugas_praktikum_02.model.Post;

import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private ArrayList<Post> posts; //simpan data feed
    private Context context;  //akses layout dan resource
    public interface OnProfileClickListener { //menangani klil profile
        void onProfileClick(Post post);
    }
 
    private OnProfileClickListener profileClickListener;

    public FeedAdapter(Context context,
                       ArrayList<Post> posts,
                       OnProfileClickListener listener) {

        this.context = context;
        this.posts = posts;
        this.profileClickListener = listener;
    } //menerima data dari activity


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)  //membuat tampilan item feed
                .inflate(R.layout.item_feed,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(  //mengambil data semua posisi, lalu kirim ke viewholder
            @NonNull ViewHolder holder,
            int position) {

        Post post = posts.get(position);
        holder.setData(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView ivProfile;
        private TextView tvUsername;
        private ImageView ivPost;
        private TextView tvCaption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfile = itemView.findViewById(R.id.iv_profile);
            tvUsername = itemView.findViewById(R.id.tv_username);
            ivPost = itemView.findViewById(R.id.iv_post);
            tvCaption = itemView.findViewById(R.id.tv_caption);
        }

        public void setData(Post post) {

            ivProfile.setImageResource(
                    post.getProfileImage());

            tvUsername.setText(
                    post.getUsername());

            ivPost.setImageResource(
                    post.getPostImage());

            tvCaption.setText(
                    post.getCaption());
            ivProfile.setOnClickListener(v -> {

                if (profileClickListener != null) {
                    profileClickListener
                            .onProfileClick(post);
                }

            });

            tvUsername.setOnClickListener(v -> {

                if (profileClickListener != null) {
                    profileClickListener
                            .onProfileClick(post);
                }

            });

        }
    }
}