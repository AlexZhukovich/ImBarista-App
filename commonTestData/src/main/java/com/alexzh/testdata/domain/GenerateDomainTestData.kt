package com.alexzh.testdata.domain

import com.alexzh.imbarista.domain.interactor.coffeedrink.browse.GetCoffeeDrinkById
import com.alexzh.imbarista.domain.interactor.coffeedrink.browse.GetCoffeeDrinksByName
import com.alexzh.imbarista.domain.interactor.coffeedrink.favourite.AddCoffeeDrinkToFavourites
import com.alexzh.imbarista.domain.interactor.coffeedrink.favourite.RemoveCoffeeDrinkFromFavourite
import com.alexzh.imbarista.domain.interactor.user.*
import com.alexzh.imbarista.domain.model.*
import com.alexzh.testdata.base.RandomData.randomBoolean
import com.alexzh.testdata.base.RandomData.randomEmail
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.base.RandomData.randomString

object GenerateDomainTestData {

    fun generateSession(): Session {
        return Session(
            sessionId = randomLong(),
            accessToken = randomString(),
            accessTokenExpiry = randomLong(),
            refreshToken = randomString(),
            refreshTokenExpiry = randomLong()
        )
    }

    fun generateUser(): User {
        return User(
            id = randomLong(),
            name = randomString(),
            email = randomEmail()
        )
    }

    fun generateCreateAccountParam(name: String = randomString()): CreateAccount.Param {
        return CreateAccount.Param.forCreatingAccount(
            name = name,
            email = randomString(),
            password = randomString()
        )
    }

    fun generateLogInParam(): LogIn.Param {
        return LogIn.Param.forLogIn(
            email = randomEmail(),
            password = randomString()
        )
    }

    fun generateCoffeeDrinks(coffeeCount: Int = 1, ingredientsCount: Int = 1): List<CoffeeDrink> {
        val coffeeDrinks = mutableListOf<CoffeeDrink>()
        repeat(coffeeCount) {
            coffeeDrinks.add(generateCoffeeDrink(ingredientsCount))
        }
        return coffeeDrinks
    }

    fun generateCoffeeDrink(ingredientsCount: Int = 1): CoffeeDrink {
        var ingredients = ""
        repeat(ingredientsCount) {
            ingredients += "${randomString()}, "
        }

        return CoffeeDrink(
            id = randomLong(),
            name = randomString(),
            imageUrl = randomString(),
            description = randomString(),
            ingredients = ingredients,
            isFavourite = randomBoolean()
        )
    }

    fun generateGetCoffeeByIdParam(coffeeId: Long = randomLong()): GetCoffeeDrinkById.Param {
        return GetCoffeeDrinkById.Param.forCoffee(coffeeId)
    }

    fun generateGetCoffeeDrinksByNameParam(coffeeName: String = randomString()): GetCoffeeDrinksByName.Param {
        return GetCoffeeDrinksByName.Param.forCoffeeDrinks(coffeeName)
    }

    fun generateAddCoffeeToFavourites(coffeeId: Long = randomLong()): AddCoffeeDrinkToFavourites.Param {
        return AddCoffeeDrinkToFavourites.Param.forCoffeeDrink(coffeeId)
    }

    fun generateRemoveCoffeeFromFavourite(coffeeId: Long = randomLong()): RemoveCoffeeDrinkFromFavourite.Param {
        return RemoveCoffeeDrinkFromFavourite.Param.forCoffeeDrink(coffeeId)
    }
}