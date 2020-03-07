package com.example.cse110_project.ui_tests;


import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.DataInteraction;
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
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DRoutesWalkDetailsUITest {
    private static final String TEST_SERVICE = "TEST_SERVICE";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        WWRApplication.getUser().setStepsOffset(0);
        WWRApplication.getUser().setHeight(61);
        FitnessServiceFactory.put(TEST_SERVICE, DRoutesWalkDetailsUITest.TestFitnessService::new);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        intent.putExtra(MainActivity.FITNESS_SERVICE_KEY, TEST_SERVICE);
    }

    @Test
    public void routesWalkDetailsUITest() {ViewInteraction appCompatButton2 = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.mockingButton), withText("DEV"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                14),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton1 = onView(
                allOf(withId(R.id.mockingStepsButton), withText("+500"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton1.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.mockingStepsButton), withText("+500"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.mockingStepsButton), withText("+500"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.mockingStepsButton), withText("+500"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.mockingTimeSubmitButton), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.mockingBackButton), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction textView = onView(withId(R.id.dailySteps));
        textView.check(matches(withText("2000")));

        ViewInteraction textView2 = onView(withId(R.id.dailyMiles));
        textView2.check(matches(withText("0.8")));

        ViewInteraction textView3 = onView(withId(R.id.recentSteps));
        textView3.check(matches(withText("N/A")));

        ViewInteraction textView4 = onView(withId(R.id.recentMiles));
        textView4.check(matches(withText("N/A")));

        ViewInteraction textView5 = onView(withId(R.id.recentTime));
        textView5.check(matches(withText("N/A")));

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.startWalkButton), withText("Start Walk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction textView6 = onView(withId(R.id.walkRouteName));
        textView6.check(matches(withText("New Route")));

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.walkMockingButton), withText("DEV"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton10.perform(click());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.mockingStepsButton), withText("+500"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton11.perform(click());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.mockingStepsButton), withText("+500"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton12.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.mockingTimeInput), withText("00:00"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.mockingTimeInput), withText("00:00"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("12:21"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.mockingTimeInput), withText("12:21"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.mockingTimeSubmitButton), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton13.perform(click());

        ViewInteraction appCompatButton14 = onView(
                allOf(withId(R.id.mockingBackButton), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton14.perform(click());

        ViewInteraction appCompatButton15 = onView(
                allOf(withId(R.id.stopWalkButton), withText("Stop Walk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                10),
                        isDisplayed()));
        appCompatButton15.perform(click());

        ViewInteraction appCompatButton16 = onView(
                allOf(withId(android.R.id.button1), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton16.perform(scrollTo(), click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.routeNameInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("Alpha"), closeSoftKeyboard());

        ViewInteraction appCompatButton17 = onView(
                allOf(withId(android.R.id.button1), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton17.perform(scrollTo(), click());

        ViewInteraction textView7 = onView(withId(R.id.routeRowName));
        textView7.check(matches(withText("Alpha")));

        ViewInteraction textView8 = onView(withId(R.id.routeRowSteps));
        textView8.check(matches(withText("1000")));

        ViewInteraction textView9 = onView(withId(R.id.routeRowMiles));
        textView9.check(matches(withText("0.4")));

        ViewInteraction textView10 = onView(withId(R.id.routeRowTime));
        textView10.check(matches(withText("12:21")));

        ViewInteraction textView11 = onView(withId(R.id.routeRowStartingPoint));
        textView11.check(matches(withText("")));

        ViewInteraction textView12 = onView(withId(R.id.routeRowFlatHilly));
        textView12.check(matches(withText("")));

        ViewInteraction textView13 = onView(withId(R.id.routeRowLoopOutBack));
        textView13.check(matches(withText("")));

        ViewInteraction textView14 = onView(withId(R.id.routeRowStreetsTrail));
        textView14.check(matches(withText("")));

        ViewInteraction textView15 = onView(withId(R.id.routeRowEvenUneven));
        textView15.check(matches(withText("")));

        ViewInteraction textView16 = onView(withId(R.id.routeRowDifficulty));
        textView16.check(matches(withText("")));

        ViewInteraction appCompatButton18 = onView(
                allOf(withId(R.id.routesHomeButton), withText("Home"),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton18.perform(click());

        ViewInteraction textView17 = onView(withId(R.id.dailySteps));
        textView17.check(matches(withText("3000")));

        ViewInteraction textView18 = onView(withId(R.id.dailyMiles));
        textView18.check(matches(withText("1.2")));

        ViewInteraction textView19 = onView(withId(R.id.recentSteps));
        textView19.check(matches(withText("1000")));

        ViewInteraction textView20 = onView(withId(R.id.recentMiles));
        textView20.check(matches(withText("0.4")));

        ViewInteraction textView21 = onView(withId(R.id.recentTime));
        textView21.check(matches(withText("12:21")));

        ViewInteraction appCompatButton19 = onView(
                allOf(withId(R.id.routesButton), withText("Routes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                15),
                        isDisplayed()));
        appCompatButton19.perform(click());

        ViewInteraction appCompatButton20 = onView(
                allOf(withId(R.id.routesNewRouteButton), withText("+"),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatButton20.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.routeNameInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("Gamma"), closeSoftKeyboard());

        ViewInteraction appCompatButton21 = onView(
                allOf(withId(R.id.favButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton21.perform(click());

        ViewInteraction appCompatButton22 = onView(
                allOf(withId(R.id.streetsButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        7),
                                0),
                        isDisplayed()));
        appCompatButton22.perform(click());

        ViewInteraction appCompatButton23 = onView(
                allOf(withId(R.id.hillyButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        5),
                                1),
                        isDisplayed()));
        appCompatButton23.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.startingPointInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("Star"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.routeNotesInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                11),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("Notes"), closeSoftKeyboard());

        ViewInteraction appCompatButton24 = onView(
                allOf(withId(android.R.id.button1), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton24.perform(scrollTo(), click());

        ViewInteraction textView22 = onView(
                allOf(withId(R.id.routeRowName), withText("Gamma"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0)));
        textView22.check(matches(withText("Gamma")));

        ViewInteraction textView23 = onView(
                allOf(withId(R.id.routeRowFavorite), withText("FAV"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                1)));
        textView23.check(matches(withText("FAV")));

        ViewInteraction textView24 = onView(
                allOf(withId(R.id.routeRowSteps), withText("N/A"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                0)));
        textView24.check(matches(withText("N/A")));

        ViewInteraction textView25 = onView(
                allOf(withId(R.id.routeRowMiles), withText("N/A"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                2)));
        textView25.check(matches(withText("N/A")));

        ViewInteraction textView26 = onView(
                allOf(withId(R.id.routeRowTime), withText("N/A"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                4)));
        textView26.check(matches(withText("N/A")));

        ViewInteraction textView27 = onView(
                allOf(withId(R.id.routeRowDate), withText("N/A"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                6)));
        textView27.check(matches(withText("N/A")));

        ViewInteraction textView28 = onView(
                allOf(withId(R.id.routeRowStartingPoint), withText("Star"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        2),
                                0)));
        textView28.check(matches(withText("Star")));

        ViewInteraction textView29 = onView(
                allOf(withId(R.id.routeRowFlatHilly), withText("Hilly"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        3),
                                0)));
        textView29.check(matches(withText("Hilly")));

        ViewInteraction textView30 = onView(
                allOf(withId(R.id.routeRowStreetsTrail), withText("Streets"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        3),
                                2)));
        textView30.check(matches(withText("Streets")));

        ViewInteraction textView31 = onView(
                allOf(withId(R.id.routeRowName), withText("Alpha"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0)));
        textView31.check(matches(withText("Alpha")));

        ViewInteraction appCompatButton25 = onView(
                allOf(withId(R.id.routesHomeButton), withText("Home"),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton25.perform(click());

        ViewInteraction textView32 = onView(withId(R.id.dailySteps));
        textView32.check(matches(withText("3000")));

        ViewInteraction textView33 = onView(withId(R.id.dailyMiles));
        textView33.check(matches(withText("1.2")));

        ViewInteraction textView34 = onView(withId(R.id.recentSteps));
        textView34.check(matches(withText("1000")));

        ViewInteraction textView35 = onView(withId(R.id.recentMiles));
        textView35.check(matches(withText("0.4")));

        ViewInteraction textView36 = onView(withId(R.id.recentTime));
        textView36.check(matches(withText("12:21")));

        ViewInteraction appCompatButton26 = onView(
                allOf(withId(R.id.routesButton), withText("Routes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                15),
                        isDisplayed()));
        appCompatButton26.perform(click());

        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.routesListView),
                        childAtPosition(
                                withId(R.id.coordinatorLayout),
                                3)))
                .atPosition(1);
        linearLayout.perform(click());

        ViewInteraction textView37 = onView(withId(R.id.detailsRouteName));
        textView37.check(matches(withText("Gamma")));

        ViewInteraction textView38 = onView(withId(R.id.detailsRouteSteps));
        textView38.check(matches(withText("N/A")));

        ViewInteraction textView39 = onView(withId(R.id.schedRouteMiles));
        textView39.check(matches(withText("N/A")));

        ViewInteraction textView40 = onView(withId(R.id.detailsRouteTime));
        textView40.check(matches(withText("N/A")));

        ViewInteraction textView41 = onView(withId(R.id.detailsStartDate));
        textView41.check(matches(withText("N/A")));

        ViewInteraction textView42 = onView(withId(R.id.detailsStartingPoint));
        textView42.check(matches(withText("Star")));

        ViewInteraction textView43 = onView(withId(R.id.detailsFav));
        textView43.check(matches(withText("FAV")));

        ViewInteraction textView44 = onView(withId(R.id.detailsFlatHilly));
        textView44.check(matches(withText("Hilly")));

        ViewInteraction textView45 = onView(withId(R.id.detailsStreetsTrail));
        textView45.check(matches(withText("Streets")));

        ViewInteraction textView46 = onView(withId(R.id.detailsNotes));
        textView46.check(matches(withText("Notes")));

        ViewInteraction appCompatButton27 = onView(
                allOf(withId(R.id.detailsStartWalkButton), withText("Start Walk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                22),
                        isDisplayed()));
        appCompatButton27.perform(click());

        ViewInteraction textView47 = onView(withId(R.id.walkRouteName));
        textView47.check(matches(withText("Gamma")));

        ViewInteraction textView48 = onView(withId(R.id.walkStartingPoint));
        textView48.check(matches(withText("Star")));

        ViewInteraction textView49 = onView(withId(R.id.walkNotes));
        textView49.check(matches(withText("Notes")));

        ViewInteraction appCompatButton30 = onView(
                allOf(withId(R.id.walkMockingButton), withText("DEV"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton30.perform(click());

        ViewInteraction appCompatButton31 = onView(
                allOf(withId(R.id.mockingStepsButton), withText("+500"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton31.perform(click());

        ViewInteraction appCompatButton32 = onView(
                allOf(withId(R.id.mockingTimeSubmitButton), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton32.perform(click());

        ViewInteraction appCompatButton33 = onView(
                allOf(withId(R.id.mockingBackButton), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton33.perform(click());

        ViewInteraction appCompatButton34 = onView(
                allOf(withId(R.id.stopWalkButton), withText("Stop Walk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                10),
                        isDisplayed()));
        appCompatButton34.perform(click());

        ViewInteraction appCompatButton35 = onView(
                allOf(withId(android.R.id.button1), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton35.perform(scrollTo(), click());

        DataInteraction linearLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.routesListView),
                        childAtPosition(
                                withId(R.id.coordinatorLayout),
                                3)))
                .atPosition(1);
        linearLayout2.perform(click());

        ViewInteraction textView50 = onView(withId(R.id.detailsRouteSteps));
        textView50.check(matches(withText("500")));

        ViewInteraction textView51 = onView(withId(R.id.schedRouteMiles));
        textView51.check(matches(withText("0.2")));

        ViewInteraction textView52 = onView(withId(R.id.detailsRouteTime));
        textView52.check(matches(withText("11:39")));

        ViewInteraction textView53 = onView(withId(R.id.detailsRouteName));
        textView53.check(matches(withText("Gamma")));

        ViewInteraction textView54 = onView(withId(R.id.detailsStartingPoint));
        textView54.check(matches(withText("Star")));

        ViewInteraction textView55 = onView(withId(R.id.detailsFav));
        textView55.check(matches(withText("FAV")));

        ViewInteraction textView56 = onView(withId(R.id.detailsFlatHilly));
        textView56.check(matches(withText("Hilly")));

        ViewInteraction textView57 = onView(withId(R.id.detailsStreetsTrail));
        textView57.check(matches(withText("Streets")));

        ViewInteraction textView58 = onView(withId(R.id.detailsNotes));
        textView58.check(matches(withText("Notes")));

        ViewInteraction appCompatButton37 = onView(
                allOf(withId(R.id.detailsBackButton), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                20),
                        isDisplayed()));
        appCompatButton37.perform(click());

        ViewInteraction textView59 = onView(
                allOf(withId(R.id.routeRowSteps), withText("500"),
                    childAtPosition(
                            childAtPosition(
                                    IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                    1),
                            0)));
        textView59.check(matches(withText("500")));

        ViewInteraction textView60 = onView(
                allOf(withId(R.id.routeRowMiles), withText("0.2"),
                    childAtPosition(
                            childAtPosition(
                                    IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                    1),
                            2)));
        textView60.check(matches(withText("0.2")));

        ViewInteraction textView61 = onView(
                allOf(withId(R.id.routeRowTime), withText("11:39"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                4)));
        textView61.check(matches(withText("11:39")));

        ViewInteraction appCompatButton38 = onView(
                allOf(withId(R.id.routesHomeButton), withText("Home"),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton38.perform(click());

        ViewInteraction textView62 = onView(withId(R.id.dailySteps));
        textView62.check(matches(withText("3500")));

        ViewInteraction textView63 = onView(withId(R.id.dailyMiles));
        textView63.check(matches(withText("1.4")));

        ViewInteraction textView64 = onView(withId(R.id.recentSteps));
        textView64.check(matches(withText("500")));

        ViewInteraction textView65 = onView(withId(R.id.recentMiles));
        textView65.check(matches(withText("0.2")));

        ViewInteraction textView66 = onView(withId(R.id.recentTime));
        textView66.check(matches(withText("11:39")));

        ViewInteraction appCompatButton39 = onView(
                allOf(withId(R.id.routesButton), withText("Routes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                15),
                        isDisplayed()));
        appCompatButton39.perform(click());

        ViewInteraction appCompatButton40 = onView(
                allOf(withId(R.id.routesHomeButton), withText("Home"),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton40.perform(click());

        ViewInteraction appCompatButton41 = onView(
                allOf(withId(R.id.startWalkButton), withText("Start Walk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton41.perform(click());

        ViewInteraction appCompatButton42 = onView(
                allOf(withId(R.id.stopWalkButton), withText("Stop Walk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                10),
                        isDisplayed()));
        appCompatButton42.perform(click());

        ViewInteraction appCompatButton43 = onView(
                allOf(withId(android.R.id.button1), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton43.perform(scrollTo(), click());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.routeNameInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("Beta"), closeSoftKeyboard());

        ViewInteraction appCompatButton44 = onView(
                allOf(withId(R.id.outBackButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        6),
                                1),
                        isDisplayed()));
        appCompatButton44.perform(click());

        ViewInteraction appCompatButton45 = onView(
                allOf(withId(R.id.streetsButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        7),
                                0),
                        isDisplayed()));
        appCompatButton45.perform(click());

        ViewInteraction appCompatButton46 = onView(
                allOf(withId(R.id.unevenSurfaceButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        8),
                                1),
                        isDisplayed()));
        appCompatButton46.perform(click());

        ViewInteraction appCompatButton47 = onView(
                allOf(withId(R.id.midDifficultyButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        9),
                                1),
                        isDisplayed()));
        appCompatButton47.perform(click());

        ViewInteraction appCompatButton48 = onView(
                allOf(withId(android.R.id.button1), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton48.perform(scrollTo(), click());

        ViewInteraction textView67 = onView(
                allOf(withId(R.id.routeRowSteps), withText("0"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                0)));
        textView67.check(matches(withText("0")));

        ViewInteraction textView68 = onView(
                allOf(withId(R.id.routeRowMiles), withText("0.0"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                2)));
        textView68.check(matches(withText("0.0")));

        ViewInteraction textView69 = onView(
                allOf(withId(R.id.routeRowTime), withText("00:00"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                4)));
        textView69.check(matches(withText("00:00")));

        ViewInteraction textView70 = onView(
                allOf(withId(R.id.routeRowName), withText("Beta"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0)));
        textView70.check(matches(withText("Beta")));

        ViewInteraction textView71 = onView(
                allOf(withId(R.id.routeRowLoopOutBack), withText("Out-and-back"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        3),
                                1)));
        textView71.check(matches(withText("Out-and-back")));

        ViewInteraction textView73 = onView(
                allOf(withId(R.id.routeRowEvenUneven), withText("Uneven surface"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        3),
                                3)));
        textView73.check(matches(withText("Uneven surface")));

        ViewInteraction textView74 = onView(
                allOf(withId(R.id.routeRowDifficulty), withText("Moderate"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        3),
                                4)));
        textView74.check(matches(withText("Moderate")));

        ViewInteraction textView75 = onView(
                allOf(withId(R.id.routeRowName), withText("Alpha"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0)));
        textView75.check(matches(withText("Alpha")));

        ViewInteraction textView76 = onView(
                allOf(withId(R.id.routeRowName), withText("Gamma"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0)));
        textView76.check(matches(withText("Gamma")));

        ViewInteraction appCompatButton49 = onView(
                allOf(withId(R.id.routesHomeButton), withText("Home"),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton49.perform(click());

        ViewInteraction textView77 = onView(withId(R.id.dailySteps));
        textView77.check(matches(withText("3500")));

        ViewInteraction textView78 = onView(withId(R.id.dailyMiles));
        textView78.check(matches(withText("1.4")));

        ViewInteraction textView79 = onView(withId(R.id.recentSteps));
        textView79.check(matches(withText("0")));

        ViewInteraction textView80 = onView(withId(R.id.recentMiles));
        textView80.check(matches(withText("0.0")));

        ViewInteraction textView81 = onView(withId(R.id.recentTime));
        textView81.check(matches(withText("00:00")));

        ViewInteraction appCompatButton50 = onView(
                allOf(withId(R.id.routesButton), withText("Routes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                15),
                        isDisplayed()));
        appCompatButton50.perform(click());

        DataInteraction linearLayout3 = onData(anything())
                .inAdapterView(allOf(withId(R.id.routesListView),
                        childAtPosition(
                                withId(R.id.coordinatorLayout),
                                3)))
                .atPosition(1);
        linearLayout3.perform(click());

        ViewInteraction textView82 = onView(withId(R.id.detailsRouteName));
        textView82.check(matches(withText("Beta")));

        ViewInteraction textView83 = onView(withId(R.id.detailsRouteSteps));
        textView83.check(matches(withText("0")));

        ViewInteraction textView84 = onView(withId(R.id.schedRouteMiles));
        textView84.check(matches(withText("0.0")));

        ViewInteraction textView85 = onView(withId(R.id.detailsRouteTime));
        textView85.check(matches(withText("00:00")));

        ViewInteraction textView86 = onView(withId(R.id.detailsLoopOutBack));
        textView86.check(matches(withText("Out-and-back")));

        ViewInteraction textView87 = onView(withId(R.id.detailsEvenUneven));
        textView87.check(matches(withText("Uneven surface")));

        ViewInteraction textView88 = onView(withId(R.id.detailsStreetsTrail));
        textView88.check(matches(withText("Streets")));

        ViewInteraction textView89 = onView(withId(R.id.detailsDifficulty));
        textView89.check(matches(withText("Moderate")));

        ViewInteraction appCompatButton51 = onView(
                allOf(withId(R.id.detailsStartWalkButton), withText("Start Walk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                22),
                        isDisplayed()));
        appCompatButton51.perform(click());

        ViewInteraction appCompatButton52 = onView(
                allOf(withId(R.id.walkMockingButton), withText("DEV"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton52.perform(click());

        ViewInteraction appCompatButton53 = onView(
                allOf(withId(R.id.mockingStepsButton), withText("+500"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton53.perform(click());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.mockingTimeInput), withText("00:00"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText10.perform(click());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.mockingTimeInput), withText("00:00"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText11.perform(replaceText("03:00"));

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.mockingTimeInput), withText("03:00"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText12.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton54 = onView(
                allOf(withId(R.id.mockingTimeSubmitButton), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton54.perform(click());

        ViewInteraction appCompatButton55 = onView(
                allOf(withId(R.id.mockingBackButton), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton55.perform(click());

        ViewInteraction appCompatButton56 = onView(
                allOf(withId(R.id.stopWalkButton), withText("Stop Walk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                10),
                        isDisplayed()));
        appCompatButton56.perform(click());

        ViewInteraction appCompatButton57 = onView(
                allOf(withId(android.R.id.button1), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton57.perform(scrollTo(), click());

        ViewInteraction textView92 = onView(
                allOf(withId(R.id.routeRowTime), withText("03:00"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                4)));
        textView92.check(matches(withText("03:00")));

        ViewInteraction textView93 = onView(
                allOf(withId(R.id.routeRowLoopOutBack), withText("Out-and-back"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        3),
                                1)));
        textView93.check(matches(withText("Out-and-back")));

        DataInteraction linearLayout4 = onData(anything())
                .inAdapterView(allOf(withId(R.id.routesListView),
                        childAtPosition(
                                withId(R.id.coordinatorLayout),
                                3)))
                .atPosition(1);
        linearLayout4.perform(click());

        ViewInteraction textView94 = onView(withId(R.id.detailsRouteSteps));
        textView94.check(matches(withText("500")));

        ViewInteraction textView95 = onView(withId(R.id.schedRouteMiles));
        textView95.check(matches(withText("0.2")));

        ViewInteraction textView96 = onView(withId(R.id.detailsRouteTime));
        textView96.check(matches(withText("03:00")));

        ViewInteraction textView97 = onView(withId(R.id.detailsLoopOutBack));
        textView97.check(matches(withText("Out-and-back")));

        ViewInteraction textView98 = onView(withId(R.id.detailsRouteName));
        textView98.check(matches(withText("Beta")));

        ViewInteraction textView99 = onView(withId(R.id.detailsEvenUneven));
        textView99.check(matches(withText("Uneven surface")));

        ViewInteraction textView100 = onView(withId(R.id.detailsStreetsTrail));
        textView100.check(matches(withText("Streets")));

        ViewInteraction textView101 = onView(withId(R.id.detailsDifficulty));
        textView101.check(matches(withText("Moderate")));

        ViewInteraction appCompatButton59 = onView(
                allOf(withId(R.id.detailsBackButton), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                20),
                        isDisplayed()));
        appCompatButton59.perform(click());

        ViewInteraction appCompatButton60 = onView(
                allOf(withId(R.id.routesHomeButton), withText("Home"),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton60.perform(click());

        ViewInteraction textView102 = onView(withId(R.id.dailySteps));
        textView102.check(matches(withText("4000")));

        ViewInteraction textView103 = onView(withId(R.id.dailyMiles));
        textView103.check(matches(withText("1.6")));

        ViewInteraction textView104 = onView(withId(R.id.recentSteps));
        textView104.check(matches(withText("500")));

        ViewInteraction textView105 = onView(withId(R.id.recentMiles));
        textView105.check(matches(withText("0.2")));

        ViewInteraction textView106 = onView(withId(R.id.recentTime));
        textView106.check(matches(withText("03:00")));
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
