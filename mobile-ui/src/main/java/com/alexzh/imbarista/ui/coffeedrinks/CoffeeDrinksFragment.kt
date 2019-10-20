package com.alexzh.imbarista.ui.coffeedrinks

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexzh.imbarista.R
import com.alexzh.imbarista.exception.AuthViewException
import com.alexzh.imbarista.model.CoffeeDrinkView
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import com.alexzh.imbarista.ui.coffeedrinkdetails.CoffeeDrinkDetailsActivity
import com.alexzh.imbarista.ui.coffeedrinks.adapter.CoffeeDrinksAdapter
import com.alexzh.imbarista.ui.login.LoginActivity
import com.alexzh.imbarista.viewmodel.GetCoffeeDrinksViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_coffee_drinks.*
import kotlinx.android.synthetic.main.fragment_coffee_drinks.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoffeeDrinksFragment : Fragment() {

    private val coffeeDrinksViewModel: GetCoffeeDrinksViewModel by viewModel()

    private val adapter by lazy { CoffeeDrinksAdapter(coffeeDrinkItemClick, coffeeDrinkFavouriteItem) }

    private val coffeeDrinkItemClick: (CoffeeDrinkView) -> Unit = {
        CoffeeDrinkDetailsActivity.start(this.activity!!, it)
    }

    private var snackbar: Snackbar? = null

    private val coffeeDrinkFavouriteItem: (CoffeeDrinkView) -> Unit = {
        if (it.isFavourite) {
            coffeeDrinksViewModel.removeCoffeeDrinkFromFavourites(it.id)
        } else {
            coffeeDrinksViewModel.addCoffeeDrinkToFavourite(it.id)
        }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.coffee_drinks_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
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
                resource.data?.let { adapter.setCoffeeDrinks(it) }
            }
            ResourceState.ERROR -> {
                progressBar.visibility = View.GONE

                if (resource.error is AuthViewException) {
                    showErrorMessage(
                        getString(R.string.error_please_log_again),
                        getString(R.string.log_in_again)
                    ) {
                        LoginActivity.start(this.requireContext())
                        activity?.finish()
                    }
                } else {
                    showErrorMessage(
                        getString(R.string.error_cannot_load_coffee_drinks),
                        getString(R.string.try_again_action)
                    ) { coffeeDrinksViewModel.fetchCoffeeDrinks() }
                }
            }
        }
    }

    @SuppressLint("PrivateResource")
    private fun showErrorMessage(
        messageText: String,
        actionText: String,
        action: () -> Unit
    ) {
        snackbar = Snackbar.make(root, messageText, Snackbar.LENGTH_INDEFINITE)
            .setAction(actionText) { action() }
            .apply {
                val commonMargin = resources.getDimension(R.dimen.common_margin)
                val navigationHeight = resources.getDimension(R.dimen.design_bottom_navigation_height)
                view.layoutParams = (view.layoutParams as FrameLayout.LayoutParams)
                    .apply {
                        setMargins(leftMargin, topMargin, rightMargin, (navigationHeight + commonMargin).toInt())
                    }
            }
        snackbar?.show()
    }

    override fun onPause() {
        if (snackbar != null) {
            snackbar?.dismiss()
        }
        super.onPause()
    }
}
