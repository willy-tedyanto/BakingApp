package com.bobnono.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobnono.bakingapp.model.RecipeModel;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

/**
 * Created by user on 2017-08-06.
 */

public class RecipeStepsFragment extends Fragment{

    String TAG = RecipeStepsFragment.class.getSimpleName();

    private static RecipeModel mRecipe;
    private static int mPosition;

    private boolean mIsOrientationLandscape;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mIsOrientationLandscape = (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE);

        View rootView = inflater.inflate(
                (mIsOrientationLandscape
                        ? R.layout.fragment_recipe_steps_landscape
                        : R.layout.fragment_recipe_steps
                ),
                container,
                false);

        replaceFragment();

        return rootView;
    }

    void replaceFragment(){
        if (!mIsOrientationLandscape) {
            StepFragment stepFragment = new StepFragment();
            stepFragment.setRecipe(mRecipe);
            stepFragment.setPosition(mPosition);
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.recipe_steps_container, stepFragment)
                    .commit();

            NavigatorFragment navigatorFragment = new NavigatorFragment();
            navigatorFragment.setRecipe(mRecipe);
            navigatorFragment.setPosition(mPosition);
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.navigator_container, navigatorFragment)
                    .commit();
        }

        MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
        mediaPlayerFragment.setRecipe(mRecipe);
        mediaPlayerFragment.setPosition(mPosition);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.media_player_container, mediaPlayerFragment)
                .commit();



    }

    public void setRecipe(RecipeModel recipe){
        mRecipe = recipe;
    }

    public void setPosition(int position){
        mPosition = position;
    }

}
