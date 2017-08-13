package com.bobnono.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobnono.bakingapp.model.RecipeModel;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2017-08-06.
 */

public class MediaPlayerFragment extends Fragment {
    String TAG = MediaPlayerFragment.class.getSimpleName();

    final String EXOPLAYER_RESUME_WINDOW_KEY = "com.bobnono.bakingapp.mediaplayerfragment.EXOPLAYER_RESUME_WINDOW_KEY";
    final String EXOPLAYER_RESUME_RESUME_POSITION = "com.bobnono.bakingapp.mediaplayerfragment.EXOPLAYER_RESUME_POSITION";

    private static RecipeModel mRecipe;
    private static int mPosition;

    private static int resumeWindow;
    private static long resumePosition;

    private SimpleExoPlayer mExoPlayer;
    @BindView(R.id.exoPlayer) SimpleExoPlayerView mPlayerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_media_player, container, false);

        ButterKnife.bind(this, rootView);


        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            resumeWindow = savedInstanceState.getInt(EXOPLAYER_RESUME_WINDOW_KEY);
            resumePosition = savedInstanceState.getLong(EXOPLAYER_RESUME_RESUME_POSITION);
        }

    }

    void showVideo(){
        if (mExoPlayer == null)
        {
            Uri mediaUri = Uri.parse(mRecipe.getStep(mPosition-1).getVideoUrl());

            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

            boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
            if (haveResumePosition) {
                mExoPlayer.seekTo(resumeWindow, resumePosition);
            }

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }

    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            updateResumePosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void updateResumePosition() {
        resumeWindow = mExoPlayer.getCurrentWindowIndex();
        resumePosition = Math.max(0, mExoPlayer.getCurrentPosition());
    }

    private void clearResumePosition() {
Log.e(TAG, "ClearresumePosition");
        resumeWindow = C.INDEX_UNSET;
        resumePosition = C.TIME_UNSET;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mRecipe != null) {
            showVideo();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mRecipe != null) {
            showVideo();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    public void setRecipe(RecipeModel recipe){
        if (mRecipe != recipe) {
            mRecipe = recipe;
            clearResumePosition();
        }
    }

    public void setPosition(int position){
        if (mPosition != position) {
            mPosition = position;
            clearResumePosition();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(EXOPLAYER_RESUME_WINDOW_KEY, resumeWindow);
        outState.putLong(EXOPLAYER_RESUME_RESUME_POSITION, resumePosition);
    }
}
