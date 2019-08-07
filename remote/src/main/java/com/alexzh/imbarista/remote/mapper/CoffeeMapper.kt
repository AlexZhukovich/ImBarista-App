package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.imbarista.remote.model.CoffeeDrinkModel

class CoffeeMapper(
    private val ingredientMapper: IngredientsMapper
) : ModelMapper<CoffeeDrinkModel, CoffeeDrinkEntity> {

    override fun mapFromModel(model: CoffeeDrinkModel): CoffeeDrinkEntity {
        val ingredientEntities = model.ingredients.map { ingredientMapper.mapFromModel(it) }

        return CoffeeDrinkEntity(
            model.id,
            model.name,
            model.description,
            ingredientEntities
        )
    }
}