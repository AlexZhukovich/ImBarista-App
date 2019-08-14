package com.alexzh.imbarista.domain.interactor.coffeedrink.favourite

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.domain.GenerateDomainTestData.generateCoffeeDrink
import com.alexzh.testdata.domain.GenerateDomainTestData.generateRemoveCoffeeFromFavourite
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test
import java.lang.IllegalArgumentException

class RemoveCoffeeDrinkFromFavouriteTest {

    private val repository = mockk<CoffeeDrinksRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val removeCoffeeFromFavourite = RemoveCoffeeDrinkFromFavourite(
        repository,
        postExecutionThread
    )

    @Test
    fun removeCoffeeFromFavouriteCompletesSuccessfullyWhenParamIsCorrect() {
        val param = generateRemoveCoffeeFromFavourite()
        val coffeeDrink = generateCoffeeDrink()
        stubRemoveCoffeeFromFavourite(param.coffeeDrinkId, Single.just(coffeeDrink))

        removeCoffeeFromFavourite.buildSingleUseCase(param)
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun removeCoffeeFromFavouriteThrowsExceptionWhenParamIsMissing() {
        val coffeeId = randomLong()
        val coffeeDrink = generateCoffeeDrink()
        stubRemoveCoffeeFromFavourite(coffeeId, Single.just(coffeeDrink))

        removeCoffeeFromFavourite.buildSingleUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun removeCoffeeFromFavouriteThrowsExceptionWhenParamIsNull() {
        val coffeeId = randomLong()
        val coffeeDrink = generateCoffeeDrink()
        stubRemoveCoffeeFromFavourite(coffeeId, Single.just(coffeeDrink))

        removeCoffeeFromFavourite.buildSingleUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubRemoveCoffeeFromFavourite(coffeeId: Long, single: Single<CoffeeDrink>) {
        every { repository.removeCoffeeDrinkFromFavourites(coffeeId) } returns single
    }
}