package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.model.IngredientEntity
import com.alexzh.imbarista.remote.model.IngredientModel

class IngredientsMapper : ModelMapper<IngredientModel, IngredientEntity> {

    override fun mapFromModel(model: IngredientModel): IngredientEntity {
        return IngredientEntity(model.name)
    }
}