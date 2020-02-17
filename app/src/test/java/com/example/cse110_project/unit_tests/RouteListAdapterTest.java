package com.example.cse110_project.unit_tests;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import com.example.cse110_project.MainActivity;
import com.example.cse110_project.R;
import com.example.cse110_project.RoutesActivity;
import com.example.cse110_project.util.RouteListAdapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class RouteListAdapterTest {
    @Rule
    public ActivityTestRule<RoutesActivity> routesActivity =
            new ActivityTestRule<>(RoutesActivity.class);

    @Test
    public void testGetView() {
        String[] empty = {""};

        RouteListAdapter adapter = new RouteListAdapter(routesActivity.getActivity(),
                new String[] {"Name"}, new String[] {"Start"}, new String[] {"0"},
                new String[] {"0.0"},  new String[] {"00:00"}, new String[] {"00-00"}, empty,
                empty, empty, empty,  empty, empty);
        View v = adapter.getView(0, null, null);

        assertEquals("Name", ((TextView)v.findViewById(R.id.routeRowName)).getText());
        assertEquals("Start",
                ((TextView)v.findViewById(R.id.routeRowStartingPoint)).getText());
        assertEquals("0", ((TextView)v.findViewById(R.id.routeRowSteps)).getText());
        assertEquals("0.0", ((TextView)v.findViewById(R.id.routeRowMiles)).getText());
        assertEquals("00:00", ((TextView)v.findViewById(R.id.routeRowTime)).getText());
        assertEquals("00-00", ((TextView)v.findViewById(R.id.routeRowDate)).getText());
        assertEquals("", ((TextView)v.findViewById(R.id.routeRowLoopOutBack)).getText());
        assertEquals("", ((TextView)v.findViewById(R.id.routeRowFavorite)).getText());
        assertEquals("", ((TextView)v.findViewById(R.id.routeRowFlatHilly)).getText());
        assertEquals("", ((TextView)v.findViewById(R.id.routeRowEvenUneven)).getText());
        assertEquals("", ((TextView)v.findViewById(R.id.routeRowDifficulty)).getText());
        assertEquals("", ((TextView)v.findViewById(R.id.routeRowStreetsTrail)).getText());
    }
}
