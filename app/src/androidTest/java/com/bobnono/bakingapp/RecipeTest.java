package com.bobnono.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by user on 2017-08-08.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeTest {

    public static final String RECIPE_NAME = "Brownies";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void clickRecyclerViewItem_OpensDetailActivity(){
//        onData(anything()).inAdapterView(withId(R.id.rv_recipes)).atPosition(1).perform(click());
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.recipe_name_tv)).check(matches(withText(RECIPE_NAME)));

    }

//    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
//        return new RecyclerViewMatcher(recyclerViewId);
//    }


}
