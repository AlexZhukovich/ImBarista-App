package com.alexzh.data.store

import com.alexzh.data.mapper.AuthExceptionMapper
import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.data.repository.CoffeeDrinksRemoteRepository
import com.alexzh.data.repository.PreferencesRepository
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.base.RandomData.randomString
import com.alexzh.testdata.data.GenerateDataTestData.generateCoffeeEntities
import com.alexzh.testdata.data.GenerateDataTestData.generateCoffeeEntity
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

    private val preferencesRepository = mockk<PreferencesRepository>()

    private val authExceptionMapper = mockk<AuthExceptionMapper>()

    private val store = CoffeeDrinksRemoteDataStore(repository, preferencesRepository, authExceptionMapper)

    @Test
    fun getCoffeeDrinksCompletesSuccessfully() {
        val accessToken = randomString()
        stubGetAccessToken(accessToken)
        stubGetCoffeeDrinks(Single.just(generateCoffeeEntities(2)))
        store.getCoffeeDrinks()
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeeDrinksReturnsCorrectData() {
        val accessToken = randomString()
        stubGetAccessToken(accessToken)
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
    fun getCoffeeByIdCompletesSuccessfully() {
        val coffeeDrinkId = randomLong()
        val accessToken = randomString()
        val coffeeDrink = generateCoffeeEntity()

        stubGetAccessToken(accessToken)
        stubGetCoffeeDrinkById(coffeeDrinkId, accessToken, Single.just(coffeeDrink))

        store.getCoffeeById(coffeeDrinkId)
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeeByIdReturnsCorrectData() {
        val coffeeDrinkId = randomLong()
        val accessToken = randomString()
        val coffeeDrink = generateCoffeeEntity()

        stubGetAccessToken(accessToken)
        stubGetCoffeeDrinkById(coffeeDrinkId, accessToken, Single.just(coffeeDrink))

        store.getCoffeeById(coffeeDrinkId)
            .test()
            .assertValue(coffeeDrink)
    }

    @Test
    fun setCoffeeAsFavouriteCompletesSuccessfully() {
        val coffeeDrinkId = randomLong()
        val accessToken = randomString()
        val coffeeDrink = generateCoffeeEntity()

        stubGetAccessToken(accessToken)
        stubSetCoffeeAsFavourite(coffeeDrinkId, accessToken, Single.just(coffeeDrink))

        store.setCoffeeAsFavourite(coffeeDrinkId)
            .test()
            .assertComplete()
    }

    @Test
    fun setCoffeeAsFavouriteReturnsCorrectData() {
        val coffeeDrinkId = randomLong()
        val accessToken = randomString()
        val coffeeDrink = generateCoffeeEntity()

        stubGetAccessToken(accessToken)
        stubSetCoffeeAsFavourite(coffeeDrinkId, accessToken, Single.just(coffeeDrink))

        store.setCoffeeAsFavourite(coffeeDrinkId)
            .test()
            .assertValue(coffeeDrink)
    }

    @Test
    fun setCoffeeAsNotFavouriteCompletesSuccessfully() {
        val coffeeDrinkId = randomLong()
        val accessToken = randomString()
        val coffeeDrink = generateCoffeeEntity()

        stubGetAccessToken(accessToken)
        stubSetCoffeeAsNotFavourite(coffeeDrinkId, accessToken, Single.just(coffeeDrink))

        store.setCoffeeAsNotFavourite(coffeeDrinkId)
            .test()
            .assertComplete()
    }

    @Test
    fun setCoffeeAsNotFavouriteReturnsCorrectData() {
        val coffeeDrinkId = randomLong()
        val accessToken = randomString()
        val coffeeDrink = generateCoffeeEntity()

        stubGetAccessToken(accessToken)
        stubSetCoffeeAsNotFavourite(coffeeDrinkId, accessToken, Single.just(coffeeDrink))

        store.setCoffeeAsNotFavourite(coffeeDrinkId)
            .test()
            .assertValue(coffeeDrink)
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
        every { repository.getCoffeeDrinks(any()) } returns coffeeDrinksSingle
    }

    private fun stubGetCoffeeDrinkById(coffeeDrinkId: Long, accessToken: String, single: Single<CoffeeDrinkEntity>) {
        every { repository.getCoffeeById(coffeeDrinkId, accessToken) } returns single
    }

    private fun stubSetCoffeeAsFavourite(coffeeDrinkId: Long, accessToken: String, single: Single<CoffeeDrinkEntity>) {
        every { repository.addCoffeeDrinkToFavourite(coffeeDrinkId, accessToken) } returns single
    }

    private fun stubSetCoffeeAsNotFavourite(coffeeDrinkId: Long, accessToken: String, single: Single<CoffeeDrinkEntity>) {
        every { repository.removeCoffeeDrinkFromFavourite(coffeeDrinkId, accessToken) } returns single
    }

    private fun stubGetAccessToken(accessToken: String) {
        every { preferencesRepository.getSessionInfo().accessToken } returns accessToken
    }
}