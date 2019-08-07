package com.alexzh.data.mapper

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.data.model.IngredientEntity
import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.imbarista.domain.model.Ingredient

class CoffeeMapper(
    private val ingredientMapper: EntityMapper<IngredientEntity, Ingredient>
) : EntityMapper<CoffeeDrinkEntity, CoffeeDrink> {

    override fun mapFromEntity(entity: CoffeeDrinkEntity): CoffeeDrink {
        val ingredients = entity.ingredients.map { ingredientMapper.mapFromEntity(it) }

        return CoffeeDrink(
            entity.id,
            entity.name,
            entity.description,
            ingredients
        )
    }

    override fun mapToEntity(domainModel: CoffeeDrink): CoffeeDrinkEntity {
        val ingredientEntities = domainModel.ingredients.map { ingredientMapper.mapToEntity(it) }

        return CoffeeDrinkEntity(
            domainModel.id,
            domainModel.name,
            domainModel.description,
            ingredientEntities
        )
    }
}