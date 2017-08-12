package com.bobnono.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobnono.bakingapp.model.RecipeModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 2017-08-02.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder> {
    private String TAG = RecipesAdapter.class.getSimpleName();

    private Context mContext;
    private final RecipesAdapterHandler mHandler;

    private ArrayList<RecipeModel> mRecipes;

    public interface RecipesAdapterHandler{
        void onRecipeItemClick(RecipeModel recipe);
    }

    public RecipesAdapter(Context context, RecipesAdapterHandler handler){
        mContext = context;
        mHandler = handler;
    }

    @Override
    public RecipesAdapter.RecipesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recipes_grid_item_layout, parent, false);

        return new RecipesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipesAdapter.RecipesAdapterViewHolder holder, int position) {
        if (mRecipes.get(position).getImageLocation().length() == 0){
            mRecipes.get(position).setImageLocation("-");
        }

        Picasso.with(mContext)
                .load(mRecipes.get(position).getImageLocation())
                .placeholder(R.drawable.cutlery)
                .error(R.drawable.foods)
                .into(holder.mRecipeItemImageView);

        holder.mRecipeNameTextView.setText(mRecipes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (mRecipes == null)return 0;
        return mRecipes.size();
    }

    public class RecipesAdapterViewHolder
        extends RecyclerView.ViewHolder
        implements View.OnClickListener{

        public final ImageView mRecipeItemImageView;
        public final TextView mRecipeNameTextView;

        public RecipesAdapterViewHolder(View view){
            super(view);
            mRecipeItemImageView = (ImageView) view.findViewById(R.id.recipe_item_image_view);
            mRecipeNameTextView = (TextView) view.findViewById(R.id.recipe_name_text_view);
            view.setOnClickListener(this);
        };

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            RecipeModel recipe = mRecipes.get(adapterPosition);

            mHandler.onRecipeItemClick(recipe);
        }
    }

    public void setRecipesData(ArrayList<RecipeModel> recipes){
        if (recipes == null){
            mRecipes = null;
        }
        else {
            if (mRecipes == null){
                mRecipes = recipes;
            } else {
                mRecipes.addAll(recipes);
            }
        }
        notifyDataSetChanged();
    }

}
