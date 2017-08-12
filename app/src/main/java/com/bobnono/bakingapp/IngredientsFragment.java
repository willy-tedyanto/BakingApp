package com.bobnono.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bobnono.bakingapp.model.RecipeModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2017-08-06.
 */

public class IngredientsFragment extends Fragment {
    String TAG = IngredientsFragment.class.getSimpleName();

    @BindView(R.id.ingredients_textview) TextView mIngredientTextView;

    private RecipeModel mRecipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
Log.e(TAG, "on createview");
        ButterKnife.bind(this, rootView);

        showIngredients();

        return rootView;
    }

    void showIngredients(){
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mRecipe.getName()
                + " - " + getString(R.string.ingredients_term));

        ArrayList<RecipeModel.Ingredient> ingredients = mRecipe.getAllIngredients();

        for (RecipeModel.Ingredient ingredient :
                ingredients) {
            mIngredientTextView.append(ingredient.getQuantity()
                    + " " + ingredient.getMeasure()
                    + " " + ingredient.getIngredient() + "\n");
        }

    }

    public void setRecipe(RecipeModel recipe){
        mRecipe = recipe;
    }

}
