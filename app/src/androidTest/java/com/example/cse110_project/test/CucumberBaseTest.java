package com.example.cse110_project.test;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.runner.lifecycle.ActivityLifecycleMonitor;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import cucumber.api.CucumberOptions;
import cucumber.api.java.After;
import cucumber.api.java.Before;

@CucumberOptions(tags = {"~@skip", "~@skipAndroid"}
        //format = {"json:/sdcard/cuctests/cuc.json", "html:/sdcard/cuctests/cuc-html"}
)
public class CucumberBaseTest {

    @Before
    public void beforeScenario() {
        //
    }

    @After
    public void afterScenario() {
        //
    }


    public static void closeAllActivities(Instrumentation instrumentation) throws Exception {
        final int NUMBER_OF_RETRIES = 100;
        int i = 0;
        while (closeActivity(instrumentation)) {
            if (i++ > NUMBER_OF_RETRIES) {
                throw new AssertionError("Limit of retries excesses");
            }
            Thread.sleep(200);
        }
    }

    public static <X> X callOnMainSync(Instrumentation instrumentation, final Callable<X> callable) throws Exception {
        final AtomicReference<X> retAtomic = new AtomicReference<>();
        final AtomicReference<Throwable> exceptionAtomic = new AtomicReference<>();
        instrumentation.runOnMainSync(() -> {
            try {
                retAtomic.set(callable.call());
            } catch (Throwable e) {
                exceptionAtomic.set(e);
            }
        });
        final Throwable exception = exceptionAtomic.get();
        if (exception != null) {
            throw new RuntimeException(exception);
        }
        return retAtomic.get();
    }

    public static Set<Activity> getActivitiesInStages(Stage... stages) {
        final Set<Activity> activities = new HashSet<>();
        final ActivityLifecycleMonitor instance = ActivityLifecycleMonitorRegistry.getInstance();
        for (Stage stage : stages) {
            final Collection<Activity> activitiesInStage = instance.getActivitiesInStage(stage);
            if (activitiesInStage != null) {
                activities.addAll(activitiesInStage);
            }
        }
        return activities;
    }

    private static boolean closeActivity(Instrumentation instrumentation) throws Exception {
        final Boolean activityClosed = callOnMainSync(instrumentation, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final Set<Activity> activities = getActivitiesInStages(Stage.RESUMED,
                        Stage.STARTED, Stage.PAUSED, Stage.STOPPED, Stage.CREATED);
                activities.removeAll(getActivitiesInStages(Stage.DESTROYED));
                if (activities.size() > 0) {
                    final Activity activity = activities.iterator().next();
                    activity.finish();
                    return true;
                } else {
                    return false;
                }
            }
        });
        if (activityClosed) {
            instrumentation.waitForIdleSync();
        }
        return activityClosed;
    }
}