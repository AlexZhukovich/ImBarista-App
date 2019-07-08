package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.model.IngredientEntity
import com.alexzh.imbarista.remote.model.IngredientModel
import com.alexzh.testdata.remote.GenerateRemoteTestData.generateIngredientModel
import org.junit.Assert.assertEquals
import org.junit.Test

class IngredientsMapperTest {

    private val mapper = IngredientsMapper()

    @Test
    fun mapFromModelMapsDataCorrectly() {
        val ingredientModel = generateIngredientModel()
        val ingredientEntity = mapper.mapFromModel(ingredientModel)

        assertEqualsData(ingredientModel, ingredientEntity)
    }

    private fun assertEqualsData(
        ingredientModel: IngredientModel,
        ingredientEntity: IngredientEntity
    ) {
        assertEquals(ingredientModel.name, ingredientEntity.name)
    }
}