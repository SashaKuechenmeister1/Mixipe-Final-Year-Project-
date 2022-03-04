package com.example.mixipe;

import android.content.Context;

import com.example.mixipe.Listeners.RandomRecipeListener;
import com.example.mixipe.Listeners.RecipeDetailsListener;
import com.example.mixipe.Models.DetailsResponse;
import com.example.mixipe.Models.apiRandomRecipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    // Method to access random recipes interface
    public void getRandomRecipe(RandomRecipeListener listener, List<String> tags){
        RandomRecipeCall randomRecipeCall = retrofit.create(RandomRecipeCall.class);
        Call<apiRandomRecipe> call = randomRecipeCall.RandomRecipeCall(context.getString(R.string.apiKey), "3", tags);
        call.enqueue(new Callback<apiRandomRecipe>() {
            @Override
            public void onResponse(Call<apiRandomRecipe> call, Response<apiRandomRecipe> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<apiRandomRecipe> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    // Method to access recipe details interface
    public void getRecipeDetails(RecipeDetailsListener listener, int id) {
        RecipeDetailsCall recipeDetailsCall = retrofit.create(RecipeDetailsCall.class);
        Call<DetailsResponse> call = recipeDetailsCall.recipeDetailsCall(id, context.getString(R.string.apiKey));
        call.enqueue(new Callback<DetailsResponse>() {
            @Override
            public void onResponse(Call<DetailsResponse> call, Response<DetailsResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<DetailsResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    private interface RandomRecipeCall {
        @GET("recipes/random")
        Call<apiRandomRecipe> RandomRecipeCall(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }

    //interface for recipe details
    private interface RecipeDetailsCall {
        @GET("recipes/{id}/information")
        Call<DetailsResponse> recipeDetailsCall (
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

}
