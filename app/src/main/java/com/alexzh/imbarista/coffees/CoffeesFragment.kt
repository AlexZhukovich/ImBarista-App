package com.alexzh.imbarista.coffees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexzh.imbarista.DummyData
import com.alexzh.imbarista.R
import com.alexzh.imbarista.coffeedetails.CoffeeDetailsActivity
import com.alexzh.imbarista.coffees.adapter.CoffeesAdapter
import kotlinx.android.synthetic.main.fragment_coffees.view.*

class CoffeesFragment : Fragment() {

    private val adapter by lazy { CoffeesAdapter(coffeeItemClick) }

    private val coffeeItemClick: (DummyData.Coffee) -> Unit = {
        CoffeeDetailsActivity.start(this.activity!!, it)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_coffees, container, false)


        val layoutManager = LinearLayoutManager(this.context)
        rootView.recyclerView.layoutManager = layoutManager
        rootView.recyclerView.adapter = adapter
        rootView.recyclerView.addItemDecoration(DividerItemDecoration(rootView.recyclerView.context, layoutManager.orientation))

        adapter.addCoffees(DummyData.getCoffees())

        return rootView
    }
}
