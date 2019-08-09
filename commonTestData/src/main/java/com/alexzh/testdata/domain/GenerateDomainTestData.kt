package com.alexzh.testdata.domain

import com.alexzh.imbarista.domain.interactor.coffeedrink.browse.GetCoffeeDrinkById
import com.alexzh.imbarista.domain.interactor.coffeedrink.browse.GetCoffeeDrinksByName
import com.alexzh.imbarista.domain.interactor.coffeedrink.favourite.AddCoffeeToFavourites
import com.alexzh.imbarista.domain.interactor.coffeedrink.favourite.RemoveCoffeeFromFavourite
import com.alexzh.imbarista.domain.interactor.user.*
import com.alexzh.imbarista.domain.model.*
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
            coffeeDrinks.add(generateCoffee(ingredientsCount))
        }
        return coffeeDrinks
    }

    fun generateCoffee(ingredientsCount: Int = 1): CoffeeDrink {
        return CoffeeDrink(
            id = randomLong(),
            name = randomString(),
            description = randomString(),
            ingredients = generateIngredients(ingredientsCount)
        )
    }

    fun generateIngredient(): Ingredient {
        return Ingredient(randomString())
    }

    private fun generateIngredients(ingredientsCount: Int): List<Ingredient> {
        val ingredients = mutableListOf<Ingredient>()
        repeat(ingredientsCount) {
            ingredients.add(generateIngredient())
        }
        return ingredients
    }

    fun generateGetCoffeeByIdParam(coffeeId: Long = randomLong()): GetCoffeeDrinkById.Param {
        return GetCoffeeDrinkById.Param.forCoffee(coffeeId)
    }

    fun generateGetCoffeeDrinksByNameParam(coffeeName: String = randomString()): GetCoffeeDrinksByName.Param {
        return GetCoffeeDrinksByName.Param.forCoffeeDrinks(coffeeName)
    }

    fun generateAddCoffeeToFavourites(coffeeId: Long = randomLong()): AddCoffeeToFavourites.Param {
        return AddCoffeeToFavourites.Param.forCoffee(coffeeId)
    }

    fun generateRemoveCoffeeFromFavourite(coffeeId: Long = randomLong()): RemoveCoffeeFromFavourite.Param {
        return RemoveCoffeeFromFavourite.Param.forCoffee(coffeeId)
    }
}