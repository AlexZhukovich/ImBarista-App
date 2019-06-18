package com.alexzh.imbarista.coffees.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexzh.imbarista.DummyData
import com.alexzh.imbarista.R

class CoffeesAdapter(
    private val itemClick: (DummyData.Coffee) -> Unit
) : RecyclerView.Adapter<CoffeeViewHolder>() {

    private val coffees = mutableListOf<DummyData.Coffee>()

    fun addCoffees(coffees: List<DummyData.Coffee>) {
        this.coffees += coffees
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coffee, parent, false)
        return CoffeeViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        holder.bind(coffees[position])
    }

    override fun getItemCount(): Int = coffees.size
}