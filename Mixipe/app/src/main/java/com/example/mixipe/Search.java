package com.example.mixipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mixipe.Adapters.RecipeAdapter;
import com.example.mixipe.Listeners.RandomRecipeListener;
import com.example.mixipe.Listeners.RecipeOnClickListener;
import com.example.mixipe.Models.RandomRecipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class Search extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    ProgressDialog progressDialog;
    RequestManager requestManager;
    RecipeAdapter recipeAdapter;
    RecyclerView recyclerView;
    Spinner spinner;
    List<String> tags = new ArrayList<>();
    SearchView searchView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading recipes...");

        //Search Bar
        searchView = findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tags.clear();
                tags.add(query);
                requestManager.getRandomRecipe(randomRecipeListener, tags);
                progressDialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //Spinner Tags
        spinner = findViewById(R.id.spinnerTags);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tags,
                R.layout.spinner_text
        );
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(selectedSpinnerListener);

        requestManager = new RequestManager(this);

        //getting bottom navigation view and attaching the listener
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigator);
        bottomNav.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);

    }

    private final RandomRecipeListener randomRecipeListener = new RandomRecipeListener() {
        @Override
        public void didFetch(RandomRecipe response, String message) {
            progressDialog.dismiss();
            recyclerView = findViewById(R.id.random_recycler);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(Search.this, 1));
            recipeAdapter = new RecipeAdapter(Search.this, response.recipes, recipeOnClickListener);
            recyclerView.setAdapter(recipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(Search.this, "error", Toast.LENGTH_SHORT);
        }
    };

    // OnClickListener for the spinner tags
    private final AdapterView.OnItemSelectedListener selectedSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            tags.clear();
            tags.add(adapterView.getSelectedItem().toString());
            requestManager.getRandomRecipe(randomRecipeListener, tags);
            progressDialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    // OnClickListener for when user click on a recipe
    private final RecipeOnClickListener recipeOnClickListener = new RecipeOnClickListener() {
        @Override
        public void onRecipeClick(String id) {
            startActivity(new Intent(Search.this, RecipeDetails.class)
            .putExtra("id", id));
        }
    };

    /**** bottom navigation bar ****/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // switch statement to change between activities
        switch (item.getItemId()) {

            case R.id.search:
                return true;

            case R.id.swipe:
                startActivity(new Intent(getApplicationContext(), Swipe.class));
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




