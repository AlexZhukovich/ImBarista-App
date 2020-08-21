package com.alexzh.imbarista.matchers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object RecyclerViewMatchers {

    fun withItemCount(count: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun matchesSafely(view: View): Boolean {
                // TODO: implement it
                return false
            }

            override fun describeTo(description: Description) {
                // TODO: implement it
            }
        }
    }

    fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                    ?: return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }
}