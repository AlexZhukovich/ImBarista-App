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
                val menu = view.menu
                return menu.size() == count
            }

            override fun describeTo(description: Description) {
                description.appendText("$count item")
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
                val menu = view.menu
                for (i in 0 until menu.size()) {
                    val item = menu.getItem(i)
                    if (item.title.contains(text)) {
                        return true
                    }
                }
                return false
            }

            override fun describeTo(description: Description) {
                description.appendText("\"$text\" title")
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
                val menu = view.menu
                for (i in 0 until menu.size()) {
                    val item = menu.getItem(i)
                    if (item.isChecked) {
                        return item.itemId == itemId
                    }
                }
                return false
            }

            override fun describeTo(description: Description) {
                description.appendText("view with $itemId checked")
            }
        }
    }
}