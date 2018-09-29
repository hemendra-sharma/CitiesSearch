package com.hemendra.citiessearch.view;


import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.hemendra.citiessearch.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CitiesActivityTest {

    @Rule
    public ActivityTestRule<CitiesActivity> mActivityTestRule =
            new ActivityTestRule<>(CitiesActivity.class);

    @Test
    public void citiesActivityTest() {
        ViewInteraction relativeLayout = onView(
                allOf(withId(R.id.rlProgress),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        try {
            while(true) {
                relativeLayout.check(matches(isDisplayed()));
            }
        }catch (NoMatchingViewException ignore) {}

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {}

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.etSearch),
                        childAtPosition(
                                allOf(withId(R.id.rlSearch),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("a"), closeSoftKeyboard());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {}

        appCompatEditText.perform(replaceText("al"), closeSoftKeyboard());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {}

        appCompatEditText.perform(replaceText("alb"), closeSoftKeyboard());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {}

        appCompatEditText.perform(replaceText("albu"), closeSoftKeyboard());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {}

        appCompatEditText.perform(replaceText("albuq"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.etSearch), withText("albuq"),
                        childAtPosition(
                                allOf(withId(R.id.rlSearch),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignore) {}

        pressBack();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {}

        appCompatEditText.perform(replaceText(""));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {}

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.etSearch),
                        childAtPosition(
                                allOf(withId(R.id.rlSearch),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.etSearch),
                        childAtPosition(
                                allOf(withId(R.id.rlSearch),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(pressImeActionButton());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
