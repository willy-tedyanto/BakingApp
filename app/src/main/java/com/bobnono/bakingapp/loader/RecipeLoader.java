package com.bobnono.bakingapp.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.bobnono.bakingapp.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

/**
 * Created by user on 2017-08-02.
 */

public class RecipeLoader extends AsyncTaskLoader {
    private final String UrlToLoad;
    private final String TAG = RecipeLoader.class.getSimpleName();

    String mResult;

    public RecipeLoader(Context context, String UrlToLoad){
        super(context);
        this.UrlToLoad = UrlToLoad;
    };

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if (mResult == null){
            forceLoad();
        } else {
            deliverResult(mResult);
        }
    }

    @Override
    public Object loadInBackground() {
        String jsonResult = null;
        try{
            URL url = new URL(UrlToLoad);
            jsonResult = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonResult;
    }

    @Override
    public void deliverResult(Object data) {
        if (null != data) {
            mResult = data.toString();
        }

        super.deliverResult(data);
    }
}
