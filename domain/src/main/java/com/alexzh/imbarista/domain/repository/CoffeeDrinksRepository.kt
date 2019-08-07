package com.alexzh.imbarista.domain.repository

import com.alexzh.imbarista.domain.model.Coffee
import io.reactivex.Completable
import io.reactivex.Single

interface CoffeeDrinksRepository {

    fun getCoffees(): Single<List<Coffee>>

    fun getCoffeesByName(name: String): Single<List<Coffee>>

    fun getCoffeesById(id: Long): Single<Coffee>

    fun addCoffeeToFavourites(coffeeId: Long): Completable

    fun removeCoffeeFromFavourites(coffeeId: Long): Completable
}