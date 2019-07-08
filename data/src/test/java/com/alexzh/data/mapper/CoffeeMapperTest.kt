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
    fun mapFromEntityToDomainModelCorrectly() {
        val coffeeEntity = generateCoffeeEntity()
        val coffee = mapper.mapFromEntity(coffeeEntity)

        assertEqualsData(coffee, coffeeEntity)
    }

    @Test
    fun mapToEntityFromDomainModelCorrectly() {
        val coffee = generateCoffee()
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
        assertEquals(coffee.ingredients.first().name, coffeeEntity.ingredients.first().name)
    }
}