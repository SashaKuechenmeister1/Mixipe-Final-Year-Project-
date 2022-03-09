package com.example.mixipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mixipe.Adapters.IngredientAdapter;
import com.example.mixipe.Adapters.MethodAdapter;
import com.example.mixipe.Adapters.SimilarAdapter;
import com.example.mixipe.Listeners.RecipeMethodListener;
import com.example.mixipe.Listeners.RecipeOnClickListener;
import com.example.mixipe.Listeners.RecipeDetailsListener;
import com.example.mixipe.Listeners.SimilarRecipeListener;
import com.example.mixipe.Models.Details;
import com.example.mixipe.Models.Method;
import com.example.mixipe.Models.SimilarRecipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetails extends AppCompatActivity {

    int id;
    TextView Meal_Name, Meal_Source, Meal_Description;
    ImageView Meal_Image;
    RecyclerView Meal_Ingredients, Meal_Similar, Meal_Method;
    RequestManager requestManager;
    ProgressDialog progressDialog;
    IngredientAdapter ingredientAdapter;
    SimilarAdapter similarAdapter;
    MethodAdapter methodAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        findViewItems();

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        requestManager = new RequestManager(this);
        requestManager.getRecipeDetails(recipeDetailsListener, id);
        requestManager.getSimilarRecipe(similarRecipeListener, id);
        requestManager.getMethod(recipeMethodListener, id);
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
        Meal_Similar = findViewById(R.id.Meal_Similar);
        Meal_Method = findViewById(R.id.Meal_Method);
    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(Details response, String message) {
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

    private final SimilarRecipeListener similarRecipeListener = new SimilarRecipeListener() {
        @Override
        public void didFetch(List<SimilarRecipe> response, String message) {
            Meal_Similar.setHasFixedSize(true);
            Meal_Similar.setLayoutManager(new LinearLayoutManager(RecipeDetails.this, LinearLayoutManager.HORIZONTAL, false));
            similarAdapter = new SimilarAdapter(RecipeDetails.this, response, recipeOnClickListener);
            Meal_Similar.setAdapter(similarAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetails.this, message, Toast.LENGTH_SHORT);
        }
    };

    private final RecipeOnClickListener recipeOnClickListener = new RecipeOnClickListener() {
        @Override
        public void onRecipeClick(String id) {
            startActivity(new Intent(RecipeDetails.this, RecipeDetails.class)
            .putExtra("id", id));
        }
    };

    private final RecipeMethodListener recipeMethodListener = new RecipeMethodListener() {
        @Override
        public void didFetch(List<Method> response, String message) {
            Meal_Method.setHasFixedSize(true);
            Meal_Method.setLayoutManager(new LinearLayoutManager(RecipeDetails.this, LinearLayoutManager.VERTICAL, false));
            methodAdapter = new MethodAdapter(RecipeDetails.this, response);
            Meal_Method.setAdapter(methodAdapter);
        }

        @Override
        public void didError(String message) {

        }
    };
}