package com.alexzh.imbarista.domain.interactor.coffee.browse

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.Coffee
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import com.alexzh.testdata.domain.GenerateDomainTestData.generateCoffees
import com.alexzh.testdata.domain.GenerateDomainTestData.generateGetCoffeesByNameParam
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class GetCoffeeDrinksByNameTest {

    private val repository = mockk<CoffeeDrinksRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val getCoffeesByName = GetCoffeeDrinksByName(
        repository,
        postExecutionThread
    )

    @Test
    fun getCoffeesByNameCompletesSuccessfullyWhenParamIsCorrect() {
        val coffees = generateCoffees()
        val param = generateGetCoffeesByNameParam(coffees.first().name)
        stubGetCoffeesByName(param.coffeeName, Single.just(coffees))

        getCoffeesByName.buildSingleUseCase(param)
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeesByNameReturnsCorrectDataWhenParamIsCorrect() {
        val coffees = generateCoffees()
        val param = generateGetCoffeesByNameParam(coffees.first().name)
        stubGetCoffeesByName(param.coffeeName, Single.just(coffees))

        getCoffeesByName.buildSingleUseCase(param)
            .test()
            .assertValue(coffees)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getCoffeesByNameThrowsExceptionWhenParamIsMissing() {
        val coffees = generateCoffees()
        val param = generateGetCoffeesByNameParam(coffees.first().name)
        stubGetCoffeesByName(param.coffeeName, Single.just(coffees))

        getCoffeesByName.buildSingleUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getCoffeesByNameThrowsExceptionWhenParamIsNull() {
        val coffees = generateCoffees()
        val param = generateGetCoffeesByNameParam(coffees.first().name)
        stubGetCoffeesByName(param.coffeeName, Single.just(coffees))

        getCoffeesByName.buildSingleUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubGetCoffeesByName(name: String, coffees: Single<List<Coffee>>) {
        every { repository.getCoffeesByName(name) } returns coffees
    }
}