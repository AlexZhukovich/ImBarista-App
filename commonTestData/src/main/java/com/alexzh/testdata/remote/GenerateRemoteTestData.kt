package com.alexzh.testdata.remote

import com.alexzh.imbarista.remote.model.*
import com.alexzh.testdata.base.RandomData.randomBoolean
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

    fun generateCoffeeModel(ingredientsCount: Int = 1): CoffeeDrinkModel {
        var ingredients = ""
        repeat(ingredientsCount) {
            ingredients += "${randomString()}, "
        }

        return CoffeeDrinkModel(
            id = randomLong(),
            name = randomString(),
            imageUrl = randomString(),
            description = randomString(),
            ingredients = ingredients,
            isFavourite = randomBoolean()
        )
    }
}