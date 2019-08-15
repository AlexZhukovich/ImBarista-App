package com.alexzh.imbarista.remote

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.data.repository.CoffeeDrinksRemoteRepository
import com.alexzh.imbarista.remote.mapper.CoffeeMapper
import com.alexzh.imbarista.remote.mapper.HttpExceptionMapper
import com.alexzh.imbarista.remote.model.CoffeeDrinkDataModel
import com.alexzh.imbarista.remote.model.CoffeeDrinkFavouriteValueModel
import com.alexzh.imbarista.remote.model.ResponseModel
import com.alexzh.imbarista.remote.service.CoffeeDrinksService
import io.reactivex.Single
import retrofit2.HttpException

class CoffeeDrinkRemoteRepositoryImpl(
    private val service: CoffeeDrinksService,
    private val coffeeMapper: CoffeeMapper,
    private val httpExceptionMapper: HttpExceptionMapper
) : CoffeeDrinksRemoteRepository {

    override fun getCoffeeDrinks(
        accessToken: String
    ): Single<List<CoffeeDrinkEntity>> {
        return service.getCoffeeDrinks(accessToken)
            .onErrorResumeNext { handleHttpError(it) }
            .map { convertCoffeeDrinkResponseToCoffeeDrinkEntities(it) }
    }

    override fun getCoffeeById(coffeeDrinkId: Long, accessToken: String): Single<CoffeeDrinkEntity> {
        return service.getCoffeeDrinkById(coffeeDrinkId, accessToken)
            .onErrorResumeNext { handleHttpError(it) }
            .map { convertCoffeeDrinkResponseToCoffeeDrinkEntities(it) }
            .flatMap { Single.just(it.first()) }
    }

    override fun addCoffeeDrinkToFavourite(
        coffeeDrinkId: Long,
        accessToken: String
    ): Single<CoffeeDrinkEntity> {
        return service.markDrinkAsFavourite(coffeeDrinkId, accessToken, CoffeeDrinkFavouriteValueModel(true))
            .onErrorResumeNext { handleHttpError(it) }
            .map { convertCoffeeDrinkResponseToCoffeeDrinkEntities(it) }
            .flatMap { Single.just(it.first()) }
    }

    override fun removeCoffeeDrinkFromFavourite(
        coffeeDrinkId: Long,
        accessToken: String
    ): Single<CoffeeDrinkEntity> {
        return service.markDrinkAsFavourite(coffeeDrinkId, accessToken, CoffeeDrinkFavouriteValueModel(false))
            .onErrorResumeNext { handleHttpError(it) }
            .map { convertCoffeeDrinkResponseToCoffeeDrinkEntities(it) }
            .flatMap { Single.just(it.first()) }
    }

    private fun handleHttpError(error: Throwable): Single<ResponseModel<CoffeeDrinkDataModel>> {
        if (error is HttpException) {
            return Single.error(httpExceptionMapper.mapToDataException(error))
        }
        return Single.error(error)
    }

    private fun convertCoffeeDrinkResponseToCoffeeDrinkEntities(
        response: ResponseModel<CoffeeDrinkDataModel>
    ): List<CoffeeDrinkEntity> {
        return response.data.data.map { coffeeMapper.mapFromModel(it) }
    }
}