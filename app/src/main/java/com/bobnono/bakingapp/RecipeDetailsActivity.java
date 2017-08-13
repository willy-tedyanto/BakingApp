package com.bobnono.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.bobnono.bakingapp.model.RecipeModel;

public class RecipeDetailsActivity extends AppCompatActivity
    implements MasterListFragment.OnMasterListFragmentClickListener,
    NavigatorFragment.OnNavigatorFragmentClickListener{

    String TAG = RecipeDetailsActivity.class.getSimpleName();

    public static final String BUNDLE_RECIPE = "com.bobnono.bakingapp.bundle_recipe";
    public static final String BUNDLE_POSITION = "com.bobnono.bakingapp.bundle_position";
    public static final String BUNDLE_FRAGMENT_SHOW_WHAT = "com.bobnono.bakingapp.bundle_fragment_show_what";

    public static final String EXTRA_RECIPE_POSITION = "com.bobnono.bakingapp.EXTRA_RECIPE_POSITION";

    private enum FragmentCurrentContent{MASTER, INGREDIENT, STEP};

    private RecipeModel mRecipe;
    private int mPosition;
    private FragmentCurrentContent mFragmentShowWhat = FragmentCurrentContent.MASTER;

    public static boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        if (savedInstanceState != null){
            mRecipe = savedInstanceState.getParcelable(BUNDLE_RECIPE);
            mPosition = savedInstanceState.getInt(BUNDLE_POSITION);
            mFragmentShowWhat = FragmentCurrentContent.values()[savedInstanceState.getInt(BUNDLE_FRAGMENT_SHOW_WHAT)];
        }

        Intent intent = getIntent();

        mRecipe = intent.getParcelableExtra(MainActivity.BUNDLE_RECIPE_DETAILS);

        //=== Check Id if clicked from widget
        int mRecipePosition = intent.getIntExtra(RecipeDetailsActivity.EXTRA_RECIPE_POSITION, -1);
        if (mRecipePosition > -1){
            mRecipe = MainActivity.mRecipesList.get(mRecipePosition);
            mPosition = 0; //0 for show ingredients
            mFragmentShowWhat = FragmentCurrentContent.INGREDIENT;
        }

        if(findViewById(R.id.detail_linear_layout) != null) {
            mTwoPane = true;

            if (mRecipe != null){
                if (mPosition == 0) {
                    replaceFragmentIngredient();
                } else {
                    replaceFragmentStep();
                }
            }
        }
        else {
            switch (mFragmentShowWhat) {
                case MASTER:
                    replaceFragmentMaster();
                    break;
                case INGREDIENT:
                    replaceFragmentIngredient();
                    break;
                case STEP:
                    replaceFragmentStep();
                    break;
            }
        }
    }

    @Override
    public void onBackPressed(){
        if (mFragmentShowWhat == FragmentCurrentContent.INGREDIENT || mFragmentShowWhat == FragmentCurrentContent.STEP){
            replaceFragmentMaster();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMasterItemListSelected(RecipeModel recipe, int position) {

        mRecipe = recipe;
        if (0 == position) { //0 is ingredients
            replaceFragmentIngredient();
        } else {  // > 0 for recipe steps
            mPosition = position;
            replaceFragmentStep();
        }
    }

    void replaceFragmentMaster(){
        mFragmentShowWhat = FragmentCurrentContent.MASTER;

        MasterListFragment masterListFragment = new MasterListFragment();
        masterListFragment.setRecipe(mRecipe);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_details_linear_layout, masterListFragment)
                .commit();
    }

    void replaceFragmentIngredient(){

        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.setRecipe(mRecipe);

        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();

        if (mTwoPane){
            fm.replace(R.id.remark_container, ingredientsFragment);
        } else {
            fm.replace(R.id.recipe_details_linear_layout, ingredientsFragment);
            mFragmentShowWhat = FragmentCurrentContent.INGREDIENT;
        }
        fm.commit();

    }

    void replaceFragmentStep(){

        if (mTwoPane){
            StepFragment stepFragment = new StepFragment();
            stepFragment.setRecipe(mRecipe);
            stepFragment.setPosition(mPosition);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.remark_container, stepFragment)
                    .commit();

            MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
            mediaPlayerFragment.setRecipe(mRecipe);
            mediaPlayerFragment.setPosition(mPosition);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.media_player_container, mediaPlayerFragment)
                    .commit();

        } else {
            RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();

            recipeStepsFragment.setRecipe(mRecipe);
            recipeStepsFragment.setPosition(mPosition);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_details_linear_layout, recipeStepsFragment)
                    .commit();

            mFragmentShowWhat = FragmentCurrentContent.STEP;
        }

    }

    @Override
    public void onNavPreviousClick() {
        if (mPosition > 1){
            mPosition--;
            replaceFragmentStep();
        }

    }

    @Override
    public void onNavNextClick() {
        if (mPosition < mRecipe.getStepsCount()){
            mPosition++;
            replaceFragmentStep();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        currentState.putParcelable(BUNDLE_RECIPE, mRecipe);
        currentState.putInt(BUNDLE_POSITION, mPosition);
        currentState.putInt(BUNDLE_FRAGMENT_SHOW_WHAT, mFragmentShowWhat.ordinal());
    }
}

