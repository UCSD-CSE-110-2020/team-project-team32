package com.example.cse110_project.unit_tests;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import com.example.cse110_project.activities.MainActivity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.rule.ActivityTestRule;

import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.util.DataConstants;
import com.example.cse110_project.user_routes.RouteData;
import com.example.cse110_project.user_routes.UserRoute;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;

@RunWith(RobolectricTestRunner.class)
public class RouteDataTest {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context c;

    @Rule
    public ActivityTestRule<MainActivity> mainActivity =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        pref = mainActivity.getActivity().getSharedPreferences(DataConstants.ROUTES_DATA_FILE,
                                                               Context.MODE_PRIVATE);
        editor = pref.edit();
        c = mainActivity.getActivity().getApplicationContext();
    }

    @Test
    public void testRetrieveNameExists() {
        editor.putString(String.format(DataConstants.ROUTE_NAME_KEY, 10), "Name");
        editor.apply();
        assertEquals(RouteData.retrieveRouteName(c, 10), "Name");
    }

    @Test
    public void testRetrieveNameDNE() {
        assertEquals(RouteData.retrieveRouteName(c, 10), DataConstants.STR_NOT_FOUND);
    }

    @Test
    public void testSaveNameNew() {
        assertEquals(RouteData.retrieveRouteName(c, 10), DataConstants.STR_NOT_FOUND);
        RouteData.saveRouteName(c, 10, "Name");
        assertEquals("Name",
                pref.getString(String.format(DataConstants.ROUTE_NAME_KEY, 10), ""));
    }

    @Test
    public void testSaveNameOverriding() {
        assertEquals(RouteData.retrieveRouteName(c, 10), DataConstants.STR_NOT_FOUND);
        RouteData.saveRouteName(c, 10, "Name");
        assertEquals("Name",
                pref.getString(String.format(DataConstants.ROUTE_NAME_KEY, 10), ""));
        RouteData.saveRouteName(c, 10, "NewName");
        assertEquals("NewName",
                pref.getString(String.format(DataConstants.ROUTE_NAME_KEY, 10), ""));
    }

    @Test
    public void testSaveRouteDataFull() {
        UserRoute r = new UserRoute(10, "Name", 200, LocalTime.of(5, 20),
                LocalDateTime.of(2020, 1, 20, 10, 30));
        r.setStartingPoint("Start");
        r.setFlatVsHilly(Route.FLAT);
        r.setEvenVsUneven(Route.UNEVEN_S);
        r.setLoopVsOutBack(Route.OUT_BACK);
        r.setDifficulty(Route.EASY_D);
        r.setNotes("Notes");
        r.setFavorite(true);
        RouteData.saveRouteData(c, r);

        assertEquals(RouteData.retrieveRouteName(c, 10), "Name");
        assertEquals(RouteData.retrieveRouteSteps(c, 10), 200);
        assertEquals(RouteData.retrieveRouteTime(c, 10),
                LocalTime.of(5, 20).toString());
        assertEquals(RouteData.retrieveRouteDate(c, 10),
                LocalDateTime.of(2020, 1, 20, 10, 30).toString());

        assertEquals("Start", RouteData.retrieveStartingPoint(c, 10));
        assertEquals(Route.FLAT, RouteData.retrieveFlatVsHilly(c, 10));
        assertEquals(Route.UNEVEN_S, RouteData.retrieveEvenVsUneven(c, 10));
        assertEquals(Route.OUT_BACK, RouteData.retrieveLoopVsOutBack(c, 10));
        assertEquals(Route.EASY_D, RouteData.retrieveDifficulty(c, 10));
        assertEquals("Notes", RouteData.retrieveNotes(c, 10));
        assertEquals(true, RouteData.retrieveFavorite(c, 10));

    }

    @Test
    public void testSaveRouteDataMinimum() {
        UserRoute r = new UserRoute(10, "Name");
        RouteData.saveRouteData(c, r);

        assertEquals(RouteData.retrieveRouteName(c, 10), "Name");
        assertEquals(RouteData.retrieveRouteSteps(c, 10), DataConstants.INT_NOT_FOUND);
        assertEquals(RouteData.retrieveRouteTime(c, 10), DataConstants.STR_NOT_FOUND);
        assertEquals(RouteData.retrieveRouteDate(c, 10), DataConstants.STR_NOT_FOUND);
    }

    @Test
    public void testStartingPoint() {
        assertEquals(RouteData.retrieveStartingPoint(c, 10), DataConstants.STR_NOT_FOUND);
        RouteData.saveStartingPoint(c,10, "Starting Point");
        assertEquals(RouteData.retrieveStartingPoint(c, 10), "Starting Point");
    }

    @Test
    public void testFlatVsHilly() {
        assertEquals(RouteData.retrieveFlatVsHilly(c, 10), DataConstants.STR_NOT_FOUND);
        RouteData.saveFlatVsHilly(c,10,"Hilly");
        assertEquals(RouteData.retrieveFlatVsHilly(c, 10), "Hilly");
    }

    @Test
    public void testLoopVsOutBack() {
        assertEquals(RouteData.retrieveLoopVsOutBack(c, 10), DataConstants.STR_NOT_FOUND);
        RouteData.saveLoopVsOutBack(c, 10, "Loop");
        assertEquals(RouteData.retrieveLoopVsOutBack(c, 10), "Loop");
    }

    @Test
    public void testStreetVsTrail() {
        assertEquals(RouteData.retrieveStreetVsTrail(c, 10), DataConstants.STR_NOT_FOUND);
        RouteData.saveStreetsVsTrail(c, 10, "Trail");
        assertEquals(RouteData.retrieveStreetVsTrail(c, 10), "Trail");
    }

    @Test
    public void testEvenVsUneven() {
        assertEquals(RouteData.retrieveEvenVsUneven(c, 10), DataConstants.STR_NOT_FOUND);
        RouteData.saveEvenVsUneven(c, 10, "Even Surface");
        assertEquals(RouteData.retrieveEvenVsUneven(c, 10), "Even Surface");
    }

    @Test
    public void testDifficulty() {
        assertEquals(RouteData.retrieveDifficulty(c, 10), DataConstants.STR_NOT_FOUND);
        RouteData.saveDifficulty(c, 10, "Easy");
        assertEquals(RouteData.retrieveDifficulty(c, 10), "Easy");
    }

    @Test
    public void testNotes() {
        assertEquals(RouteData.retrieveNotes(c, 10), DataConstants.STR_NOT_FOUND);
        RouteData.saveNotes(c, 10, "It was a nice hike!");
        assertEquals(RouteData.retrieveNotes(c, 10), "It was a nice hike!");
    }

    @Test
    public void testFavorite() {
        assertEquals(RouteData.retrieveFavorite(c, 20), DataConstants.BOOL_NOT_FOUND);
        RouteData.saveFavorite(c, 20, true);
        assertTrue(RouteData.retrieveFavorite(c, 20));
    }

}
