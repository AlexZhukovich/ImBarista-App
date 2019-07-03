package com.alexzh.imbarista.domain.interactor.browse

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.Coffee
import com.alexzh.imbarista.domain.model.Ingredient
import com.alexzh.imbarista.domain.repository.CoffeesRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test
import java.lang.IllegalArgumentException

class GetCoffeesByIdTest {

    private val repository = mockk<CoffeesRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val getCoffeesById = GetCoffeesById(repository, postExecutionThread)

    @Test
    fun getCoffeesByIdCompletesSuccessfullyWhenParamIsCorrect() {
        val coffeeId = 1L
        val coffee = Coffee(
            id = coffeeId,
            name = "Espresso",
            description = "Espresso description",
            ingredients = listOf(Ingredient("test ingredient"))
        )
        val param = GetCoffeesById.Param.forCoffee(coffeeId)
        stubGetCoffeesById(coffeeId, Single.just(coffee))

        getCoffeesById.buildSingleUseCase(param)
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeesByIdReturnsCorrectDataWhenParamIsCorrect() {
        val coffeeId = 1L
        val coffee = Coffee(
            id = coffeeId,
            name = "Espresso",
            description = "Espresso description",
            ingredients = listOf(Ingredient("test ingredient"))
        )
        val param = GetCoffeesById.Param.forCoffee(coffeeId)
        stubGetCoffeesById(coffeeId, Single.just(coffee))

        getCoffeesById.buildSingleUseCase(param)
            .test()
            .assertValue(coffee)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getCoffeesByIdThrowsExceptionWhenParamIsMissing() {
        val coffeeId = 1L
        val coffee = Coffee(
            id = coffeeId,
            name = "Espresso",
            description = "Espresso description",
            ingredients = listOf(Ingredient("test ingredient"))
        )
        stubGetCoffeesById(coffeeId, Single.just(coffee))

        getCoffeesById.buildSingleUseCase()
            .test()
            .assertValue(coffee)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getCoffeesByIdThrowsExceptionWhenParamIsNull() {
        val coffeeId = 1L
        val coffee = Coffee(
            id = coffeeId,
            name = "Espresso",
            description = "Espresso description",
            ingredients = listOf(Ingredient("test ingredient"))
        )
        stubGetCoffeesById(coffeeId, Single.just(coffee))

        getCoffeesById.buildSingleUseCase(null)
            .test()
            .assertValue(coffee)
    }

    private fun stubGetCoffeesById(coffeeId: Long, single: Single<Coffee>) {
        every { repository.getCoffeesById(coffeeId) } returns single
    }
}