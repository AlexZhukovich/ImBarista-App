package com.alexzh.imbarista.matchers

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.hamcrest.Description
import org.hamcrest.Matcher

object BottomNavigationViewMatchers {

    fun withItemCount(count: Int): Matcher<View> {
        return object : BoundedMatcher<View, BottomNavigationView>(BottomNavigationView::class.java) {
            override fun matchesSafely(view: BottomNavigationView): Boolean {
                return false
            }

            override fun describeTo(description: Description) {
                description.appendText("BottomNavigationView should have $count item")
            }
        }
    }

    fun hasItemTitle(text: String): Matcher<View> {
        return object : BoundedMatcher<View, BottomNavigationView>(BottomNavigationView::class.java) {
            override fun matchesSafely(view: BottomNavigationView): Boolean {
                return false
            }

            override fun describeTo(description: Description) {
                description.appendText("BottomNavigationView should have item with text: $text")
            }
        }
    }

    fun hasCheckedItem(itemId: Int): Matcher<View> {
        return object : BoundedMatcher<View, BottomNavigationView>(BottomNavigationView::class.java) {
            override fun matchesSafely(view: BottomNavigationView): Boolean {
                return false
            }

            override fun describeTo(description: Description) {
                description.appendText("BottomNavigationView should have checked item with id: $itemId")
            }
        }
    }
}