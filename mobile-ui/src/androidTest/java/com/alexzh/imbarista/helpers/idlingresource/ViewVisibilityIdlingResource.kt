package com.alexzh.imbarista.helpers.idlingresource

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.IdlingResource
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage

/**
 * Idling Resource for [View] visibility verification.
 */
class ViewVisibilityIdlingResource(
    @IdRes private val expectedViewId: Int,
    private val expectedViewVisibility: Int
) : IdlingResource {

    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return ViewVisibilityIdlingResource::class.java.simpleName
    }

    override fun isIdleNow(): Boolean {
        val view: View? = getIdlingResourceActivityInstance().findViewById(expectedViewId)
        val isIdle = if (view != null) {
            view.visibility == expectedViewVisibility
        } else {
            false
        }

        if (isIdle && resourceCallback != null) {
            resourceCallback?.onTransitionToIdle()
        }
        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        resourceCallback = callback
    }

    private fun getIdlingResourceActivityInstance(): Activity {
        var currentActivity: Activity? = null
        val resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
        if (resumedActivities.iterator().hasNext()) {
            currentActivity = resumedActivities.iterator().next()
        }
        return currentActivity!!
    }
}