package com.example.cse110_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.bouncycastle.x509.NoSuchStoreException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class HeightUnitTests {
    private ActivityScenario<MainActivity> scenario;

    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        scenario = scenarioRule.getScenario();
    }

    // NEED TO FIX TESTS
    @Test
    public void HeightPassingInput() {
        scenario.onActivity(mainActivity -> {
            System.out.println(mainActivity.showInputDialog().isShowing());
            TextView DailySteps = (TextView) mainActivity.findViewById(R.id.NumbeOfSteps);
            TextView DailyMiles = (TextView) mainActivity.findViewById(R.id.miles);
            mainActivity.setContentView(R.layout.dialog_height);
            EditText setHeight = (EditText) mainActivity.findViewById(R.id.HeightInput);
            setHeight.setText("707909");

            assertThat(setHeight.getError()).isEqualTo(null);     //no errors should pop up

            Button clickOk = mainActivity.showInputDialog().getButton(DialogInterface.BUTTON_POSITIVE);
            clickOk.performClick();

            //System.out.println("klkklkkl" + mainActivity.retrieveHeight());
            // make sure input is saved
            //assertThat(MilesCalculator.retrieveHeight()).isEqualTo("70");
        });
    }
}
