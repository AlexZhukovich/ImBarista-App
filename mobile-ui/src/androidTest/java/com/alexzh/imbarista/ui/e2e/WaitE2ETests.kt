package com.alexzh.imbarista.ui.e2e

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingPolicies
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.alexzh.imbarista.R
import com.alexzh.imbarista.idlingresources.ViewVisibilityIdlingResource
import com.alexzh.imbarista.matchers.RecyclerViewMatchers
import com.alexzh.imbarista.ui.login.LoginActivity
import com.alexzh.imbarista.ui.splash.SplashActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*
import java.util.concurrent.TimeUnit

class WaitE2ETests {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<SplashActivity>(SplashActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    /**
     * Log in with correct email and correct password
     * Coffee Drink Screen has 13 items
     * Move to "Profile" Menu Item
     * Open additional menu
     * Press Logout
     * Login screen is opened
     */
    @Test
    fun shouldLoginAndCheckCoffeeDrinksCount() {

    }

    /**
     * Log in with correct email and correct password
     * Move to "Profile" Menu Item
     * Profile screen has proper user name
     * Profile screen has proper email address
     * Open additional menu
     * Press Logout
     * Login screen is opened
     */
    @Test
    fun shouldLoginAndCheckProfileInfo() {

    }

    /**
     * Log In with correct email and incorrect password
     * "Check email and password" message is visible
     */
    @Test
    fun shouldEnterIncorrectLoginAndPasswordAndCheckError() {

    }

    @After
    fun tearDown() {
        Intents.release()
    }
}