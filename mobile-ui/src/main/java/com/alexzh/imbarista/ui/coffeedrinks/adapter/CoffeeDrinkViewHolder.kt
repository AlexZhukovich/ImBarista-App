package com.alexzh.imbarista.ui.coffeedrinks.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alexzh.imbarista.R
import com.alexzh.imbarista.model.CoffeeDrinkView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_coffee_drink.view.*

class CoffeeDrinkViewHolder(
    private val view: View,
    val itemClick: (CoffeeDrinkView) -> Unit
) : RecyclerView.ViewHolder(view) {

    companion object {
        const val SMALL_COFFEE_DRINK_ICON_BASE_URL = "https://api.coffee-drinks.alexzh.com/store/img/196"
    }

    fun bind(coffeeDrink: CoffeeDrinkView) {
        val imageSize = itemView.photo.height
        with(coffeeDrink) {
            if (imageUrl.isNotBlank()) {
                Glide.with(view.context)
                    .load("$SMALL_COFFEE_DRINK_ICON_BASE_URL/$imageUrl")
                    .apply(RequestOptions.circleCropTransform())
                    .override(imageSize, imageSize)
                    .into(itemView.photo)
            }
            itemView.name.text = name
            itemView.ingredients.text = ingredients

            Glide.with(view.context)
                .load(if (isFavourite) R.drawable.ic_favorite_black_24dp else R.drawable.ic_favorite_border_black_24dp)
                .into(itemView.favouriteIcon)

            itemView.setOnClickListener { itemClick(coffeeDrink) }
        }
    }
}