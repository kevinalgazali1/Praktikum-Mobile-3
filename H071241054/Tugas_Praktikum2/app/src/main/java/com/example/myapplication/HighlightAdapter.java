package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.ViewHolder> {

    private Context context;
    private List<Highlight> highlightList;

    public HighlightAdapter(Context context, List<Highlight> highlightList) {
        this.context = context;
        this.highlightList = highlightList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_highlight, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Highlight highlight = highlightList.get(position);

        holder.ivHighlight.setImageResource(highlight.getImage());
        holder.tvHighlightTitle.setText(highlight.getTitle());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailStoryActivity.class);
            intent.putExtra("STORY_IMG", highlight.getImage());
            intent.putExtra("STORY_TITLE", highlight.getTitle());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return highlightList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHighlight;
        TextView tvHighlightTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHighlight = itemView.findViewById(R.id.ivHighlight);
            tvHighlightTitle = itemView.findViewById(R.id.tvHighlightTitle);
        }
    }
}
