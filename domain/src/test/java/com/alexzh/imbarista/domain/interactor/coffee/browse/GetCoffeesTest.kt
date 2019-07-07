package com.alexzh.imbarista.domain.interactor.coffee.browse

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.Coffee
import com.alexzh.imbarista.domain.model.Ingredient
import com.alexzh.imbarista.domain.repository.CoffeesRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class GetCoffeesTest {

    private val repository = mockk<CoffeesRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val getCoffees = GetCoffees(repository, postExecutionThread)

    @Test
    fun getCoffeesCompletesSuccessfully() {
        val coffees = listOf(
            Coffee(
                id = 1,
                name = "Espresso",
                description = "Espresso description",
                ingredients = listOf(Ingredient("test ingredient"))
            )
        )
        stubGetCoffees(Single.just(coffees))

        getCoffees.buildSingleUseCase()
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeesReturnsCorrectData() {
        val coffees = listOf(
            Coffee(
                id = 1,
                name = "Espresso",
                description = "Espresso description",
                ingredients = listOf(Ingredient("test ingredient"))
            )
        )
        stubGetCoffees(Single.just(coffees))

        getCoffees.buildSingleUseCase()
            .test()
            .assertValue(coffees)
    }

    private fun stubGetCoffees(single: Single<List<Coffee>>) {
        every { repository.getCoffees() } returns single
    }
}