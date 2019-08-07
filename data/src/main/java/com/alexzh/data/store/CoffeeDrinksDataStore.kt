package com.alexzh.data.store

import com.alexzh.data.model.CoffeeEntity
import io.reactivex.Completable
import io.reactivex.Single

interface CoffeeDrinksDataStore {

    fun getCoffees(): Single<List<CoffeeEntity>>

    fun getCoffeesByName(name: String): Single<List<CoffeeEntity>>

    fun getCoffeeById(id: Long): Single<CoffeeEntity>

    fun setCoffeeAsFavourite(id: Long): Completable

    fun setCoffeeAsNotFavourite(id: Long): Completable

    fun saveCoffees(coffees: List<CoffeeEntity>): Completable

    fun clearCoffees(): Completable
}