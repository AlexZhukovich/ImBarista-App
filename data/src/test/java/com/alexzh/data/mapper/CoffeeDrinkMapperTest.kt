package com.alexzh.data.mapper

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.testdata.data.GenerateDataTestData.generateCoffeeEntity
import com.alexzh.testdata.domain.GenerateDomainTestData.generateCoffee
import org.junit.Assert.assertEquals
import org.junit.Test

class CoffeeDrinkMapperTest {

    private val mapper = CoffeeMapper(IngredientMapper())

    @Test
    fun mapFromEntityWithOneIngredientMapsDataCorrectly() {
        val coffeeEntity = generateCoffeeEntity()
        val coffee = mapper.mapFromEntity(coffeeEntity)

        assertEqualsData(coffee, coffeeEntity)
    }

    @Test
    fun mapFromEntityWithMultipleIngredientMapsDataCorrectly() {
        val coffeeEntity = generateCoffeeEntity(10)
        val coffee = mapper.mapFromEntity(coffeeEntity)

        assertEqualsData(coffee, coffeeEntity)
    }

    @Test
    fun mapFromEntityWithNoIngredientMapsDataCorrectly() {
        val coffeeEntity = generateCoffeeEntity(0)
        val coffee = mapper.mapFromEntity(coffeeEntity)

        assertEqualsData(coffee, coffeeEntity)
    }

    @Test
    fun mapToEntityWithOneIngredientMapsDataCorrectly() {
        val coffee = generateCoffee()
        val coffeeEntity = mapper.mapToEntity(coffee)

        assertEqualsData(coffee, coffeeEntity)
    }

    @Test
    fun mapToEntityWithMultipleIngredientMapsDataCorrectly() {
        val coffee = generateCoffee(10)
        val coffeeEntity = mapper.mapToEntity(coffee)

        assertEqualsData(coffee, coffeeEntity)
    }

    @Test
    fun mapToEntityWithNoIngredientMapsDataCorrectly() {
        val coffee = generateCoffee(0)
        val coffeeEntity = mapper.mapToEntity(coffee)

        assertEqualsData(coffee, coffeeEntity)
    }

    private fun assertEqualsData(
        coffeeDrink: CoffeeDrink,
        coffeeDrinkEntity: CoffeeDrinkEntity
    ) {
        assertEquals(coffeeDrink.id, coffeeDrinkEntity.id)
        assertEquals(coffeeDrink.name, coffeeDrinkEntity.name)
        assertEquals(coffeeDrink.description, coffeeDrinkEntity.description)

        assertEquals(coffeeDrink.ingredients.size, coffeeDrinkEntity.ingredients.size)
        for (index in 0 until coffeeDrink.ingredients.size) {
            assertEquals(coffeeDrink.ingredients[index].name, coffeeDrinkEntity.ingredients[index].name)
        }
    }
}