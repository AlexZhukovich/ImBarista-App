package com.alexzh.data.store

import com.alexzh.data.model.CoffeeEntity
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

class CoffeesRemoteDataStoreTest {

    @Rule @JvmField
    val expectedException: ExpectedException = ExpectedException.none()

    private val repository = mockk<CoffeeDrinksRemoteRepository>()

    private val store = CoffeeDrinksRemoteDataStore(repository)

    @Test
    fun getCoffeesCompletesSuccessfully() {
        stubGetCoffees(Single.just(generateCoffeeEntities(2)))
        store.getCoffees()
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeesReturnsCorrectData() {
        val coffees = generateCoffeeEntities(2)
        stubGetCoffees(Single.just(coffees))
        store.getCoffees()
            .test()
            .assertValue(coffees)
    }

    @Test
    fun getCoffeesByNameThrowsException() {
        expectedException.expect(UnsupportedOperationException::class.java)
        expectedException.expectMessage("'Getting coffees by name' operation is unsupported")

        store.getCoffeesByName(randomString())
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
    fun saveCoffeesThrowsException() {
        expectedException.expect(UnsupportedOperationException::class.java)
        expectedException.expectMessage("'Saving coffees' operation is unsupported")

        store.saveCoffees(generateCoffeeEntities())
    }

    @Test
    fun clearCoffeesThrowsException() {
        expectedException.expect(UnsupportedOperationException::class.java)
        expectedException.expectMessage("'Clearing coffees' operation is unsupported")

        store.clearCoffees()
    }

    private fun stubGetCoffees(coffeesSingle: Single<List<CoffeeEntity>>) {
        every { repository.getCoffees() } returns coffeesSingle
    }
}