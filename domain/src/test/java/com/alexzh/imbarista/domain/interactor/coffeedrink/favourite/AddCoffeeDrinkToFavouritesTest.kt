package com.alexzh.imbarista.domain.interactor.coffeedrink.favourite

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.domain.GenerateDomainTestData
import com.alexzh.testdata.domain.GenerateDomainTestData.generateCoffeeDrink
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class AddCoffeeDrinkToFavouritesTest {

    private val repository = mockk<CoffeeDrinksRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val addCoffeeToFavourites = AddCoffeeDrinkToFavourites(
        repository,
        postExecutionThread
    )

    @Test
    fun addCoffeeToFavouritesCompletesSuccessfullyWhenParamIsCorrect() {
        val coffeeDrink = generateCoffeeDrink()
        val param = GenerateDomainTestData.generateAddCoffeeToFavourites()
        stubAddCoffeeToFavourites(param.coffeeDrinkId, Single.just(coffeeDrink))

        addCoffeeToFavourites.buildSingleUseCase(param)
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun addCoffeeToFavouritesThrowsExceptionWhenParamIsMissing() {
        val coffeeId = randomLong()
        val coffeeDrink = generateCoffeeDrink()
        stubAddCoffeeToFavourites(coffeeId, Single.just(coffeeDrink))

        addCoffeeToFavourites.buildSingleUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun addCoffeeToFavouritesThrowsExceptionWhenParamIsNull() {
        val coffeeId = randomLong()
        val coffeeDrink = generateCoffeeDrink()
        stubAddCoffeeToFavourites(coffeeId, Single.just(coffeeDrink))

        addCoffeeToFavourites.buildSingleUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubAddCoffeeToFavourites(
        coffeeId: Long,
        single: Single<CoffeeDrink>
    ) {
        every { repository.addCoffeeDrinkToFavourites(coffeeId) } returns single
    }
}