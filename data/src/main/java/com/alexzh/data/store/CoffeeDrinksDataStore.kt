package com.alexzh.data.store

import com.alexzh.data.model.CoffeeDrinkEntity
import io.reactivex.Completable
import io.reactivex.Single

interface CoffeeDrinksDataStore {

    fun getCoffeeDrinks(accessToken: String): Single<List<CoffeeDrinkEntity>>

    fun getCoffeeDrinksByName(name: String): Single<List<CoffeeDrinkEntity>>

    fun getCoffeeById(id: Long): Single<CoffeeDrinkEntity>

    fun setCoffeeAsFavourite(id: Long): Completable

    fun setCoffeeAsNotFavourite(id: Long): Completable

    fun saveCoffeeDrinks(coffeeDrinks: List<CoffeeDrinkEntity>): Completable

    fun clearCoffeeDrinks(): Completable
}