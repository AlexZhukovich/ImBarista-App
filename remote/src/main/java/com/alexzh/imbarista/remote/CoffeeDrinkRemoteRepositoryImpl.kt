package com.alexzh.imbarista.remote

import com.alexzh.data.model.CoffeeEntity
import com.alexzh.data.repository.CoffeesRemoteRepository
import com.alexzh.imbarista.remote.mapper.CoffeeMapper
import com.alexzh.imbarista.remote.service.CoffeeDrinksService
import io.reactivex.Single
import java.lang.UnsupportedOperationException

class CoffeeDrinkRemoteRepositoryImpl(
    private val service: CoffeeDrinksService,
    private val mapper: CoffeeMapper
) : CoffeesRemoteRepository {

    override fun getCoffees(): Single<List<CoffeeEntity>> {
        return Single.error(UnsupportedOperationException("Not implemented yet"))
    }
}