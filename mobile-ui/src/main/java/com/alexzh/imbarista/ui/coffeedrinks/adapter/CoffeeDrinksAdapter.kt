package com.alexzh.imbarista.ui.coffeedrinks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexzh.imbarista.DummyData
import com.alexzh.imbarista.R

class CoffeeDrinksAdapter(
    private val itemClick: (DummyData.CoffeeDrink) -> Unit
) : RecyclerView.Adapter<CoffeeDrinkViewHolder>() {

    private val coffeeDrinks = mutableListOf<DummyData.CoffeeDrink>()

    fun addCoffeeDrinks(coffeeDrinks: List<DummyData.CoffeeDrink>) {
        this.coffeeDrinks += coffeeDrinks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeDrinkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coffee_drink, parent, false)
        return CoffeeDrinkViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: CoffeeDrinkViewHolder, position: Int) {
        holder.bind(coffeeDrinks[position])
    }

    override fun getItemCount(): Int = coffeeDrinks.size
}