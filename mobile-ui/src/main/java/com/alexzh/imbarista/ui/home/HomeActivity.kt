package com.alexzh.imbarista.ui.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.alexzh.imbarista.R
import com.alexzh.imbarista.ui.coffeedrinks.CoffeeDrinksFragment
import com.alexzh.imbarista.ui.map.TomTomMapFragment
import com.alexzh.imbarista.ui.profile.ProfileFragment
import com.alexzh.imbarista.ui.settings.SettingsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }

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

        replaceFragment(CoffeeDrinksFragment())

        navigation.selectedItemId = R.id.navigation_coffee_drinks
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun handleNavigationItemClick(itemId: Int): Boolean {
        when (itemId) {
            R.id.navigation_coffee_drinks -> {
                replaceFragment(CoffeeDrinksFragment())
                supportActionBar?.title = getString(R.string.nav_coffee_drinks)
            }
            R.id.navigation_near_me -> {
                replaceFragment(TomTomMapFragment())
                supportActionBar?.title = getString(R.string.nav_near_me)
            }
            R.id.navigation_profile -> {
                replaceFragment(ProfileFragment())
                supportActionBar?.title = getString(R.string.nav_profile)
            }
        }
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
