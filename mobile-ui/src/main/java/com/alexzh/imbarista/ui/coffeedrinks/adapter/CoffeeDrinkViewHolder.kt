package com.alexzh.imbarista.ui.coffeedrinks.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alexzh.imbarista.R
import com.alexzh.imbarista.model.CoffeeDrinkView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_coffee_drink.view.*

class CoffeeDrinkViewHolder(
    view: View,
    val itemClick: (CoffeeDrinkView) -> Unit,
    val favouriteItemClick: (CoffeeDrinkView) -> Unit
) : RecyclerView.ViewHolder(view) {

    companion object {
        const val SMALL_COFFEE_DRINK_ICON_BASE_URL = "https://sandbox-api.alexzh.com/coffee-drinks/store/img/196"
    }

    fun bind(coffeeDrink: CoffeeDrinkView) {
        with(coffeeDrink) {
            if (imageUrl.isNotBlank()) {

                Picasso.get()
                    .load("$SMALL_COFFEE_DRINK_ICON_BASE_URL/$imageUrl")
                    .into(itemView.photo)
            }
            itemView.name.text = name
            itemView.ingredients.text = ingredients

            itemView.favouriteIcon.setImageResource(if (isFavourite) R.drawable.ic_favorite_black_24dp else R.drawable.ic_favorite_border_black_24dp)
            itemView.favouriteIcon.tag = if (isFavourite) "favourite" else "not-favourite"

            itemView.favouriteIcon.setOnClickListener { favouriteItemClick(coffeeDrink) }
            itemView.setOnClickListener { itemClick(coffeeDrink) }
        }
    }
}