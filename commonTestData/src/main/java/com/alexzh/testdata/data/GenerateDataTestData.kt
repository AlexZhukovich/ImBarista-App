package com.alexzh.testdata.data

import com.alexzh.data.model.AuthUserEntity
import com.alexzh.data.model.CoffeeEntity
import com.alexzh.data.model.IngredientEntity
import com.alexzh.data.model.UserEntity
import com.alexzh.testdata.base.RandomData

object GenerateDataTestData {

    fun generateAuthUserEntity(): AuthUserEntity {
        return AuthUserEntity(
            name = RandomData.randomString(),
            token = RandomData.randomString()
        )
    }

    fun generateUserEntity(): UserEntity {
        return UserEntity(
            id = RandomData.randomLong(),
            name = RandomData.randomString(),
            email = RandomData.randomEmail(),
            password = RandomData.randomString()
        )
    }

    fun generateIngredientEntity(): IngredientEntity {
        return IngredientEntity(RandomData.randomString())
    }

    fun generateCoffeeEntity(): CoffeeEntity {
        return CoffeeEntity(
            id = RandomData.randomLong(),
            name = RandomData.randomString(),
            description = RandomData.randomString(),
            ingredients = listOf(generateIngredientEntity())
        )
    }
}