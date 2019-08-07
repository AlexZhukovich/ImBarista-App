package com.alexzh.imbarista.cache

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.data.repository.CoffeeDrinksCacheRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.UnsupportedOperationException

class CoffeeDrinksCacheRepositoryImpl: CoffeeDrinksCacheRepository {

    override fun getCoffeeDrinks(): Single<List<CoffeeDrinkEntity>> {
        return Single.error(UnsupportedOperationException("Not implemented yet"))
    }

    override fun getCoffeeDrinksByName(name: String): Single<List<CoffeeDrinkEntity>> {
        return Single.error(UnsupportedOperationException("Not implemented yet"))
    }

    override fun getCoffeeById(id: Long): Single<CoffeeDrinkEntity> {
        return Single.error(UnsupportedOperationException("Not implemented yet"))
    }

    override fun setCoffeeAsFavourite(id: Long): Completable {
        return Completable.error(UnsupportedOperationException("Not implemented yet"))
    }

    override fun setCoffeeAsNotFavourite(id: Long): Completable {
        return Completable.error(UnsupportedOperationException("Not implemented yet"))
    }

    override fun saveCoffeeDrinks(coffeeDrinks: List<CoffeeDrinkEntity>): Completable {
        return Completable.error(UnsupportedOperationException("Not implemented yet"))
    }

    override fun clearCoffeeDrinks(): Completable {
        return Completable.error(UnsupportedOperationException("Not implemented yet"))
    }

    override fun areCoffeeDrinksCached(): Single<Boolean> {
        return Single.error(UnsupportedOperationException("Not implemented yet"))
    }

    override fun setLastCacheVersion(lastCacheVersion: Long): Completable {
        return Completable.error(UnsupportedOperationException("Not implemented yet"))
    }

    override fun isCoffeeDrinksCacheExpired(lastCacheVersion: Long): Boolean {
        return false
    }
}