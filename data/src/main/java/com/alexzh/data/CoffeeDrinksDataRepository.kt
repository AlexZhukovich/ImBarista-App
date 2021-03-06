package com.alexzh.data

import com.alexzh.data.exception.AuthDataException
import com.alexzh.data.mapper.CoffeeDrinkDataMapper
import com.alexzh.data.model.HttpDataException
import com.alexzh.data.repository.CoffeeDrinksCacheRepository
import com.alexzh.data.repository.PreferencesRepository
import com.alexzh.data.store.CoffeeDrinksDataStoreFactory
import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import com.alexzh.imbarista.domain.repository.UserRepository
import io.reactivex.Single

class CoffeeDrinksDataRepository(
    private val coffeeDrinkDataMapper: CoffeeDrinkDataMapper,
    private val cacheRepository: CoffeeDrinksCacheRepository,
    private val storeFactory: CoffeeDrinksDataStoreFactory,
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository
) : CoffeeDrinksRepository {

    companion object {
        const val REPEAT_REQUEST_COUNT = 3L
    }

    override fun getCoffeeDrinks(): Single<List<CoffeeDrink>> {
        return storeFactory.getRemoteDataStore().getCoffeeDrinks()
            .onErrorResumeNext { error ->
                if (error is HttpDataException && error.code == 401) {
                    userRepository.refreshToken().blockingGet()
                    return@onErrorResumeNext storeFactory.getRemoteDataStore().getCoffeeDrinks()
                }
                return@onErrorResumeNext Single.error(error)
            }
            .onErrorResumeNext { error ->
                if (error is AuthDataException) {
                    preferencesRepository.clearSessionInfo()
                }
                return@onErrorResumeNext Single.error(error)
            }
            .retry(REPEAT_REQUEST_COUNT)
            .map { it.map { entity -> coffeeDrinkDataMapper.mapFromEntity(entity) } }
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
            .onErrorResumeNext { error ->
                if (error is AuthDataException) {
                    preferencesRepository.clearSessionInfo()
                }
                return@onErrorResumeNext Single.error(error)
            }
            .retry(REPEAT_REQUEST_COUNT)
            .map { coffeeDrinkDataMapper.mapFromEntity(it) }
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
            .onErrorResumeNext { error ->
                if (error is AuthDataException) {
                    preferencesRepository.clearSessionInfo()
                }
                return@onErrorResumeNext Single.error(error)
            }
            .retry(REPEAT_REQUEST_COUNT)
            .map { coffeeDrinkDataMapper.mapFromEntity(it) }
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
            .onErrorResumeNext { error ->
                if (error is AuthDataException) {
                    preferencesRepository.clearSessionInfo()
                }
                return@onErrorResumeNext Single.error(error)
            }
            .retry(REPEAT_REQUEST_COUNT)
            .map { coffeeDrinkDataMapper.mapFromEntity(it) }
    }
}