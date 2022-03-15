package com.example.mixipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    List<Model> values;
    Context mCtx;

    public RecyclerViewAdapter(List<Model> values, Context mCtx) {
        this.values = values;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        Model m = values.get(position);
        h.title.setText(m.title);
        Glide.with(mCtx).load(m.image).placeholder(R.mipmap.ic_launcher).into(h.imageView);

        h.like.setOnClickListener(view -> Toast.makeText(mCtx, "like", Toast.LENGTH_SHORT).show());
        h.unlike.setOnClickListener(view -> Toast.makeText(mCtx, "unlike", Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        FloatingActionButton like, unlike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.image);
            like = itemView.findViewById(R.id.like);
            unlike = itemView.findViewById(R.id.unlike);
        }
    }
}
