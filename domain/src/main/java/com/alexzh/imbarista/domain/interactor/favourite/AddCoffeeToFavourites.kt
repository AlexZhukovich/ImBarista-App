package com.alexzh.imbarista.domain.interactor.favourite

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.CompletableUseCase
import com.alexzh.imbarista.domain.repository.CoffeesRepository
import io.reactivex.Completable
import java.lang.IllegalArgumentException

class AddCoffeeToFavourites constructor(
    private val coffeesRepository: CoffeesRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<AddCoffeeToFavourites.Param>(postExecutionThread) {

    override fun buildCompletableUseCase(params: Param?): Completable {
        if (params == null) throw IllegalArgumentException("Param can't be null")
        return coffeesRepository.addCoffeeToFavourites(params.coffeeId)
    }

    data class Param(val coffeeId: Long) {
        companion object {
            fun forCoffee(id: Long): Param {
                return Param(id)
            }
        }
    }
}