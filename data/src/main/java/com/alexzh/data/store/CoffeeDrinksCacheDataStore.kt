package com.alexzh.data.store

import com.alexzh.data.model.CoffeeEntity
import com.alexzh.data.repository.CoffeeDrinksCacheRepository
import io.reactivex.Completable
import io.reactivex.Single

class CoffeeDrinksCacheDataStore(
    private val cacheRepository: CoffeeDrinksCacheRepository
) : CoffeeDrinksDataStore {

    override fun getCoffees(): Single<List<CoffeeEntity>> {
        return cacheRepository.getCoffees()
    }

    override fun getCoffeesByName(name: String): Single<List<CoffeeEntity>> {
        return cacheRepository.getCoffeesByName(name)
    }

    override fun getCoffeeById(id: Long): Single<CoffeeEntity> {
        return cacheRepository.getCoffeeById(id)
    }

    override fun setCoffeeAsFavourite(id: Long): Completable {
        return cacheRepository.setCoffeeAsFavourite(id)
    }

    override fun setCoffeeAsNotFavourite(id: Long): Completable {
        return cacheRepository.setCoffeeAsNotFavourite(id)
    }

    override fun saveCoffees(coffees: List<CoffeeEntity>): Completable {
        return cacheRepository.saveCoffees(coffees)
    }

    override fun clearCoffees(): Completable {
        return cacheRepository.clearCoffees()
    }
}