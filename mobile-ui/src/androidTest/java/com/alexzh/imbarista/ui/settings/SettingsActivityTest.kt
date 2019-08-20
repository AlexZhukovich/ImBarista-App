package com.alexzh.imbarista.ui.settings

import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

class SettingsActivityTest {

    @Rule @JvmField
    val activity = ActivityTestRule(SettingsActivity::class.java)

    /**
     * - Settings has "Map" category
     * - Settings has "Cafe Search" category
     * - Settings has "Map Provider" item with "TomTom" default value
     * - Settings has "Search radius" item with "1000 meters" default value
     * - Settings has "Number of cafes on map" item with "10 cafes" default value
     */
    @Test
    fun shouldBeDisplayedSettingsItems() {

    }

    /**
     * - Press "Map Provider" item
     * - Press "Google" item
     * - Check that "Map Provider" item has "Google" value
     */
    @Test
    fun shouldMapProviderDisplayDefaultAndNonDefaultValues() {

    }

    /**
     * - Press "Search Radius" item
     * - Press "500 meters" item
     * - Check that "Search Radius" item has "500 meters" value
     */
    @Test
    fun shouldSearchRadiusDisplayDefaultAndNonDefaultValues() {

    }

    /**
     * - Press "Number of cafes on map" item
     * - Press "5 cafes" item
     * - Check that "Number of cafes on map" item has "5 cafes" value
     */
    @Test
    fun shouldNumberOfCafesOnMapDisplayDefaultAndNonDefaultValues() {

    }
}