package com.bobnono.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bobnono.bakingapp.model.RecipeModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2017-08-06.
 */

public class StepFragment extends Fragment {
    String TAG = StepFragment.class.getSimpleName();

    @BindView(R.id.step_textview) TextView mStepTextview;

    private static RecipeModel mRecipe;
    private static int mPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        ButterKnife.bind(this, rootView);

        if (mRecipe != null) {
            showStep();
        }
        else {
            mStepTextview.setText("hello from tablet");
        }

        return rootView;
    }

    void showStep(){
        int stepId = mRecipe.getStep(mPosition - 1).getId();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mRecipe.getName()
                + " - Recipe Step " + (stepId > 0 ? stepId : ""));

        mStepTextview.setText(mRecipe.getStep(mPosition - 1).getDescription());
    }

    public void setRecipe(RecipeModel recipe){
        mRecipe = recipe;
    }

    public void setPosition(int position){
        mPosition = position;
    }

}
