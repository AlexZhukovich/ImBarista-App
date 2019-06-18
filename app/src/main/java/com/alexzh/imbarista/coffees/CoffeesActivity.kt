package com.alexzh.imbarista.coffees

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexzh.imbarista.DummyData
import com.alexzh.imbarista.R
import com.alexzh.imbarista.coffees.adapter.CoffeesAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_coffees.*

class CoffeesActivity : AppCompatActivity() {

    private val adapter by lazy { CoffeesAdapter(coffeeItemClick) }

    private val coffeeItemClick: (DummyData.Coffee) -> Unit = {
        Toast.makeText(this@CoffeesActivity, it.name, Toast.LENGTH_SHORT).show()
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        if (navigation.selectedItemId == item.itemId) {
            return@OnNavigationItemSelectedListener false
        }
        return@OnNavigationItemSelectedListener handleNavigationItemClick(item.itemId)
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CoffeesActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coffees)

        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, layoutManager.orientation))

        adapter.addCoffees(DummyData.getCoffees())
    }

    override fun onResume() {
        super.onResume()

        navigation.selectedItemId = R.id.navigation_coffees
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun handleNavigationItemClick(itemId: Int): Boolean {
        val menuTitle = when (itemId) {
            R.id.navigation_coffees -> "Coffees"
            R.id.navigation_near_me -> "Near Me"
            else -> "Profile"
        }
        Toast.makeText(this@CoffeesActivity, menuTitle, Toast.LENGTH_SHORT).show()
        return true
    }
}
