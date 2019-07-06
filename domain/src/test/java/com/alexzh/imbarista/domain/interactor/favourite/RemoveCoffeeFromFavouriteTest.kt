package com.alexzh.imbarista.domain.interactor.favourite

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.repository.CoffeesRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import org.junit.Test
import java.lang.IllegalArgumentException

class RemoveCoffeeFromFavouriteTest {

    private val repository = mockk<CoffeesRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val removeCoffeeFromFavourite = RemoveCoffeeFromFavourite(
        repository,
        postExecutionThread
    )

    @Test
    fun removeCoffeeFromFavouriteCompletesSuccessfullyWhenParamIsCorrect() {
        val coffeeId = 1L
        val param = RemoveCoffeeFromFavourite.Param.forCoffee(coffeeId)
        stubRemoveCoffeeFromFavourite(coffeeId, Completable.complete())

        removeCoffeeFromFavourite.buildCompletableUseCase(param)
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun removeCoffeeFromFavouriteThrowsExceptionWhenParamIsMissing() {
        val coffeeId = 1L
        stubRemoveCoffeeFromFavourite(coffeeId, Completable.complete())

        removeCoffeeFromFavourite.buildCompletableUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun removeCoffeeFromFavouriteThrowsExceptionWhenParamIsNull() {
        val coffeeId = 1L
        stubRemoveCoffeeFromFavourite(coffeeId, Completable.complete())

        removeCoffeeFromFavourite.buildCompletableUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubRemoveCoffeeFromFavourite(coffeeId: Long, completable: Completable) {
        every { repository.removeCoffeeFromFavourites(coffeeId) } returns completable
    }
}