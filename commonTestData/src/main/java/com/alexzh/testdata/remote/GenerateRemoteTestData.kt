package com.alexzh.testdata.remote

import com.alexzh.imbarista.remote.model.*
import com.alexzh.testdata.base.RandomData.randomEmail
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.base.RandomData.randomString

object GenerateRemoteTestData {

    fun generateSessionModel(): SessionModel {
        return SessionModel(
            sessionId = randomLong(),
            accessToken = randomString(),
            accessTokenExpiry = randomLong(),
            refreshToken = randomString(),
            refreshTokenExpiry = randomLong()
        )
    }

    fun generateUserModel(): UserModel {
        return UserModel(
            id = randomLong(),
            name = randomString(),
            email = randomEmail(),
            password = randomString()
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