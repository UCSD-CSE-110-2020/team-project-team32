package com.example.cse110_project.unit_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.cse110_project.MainActivity;
import com.example.cse110_project.R;
import com.example.cse110_project.UserData;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.common.truth.Truth;

import org.apache.tools.ant.Main;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowToast;

@RunWith(AndroidJUnit4.class)
public class HeightUnitTests {
    private ActivityScenario<MainActivity> scenario;
    private TextView DailySteps;
    private  TextView DailyMiles;
    private EditText setHeight;
    private androidx.appcompat.app.AlertDialog dialog;
    private Button clickOk;

    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        scenario = scenarioRule.getScenario();
        scenario.onActivity(mainActivity -> {
             DailySteps = (TextView) mainActivity.findViewById(R.id.dailyStepsDisplay);
             DailyMiles = (TextView) mainActivity.findViewById(R.id.dailyMilesDisplay);
             dialog = mainActivity.showInputDialog();
             setHeight = dialog.findViewById(R.id.heightInput);
             clickOk = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        });

                }

    @Test
    public void HeightPassingInput() {
        scenario.onActivity(mainActivity -> {
            setHeight.setText("70");

            clickOk.performClick();

            String latestToast = ShadowToast.getTextOfLatestToast();
            assertNull(latestToast);

            Truth.assertThat(setHeight.getError()).isEqualTo(null);
            Truth.assertThat(UserData.retrieveHeight(mainActivity)).isEqualTo(70);
        });
    }

    @Test
    public void HeightInputLong() {
        scenario.onActivity(mainActivity -> {
            setHeight.setText("707");

            clickOk.performClick();

            String latestToast = ShadowToast.getTextOfLatestToast();
            Truth.assertThat(latestToast).isEqualTo("Invalid");

            Truth.assertThat(setHeight.getError()).isEqualTo("Height must have no more than 2 digits");
            Truth.assertThat(UserData.retrieveHeight(mainActivity)).isEqualTo(0);
        });
    }

    @Test
    public void HeightInputTooLong() {
        scenario.onActivity(mainActivity -> {
            setHeight.setText("707777777777777777777");

            clickOk.performClick();

            String latestToast = ShadowToast.getTextOfLatestToast();
            Truth.assertThat(latestToast).isEqualTo("Invalid");

            Truth.assertThat(setHeight.getError()).isEqualTo("Height must have no more than 2 digits");
            Truth.assertThat(UserData.retrieveHeight(mainActivity)).isEqualTo(0);
        });
    }

    @Test
    public void HeightNoInput() {
        scenario.onActivity(mainActivity -> {
            setHeight.setText("");

            clickOk.performClick();

            String latestToast = ShadowToast.getTextOfLatestToast();
            Truth.assertThat(latestToast).isEqualTo("Invalid");

            Truth.assertThat(setHeight.getError()).isEqualTo("Height must be a whole number");
            Truth.assertThat(UserData.retrieveHeight(mainActivity)).isEqualTo(0);
        });
    }

    @Test
    public void HeightInputZero() {
        scenario.onActivity(mainActivity -> {
            setHeight.setText("00");

            clickOk.performClick();

            String latestToast = ShadowToast.getTextOfLatestToast();
            Truth.assertThat(latestToast).isEqualTo("Invalid");

            Truth.assertThat(setHeight.getError()).isEqualTo("Height must be a positive number");
            Truth.assertThat(UserData.retrieveHeight(mainActivity)).isEqualTo(0);
        });
    }

    @Test
    public void HeightInput99() {
        scenario.onActivity(mainActivity -> {
            setHeight.setText("99");

            clickOk.performClick();

            String latestToast = ShadowToast.getTextOfLatestToast();
            Truth.assertThat(latestToast).isEqualTo(null);

            Truth.assertThat(setHeight.getError()).isEqualTo(null);
            Truth.assertThat(UserData.retrieveHeight(mainActivity)).isEqualTo(99);
        });
    }

}
