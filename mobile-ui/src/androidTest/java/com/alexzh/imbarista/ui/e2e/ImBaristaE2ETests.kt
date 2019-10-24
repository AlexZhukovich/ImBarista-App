package com.alexzh.imbarista.ui.e2e

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.alexzh.imbarista.R
import com.alexzh.imbarista.helpers.actions.ViewActions.clickByChildViewWithId
import com.alexzh.imbarista.helpers.activity.ActivityInstance
import com.alexzh.imbarista.helpers.idlingresource.ViewVisibilityIdlingResource
import com.alexzh.imbarista.helpers.matchers.RecyclerViewMatchers.atPosition
import com.alexzh.imbarista.helpers.matchers.RecyclerViewMatchers.withItemCount
import com.alexzh.imbarista.helpers.snackbar.SnackbarVerification.assertSnackbarText
import com.alexzh.imbarista.ui.coffeedrinks.adapter.CoffeeDrinkViewHolder
import com.alexzh.imbarista.ui.splash.SplashActivity
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import java.util.*

class ImBaristaE2ETests {

    @Rule @JvmField
    val activityRule = ActivityTestRule<SplashActivity>(SplashActivity::class.java)

    /**
     * - Create a new User
     * - Log In
     * - Coffee drinks are loaded and no favourite coffee drinks
     * - Mark Americano as favourite from Coffee Drinks details screen
     * - Mark Espresso as favourite from Coffee Drinks screen
     * - Check that Americano and Espresso marked as favourite ones
     * - Log Out
     */
    @Test
    fun shouldBeChangedCoffeeDrinkStateForTheLoginUserSuccessfully() {
        val username = "test"
        val email = "test-${Date().time}@test.com"
        val password = "test"

        // -- SPLASH SCREEN --

        val toolbarVisibilityIdlingResult = ViewVisibilityIdlingResource(R.id.toolbar, View.VISIBLE)
        IdlingRegistry.getInstance().register(toolbarVisibilityIdlingResult)

        onView(withId(R.id.toolbar))
            .check(matches(isDisplayed()))

        IdlingRegistry.getInstance().unregister(toolbarVisibilityIdlingResult)

        onView(withId(R.id.createAccountButton))
            .perform(click())

        // -- CREATE ACCOUNT --
        onView(withId(R.id.nameEditText))
            .perform(replaceText(username))

        onView(withId(R.id.emailEditText))
            .perform(replaceText(email))

        onView(withId(R.id.passwordEditText))
            .perform(replaceText(password), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.createAccountButton))
            .perform(click())

        assertSnackbarText(ActivityInstance.getInstance().getString(R.string.user_was_created))

        onView(withId(com.google.android.material.R.id.snackbar_action))
            .check(matches(withText(R.string.login_btn_text)))
            .perform(click())

        // -- LOGIN --
        onView(withId(R.id.emailEditText))
            .perform(replaceText(email))

        onView(withId(R.id.passwordEditText))
            .perform(replaceText(password), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.loginButton))
            .perform(click())

        // -- HOME SCREEN --

        val progressBarIR = ViewVisibilityIdlingResource(R.id.progressBar, View.GONE)
        IdlingRegistry.getInstance().register(progressBarIR)

        onView(withId(R.id.recyclerView))
            .check(matches(withItemCount(13)))

        IdlingRegistry.getInstance().unregister(progressBarIR)

        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<CoffeeDrinkViewHolder>(0, click()))

        IdlingRegistry.getInstance().register(progressBarIR)

        onView(withId(R.id.coffeeDrinkName))
            .check(matches(withText("Americano")))

        IdlingRegistry.getInstance().unregister(progressBarIR)

        onView(withId(R.id.coffeeDrinkFavourite))
            .perform(click())

        IdlingRegistry.getInstance().register(progressBarIR)

        onView(withId(R.id.coffeeDrinkFavourite))
            .check(matches(withTagValue(`is`("favourite"))))

        IdlingRegistry.getInstance().unregister(progressBarIR)

        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack()

        IdlingRegistry.getInstance().register(progressBarIR)

        onView(withId(R.id.recyclerView))
            .check(matches(withItemCount(13)))

        IdlingRegistry.getInstance().unregister(progressBarIR)

        onView(withId(R.id.recyclerView))
            .perform(
                actionOnItemAtPosition<CoffeeDrinkViewHolder>(3, clickByChildViewWithId(R.id.favouriteIcon))
            )

        IdlingRegistry.getInstance().register(progressBarIR)

        onView(withId(R.id.recyclerView))
            .check(matches(withItemCount(13)))
            .check(matches(atPosition(0, hasDescendant(withTagValue(`is`("favourite"))))))
            .check(matches(atPosition(1, hasDescendant(withTagValue(`is`("favourite"))))))
            .check(matches(atPosition(2, hasDescendant(withTagValue(not("favourite"))))))

        IdlingRegistry.getInstance().unregister(progressBarIR)

        onView(withId(R.id.navigation_profile))
            .perform(click())

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)

        onView(withText(R.string.logout_action))
            .perform(click())
    }
}