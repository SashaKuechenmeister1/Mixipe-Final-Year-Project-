package com.example.mixipe.Listeners;

import com.example.mixipe.Models.DetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(DetailsResponse response, String message);
    void didError(String message);
}
