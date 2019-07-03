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

class GetCoffeesByNameTest {

    private val repository = mockk<CoffeesRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val getCoffeesByName = GetCoffeesByName(repository, postExecutionThread)

    @Test
    fun getCoffeesByNameCompletesSuccessfullyWhenParamIsCorrect() {
        val coffeeName = "Espresso"
        val coffees = mutableListOf(
            Coffee(
                id = 1,
                name = coffeeName,
                description = "Espresso description",
                ingredients = listOf(Ingredient("test ingredient"))
            )
        )
        val param = GetCoffeesByName.Param.forCoffees(coffeeName)

        stubGetCoffeesByName(coffeeName, Single.just(coffees))

        getCoffeesByName.buildSingleUseCase(param)
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeesByNameReturnsCorrectDataWhenParamIsCorrect() {
        val coffeeName = "Espresso"
        val coffees = mutableListOf(
            Coffee(
                id = 1,
                name = coffeeName,
                description = "Espresso description",
                ingredients = listOf(Ingredient("test ingredient"))
            )
        )
        val param = GetCoffeesByName.Param.forCoffees(coffeeName)

        stubGetCoffeesByName(coffeeName, Single.just(coffees))

        getCoffeesByName.buildSingleUseCase(param)
            .test()
            .assertValue(coffees)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getCoffeesByNameThrowsExceptionWhenParamIsMissing() {
        val coffeeName = "Espresso"
        val coffees = mutableListOf(
            Coffee(
                id = 1,
                name = coffeeName,
                description = "Espresso description",
                ingredients = listOf(Ingredient("test ingredient"))
            )
        )

        stubGetCoffeesByName(coffeeName, Single.just(coffees))

        getCoffeesByName.buildSingleUseCase()
            .test()
            .assertValue(coffees)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getCoffeesByNameThrowsExceptionWhenParamIsNull() {
        val coffeeName = "Espresso"
        val coffees = mutableListOf(
            Coffee(
                id = 1,
                name = coffeeName,
                description = "Espresso description",
                ingredients = listOf(Ingredient("test ingredient"))
            )
        )

        stubGetCoffeesByName(coffeeName, Single.just(coffees))

        getCoffeesByName.buildSingleUseCase(null)
            .test()
            .assertValue(coffees)
    }

    private fun stubGetCoffeesByName(name: String, coffees: Single<List<Coffee>>) {
        every { repository.getCoffeesByName(name) } returns coffees
    }
}