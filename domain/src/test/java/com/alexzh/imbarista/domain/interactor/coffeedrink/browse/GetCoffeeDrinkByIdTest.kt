package com.alexzh.imbarista.domain.interactor.coffeedrink.browse

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import com.alexzh.testdata.domain.GenerateDomainTestData.generateCoffee
import com.alexzh.testdata.domain.GenerateDomainTestData.generateGetCoffeeByIdParam
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class GetCoffeeDrinkByIdTest {

    private val repository = mockk<CoffeeDrinksRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val getCoffeeDrinkById = GetCoffeeDrinkById(
        repository,
        postExecutionThread
    )

    @Test
    fun getCoffeeDrinkByIdCompletesSuccessfullyWhenParamIsCorrect() {
        val coffee = generateCoffee()
        val param = generateGetCoffeeByIdParam(coffee.id)
        stubGetCoffeeDrinksById(param.coffeeId, Single.just(coffee))

        getCoffeeDrinkById.buildSingleUseCase(param)
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeeDrinkByIdReturnsCorrectDataWhenParamIsCorrect() {
        val coffee = generateCoffee()
        val param = generateGetCoffeeByIdParam(coffee.id)
        stubGetCoffeeDrinksById(param.coffeeId, Single.just(coffee))

        getCoffeeDrinkById.buildSingleUseCase(param)
            .test()
            .assertValue(coffee)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getCoffeeDrinkByIdThrowsExceptionWhenParamIsMissing() {
        val coffee = generateCoffee()
        val param = generateGetCoffeeByIdParam(coffee.id)
        stubGetCoffeeDrinksById(param.coffeeId, Single.just(coffee))

        getCoffeeDrinkById.buildSingleUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getCoffeeDrinkByIdThrowsExceptionWhenParamIsNull() {
        val coffee = generateCoffee()
        val param = generateGetCoffeeByIdParam(coffee.id)
        stubGetCoffeeDrinksById(param.coffeeId, Single.just(coffee))

        getCoffeeDrinkById.buildSingleUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubGetCoffeeDrinksById(coffeeId: Long, single: Single<CoffeeDrink>) {
        every { repository.getCoffeeDrinkById(coffeeId) } returns single
    }
}