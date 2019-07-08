package com.alexzh.data.mapper

import com.alexzh.data.model.IngredientEntity
import com.alexzh.imbarista.domain.model.Ingredient

class IngredientMapper : EntityMapper<IngredientEntity, Ingredient> {

    override fun mapFromEntity(entity: IngredientEntity): Ingredient {
        return Ingredient(entity.name)
    }

    override fun mapToEntity(domainModel: Ingredient): IngredientEntity {
        return IngredientEntity(domainModel.name)
    }
}