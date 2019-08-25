package com.alexzh.imbarista.ui.coffeedrinkdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexzh.imbarista.R
import com.alexzh.imbarista.model.CoffeeDrinkDetailsItemView
import com.alexzh.imbarista.model.CoffeeDrinkView
import com.alexzh.imbarista.model.SessionView
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import com.alexzh.imbarista.ui.coffeedrinkdetails.adapter.CoffeeDetailsAdapter
import com.alexzh.imbarista.ui.login.LoginActivity
import com.alexzh.imbarista.viewmodel.CheckExistingSessionViewModel
import com.alexzh.imbarista.viewmodel.CoffeeDrinkDetailsViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coffee_drink_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoffeeDrinkDetailsActivity : AppCompatActivity() {

    companion object {
        private const val KEY_COFFEE = "coffee"
        private const val LARGE_COFFEE_DRINK_ICON_BASE_URL = "https://api.coffee-drinks.alexzh.com/store/img/512"

        fun start(context: Context, coffeeDrink: CoffeeDrinkView) {
            val intent = Intent(context, CoffeeDrinkDetailsActivity::class.java)
            intent.putExtra(KEY_COFFEE, coffeeDrink)
            context.startActivity(intent)
        }
    }

    private val adapter by lazy { CoffeeDetailsAdapter() }

    private val checkExistingSessionViewModel: CheckExistingSessionViewModel by viewModel()
    private val coffeeDrinkDetailsViewModel: CoffeeDrinkDetailsViewModel by viewModel()

    private lateinit var currentCoffeeDrink: CoffeeDrinkView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coffee_drink_details)

        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        coffeeDrinkFavourite.setOnClickListener {
            coffeeDrinkDetailsViewModel.updateDrinkCoffeeData(!currentCoffeeDrink.isFavourite)
        }
    }

    override fun onStart() {
        super.onStart()

        checkExistingSessionViewModel.getExistingSessionInfo().observe(this, Observer<Resource<SessionView>> {
            if (it.error != null) {
                LoginActivity.start(this)
                finish()
            }
        })
        checkExistingSessionViewModel.checkExistingSession()

        coffeeDrinkDetailsViewModel.getCoffeeDrinkInfo().observe(this, Observer<Resource<CoffeeDrinkView>> {
            it?.let { handleCoffeeDrinkChanges(it) }
        })
        coffeeDrinkDetailsViewModel.fetchCoffeeDrink(intent.getParcelableExtra<CoffeeDrinkView>(KEY_COFFEE).id)
    }

    private fun handleCoffeeDrinkChanges(resource: Resource<CoffeeDrinkView>) {
        when (resource.status) {
            ResourceState.LOADING -> {
                progressBar.visibility = View.VISIBLE
            }
            ResourceState.SUCCESS -> {
                progressBar.visibility = View.GONE
                currentCoffeeDrink = resource.data as CoffeeDrinkView
                coffeeDrinkName.text = resource.data.name

                Picasso.get()
                    .load("$LARGE_COFFEE_DRINK_ICON_BASE_URL/${resource.data.imageUrl}")
                    .into(coffeeDrinkPicture)

                val coffeeDrinkItem = mutableListOf<CoffeeDrinkDetailsItemView>()
                coffeeDrinkItem.add(CoffeeDrinkDetailsItemView(R.drawable.ic_description_black_24dp,
                    resource.data.description
                ))
                coffeeDrinkItem.add(CoffeeDrinkDetailsItemView(R.drawable.ic_book_black_24dp, getStringIngredients(resource.data.ingredients)))

                val layoutManager = LinearLayoutManager(this)
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = adapter

                adapter.setItems(coffeeDrinkItem)

                closeIcon.setOnClickListener {
                    finish()
                }

                updateFavouriteIcon(resource.data.isFavourite)
            }
            ResourceState.ERROR -> {
                progressBar.visibility = View.GONE
                Snackbar.make(root, R.string.error_coffee_drink_favourite_state_cannot_be_changed, Snackbar.LENGTH_LONG)
                    .setAction(R.string.try_again_action) {
                        coffeeDrinkDetailsViewModel.fetchCoffeeDrink(intent.getParcelableExtra<CoffeeDrinkView>(KEY_COFFEE).id)
                    }
                    .show()
            }
        }
    }

    private fun updateFavouriteIcon(isFavourite: Boolean) {
        val favouriteIcon = if (isFavourite) {
            R.drawable.ic_favorite_black_24dp
        } else {
            R.drawable.ic_favorite_border_black_24dp
        }
        coffeeDrinkFavourite.setImageResource(favouriteIcon)
        coffeeDrinkFavourite.tag = if (isFavourite) "favourite" else "not-favourite"
    }

    private fun getStringIngredients(ingredients: String): String {
        return ingredients.replace(", ", "\n")
    }
}