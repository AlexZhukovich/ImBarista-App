package com.alexzh.data.mapper

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.testdata.data.GenerateDataTestData.generateCoffeeEntity
import com.alexzh.testdata.domain.GenerateDomainTestData.generateCoffeeDrink
import org.junit.Assert.assertEquals
import org.junit.Test

class CoffeeDrinkMapperTest {

    private val mapper = CoffeeMapper()

    @Test
    fun mapFromEntityMapsDataCorrectly() {
        val coffeeDrinkEntity = generateCoffeeEntity()
        val coffeeDrink = mapper.mapFromEntity(coffeeDrinkEntity)

        assertEqualsData(coffeeDrink, coffeeDrinkEntity)
    }

    @Test
    fun mapToEntityMapsDataCorrectly() {
        val coffeeDrink = generateCoffeeDrink()
        val coffeeDrinkEntity = mapper.mapToEntity(coffeeDrink)

        assertEqualsData(coffeeDrink, coffeeDrinkEntity)
    }

    private fun assertEqualsData(
        coffeeDrink: CoffeeDrink,
        coffeeDrinkEntity: CoffeeDrinkEntity
    ) {
        assertEquals(coffeeDrink.id, coffeeDrinkEntity.id)
        assertEquals(coffeeDrink.name, coffeeDrinkEntity.name)
        assertEquals(coffeeDrink.imageUrl, coffeeDrinkEntity.imageUrl)
        assertEquals(coffeeDrink.description, coffeeDrinkEntity.description)
        assertEquals(coffeeDrink.ingredients, coffeeDrinkEntity.ingredients)
        assertEquals(coffeeDrink.isFavourite, coffeeDrinkEntity.isFavourite)
    }
}