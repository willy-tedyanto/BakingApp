package com.bobnono.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.bobnono.bakingapp.model.RecipeModel;

import java.util.ArrayList;

/**
 * Created by user on 2017-08-07.
 */

public class BakingWidgetService extends IntentService {
    String TAG = BakingWidgetService.class.getSimpleName();

    public static final String ACTION_UPDATE_BAKING_WIDGETS = "com.bobnono.bakingapp.action.update_baking_widgets";
    public static final String ACTION_CLICK_PREVIOUS = "com.bobnono.bakingapp.action.click_previous";
    public static final String ACTION_CLICK_NEXT = "com.bobnono.bakingapp.action.click_next";

    public static int recipePosition = 0;

    public BakingWidgetService() {
        super("BakingWidgetService");
    }

    public static void startActionUpdateRecipeWidgets(Context context) {
        Intent intent = new Intent(context, BakingWidgetService.class);
        intent.setAction(ACTION_UPDATE_BAKING_WIDGETS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && MainActivity.mRecipesList != null){
            final String action = intent.getAction();
            if (action == ACTION_CLICK_PREVIOUS){
                handleActionNavigation(ACTION_CLICK_PREVIOUS);
            } else if (action == ACTION_CLICK_NEXT){
                handleActionNavigation(ACTION_CLICK_NEXT);
            } else if (action == ACTION_UPDATE_BAKING_WIDGETS){
                handleActionUpdateRecipeWidgets();
            }
        }
    }

    private void handleActionNavigation(String action){
        if (action == ACTION_CLICK_NEXT){
            if (recipePosition < (MainActivity.mRecipesList.size() - 1)){
                recipePosition++;
            }
        } else if (action == ACTION_CLICK_PREVIOUS) {
            if (recipePosition > 0){
                recipePosition--;
            }
        }
        startActionUpdateRecipeWidgets(this);
    }

    private void handleActionUpdateRecipeWidgets(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));

        String recipeName = MainActivity.mRecipesList.get(recipePosition).getName();
        String ingredientsText = getIngredientsText();
        //Now update all widgets
        BakingWidgetProvider.updateBakingWidgets(this, appWidgetManager, recipeName, ingredientsText, appWidgetIds);
    }

    private String getIngredientsText(){
        ArrayList<RecipeModel.Ingredient> ingredients = MainActivity.mRecipesList.get(recipePosition).getAllIngredients();

        String ingredientText = "";
        for (RecipeModel.Ingredient ingredient : ingredients){
            ingredientText += (ingredient.getQuantity()
                    + " " + ingredient.getMeasure()
                    + " " + ingredient.getIngredient()
                    + "\n"
            );
        }
        return ingredientText;
    }
}
