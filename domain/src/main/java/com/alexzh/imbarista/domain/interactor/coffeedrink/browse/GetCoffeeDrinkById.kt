package com.alexzh.imbarista.domain.interactor.coffeedrink.browse

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.SingleUseCase
import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import io.reactivex.Single
import java.lang.IllegalArgumentException

class GetCoffeeDrinkById(
    private val coffeeDrinksRepository: CoffeeDrinksRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<CoffeeDrink, GetCoffeeDrinkById.Param>(postExecutionThread) {

    override fun buildSingleUseCase(param: Param?): Single<CoffeeDrink> {
        if (param == null) throw IllegalArgumentException("Param can't be null")
        return coffeeDrinksRepository.getCoffeeDrinkById(param.coffeeId)
    }

    data class Param(val coffeeId: Long) {
        companion object {
            fun forCoffee(id: Long): Param {
                return Param(id)
            }
        }
    }
}