package com.alexzh.imbarista.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alexzh.imbarista.R
import com.alexzh.imbarista.matchers.BottomNavigationViewMatchers
import com.alexzh.imbarista.matchers.BottomNavigationViewMatchers.hasCheckedItem
import com.alexzh.imbarista.matchers.BottomNavigationViewMatchers.hasItemTitle
import com.alexzh.imbarista.matchers.RecyclerViewMatchers
import com.alexzh.imbarista.matchers.TextInputLayoutMatchers
import com.alexzh.imbarista.matchers.ToolbarMatchers
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*

open class BaseTestRobot {

    fun isToolbarHasTitle(viewId: Int, title: String): ViewInteraction =
        onView(withId(viewId))
            .check(matches(ToolbarMatchers.withToolbarTitle(`is`(title))))
}