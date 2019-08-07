package com.alexzh.imbarista.domain.interactor.coffee.browse

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.Coffee
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import com.alexzh.testdata.domain.GenerateDomainTestData.generateCoffee
import com.alexzh.testdata.domain.GenerateDomainTestData.generateGetCoffeeByIdParam
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class GetCoffeeByIdTest {

    private val repository = mockk<CoffeeDrinksRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val getCoffeeById = GetCoffeeById(
        repository,
        postExecutionThread
    )

    @Test
    fun getCoffeesByIdCompletesSuccessfullyWhenParamIsCorrect() {
        val coffee = generateCoffee()
        val param = generateGetCoffeeByIdParam(coffee.id)
        stubGetCoffeesById(param.coffeeId, Single.just(coffee))

        getCoffeeById.buildSingleUseCase(param)
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeesByIdReturnsCorrectDataWhenParamIsCorrect() {
        val coffee = generateCoffee()
        val param = generateGetCoffeeByIdParam(coffee.id)
        stubGetCoffeesById(param.coffeeId, Single.just(coffee))

        getCoffeeById.buildSingleUseCase(param)
            .test()
            .assertValue(coffee)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getCoffeesByIdThrowsExceptionWhenParamIsMissing() {
        val coffee = generateCoffee()
        val param = generateGetCoffeeByIdParam(coffee.id)
        stubGetCoffeesById(param.coffeeId, Single.just(coffee))

        getCoffeeById.buildSingleUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getCoffeesByIdThrowsExceptionWhenParamIsNull() {
        val coffee = generateCoffee()
        val param = generateGetCoffeeByIdParam(coffee.id)
        stubGetCoffeesById(param.coffeeId, Single.just(coffee))

        getCoffeeById.buildSingleUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubGetCoffeesById(coffeeId: Long, single: Single<Coffee>) {
        every { repository.getCoffeesById(coffeeId) } returns single
    }
}