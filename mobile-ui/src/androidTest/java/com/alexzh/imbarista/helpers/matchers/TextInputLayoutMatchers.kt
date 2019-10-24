package com.alexzh.imbarista.helpers.matchers

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher

/**
 * Matcher for [TextInputLayout] class.
 */
object TextInputLayoutMatchers {

    /**
     * Returns a matcher that matches [View] that contains error in TextInputLayout.
     *
     * @param textMatcher the matcher with error
     */
    fun withTextInputLayoutError(textMatcher: Matcher<String>): Matcher<View> {
        return object : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {
            override fun describeTo(description: Description?) {
                // should be implemented
            }

            override fun matchesSafely(textInputEditText: TextInputLayout?): Boolean {
                return false
            }
        }
    }

    /**
     * Returns a matcher that matches [View] that not contains errors in TextInputLayout.
     */
    fun hasNoErrorsInTextInputLayout(): Matcher<View> {
        return object : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {
            override fun describeTo(description: Description?) {
                // should be implemented
            }

            override fun matchesSafely(textInputEditText: TextInputLayout?): Boolean {
                return false
            }
        }
    }
}