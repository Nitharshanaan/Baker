package com.nitharshanaan.android.baker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.nitharshanaan.android.baker.adapter.OnListFragmentInteractionListener;
import com.nitharshanaan.android.baker.data.Recipe;
import com.nitharshanaan.android.baker.fragment.RecipesFragment;

import org.parceler.Parcels;

import java.util.List;

import static com.nitharshanaan.android.baker.utils.AppConstants.FETCHED_RECIPES;
import static com.nitharshanaan.android.baker.utils.AppConstants.SELECTED_RECIPE;

/**
 * Created by nitha on 10-02-2018.
 */

public class MainActivity extends AppCompatActivity implements OnListFragmentInteractionListener<Recipe>, RecipesFragment.OnRecipesFetchedListener {

    FragmentManager fragmentManager;
    List<Recipe> fetchedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipesFragment recipesFragment = new RecipesFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_container, recipesFragment).commit();
    }

    @Override
    public void onListFragmentInteraction(Recipe selectedRecipe) {
        Toast.makeText(this, "Selected recipe " + selectedRecipe.getName(), Toast.LENGTH_SHORT).show();
        Intent recipeIntent = new Intent(this, RecipeActivity.class);
        recipeIntent.putExtra(SELECTED_RECIPE, Parcels.wrap(selectedRecipe));
        recipeIntent.putExtra(FETCHED_RECIPES, Parcels.wrap(fetchedRecipes));
        startActivity(recipeIntent);
    }

    @Override
    public void onRecipesFetched(List<Recipe> fetchedRecipes) {
        this.fetchedRecipes = fetchedRecipes;
    }
}
