package com.example.cse110_project.unit_tests;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.rule.ActivityTestRule;

import com.example.cse110_project.MainActivity;
import com.example.cse110_project.util.DataConstants;
import com.example.cse110_project.user_routes.RouteData;
import com.example.cse110_project.user_routes.UserData;
import com.example.cse110_project.user_routes.UserRoute;
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

    // Tests processRoutes by way of the constructor
    @Test
    public void testGenerationNoRoutes() {
        RouteList list = new RouteList(c);
        assertEquals(list.length(), 0);
    }

    @Test
    public void testGenerationSomeRoutes() {
        UserRoute r = new UserRoute(10, "Name", 50, LocalTime.of(10,10),
                LocalDateTime.of(1, 1, 1, 1, 1));
        UserData.saveRoute(c, r);
        RouteData.saveRouteData(c, r);
        UserRoute r2 = new UserRoute(15, "Name2");
        UserData.saveRoute(c, r2);
        RouteData.saveRouteData(c, r2);
        UserRoute r3 = new UserRoute(100, "Name");
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
        UserRoute r = new UserRoute(10, "Name");
        UserData.saveRoute(c, r);
        RouteData.saveRouteData(c, r);

        RouteList list = new RouteList(c);
        assertNull(list.getMostRecentRoute());
    }

    @Test
    public void testRecentMultRoutesFirstDate() {
        UserRoute r = new UserRoute(10, "Name", 50, LocalTime.of(10,10),
                LocalDateTime.of(1, 1, 1, 1, 1));
        UserData.saveRoute(c, r);
        RouteData.saveRouteData(c, r);
        UserRoute r2 = new UserRoute(15, "Name2");
        UserData.saveRoute(c, r2);
        RouteData.saveRouteData(c, r2);
        UserRoute r3 = new UserRoute(100, "Name3");
        UserData.saveRoute(c, r3);
        RouteData.saveRouteData(c, r3);

        RouteList list = new RouteList(c);
        assertEquals(10, list.getMostRecentRoute().getID());
    }

    @Test
    public void testRecentMultRoutesLastDate() {
        UserRoute r2 = new UserRoute(15, "Name2");
        UserData.saveRoute(c, r2);
        RouteData.saveRouteData(c, r2);
        UserRoute r3 = new UserRoute(100, "Name3");
        UserData.saveRoute(c, r3);
        RouteData.saveRouteData(c, r3);
        UserRoute r = new UserRoute(10, "Name", 50, LocalTime.of(10,10),
                LocalDateTime.of(1, 1, 1, 1, 1));
        UserData.saveRoute(c, r);
        RouteData.saveRouteData(c, r);

        RouteList list = new RouteList(c);
        assertEquals(10, list.getMostRecentRoute().getID());
    }

    @Test
    public void testRecentMultRoutesMultDates() {
        UserRoute r = new UserRoute(10, "Name", 50, LocalTime.of(10,10),
                LocalDateTime.of(1, 1, 1, 1, 1));
        UserData.saveRoute(c, r);
        RouteData.saveRouteData(c, r);
        UserRoute r2 = new UserRoute(15, "Name2");
        UserData.saveRoute(c, r2);
        RouteData.saveRouteData(c, r2);
        UserRoute r3 = new UserRoute(100, "Name3", 50, LocalTime.of(10,10),
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
        list.createRoute(new UserRoute(0, "Name"));
        assertEquals(1, list.length());
        assertNotEquals(0, list.getRoute(0).getID());
        assertEquals("Name", list.getRoute(0).getName());

        list.createRoute(new UserRoute(list.getRoute(0).getID(), "Name"));
        assertEquals(2, list.length());
        assertNotEquals(list.getRoute(0).getID(), list.getRoute(1).getID());
        assertEquals("Name", list.getRoute(1).getName());
    }

    @Test
    public void testUpdateRouteData(){
        RouteList list = new RouteList(c);
        list.createRoute(new UserRoute(0, "Name"));
        list.updateRouteData(0, 500,LocalTime.of(10,10),
                LocalDateTime.of(2020,1,1,1,1) );

        list.createRoute(new UserRoute(list.getRoute(0).getID(), "Name"));
        assertNotEquals(list.getRoute(0).getID(), list.getRoute(1).getID());
        assertEquals(list.getRoute(0).getName(), "Name");
        assertEquals(list.getRoute(0).getSteps(), 500);
        assertEquals(list.getRoute(0).getDuration(), LocalTime.of(10,10));
        assertEquals(list.getRoute(0).getStartDate(),
                LocalDateTime.of(2020,1,1,1,1));
    }

    @Test
    public void testSortByName(){
        RouteList list = new RouteList(c);
        list.createRoute(new UserRoute(0, "A"));
        list.createRoute(new UserRoute(2, "B1"));
        list.createRoute(new UserRoute(3, "a1"));
        list.createRoute(new UserRoute(1, "b"));

        assertEquals(list.getRoute(0).getName(), "A");
        assertEquals(list.getRoute(1).getName(), "B1");
        assertEquals(list.getRoute(2).getName(), "a1");
        assertEquals(list.getRoute(3).getName(), "b");

        list.sortByName();

        assertEquals(list.getRoute(0).getName(), "A");
        assertEquals(list.getRoute(1).getName(), "a1");
        assertEquals(list.getRoute(2).getName(), "b");
        assertEquals(list.getRoute(3).getName(), "B1");
    }

}