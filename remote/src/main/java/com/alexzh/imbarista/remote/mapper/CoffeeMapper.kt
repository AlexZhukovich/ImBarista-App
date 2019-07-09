package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.model.CoffeeEntity
import com.alexzh.imbarista.remote.model.CoffeeModel

class CoffeeMapper(
    private val ingredientMapper: IngredientsMapper
) : ModelMapper<CoffeeModel, CoffeeEntity> {

    override fun mapFromModel(model: CoffeeModel): CoffeeEntity {
        val ingredientEntities = model.ingredients.map { ingredientMapper.mapFromModel(it) }

        return CoffeeEntity(
            model.id,
            model.name,
            model.description,
            ingredientEntities
        )
    }
}