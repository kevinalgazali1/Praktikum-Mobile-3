package com.example.cinetracker;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder> {

    private List<Movie> watchlist;
    private OnDeleteClickListener deleteListener;
    private OnStatusClickListener statusListener;
    private int lastPosition = -1;

    public interface OnDeleteClickListener {
        void onDelete(Movie movie, int position);
    }

    public interface OnStatusClickListener {
        void onStatusClick(Movie movie, int position);
    }

    public WatchlistAdapter(List<Movie> watchlist,
                            OnDeleteClickListener deleteListener,
                            OnStatusClickListener statusListener) {
        this.watchlist      = watchlist;
        this.deleteListener = deleteListener;
        this.statusListener = statusListener;
    }

    @NonNull
    @Override
    public WatchlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_watchlist, parent, false);
        return new WatchlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistViewHolder holder, int position) {
        Movie movie = watchlist.get(position);

        holder.tvTitle.setText(movie.getTitle());
        holder.tvRating.setText(String.format("%.1f", movie.getVoteAverage()));
        holder.tvOverview.setText(movie.getOverview());

        String status = movie.getWatchStatus();
        holder.tvStatus.setText(status);
        switch (status) {
            case "Sedang Ditonton":
                holder.tvStatus.setBackgroundColor(Color.parseColor("#BBC8960C"));
                holder.tvStatus.setTextColor(Color.parseColor("#2C1A0E"));
                break;
            case "Sudah Ditonton":
                holder.tvStatus.setBackgroundColor(Color.parseColor("#BB4CAF50"));
                holder.tvStatus.setTextColor(Color.WHITE);
                break;
            default:
                holder.tvStatus.setBackgroundColor(Color.parseColor("#BB1B2A5E"));
                holder.tvStatus.setTextColor(Color.WHITE);
                break;
        }

        holder.tvStatus.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos != RecyclerView.NO_ID && statusListener != null) {
                statusListener.onStatusClick(movie, pos);
            }
        });

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.ivPoster);

        if (position > lastPosition) {
            Animation anim = AnimationUtils.loadAnimation(
                    holder.itemView.getContext(), R.anim.item_slide_up);
            anim.setStartOffset(position % 8 * 50L);
            holder.itemView.startAnimation(anim);
            lastPosition = position;
        }

        holder.btnDelete.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos != RecyclerView.NO_ID && deleteListener != null) {
                deleteListener.onDelete(movie, pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return watchlist != null ? watchlist.size() : 0;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull WatchlistViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public static class WatchlistViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle, tvRating, tvOverview, tvStatus;
        ImageButton btnDelete;

        public WatchlistViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster   = itemView.findViewById(R.id.iv_watchlist_poster);
            tvTitle    = itemView.findViewById(R.id.tv_watchlist_title);
            tvRating   = itemView.findViewById(R.id.tv_watchlist_rating);
            tvOverview = itemView.findViewById(R.id.tv_watchlist_overview);
            tvStatus   = itemView.findViewById(R.id.tv_watch_status);
            btnDelete  = itemView.findViewById(R.id.btn_delete_watchlist);
        }
    }
}