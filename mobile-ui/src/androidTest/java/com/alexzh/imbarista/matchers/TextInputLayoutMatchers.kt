package com.alexzh.imbarista.matchers

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher

object TextInputLayoutMatchers {

    fun withTextInputLayoutError(textMatcher: Matcher<String>): Matcher<View> {
        return object : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {
            override fun describeTo(description: Description?) {
                description?.appendText("with text input layer error: $textMatcher")
            }

            override fun matchesSafely(textInputEditText: TextInputLayout?): Boolean {
                return textMatcher.matches(textInputEditText?.error.toString())
            }
        }
    }

    fun hasNoErrorsInTextInputLayout(): Matcher<View> {
        return object : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {
            override fun describeTo(description: Description?) {
                description?.appendText("with text input layer error")
            }

            override fun matchesSafely(textInputEditText: TextInputLayout?): Boolean {
                return textInputEditText?.error == null
            }
        }
    }
}