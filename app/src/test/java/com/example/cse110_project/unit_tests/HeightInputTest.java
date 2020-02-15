package com.example.cse110_project.unit_tests;

import static org.junit.Assert.assertEquals;
import com.example.cse110_project.R;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import android.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cse110_project.MainActivity;
import com.example.cse110_project.data.UserData;
import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowToast;

@RunWith(AndroidJUnit4.class)
public class HeightInputTest {
    private ActivityScenario<MainActivity> scenario;
    private EditText setHeight;
    private androidx.appcompat.app.AlertDialog dialog;
    private Button clickOk;
    private String invalidHeightToast;
    private String longHeightError;
    private String invalidHeightError;
    private String nonpositiveHeightError;

    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        scenario = scenarioRule.getScenario();
        scenario.onActivity(mainActivity -> {
             dialog = mainActivity.showInputDialog();
             setHeight = dialog.findViewById(R.id.heightInput);
             clickOk = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

             invalidHeightToast = mainActivity.getResources().getString(
                     R.string.invalidHeightToast);
             longHeightError = mainActivity.getResources().getString(
                     R.string.longHeightError);
             invalidHeightError = mainActivity.getResources().getString(
                     R.string.invalidHeightError);
             nonpositiveHeightError = mainActivity.getResources().getString(
                     R.string.nonpositiveHeightError);
        });

    }

    @Test
    public void heightPassingInput() {
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
    public void heightInputLong() {
        scenario.onActivity(mainActivity -> {
            setHeight.setText("707");

            clickOk.performClick();

            String latestToast = ShadowToast.getTextOfLatestToast();
            assertEquals(latestToast, invalidHeightToast);

            assertEquals(setHeight.getError(), longHeightError);
            Truth.assertThat(UserData.retrieveHeight(mainActivity)).isEqualTo(0);
        });
    }

    @Test
    public void HeightInputTooLong() {
        scenario.onActivity(mainActivity -> {
            setHeight.setText("707777777777777777777");

            clickOk.performClick();

            String latestToast = ShadowToast.getTextOfLatestToast();
            Truth.assertThat(latestToast).isEqualTo(invalidHeightToast);

            Truth.assertThat(setHeight.getError()).isEqualTo(longHeightError);
            Truth.assertThat(UserData.retrieveHeight(mainActivity)).isEqualTo(0);
        });
    }

    @Test
    public void HeightNoInput() {
        scenario.onActivity(mainActivity -> {
            setHeight.setText("");

            clickOk.performClick();

            String latestToast = ShadowToast.getTextOfLatestToast();
            Truth.assertThat(latestToast).isEqualTo(invalidHeightToast);

            Truth.assertThat(setHeight.getError()).isEqualTo(invalidHeightError);
            Truth.assertThat(UserData.retrieveHeight(mainActivity)).isEqualTo(0);
        });
    }

    @Test
    public void HeightInputZero() {
        scenario.onActivity(mainActivity -> {
            setHeight.setText("00");

            clickOk.performClick();

            String latestToast = ShadowToast.getTextOfLatestToast();
            Truth.assertThat(latestToast).isEqualTo(invalidHeightToast);

            Truth.assertThat(setHeight.getError()).isEqualTo(nonpositiveHeightError);
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
