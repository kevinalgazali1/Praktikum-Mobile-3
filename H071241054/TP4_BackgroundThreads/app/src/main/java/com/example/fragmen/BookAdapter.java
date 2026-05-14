package com.example.fragmen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private ArrayList<Book> listBuku;
    private Context context;

    public BookAdapter(Context context, ArrayList<Book> listBuku) {
        this.context = context;
        this.listBuku = listBuku;
    }

    public void setListBuku(ArrayList<Book> listBukuBaru) {
        this.listBuku = listBukuBaru;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book buku = listBuku.get(position);

        holder.tvTitle.setText(buku.getTitle());
        holder.tvAuthor.setText(buku.getAuthor());
        holder.tvYear.setText(String.valueOf(buku.getYear()));

        if (buku.getImageUri() != null && !buku.getImageUri().isEmpty()) {
            holder.ivCover.setImageURI(Uri.parse(buku.getImageUri()));
        } else {
            holder.ivCover.setImageResource(buku.getCoverImage());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("EXTRA_BOOK", buku);
                intent.putExtra("EXTRA_POSITION", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBuku.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAuthor, tvYear;
        ImageView ivCover;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvYear = itemView.findViewById(R.id.tvYear);
            ivCover = itemView.findViewById(R.id.ivCover);
        }
    }
}
