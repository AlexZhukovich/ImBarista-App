package com.alexzh.imbarista.remote

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.data.repository.CoffeeDrinksRemoteRepository
import com.alexzh.imbarista.remote.mapper.CoffeeMapper
import com.alexzh.imbarista.remote.service.CoffeeDrinksService
import io.reactivex.Single
import java.lang.UnsupportedOperationException

class CoffeeDrinkRemoteRepositoryImpl(
    private val service: CoffeeDrinksService,
    private val mapper: CoffeeMapper
) : CoffeeDrinksRemoteRepository {

    override fun getCoffeeDrinks(): Single<List<CoffeeDrinkEntity>> {
        return Single.error(UnsupportedOperationException("Not implemented yet"))
    }
}