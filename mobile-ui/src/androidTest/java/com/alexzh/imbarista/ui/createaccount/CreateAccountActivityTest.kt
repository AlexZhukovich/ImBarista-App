package com.alexzh.imbarista.ui.createaccount

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import com.alexzh.imbarista.R
import com.alexzh.imbarista.matchers.TextInputLayoutMatchers
import com.alexzh.imbarista.matchers.ToolbarMatchers
import com.alexzh.imbarista.ui.login.LoginActivity
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test
import java.util.*

class CreateAccountActivityTest {

    @Rule @JvmField
    val activityRule = ActivityTestRule(CreateAccountActivity::class.java)

    /**
     * - "Create account" title should be displayed in Toolbar
     * - "Name Input Field" has "Name" hint and enabled
     * - "Email Input Field" has "Email" hint and enabled
     * - "Password Input Field" has "Password" hint enabled
     * - "Create Account" button is enabled
     */
    @Test
    fun shouldBeDisplayed() {
        val expectedTitle = InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.activity_create_account_title)

        onView(withId(R.id.toolbar))
            .check(matches(ToolbarMatchers.withToolbarTitle(`is`(expectedTitle))))

        onView(withId(R.id.nameEditText))
            .check(matches(isEnabled()))

        onView(withId(R.id.emailEditText))
            .check(matches(isEnabled()))

        onView(withId(R.id.passwordEditText))
            .check(matches(isEnabled()))

        onView(withId(R.id.createAccountButton))
            .check(matches(isEnabled()))
    }

    /**
     * - When "Name Input Field" is in focus Then "Name is blank" error should be displayed for empty text
     * - When "Name Input Field" has any text Then no error for "Name Input Field" is displayed
     */
    @Test
    fun shouldBeHandledNameInputFieldErrors() {
        val nameIsBlankError = activityRule.activity.getString(R.string.error_name_is_blank)

        onView(withId(R.id.nameInputLayout))
            .perform(click())
            .check(matches(TextInputLayoutMatchers.withTextInputLayoutError(`is`(nameIsBlankError))))

        onView(withId(R.id.nameEditText))
            .perform(replaceText("test"))

        onView(withId(R.id.nameInputLayout))
            .check(matches(TextInputLayoutMatchers.hasNoErrorsInTextInputLayout()))
    }

    /**
     * - When "Email Input Field" is in focus Then "Email is blank" error should be displayed for empty text
     * - When "Email Input Field" has not got email text Then "Text is not an email address" error should be displayed
     * - When "Email Input Field" has correct email address Then no error should be displayed
     */
    @Test
    fun shouldBeHandledEmailInputFiendErrors() {
        fail()
    }

    /**
     * - When "Password Input Field" is in focus Then "Password is blank" error should be displayed for empty text
     * - When "Password Input Field" has any text Then no error for "Password Input Field" is displayed
     */
    @Test
    fun shouldBeHandledPasswordInputFieldErrors() {
        fail()
    }

    /**
     * - "Name Input Field" should have any text
     * - "Email Input Field" should have a text in email format (*@*.*)
     * - "Password Input Field" should have any text
     * - No errors should be displayed for "Name Input Field"
     * - No errors should be displayed for "Email Input Field"
     * - No errors should be displayed for "Password Input Field"
     */
    @Test
    fun shouldBeNoErrorsWhenNameEmailAndPasswordAreCorrect() {
        fail()
    }

    /**
     * - "Name Input Field" should have any text
     * - "Email Input Field" should have an email address for the not-registered previously User
     * - "Password Input Field" should have any text
     * - Press "Create Account Button"
     * - Check that message "User was created, please log in" is displayed
     * - Press "LOGIN" button
     * - Check that User moved to "Log In" screen
     */
    @Test
    fun shouldBeOpenLoginScreenWhenUserCreatedSuccessfully() {
        val name = "test"
        val email = "test-${Date().time}@test.com"
        val password = "test"

        Intents.init()

        onView(withId(R.id.nameEditText))
            .perform(replaceText(name))

        onView(withId(R.id.emailEditText))
            .perform(replaceText(email))

        onView(withId(R.id.passwordEditText))
            .perform(replaceText(password))

        onView(withId(R.id.createAccountButton))
            .perform(click())

        assertTrue(verifyMessage(activityRule.activity.getString(R.string.user_was_created)))

        onView(withId(com.google.android.material.R.id.snackbar_action))
            .check(matches(withText(R.string.login_btn_text)))
            .perform(click())

        Intents.intended(IntentMatchers.hasComponent(LoginActivity::class.java.name))
        Intents.release()
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

    /**
     * - "Name Input Field" should have any text
     * - "Email Input Field" should have an email address for the previously registered User
     * - "Password Input Field" should have any text
     * - Press "Create Account Button"
     * - Check that message "User already exist" is displayed
     */
    @Test
    fun shouldBeDisplayedErrorMessageThatUserAlreadyExist() {
        fail()
    }
}