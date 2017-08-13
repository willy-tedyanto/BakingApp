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

import static android.view.View.GONE;

/**
 * Created by user on 2017-08-06.
 */

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.MasterListAdapterViewHolder> {
    String TAG = MasterListAdapter.class.getSimpleName();

    private Context mContext;
    private final MasterListAdapterHandler mHandler;

    private RecipeModel mRecipe;

    public interface MasterListAdapterHandler{
        void onMasterListItemSelected(RecipeModel recipe, int index);
    }

    public MasterListAdapter(Context context, MasterListAdapterHandler handler){
        mContext = context;
        mHandler = handler;
    }

    @Override
    public MasterListAdapter.MasterListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.master_list_item_layout, parent, false);
        return new MasterListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MasterListAdapter.MasterListAdapterViewHolder holder, int position) {
        if (position == 0){
            //Return ingredients
            holder.mTitleTextView.setText(mContext.getString(R.string.ingredients_term));
            holder.mSubTitleTextView.setText(mRecipe.getIngredientsCount() +
                    " " + mContext.getString(R.string.item_term));
            holder.mThumbnailImageView.setVisibility(GONE);

        } else {
            //Return Steps
            int stepId = mRecipe.getStep(position - 1).getId();
            holder.mTitleTextView.setText(mContext.getString(R.string.step_term) +
                " " + (stepId > 0 ? stepId : ""));
            holder.mSubTitleTextView.setText(mRecipe.getStep(position - 1).getShortDescription());

            if (mRecipe.getStep(position - 1).getThumbnailUrl().length() == 0){
                mRecipe.getStep(position - 1).setThumbnailUrl("-");
            }

            Picasso.with(mContext)
                    .load(mRecipe.getStep(position - 1).getThumbnailUrl())
                    .placeholder(R.drawable.ic_cached_black_24dp)
                    .error(R.drawable.ic_error_outline_black_24dp)
                    .into(holder.mThumbnailImageView);
        }
    }

    @Override
    public int getItemCount() {
        if (mRecipe == null) return 0;
        return mRecipe.getStepsCount() + 1;
        //Return total steps + 1 for ingredients
    }

    public class MasterListAdapterViewHolder
        extends RecyclerView.ViewHolder
        implements View.OnClickListener{

        public final TextView mTitleTextView;
        public final TextView mSubTitleTextView;
        public final ImageView mThumbnailImageView;

        public MasterListAdapterViewHolder(View view){
            super(view);
            mTitleTextView = (TextView) view.findViewById(R.id.master_list_item_title_textview);
            mSubTitleTextView = (TextView) view.findViewById(R.id.master_list_item_subtitle_textview);
            mThumbnailImageView = (ImageView) view.findViewById(R.id.recipe_step_thumbnail_imageview);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();

            mHandler.onMasterListItemSelected(mRecipe, adapterPosition);
        }
    }

    public void setRecipeData(RecipeModel recipe){
        if (recipe == null){
            mRecipe = null;
        }
        else {
            mRecipe = recipe;
        }
        notifyDataSetChanged();
    }

}
