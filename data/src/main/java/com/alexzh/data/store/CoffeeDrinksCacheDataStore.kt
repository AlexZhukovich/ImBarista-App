package com.alexzh.data.store

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.data.repository.CoffeeDrinksCacheRepository
import io.reactivex.Completable
import io.reactivex.Single

class CoffeeDrinksCacheDataStore(
    private val cacheRepository: CoffeeDrinksCacheRepository
) : CoffeeDrinksDataStore {

    override fun getCoffeeDrinks(accessToken: String): Single<List<CoffeeDrinkEntity>> {
        return cacheRepository.getCoffeeDrinks()
    }

    override fun getCoffeeDrinksByName(name: String): Single<List<CoffeeDrinkEntity>> {
        return cacheRepository.getCoffeeDrinksByName(name)
    }

    override fun getCoffeeById(id: Long): Single<CoffeeDrinkEntity> {
        return cacheRepository.getCoffeeById(id)
    }

    override fun setCoffeeAsFavourite(id: Long): Completable {
        return cacheRepository.setCoffeeAsFavourite(id)
    }

    override fun setCoffeeAsNotFavourite(id: Long): Completable {
        return cacheRepository.setCoffeeAsNotFavourite(id)
    }

    override fun saveCoffeeDrinks(coffeeDrinks: List<CoffeeDrinkEntity>): Completable {
        return cacheRepository.saveCoffeeDrinks(coffeeDrinks)
    }

    override fun clearCoffeeDrinks(): Completable {
        return cacheRepository.clearCoffeeDrinks()
    }
}