package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.imbarista.remote.model.CoffeeDrinkModel
import com.alexzh.testdata.remote.GenerateRemoteTestData.generateCoffeeModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CoffeeDrinkMapperTest {

    private val mapper = CoffeeMapper(IngredientsMapper())

    @Test
    fun mapFromModelWithOneIngredientMapsDataCorrectly() {
        val coffeeModel = generateCoffeeModel()
        val coffeeEntity = mapper.mapFromModel(coffeeModel)

        assertEqualsData(coffeeModel, coffeeEntity)
    }

    @Test
    fun mapFromModelWithMultipleIngredientsMapsDataCorrectly() {
        val coffeeModel = generateCoffeeModel(10)
        val coffeeEntity = mapper.mapFromModel(coffeeModel)

        assertEqualsData(coffeeModel, coffeeEntity)
    }

    @Test
    fun mapFromModelWithNoIngredientsMapsDataCorrectly() {
        val coffeeModel = generateCoffeeModel(0)
        val coffeeEntity = mapper.mapFromModel(coffeeModel)

        assertEqualsData(coffeeModel, coffeeEntity)
    }

    private fun assertEqualsData(
        coffeeDrinkModel: CoffeeDrinkModel,
        coffeeDrinkEntity: CoffeeDrinkEntity
    ) {
        assertEquals(coffeeDrinkModel.id, coffeeDrinkEntity.id)
        assertEquals(coffeeDrinkModel.name, coffeeDrinkEntity.name)
        assertEquals(coffeeDrinkModel.description, coffeeDrinkEntity.description)

        assertEquals(coffeeDrinkModel.ingredients.size, coffeeDrinkEntity.ingredients.size)
        for (index in 0 until coffeeDrinkModel.ingredients.size) {
            assertEquals(coffeeDrinkModel.ingredients[index].name, coffeeDrinkEntity.ingredients[index].name)
        }
    }
}