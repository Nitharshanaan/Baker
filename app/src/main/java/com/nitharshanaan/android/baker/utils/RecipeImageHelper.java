package com.nitharshanaan.android.baker.utils;


import com.nitharshanaan.android.baker.R;

public final class RecipeImageHelper {

    private RecipeImageHelper() {
        throw new AssertionError();
    }

    public static int getRecipeImage(String recipeName) {
        switch (recipeName) {
            case "Nutella Pie":
                return R.drawable.nutellapie;
            case "Brownies":
                return R.drawable.brownie;
            case "Yellow Cake":
                return R.drawable.yellowcake;
            case "Cheesecake":
                return R.drawable.cheesecake;
        }
        return R.drawable.questionmark;
    }
}
