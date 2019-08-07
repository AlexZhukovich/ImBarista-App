package com.alexzh.imbarista.domain.interactor.coffeedrink.browse

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.SingleUseCase
import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import io.reactivex.Single

open class GetCoffeeDrinks constructor(
    private val coffeeDrinksRepository: CoffeeDrinksRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<CoffeeDrink>, Nothing?>(postExecutionThread) {

    override fun buildSingleUseCase(param: Nothing?): Single<List<CoffeeDrink>> {
        return coffeeDrinksRepository.getCoffeeDrinks()
    }
}