package com.example.mixipe.Listeners;

import com.example.mixipe.Models.Method;

import java.util.List;

public interface RecipeMethodListener {
    void didFetch(List<Method> response, String message );
    void didError(String message);
}
