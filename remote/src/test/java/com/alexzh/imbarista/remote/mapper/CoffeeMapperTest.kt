package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.model.CoffeeEntity
import com.alexzh.imbarista.remote.model.CoffeeModel
import com.alexzh.testdata.remote.GenerateRemoteTestData.generateCoffeeModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CoffeeMapperTest {

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
        coffeeModel: CoffeeModel,
        coffeeEntity: CoffeeEntity
    ) {
        assertEquals(coffeeModel.id, coffeeEntity.id)
        assertEquals(coffeeModel.name, coffeeEntity.name)
        assertEquals(coffeeModel.description, coffeeEntity.description)

        assertEquals(coffeeModel.ingredients.size, coffeeEntity.ingredients.size)
        for (index in 0 until coffeeModel.ingredients.size) {
            assertEquals(coffeeModel.ingredients[index].name, coffeeEntity.ingredients[index].name)
        }
    }
}