package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.imbarista.remote.model.CoffeeDrinkModel

class CoffeeMapper : ModelMapper<CoffeeDrinkModel, CoffeeDrinkEntity> {

    override fun mapFromModel(model: CoffeeDrinkModel): CoffeeDrinkEntity {

        return CoffeeDrinkEntity(
            model.id,
            model.name,
            model.imageUrl,
            model.description,
            model.ingredients,
            model.isCoffeeDrinkFavourite()
        )
    }
}