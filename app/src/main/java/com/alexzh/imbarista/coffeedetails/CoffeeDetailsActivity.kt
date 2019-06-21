package com.alexzh.imbarista.coffeedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexzh.imbarista.DummyData
import com.alexzh.imbarista.R
import com.alexzh.imbarista.coffeedetails.adapter.CoffeeDetailsAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_coffee_details.*

class CoffeeDetailsActivity : AppCompatActivity() {

    companion object {
        private const val KEY_COFFEE = "coffee"

        fun start(context: Context, coffee: DummyData.Coffee) {
            val intent = Intent(context, CoffeeDetailsActivity::class.java)
            intent.putExtra(KEY_COFFEE, coffee)
            context.startActivity(intent)
        }
    }

    private val adapter by lazy { CoffeeDetailsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coffee_details)

        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        val coffee = intent.getParcelableExtra<DummyData.Coffee>(KEY_COFFEE)
        coffeeName.text = coffee.name
        Glide.with(this)
            .load(coffee.photoUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(coffeePicture)

        val coffeeItems = mutableListOf<DummyData.CoffeeDetailsItem>()
        coffeeItems.add(DummyData.CoffeeDetailsItem(R.drawable.ic_description_black_24dp, coffee.description))
        coffeeItems.add(DummyData.CoffeeDetailsItem(R.drawable.ic_book_black_24dp, coffee.ingredients.toString()))

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        adapter.addItems(coffeeItems)

        closeIcon.setOnClickListener {
            finish()
        }

        updateFavouriteIcon(coffee.isFavourite)

        coffeeFavourite.setOnClickListener {
            updateFavouriteIcon(!coffee.isFavourite)
        }
    }

    private fun updateFavouriteIcon(isFavourite: Boolean) {
        val favouriteIcon = if (isFavourite) {
            R.drawable.ic_favorite_black_24dp
        } else {
            R.drawable.ic_favorite_border_black_24dp
        }
        coffeeFavourite.setImageResource(favouriteIcon)
    }
}