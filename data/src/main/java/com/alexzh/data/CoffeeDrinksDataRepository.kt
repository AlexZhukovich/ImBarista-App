package com.alexzh.data

import com.alexzh.data.mapper.CoffeeMapper
import com.alexzh.data.repository.CoffeeDrinksCacheRepository
import com.alexzh.data.store.CoffeesDataStoreFactory
import com.alexzh.imbarista.domain.model.Coffee
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.UnsupportedOperationException

class CoffeeDrinksDataRepository(
    private val mapper: CoffeeMapper,
    private val cacheRepository: CoffeeDrinksCacheRepository,
    private val storeFactory: CoffeesDataStoreFactory
) : CoffeeDrinksRepository {

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