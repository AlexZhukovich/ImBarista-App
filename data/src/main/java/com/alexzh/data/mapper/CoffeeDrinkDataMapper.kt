package com.alexzh.data.mapper

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.imbarista.domain.model.CoffeeDrink

class CoffeeDrinkDataMapper : EntityMapper<CoffeeDrinkEntity, CoffeeDrink> {

    override fun mapFromEntity(entity: CoffeeDrinkEntity): CoffeeDrink {
        return CoffeeDrink(
            entity.id,
            entity.name,
            entity.imageUrl,
            entity.description,
            entity.ingredients,
            entity.isFavourite
        )
    }

    override fun mapToEntity(domainModel: CoffeeDrink): CoffeeDrinkEntity {
        return CoffeeDrinkEntity(
            domainModel.id,
            domainModel.name,
            domainModel.imageUrl,
            domainModel.description,
            domainModel.ingredients,
            domainModel.isFavourite
        )
    }
}