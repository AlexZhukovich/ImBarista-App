package com.alexzh.data.store

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.data.repository.CoffeeDrinksRemoteRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.UnsupportedOperationException

class CoffeeDrinksRemoteDataStore(
    private val remoteRepository: CoffeeDrinksRemoteRepository
) : CoffeeDrinksDataStore {

    override fun getCoffeeDrinks(): Single<List<CoffeeDrinkEntity>> {
        return remoteRepository.getCoffeeDrinks()
    }

    override fun getCoffeeDrinksByName(name: String): Single<List<CoffeeDrinkEntity>> {
        throw UnsupportedOperationException("'Getting coffee drinks by name' operation is unsupported")
    }

    override fun getCoffeeById(id: Long): Single<CoffeeDrinkEntity> {
        throw UnsupportedOperationException("'Getting coffee by id' operation is unsupported")
    }

    override fun setCoffeeAsFavourite(id: Long): Completable {
        throw UnsupportedOperationException("'Setting coffee as favourite' operation is unsupported")
    }

    override fun setCoffeeAsNotFavourite(id: Long): Completable {
        throw UnsupportedOperationException("'Setting coffee as not favourite' operation is unsupported")
    }

    override fun saveCoffeeDrinks(coffeeDrinks: List<CoffeeDrinkEntity>): Completable {
        throw UnsupportedOperationException("'Saving coffee drinks' operation is unsupported")
    }

    override fun clearCoffeeDrinks(): Completable {
        throw UnsupportedOperationException("'Clearing coffee drinks' operation is unsupported")
    }
}