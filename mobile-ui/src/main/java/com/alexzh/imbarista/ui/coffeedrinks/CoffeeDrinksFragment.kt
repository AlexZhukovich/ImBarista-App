package com.alexzh.imbarista.ui.coffeedrinks

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexzh.imbarista.R
import com.alexzh.imbarista.model.CoffeeDrinkView
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import com.alexzh.imbarista.ui.coffeedrinkdetails.CoffeeDrinkDetailsActivity
import com.alexzh.imbarista.ui.coffeedrinks.adapter.CoffeeDrinksAdapter
import com.alexzh.imbarista.viewmodel.GetCoffeeDrinksViewModel
import kotlinx.android.synthetic.main.fragment_coffee_drinks.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.fragment_coffee_drinks.view.*

class CoffeeDrinksFragment : Fragment() {

    private val coffeeDrinksViewModel: GetCoffeeDrinksViewModel by viewModel()

    private val adapter by lazy { CoffeeDrinksAdapter(coffeeDrinkItemClick) }

    private val coffeeDrinkItemClick: (CoffeeDrinkView) -> Unit = {
        CoffeeDrinkDetailsActivity.start(this.activity!!, it)
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

        return rootView
    }

    override fun onResume() {
        super.onResume()
        coffeeDrinksViewModel.getCoffeeDrinks().observe(this, Observer<Resource<List<CoffeeDrinkView>>> {
            it?.let { handleCoffeeDrinks(it) }
        })
        coffeeDrinksViewModel.fetchCoffeeDrinks()
    }

    private fun handleCoffeeDrinks(resource: Resource<List<CoffeeDrinkView>>) {
        when (resource.status) {
            ResourceState.LOADING -> {
                progressBar.visibility = View.VISIBLE
            }
            ResourceState.SUCCESS -> {
                progressBar.visibility = View.GONE
                resource.data?.let { adapter.addCoffeeDrinks(it) }
            }
            ResourceState.ERROR -> {
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.coffee_drinks_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
