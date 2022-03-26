package com.example.mixipe;

import android.content.Context;

import com.example.mixipe.Listeners.RandomRecipeListener;
import com.example.mixipe.Listeners.RecipeDetailsListener;
import com.example.mixipe.Listeners.RecipeMethodListener;
import com.example.mixipe.Listeners.SimilarRecipeListener;
import com.example.mixipe.Models.Details;
import com.example.mixipe.Models.Method;
import com.example.mixipe.Models.SimilarRecipe;
import com.example.mixipe.Models.RandomRecipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

//Class that handles all the API requests

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    // Method to access random recipes interface / get random recipes
    public void getRandomRecipe(RandomRecipeListener listener, List<String> tags){
        RandomRecipeCall randomRecipeCall = retrofit.create(RandomRecipeCall.class);
        // list 5 random recipes
        Call<RandomRecipe> call = randomRecipeCall.RandomRecipeCall(context.getString(R.string.apiKey), "5", tags);
        call.enqueue(new Callback<RandomRecipe>() {
            @Override
            public void onResponse(Call<RandomRecipe> call, Response<RandomRecipe> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipe> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    // Method to access recipe details interface / get recipe details
    public void getRecipeDetails(RecipeDetailsListener listener, int id) {
        RecipeDetailsCall recipeDetailsCall = retrofit.create(RecipeDetailsCall.class);
        Call<Details> call = recipeDetailsCall.recipeDetailsCall(id, context.getString(R.string.apiKey));
        call.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(Call<Details> call, Response<Details> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<Details> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    // Method to access similar recipe interface / get similar recipes
    public void getSimilarRecipe(SimilarRecipeListener listener, int id) {
        SimilarRecipeCall similarRecipeCall = retrofit.create(SimilarRecipeCall.class);
        // lists 5 similar recipes
        Call<List<SimilarRecipe>> call = similarRecipeCall.similarRecipeCall(id, "5", context.getString(R.string.apiKey));
        call.enqueue(new Callback<List<SimilarRecipe>>() {
            @Override
            public void onResponse(Call<List<SimilarRecipe>> call, Response<List<SimilarRecipe>> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<SimilarRecipe>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    //Method to access recipe instructions interface / get recipe instructions
    public void getMethod(RecipeMethodListener listener, int id) {
        MethodCall methodCall = retrofit.create(MethodCall.class);
        Call<List<Method>> call = methodCall.methodCall(id, context.getString(R.string.apiKey));
        call.enqueue(new Callback<List<Method>>() {
            @Override
            public void onResponse(Call<List<Method>> call, Response<List<Method>> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<Method>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    // interface to get random recipes
    private interface RandomRecipeCall {
        @GET("recipes/random")
        Call<RandomRecipe> RandomRecipeCall(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }

    // interface to get recipe details
    private interface RecipeDetailsCall {
        @GET("recipes/{id}/information")
        Call<Details> recipeDetailsCall (
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

    // interface to get recipe instructions
    private interface MethodCall {
        @GET("recipes/{id}/analyzedInstructions")
        Call<List<Method>> methodCall(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

    // interface to get similar recipes
    private interface SimilarRecipeCall {
        @GET("recipes/{id}/similar")
        Call<List<SimilarRecipe>> similarRecipeCall (
            @Path("id") int id,
            @Query("number") String number,
            @Query("apiKey") String apiKey
        );
    }


}
