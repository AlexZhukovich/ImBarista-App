package com.alexzh.imbarista.domain.interactor.coffee.browse

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.SingleUseCase
import com.alexzh.imbarista.domain.model.Coffee
import com.alexzh.imbarista.domain.repository.CoffeesRepository
import io.reactivex.Single
import java.lang.IllegalArgumentException

class GetCoffeesById(
    private val coffeesRepository: CoffeesRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Coffee, GetCoffeesById.Param>(postExecutionThread) {

    override fun buildSingleUseCase(param: Param?): Single<Coffee> {
        if (param == null) throw IllegalArgumentException("Param can't be null")
        return coffeesRepository.getCoffeesById(param.coffeeId)
    }

    data class Param(val coffeeId: Long) {
        companion object {
            fun forCoffee(id: Long): Param {
                return Param(id)
            }
        }
    }
}