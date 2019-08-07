package com.alexzh.imbarista.ui.coffeedrinkdetails.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alexzh.imbarista.DummyData
import kotlinx.android.synthetic.main.item_coffee_drink_detail_info.view.*

class CoffeeDetailsViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {

    fun bind(coffeeDetailsItem: DummyData.CoffeeDetailsItem) {
        with(coffeeDetailsItem) {
            itemView.categoryImage.setImageResource(categoryIconRes)
            itemView.categoryValue.text = categoryValue
        }
    }
}