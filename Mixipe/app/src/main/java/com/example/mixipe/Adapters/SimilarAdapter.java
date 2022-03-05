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

import com.example.mixipe.Listeners.RecipeOnClickListener;
import com.example.mixipe.Models.SimilarRecipe;
import com.example.mixipe.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimilarAdapter extends RecyclerView.Adapter<SimilarViewHolder> {

    Context context;
    List<SimilarRecipe> list;
    RecipeOnClickListener listener;

    //Constructor
    public SimilarAdapter(Context context, List<SimilarRecipe> list, RecipeOnClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SimilarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarViewHolder(LayoutInflater.from(context).inflate(R.layout.list_similar, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarViewHolder holder, int position) {
        holder.Similar_Title.setText(list.get(position).title);
        holder.Similar_Title.setSelected(true);
        holder.Similar_Serving.setText(list.get(position).servings+" Servings");
        holder.Similar_Serving.setSelected(true);
        Picasso.get().load("https://spoonacular.com/recipeImages/"+list.get(position).id+"-556x370."+list.get(position).imageType).into(holder.Similar_Image);

        //Enables user to click on the similar recipe to open it and see more details
        holder.Similar_Holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeClick(String.valueOf(list.get(holder.getAdapterPosition()).id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class SimilarViewHolder extends RecyclerView.ViewHolder {

    CardView Similar_Holder;
    TextView Similar_Title, Similar_Serving;
    ImageView Similar_Image;

    public SimilarViewHolder(@NonNull View itemView) {
        super(itemView);
        Similar_Holder = itemView.findViewById(R.id.Similar_Holder);
        Similar_Title = itemView.findViewById(R.id.Similar_Title);
        Similar_Serving = itemView.findViewById(R.id.Similar_Serving);
        Similar_Image = itemView.findViewById(R.id.Similar_Image);
    }
}