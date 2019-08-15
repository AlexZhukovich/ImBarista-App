package com.alexzh.data.store

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.data.repository.CoffeeDrinksRemoteRepository
import com.alexzh.data.repository.PreferencesRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.UnsupportedOperationException

class CoffeeDrinksRemoteDataStore(
    private val remoteRepository: CoffeeDrinksRemoteRepository,
    private val preferencesRepository: PreferencesRepository
) : CoffeeDrinksDataStore {

    override fun getCoffeeDrinks(): Single<List<CoffeeDrinkEntity>> {
        val sessionEntity = preferencesRepository.getSessionInfo()
        return remoteRepository.getCoffeeDrinks(sessionEntity.accessToken)
    }

    override fun getCoffeeDrinksByName(name: String): Single<List<CoffeeDrinkEntity>> {
        throw UnsupportedOperationException("'Getting coffee drinks by name' operation is unsupported")
    }

    override fun getCoffeeById(id: Long): Single<CoffeeDrinkEntity> {
        val sessionEntity = preferencesRepository.getSessionInfo()
        return remoteRepository.getCoffeeById(id, sessionEntity.accessToken)
    }

    override fun setCoffeeAsFavourite(id: Long): Single<CoffeeDrinkEntity> {
        val sessionEntity = preferencesRepository.getSessionInfo()
        return remoteRepository.addCoffeeDrinkToFavourite(id, sessionEntity.accessToken)
    }

    override fun setCoffeeAsNotFavourite(id: Long): Single<CoffeeDrinkEntity> {
        val sessionEntity = preferencesRepository.getSessionInfo()
        return remoteRepository.removeCoffeeDrinkFromFavourite(id, sessionEntity.accessToken)
    }

    override fun saveCoffeeDrinks(coffeeDrinks: List<CoffeeDrinkEntity>): Completable {
        throw UnsupportedOperationException("'Saving coffee drinks' operation is unsupported")
    }

    override fun clearCoffeeDrinks(): Completable {
        throw UnsupportedOperationException("'Clearing coffee drinks' operation is unsupported")
    }
}