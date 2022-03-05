package com.example.mixipe.Listeners;

import com.example.mixipe.Models.SimilarRecipe;

import java.util.List;

public interface SimilarRecipeListener {
    void didFetch(List<SimilarRecipe> response, String message);
    void didError(String message);
}
