package com.alexzh.imbarista.ui.login

import org.junit.Assert.fail
import org.junit.Test

class LoginActivityTest {

    /**
     * - "Login" title should be displayed in Toolbar
     * - "Email Input Field" has "Email" hint and enabled
     * - "Password Input Field" has "Password" hint enabled
     * - "Login" button is enabled
     * - "Create Account" button is enabled
     */
    @Test
    fun shouldBeDisplayed() {
        fail()
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
     * - "Email Input Field" should have a text in email format (*@*.*)
     * - "Password Input Field" should have any text
     * - No errors should be displayed for "Email Input Field"
     * - No errors should be displayed for "Password Input Field"
     */
    @Test
    fun shouldBeNoErrorsWhenNameEmailAndPasswordAreCorrect() {
        fail()
    }

    /**
     * - "Email Input Field" should have a text in email format (*@*.*) for previously registered User
     * - "Password Input Field" should have any text which is not matched with a password for User with email entered in "Email Input Field"
     * - Press "LOGIN" button
     * - Check that message "Check Email and Password" is displayed
     */
    @Test
    fun shouldBeDisplayedCheckEmailAndPasswordErrorWhenDataAreIncorrect() {
        fail()
    }

    /**
     * - "Email Input Field" should have a text in email format (*@*.*) for previously registered User
     * - "Password Input Field" should have any text which is matched with a password for User with email entered in "Email Input Field"
     * - Press "LOGIN" button
     * - Check that Home screen was opened
     */
    @Test
    fun shouldBeOpenedHomeScreenWhenUserLoginSuccessfully() {
        fail()
    }
}