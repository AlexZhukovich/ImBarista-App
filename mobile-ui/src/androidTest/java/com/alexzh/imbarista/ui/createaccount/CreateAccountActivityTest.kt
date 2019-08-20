package com.alexzh.imbarista.ui.createaccount

import org.junit.Test

class CreateAccountActivityTest {

    /**
     * - "Create account" title should be displayed in Toolbar
     * - "Name Input Field" has "Name" hint and enabled
     * - "Email Input Field" has "Email" hint and enabled
     * - "Password Input Field" has "Password" hint enabled
     * - "Create Account" button is enabled
     */
    @Test
    fun shouldBeDisplayed() {

    }

    /**
     * - When "Name Input Field" is in focus Then "Name is blank" error should be displayed for empty text
     * - When "Name Input Field" has any text Then no error for "Name Input Field" is displayed
     */
    @Test
    fun shouldBeHandledNameInputFieldErrors() {

    }

    /**
     * - When "Email Input Field" is in focus Then "Email is blank" error should be displayed for empty text
     * - When "Email Input Field" has not got email text Then "Text is not an email address" error should be displayed
     * - When "Email Input Field" has correct email address Then no error should be displayed
     */
    @Test
    fun shouldBeHandledEmailInputFiendErrors() {

    }

    /**
     * - When "Password Input Field" is in focus Then "Password is blank" error should be displayed for empty text
     * - When "Password Input Field" has any text Then no error for "Password Input Field" is displayed
     */
    @Test
    fun shouldBeHandledPasswordInputFieldErrors() {

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

    }
}