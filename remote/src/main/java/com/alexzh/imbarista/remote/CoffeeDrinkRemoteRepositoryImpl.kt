package com.alexzh.imbarista.remote

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.data.repository.CoffeeDrinksRemoteRepository
import com.alexzh.imbarista.remote.mapper.CoffeeMapper
import com.alexzh.imbarista.remote.model.CoffeeDrinkFavouriteValueModel
import com.alexzh.imbarista.remote.service.CoffeeDrinksService
import io.reactivex.Single
import java.lang.UnsupportedOperationException

class CoffeeDrinkRemoteRepositoryImpl(
    private val service: CoffeeDrinksService,
    private val mapper: CoffeeMapper
) : CoffeeDrinksRemoteRepository {

    override fun getCoffeeDrinks(
        accessToken: String
    ): Single<List<CoffeeDrinkEntity>> {
        return service.getCoffeeDrinks(accessToken)
            .map { response ->
                response.data.data.map { coffeeDrinkModel ->
                    mapper.mapFromModel(coffeeDrinkModel)
                }
            }
    }

    override fun getCoffeeById(coffeeDrinkId: Long, accessToken: String): Single<CoffeeDrinkEntity> {
        return service.getCoffeeDrinkById(coffeeDrinkId, accessToken)
            .map { response ->
                response.data.data.map { coffeeDrinkModel ->
                    mapper.mapFromModel(coffeeDrinkModel)
                }
            }
            .flatMap {
                Single.just(it.first())
            }
    }

    override fun addCoffeeDrinkToFavourite(
        coffeeDrinkId: Long,
        isFavourite: Boolean,
        accessToken: String
    ): Single<CoffeeDrinkEntity> {
        return service.markDrinkAsFavourite(coffeeDrinkId, accessToken, CoffeeDrinkFavouriteValueModel(isFavourite))
            .map { response ->
                response.data.data.map { coffeeDrinkModel ->
                    mapper.mapFromModel(coffeeDrinkModel)
                }
            }
            .flatMap {
                Single.just(it.first())
            }
    }

    override fun removeCoffeeDrinkFromFavourite(
        coffeeDrinkId: Long,
        isFavourite: Boolean,
        accessToken: String
    ): Single<CoffeeDrinkEntity> {
        return service.markDrinkAsFavourite(coffeeDrinkId, accessToken, CoffeeDrinkFavouriteValueModel(isFavourite))
            .map { response ->
                response.data.data.map { coffeeDrinkModel ->
                    mapper.mapFromModel(coffeeDrinkModel)
                }
            }
            .flatMap {
                Single.just(it.first())
            }
    }
}