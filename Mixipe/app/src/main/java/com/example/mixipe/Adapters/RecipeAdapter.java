package com.example.mixipe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mixipe.Models.Recipe;
import com.example.mixipe.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder>{

    Context context;
    List<Recipe> list;

    public RecipeAdapter(Context context, List<Recipe> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.TitleView.setText(list.get(position).title);
        holder.TitleView.setSelected(true);
        holder.rating.setText("Likes: "+list.get(position).aggregateLikes);
        holder.servings_size.setText("Servings: "+list.get(position).servings);
        holder.cooking_time.setText("Minutes: "+list.get(position).readyInMinutes);

        Picasso.get().load(list.get(position).image).into(holder.RecipeImageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class RecipeViewHolder extends RecyclerView.ViewHolder {
    CardView list_container;
    TextView TitleView, servings_size, rating, cooking_time;
    ImageView RecipeImageView;


    public RecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        list_container = itemView.findViewById(R.id.list_container);
        TitleView = itemView.findViewById(R.id.TitleView);
        servings_size = itemView.findViewById(R.id.servings_size);
        rating = itemView.findViewById(R.id.rating);
        cooking_time = itemView.findViewById(R.id.cooking_time);
        RecipeImageView = itemView.findViewById(R.id.RecipeImageView);

    }
}
