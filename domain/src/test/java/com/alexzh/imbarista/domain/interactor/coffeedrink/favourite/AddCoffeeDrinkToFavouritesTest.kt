package com.alexzh.imbarista.domain.interactor.coffeedrink.favourite

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.domain.GenerateDomainTestData
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import org.junit.Test

class AddCoffeeDrinkToFavouritesTest {

    private val repository = mockk<CoffeeDrinksRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val addCoffeeToFavourites = AddCoffeeToFavourites(
        repository,
        postExecutionThread
    )

    @Test
    fun addCoffeeToFavouritesCompletesSuccessfullyWhenParamIsCorrect() {
        val param = GenerateDomainTestData.generateAddCoffeeToFavourites()
        stubAddCoffeeToFavourites(param.coffeeId, Completable.complete())

        addCoffeeToFavourites.buildCompletableUseCase(param)
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun addCoffeeToFavouritesThrowsExceptionWhenParamIsMissing() {
        val coffeeId = randomLong()
        stubAddCoffeeToFavourites(coffeeId, Completable.complete())

        addCoffeeToFavourites.buildCompletableUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun addCoffeeToFavouritesThrowsExceptionWhenParamIsNull() {
        val coffeeId = randomLong()
        stubAddCoffeeToFavourites(coffeeId, Completable.complete())

        addCoffeeToFavourites.buildCompletableUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubAddCoffeeToFavourites(coffeeId: Long, completable: Completable) {
        every { repository.addCoffeeDrinkToFavourites(coffeeId) } returns completable
    }
}