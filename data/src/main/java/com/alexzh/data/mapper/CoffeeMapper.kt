package com.alexzh.data.mapper

import com.alexzh.data.model.CoffeeEntity
import com.alexzh.data.model.IngredientEntity
import com.alexzh.imbarista.domain.model.Coffee
import com.alexzh.imbarista.domain.model.Ingredient

class CoffeeMapper(
    private val ingredientMapper: EntityMapper<IngredientEntity, Ingredient>
) : EntityMapper<CoffeeEntity, Coffee> {

    override fun mapFromEntity(entity: CoffeeEntity): Coffee {
        val ingredients = entity.ingredients.map { ingredientMapper.mapFromEntity(it) }

        return Coffee(
            entity.id,
            entity.name,
            entity.description,
            ingredients
        )
    }

    override fun mapToEntity(domainModel: Coffee): CoffeeEntity {
        val ingredientEntities = domainModel.ingredients.map { ingredientMapper.mapToEntity(it) }

        return CoffeeEntity(
            domainModel.id,
            domainModel.name,
            domainModel.description,
            ingredientEntities
        )
    }
}