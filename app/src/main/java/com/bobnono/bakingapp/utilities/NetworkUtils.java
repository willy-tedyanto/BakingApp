package com.bobnono.bakingapp.utilities;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


/**
 * Created by user on 2017-06-25.
 */

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }
        catch (IOException e){
            Log.e(TAG, "Error IOException : " + e.getMessage());
            return null;
        }
        catch (Exception e){
            Log.e(TAG, "Error : " + e.getMessage());
            return null;
        }
        finally {
            urlConnection.disconnect();
        }
    }

}
