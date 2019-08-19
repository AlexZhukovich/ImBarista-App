package com.alexzh.imbarista.ui.coffeedrinks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexzh.imbarista.R
import com.alexzh.imbarista.model.CoffeeDrinkView

class CoffeeDrinksAdapter(
    private val itemClick: (CoffeeDrinkView) -> Unit,
    private val favouriteItemClick: (CoffeeDrinkView) -> Unit
) : RecyclerView.Adapter<CoffeeDrinkViewHolder>() {

    private val coffeeDrinks = mutableListOf<CoffeeDrinkView>()

    fun setCoffeeDrinks(coffeeDrinks: List<CoffeeDrinkView>) {
        this.coffeeDrinks.clear()
        this.coffeeDrinks += coffeeDrinks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeDrinkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coffee_drink, parent, false)
        return CoffeeDrinkViewHolder(view, itemClick, favouriteItemClick)
    }

    override fun onBindViewHolder(holder: CoffeeDrinkViewHolder, position: Int) {
        holder.bind(coffeeDrinks[position])
    }

    override fun getItemCount(): Int = coffeeDrinks.size
}