package com.alexzh.imbarista.remote

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.data.repository.CoffeeDrinksRemoteRepository
import com.alexzh.data.repository.PreferencesRepository
import com.alexzh.imbarista.remote.mapper.CoffeeMapper
import com.alexzh.imbarista.remote.mapper.SessionMapper
import com.alexzh.imbarista.remote.model.CoffeeDrinkFavouriteValueModel
import com.alexzh.imbarista.remote.model.RefreshTokenModel
import com.alexzh.imbarista.remote.service.CoffeeDrinksService
import io.reactivex.Single
import retrofit2.HttpException

class CoffeeDrinkRemoteRepositoryImpl(
    private val service: CoffeeDrinksService,
    private val coffeeMapper: CoffeeMapper,
    private val sessionMapper: SessionMapper,
    private val preferencesRepository: PreferencesRepository
) : CoffeeDrinksRemoteRepository {

    override fun getCoffeeDrinks(
        accessToken: String
    ): Single<List<CoffeeDrinkEntity>> {
        return service.getCoffeeDrinks(accessToken)
            .onErrorResumeNext { error ->
                val session = preferencesRepository.getSessionInfo()
                if (error is HttpException && error.code() == 401) {
                    service.refreshToken(session.sessionId, session.accessToken, RefreshTokenModel(session.refreshToken))
                        .doOnSuccess {
                            val newSession = sessionMapper.mapFromModel(it.data)
                            preferencesRepository.saveSessionInfo(newSession)
                        }.blockingGet()
                    val updatedSession = preferencesRepository.getSessionInfo()
                    return@onErrorResumeNext service.getCoffeeDrinks(updatedSession.accessToken)
                }
                return@onErrorResumeNext Single.error(error)
            }
            .retry(3)
            .map { response ->
                response.data.data.map { coffeeDrinkModel ->
                    coffeeMapper.mapFromModel(coffeeDrinkModel)
                }
            }
    }

    override fun getCoffeeById(coffeeDrinkId: Long, accessToken: String): Single<CoffeeDrinkEntity> {
        return service.getCoffeeDrinkById(coffeeDrinkId, accessToken)
            .onErrorResumeNext { error ->
                val session = preferencesRepository.getSessionInfo()
                if (error is HttpException && error.code() == 401) {
                    service.refreshToken(session.sessionId, session.accessToken, RefreshTokenModel(session.refreshToken))
                        .doOnSuccess {
                            val newSession = sessionMapper.mapFromModel(it.data)
                            preferencesRepository.saveSessionInfo(newSession)
                        }.blockingGet()
                    val updatedSession = preferencesRepository.getSessionInfo()
                    return@onErrorResumeNext service.getCoffeeDrinkById(coffeeDrinkId, updatedSession.accessToken)
                }
                return@onErrorResumeNext Single.error(error)
            }
            .retry(3)
            .map { response ->
                response.data.data.map { coffeeDrinkModel ->
                    coffeeMapper.mapFromModel(coffeeDrinkModel)
                }
            }
            .flatMap {
                Single.just(it.first())
            }
    }

    override fun addCoffeeDrinkToFavourite(
        coffeeDrinkId: Long,
        accessToken: String
    ): Single<CoffeeDrinkEntity> {
        return service.markDrinkAsFavourite(coffeeDrinkId, accessToken, CoffeeDrinkFavouriteValueModel(true))
            .onErrorResumeNext { error ->
                val session = preferencesRepository.getSessionInfo()
                if (error is HttpException && error.code() == 401) {
                    service.refreshToken(session.sessionId, session.accessToken, RefreshTokenModel(session.refreshToken))
                        .doOnSuccess {
                            val newSession = sessionMapper.mapFromModel(it.data)
                            preferencesRepository.saveSessionInfo(newSession)
                        }.blockingGet()
                    val updatedSession = preferencesRepository.getSessionInfo()
                    return@onErrorResumeNext service.markDrinkAsFavourite(coffeeDrinkId, updatedSession.accessToken, CoffeeDrinkFavouriteValueModel(true))
                }
                return@onErrorResumeNext Single.error(error)
            }
            .retry(3)
            .map { response ->
                response.data.data.map { coffeeDrinkModel ->
                    coffeeMapper.mapFromModel(coffeeDrinkModel)
                }
            }
            .flatMap {
                Single.just(it.first())
            }
    }

    override fun removeCoffeeDrinkFromFavourite(
        coffeeDrinkId: Long,
        accessToken: String
    ): Single<CoffeeDrinkEntity> {
        return service.markDrinkAsFavourite(coffeeDrinkId, accessToken, CoffeeDrinkFavouriteValueModel(false))
            .onErrorResumeNext { error ->
                val session = preferencesRepository.getSessionInfo()
                if (error is HttpException && error.code() == 401) {
                    service.refreshToken(session.sessionId, session.accessToken, RefreshTokenModel(session.refreshToken))
                        .doOnSuccess {
                            val newSession = sessionMapper.mapFromModel(it.data)
                            preferencesRepository.saveSessionInfo(newSession)
                        }.blockingGet()
                    val updatedSession = preferencesRepository.getSessionInfo()
                    return@onErrorResumeNext service.markDrinkAsFavourite(coffeeDrinkId, updatedSession.accessToken, CoffeeDrinkFavouriteValueModel(false))
                }
                return@onErrorResumeNext Single.error(error)
            }
            .retry(3)
            .map { response ->
                response.data.data.map { coffeeDrinkModel ->
                    coffeeMapper.mapFromModel(coffeeDrinkModel)
                }
            }
            .flatMap {
                Single.just(it.first())
            }
    }
}