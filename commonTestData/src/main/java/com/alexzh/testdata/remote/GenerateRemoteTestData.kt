package com.alexzh.testdata.remote

import com.alexzh.imbarista.remote.model.AuthUserModel
import com.alexzh.imbarista.remote.model.CoffeeModel
import com.alexzh.imbarista.remote.model.IngredientModel
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.base.RandomData.randomString

object GenerateRemoteTestData {

    fun generateAuthUserModel(): AuthUserModel {
        return AuthUserModel(
            randomString(),
            randomString()
        )
    }

    fun generateIngredientModel(): IngredientModel {
        return IngredientModel(randomString())
    }

    fun generateCoffeeModel(ingredientCount: Int = 1): CoffeeModel {
        return CoffeeModel(
            id = randomLong(),
            name = randomString(),
            description = randomString(),
            ingredients = generateIngredientModels(ingredientCount)
        )
    }

    private fun generateIngredientModels(count: Int = 1): List<IngredientModel> {
        val ingredients = mutableListOf<IngredientModel>()
        repeat(count) {
            ingredients.add(generateIngredientModel())
        }
        return ingredients
    }
}