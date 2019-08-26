package com.alexzh.imbarista.ui.e2e

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.alexzh.imbarista.R
import com.alexzh.imbarista.idlingresources.ViewVisibilityIdlingResource
import com.alexzh.imbarista.matchers.RecyclerViewMatchers
import com.alexzh.imbarista.ui.coffeedrinks.adapter.CoffeeDrinkViewHolder
import com.alexzh.imbarista.ui.splash.SplashActivity
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Assert
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test
import java.util.*

class ImBaristaE2ETests {

    @Rule @JvmField
    val activityTestRule = ActivityTestRule(SplashActivity::class.java)

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
        val name = "test"
        val email = "test-${Date().time}@test.com"
        val password = "test"

        val expectedUserWasCreatedMsg = InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.user_was_created)

        // SPLASH
        val toolbarVisibilityIR = ViewVisibilityIdlingResource(R.id.toolbar, View.VISIBLE)
        IdlingRegistry.getInstance().register(toolbarVisibilityIR)

        onView(withId(R.id.toolbar))
            .check(matches(isDisplayed()))

        IdlingRegistry.getInstance().unregister(toolbarVisibilityIR)
        // LOGIN

        onView(withId(R.id.createAccountButton))
            .perform(click())

        // CREATE ACCOUNT

        onView(withId(R.id.nameEditText))
            .perform(ViewActions.replaceText(name))

        onView(withId(R.id.emailEditText))
            .perform(ViewActions.replaceText(email))

        onView(withId(R.id.passwordEditText))
            .perform(ViewActions.replaceText(password))

        onView(withId(R.id.createAccountButton))
            .perform(click())

        Assert.assertTrue(verifyMessage(expectedUserWasCreatedMsg))

        onView(withId(com.google.android.material.R.id.snackbar_action))
            .check(matches(withText(R.string.login_btn_text)))
            .perform(click())

        // LOGIN

        onView(withId(R.id.emailEditText))
            .perform(ViewActions.replaceText(email))

        onView(withId(R.id.passwordEditText))
            .perform(ViewActions.replaceText(password))

        onView(withId(R.id.loginButton))
            .perform(click())

        // HOME

        val progressBarInvisibilityIR = ViewVisibilityIdlingResource(R.id.progressBar, View.GONE)
        IdlingRegistry.getInstance().register(progressBarInvisibilityIR)

        onView(withId(R.id.recyclerView))
            .check(matches(RecyclerViewMatchers.withItemCount(13)))

        IdlingRegistry.getInstance().unregister(progressBarInvisibilityIR)

        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<CoffeeDrinkViewHolder>(0, click()))

        // DETAIL

        IdlingRegistry.getInstance().register(progressBarInvisibilityIR)

        onView(withText("Americano"))
            .check(matches(isDisplayed()))

        IdlingRegistry.getInstance().unregister(progressBarInvisibilityIR)

        onView(withId(R.id.coffeeDrinkFavourite))
            .perform(click())

        IdlingRegistry.getInstance().register(progressBarInvisibilityIR)

        onView(withId(R.id.coffeeDrinkFavourite))
            .check(matches(withTagValue(`is`("favourite"))))

        IdlingRegistry.getInstance().unregister(progressBarInvisibilityIR)

        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack()

        // HOME

        IdlingRegistry.getInstance().register(progressBarInvisibilityIR)

        onView(withId(R.id.recyclerView))
            .check(matches(RecyclerViewMatchers.withItemCount(13)))

        IdlingRegistry.getInstance().unregister(progressBarInvisibilityIR)

        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<CoffeeDrinkViewHolder>(3, com.alexzh.imbarista.actions.RecyclerViewActions.clickByChildViewWithId(R.id.favouriteIcon))
            )

        IdlingRegistry.getInstance().register(progressBarInvisibilityIR)

        onView(withId(R.id.recyclerView))
            .check(matches(RecyclerViewMatchers.atPosition(0, hasDescendant(withTagValue(`is`("favourite"))))))
            .check(matches(RecyclerViewMatchers.atPosition(1, hasDescendant(withTagValue(`is`("favourite"))))))
            .check(matches(RecyclerViewMatchers.atPosition(2, hasDescendant(withTagValue(not("favourite"))))))

        IdlingRegistry.getInstance().unregister(progressBarInvisibilityIR)

        // PROFILE

        onView(withId(R.id.navigation_profile))
            .perform(click())

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)

        onView(withText(R.string.logout_action))
            .perform(click())
    }

    /**
     * - Create a new User
     * - Log In
     * - Check that User on Home screen
     * - When Home screen is opened Coffee Drinks tab should be active
     * - Press "Near Me" tab
     * - Rotate device
     * - Check that "Near Me" tab is selected
     * - Press "Profile" tab
     * - Rotate device
     * - Check that "Profile" tab is selected
     * - Press "Coffee Drinks" tab
     * - Rotate device
     * - Check that "Profile" tab is selected
     */
    @Test
    fun shouldBeRestoredCorrectTabAfterRotation() {
        fail()
    }

    private fun verifyMessage(text: String): Boolean {
        val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        repeat(10) {
            val msg = uiDevice.findObject(By.text(text))
            if (msg != null) {
                return true
            }
            uiDevice.waitForIdle(500)
        }
        return false
    }

}