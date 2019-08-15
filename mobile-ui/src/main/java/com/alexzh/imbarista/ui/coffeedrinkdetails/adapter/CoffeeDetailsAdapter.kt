package com.alexzh.imbarista.ui.coffeedrinkdetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexzh.imbarista.R
import com.alexzh.imbarista.model.CoffeeDrinkDetailsItemView

class CoffeeDetailsAdapter : RecyclerView.Adapter<CoffeeDetailsViewHolder>() {

    private val items = mutableListOf<CoffeeDrinkDetailsItemView>()

    fun setItems(items: List<CoffeeDrinkDetailsItemView>) {
        this.items.clear()
        this.items += items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeDetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coffee_drink_detail_info, parent, false)
        return CoffeeDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoffeeDetailsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}