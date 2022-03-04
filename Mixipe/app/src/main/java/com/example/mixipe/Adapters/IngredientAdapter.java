package com.example.mixipe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mixipe.Models.ExtendedIngredient;
import com.example.mixipe.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientsViewHolder>{

    Context context;
    List<ExtendedIngredient> list;

    public IngredientAdapter(Context context, List<ExtendedIngredient> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_ingredients, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.Ingredients_Title.setText(list.get(position).name);
        holder.Ingredients_Title.setSelected(true);
        holder.Ingredients_Quantity.setText(list.get(position).original);
        holder.Ingredients_Quantity.setSelected(true);
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/"+list.get(position).image).into(holder.Ingredients_Image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class IngredientsViewHolder extends RecyclerView.ViewHolder {

    TextView Ingredients_Quantity, Ingredients_Title;
    ImageView Ingredients_Image;

    public IngredientsViewHolder (@NonNull View itemView) {
        super(itemView);

        Ingredients_Quantity = itemView.findViewById(R.id.Ingredients_Quantity);
        Ingredients_Title = itemView.findViewById(R.id.Ingredients_Title);
        Ingredients_Image = itemView.findViewById(R.id.Ingredients_Image);

    }

}
