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

    fun generateCoffeeEntity(ingredientCount: Int = 1): CoffeeDrinkEntity {
        val ingredients = mutableListOf<IngredientEntity>()
        repeat(ingredientCount) {
            ingredients.add(generateIngredientEntity())
        }

        return CoffeeDrinkEntity(
            id = RandomData.randomLong(),
            name = RandomData.randomString(),
            description = RandomData.randomString(),
            ingredients = ingredients
        )
    }

    fun generateCoffeeEntities(
        coffeeCount: Int = 1,
        ingredientCount: Int = 1
    ): List<CoffeeDrinkEntity> {
        val coffeeDrinks = mutableListOf<CoffeeDrinkEntity>()
        repeat(coffeeCount) {
            coffeeDrinks.add(generateCoffeeEntity(ingredientCount))
        }
        return coffeeDrinks
    }
}