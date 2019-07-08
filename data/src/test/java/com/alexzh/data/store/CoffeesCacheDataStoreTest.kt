package com.alexzh.data.store

import com.alexzh.data.model.CoffeeEntity
import com.alexzh.data.repository.CoffeesCacheRepository
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.base.RandomData.randomString
import com.alexzh.testdata.data.GenerateDataTestData.generateCoffeeEntities
import com.alexzh.testdata.data.GenerateDataTestData.generateCoffeeEntity
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test

class CoffeesCacheDataStoreTest {

    private val repository = mockk<CoffeesCacheRepository>()

    private val dataStore = CoffeesCacheDataStore(repository)

    @Test
    fun getCoffeesReturnsCompletesSuccessfully() {
        stubGetCoffees(Single.just(generateCoffeeEntities(2)))

        dataStore.getCoffees()
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeesReturnsReturnsCorrectData() {
        val coffees = generateCoffeeEntities(2)
        stubGetCoffees(Single.just(coffees))

        dataStore.getCoffees()
            .test()
            .assertValue(coffees)
    }

    @Test
    fun getCoffeesByNameCompletesSuccessfully() {
        val coffeeName = randomString()
        stubGetCoffeesByName(coffeeName, Single.just(generateCoffeeEntities(2)))

        dataStore.getCoffeesByName(coffeeName)
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeesByNameReturnsCorrectData() {
        val coffeeName = randomString()
        val coffees = generateCoffeeEntities(2)
        stubGetCoffeesByName(coffeeName, Single.just(coffees))

        dataStore.getCoffeesByName(coffeeName)
            .test()
            .assertValue(coffees)
    }

    @Test
    fun getCoffeeByIdReturnsCompletesSuccessfully() {
        val coffeeId = randomLong()
        stubGetCoffeeById(coffeeId, Single.just(generateCoffeeEntity()))

        dataStore.getCoffeeById(coffeeId)
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeeByIdReturnsCorrectData() {
        val coffeeId = randomLong()
        val coffee = generateCoffeeEntity()
        stubGetCoffeeById(coffeeId, Single.just(coffee))

        dataStore.getCoffeeById(coffeeId)
            .test()
            .assertValue(coffee)
    }

    @Test
    fun setCoffeeAsFavouriteCompletesSuccessfully() {
        val coffeeId = randomLong()
        stubSetCoffeeAsFavourite(coffeeId, Completable.complete())

        dataStore.setCoffeeAsFavourite(coffeeId)
            .test()
            .assertComplete()
    }

    @Test
    fun setCoffeeAsNotFavouriteCompletesSuccessfully() {
        val coffeeId = randomLong()
        stubSetCoffeeAsNotFavourite(coffeeId, Completable.complete())

        dataStore.setCoffeeAsNotFavourite(coffeeId)
            .test()
            .assertComplete()
    }

    @Test
    fun saveCoffeesCompletesSuccessfully() {
        val coffees = generateCoffeeEntities(2)
        stubSaveCoffees(coffees, Completable.complete())

        dataStore.saveCoffees(coffees)
            .test()
            .assertComplete()
    }

    @Test
    fun clearCoffeesCompletesSuccessfully() {
        stubClearCoffees(Completable.complete())

        dataStore.clearCoffees()
            .test()
            .assertComplete()
    }

    private fun stubGetCoffees(coffeesSingle: Single<List<CoffeeEntity>>) {
        every { repository.getCoffees() } returns coffeesSingle
    }

    private fun stubGetCoffeesByName(
        name: String,
        coffeesSingle: Single<List<CoffeeEntity>>
    ) {
        every { repository.getCoffeesByName(name) } returns coffeesSingle
    }

    private fun stubGetCoffeeById(
        id: Long,
        coffeeSingle: Single<CoffeeEntity>
    ) {
        every { repository.getCoffeeById(id) } returns coffeeSingle
    }

    private fun stubSetCoffeeAsFavourite(
        id: Long,
        completable: Completable
    ) {
        every { repository.setCoffeeAsFavourite(id) } returns completable
    }

    private fun stubSetCoffeeAsNotFavourite(
        id: Long,
        completable: Completable
    ) {
        every { repository.setCoffeeAsNotFavourite(id) } returns completable
    }

    private fun stubSaveCoffees(
        coffees: List<CoffeeEntity>,
        completable: Completable
    ) {
        every { repository.saveCoffees(coffees) } returns completable
    }

    private fun stubClearCoffees(
        completable: Completable
    ) {
        every { repository.clearCoffees() } returns completable
    }
}