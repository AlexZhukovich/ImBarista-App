package com.alexzh.data

import com.alexzh.data.mapper.CoffeeMapper
import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.data.repository.CoffeeDrinksCacheRepository
import com.alexzh.data.store.CoffeeDrinksDataStoreFactory
import com.alexzh.data.store.CoffeeDrinksRemoteDataStore
import com.alexzh.imbarista.domain.model.CoffeeDrink
import com.alexzh.imbarista.domain.repository.UserRepository
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.base.RandomData.randomString
import com.alexzh.testdata.data.GenerateDataTestData.generateCoffeeEntity
import com.alexzh.testdata.domain.GenerateDomainTestData.generateCoffeeDrink
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class CoffeeDrinksDataRepositoryTest {

    private val mapper = mockk<CoffeeMapper>()
    private val storeFactory = mockk<CoffeeDrinksDataStoreFactory>()
    private val cacheRepository = mockk<CoffeeDrinksCacheRepository>()
    private val remoteDataStore = mockk<CoffeeDrinksRemoteDataStore>()
    private val userRepository = mockk<UserRepository>()

    private val dataRepository = CoffeeDrinksDataRepository(
        mapper,
        cacheRepository,
        storeFactory,
        userRepository
    )

    @Test
    fun getCoffeeDrinksCompletesSuccessfully() {
        val coffeeEntity = generateCoffeeEntity()
        val coffee = generateCoffeeDrink()

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
        val coffee = generateCoffeeDrink()
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
        val coffee = generateCoffeeDrink()

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
        val coffee = generateCoffeeDrink()

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
        val coffeeDrinkEntity = generateCoffeeEntity()
        val coffeeDrink = generateCoffeeDrink()
        stubGetRemoteDataStore()
        stubMapFromEntity(coffeeDrinkEntity, coffeeDrink)
        stubAddCoffeeToFavourites(coffeeId, Single.just(coffeeDrinkEntity))

        dataRepository.addCoffeeDrinkToFavourites(coffeeId)
            .test()
            .assertComplete()
    }

    @Test
    fun addCoffeeToFavouritesReturnsCorrectData() {
        val coffeeId = randomLong()
        val coffeeDrinkEntity = generateCoffeeEntity()
        val coffeeDrink = generateCoffeeDrink()
        stubGetRemoteDataStore()
        stubMapFromEntity(coffeeDrinkEntity, coffeeDrink)
        stubAddCoffeeToFavourites(coffeeId, Single.just(coffeeDrinkEntity))

        dataRepository.addCoffeeDrinkToFavourites(coffeeId)
            .test()
            .assertValue(coffeeDrink)
    }

    @Test
    fun removeCoffeeFromFavouritesCompletesSuccessfully() {
        val coffeeId = randomLong()
        val coffeeDrinkEntity = generateCoffeeEntity()
        val coffeeDrink = generateCoffeeDrink()
        stubGetRemoteDataStore()
        stubMapFromEntity(coffeeDrinkEntity, coffeeDrink)
        stubRemoveCoffeeFromFavourites(coffeeId, Single.just(coffeeDrinkEntity))

        dataRepository.removeCoffeeDrinkFromFavourites(randomLong())
            .test()
            .assertComplete()
    }

    @Test
    fun removeCoffeeFromFavouritesReturnsCorrectData() {
        val coffeeId = randomLong()
        val coffeeDrinkEntity = generateCoffeeEntity()
        val coffeeDrink = generateCoffeeDrink()
        stubGetRemoteDataStore()
        stubMapFromEntity(coffeeDrinkEntity, coffeeDrink)
        stubRemoveCoffeeFromFavourites(coffeeId, Single.just(coffeeDrinkEntity))

        dataRepository.removeCoffeeDrinkFromFavourites(randomLong())
            .test()
            .assertValue(coffeeDrink)
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
        single: Single<CoffeeDrinkEntity>
    ) {
        every { remoteDataStore.setCoffeeAsFavourite(coffeeId) } returns single
    }

    private fun stubRemoveCoffeeFromFavourites(
        coffeeId: Long,
        single: Single<CoffeeDrinkEntity>
    ) {
        every { remoteDataStore.setCoffeeAsNotFavourite(coffeeId) } returns single
    }
}