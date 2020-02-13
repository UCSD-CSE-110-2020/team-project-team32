package com.example.cse110_project;


import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.cse110_project.fitness_api.FitnessService;
import com.example.cse110_project.fitness_api.FitnessServiceFactory;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CancelWalkUITest {
    private static final String TEST_SERVICE = "TEST_SERVICE";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        FitnessServiceFactory.put(TEST_SERVICE, CancelWalkUITest.TestFitnessService::new);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        intent.putExtra(MainActivity.FITNESS_SERVICE_KEY, TEST_SERVICE);
    }

    @Test
    public void cancelWalkUITest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.heightInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("61"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());

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
                allOf(withId(R.id.cancelButton), withText("Cancel"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction textView = onView(withId(R.id.recentStepsDisplay));
        textView.check(matches(withText("N/A")));

        ViewInteraction textView2 = onView(withId(R.id.recentMilesDisplay));
        textView2.check(matches(withText("N/A")));

        ViewInteraction textView3 = onView(withId(R.id.recentTimeDisplay));
        textView3.check(matches(withText("N/A")));

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
                allOf(withId(R.id.stopWalkButton), withText("Stop Walk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                10),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button2), withText("Cancel"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                2)));
        appCompatButton6.perform(scrollTo(), click());

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
                allOf(withId(android.R.id.button1), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton8.perform(scrollTo(), click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(android.R.id.button2), withText("Cancel"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                2)));
        appCompatButton9.perform(scrollTo(), click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.cancelButton), withText("Cancel"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()));
        appCompatButton10.perform(click());

        ViewInteraction textView4 = onView(withId(R.id.recentStepsDisplay));
        textView4.check(matches(withText("N/A")));

        ViewInteraction textView5 = onView(withId(R.id.recentMilesDisplay));
        textView5.check(matches(withText("N/A")));

        ViewInteraction textView6 = onView(withId(R.id.recentTimeDisplay));
        textView6.check(matches(withText("N/A")));
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
