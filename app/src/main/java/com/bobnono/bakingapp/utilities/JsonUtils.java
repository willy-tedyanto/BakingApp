package com.bobnono.bakingapp.utilities;

import com.bobnono.bakingapp.model.RecipeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by user on 2017-08-02.
 */

public class JsonUtils {

    public static ArrayList<RecipeModel> getRecipeListsFromJson(String jsonString)
            throws JSONException {

        final String TAG = JsonUtils.class.getSimpleName();

        final String RCP_ID = "id";
        final String RCP_NAME = "name";
        final String RCP_INGREDIENTS = "ingredients";
        final String RCP_INGREDIENTS_QTY = "quantity";
        final String RCP_INGREDIENTS_MEASURE = "measure";
        final String RCP_INGREDIENTS_INGREDIENT = "ingredient";
        final String RCP_STEPS = "steps";
        final String RCP_STEPS_ID = "id";
        final String RCP_STEPS_SHORT_DESC = "shortDescription";
        final String RCP_STEPS_DESC = "description";
        final String RCP_STEPS_VIDEO_URL = "videoURL";
        final String RCP_STEPS_THUMBNAIL_URL = "thumbnailURL";
        final String RCP_SERVINGS = "servings";
        final String RCP_IMAGE = "image";


        ArrayList<RecipeModel> recipesList = new ArrayList<>();

        JSONArray recipesArray = new JSONArray(jsonString);

        for (int i = 0; i < recipesArray.length(); i++){
            JSONObject recipeItemObject = recipesArray.getJSONObject(i);

            RecipeModel recipeItem = new RecipeModel();

            recipeItem.setId(recipeItemObject.getInt(RCP_ID));
            recipeItem.setName(recipeItemObject.getString(RCP_NAME));
            recipeItem.setServings(recipeItemObject.getInt(RCP_SERVINGS));
            recipeItem.setImageLocation(recipeItemObject.getString(RCP_IMAGE));

            JSONArray recipeIngredientArray = recipeItemObject.getJSONArray(RCP_INGREDIENTS);
            for (int n = 0; n < recipeIngredientArray.length(); n++){
                JSONObject ingredientObject = recipeIngredientArray.getJSONObject(n);

                RecipeModel.Ingredient ingredient = new RecipeModel.Ingredient();

                ingredient.setQuantity(ingredientObject.getInt(RCP_INGREDIENTS_QTY));
                ingredient.setMeasure(ingredientObject.getString(RCP_INGREDIENTS_MEASURE));
                ingredient.setIngredient(ingredientObject.getString(RCP_INGREDIENTS_INGREDIENT));

                recipeItem.setIngredients(ingredient);
            }

            JSONArray recipeStepArray = recipeItemObject.getJSONArray(RCP_STEPS);
            for (int n = 0; n < recipeStepArray.length(); n++){
                JSONObject stepObject = recipeStepArray.getJSONObject(n);

                RecipeModel.Step step = new RecipeModel.Step();

                step.setId(stepObject.getInt(RCP_STEPS_ID));
                step.setShortDescription(stepObject.getString(RCP_STEPS_SHORT_DESC));
                step.setDescription(stepObject.getString(RCP_STEPS_DESC));
                step.setVideoUrl(stepObject.getString(RCP_STEPS_VIDEO_URL));
                step.setThumbnailUrl(stepObject.getString(RCP_STEPS_THUMBNAIL_URL));

                recipeItem.setSteps(step);
            }

            recipesList.add(recipeItem);
        }

        return recipesList;
    }

}
