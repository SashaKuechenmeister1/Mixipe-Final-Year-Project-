package com.example.mixipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mixipe.Adapters.RecipeAdapter;
import com.example.mixipe.Listeners.RandomRecipeListener;
import com.example.mixipe.Models.apiRandomRecipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Search extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    ProgressDialog progressDialog;
    RequestManager requestManager;
    RecipeAdapter recipeAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading recipes...");

        requestManager = new RequestManager(this);
        requestManager.getRandomRecipe(randomRecipeListener);
        progressDialog.show();

    }

    private final RandomRecipeListener randomRecipeListener = new RandomRecipeListener() {
        @Override
        public void didFetch(apiRandomRecipe response, String message) {
            progressDialog.dismiss();
            recyclerView = findViewById(R.id.random_recycler);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(Search.this, 1));
            recipeAdapter = new RecipeAdapter(Search.this, response.recipes);
            recyclerView.setAdapter(recipeAdapter);
        }

        @Override
        public void didError(String message) {
            progressDialog.dismiss();
            Toast.makeText(Search.this, "error", Toast.LENGTH_SHORT);
        }
    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.search:
                return true;

            case R.id.swipe:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(0, 0);
                return true;

            case R.id.liked:
                startActivity(new Intent(getApplicationContext(), Liked.class));
                overridePendingTransition(0, 0);
                return true;

        }

        return false;
    }
}




