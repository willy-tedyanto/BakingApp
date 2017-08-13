package com.bobnono.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class MasterListFragment extends Fragment
        implements MasterListAdapter.MasterListAdapterHandler{

    OnMasterListFragmentClickListener mCallback;

    public interface OnMasterListFragmentClickListener{
        void onMasterItemListSelected(RecipeModel recipe, int position);
    }

    String TAG = MasterListFragment.class.getSimpleName();
    private final String RECYCLERVIEW_LIST_STATE_KEY = "com.bobnono.bakingapp.masterlistfragment.recyclerview_list_state_key";

    private RecipeModel mRecipe;
    private MasterListAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    private static Parcelable mListState;

    @BindView(R.id.recipe_details_recycler_view) RecyclerView mRecipeRecyclerView;
    @BindView(R.id.recipe_name_tv) TextView mRecipeNameTextView;
    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        ButterKnife.bind(this, rootView);

        Intent intent = getActivity().getIntent();
        mRecipe = intent.getParcelableExtra(MainActivity.BUNDLE_RECIPE_DETAILS);

        mAdapter = new MasterListAdapter(getContext(), this);

        linearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecipeRecyclerView.setLayoutManager(linearLayoutManager);
        mRecipeRecyclerView.setHasFixedSize(true);
        mRecipeRecyclerView.setAdapter(mAdapter);
        mAdapter.setRecipeData(mRecipe);

        mRecipeNameTextView.setText(mRecipe.getName());
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mRecipe.getName());

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mCallback = (OnMasterListFragmentClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement OnMasterListFragmentClickListener");
        }
    }

    @Override
    public void onMasterListItemSelected(RecipeModel recipe, int index) {
        mListState = linearLayoutManager.onSaveInstanceState();
        mCallback.onMasterItemListSelected(recipe, index);
    }

    public void setRecipe(RecipeModel recipe){
        mRecipe = recipe;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mListState = linearLayoutManager.onSaveInstanceState();
        outState.putParcelable(RECYCLERVIEW_LIST_STATE_KEY, mListState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            Log.e(TAG, "onactivitycreated : after if ");
            mListState = savedInstanceState.getParcelable(RECYCLERVIEW_LIST_STATE_KEY);
            Log.e(TAG, "onactivitycreated : mlistState = " + (mListState == null));
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if (mListState != null){
            linearLayoutManager.onRestoreInstanceState(mListState);
        }
    }
}
