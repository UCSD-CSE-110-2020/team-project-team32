package com.example.cse110_project.fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import com.example.cse110_project.MainActivity;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.user_routes.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class GoogleFitAdapter implements FitnessService {
    private final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = System.identityHashCode(this) & 0xFFFF;
    private final String TAG = "GoogleFitAdapter";
    private GoogleSignInAccount account;

    private User user;
    private AppCompatActivity activity;

    public GoogleFitAdapter(AppCompatActivity activity) {
        user = WWRApplication.getUser();
        this.activity = activity;
    }


    public void setup() {
        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build();


        account = GoogleSignIn.getAccountForExtension(activity, fitnessOptions);
        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    activity, // your activity
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    account,
                    fitnessOptions);
            System.out.println("No permissions? : " + GoogleSignIn.hasPermissions(account, fitnessOptions));
        } else {
            updateStepCount();
            startRecording();
        }
    }

    private void startRecording() {
        System.out.println("Recording");
        if (account == null) {
            return;
        }

        Fitness.getRecordingClient(activity, account)
                .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "Successfully subscribed!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "There was a problem subscribing.");
                    }
                });
    }


    /**
     * Reads the current daily step total, computed from midnight of the current day on the device's
     * current timezone.
     */
    public void updateStepCount() {
        if (account == null) {
            System.out.println(TAG + " Account was null");
            return;
        }

        Fitness.getHistoryClient(activity, account)
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(
                        dataSet -> {
                            Log.d(TAG, dataSet.toString());
                            long total =
                                    dataSet.isEmpty()
                                            ? 0
                                            : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();

                            user.setFitnessSteps((int)total);
                            if (activity instanceof MainActivity) {
                                ((MainActivity)activity).updateDailySteps((int)total);
                            }
                            Log.d(TAG, "Total steps: " + total);
                        })
                .addOnFailureListener(
                        e -> Log.d(TAG, "There was a problem getting the step count.", e));
    }


    @Override
    public int getRequestCode() {
        return GOOGLE_FIT_PERMISSIONS_REQUEST_CODE;
    }

}