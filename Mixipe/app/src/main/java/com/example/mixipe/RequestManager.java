package com.example.mixipe;

import android.content.Context;

import com.example.mixipe.Listeners.RandomRecipeListener;
import com.example.mixipe.Models.apiRandomRecipe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
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

    public void getRandomRecipe(RandomRecipeListener listener){
        RandomRecipeCall randomRecipeCall = retrofit.create(RandomRecipeCall.class);
        Call<apiRandomRecipe> call = randomRecipeCall.RandomRecipeCall(context.getString(R.string.apiKey), "5");
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

    private interface RandomRecipeCall {
        @GET("recipes/random")
        Call<apiRandomRecipe> RandomRecipeCall(
                @Query("apiKey") String apiKey,
                @Query("number") String number
        );
    }
}
