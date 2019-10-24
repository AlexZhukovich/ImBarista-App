package com.alexzh.imbarista.helpers.matchers

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.hamcrest.Description
import org.hamcrest.Matcher

/**
 * Matcher(s) for [BottomNavigationView] class.
 */
object BottomNavigationMatchers {

    /**
     * Returns a matcher that matches [View] that contains number of items in BottomNavigationView
     * associated with the given View.
     *
     * @param count the number of items in [BottomNavigationView].
     */
    fun withItemCount(count: Int): Matcher<View> {
        return object : BoundedMatcher<View, BottomNavigationView>(BottomNavigationView::class.java) {
            override fun matchesSafely(view: BottomNavigationView): Boolean {
                return false
            }

            override fun describeTo(description: Description) {
                // should be implemented
            }
        }
    }

    /**
     * Returns a matcher that matches [View] that contains text of item in BottomNavigationView
     * associated with the View.
     *
     * @param text the title of items in [BottomNavigationView].
     */
    fun hasItemTitle(text: String): Matcher<View> {
        return object : BoundedMatcher<View, BottomNavigationView>(BottomNavigationView::class.java) {
            override fun matchesSafely(view: BottomNavigationView): Boolean {
                return false
            }

            override fun describeTo(description: Description) {
                // should be implemented
            }
        }
    }

    /**
     * Returns a matcher that matches [View] that has checked item in BottomNavigationView
     * associated with the View.
     *
     * @param itemId the ID of items in [BottomNavigationView].
     */
    fun hasCheckedItem(itemId: Int): Matcher<View> {
        return object : BoundedMatcher<View, BottomNavigationView>(BottomNavigationView::class.java) {
            override fun matchesSafely(view: BottomNavigationView): Boolean {
                return false
            }

            override fun describeTo(description: Description) {
                // should be implemented
            }
        }
    }
}