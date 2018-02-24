package com.nitharshanaan.android.baker.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.nitharshanaan.android.baker.R;
import com.nitharshanaan.android.baker.data.Recipe;
import com.nitharshanaan.android.baker.network.RecipeService;
import com.nitharshanaan.android.baker.utils.RecipeImageHelper;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Response;

import static com.nitharshanaan.android.baker.utils.AppConstants.SELECTED_RECIPE;


public class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = RecipeRemoteViewsFactory.class.getSimpleName();
    public static String SELECTED_RECIPES = "selected_recipes";

    Context context;
    List<Recipe> recipes;

    public RecipeRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
    }

    private void setRecipes(Intent intent) {
        recipes = intent.getParcelableExtra(SELECTED_RECIPES);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        try {
            Response<List<Recipe>> response = RecipeService.getInstance().getAllRecipe().execute();
            if (response.isSuccessful()) {
                recipes = response.body();
            } else {
                Log.i(TAG, String.format("Message : %s,Response code: %s in widget", response.message(), response.code()));
                recipes = Collections.emptyList();
            }
        } catch (IOException e) {
            e.printStackTrace();
            recipes = Collections.emptyList();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION || recipes.isEmpty()) {
            return null;
        }

        Recipe selectedRecipe = recipes.get(position);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_list_item);
        rv.setTextViewText(R.id.tv_recipe_name, selectedRecipe.getName());
        rv.setTextViewText(R.id.tv_recipe_servings, selectedRecipe.getServings());
        rv.setImageViewResource(R.id.iv_recipe_image, RecipeImageHelper.getRecipeImage(selectedRecipe.getName()));

        Bundle extras = new Bundle();
        extras.putParcelable(SELECTED_RECIPE, Parcels.wrap(selectedRecipe));
        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(SELECTED_RECIPE, extras);
        rv.setOnClickFillInIntent(R.id.recipe_widget_item_container, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
