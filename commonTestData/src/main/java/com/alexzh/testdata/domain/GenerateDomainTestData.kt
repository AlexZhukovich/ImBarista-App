package com.alexzh.testdata.domain

import com.alexzh.imbarista.domain.interactor.coffee.browse.GetCoffeeById
import com.alexzh.imbarista.domain.interactor.coffee.browse.GetCoffeesByName
import com.alexzh.imbarista.domain.interactor.coffee.favourite.AddCoffeeToFavourites
import com.alexzh.imbarista.domain.interactor.coffee.favourite.RemoveCoffeeFromFavourite
import com.alexzh.imbarista.domain.interactor.user.*
import com.alexzh.imbarista.domain.model.AuthUser
import com.alexzh.imbarista.domain.model.Coffee
import com.alexzh.imbarista.domain.model.Ingredient
import com.alexzh.imbarista.domain.model.User
import com.alexzh.testdata.base.RandomData.randomEmail
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.base.RandomData.randomString

object GenerateDomainTestData {

    fun generateAuthUser(): AuthUser {
        return AuthUser(
            name = randomString(),
            token = randomString()
        )
    }

    fun generateUser(): User {
        return User(
            id = randomLong(),
            name = randomString(),
            email = randomEmail(),
            password = randomString()
        )
    }

    fun generateChangeNameParam(): ChangeName.Param {
        return ChangeName.Param.forChangingName(
            randomLong(),
            randomString()
        )
    }

    fun generateChangePasswordParam(): ChangePassword.Param {
        return ChangePassword.Param.forChangingPassword(
            randomLong(),
            randomString()
        )
    }

    fun generateGetUserByIdParam(userId: Long = randomLong()): GetUserById.Param {
        return GetUserById.Param.forUser(userId)
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

    fun generateCoffees(coffeeCount: Int = 1, ingredientsCount: Int = 1): List<Coffee> {
        val coffees = mutableListOf<Coffee>()
        repeat(coffeeCount) {
            coffees.add(generateCoffee(ingredientsCount))
        }
        return coffees
    }

    fun generateCoffee(ingredientsCount: Int = 1): Coffee {
        return Coffee(
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

    fun generateGetCoffeeByIdParam(coffeeId: Long = randomLong()): GetCoffeeById.Param {
        return GetCoffeeById.Param.forCoffee(coffeeId)
    }

    fun generateGetCoffeesByNameParam(coffeeName: String = randomString()): GetCoffeesByName.Param {
        return GetCoffeesByName.Param.forCoffees(coffeeName)
    }

    fun generateAddCoffeeToFavourites(coffeeId: Long = randomLong()): AddCoffeeToFavourites.Param {
        return AddCoffeeToFavourites.Param.forCoffee(coffeeId)
    }

    fun generateRemoveCoffeeFromFavourite(coffeeId: Long = randomLong()): RemoveCoffeeFromFavourite.Param {
        return RemoveCoffeeFromFavourite.Param.forCoffee(coffeeId)
    }
}