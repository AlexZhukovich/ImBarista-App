package com.alexzh.imbarista.domain.interactor.coffeedrink.browse

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.SingleUseCase
import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import io.reactivex.Single
import java.lang.IllegalArgumentException

class GetCoffeeDrinksByName(
    private val coffeeDrinksRepository: CoffeeDrinksRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<CoffeeDrink>, GetCoffeeDrinksByName.Param>(postExecutionThread) {

    override fun buildSingleUseCase(param: Param?): Single<List<CoffeeDrink>> {
        if (param == null) throw IllegalArgumentException("Param can't be null")
        return coffeeDrinksRepository.getCoffeeDrinksByName(param.coffeeName)
    }

    data class Param(val coffeeName: String) {
        companion object {
            fun forCoffeeDrinks(name: String): Param {
                return Param(name)
            }
        }
    }
}