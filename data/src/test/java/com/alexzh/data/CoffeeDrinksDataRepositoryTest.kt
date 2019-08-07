package com.alexzh.data

import com.alexzh.data.mapper.CoffeeMapper
import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.data.repository.CoffeeDrinksCacheRepository
import com.alexzh.data.store.CoffeeDrinksDataStoreFactory
import com.alexzh.data.store.CoffeeDrinksRemoteDataStore
import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.base.RandomData.randomString
import com.alexzh.testdata.data.GenerateDataTestData.generateCoffeeEntity
import com.alexzh.testdata.domain.GenerateDomainTestData.generateCoffee
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test

class CoffeeDrinksDataRepositoryTest {

    private val mapper = mockk<CoffeeMapper>()
    private val storeFactory = mockk<CoffeeDrinksDataStoreFactory>()
    private val cacheRepository = mockk<CoffeeDrinksCacheRepository>()
    private val remoteDataStore = mockk<CoffeeDrinksRemoteDataStore>()

    private val dataRepository = CoffeeDrinksDataRepository(
        mapper,
        cacheRepository,
        storeFactory
    )

    @Test
    fun getCoffeeDrinksCompletesSuccessfully() {
        val coffeeEntity = generateCoffeeEntity()
        val coffee = generateCoffee()

        stubGetRemoteDataStore()
        stubGetCoffeeDrinksFromRemoteStore(Single.just(listOf(coffeeEntity)))
        stubMapFromEntity(coffeeEntity, coffee)

        dataRepository.getCoffeeDrinks()
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeeDrinksReturnsCorrectData() {
        val coffeeEntity = generateCoffeeEntity()
        val coffee = generateCoffee()
        val expectedCoffeeDrinks = listOf(coffee)

        stubGetRemoteDataStore()
        stubGetCoffeeDrinksFromRemoteStore(Single.just(listOf(coffeeEntity)))
        stubMapFromEntity(coffeeEntity, coffee)

        dataRepository.getCoffeeDrinks()
            .test()
            .assertValue(expectedCoffeeDrinks)
    }

    @Test
    fun getCoffeeDrinksByNameThrowsException() {
        dataRepository.getCoffeeDrinksByName(randomString())
            .test()
            .assertError(java.lang.UnsupportedOperationException::class.java)
    }

    @Test
    fun getCoffeeDrinkByIdCompletesSuccessfully() {
        val coffeeEntity = generateCoffeeEntity()
        val coffee = generateCoffee()

        stubGetRemoteDataStore()
        stubGetCoffeeFromRemoteStoreById(coffeeEntity.id, Single.just(coffeeEntity))
        stubMapFromEntity(coffeeEntity, coffee)

        dataRepository.getCoffeeDrinkById(randomLong())
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeeDrinkByIdReturnsCorrectData() {
        val coffeeEntity = generateCoffeeEntity()
        val coffee = generateCoffee()

        stubGetRemoteDataStore()
        stubGetCoffeeFromRemoteStoreById(coffeeEntity.id, Single.just(coffeeEntity))
        stubMapFromEntity(coffeeEntity, coffee)

        dataRepository.getCoffeeDrinkById(randomLong())
            .test()
            .assertValue(coffee)
    }

    @Test
    fun addCoffeeToFavouritesCompletesSuccessfully() {
        val coffeeId = randomLong()

        stubGetRemoteDataStore()
        stubAddCoffeeToFavourites(coffeeId, Completable.complete())

        dataRepository.addCoffeeDrinkToFavourites(coffeeId)
            .test()
            .assertComplete()
    }

    @Test
    fun removeCoffeeFromFavouritesCompletesSuccessfully() {
        val coffeeId = randomLong()

        stubGetRemoteDataStore()
        stubRemoveCoffeeFromFavourites(coffeeId, Completable.complete())

        dataRepository.removeCoffeeDrinkFromFavourites(randomLong())
            .test()
            .assertComplete()
    }

    private fun stubGetRemoteDataStore() {
        every { storeFactory.getRemoteDataStore() } returns remoteDataStore
    }

    private fun stubGetCoffeeDrinksFromRemoteStore(coffeeDrinksSingle: Single<List<CoffeeDrinkEntity>>) {
        every { remoteDataStore.getCoffeeDrinks() } returns coffeeDrinksSingle
    }

    private fun stubGetCoffeeFromRemoteStoreById(coffeeId: Long, coffeeDrinkSingle: Single<CoffeeDrinkEntity>) {
        every { remoteDataStore.getCoffeeById(coffeeId) } returns coffeeDrinkSingle
    }

    private fun stubMapFromEntity(
        coffeeDrinkEntities: CoffeeDrinkEntity,
        coffeeDrink: CoffeeDrink
    ) {
        every { mapper.mapFromEntity(coffeeDrinkEntities) } returns coffeeDrink
    }

    private fun stubAddCoffeeToFavourites(
        coffeeId: Long,
        completable: Completable
    ) {
        every { remoteDataStore.setCoffeeAsFavourite(coffeeId) } returns completable
    }

    private fun stubRemoveCoffeeFromFavourites(
        coffeeId: Long,
        completable: Completable
    ) {
        every { remoteDataStore.setCoffeeAsNotFavourite(coffeeId) } returns completable
    }
}