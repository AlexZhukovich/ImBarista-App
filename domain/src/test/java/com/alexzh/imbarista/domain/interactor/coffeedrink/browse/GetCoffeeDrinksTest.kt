package com.alexzh.imbarista.domain.interactor.coffeedrink.browse

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import com.alexzh.testdata.domain.GenerateDomainTestData
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class GetCoffeeDrinksTest {

    private val repository = mockk<CoffeeDrinksRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val getCoffeeDrinks = GetCoffeeDrinks(
        repository,
        postExecutionThread
    )

    @Test
    fun getCoffeeDrinksCompletesSuccessfully() {
        val coffeeDrinks = GenerateDomainTestData.generateCoffeeDrinks()
        stubGetCoffeeDrinks(Single.just(coffeeDrinks))

        getCoffeeDrinks.buildSingleUseCase()
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeeDrinksReturnsCorrectData() {
        val coffeeDrinks = GenerateDomainTestData.generateCoffeeDrinks()
        stubGetCoffeeDrinks(Single.just(coffeeDrinks))

        getCoffeeDrinks.buildSingleUseCase()
            .test()
            .assertValue(coffeeDrinks)
    }

    private fun stubGetCoffeeDrinks(single: Single<List<CoffeeDrink>>) {
        every { repository.getCoffeeDrinks() } returns single
    }
}