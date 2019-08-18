package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.imbarista.remote.model.CoffeeDrinkModel
import com.alexzh.imbarista.commonandroidtestdata.remote.GenerateRemoteTestData.generateCoffeeDrinkModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CoffeeDrinkMapperTest {

    private val mapper = CoffeeMapper()

    @Test
    fun mapFromModelWithOneIngredientMapsDataCorrectly() {
        val coffeeModel = generateCoffeeDrinkModel()
        val coffeeEntity = mapper.mapFromModel(coffeeModel)

        assertEqualsData(coffeeModel, coffeeEntity)
    }

    @Test
    fun mapFromModelWithMultipleIngredientsMapsDataCorrectly() {
        val coffeeModel = generateCoffeeDrinkModel(10)
        val coffeeEntity = mapper.mapFromModel(coffeeModel)

        assertEqualsData(coffeeModel, coffeeEntity)
    }

    @Test
    fun mapFromModelWithNoIngredientsMapsDataCorrectly() {
        val coffeeModel = generateCoffeeDrinkModel(0)
        val coffeeEntity = mapper.mapFromModel(coffeeModel)

        assertEqualsData(coffeeModel, coffeeEntity)
    }

    private fun assertEqualsData(
        coffeeDrinkModel: CoffeeDrinkModel,
        coffeeDrinkEntity: CoffeeDrinkEntity
    ) {
        assertEquals(coffeeDrinkModel.id, coffeeDrinkEntity.id)
        assertEquals(coffeeDrinkModel.name, coffeeDrinkEntity.name)
        assertEquals(coffeeDrinkModel.imageUrl, coffeeDrinkEntity.imageUrl)
        assertEquals(coffeeDrinkModel.description, coffeeDrinkEntity.description)
        assertEquals(coffeeDrinkModel.ingredients, coffeeDrinkEntity.ingredients)
        assertEquals(coffeeDrinkModel.isCoffeeDrinkFavourite(), coffeeDrinkEntity.isFavourite)
    }
}