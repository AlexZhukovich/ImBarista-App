package com.alexzh.data.repository

import com.alexzh.data.model.CoffeeEntity
import io.reactivex.Completable
import io.reactivex.Single

interface CoffeesRepositoryCache {

    fun getCoffees(): Single<List<CoffeeEntity>>

    fun getCoffeesByName(name: String): Single<List<CoffeeEntity>>

    fun getCoffeeById(id: Long): Single<CoffeeEntity>

    fun setCoffeeAsFavourite(id: Long): Completable

    fun setCoffeeAsNotFavourite(id: Long): Completable

    fun saveCoffees(coffees: List<CoffeeEntity>): Completable

    fun clearCoffees(): Completable

    fun areCoffeesCached(): Single<Boolean>

    fun setLastCacheVersion(lastCacheVersion: Long): Completable

    fun isCoffeesCacheExpired(lastCacheVersion: Long): Boolean
}