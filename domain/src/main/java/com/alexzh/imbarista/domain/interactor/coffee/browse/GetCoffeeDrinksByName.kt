package com.alexzh.imbarista.domain.interactor.coffee.browse

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.SingleUseCase
import com.alexzh.imbarista.domain.model.Coffee
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import io.reactivex.Single
import java.lang.IllegalArgumentException

class GetCoffeeDrinksByName(
    private val coffeeDrinksRepository: CoffeeDrinksRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Coffee>, GetCoffeeDrinksByName.Param>(postExecutionThread) {

    override fun buildSingleUseCase(param: Param?): Single<List<Coffee>> {
        if (param == null) throw IllegalArgumentException("Param can't be null")
        return coffeeDrinksRepository.getCoffeesByName(param.coffeeName)
    }

    data class Param(val coffeeName: String) {
        companion object {
            fun forCoffees(name: String): Param {
                return Param(name)
            }
        }
    }
}