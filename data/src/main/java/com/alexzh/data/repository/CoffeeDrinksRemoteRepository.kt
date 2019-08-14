package com.alexzh.data.repository

import com.alexzh.data.model.CoffeeDrinkEntity
import io.reactivex.Single

interface CoffeeDrinksRemoteRepository {

    fun getCoffeeDrinks(accessToken: String): Single<List<CoffeeDrinkEntity>>

    fun getCoffeeById(coffeeDrinkId: Long, accessToken: String): Single<CoffeeDrinkEntity>

    fun addCoffeeDrinkToFavourite(coffeeDrinkId: Long, accessToken: String): Single<CoffeeDrinkEntity>

    fun removeCoffeeDrinkFromFavourite(coffeeDrinkId: Long, accessToken: String): Single<CoffeeDrinkEntity>
}