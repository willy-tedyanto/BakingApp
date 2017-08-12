package com.bobnono.bakingapp.utilities;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by user on 2017-08-02.
 */

public class GeneralUtils {
    static String TAG = GeneralUtils.class.getSimpleName();

    public static int calculateNoOfColumns(Context context, int scalingFactor) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }

}
