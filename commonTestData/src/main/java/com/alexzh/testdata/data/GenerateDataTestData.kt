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

    fun generateCoffeeEntity(ingredientsCount: Int = 1): CoffeeDrinkEntity {
        var ingredients = ""
        repeat(ingredientsCount) {
            ingredients += "${RandomData.randomString()}, "
        }

        return CoffeeDrinkEntity(
            id = RandomData.randomLong(),
            name = RandomData.randomString(),
            imageUrl = RandomData.randomString(),
            description = RandomData.randomString(),
            ingredients = ingredients,
            isFavourite = RandomData.randomBoolean()
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