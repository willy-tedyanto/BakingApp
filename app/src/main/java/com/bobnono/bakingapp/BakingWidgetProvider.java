package com.bobnono.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {
    static String TAG = BakingWidgetProvider.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                String recipeName, String ingredientText, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);

        views.setTextViewText(R.id.widget_menu_name, recipeName);
        views.setTextViewText(R.id.widget_ingredients, ingredientText);

        Intent intent;
        PendingIntent pendingIntent;
//        if (MainActivity.mRecipesList != null && MainActivity.mRecipesList.size() > 0) {
//            intent = new Intent(context, RecipeDetailsActivity.class);
//            intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE_POSITION, BakingWidgetService.recipePosition);
//            pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        } else {
            intent = new Intent(context, MainActivity.class);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//        }

        views.setOnClickPendingIntent(R.id.widget_ingredients, pendingIntent);

        Intent prevIntent = new Intent(context, BakingWidgetService.class);
        prevIntent.setAction(BakingWidgetService.ACTION_CLICK_PREVIOUS);
        PendingIntent prevPendingIntent = PendingIntent.getService(context, 0, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_previous, prevPendingIntent);

        Intent nextIntent = new Intent(context, BakingWidgetService.class);
        nextIntent.setAction(BakingWidgetService.ACTION_CLICK_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getService(context, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_next, nextPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        BakingWidgetService.startActionUpdateRecipeWidgets(context);
    }

    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager,
                                          String recipeName, String ingredientText, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipeName, ingredientText, appWidgetId);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

