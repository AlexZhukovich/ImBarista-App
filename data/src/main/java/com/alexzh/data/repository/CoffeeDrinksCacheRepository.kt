package com.alexzh.data.repository

import com.alexzh.data.model.CoffeeDrinkEntity
import io.reactivex.Completable
import io.reactivex.Single

interface CoffeeDrinksCacheRepository {

    fun getCoffeeDrinks(): Single<List<CoffeeDrinkEntity>>

    fun getCoffeeDrinksByName(name: String): Single<List<CoffeeDrinkEntity>>

    fun getCoffeeById(id: Long): Single<CoffeeDrinkEntity>

    fun setCoffeeAsFavourite(id: Long): Single<CoffeeDrinkEntity>

    fun setCoffeeAsNotFavourite(id: Long): Single<CoffeeDrinkEntity>

    fun saveCoffeeDrinks(coffeeDrinks: List<CoffeeDrinkEntity>): Completable

    fun clearCoffeeDrinks(): Completable

    fun areCoffeeDrinksCached(): Single<Boolean>

    fun setLastCacheVersion(lastCacheVersion: Long): Completable

    fun isCoffeeDrinksCacheExpired(lastCacheVersion: Long): Boolean
}