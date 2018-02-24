package com.nitharshanaan.android.baker;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import com.nitharshanaan.android.baker.data.Recipe;
import com.nitharshanaan.android.baker.data.Step;
import com.nitharshanaan.android.baker.network.RecipeService;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.nitharshanaan.android.baker.utils.AppConstants.FETCHED_RECIPES;
import static com.nitharshanaan.android.baker.utils.AppConstants.SELECTED_RECIPE;
import static org.hamcrest.CoreMatchers.is;


@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

    private static List<Step> steps;
    private static List<Recipe> recipes;

    @Rule
    public ActivityTestRule<RecipeActivity> mActivityTestRule = new ActivityTestRule<>(RecipeActivity.class, false, false);

    @BeforeClass
    public static void FetchRecipe() throws IOException {
        recipes = RecipeService.getInstance().getAllRecipe().execute().body();
    }

    private static ViewInteraction matchToolbarTitle(
            CharSequence title) {
        return onView(isAssignableFrom(Toolbar.class))
                .check(matches(withToolbarTitle(is(title))));
    }

    private static Matcher<Object> withToolbarTitle(final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override
            public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }

    @Before
    public void startActivity() {
//        pass first recipe has selected recipe to be displayed
        Intent recipeIntent = new Intent();
        recipeIntent.putExtra(SELECTED_RECIPE, Parcels.wrap(recipes.get(0)));
        recipeIntent.putExtra(FETCHED_RECIPES, Parcels.wrap(recipes));
        mActivityTestRule.launchActivity(recipeIntent);
    }

    @Test
    public void clickRecipeStepsRecyclerViewItem_ChangeActivityToolbarTitleToRecipeShortDescription() {

        // Click recipe step at position 0
        onView(withId(R.id.steps_recycler_view))
                .perform(actionOnItemAtPosition(0, click()));

        matchToolbarTitle(recipes.get(0).getSteps().get(0).getShortDescription());
    }
}
