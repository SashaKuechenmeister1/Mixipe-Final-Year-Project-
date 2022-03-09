package com.example.mixipe.Listeners;

import com.example.mixipe.Models.RandomRecipe;

public interface RandomRecipeListener {

    void didFetch(RandomRecipe response, String message);
    void didError(String message);

}
