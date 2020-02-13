package com.example.cse110_project.unit_tests;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.rule.ActivityTestRule;

import com.example.cse110_project.MainActivity;
import com.example.cse110_project.data_access.DataConstants;
import com.example.cse110_project.data_access.RouteData;
import com.example.cse110_project.data_access.UserData;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.RouteList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricTestRunner.class)
public class RouteListTest {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context c;

    @Rule
    public ActivityTestRule<MainActivity> mainActivity =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        pref = mainActivity.getActivity().getSharedPreferences(DataConstants.USER_DATA_FILE,
                Context.MODE_PRIVATE);
        editor = pref.edit();
        c = mainActivity.getActivity().getApplicationContext();
    }

    // Tests processRoutes by way of the ctor
    @Test
    public void testGenerationNoRoutes() {
        RouteList list = new RouteList(c);
        assertEquals(list.length(), 0);
    }

    // Tests processRoutes by way of the ctor
    @Test
    public void testGenerationSomeRoutes() {
        Route r = new Route(10, "Name", 50, LocalTime.of(10,10),
                LocalDateTime.of(1, 1, 1, 1, 1));
        UserData.saveRoute(c, r);
        RouteData.saveRouteData(c, r);
        Route r2 = new Route(15, "Name2");
        UserData.saveRoute(c, r2);
        RouteData.saveRouteData(c, r2);
        Route r3 = new Route(100, "Name");
        UserData.saveRoute(c, r3);
        RouteData.saveRouteData(c, r3);

        RouteList list = new RouteList(c);
        assertEquals(3, list.length());

        assertEquals(10, list.getRoute(0).getID());
        assertEquals("Name", list.getRoute(0).getName());
        assertEquals(50, list.getRoute(0).getSteps());
        assertEquals(LocalTime.of(10,10).toString(),
                list.getRoute(0).getDuration().toString());
        assertEquals(LocalDateTime.of(1, 1, 1, 1, 1).toString(),
                list.getRoute(0).getStartDate().toString());

        assertEquals(15, list.getRoute(1).getID());
        assertEquals("Name2", list.getRoute(1).getName());
        assertEquals(DataConstants.INT_NOT_FOUND, list.getRoute(1).getSteps());
        assertNull(list.getRoute(1).getDuration());
        assertNull(list.getRoute(1).getStartDate());

        assertEquals(100, list.getRoute(2).getID());
        assertEquals("Name", list.getRoute(2).getName());
        assertEquals(DataConstants.INT_NOT_FOUND, list.getRoute(2).getSteps());
        assertNull(list.getRoute(2).getDuration());
        assertNull(list.getRoute(2).getStartDate());
    }

    @Test
    public void testRecentNoRoutes() {
        RouteList list = new RouteList(c);
        assertNull(list.getMostRecentRoute());
    }

    @Test
    public void testRecentOneRouteNoDate() {
        Route r = new Route(10, "Name");
        UserData.saveRoute(c, r);
        RouteData.saveRouteData(c, r);

        RouteList list = new RouteList(c);
        assertNull(list.getMostRecentRoute());
    }

    @Test
    public void testRecentMultRoutesFirstDate() {
        Route r = new Route(10, "Name", 50, LocalTime.of(10,10),
                LocalDateTime.of(1, 1, 1, 1, 1));
        UserData.saveRoute(c, r);
        RouteData.saveRouteData(c, r);
        Route r2 = new Route(15, "Name2");
        UserData.saveRoute(c, r2);
        RouteData.saveRouteData(c, r2);
        Route r3 = new Route(100, "Name3");
        UserData.saveRoute(c, r3);
        RouteData.saveRouteData(c, r3);

        RouteList list = new RouteList(c);
        assertEquals(10, list.getMostRecentRoute().getID());
    }

    @Test
    public void testRecentMultRoutesLastDate() {
        Route r2 = new Route(15, "Name2");
        UserData.saveRoute(c, r2);
        RouteData.saveRouteData(c, r2);
        Route r3 = new Route(100, "Name3");
        UserData.saveRoute(c, r3);
        RouteData.saveRouteData(c, r3);
        Route r = new Route(10, "Name", 50, LocalTime.of(10,10),
                LocalDateTime.of(1, 1, 1, 1, 1));
        UserData.saveRoute(c, r);
        RouteData.saveRouteData(c, r);

        RouteList list = new RouteList(c);
        assertEquals(10, list.getMostRecentRoute().getID());
    }

    @Test
    public void testRecentMultRoutesMultDates() {
        Route r = new Route(10, "Name", 50, LocalTime.of(10,10),
                LocalDateTime.of(1, 1, 1, 1, 1));
        UserData.saveRoute(c, r);
        RouteData.saveRouteData(c, r);
        Route r2 = new Route(15, "Name2");
        UserData.saveRoute(c, r2);
        RouteData.saveRouteData(c, r2);
        Route r3 = new Route(100, "Name3", 50, LocalTime.of(10,10),
                LocalDateTime.of(2, 1, 1, 1, 1));
        UserData.saveRoute(c, r3);
        RouteData.saveRouteData(c, r3);

        RouteList list = new RouteList(c);
        assertEquals(3, list.length());
        assertEquals(100, list.getMostRecentRoute().getID());
        assertEquals(50, list.getMostRecentRoute().getSteps());
    }

    @Test
    public void testCreateRoute() {
        RouteList list = new RouteList(c);
        list.createRoute(c, new Route(0, "Name"));
        assertEquals(1, list.length());
        assertNotEquals(0, list.getRoute(0).getID());
        assertEquals("Name", list.getRoute(0).getName());

        list.createRoute(c, new Route(list.getRoute(0).getID(), "Name"));
        assertEquals(2, list.length());
        assertNotEquals(list.getRoute(0).getID(), list.getRoute(1).getID());
        assertEquals("Name", list.getRoute(1).getName());
    }
}