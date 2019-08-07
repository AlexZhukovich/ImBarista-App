package com.alexzh.imbarista.domain.interactor.coffee.browse

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.SingleUseCase
import com.alexzh.imbarista.domain.model.Coffee
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import io.reactivex.Single

open class GetCoffeeDrinks constructor(
    private val coffeeDrinksRepository: CoffeeDrinksRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Coffee>, Nothing?>(postExecutionThread) {

    override fun buildSingleUseCase(param: Nothing?): Single<List<Coffee>> {
        return coffeeDrinksRepository.getCoffees()
    }
}