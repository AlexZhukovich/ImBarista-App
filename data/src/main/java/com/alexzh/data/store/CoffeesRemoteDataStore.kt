package com.alexzh.data.store

import com.alexzh.data.model.CoffeeEntity
import com.alexzh.data.repository.CoffeesRemoteRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.UnsupportedOperationException

class CoffeesRemoteDataStore(
    private val remoteRepository: CoffeesRemoteRepository
) : CoffeesDataStore {

    override fun getCoffees(): Single<List<CoffeeEntity>> {
        return remoteRepository.getCoffees()
    }

    override fun getCoffeesByName(name: String): Single<List<CoffeeEntity>> {
        throw UnsupportedOperationException("'Getting coffees by name' operation is unsupported")
    }

    override fun getCoffeeById(id: Long): Single<CoffeeEntity> {
        throw UnsupportedOperationException("'Getting coffee by id' operation is unsupported")
    }

    override fun setCoffeeAsFavourite(id: Long): Completable {
        throw UnsupportedOperationException("'Setting coffee as favourite' operation is unsupported")
    }

    override fun setCoffeeAsNotFavourite(id: Long): Completable {
        throw UnsupportedOperationException("'Setting coffee as not favourite' operation is unsupported")
    }

    override fun saveCoffees(coffees: List<CoffeeEntity>): Completable {
        throw UnsupportedOperationException("'Saving coffees' operation is unsupported")
    }

    override fun clearCoffees(): Completable {
        throw UnsupportedOperationException("'Clearing coffees' operation is unsupported")
    }
}