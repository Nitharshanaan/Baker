package com.nitharshanaan.android.baker.network;

import com.nitharshanaan.android.baker.data.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface RecipeClient {

    @GET("baking.json")
    Call<List<Recipe>> getAllRecipe();
}
