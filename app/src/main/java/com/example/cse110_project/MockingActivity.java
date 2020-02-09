package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import com.example.cse110_project.MainActivity;

import com.example.cse110_project.user_routes.User;


public class MockingActivity extends AppCompatActivity {

    private Button back_button;
    private Button increment_button;

//    private MainActivity activity;
//
//    public MockingActivity(MainActivity activity) {
//        this.activity = activity;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mocking);

        // creating back button
        back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });


        // linking 500+ button
        increment_button = (Button) findViewById(R.id.increment);
        increment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get current steps from API
                int current_steps = User.getSteps();
                System.out.println(current_steps);

                current_steps = current_steps + 500;

                // run a function to update steps
                // .updateDailySteps(value + 500);
//
//               activity.updateDailySteps(current_steps);
//                User.setSteps(current_steps);

                System.out.println(current_steps);

            }
        });



    }

    //openMockingActivity method
    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
