package com.alexzh.data

import com.alexzh.data.mapper.CoffeeMapper
import com.alexzh.data.repository.CoffeesCacheRepository
import com.alexzh.data.store.CoffeesDataStoreFactory
import com.alexzh.imbarista.domain.model.Coffee
import com.alexzh.imbarista.domain.repository.CoffeesRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.UnsupportedOperationException

class CoffeesDataRepository(
    private val mapper: CoffeeMapper,
    private val cacheRepository: CoffeesCacheRepository,
    private val storeFactory: CoffeesDataStoreFactory
) : CoffeesRepository {

    override fun getCoffees(): Single<List<Coffee>> {
        return storeFactory.getRemoteDataStore().getCoffees()
            .map { it.map { entity -> mapper.mapFromEntity(entity) } }
    }

    override fun getCoffeesByName(name: String): Single<List<Coffee>> {
        return Single.error(UnsupportedOperationException("Not implemented yet"))
    }

    override fun getCoffeesById(id: Long): Single<Coffee> {
        return storeFactory.getRemoteDataStore().getCoffeeById(id)
            .map { mapper.mapFromEntity(it) }
    }

    override fun addCoffeeToFavourites(coffeeId: Long): Completable {
        return Completable.defer {
            storeFactory.getRemoteDataStore().setCoffeeAsFavourite(coffeeId)
            Completable.complete()
        }
    }

    override fun removeCoffeeFromFavourites(coffeeId: Long): Completable {
        return Completable.defer {
            storeFactory.getRemoteDataStore().setCoffeeAsNotFavourite(coffeeId)
            Completable.complete()
        }
    }
}