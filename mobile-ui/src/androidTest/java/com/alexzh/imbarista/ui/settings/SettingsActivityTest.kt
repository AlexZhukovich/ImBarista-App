package com.alexzh.imbarista.ui.settings

import android.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.alexzh.imbarista.R
import com.alexzh.imbarista.matchers.RecyclerViewMatchers
import org.junit.After
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test

class SettingsActivityTest {

    @Rule @JvmField
    val activityTestRule = ActivityTestRule(SettingsActivity::class.java)

    /**
     * - Settings has "Map" category
     * - Settings has "Cafe Search" category
     * - Settings has "Map Provider" item with "TomTom" default value
     * - Settings has "Search radius" item with "1000 meters" default value
     * - Settings has "Number of cafes on map" item with "10 cafes" default value
     */
    @Test
    fun shouldBeDisplayedSettingsItems() {
        onView(withId(R.id.recycler_view))
            .check(matches(RecyclerViewMatchers.atPosition(0, hasDescendant(withText(R.string.pref_map_header)))))

        onView(withId(R.id.recycler_view))
            .check(matches(RecyclerViewMatchers.atPosition(1, hasDescendant(withText(R.string.pref_map_provider_label)))))
            .check(matches(RecyclerViewMatchers.atPosition(1, hasDescendant(withText(R.string.pref_map_provider_tomtom_entity_option)))))

        onView(withId(R.id.recycler_view))
            .check(matches(RecyclerViewMatchers.atPosition(2, hasDescendant(withText(R.string.pref_cafe_search_category_title)))))

        onView(withId(R.id.recycler_view))
            .check(matches(RecyclerViewMatchers.atPosition(3, hasDescendant(withText(R.string.pref_cafe_search_radius_title)))))
            .check(matches(RecyclerViewMatchers.atPosition(3, hasDescendant(withText(R.string.pref_cafe_search_radius_1000_m_entity)))))

        onView(withId(R.id.recycler_view))
            .check(matches(RecyclerViewMatchers.atPosition(4, hasDescendant(withText(R.string.pref_cafe_search_cafe_limits_on_map_title)))))
            .check(matches(RecyclerViewMatchers.atPosition(4, hasDescendant(withText(R.string.pref_cafe_search_cafe_limit_ten_entity)))))

        onView(withId(R.id.recycler_view))
            .check(matches(RecyclerViewMatchers.withItemCount(5)))
    }

    /**
     * - Press "Map Provider" item
     * - Press "Google" item
     * - Check that "Map Provider" item has "Google" value
     */
    @Test
    fun shouldMapProviderDisplayDefaultAndNonDefaultValues() {
        onView(withId(R.id.recycler_view))
            .check(matches(RecyclerViewMatchers.atPosition(1, hasDescendant(withText(R.string.pref_map_provider_label)))))
            .check(matches(RecyclerViewMatchers.atPosition(1, hasDescendant(withText(R.string.pref_map_provider_tomtom_entity_option)))))

        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.actionOnItem<ViewHolder>(hasDescendant(withText(R.string.pref_map_provider_label)), click()))

        onView(withText(R.string.pref_map_provider_google_entity_option))
            .perform(click())

        onView(withId(R.id.recycler_view))
            .check(matches(RecyclerViewMatchers.atPosition(1, hasDescendant(withText(R.string.pref_map_provider_label)))))
            .check(matches(RecyclerViewMatchers.atPosition(1, hasDescendant(withText(R.string.pref_map_provider_google_entity_option)))))
    }

    /**
     * - Press "Search Radius" item
     * - Press "500 meters" item
     * - Check that "Search Radius" item has "500 meters" value
     */
    @Test
    fun shouldSearchRadiusDisplayDefaultAndNonDefaultValues() {
        fail()
    }

    /**
     * - Press "Number of cafes on map" item
     * - Press "5 cafes" item
     * - Check that "Number of cafes on map" item has "5 cafes" value
     */
    @Test
    fun shouldNumberOfCafesOnMapDisplayDefaultAndNonDefaultValues() {
        fail()
    }

    @After
    fun tearDown() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().clear().commit()
    }
}