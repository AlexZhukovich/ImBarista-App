package com.alexzh.data.store

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.data.repository.CoffeeDrinksCacheRepository
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.base.RandomData.randomString
import com.alexzh.testdata.data.GenerateDataTestData.generateCoffeeEntities
import com.alexzh.testdata.data.GenerateDataTestData.generateCoffeeEntity
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test

class CoffeeDrinksCacheDataStoreTest {

    private val repository = mockk<CoffeeDrinksCacheRepository>()

    private val dataStore = CoffeeDrinksCacheDataStore(repository)

    @Test
    fun getCoffeeDrinksReturnsCompletesSuccessfully() {
        stubGetCoffeeDrinks(Single.just(generateCoffeeEntities(2)))

        dataStore.getCoffeeDrinks()
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeeDrinksReturnsReturnsCorrectData() {
        val coffeeDrinks = generateCoffeeEntities(2)
        stubGetCoffeeDrinks(Single.just(coffeeDrinks))

        dataStore.getCoffeeDrinks()
            .test()
            .assertValue(coffeeDrinks)
    }

    @Test
    fun getCoffeeDrinksByNameCompletesSuccessfully() {
        val coffeeName = randomString()
        stubGetCoffeeDrinksByName(coffeeName, Single.just(generateCoffeeEntities(2)))

        dataStore.getCoffeeDrinksByName(coffeeName)
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeeDrinksByNameReturnsCorrectData() {
        val coffeeName = randomString()
        val coffeeDrinks = generateCoffeeEntities(2)
        stubGetCoffeeDrinksByName(coffeeName, Single.just(coffeeDrinks))

        dataStore.getCoffeeDrinksByName(coffeeName)
            .test()
            .assertValue(coffeeDrinks)
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
    fun saveCoffeeDrinksCompletesSuccessfully() {
        val coffeeDrinks = generateCoffeeEntities(2)
        stubSaveCoffeeDrinks(coffeeDrinks, Completable.complete())

        dataStore.saveCoffeeDrinks(coffeeDrinks)
            .test()
            .assertComplete()
    }

    @Test
    fun clearCoffeeDrinksCompletesSuccessfully() {
        stubClearCoffeeDrinks(Completable.complete())

        dataStore.clearCoffeeDrinks()
            .test()
            .assertComplete()
    }

    private fun stubGetCoffeeDrinks(coffeeDrinksSingle: Single<List<CoffeeDrinkEntity>>) {
        every { repository.getCoffeeDrinks() } returns coffeeDrinksSingle
    }

    private fun stubGetCoffeeDrinksByName(
        name: String,
        coffeeDrinksSingle: Single<List<CoffeeDrinkEntity>>
    ) {
        every { repository.getCoffeeDrinksByName(name) } returns coffeeDrinksSingle
    }

    private fun stubGetCoffeeById(
        id: Long,
        coffeeDrinkSingle: Single<CoffeeDrinkEntity>
    ) {
        every { repository.getCoffeeById(id) } returns coffeeDrinkSingle
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

    private fun stubSaveCoffeeDrinks(
        coffeeDrinks: List<CoffeeDrinkEntity>,
        completable: Completable
    ) {
        every { repository.saveCoffeeDrinks(coffeeDrinks) } returns completable
    }

    private fun stubClearCoffeeDrinks(
        completable: Completable
    ) {
        every { repository.clearCoffeeDrinks() } returns completable
    }
}