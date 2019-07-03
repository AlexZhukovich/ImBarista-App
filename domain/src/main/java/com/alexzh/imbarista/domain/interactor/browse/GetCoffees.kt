package com.alexzh.imbarista.domain.interactor.browse

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.SingleUseCase
import com.alexzh.imbarista.domain.model.Coffee
import com.alexzh.imbarista.domain.repository.CoffeesRepository
import io.reactivex.Single

open class GetCoffees constructor(
    private val coffeesRepository: CoffeesRepository,
    postExecutionThread: PostExecutionThread
): SingleUseCase<List<Coffee>, Nothing?>(postExecutionThread) {

    override fun buildSingleUseCase(params: Nothing?): Single<List<Coffee>> {
        return coffeesRepository.getCoffees()
    }
}