package com.example.cse110_project.unit_tests;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;

import androidx.test.rule.ActivityTestRule;

import com.example.cse110_project.R;
import com.example.cse110_project.SaveRoute;
import com.example.cse110_project.WalkActivity;
import com.example.cse110_project.data_access.RouteData;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.RouteList;
import com.example.cse110_project.user_routes.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static junit.framework.TestCase.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class SaveRouteTest {
    private Context c;

    @Rule
    public ActivityTestRule<WalkActivity> walkActivity = new ActivityTestRule<>(WalkActivity.class);

    @Before
    public void setup() {
        c = walkActivity.getActivity().getApplicationContext();
        c.setTheme(R.style.AppTheme);
    }

    @Test
    public void testRouteSavedDataProvided() {
        SaveRoute saveRoute = new SaveRoute(walkActivity.getActivity(), c, 100,
                LocalTime.of(1, 2),
                LocalDateTime.of(3, 4, 5, 6, 7));
        AlertDialog alert = saveRoute.inputRouteDataDialog();

        RouteList routes = User.getRoutes(c);
        int prevLength = routes.length();
        String expTime = LocalTime.of(1, 2).toString();
        String expDate = LocalDateTime.of(3, 4, 5, 6, 7)
                .toString();

        EditText nameEditor = saveRoute.getWalkName();
        nameEditor.setText("TestName");
        saveRoute.saveRoute();

        Route route = routes.getRoute(prevLength);
        int id = route.getID();

        assertEquals(prevLength + 1, routes.length());
        assertEquals("TestName", route.getName());
        assertEquals(100, route.getSteps());
        assertEquals(expTime, route.getDuration().toString());
        assertEquals(expDate, route.getStartDate().toString());

        assertEquals("TestName", RouteData.retrieveRouteName(c, id));
        assertEquals(100, RouteData.retrieveRouteSteps(c, id));
        assertEquals(expTime, RouteData.retrieveRouteTime(c, id));
        assertEquals(expDate, RouteData.retrieveRouteDate(c, id));
    }
}
