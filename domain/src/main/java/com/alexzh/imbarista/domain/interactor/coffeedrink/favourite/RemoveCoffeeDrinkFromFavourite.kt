package com.alexzh.imbarista.domain.interactor.coffeedrink.favourite

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.SingleUseCase
import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import io.reactivex.Single

class RemoveCoffeeDrinkFromFavourite constructor(
    private val coffeeDrinksRepository: CoffeeDrinksRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<CoffeeDrink, RemoveCoffeeDrinkFromFavourite.Param>(postExecutionThread) {

    override fun buildSingleUseCase(param: Param?): Single<CoffeeDrink> {
        if (param == null) throw IllegalArgumentException("Param can't be null")
        return coffeeDrinksRepository.removeCoffeeDrinkFromFavourites(param.coffeeDrinkId)
    }

    data class Param(val coffeeDrinkId: Long) {
        companion object {
            fun forCoffeeDrink(id: Long): Param {
                return Param(id)
            }
        }
    }
}