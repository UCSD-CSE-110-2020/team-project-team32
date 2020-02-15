package com.example.cse110_project.unit_tests;

import static org.junit.Assert.assertEquals;

import com.example.cse110_project.MainActivity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.rule.ActivityTestRule;

import com.example.cse110_project.data_access.DataConstants;
import com.example.cse110_project.data_access.RouteData;
import com.example.cse110_project.user_routes.Route;

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
        assertEquals(pref.getString(String.format(DataConstants.ROUTE_NAME_KEY, 10), ""), "Name");
    }

    @Test
    public void testSaveNameOverriding() {
        assertEquals(RouteData.retrieveRouteName(c, 10), DataConstants.STR_NOT_FOUND);
        RouteData.saveRouteName(c, 10, "Name");
        assertEquals(pref.getString(String.format(DataConstants.ROUTE_NAME_KEY, 10), ""), "Name");
        RouteData.saveRouteName(c, 10, "NewName");
        assertEquals(pref.getString(String.format(DataConstants.ROUTE_NAME_KEY, 10), ""), "NewName");
    }

    @Test
    public void testSaveRouteDataComplete() {
        Route r = new Route(10, "Name", 200, LocalTime.of(5, 20),
                LocalDateTime.of(2020, 1, 20, 10, 30));
        RouteData.saveRouteData(c, r);

        assertEquals(RouteData.retrieveRouteName(c, 10), "Name");
        assertEquals(RouteData.retrieveRouteSteps(c, 10), 200);
        assertEquals(RouteData.retrieveRouteTime(c, 10), LocalTime.of(5, 20).toString());
        assertEquals(RouteData.retrieveRouteDate(c, 10),
                LocalDateTime.of(2020, 1, 20, 10, 30).toString());
    }

    @Test
    public void testSaveRouteDataPartial() {
        Route r = new Route(10, "Name");
        RouteData.saveRouteData(c, r);

        assertEquals(RouteData.retrieveRouteName(c, 10), "Name");
        assertEquals(RouteData.retrieveRouteSteps(c, 10), DataConstants.INT_NOT_FOUND);
        assertEquals(RouteData.retrieveRouteTime(c, 10), DataConstants.STR_NOT_FOUND);
        assertEquals(RouteData.retrieveRouteDate(c, 10), DataConstants.STR_NOT_FOUND);
    }

    @Test
    public void testSaveStartingPoint() {
        assertEquals(RouteData.retrieveStartingPoint(c, 10), DataConstants.STR_NOT_FOUND);
        RouteData.saveStartingPoint(c,10, "Starting Point");
        assertEquals(pref.getString(String.format(DataConstants.STARTING_POINT_KEY, 10), ""), "Starting Point");
    }

    @Test
    public void testSaveFlatVsHilly() {
        assertEquals(RouteData.retrieveFlatVsHilly(c, 10), DataConstants.STR_NOT_FOUND);
        RouteData.saveFlatVsHilly(c,10,"Hilly");
        assertEquals(pref.getString(String.format(DataConstants.FLAT_VS_HILLY_KEY, 10), ""), "Hilly");
    }

    @Test
    public void testSaveLoopVsOAB() {
        assertEquals(RouteData.retrieveLoopVsOAB(c, 10), DataConstants.STR_NOT_FOUND);
        RouteData.saveLoopVsOAB(c, 10, "Loop");
        assertEquals(pref.getString(String.format(DataConstants.LOOP_VS_OUTBACK_KEY, 10), ""), "Loop");
    }

    @Test
    public void testSaveStreetVsTrail() {
        assertEquals(RouteData.retrieveStreetVsTrail(c, 10), DataConstants.STR_NOT_FOUND);
        RouteData.saveStreetsVsTrail(c, 10, "Trail");
        assertEquals(pref.getString(String.format(DataConstants.STREETS_VS_TRAIL_KEY, 10), ""), "Trail");
    }

    @Test
    public void testSaveEvenVsUneven() {
        assertEquals(RouteData.retrieveEvenVsUneven(c, 10), DataConstants.STR_NOT_FOUND);
        RouteData.saveEvenVsUneven(c, 10, "Even Surface");
        assertEquals(pref.getString(String.format(DataConstants.EVEN_VS_UNEVEN_SURFACE_KEY, 10), ""), "Even Surface");
    }

    @Test
    public void testSaveDifficulty() {
        assertEquals(RouteData.retrieveDifficulty(c, 10), DataConstants.STR_NOT_FOUND);
        RouteData.saveDifficulty(c, 10, "Easy");
        assertEquals(pref.getString(String.format(DataConstants.ROUTE_DIFFICULTY_KEY, 10), ""), "Easy");
    }

    @Test
    public void testSaveNotes() {
        assertEquals(RouteData.retrieveNotes(c, 10), DataConstants.STR_NOT_FOUND);
        RouteData.saveNotes(c, 10, "It was a nice hike!");
        assertEquals(pref.getString(String.format(DataConstants.ROUTE_NOTES_KEY, 10), ""), "It was a nice hike!");
    }

}
