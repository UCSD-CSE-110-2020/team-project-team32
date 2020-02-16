package com.example.cse110_project.unit_tests;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.test.rule.ActivityTestRule;

import com.example.cse110_project.MainActivity;
import com.example.cse110_project.R;
import com.example.cse110_project.SaveRoute;
import com.example.cse110_project.WalkActivity;
import com.example.cse110_project.data_access.UserData;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.RouteList;
import com.example.cse110_project.user_routes.User;
import com.google.common.truth.Truth;

import org.apache.tools.ant.Main;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import java.time.LocalDateTime;
import java.time.LocalTime;

@RunWith(RobolectricTestRunner.class)
public class SaveRouteDialogTest {
    private Context c;
    private AlertDialog dialogSave ;
    private SaveRoute saveRoute;
    private EditText walkName;
    private EditText startingPoint;
    private Button FlatButton;
    private Button LoopButton;
    private Button trailButton;
    private Button streetButton;
    private Button DifficultyEasy;
    private Button EvenSurfaceButton;

    private EditText notesInput;
    private int getRouteLength;
    Route route;



    @Rule
    public ActivityTestRule<WalkActivity> walkActivity = new ActivityTestRule<>(WalkActivity.class);

    @Before
    public void setup() {
        c = walkActivity.getActivity().getApplicationContext();
        c.setTheme(R.style.AppTheme);

        saveRoute = new SaveRoute(walkActivity.getActivity(), c, 100,
                LocalTime.of(1, 2),
                LocalDateTime.of(3, 4, 5, 6, 7));

        dialogSave = saveRoute.inputRouteDataDialog();
        walkName = dialogSave.findViewById(R.id.NameOfWalkInput);
        startingPoint = dialogSave.findViewById(R.id.startingPointInput);
        FlatButton =  dialogSave.findViewById(R.id.FlatButton);
        LoopButton = dialogSave.findViewById(R.id.loopButton);
        trailButton = dialogSave.findViewById(R.id.trailButton);
        DifficultyEasy = dialogSave.findViewById(R.id.EasyButton);
        notesInput = dialogSave.findViewById(R.id.NotesOfWalkInput);
        streetButton = dialogSave.findViewById(R.id.streetButton);
        EvenSurfaceButton = dialogSave.findViewById(R.id.evenSurfaceButton);
    }

    @Test
    public void NormalPassingInputsTest() {
        assertTrue(dialogSave.isShowing());

        // enter data
        walkName.setText("this is a test");
        startingPoint.setText("San Diego");
        FlatButton.performClick();
        LoopButton.performClick();
        trailButton.performClick();
        notesInput.setText("this is a note");

        //save data
        saveRoute.saveRoute();
        dialogSave.dismiss();

        //assert data is being saved in routes
        getRouteLength = User.getRoutes(c).length();
        route = User.getRoutes(c).getRoute(getRouteLength - 1);

        assertEquals(route.getName(), "this is a test");
        assertEquals(route.getStartingPoint(), "San Diego");
        assertEquals(route.getFlatVsHilly(),"Flat");
        assertEquals(route.getLoopVsOAB(), "Loop");
        assertEquals(route.getStreetsVsTrail(),"Trail");
        assertEquals(route.getNotes(),"this is a note");
        assertEquals(route.getDifficulty(), "");
        assertEquals(route.getEvenVsUneven(),"");

    }

    @Test
    public void noWalkNameEntered() {
        assertTrue(dialogSave.isShowing());

        // no walk name entered
        walkName.setText("");
        assertEquals(walkName.getError(),"Please enter a walk name");

        walkName.setText("this is another test");
        startingPoint.setText("new walk");
        FlatButton.performClick();
        LoopButton.performClick();
        streetButton.performClick();
        DifficultyEasy.performClick();
        notesInput.setText("this is a new note");


        //save data
        saveRoute.saveRoute();
        dialogSave.dismiss();

        //assert data is being saved in routes
        getRouteLength = User.getRoutes(c).length();
        //size should increment
        route = User.getRoutes(c).getRoute(getRouteLength - 1);

        assertEquals(route.getName(), "this is another test");
        assertEquals(route.getStartingPoint(), "new walk");
        assertEquals(route.getFlatVsHilly(),"Flat");
        assertEquals(route.getLoopVsOAB(),"Loop");
        assertEquals(route.getStreetsVsTrail(),"Streets");
        assertEquals(route.getNotes(),"this is a new note");
        assertEquals(route.getDifficulty(), "Easy");
        assertEquals(route.getEvenVsUneven(),"");

    }

    @Test
    public void exceedmaxCharLimit() {
        assertTrue(dialogSave.isShowing());
        String walkNameText = "this is a walk name dialog shouldnt take more than the first twenty one " +
                "characters of this text";

        String NotesText = "dialog should not take no more than 80 characters of this text because it is restricted" +
                "dialog should not take no more than 80 characters of this text because it is restricted " +
                "dialog should not take no more than 80 characters of this text because it is restricted ";

        walkName.setText(walkNameText);

        startingPoint.setText(walkNameText);

        FlatButton.performClick();
        LoopButton.performClick();
        streetButton.performClick();
        DifficultyEasy.performClick();
        EvenSurfaceButton.performClick();

        notesInput.setText(NotesText);


        //save data
        saveRoute.saveRoute();
        dialogSave.dismiss();

        //assert data is being saved in routes
        getRouteLength = User.getRoutes(c).length();
        //size should increment
        route = User.getRoutes(c).getRoute(getRouteLength - 1);

        assertEquals(route.getName(), walkNameText.substring(0,21));
        assertEquals(route.getStartingPoint(), walkNameText.substring(0,24));
        assertEquals(route.getFlatVsHilly(),"Flat");
        assertEquals(route.getLoopVsOAB(),"Loop");
        assertEquals(route.getStreetsVsTrail(),"Streets");
        assertEquals(route.getNotes(),NotesText.substring(0,80));
        assertEquals(route.getDifficulty(), "Easy");
        assertEquals(route.getEvenVsUneven(), "Even surface");

    }

    @Test
    public void onlyWalkNameEntered () {
        assertTrue(dialogSave.isShowing());

        walkName.setText("test");

        //save data
        saveRoute.saveRoute();
        dialogSave.dismiss();

        //assert data is being saved in routes
        getRouteLength = User.getRoutes(c).length();
        //size should increment
        route = User.getRoutes(c).getRoute(getRouteLength - 1);

        assertEquals(route.getName(), "test");
        assertEquals(route.getStartingPoint(), "");
        assertEquals(route.getFlatVsHilly(),"");
        assertEquals(route.getLoopVsOAB(),"");
        assertEquals(route.getStreetsVsTrail(),"");
        assertEquals(route.getNotes(),"");
        assertEquals(route.getDifficulty(), "");
        assertEquals(route.getEvenVsUneven(), "");

    }
}
