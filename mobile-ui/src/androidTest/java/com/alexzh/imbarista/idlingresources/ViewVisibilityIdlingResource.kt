package com.alexzh.imbarista.idlingresources

import android.app.Activity
import android.view.View
import androidx.test.espresso.IdlingResource
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage

class ViewVisibilityIdlingResource(
    private val viewId: Int,
    private val expectedVisibility: Int
) : IdlingResource {

    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return ViewVisibilityIdlingResource::class.java.name
    }

    override fun isIdleNow(): Boolean {
        val view: View? = getIdlingResourceActivityInstance().findViewById(viewId)
        val isIdleNow = if (view != null) {
            view.visibility == expectedVisibility
        } else {
            false
        }

        if (isIdleNow && resourceCallback != null) {
            resourceCallback?.onTransitionToIdle()
        }
        return isIdleNow
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = callback
    }

    private fun getIdlingResourceActivityInstance(): Activity {
        var currentActivity: Activity? = null
        val resumedActivity =
            ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
        if (resumedActivity.iterator().hasNext()) {
            currentActivity = resumedActivity.iterator().next()
        }
        return currentActivity!!
    }
}