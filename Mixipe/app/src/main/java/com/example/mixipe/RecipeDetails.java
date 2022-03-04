package com.example.mixipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mixipe.Adapters.IngredientAdapter;
import com.example.mixipe.Adapters.RecipeAdapter;
import com.example.mixipe.Listeners.RecipeOnClickListener;
import com.example.mixipe.Listeners.RecipeDetailsListener;
import com.example.mixipe.Models.ExtendedIngredient;
import com.example.mixipe.Models.DetailsResponse;
import com.squareup.picasso.Picasso;

public class RecipeDetails extends AppCompatActivity {

    int id;
    TextView Meal_Name, Meal_Source, Meal_Description;
    ImageView Meal_Image;
    RecyclerView Meal_Ingredients;
    RequestManager requestManager;
    ProgressDialog progressDialog;
    IngredientAdapter ingredientAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        findViewItems();

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        requestManager = new RequestManager(this);
        requestManager.getRecipeDetails(recipeDetailsListener, id);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Details Loading...");
        progressDialog.show();
    }

    private void findViewItems() {
        Meal_Name = findViewById(R.id.Meal_Name);
        Meal_Source = findViewById(R.id.Meal_Source);
        Meal_Description = findViewById(R.id.Meal_Description);
        Meal_Image = findViewById(R.id.Meal_Image);
        Meal_Ingredients = findViewById(R.id.Meal_Ingredients);
    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(DetailsResponse response, String message) {
            progressDialog.dismiss();
            Meal_Name.setText(response.title);
            Meal_Source.setText(response.sourceName);
            Meal_Description.setText(response.summary);
            Picasso.get().load(response.image).into(Meal_Image);

            Meal_Ingredients.setHasFixedSize(true);
            Meal_Ingredients.setLayoutManager(new LinearLayoutManager(RecipeDetails.this, LinearLayoutManager.HORIZONTAL, false));
            ingredientAdapter = new IngredientAdapter(RecipeDetails.this, response.extendedIngredients);
            Meal_Ingredients.setAdapter(ingredientAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetails.this, message, Toast.LENGTH_SHORT);
        }
    };
}