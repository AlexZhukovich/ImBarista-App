package com.alexzh.imbarista.matchers

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

object ToolbarMatchers {

    fun withToolbarTitle(textMatcher: Matcher<String>): Matcher<View> {
        return object : BoundedMatcher<View, Toolbar>(Toolbar::class.java) {
            override fun describeTo(description: Description?) {
                description?.appendText("with toolbar title: $textMatcher")
            }

            override fun matchesSafely(toolbar: Toolbar?): Boolean {
                return textMatcher.matches(toolbar?.title)
            }
        }
    }
}