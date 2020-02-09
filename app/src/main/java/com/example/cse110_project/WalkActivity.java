package com.example.cse110_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessActivities;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.request.SessionInsertRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.result.SessionReadResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class WalkActivity extends AppCompatActivity {

    private GoogleSignInAccount account;
    private Button cancelButton;
    private long startTime, endTime;
    private Session session;
    private String uniqueID;
    private Button stopButton;
    private final String TAG = "AddSession";
    private Calendar calendar;
    private EditText WalkName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        // cancel Button Clicked
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> BackToHomeActivity());
        stopButton = findViewById(R.id.stopWalkButton);
        calendar = Calendar.getInstance();

        startTime = calendar.getTimeInMillis();
        getGoogleAccount();
        generateUniqueSessionID();
        setUpSession();

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                endTime = calendar.getTimeInMillis();
                stopSession();
                showSaveDialog();
            }
        });


    }

    public void getGoogleAccount(){
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            account = GoogleSignIn.getLastSignedInAccount(this);
        }
        else {
            Toast.makeText(WalkActivity.this, "Please Sign In To Your Google Account",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void stopSession() {
        Task<List<Session>> response = Fitness.getSessionsClient(this, account)
                .stopSession(session.getIdentifier());
    }

    public void setUpSession() {
         session = new Session.Builder()
                .setName("")
                .setIdentifier(uniqueID)
                .setDescription("")
                .setStartTime(startTime, TimeUnit.MILLISECONDS)
                .build();

        Task<Void> response = Fitness.getSessionsClient(this, account).startSession(session);
    }

    public void generateUniqueSessionID() {
        uniqueID = UUID.randomUUID().toString();
    }

    public void BackToHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public Task<Void> saveSessionRequest() {
        Session session = new Session.Builder()
                .setName(WalkName.getText().toString())
                .setDescription("Long run around Shoreline Park") // need user input here
                .setIdentifier(uniqueID)
                .setActivity(FitnessActivities.RUNNING)
                .setStartTime(startTime, TimeUnit.MILLISECONDS)
                .setEndTime(endTime, TimeUnit.MILLISECONDS)
                .build();

        SessionInsertRequest insertRequest = new SessionInsertRequest.Builder()
                .setSession(session)
                //.addDataSet() HERE WE CAN ADD WHATEVER WE NEED TO SAVE FOR THE RUN
                //.addDataSet(activitySegments)
                .build();

        Log.i(TAG, "Inserting the session in the Sessions API");
        return Fitness.getSessionsClient(this, account)
                .insertSession(insertRequest)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // At this point, the session has been inserted and can be read.
                        System.out.println("session being inserted" + session);
                        Log.i(TAG, "Session insert was successful!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "There was a problem inserting the session: " +
                                e.getLocalizedMessage());
                    }
                });
    }

    public Task<SessionReadResponse> readSession(){
        // Set a start and end time for our query, using a start time of 1 week before this moment.
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        long startTime = cal.getTimeInMillis();

        // Build a session read request
        SessionReadRequest readRequest = new SessionReadRequest.Builder()
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                .read(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                //.setSessionName("get sessions 1 week before this moment") for some reason this line was causing to return 0 sessions
                .build();

        return Fitness.getSessionsClient(this, account)
                .readSession(readRequest)
                .addOnSuccessListener(new OnSuccessListener<SessionReadResponse>() {
                    @Override
                    public void onSuccess(SessionReadResponse sessionReadResponse) {
                        // Get a list of the sessions that match the criteria to check the result.
                        List<Session> sessions = sessionReadResponse.getSessions();
                        Log.i(TAG, "Session read was successful. Number of returned sessions is: "
                                + sessions.size());

                        for (Session session : sessions) {
                            // Process the session
                            System.out.println("session is: " + session);
                            // Process the data sets for this session
                            List<DataSet> dataSets = sessionReadResponse.getDataSet(session);
                            for (DataSet dataSet : dataSets) {
                                System.out.println("data: " + dataSet);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "Failed to read session");
                    }
                });
    }

    public AlertDialog showSaveDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(WalkActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_save, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WalkActivity.this)
                .setCancelable(false)
                .setPositiveButton("Save", null)
                .setNegativeButton("Cancel", null);
        alertDialogBuilder.setView(promptView);

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        Button submitButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        Button cancelButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                makeSureToCancel();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                InputWalkDataDialog();
            }
        });

        return alert;
    }

    public AlertDialog InputWalkDataDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(WalkActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_walkdata, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WalkActivity.this)
                .setCancelable(false)
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null);
        alertDialogBuilder.setView(promptView);

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        WalkName = promptView.findViewById(R.id.NameOfWalkInput);
        Button submitButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        Button cancelButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WalkActivity.this , R.string.cancelDialog,
                        Toast.LENGTH_SHORT).show();
                alert.dismiss();
                BackToHomeActivity();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSessionRequest();
                readSession();
                Toast.makeText(WalkActivity.this , "saved",
                        Toast.LENGTH_SHORT).show();
                alert.dismiss();
                BackToHomeActivity();
            }
        });

        return alert;
    }

    public AlertDialog makeSureToCancel() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(WalkActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_sure_cancel, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WalkActivity.this)
                .setCancelable(false)
                .setPositiveButton("Yes", null)
                .setNegativeButton("No", null);
        alertDialogBuilder.setView(promptView);

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        Button submitButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        Button cancelButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                InputWalkDataDialog();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WalkActivity.this , R.string.cancelDialog,
                        Toast.LENGTH_SHORT).show();
                alert.dismiss();
                BackToHomeActivity();
            }
        });

        return alert;
    }

}
