package com.alexzh.imbarista.coffeedrinks

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexzh.imbarista.DummyData
import com.alexzh.imbarista.R
import com.alexzh.imbarista.coffeedrinkdetails.CoffeeDetailsActivity
import com.alexzh.imbarista.coffeedrinks.adapter.CoffeeDrinksAdapter
import kotlinx.android.synthetic.main.fragment_coffee_drinks.view.*

class CoffeeDrinksFragment : Fragment() {

    private val adapter by lazy { CoffeeDrinksAdapter(coffeeDrinkItemClick) }

    private val coffeeDrinkItemClick: (DummyData.CoffeeDrink) -> Unit = {
        CoffeeDetailsActivity.start(this.activity!!, it)
    }

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_coffee_drinks, container, false)

        val layoutManager = LinearLayoutManager(this.context)
        rootView.recyclerView.layoutManager = layoutManager
        rootView.recyclerView.adapter = adapter
        rootView.recyclerView.addItemDecoration(DividerItemDecoration(rootView.recyclerView.context, layoutManager.orientation))

        adapter.addCoffeeDrinks(DummyData.getCoffeeDrinks())

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.coffee_drinks_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
