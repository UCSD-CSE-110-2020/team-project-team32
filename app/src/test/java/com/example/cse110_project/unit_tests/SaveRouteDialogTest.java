package com.example.cse110_project.unit_tests;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.rule.ActivityTestRule;

import com.example.cse110_project.R;
import com.example.cse110_project.SaveRouteDialog;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.WalkActivity;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.RouteData;
import com.example.cse110_project.user_routes.UserRoute;
import com.example.cse110_project.user_routes.RouteList;
import com.example.cse110_project.user_routes.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowToast;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

@RunWith(RobolectricTestRunner.class)
public class SaveRouteDialogTest {
    private Context c;
    private User user;

    @Rule
    public ActivityTestRule<WalkActivity> walkActivity = new ActivityTestRule<>(WalkActivity.class);

    @Before
    public void setup() {
        user = WWRApplication.getUser();
        c = walkActivity.getActivity().getApplicationContext();
        c.setTheme(R.style.AppTheme);
    }

    @Test
    public void testSaveRouteDataProvided() {
        SaveRouteDialog saveRouteDialog = new SaveRouteDialog(walkActivity.getActivity(), c, 100,
                LocalTime.of(1, 2),
                LocalDateTime.of(3, 4, 5, 6, 7));
        saveRouteDialog.inputRouteDataDialog();

        RouteList routes = user.getRoutes();
        int prevLength = routes.length();
        String expTime = LocalTime.of(1, 2).toString();
        String expDate = LocalDateTime.of(3, 4, 5, 6, 7)
                .toString();

        EditText nameEditor = saveRouteDialog.getWalkName();
        nameEditor.setText("TestName");
        assertNull(nameEditor.getError());
        saveRouteDialog.saveRoute();

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

    @Test
    public void testSaveRouteNoName() {
        SaveRouteDialog saveRouteDialog = new SaveRouteDialog(walkActivity.getActivity(), c, 100,
                LocalTime.of(1, 2),
                LocalDateTime.of(3, 4, 5, 6, 7));
        AlertDialog alert = saveRouteDialog.inputRouteDataDialog();

        EditText nameEditor = saveRouteDialog.getWalkName();
        nameEditor.setText("");
        assertEquals(walkActivity.getActivity().getResources().getString(R.string.emptyRouteName),
                nameEditor.getError());

        Button submitButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        submitButton.performClick();
        assertEquals(walkActivity.getActivity().getResources().getString(R.string.emptyRouteName),
                ShadowToast.getTextOfLatestToast());
    }
}
