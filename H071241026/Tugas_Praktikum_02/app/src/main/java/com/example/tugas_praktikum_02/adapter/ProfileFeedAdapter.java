package com.example.tugas_praktikum_02.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugas_praktikum_02.R;
import com.example.tugas_praktikum_02.model.Post;

import java.util.ArrayList;

public class ProfileFeedAdapter
        extends RecyclerView.Adapter<ProfileFeedAdapter.ViewHolder> {

    private ArrayList<Post> posts; //simoan data postingan profile

    public interface OnFeedClickListener { //Menangani klik pada post
        void onFeedClick(Post post);
    }

    private OnFeedClickListener feedClickListener;

    public ProfileFeedAdapter( //Menerima data dan listener dari Activity
            ArrayList<Post> posts,
            OnFeedClickListener listener) {

        this.posts = posts;
        this.feedClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_profile_feed,
                                parent,
                                false); //tampilan grid post

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( //mengisi gambar ke grid
            @NonNull ViewHolder holder,
            int position) {

        Post post = posts.get(position);

        holder.ivGridPost.setImageResource(
                post.getPostImage());

        holder.itemView.setOnClickListener(v -> {

            if (feedClickListener != null) {
                feedClickListener.onFeedClick(post);
            }

        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // tambah post baru

    public void addPost(Post post) { //Menambahkan post baru ke posisi paling atas

        posts.add(0, post);
        notifyItemInserted(0);

    }

    public class ViewHolder
            extends RecyclerView.ViewHolder {

        ImageView ivGridPost; //Menyimpan komponen tampilan

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivGridPost =
                    itemView.findViewById(R.id.iv_grid_post);
        }
    }
}