package com.alexzh.imbarista.coffees.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alexzh.imbarista.DummyData
import com.alexzh.imbarista.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_coffee.view.*

class CoffeeViewHolder(
    private val view: View,
    val itemClick: (DummyData.Coffee) -> Unit
) : RecyclerView.ViewHolder(view) {

    fun bind(coffee: DummyData.Coffee) {
        with(coffee) {
            Glide.with(view.context)
                .load(photoUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(itemView.photo)
            itemView.name.text = name
            itemView.ingredients.text = getStringIngredients(ingredients).substring(0, getStringIngredients(ingredients).length - 2)

            Glide.with(view.context)
                .load(if (isFavourite) R.drawable.ic_favorite_black_24dp else R.drawable.ic_favorite_border_black_24dp)
                .into(itemView.favouriteIcon)

            itemView.setOnClickListener { itemClick(coffee) }
        }
    }

    private fun getStringIngredients(ingredients: List<DummyData.Ingredient>): String {
        var result = String()
        for (ingredient in ingredients) {
            result += "${ingredient.name}, "
        }
        return result
    }
}