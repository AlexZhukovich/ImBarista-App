package com.alexzh.imbarista.helpers.matchers

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

/**
 * Matcher(s) for [Toolbar] class.
 */
object ToolbarMatchers {

    /**
     * Returns a matcher that matches [View] that is displaying the string in Toolbar
     * associated with the given id.
     *
     * @param textMatcher the string matcher the toolbar title is expected to hold.
     */
    fun withToolbarTitle(textMatcher: Matcher<String>): Matcher<View> {
        return object : BoundedMatcher<View, Toolbar>(Toolbar::class.java) {
            override fun describeTo(description: Description?) {
                description?.appendText(textMatcher.toString())
            }

            override fun matchesSafely(toolbar: Toolbar?): Boolean {
                return textMatcher.matches(toolbar?.title)
            }
        }
    }
}