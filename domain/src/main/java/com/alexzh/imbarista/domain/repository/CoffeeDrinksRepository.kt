package com.alexzh.imbarista.domain.repository

import com.alexzh.imbarista.domain.model.CoffeeDrink
import io.reactivex.Single

interface CoffeeDrinksRepository {

    fun getCoffeeDrinks(): Single<List<CoffeeDrink>>

    fun getCoffeeDrinksByName(name: String): Single<List<CoffeeDrink>>

    fun getCoffeeDrinkById(id: Long): Single<CoffeeDrink>

    fun addCoffeeDrinkToFavourites(coffeeId: Long): Single<CoffeeDrink>

    fun removeCoffeeDrinkFromFavourites(coffeeId: Long): Single<CoffeeDrink>
}