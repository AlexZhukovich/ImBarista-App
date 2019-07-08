package com.alexzh.data.mapper

import com.alexzh.data.model.IngredientEntity
import com.alexzh.imbarista.domain.model.Ingredient
import com.alexzh.testdata.data.GenerateDataTestData.generateIngredientEntity
import com.alexzh.testdata.domain.GenerateDomainTestData.generateIngredient
import org.junit.Assert.assertEquals
import org.junit.Test

class IngredientMapperTest {

    private val mapper = IngredientMapper()

    @Test
    fun mapFromEntityToDomainModelCorrectly() {
        val ingredient = generateIngredient()
        val ingredientEntity = mapper.mapToEntity(ingredient)

        assertEqualsData(ingredient, ingredientEntity)
    }

    @Test
    fun mapToEntityFromDomainModelCorrectly() {
        val ingredientEntity = generateIngredientEntity()
        val ingredient = mapper.mapFromEntity(ingredientEntity)

        assertEqualsData(ingredient, ingredientEntity)
    }

    private fun assertEqualsData(
        ingredient: Ingredient,
        ingredientEntity: IngredientEntity
    ) {
        assertEquals(ingredient.name, ingredientEntity.name)
    }
}
