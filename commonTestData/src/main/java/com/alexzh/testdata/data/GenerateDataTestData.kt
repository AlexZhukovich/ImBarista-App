package com.alexzh.testdata.data

import com.alexzh.data.model.*
import com.alexzh.testdata.base.RandomData

object GenerateDataTestData {

    fun generateSessionEntity(): SessionEntity {
        return SessionEntity(
            sessionId = RandomData.randomLong(),
            accessToken = RandomData.randomString(),
            accessTokenExpiry = RandomData.randomLong(),
            refreshToken = RandomData.randomString(),
            refreshTokenExpiry = RandomData.randomLong()
        )
    }

    fun generateUserEntity(): UserEntity {
        return UserEntity(
            id = RandomData.randomLong(),
            name = RandomData.randomString(),
            email = RandomData.randomEmail()
        )
    }

    fun generateIngredientEntity(): IngredientEntity {
        return IngredientEntity(RandomData.randomString())
    }

    fun generateCoffeeEntity(ingredientCount: Int = 1): CoffeeEntity {
        val ingredients = mutableListOf<IngredientEntity>()
        repeat(ingredientCount) {
            ingredients.add(generateIngredientEntity())
        }

        return CoffeeEntity(
            id = RandomData.randomLong(),
            name = RandomData.randomString(),
            description = RandomData.randomString(),
            ingredients = ingredients
        )
    }

    fun generateCoffeeEntities(
        coffeeCount: Int = 1,
        ingredientCount: Int = 1
    ): List<CoffeeEntity> {
        val coffees = mutableListOf<CoffeeEntity>()
        repeat(coffeeCount) {
            coffees.add(generateCoffeeEntity(ingredientCount))
        }
        return coffees
    }
}