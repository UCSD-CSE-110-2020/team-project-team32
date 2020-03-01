package com.example.cse110_project.test.ui_tests;


import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.cse110_project.MainActivity;
import com.example.cse110_project.R;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.fitness.FitnessService;
import com.example.cse110_project.fitness.FitnessServiceFactory;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BBackProgressionsUITest {
    private static final String TEST_SERVICE = "TEST_SERVICE";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        WWRApplication.getUser().setHeight(61);
        FitnessServiceFactory.put(TEST_SERVICE, TestFitnessService::new);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        intent.putExtra(MainActivity.FITNESS_SERVICE_KEY, TEST_SERVICE);
    }

    @Test
    public void bBackProgressionsUITest() {
        ViewInteraction textView = onView(ViewMatchers.withId(R.id.recentSteps));
        textView.check(matches(withText("N/A")));

        ViewInteraction textView2 = onView(withId(R.id.recentMiles));
        textView2.check(matches(withText("N/A")));

        ViewInteraction textView3 = onView(withId(R.id.recentTime));
        textView3.check(matches(withText("N/A")));

        ViewInteraction textView4 = onView(withId(R.id.dailySteps));
        textView4.check(matches(withText("0")));

        ViewInteraction textView5 = onView(withId(R.id.dailyMiles));
        textView5.check(matches(withText("0.0")));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.startWalkButton), withText("Start Walk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.walkCancelButton), withText("CANCEL"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.startWalkButton), withText("Start Walk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.walkMockingButton), withText("DEV"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.mockingBackButton), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.stopWalkButton), withText("Stop Walk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                10),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(android.R.id.button2), withText("CANCEL"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                2)));
        appCompatButton8.perform(scrollTo(), click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.stopWalkButton), withText("Stop Walk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                10),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(android.R.id.button1), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton10.perform(scrollTo(), click());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(android.R.id.button2), withText("CANCEL"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                2)));
        appCompatButton11.perform(scrollTo(), click());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.walkCancelButton), withText("CANCEL"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()));
        appCompatButton12.perform(click());


        ViewInteraction textView6 = onView(withId(R.id.recentSteps));
        textView6.check(matches(withText("N/A")));

        ViewInteraction textView7= onView(withId(R.id.recentMiles));
        textView7.check(matches(withText("N/A")));

        ViewInteraction textView8 = onView(withId(R.id.recentTime));
        textView8.check(matches(withText("N/A")));

        ViewInteraction textView9 = onView(withId(R.id.dailySteps));
        textView9.check(matches(withText("0")));

        ViewInteraction textView10 = onView(withId(R.id.dailyMiles));
        textView10.check(matches(withText("0.0")));

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.routesButton), withText("Routes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                15),
                        isDisplayed()));
        appCompatButton13.perform(click());

        ViewInteraction appCompatButton15 = onView(
                allOf(withId(R.id.routesNewRouteButton), withText("+"),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatButton15.perform(click());

        ViewInteraction appCompatButton16 = onView(
                allOf(withId(android.R.id.button2), withText("CANCEL"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                2)));
        appCompatButton16.perform(scrollTo(), click());

        ViewInteraction appCompatButton17 = onView(
                allOf(withId(R.id.routesHomeButton), withText("Home"),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton17.perform(click());


        ViewInteraction textView11 = onView(withId(R.id.recentSteps));
        textView11.check(matches(withText("N/A")));

        ViewInteraction textView12 = onView(withId(R.id.recentMiles));
        textView12.check(matches(withText("N/A")));

        ViewInteraction textView13 = onView(withId(R.id.recentTime));
        textView13.check(matches(withText("N/A")));

        ViewInteraction textView14 = onView(withId(R.id.dailySteps));
        textView14.check(matches(withText("0")));

        ViewInteraction textView15 = onView(withId(R.id.dailyMiles));
        textView15.check(matches(withText("0.0")));
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


    private class TestFitnessService implements FitnessService {
        private static final String TAG = "[TestFitnessService]: ";
        private AppCompatActivity mainActivity;

        public TestFitnessService(AppCompatActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public int getRequestCode() {
            return 0;
        }

        @Override
        public void setup() {
            System.out.println(TAG + "setup");
        }

        @Override
        public void updateStepCount() {
            System.out.println(TAG + "updateStepCount");
            ((MainActivity)mainActivity).updateDailySteps(0);
        }
    }
}
