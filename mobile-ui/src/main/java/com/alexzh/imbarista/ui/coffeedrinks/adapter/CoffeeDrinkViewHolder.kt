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

    fun bind(coffeeDrink: CoffeeDrinkView) {
        with(coffeeDrink) {
            if (imageUrl.isNotBlank()) {
                Glide.with(view.context)
                    .load(imageUrl)
                    .apply(RequestOptions.circleCropTransform())
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