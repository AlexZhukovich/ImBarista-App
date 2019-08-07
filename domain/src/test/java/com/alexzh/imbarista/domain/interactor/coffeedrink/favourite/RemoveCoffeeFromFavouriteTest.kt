package com.alexzh.imbarista.domain.interactor.coffeedrink.favourite

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.domain.GenerateDomainTestData.generateRemoveCoffeeFromFavourite
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import org.junit.Test
import java.lang.IllegalArgumentException

class RemoveCoffeeFromFavouriteTest {

    private val repository = mockk<CoffeeDrinksRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val removeCoffeeFromFavourite = RemoveCoffeeFromFavourite(
        repository,
        postExecutionThread
    )

    @Test
    fun removeCoffeeFromFavouriteCompletesSuccessfullyWhenParamIsCorrect() {
        val param = generateRemoveCoffeeFromFavourite()
        stubRemoveCoffeeFromFavourite(param.coffeeId, Completable.complete())

        removeCoffeeFromFavourite.buildCompletableUseCase(param)
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun removeCoffeeFromFavouriteThrowsExceptionWhenParamIsMissing() {
        val coffeeId = randomLong()
        stubRemoveCoffeeFromFavourite(coffeeId, Completable.complete())

        removeCoffeeFromFavourite.buildCompletableUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun removeCoffeeFromFavouriteThrowsExceptionWhenParamIsNull() {
        val coffeeId = randomLong()
        stubRemoveCoffeeFromFavourite(coffeeId, Completable.complete())

        removeCoffeeFromFavourite.buildCompletableUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubRemoveCoffeeFromFavourite(coffeeId: Long, completable: Completable) {
        every { repository.removeCoffeeDrinkFromFavourites(coffeeId) } returns completable
    }
}