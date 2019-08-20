package com.alexzh.imbarista.ui.e2e

import org.junit.Assert.fail
import org.junit.Test

class ImBaristaE2ETests {

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
        fail()
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
}