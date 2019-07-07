package com.alexzh.imbarista.domain.interactor.coffee.favourite

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.repository.CoffeesRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import org.junit.Test
import java.lang.IllegalArgumentException

class AddCoffeeToFavouritesTest {

    private val repository = mockk<CoffeesRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val addCoffeeToFavourites = AddCoffeeToFavourites(
        repository,
        postExecutionThread
    )

    @Test
    fun addCoffeeToFavouritesCompletesSuccessfullyWhenParamIsCorrect() {
        val coffeeId = 1L
        val param = AddCoffeeToFavourites.Param.forCoffee(coffeeId)
        stubAddCoffeeToFavourites(coffeeId, Completable.complete())

        addCoffeeToFavourites.buildCompletableUseCase(param)
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun addCoffeeToFavouritesThrowsExceptionWhenParamIsMissing() {
        val coffeeId = 1L
        stubAddCoffeeToFavourites(coffeeId, Completable.complete())

        addCoffeeToFavourites.buildCompletableUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun addCoffeeToFavouritesThrowsExceptionWhenParamIsNull() {
        val coffeeId = 1L
        stubAddCoffeeToFavourites(coffeeId, Completable.complete())

        addCoffeeToFavourites.buildCompletableUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubAddCoffeeToFavourites(coffeeId: Long, completable: Completable) {
        every { repository.addCoffeeToFavourites(coffeeId) } returns completable
    }
}