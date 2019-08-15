package com.alexzh.imbarista.mapper

import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.imbarista.model.CoffeeDrinkView

class CoffeeDrinkViewMapper : Mapper<CoffeeDrinkView, CoffeeDrink> {
    override fun mapToView(type: CoffeeDrink): CoffeeDrinkView {
        return CoffeeDrinkView(
            type.id,
            type.name,
            type.imageUrl,
            type.description,
            type.ingredients,
            type.isFavourite
        )
    }
}