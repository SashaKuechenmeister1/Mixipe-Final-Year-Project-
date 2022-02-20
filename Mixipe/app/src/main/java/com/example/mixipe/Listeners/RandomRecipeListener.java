package com.example.mixipe.Listeners;

import com.example.mixipe.Models.apiRandomRecipe;

public interface RandomRecipeListener {

    void didFetch(apiRandomRecipe response, String message);
    void didError(String message);

}
