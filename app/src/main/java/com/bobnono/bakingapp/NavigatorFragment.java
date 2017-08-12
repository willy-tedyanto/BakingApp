package com.bobnono.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bobnono.bakingapp.model.RecipeModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 2017-08-06.
 */

public class NavigatorFragment extends Fragment
    implements View.OnClickListener {

    String TAG = NavigatorFragment.class.getSimpleName();

    private RecipeModel mRecipe;
    private int mPosition;

    OnNavigatorFragmentClickListener mCallback;

    public interface OnNavigatorFragmentClickListener{
        void onNavPreviousClick();
        void onNavNextClick();

    }

    @BindView(R.id.previous_text_view) TextView mPreviousTextView;
    @BindView(R.id.next_text_view) TextView mNextTextView;
    @BindView(R.id.position_text_view) TextView mPositionTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_navigator, container, false);

        ButterKnife.bind(this, rootView);

        showPosition();

        return rootView;
    }

    void showPosition(){
        mPositionTextView.setText((mPosition) + " of " + mRecipe.getStepsCount());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        try{
            mCallback = (NavigatorFragment.OnNavigatorFragmentClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement OnNavigatorFragmentClickListener");
        }
    }

    @Override
    @OnClick({R.id.next_text_view, R.id.previous_text_view})
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.next_text_view):
                mCallback.onNavNextClick();
                break;
            case (R.id.previous_text_view):
                mCallback.onNavPreviousClick();
                break;
            default:
                break;
        }
    }

    public void setRecipe(RecipeModel recipe){
        mRecipe = recipe;
    }

    public void setPosition(int position){
        mPosition = position;
    }

}
