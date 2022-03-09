package com.example.mixipe.Listeners;

import com.example.mixipe.Models.Details;

public interface RecipeDetailsListener {
    void didFetch(Details response, String message);
    void didError(String message);
}
