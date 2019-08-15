package com.alexzh.data

import com.alexzh.data.mapper.CoffeeMapper
import com.alexzh.data.model.HttpDataException
import com.alexzh.data.repository.CoffeeDrinksCacheRepository
import com.alexzh.data.store.CoffeeDrinksDataStoreFactory
import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import com.alexzh.imbarista.domain.repository.UserRepository
import io.reactivex.Single

class CoffeeDrinksDataRepository(
    private val coffeeMapper: CoffeeMapper,
    private val cacheRepository: CoffeeDrinksCacheRepository,
    private val storeFactory: CoffeeDrinksDataStoreFactory,
    private val userRepository: UserRepository
) : CoffeeDrinksRepository {

    override fun getCoffeeDrinks(): Single<List<CoffeeDrink>> {
        return storeFactory.getRemoteDataStore().getCoffeeDrinks()
            .onErrorResumeNext { error ->
                if (error is HttpDataException && error.code == 401) {
                    userRepository.refreshToken().blockingGet()
                    return@onErrorResumeNext storeFactory.getRemoteDataStore().getCoffeeDrinks()
                }
                return@onErrorResumeNext Single.error(error)
            }
            .retry(3)
            .map { it.map { entity -> coffeeMapper.mapFromEntity(entity) } }
    }

    override fun getCoffeeDrinksByName(name: String): Single<List<CoffeeDrink>> {
        return Single.error(UnsupportedOperationException("Not implemented yet"))
    }

    override fun getCoffeeDrinkById(id: Long): Single<CoffeeDrink> {
        return storeFactory.getRemoteDataStore().getCoffeeById(id)
            .onErrorResumeNext { error ->
                if (error is HttpDataException && error.code == 401) {
                    userRepository.refreshToken().blockingGet()
                    return@onErrorResumeNext storeFactory.getRemoteDataStore().getCoffeeById(id)
                }
                return@onErrorResumeNext Single.error(error)
            }
            .retry(3)
            .map { coffeeMapper.mapFromEntity(it) }
    }

    override fun addCoffeeDrinkToFavourites(coffeeId: Long): Single<CoffeeDrink> {
        return storeFactory.getRemoteDataStore().setCoffeeAsFavourite(coffeeId)
            .onErrorResumeNext { error ->
                if (error is HttpDataException && error.code == 401) {
                    userRepository.refreshToken().blockingGet()
                    return@onErrorResumeNext storeFactory.getRemoteDataStore().setCoffeeAsFavourite(coffeeId)
                }
                return@onErrorResumeNext Single.error(error)
            }
            .retry(3)
            .map { coffeeMapper.mapFromEntity(it) }
    }

    override fun removeCoffeeDrinkFromFavourites(coffeeId: Long): Single<CoffeeDrink> {
        return storeFactory.getRemoteDataStore().setCoffeeAsNotFavourite(coffeeId)
            .onErrorResumeNext { error ->
                if (error is HttpDataException && error.code == 401) {
                    userRepository.refreshToken().blockingGet()
                    return@onErrorResumeNext storeFactory.getRemoteDataStore().setCoffeeAsNotFavourite(coffeeId)
                }
                return@onErrorResumeNext Single.error(error)
            }
            .retry(3)
            .map { coffeeMapper.mapFromEntity(it) }
    }
}