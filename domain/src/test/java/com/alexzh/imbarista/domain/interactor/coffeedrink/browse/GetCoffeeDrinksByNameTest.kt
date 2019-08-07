package com.alexzh.imbarista.domain.interactor.coffeedrink.browse

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import com.alexzh.testdata.domain.GenerateDomainTestData.generateCoffeeDrinks
import com.alexzh.testdata.domain.GenerateDomainTestData.generateGetCoffeeDrinksByNameParam
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class GetCoffeeDrinksByNameTest {

    private val repository = mockk<CoffeeDrinksRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val getCoffeeDrinksByName = GetCoffeeDrinksByName(
        repository,
        postExecutionThread
    )

    @Test
    fun getCoffeeDrinksByNameCompletesSuccessfullyWhenParamIsCorrect() {
        val coffeeDrinks = generateCoffeeDrinks()
        val param = generateGetCoffeeDrinksByNameParam(coffeeDrinks.first().name)
        stubGetCoffeeDrinksByName(param.coffeeName, Single.just(coffeeDrinks))

        getCoffeeDrinksByName.buildSingleUseCase(param)
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeeDrinksByNameReturnsCorrectDataWhenParamIsCorrect() {
        val coffeeDrinks = generateCoffeeDrinks()
        val param = generateGetCoffeeDrinksByNameParam(coffeeDrinks.first().name)
        stubGetCoffeeDrinksByName(param.coffeeName, Single.just(coffeeDrinks))

        getCoffeeDrinksByName.buildSingleUseCase(param)
            .test()
            .assertValue(coffeeDrinks)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getCoffeeDrinksByNameThrowsExceptionWhenParamIsMissing() {
        val coffeeDrinks = generateCoffeeDrinks()
        val param = generateGetCoffeeDrinksByNameParam(coffeeDrinks.first().name)
        stubGetCoffeeDrinksByName(param.coffeeName, Single.just(coffeeDrinks))

        getCoffeeDrinksByName.buildSingleUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getCoffeeDrinksByNameThrowsExceptionWhenParamIsNull() {
        val coffeeDrinks = generateCoffeeDrinks()
        val param = generateGetCoffeeDrinksByNameParam(coffeeDrinks.first().name)
        stubGetCoffeeDrinksByName(param.coffeeName, Single.just(coffeeDrinks))

        getCoffeeDrinksByName.buildSingleUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubGetCoffeeDrinksByName(name: String, coffeeDrinks: Single<List<CoffeeDrink>>) {
        every { repository.getCoffeeDrinksByName(name) } returns coffeeDrinks
    }
}