package com.bobnono.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bobnono.bakingapp.loader.RecipeLoader;
import com.bobnono.bakingapp.model.RecipeModel;
import com.bobnono.bakingapp.utilities.GeneralUtils;
import com.bobnono.bakingapp.utilities.JsonUtils;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
    implements RecipesAdapter.RecipesAdapterHandler,
        LoaderManager.LoaderCallbacks {

    private static final String TAG = MainActivity.class.getSimpleName();

    private final String BUNDLE_RECIPES_DATA = "com.bobnono.bakingapp.MainActiity.BUNDLE_RECIPES_DATA";
    public static final String BUNDLE_RECIPE_DETAILS = "com.bobnono.bakingapp.MainActiity.BUNDLE_RECIPE_DETAILS";
    private static final String SEARCH_QUERY_URL_EXTRA = "com.bobnono.bakingapp.MainActiity.SEARCH_QUERY_URL_EXTRA";
    private final int GRID_SCALING_FACTOR = 180;
    private static final int RECIPES_SEARCH_LOADER = 100;

    private RecipesAdapter mAdapter;

    public static ArrayList<RecipeModel> mRecipesList;

    @BindView(R.id.rv_recipes) RecyclerView mRecipesRecyclerView;
    @BindView(R.id.error_message_textview) TextView mErrorMessageDisplay;
    @BindView(R.id.loading_indicator) ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Log.e(TAG, "Oncreate");

        mAdapter = new RecipesAdapter(MainActivity.this, this);

        if (!(savedInstanceState == null) && !(savedInstanceState.getParcelableArrayList(BUNDLE_RECIPES_DATA) == null)) {
            ArrayList<RecipeModel> recipesData = savedInstanceState.getParcelableArrayList(BUNDLE_RECIPES_DATA);
            mAdapter.setRecipesData(recipesData);
        }

        GridLayoutManager layoutManager = new GridLayoutManager(this,
                GeneralUtils.calculateNoOfColumns(this, GRID_SCALING_FACTOR));

        mRecipesRecyclerView.setLayoutManager(layoutManager);
        mRecipesRecyclerView.setHasFixedSize(true);
        mRecipesRecyclerView.setAdapter(mAdapter);

        loadRecipesData();
    }

    private void showErrorMessage(){
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mRecipesRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void showRecipesDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecipesRecyclerView.setVisibility(View.VISIBLE);
    }

    private void loadRecipesData(){
        Bundle queryBundle = new Bundle();
        queryBundle.putString(SEARCH_QUERY_URL_EXTRA, getString(R.string.url_recipes));

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> githubSearchLoader = loaderManager.getLoader(RECIPES_SEARCH_LOADER);
        if (githubSearchLoader == null) {
            loaderManager.initLoader(RECIPES_SEARCH_LOADER, queryBundle, this);
        } else {
            loaderManager.restartLoader(RECIPES_SEARCH_LOADER, queryBundle, this);
        }

    }

    @Override
    public void onRecipeItemClick(RecipeModel recipe) {
        showRecipeDetails(recipe);
    }

    private void showRecipeDetails(RecipeModel recipe){
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(BUNDLE_RECIPE_DETAILS, recipe);

        startActivity(intent);

    }
//=== Begin Loader Interface Method ===
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        String searchQueryUrlString = args.getString(SEARCH_QUERY_URL_EXTRA);
        mLoadingIndicator.setVisibility(View.VISIBLE);

        return new RecipeLoader(MainActivity.this, searchQueryUrlString);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);

        try {
            mRecipesList = JsonUtils.getRecipeListsFromJson(data.toString());
            mAdapter.setRecipesData(mRecipesList);
            BakingWidgetService.startActionUpdateRecipeWidgets(this);

        } catch (JSONException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
//=== End Loader Interface Method ===
}
