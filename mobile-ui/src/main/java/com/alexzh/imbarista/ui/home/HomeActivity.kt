package com.alexzh.imbarista.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alexzh.imbarista.R
import com.alexzh.imbarista.ui.coffeedrinks.CoffeeDrinksFragment
import com.alexzh.imbarista.ui.map.MapFactory
import com.alexzh.imbarista.ui.profile.ProfileFragment
import com.alexzh.imbarista.ui.settings.SettingsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {

    companion object {
        const val LOCATION_REQUEST_CODE = 100

        fun start(context: Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }

    private val mapFactory by inject<MapFactory>()

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        if (navigation.selectedItemId == item.itemId) {
            return@OnNavigationItemSelectedListener false
        }
        return@OnNavigationItemSelectedListener handleNavigationItemClick(item.itemId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.nav_coffee_drinks)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.settings_action -> {
                SettingsActivity.start(this@HomeActivity)
                return true
            }
        }
        return false
    }

    override fun onResume() {
        super.onResume()

        // TODO: fix it when application will support cache
        refreshExistingFragment()
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onBackPressed() {
        if (navigation.selectedItemId == R.id.navigation_coffee_drinks) {
            finish()
        } else {
            navigation.selectedItemId = R.id.navigation_coffee_drinks
            handleNavigationItemClick(R.id.navigation_coffee_drinks)

        }
    }

    private fun handleNavigationItemClick(itemId: Int): Boolean {
        when (itemId) {
            R.id.navigation_coffee_drinks -> {
                replaceFragment(CoffeeDrinksFragment())
                supportActionBar?.title = getString(R.string.title_coffee_drinks)
            }
            R.id.navigation_near_me -> {
                replaceFragment(mapFactory.getMap())
                supportActionBar?.title = getString(R.string.title_cafes_near_me)
            }
            R.id.navigation_profile -> {
                replaceFragment(ProfileFragment())
                supportActionBar?.title = getString(R.string.title_profile)
            }
        }
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    private fun refreshExistingFragment() {
        when {
            navigation.selectedItemId == R.id.navigation_coffee_drinks -> replaceFragment(CoffeeDrinksFragment())
            navigation.selectedItemId == R.id.navigation_near_me -> replaceFragment(mapFactory.getMap())
            navigation.selectedItemId == R.id.navigation_profile -> replaceFragment(ProfileFragment())
            else -> {
                replaceFragment(CoffeeDrinksFragment())
                navigation.selectedItemId = R.id.navigation_coffee_drinks
            }
        }
    }
}
