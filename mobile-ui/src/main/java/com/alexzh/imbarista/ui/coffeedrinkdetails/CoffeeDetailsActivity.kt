package com.alexzh.imbarista.ui.coffeedrinkdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexzh.imbarista.R
import com.alexzh.imbarista.model.CoffeeDrinkDetailsItemView
import com.alexzh.imbarista.model.CoffeeDrinkView
import com.alexzh.imbarista.ui.coffeedrinkdetails.adapter.CoffeeDetailsAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_coffee_details.*

class CoffeeDetailsActivity : AppCompatActivity() {

    companion object {
        private const val KEY_COFFEE = "coffee"

        fun start(context: Context, coffeeDrink: CoffeeDrinkView) {
            val intent = Intent(context, CoffeeDetailsActivity::class.java)
            intent.putExtra(KEY_COFFEE, coffeeDrink)
            context.startActivity(intent)
        }
    }

    private val adapter by lazy { CoffeeDetailsAdapter() }

    private lateinit var currentCoffeeDrink: CoffeeDrinkView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coffee_details)

        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        currentCoffeeDrink = intent.getParcelableExtra<CoffeeDrinkView>(KEY_COFFEE)
        coffeeDrinkName.text = currentCoffeeDrink.name
        Glide.with(this)
            .load(currentCoffeeDrink.imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(coffeeDrinkPicture)

        val coffeeDrinkItem = mutableListOf<CoffeeDrinkDetailsItemView>()
        coffeeDrinkItem.add(CoffeeDrinkDetailsItemView(R.drawable.ic_description_black_24dp, currentCoffeeDrink.description))
        coffeeDrinkItem.add(CoffeeDrinkDetailsItemView(R.drawable.ic_book_black_24dp, getStringIngredients(currentCoffeeDrink.ingredients)))

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        adapter.addItems(coffeeDrinkItem)

        closeIcon.setOnClickListener {
            finish()
        }

        updateFavouriteIcon(currentCoffeeDrink.isFavourite)

        coffeeDrinkFavourite.setOnClickListener {
            updateFavouriteIcon(!currentCoffeeDrink.isFavourite)
        }
    }

    private fun updateFavouriteIcon(isFavourite: Boolean) {
        val favouriteIcon = if (isFavourite) {
            R.drawable.ic_favorite_black_24dp
        } else {
            R.drawable.ic_favorite_border_black_24dp
        }
        coffeeDrinkFavourite.setImageResource(favouriteIcon)
        currentCoffeeDrink = currentCoffeeDrink.copy(isFavourite = isFavourite)
    }

    private fun getStringIngredients(ingredients: String): String {
//        var result = String()
//        for (ingredient in ingredients) {
//            result += "${ingredient.name}\n"
//        }
//        return result
        return ingredients.replace(", ", "\n")
    }
}