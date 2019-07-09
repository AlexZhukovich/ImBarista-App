package com.alexzh.data.mapper

import com.alexzh.data.model.CoffeeEntity
import com.alexzh.imbarista.domain.model.Coffee
import com.alexzh.testdata.data.GenerateDataTestData.generateCoffeeEntity
import com.alexzh.testdata.domain.GenerateDomainTestData.generateCoffee
import org.junit.Assert.assertEquals
import org.junit.Test

class CoffeeMapperTest {

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
        coffee: Coffee,
        coffeeEntity: CoffeeEntity
    ) {
        assertEquals(coffee.id, coffeeEntity.id)
        assertEquals(coffee.name, coffeeEntity.name)
        assertEquals(coffee.description, coffeeEntity.description)

        assertEquals(coffee.ingredients.size, coffeeEntity.ingredients.size)
        for (index in 0 until coffee.ingredients.size) {
            assertEquals(coffee.ingredients[index].name, coffeeEntity.ingredients[index].name)
        }
    }
}