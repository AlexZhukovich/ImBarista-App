package com.alexzh.imbarista.helpers

import android.app.Activity
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage

object ActivityInstance {

    fun get(): Activity {
        var currentActivity: Activity? = null
        val resumedActivity =
            ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
        if (resumedActivity.iterator().hasNext()) {
            currentActivity = resumedActivity.iterator().next()
        }
        return currentActivity!!
    }
}