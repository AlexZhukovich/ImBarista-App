package com.alexzh.imbarista.helpers.actions

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matcher

/**
 * Action(s) for [View] class.
 */
object ViewActions {

    /**
     * Returns an action that clicks the child view for a specific id.
     *
     * @param id the child view id, which should be clicked.
     */
    fun clickByChildViewWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return `is`(withEffectiveVisibility(Visibility.VISIBLE))
            }

            override fun getDescription(): String {
                return "performing click on child view with id: $id"
            }

            override fun perform(uiController: UiController, view: View) {
                view.findViewById<View>(id).performClick()
            }
        }
    }
}