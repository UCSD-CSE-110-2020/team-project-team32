package com.example.cse110_project.unit_tests;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.rule.ActivityTestRule;

import com.example.cse110_project.MainActivity;
import com.example.cse110_project.data_access.DataConstants;
import com.example.cse110_project.data_access.RouteData;
import com.example.cse110_project.data_access.UserData;
import com.example.cse110_project.user_routes.Route;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class UserDataTest {
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

    @Test
    public void testRetrieveHeightExists() {
        editor.putInt(DataConstants.HEIGHT_KEY, 60);
        editor.apply();
        assertEquals(UserData.retrieveHeight(c), 60);
    }

    @Test
    public void testRetrieveHeightDNE() {
        assertEquals(UserData.retrieveHeight(c), DataConstants.NO_HEIGHT_FOUND);
    }

    @Test
    public void testSaveHeightNew() {
        assertEquals(UserData.retrieveHeight(c), DataConstants.NO_HEIGHT_FOUND);
        UserData.saveHeight(c, 60);
        assertEquals(UserData.retrieveHeight(c), 60);
    }

    @Test
    public void testSaveNameOverriding() {
        UserData.saveHeight(c, 5);
        assertEquals(UserData.retrieveHeight(c), 5);
        UserData.saveHeight(c, 60);
        assertEquals(UserData.retrieveHeight(c), 60);
    }

    @Test
    public void testSaveFirstRoute() {
        assertEquals(UserData.retrieveRouteList(c), DataConstants.NO_ROUTES_FOUND);
        Route r = new Route(10, "Name");
        UserData.saveRoute(c, r);
        assertEquals(UserData.retrieveRouteList(c), "10");
        assertEquals(pref.getString(DataConstants.ROUTES_LIST_KEY, ""), "10");
    }

    @Test
    public void testSaveMultipleRoutes() {
        Route r = new Route(10, "Name");
        UserData.saveRoute(c, r);
        assertEquals("10", UserData.retrieveRouteList(c));
        Route r2 = new Route(15, "Name");
        UserData.saveRoute(c, r2);
        assertEquals(UserData.retrieveRouteList(c), "10\t15");
        Route r3 = new Route(100, "Name");
        UserData.saveRoute(c, r3);
        assertEquals(UserData.retrieveRouteList(c), "10\t15\t100");
        assertEquals(pref.getString(DataConstants.ROUTES_LIST_KEY, ""), "10\t15\t100");
    }
}
