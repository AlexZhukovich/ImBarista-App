package com.alexzh.data.store

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.data.repository.CoffeeDrinksRemoteRepository
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.base.RandomData.randomString
import com.alexzh.testdata.data.GenerateDataTestData.generateCoffeeEntities
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.lang.UnsupportedOperationException

class CoffeeDrinksRemoteDataStoreTest {

    @Rule @JvmField
    val expectedException: ExpectedException = ExpectedException.none()

    private val repository = mockk<CoffeeDrinksRemoteRepository>()

    private val store = CoffeeDrinksRemoteDataStore(repository)

    @Test
    fun getCoffeeDrinksCompletesSuccessfully() {
        stubGetCoffeeDrinks(Single.just(generateCoffeeEntities(2)))
        store.getCoffeeDrinks()
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeeDrinksReturnsCorrectData() {
        val coffeeDrinks = generateCoffeeEntities(2)
        stubGetCoffeeDrinks(Single.just(coffeeDrinks))
        store.getCoffeeDrinks()
            .test()
            .assertValue(coffeeDrinks)
    }

    @Test
    fun getCoffeeDrinksByNameThrowsException() {
        expectedException.expect(UnsupportedOperationException::class.java)
        expectedException.expectMessage("'Getting coffee drinks by name' operation is unsupported")

        store.getCoffeeDrinksByName(randomString())
    }

    @Test
    fun getCoffeeByIdThrowsException() {
        expectedException.expect(UnsupportedOperationException::class.java)
        expectedException.expectMessage("'Getting coffee by id' operation is unsupported")

        store.getCoffeeById(randomLong())
    }

    @Test
    fun setCoffeeAsFavouriteThrowsException() {
        expectedException.expect(UnsupportedOperationException::class.java)
        expectedException.expectMessage("'Setting coffee as favourite' operation is unsupported")

        store.setCoffeeAsFavourite(randomLong())
    }

    @Test
    fun setCoffeeAsNotFavouriteThrowsException() {
        expectedException.expect(UnsupportedOperationException::class.java)
        expectedException.expectMessage("'Setting coffee as not favourite' operation is unsupported")

        store.setCoffeeAsNotFavourite(randomLong())
    }

    @Test
    fun saveCoffeeDrinksThrowsException() {
        expectedException.expect(UnsupportedOperationException::class.java)
        expectedException.expectMessage("'Saving coffee drinks' operation is unsupported")

        store.saveCoffeeDrinks(generateCoffeeEntities())
    }

    @Test
    fun clearCoffeeDrinksThrowsException() {
        expectedException.expect(UnsupportedOperationException::class.java)
        expectedException.expectMessage("'Clearing coffee drinks' operation is unsupported")

        store.clearCoffeeDrinks()
    }

    private fun stubGetCoffeeDrinks(coffeeDrinksSingle: Single<List<CoffeeDrinkEntity>>) {
        every { repository.getCoffeeDrinks() } returns coffeeDrinksSingle
    }
}